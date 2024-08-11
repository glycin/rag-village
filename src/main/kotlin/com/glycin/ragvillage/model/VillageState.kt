package com.glycin.ragvillage.model

data class VillageState(
    val villagers: Set<String>,
    val villageLocations: Set<String>,
    var currentVillageTime: String,
)

enum class VillageLocation{
    CITY_HALL,
    GARDEN,
    POND,
    LIBRARY,
    RIVER_BRIDGE,
    MARKET,
    HEALER,
    PARK,
    BLACKSMITH,
    INN,
    PIER,
    FOREST,
}