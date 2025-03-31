package com.glycin.ragvillage.ai

import com.glycin.ragvillage.utils.PromptConstants
import dev.langchain4j.data.message.ImageContent
import dev.langchain4j.data.message.TextContent
import dev.langchain4j.data.message.UserMessage
import dev.langchain4j.model.chat.ChatLanguageModel
import io.quarkiverse.langchain4j.ModelName
import jakarta.inject.Singleton


// This should be called TheEyeOfSauronService, but ill be a good citizen...
@Singleton
class OllamaVisionService(@ModelName("vision") private val vision: ChatLanguageModel) {

    fun transcribe(image: String): String {
        return vision.generate(getVisionUserMessage(image)).content().text()
    }

    private fun getVisionUserMessage(image: String): UserMessage {
        return UserMessage.from(
            TextContent.from(PromptConstants.LLAVA_IMAGE_TRANSCRIBE_PROMPT),
            ImageContent.from(image, "image/png", ImageContent.DetailLevel.HIGH)
        )
    }
}
