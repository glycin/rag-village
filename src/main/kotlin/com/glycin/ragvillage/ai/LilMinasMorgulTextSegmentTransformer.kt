package com.glycin.ragvillage.ai

import dev.langchain4j.data.segment.TextSegment
import dev.langchain4j.data.segment.TextSegmentTransformer

class LilMinasMorgulTextSegmentTransformer: TextSegmentTransformer {
    override fun transform(segment: TextSegment): TextSegment {
        with(segment) {
            val fileName = metadata().getString("file_name")
            val transformedText = "$fileName\n${text()}"
            return TextSegment.from(transformedText, metadata())
        }
    }
}