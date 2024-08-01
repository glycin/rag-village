package com.glycin.ragvillage.services

import com.glycin.ragvillage.ai.OllamaService
import com.glycin.ragvillage.model.VillageState
import com.glycin.ragvillage.model.VillagerChatPrompt
import com.glycin.ragvillage.model.VillagerCommand
import com.glycin.ragvillage.model.VillagerCommandPrompt
import com.glycin.ragvillage.repositories.VillagerRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val LOG = KotlinLogging.logger {}

@Service
class VillageService(
    private val ollama: OllamaService,
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

    fun chat(name: String, question: String): String {
        if(!this::villageState.isInitialized) { initVillage() }
        val villager = villagerRepository.getVillager(name)
        LOG.info { "Found villager ${villager.name} which is currently ${villager.state}" }
        return villagerAssistant.chat(villager.name, VillagerChatPrompt(villager, question)).also {
            LOG.info { it }
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