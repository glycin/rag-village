package com.glycin.ragvillage.controllers

import com.glycin.ragvillage.services.DataImportService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/import")
class DataImportController(
    private val importService: DataImportService
) {

    @PostMapping("text")
    fun importText() {
        importService.importTextChunk()
    }
}