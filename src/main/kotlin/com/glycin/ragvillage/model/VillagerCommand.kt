package com.glycin.ragvillage.model

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.ALWAYS)
data class VillagerCommand(
    val moveTo: String?, // Location Name
    val talkTo: String?, // Villager Name
    val wait: Boolean,
)