package com.glycin.ragvillage.services

import com.glycin.ragvillage.ai.OllamaService
import com.glycin.ragvillage.model.VillageState
import com.glycin.ragvillage.model.Villager
import com.glycin.ragvillage.model.VillagerCommand
import com.glycin.ragvillage.repositories.VillagerRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val LOG = KotlinLogging.logger {}

@Service
class VillageService(
    private val ollama: OllamaService,
    private val villagerRepository: VillagerRepository,
) {

    private val villageState = VillageState()

    fun commandVillager(name: String): VillagerCommand {
        LOG.info { "Commanding villager $name" }
        val villager = villagerRepository.getVillager(name)
        LOG.info { "Found villager ${villager.name} which is currently ${villager.state}" }
        return ollama.commandVillager(villager).also {
            LOG.info { it }
        }
    }

    fun initVillage() {
        TODO("Not yet implemented")
    }

    fun ask(q: String): String {
        return ollama.ask(q)
    }
}