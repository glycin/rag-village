package com.glycin.ragvillage.ai.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "langchain4j.ollama-vision")
class OllamaVisionConfiguration(
    val url: String,
    val modelName: String,
    val temperature: Double,
    val logRequests: Boolean = false,
    val logResponses: Boolean = false,
)