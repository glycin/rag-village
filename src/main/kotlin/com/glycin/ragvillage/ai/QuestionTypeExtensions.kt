package com.glycin.ragvillage.ai

import io.quarkus.qute.TemplateExtension

@TemplateExtension(namespace = "questionType")
class QuestionTypeExtensions {

    companion object {
        @JvmStatic
        fun joined() = QuestionType.entries.joinToString()
    }

}
