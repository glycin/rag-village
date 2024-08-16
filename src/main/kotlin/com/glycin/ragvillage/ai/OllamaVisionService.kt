package com.glycin.ragvillage.ai

import com.glycin.ragvillage.ai.configuration.OllamaVisionConfiguration
import com.glycin.ragvillage.utils.PromptConstants
import dev.langchain4j.data.message.ImageContent
import dev.langchain4j.data.message.TextContent
import dev.langchain4j.data.message.UserMessage
import dev.langchain4j.model.ollama.OllamaChatModel
import mu.KotlinLogging
import org.springframework.stereotype.Service

val LOG = KotlinLogging.logger {}

// This should be called TheEyeOfSauronService, but ill be a good citizen...
@Service
class OllamaVisionService(
    visionConfig: OllamaVisionConfiguration
) {
    private val vision: OllamaChatModel = OllamaChatModel.builder()
                .logRequests(visionConfig.logRequests)
                .logResponses(visionConfig.logResponses)
                .baseUrl(visionConfig.url)
                .modelName(visionConfig.modelName)
                .temperature(visionConfig.temperature)
                .build()

    fun transcribe(image: String): String {
        LOG.info { image }
        return vision.generate(getVisionUserMessage(image)).content().text()
    }

    private fun getVisionUserMessage(image: String): UserMessage {
        return UserMessage.from(
            TextContent.from(PromptConstants.LLAVA_IMAGE_TRANSCRIBE_PROMPT),
            ImageContent.from(image, "image/png", ImageContent.DetailLevel.HIGH)
        )
    }
}