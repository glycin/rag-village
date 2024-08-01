package com.glycin.ragvillage.ai.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "langchain4j.ollama")
data class OllamaServiceConfiguration(
    val url: String,
    val modelName: String,
    val temperature: Double,
    val logRequests: Boolean,
    val logResponses: Boolean,
)