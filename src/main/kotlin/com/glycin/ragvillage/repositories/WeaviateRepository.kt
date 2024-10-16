package com.glycin.ragvillage.repositories

import com.fasterxml.jackson.databind.ObjectMapper
import com.glycin.ragvillage.utils.hasSchemaWithName
import io.quarkus.logging.Log
import io.weaviate.client.Config
import io.weaviate.client.WeaviateClient
import io.weaviate.client.v1.data.model.WeaviateObject
import io.weaviate.client.v1.graphql.query.argument.NearImageArgument
import io.weaviate.client.v1.graphql.query.argument.NearTextArgument
import io.weaviate.client.v1.graphql.query.argument.NearVectorArgument
import io.weaviate.client.v1.graphql.query.fields.Field
import jakarta.inject.Singleton
import org.apache.commons.io.FileUtils
import java.io.File
import java.net.URI
import java.util.*

@Singleton
class WeaviateRepository(private val objectMapper: ObjectMapper) {

    private val client = WeaviateClient(Config("http", "localhost:8080"))

    fun searchForSimpleText(text: String): List<String> {
        val result = client.graphQL()
            .get()
            .withClassName(WeaviateClassNames.SIMPLE_TEXT)
            .withNearText(
                NearTextArgument.builder()
                    .concepts(arrayOf(text))
                    .distance(0.55f)
                    .build()
            )
            .withLimit(5)
            .withFields(
                Field.builder().name("text").build()
            ).run()

        if(result.hasErrors()) {
            Log.error{ "Could not search for ${WeaviateClassNames.SIMPLE_TEXT} and text: $text" }
            return emptyList()
        }

        return objectMapper.writeValueAsString(result.result.data).let { json ->
            objectMapper.readValue(json, Data::class.java).let { data ->
                data.get.simpleText?.let { simpleText ->
                    simpleText.map { it.text }
                } ?: emptyList()
            }
        }
    }

    fun searchImageNearText(text: String): String {
        val result = client.graphQL()
            .get()
            .withClassName(WeaviateClassNames.STANDARD_MULTI_MODAL)
            .withNearText(
                NearTextArgument.builder()
                    .concepts(arrayOf(text))
                    .distance(0.9f)
                    .build()
            )
            .withLimit(1)
            .withFields(Field.builder().name("image").build())
            .run()

        if(result.hasErrors()) {
            Log.error{ "Could not search for ${WeaviateClassNames.STANDARD_MULTI_MODAL} and text: $text" }
            return ""
        }

        return objectMapper.writeValueAsString(result.result.data).let { json ->
            objectMapper.readValue(json, Data::class.java).get.standardMultimodal?.firstOrNull()?.image ?: ""
        }
    }

    fun searchImageNearImage(image: String): String {
        Log.info { "Searching for image near" }
        val result = client.graphQL()
            .get()
            .withClassName(WeaviateClassNames.STANDARD_MULTI_MODAL)
            .withNearImage(NearImageArgument.builder()
                .image(image)
                .distance(0.9f)
                .build()
            )
            .withLimit(1)
            .withFields(Field.builder().name("image").build())
            .run()

        if(result.hasErrors()) {
            Log.error{ "Could not search for ${WeaviateClassNames.STANDARD_MULTI_MODAL} with an image..." }
            return ""
        }

        return objectMapper.writeValueAsString(result.result.data).let { json ->
            objectMapper.readValue(json, Data::class.java).get.standardMultimodal?.firstOrNull()?.image ?: ""
        }
    }

    fun searchVector(inputVector: List<Float>): String {
        val result = client.graphQL()
            .get()
            .withClassName(WeaviateClassNames.VECTOR_ONLY)
            .withNearVector(NearVectorArgument.builder()
                .vector(inputVector.toTypedArray())
                .distance(0.9f)
                .build())
            .withLimit(1)
            .withFields(Field.builder().name("label").build())
            .run()

        if(result.hasErrors()) {
            Log.error{ "Could not search for ${WeaviateClassNames.VECTOR_ONLY}" }
            return ""
        }

        return objectMapper.writeValueAsString(result.result.data).let { json ->
            objectMapper.readValue(json, Data::class.java).get.vectorOnly?.firstOrNull()?.label ?: ""
        }
    }

    fun addSimpleText(text: String): Boolean {
        if(!client.hasSchemaWithName(WeaviateClassNames.SIMPLE_TEXT)) {
            Log.info { "No schema ${WeaviateClassNames.SIMPLE_TEXT} exists!" }
            return false
        }

        val properties = mapOf("text" to text)

        return client.data().creator()
            .withClassName(WeaviateClassNames.SIMPLE_TEXT)
            .withProperties(properties)
            .run()
            .let {
                if(it.hasErrors()) {
                    Log.error { "Couldn't add ${WeaviateClassNames.SIMPLE_TEXT} because of ${it.error}" }
                    false
                }else {
                    true
                }
            }
    }

    fun addImage(image: String): Boolean {
        if(!client.hasSchemaWithName(WeaviateClassNames.STANDARD_MULTI_MODAL)) {
            Log.info { "No schema ${WeaviateClassNames.STANDARD_MULTI_MODAL} exists!" }
            return false
        }

        val properties = mapOf("image" to image)
        return client.data().creator()
            .withClassName(WeaviateClassNames.STANDARD_MULTI_MODAL)
            .withProperties(properties)
            .run()
            .let {
                if(it.hasErrors()) {
                    Log.error { "Couldn't add ${WeaviateClassNames.STANDARD_MULTI_MODAL} because of ${it.error}" }
                    false
                }else {
                    true
                }
            }
    }

    fun batchAddImages(imagePaths: List<URI>): Boolean {
        if(!client.hasSchemaWithName(WeaviateClassNames.STANDARD_MULTI_MODAL)) {
            Log.info { "No schema ${WeaviateClassNames.STANDARD_MULTI_MODAL} exists!" }
            return false
        }

        val batcher = client.batch().objectsBatcher()
        Log.info { "Importing bob ross paintings" }

        imagePaths.forEachIndexed { i, uri ->
            val painting = File(uri)
            batcher.withObject(
                WeaviateObject.builder()
                    .className(WeaviateClassNames.STANDARD_MULTI_MODAL)
                    .properties(
                        mapOf(
                            "image" to Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(painting)),
                        )
                    )
                    .build()
            )

            if(i % 50 == 0){
                Log.info { "Flushing batcher at $i" }
                batcher.flush()
            }
        }
        val result = batcher.run()
        Log.info { "DONE!" }
        return !result.hasErrors()
    }

    fun addVector(label: String, vector: List<Float>): Boolean {
        if(!client.hasSchemaWithName(WeaviateClassNames.VECTOR_ONLY)) {
            Log.info { "No schema ${WeaviateClassNames.VECTOR_ONLY} exists!" }
            return false
        }

        val properties = mapOf("label" to label)

        return client.data().creator()
            .withClassName(WeaviateClassNames.VECTOR_ONLY)
            .withProperties(properties)
            .withVector(vector.toTypedArray())
            .run()
            .let {
                if(it.hasErrors()) {
                    Log.error { "Couldn't add ${WeaviateClassNames.VECTOR_ONLY} because of ${it.error}" }
                    false
                }else {
                    true
                }
            }
    }
}
