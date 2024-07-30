package com.glycin.ragvillage.ai

import com.glycin.ragvillage.model.Villager
import com.glycin.ragvillage.model.VillagerCommand
import dev.langchain4j.service.SystemMessage
import dev.langchain4j.service.spring.AiService

@AiService
interface OllamaService {

    @SystemMessage("""
        You are a helpful assistant and will answer the questions.
    """)
    fun ask(message: String): String

    @SystemMessage("""
        You are controlling villagers in an RPG. The RPG is set in a peaceful orcish town in Mordor.
        You will receive a villager description and a village state and will accordingly change the state based on the villager.
    """)
    fun commandVillager(villager: Villager): VillagerCommand
}