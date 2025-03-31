package com.glycin.ragvillage.ai

import dev.langchain4j.service.SystemMessage
import dev.langchain4j.service.UserMessage
import io.quarkiverse.langchain4j.RegisterAiService

@RegisterAiService(modelName = "chat", chatMemoryProviderSupplier = LimitedChatMemory::class)
interface QuestionJudge {
    @SystemMessage("""
                You are tasked to analyze the message you receive and extract the type of question from it.
                Use one of the following question types: {questionType:joined()}.
                Return ONLY the question type and nothing else. Do not wrap your response in quotes or in json.
                """)
    fun judgeQuestion(@UserMessage messageToBeJudge: String) : String
}
