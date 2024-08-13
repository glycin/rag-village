package com.glycin.ragvillage.ai

import com.glycin.ragvillage.ai.configuration.OllamaVisionConfiguration
import dev.langchain4j.memory.chat.MessageWindowChatMemory
import dev.langchain4j.model.ollama.OllamaChatModel
import dev.langchain4j.model.ollama.OllamaStreamingChatModel
import dev.langchain4j.service.AiServices

// This should be called TheEyeOfSauronService, but ill be a good citizen...
class OllamaVisionService(
    visionConfig: OllamaVisionConfiguration
) {
    val theEye: OllamaVision = AiServices.builder(OllamaVision::class.java)
        .chatLanguageModel(
            OllamaChatModel.builder()
                .logRequests(visionConfig.logRequests)
                .logResponses(visionConfig.logResponses)
                .baseUrl(visionConfig.url)
                .modelName(visionConfig.modelName)
                .temperature(visionConfig.temperature)
                .build()
        )
        .streamingChatLanguageModel(
            OllamaStreamingChatModel.builder()
                .logRequests(visionConfig.logRequests)
                .logResponses(visionConfig.logResponses)
                .baseUrl(visionConfig.url)
                .modelName(visionConfig.modelName)
                .temperature(visionConfig.temperature)
                .build()
        )
        .chatMemoryProvider { memoryId ->
            MessageWindowChatMemory.withMaxMessages(50)
        }
        .build()
}

interface OllamaVision {
    //In base64 encoded
    fun transcribe(image: String): String
}