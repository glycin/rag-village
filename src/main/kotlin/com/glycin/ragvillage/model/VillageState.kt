package com.glycin.ragvillage.model

data class VillageState(
    val villagers: Set<String>,
    val villageLocations: Set<String>,
    var currentVillageTime: String,
)

enum class VillageLocation{
    CITY_HALL, //done
    GARDEN, // done
    POND, // done
    LIBRARY, // done
    RIVER_BRIDE, // DONE
    MARKET,
    HEALER,
    PARK,
    BLACKSMITH,
    INN, //done
    PIER, // DONE
    FOREST, // DONE
}