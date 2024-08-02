package com.glycin.ragvillage.services

import com.glycin.ragvillage.ai.OllamaService
import com.glycin.ragvillage.model.VillageState
import com.glycin.ragvillage.model.VillagerChatPrompt
import com.glycin.ragvillage.model.VillagerCommand
import com.glycin.ragvillage.model.VillagerCommandPrompt
import com.glycin.ragvillage.repositories.VillagerRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val LOG = KotlinLogging.logger {}

@Service
class VillageService(
    ollama: OllamaService,
    private val villagerRepository: VillagerRepository,
) {

    private val villagerAssistant = ollama.villagerAssistant
    private lateinit var villageState : VillageState

    fun commandVillager(name: String): VillagerCommand {
        if(!this::villageState.isInitialized) { initVillage() }
        val villager = villagerRepository.getVillager(name)
        LOG.info { "Found villager ${villager.name} which is currently ${villager.state}" }
        return villagerAssistant.commandVillager(villager.name, VillagerCommandPrompt(villager, villageState)).also {
            LOG.info { it }
        }
    }

    fun chat(name: String, message: String): Flow<String> {
        if(!this::villageState.isInitialized) { initVillage() }

        val villager = villagerRepository.getVillager(name)
        LOG.info { "Found villager ${villager.name} which is currently ${villager.state}" }
        return callbackFlow {
            val stream = villagerAssistant.chat(villager.name, VillagerChatPrompt(villager, message))
            stream.onNext {
                trySend(it).isSuccess
            }.onComplete {
                LOG.info { "completed chat" }
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

    fun initVillage() {
        villageState = VillageState(
            villagerRepository.getAllVillagerNames(),
            "10:00"
        )
    }

    fun ask(q: String): String {
        return villagerAssistant.ask(q)
    }
}