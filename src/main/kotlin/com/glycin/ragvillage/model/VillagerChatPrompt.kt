package com.glycin.ragvillage.model

data class VillagerChatPrompt(
    val villager: Villager,
    val context: List<String>,
    val question: String,
)

data class BetweenVillagersChatPrompt(
    val from: Villager,
    val to: Villager,
    val context: List<String>,
    val question: String,
)

data class GenerateQuestionPrompt(
    val from: Villager,
    val to: Villager,
)