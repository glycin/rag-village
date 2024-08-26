package com.glycin.ragvillage.ai

import com.glycin.ragvillage.ai.configuration.OllamaServiceConfiguration
import com.glycin.ragvillage.model.BetweenVillagersChatPrompt
import com.glycin.ragvillage.model.GenerateQuestionPrompt
import com.glycin.ragvillage.model.VillagerChatPrompt
import com.glycin.ragvillage.model.VillagerCommandPrompt
import com.glycin.ragvillage.utils.PromptConstants
import dev.langchain4j.memory.chat.MessageWindowChatMemory
import dev.langchain4j.model.ollama.OllamaChatModel
import dev.langchain4j.model.ollama.OllamaStreamingChatModel
import dev.langchain4j.service.AiServices
import dev.langchain4j.service.MemoryId
import dev.langchain4j.service.SystemMessage
import dev.langchain4j.service.TokenStream
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
        .streamingChatLanguageModel(
            OllamaStreamingChatModel.builder()
                .logRequests(config.logRequests)
                .logResponses(config.logResponses)
                .baseUrl(config.url)
                .modelName(config.modelName)
                .temperature(config.temperature)
                .build()
        )
        .chatMemoryProvider { memoryId ->
            MessageWindowChatMemory.withMaxMessages(20)
        }
        .build()
}

interface VillagerAssistant {
    @SystemMessage("""You are a helpful assistant and will answer the questions.""")
    fun ask(message: String): String

    @SystemMessage(PromptConstants.COMMAND_PROMPT)
    fun commandVillager(@MemoryId name: String, @UserMessage villagerPrompt: VillagerCommandPrompt): String

    @SystemMessage("""
        ${PromptConstants.VILLAGE_DESCRIPTION}
        You will receive some context in the Context field within the json you will receive.
        You are the villager within little minas morgul as described in the Villager object within the json you will receive. Answer the question within the question field in the json. 
        Answer the question by fully adapting the personality, age and the state of the villager and using the context you receive. 
        Your answer should not be longer than 300 characters.
        Do not wrap the answer in a json or in quotation marks.
    """)
    fun chat(@MemoryId name: String, @UserMessage villagerPrompt: VillagerChatPrompt): TokenStream

    @SystemMessage("""
        ${PromptConstants.VILLAGE_DESCRIPTION}
        You are a villager within little minas morgul. You are the villager as defined in the `to` field within the json you will receive.
        Answer the question by fully adapting the personality, age, state and the context you will receive.
        The questions you receive are from the villager defined in the `from` field in the `BetweenVillagersChatPrompt` json.
        Your answer should not be longer than 300 characters.
        Do not wrap the answer in a json or in quotation marks.
    """)
    fun chatBetween(@MemoryId name: String, @UserMessage prompt: BetweenVillagersChatPrompt): String

    @SystemMessage("""
        ${PromptConstants.VILLAGE_DESCRIPTION}
        You are the villager within little minas morgul defined in the `from` field within the json you will receive.
        Generate a question you would ask to the villager defined in the `to` field within the json. The question should be in first person.
        Your answer should not be longer than 300 characters.
        Do not wrap the answer in a json or in quotation marks.
    """)
    fun getQuestion(@MemoryId name: String, @UserMessage prompt: GenerateQuestionPrompt): String

    @SystemMessage("""
        ${PromptConstants.VILLAGE_DESCRIPTION}
        You are an orc painter called Bobhu Rogosh (tribute to Bob Ross) in Little Minas Morgul. 
        You will receive a description of an image that is painted by the person you are talking to. Rewrite that description.
    """)
    fun describeArtAsBobhu(@MemoryId name: String, @UserMessage description: String): TokenStream

    @SystemMessage("""
        ${PromptConstants.VILLAGE_DESCRIPTION}
        You are the orc Shopkeeper in Little Minas Morgul. You sell Bobhu Rogosh's paintings and are eager to oversell those paintings. 
        You will receive a description of an image that is painted by the person you are talking to. Rewrite that description.
    """)
    fun describeArtAsShopkeep(@MemoryId name: String, @UserMessage description: String): TokenStream

    @SystemMessage("""
        ${PromptConstants.VILLAGE_DESCRIPTION}
        You are an orc shopkeeper in Little Minas Morgul. You are eager to sell your wares.
        ${PromptConstants.SHOPKEEPER_DESCRIPTION}
    """)
    fun shopKeeper(@MemoryId name: String, @UserMessage message: String): TokenStream

    @SystemMessage("""
        ${PromptConstants.VILLAGE_DESCRIPTION}
        You are an orc painter called Bobhu Rogosh (tribute to Bob Ross) in Little Minas Morgul.
        ${PromptConstants.BOBHU_ROGOSH_DESCRIPTION}
        You are eager to let people know that they can draw something in the canvas next to you.
    """)
    fun bobhu(@MemoryId name: String, @UserMessage message: String): TokenStream

    @SystemMessage("""
        ${PromptConstants.VILLAGE_DESCRIPTION}
        You are an orc metalhead known only as "The Metalhead" in Little Minas Morgul.
        ${PromptConstants.THE_METALHEAD_DESCRIPTION}
        You can play music for people you talk to and are eager to spread metal music across middle earth.
    """)
    fun metalhead(@MemoryId name: String, @UserMessage message: String): TokenStream
}