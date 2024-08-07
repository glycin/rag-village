package com.glycin.ragvillage.model

data class VillageState(
    val villagers: Set<String>,
    val villageLocations: Set<String>,
    var currentVillageTime: String,
)