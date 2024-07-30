package com.glycin.ragvillage.controllers

import com.glycin.ragvillage.model.VillagerCommand
import com.glycin.ragvillage.repositories.VillagerRepository
import com.glycin.ragvillage.services.VillageService
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

    @PostMapping("/init")
    fun initVillage(): ResponseEntity<*> {
        villageService.initVillage()
        return ResponseEntity.ok().body("Village initialized")
    }

    @GetMapping("/ask")
    fun askQuestion(@RequestParam("question") question : String): ResponseEntity<String> {
        val response = villageService.ask(question)
        return ResponseEntity.ok().body(response)
    }
}