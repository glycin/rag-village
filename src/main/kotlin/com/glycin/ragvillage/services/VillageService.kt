package com.glycin.ragvillage.services

import com.glycin.ragvillage.ai.OllamaService
import com.glycin.ragvillage.model.*
import com.glycin.ragvillage.repositories.VillagerRepository
import com.glycin.ragvillage.repositories.WeaviateRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.emptyFlow
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val LOG = KotlinLogging.logger {}

@Service
class VillageService(
    ollama: OllamaService,
    private val villagerRepository: VillagerRepository,
    private val weaviate: WeaviateRepository,
) {

    private val villagerAssistant = ollama.villagerAssistant
    private lateinit var villageState : VillageState

    fun commandVillager(name: String): VillagerCommand {
        if(!this::villageState.isInitialized) { initVillage() }
        val villager = villagerRepository.getVillager(name)

        val command = villagerAssistant.commandVillager(villager.name, VillagerCommandPrompt(villager, villageState))

        if(command.wait) {
            villager.state = VillagerState.IDLE
            villager.actions.add(VillagerAction("waiting"))
        }else if (command.talkTo != null) {
            villager.state = VillagerState.TALKING
            villager.actions.add(VillagerAction("talking"))
        }else {
            villager.state = VillagerState.WALKING
            villager.actions.add(VillagerAction("walking"))
        }

        return command
    }

    fun chat(name: String, message: String): Flow<String> {
        if(!this::villageState.isInitialized) { initVillage() }

        val villager = villagerRepository.getVillager(name)
        val context = weaviate.searchForSimpleText(message)

        return callbackFlow {
            val stream = villagerAssistant.chat(villager.name, VillagerChatPrompt(villager,context, message))
            stream.onNext {
                trySend(it).isSuccess
            }.onComplete {
                LOG.info { "completed chat with $name" }
                close()
            }.onError { error ->
                LOG.error { "${error.message}\n${error.stackTrace}" }
                close(error)
            }.start()

            awaitClose {
                // TODO: Necessary to do any cleanup?
            }
        }
    }

    fun villagerChat(firstChatter: String, secondChatter: String): Flow<String> {
        val firstVillager = villagerRepository.getVillager(firstChatter)
        val secondVillager = villagerRepository.getVillager(secondChatter)
        return emptyFlow()
    }

    fun initVillage(): Set<Villager> {
        LOG.info { "initializing village" }
        villageState = VillageState(
            villagerRepository.getAllVillagers().map { it.name }.toSet(),
            setOf("town square", "marketplace", "river", "farm", "library", "armory"),
            "10:00",
        )
        return villagerRepository.getAllVillagers()
    }

    fun ask(q: String): String {
        return villagerAssistant.ask(q)
    }
}