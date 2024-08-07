package com.glycin.ragvillage.model

data class VillagerChatPrompt(
    val villager: Villager,
    val context: List<String>,
    val question: String,
)