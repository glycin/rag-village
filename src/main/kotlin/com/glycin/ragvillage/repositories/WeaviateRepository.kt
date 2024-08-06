package com.glycin.ragvillage.repositories

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.glycin.ragvillage.utils.hasSchemaWithName
import io.weaviate.client.Config
import io.weaviate.client.WeaviateClient
import io.weaviate.client.v1.graphql.query.argument.NearTextArgument
import io.weaviate.client.v1.graphql.query.fields.Field
import mu.KotlinLogging
import org.springframework.stereotype.Repository

private val LOG = KotlinLogging.logger {}

@Repository
class WeaviateRepository {

    private val objectMapper: ObjectMapper = ObjectMapper().registerKotlinModule()
    private val client = WeaviateClient(Config("http", "localhost:8080"))

    fun searchForSimpleText(text: String): List<String> {
        val result = client.graphQL()
            .get()
            .withClassName(WeaviateClassNames.SIMPLE_TEXT)
            .withNearText(
                NearTextArgument.builder()
                    .concepts(arrayOf(text))
                    .distance(0.75f)
                    .build()
            )
            .withLimit(3)
            .withFields(
                Field.builder().name("text").build()
            ).run()

        if(result.hasErrors()) {
            LOG.error{ "Could not search for ${WeaviateClassNames.SIMPLE_TEXT} and text: $text" }
            return emptyList()
        }

        return objectMapper.writeValueAsString(result.result.data).let { json ->
            objectMapper.readValue(json, Data::class.java).let { data ->
                data.get.simpleText.map { it.text }
            }
        }
    }

    fun addSimpleText(text: String): Boolean {
        if(!client.hasSchemaWithName(WeaviateClassNames.SIMPLE_TEXT)) {
            LOG.info { "No schema ${WeaviateClassNames.SIMPLE_TEXT} exists!" }
            return false
        }

        val properties = mapOf("text" to text)

        return client.data().creator()
            .withClassName(WeaviateClassNames.SIMPLE_TEXT)
            .withProperties(properties)
            .run()
            .let {
                if(it.hasErrors()) {
                    LOG.error { "Couldn't add ${WeaviateClassNames.SIMPLE_TEXT} because of ${it.error}" }
                    false
                }else {
                    true
                }
            }
    }
}