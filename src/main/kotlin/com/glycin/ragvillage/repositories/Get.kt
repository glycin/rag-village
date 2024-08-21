package com.glycin.ragvillage.repositories

import com.fasterxml.jackson.annotation.JsonProperty

data class Data(
    @JsonProperty("Get")
    val get: Get
)

data class Get(
    @JsonProperty("SimpleText")
    val simpleText: List<SimpleText>?,

    @JsonProperty("StandardMultimodal")
    val standardMultimodal: List<StandardMultimodal>?,

    @JsonProperty("VectorOnly")
    val vectorOnly: List<VectorOnly>?,
)

data class SimpleText(
    @JsonProperty("text")
    val text: String
)

data class VectorOnly(
    @JsonProperty("label")
    val label: String,
)

data class StandardMultimodal(
    @JsonProperty("text")
    val text: String?,
    @JsonProperty("image")
    val image: String?,
)