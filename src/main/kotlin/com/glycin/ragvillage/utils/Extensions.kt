package com.glycin.ragvillage.utils

import io.weaviate.client.WeaviateClient
import io.weaviate.client.base.Result
import io.weaviate.client.v1.graphql.model.GraphQLResponse

fun WeaviateClient.hasSchemaWithName(name: String): Boolean = schema().exists().withClassName(name).run().result