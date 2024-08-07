package com.glycin.ragvillage.utils

import com.glycin.ragvillage.model.VillageState
import io.weaviate.client.WeaviateClient

fun WeaviateClient.hasSchemaWithName(name: String): Boolean = schema().exists().withClassName(name).run().result

fun VillageState.toPromptSummary() : String {
    return ""
}