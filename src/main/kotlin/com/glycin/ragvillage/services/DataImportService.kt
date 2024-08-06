package com.glycin.ragvillage.services

import com.glycin.ragvillage.ai.LilMinasMorgulTextSegmentTransformer
import com.glycin.ragvillage.repositories.WeaviateRepository
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader
import dev.langchain4j.data.document.parser.TextDocumentParser
import dev.langchain4j.data.document.splitter.DocumentSplitters
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val LOG = KotlinLogging.logger {}

@Service
class DataImportService(
    private val weaviate: WeaviateRepository
) {

    fun importTextChunk() {
        val doc = FileSystemDocumentLoader.loadDocument("C:\\Projects\\rag-village\\src\\main\\resources\\data\\little_minas_morgul", TextDocumentParser() )
        DocumentSplitters.recursive(250, 25)
            .split(doc)
            .map { segments ->
                LilMinasMorgulTextSegmentTransformer().transform(segments)
            }.forEach {
                if(weaviate.addSimpleText(it.text())) {
                    LOG.info { "Added text ${it.text()}" }
                }
            }
    }
}