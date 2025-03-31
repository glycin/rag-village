package com.glycin.ragvillage.services

import com.glycin.ragvillage.ai.LilMinasMorgulTextSegmentTransformer
import com.glycin.ragvillage.clients.AudioServerClient
import com.glycin.ragvillage.repositories.WeaviateRepository
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader
import dev.langchain4j.data.document.parser.TextDocumentParser
import dev.langchain4j.data.document.splitter.DocumentSplitters
import io.quarkus.logging.Log
import jakarta.inject.Singleton
import org.eclipse.microprofile.rest.client.inject.RestClient
import java.io.File


@Singleton
class DataImportService(
    private val weaviate: WeaviateRepository,
    @RestClient private val audioServerClient: AudioServerClient,
) {

    fun importTextChunk() {
        val doc = FileSystemDocumentLoader.loadDocument("C:\\Projects\\rag-village\\src\\main\\resources\\data\\little_minas_morgul", TextDocumentParser() )
        DocumentSplitters.recursive(250, 25)
            .split(doc)
            .map { segments ->
                LilMinasMorgulTextSegmentTransformer().transform(segments)
            }.forEach {
                if(weaviate.addSimpleText(it.text())) {
                    Log.info { "Added text ${it.text()}" }
                }
            }
    }

    fun importBobRossPaintings(directoryPath: String) {
        Log.info { "Loading in bob ross paintings" }
        val directory = File(directoryPath)
        val uris = directory
            .walk()
            .filter { it.isFile && it.extension == "png" }
            .map { it.toURI() }
            .toList()
        weaviate.batchAddImages(uris)
        Log.info { "Finished loading in bob ross paintings" }
    }

    fun importAudioFiles(directoryPath: String) {
        Log.info { "Loading in audio files..." }
        val directory = File(directoryPath)
        val uris = directory
            .walk()
            .filter { it.isFile && it.extension == "wav" || it.extension == "mp3" }
            .associate { it.name to it.toURI() }
        uris.forEach { uri ->
            Log.info { "Getting vectors for ${uri.value.path}" }
            val embedding = audioServerClient.getAudioEmbedding(uri.value.path.trimStart('/'))
            Log.info { "Vector with dimensionality of ${embedding.embedding.size} retrieved, saving ${uri.key} to db..." }
            weaviate.addVector(uri.key, embedding.embedding)
        }
        Log.info { "Finished loading in audio files" }
    }
}
