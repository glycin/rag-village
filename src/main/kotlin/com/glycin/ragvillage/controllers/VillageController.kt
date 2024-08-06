package com.glycin.ragvillage.controllers

import com.glycin.ragvillage.model.VillagerCommand
import com.glycin.ragvillage.services.VillageService
import kotlinx.coroutines.flow.Flow
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/village")
class VillageController(
    val villageService: VillageService,
) {

    @GetMapping("/command")
    fun commandVillager(@RequestParam("name") name: String): ResponseEntity<VillagerCommand> {
        val response = villageService.commandVillager(name)
        return ResponseEntity.ok().body(response)
    }

    @GetMapping("/chat", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun chatWithVillager(@RequestParam("name") name: String, @RequestParam message: String): ResponseEntity<Flow<String>> {
        val response = villageService.chat(name, message)
        return ResponseEntity.ok().body(response)
    }

    @GetMapping("/ask")
    fun askQuestion(@RequestParam("question") question : String): ResponseEntity<String> {
        val response = villageService.ask(question)
        return ResponseEntity.ok().body(response)
    }
}