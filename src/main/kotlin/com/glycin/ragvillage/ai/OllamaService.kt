package com.glycin.ragvillage.ai

import com.glycin.ragvillage.ai.configuration.OllamaServiceConfiguration
import com.glycin.ragvillage.model.VillagerChatPrompt
import com.glycin.ragvillage.model.VillagerCommand
import com.glycin.ragvillage.model.VillagerCommandPrompt
import dev.langchain4j.data.message.ChatMessage
import dev.langchain4j.memory.chat.ChatMemoryProvider
import dev.langchain4j.memory.chat.MessageWindowChatMemory
import dev.langchain4j.model.ollama.OllamaChatModel
import dev.langchain4j.service.AiServices
import dev.langchain4j.service.MemoryId
import dev.langchain4j.service.SystemMessage
import dev.langchain4j.service.UserMessage
import org.springframework.stereotype.Service

@Service
class OllamaService(
    config: OllamaServiceConfiguration,
) {
    val villagerAssistant: VillagerAssistant = AiServices.builder(VillagerAssistant::class.java)
        .chatLanguageModel(
            OllamaChatModel.builder()
                .logRequests(config.logRequests)
                .logResponses(config.logResponses)
                .baseUrl(config.url)
                .modelName(config.modelName)
                .temperature(config.temperature)
                .build()
        )
        .chatMemoryProvider { memoryId ->
            MessageWindowChatMemory.withMaxMessages(50)
        }
        .build()
}

interface VillagerAssistant {
    @SystemMessage("""
        You are a helpful assistant and will answer the questions.
    """)
    fun ask(message: String): String

    @SystemMessage("""
        You are controlling villagers in an RPG. The RPG is set in a peaceful orcish town in Mordor.
        You will receive a villager description and a village state and will accordingly change the state based on the villager and the current village state.
        A villager can only move to other villagers and to themselves! The moveTo property should only be the name of the target villager.If a villager shouldn't
        move then leave the moveTo null.
    """)
    fun commandVillager(@MemoryId name: String, @UserMessage villagerPrompt: VillagerCommandPrompt): VillagerCommand

    @SystemMessage("""
        You are the villager in the Villager object within the json you will receive. Answer the question within the question field within the json. 
        Answer the question by fully adapting the personality, age and the state of the villager.
    """)
    fun chat(@MemoryId name: String, @UserMessage villagerPrompt: VillagerChatPrompt): String
}