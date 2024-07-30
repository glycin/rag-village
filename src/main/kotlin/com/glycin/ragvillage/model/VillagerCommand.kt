package com.glycin.ragvillage.model

data class VillagerCommand(
    val changeStateTo: VillagerState,
    val moveTo: String, // Villager name
)