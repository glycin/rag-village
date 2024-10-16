package com.glycin.ragvillage.controllers

import com.glycin.ragvillage.model.ImageBody
import com.glycin.ragvillage.model.Villager
import com.glycin.ragvillage.model.VillagerCommand
import com.glycin.ragvillage.repositories.WeaviateRepository
import com.glycin.ragvillage.services.VillageService
import io.quarkus.logging.Log
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType.SERVER_SENT_EVENTS
import kotlinx.coroutines.flow.Flow
import org.jboss.resteasy.reactive.RestQuery

@Path("/api/village")
class VillageController(
    private val villageService: VillageService,
    private val weaviateRepository: WeaviateRepository) {

    @Path("/command")
    @GET
    fun commandVillager(@RestQuery name: String): VillagerCommand {
        return villageService.commandVillager(name)
    }

    @Path("/chat")
    @GET
    @Produces(SERVER_SENT_EVENTS)
    fun chatWithVillager(@RestQuery name: String, @RestQuery message: String): Flow<String> {
        return villageService.chat(name, message)
    }

    @Path("/chat/betweenVillagers")
    @GET
    fun chatBetweenVillagers(@RestQuery firstVillager: String,
                             @RestQuery secondVillager: String,
                             @RestQuery message: String): String {
        return villageService.chatBetween(firstVillager, secondVillager, message)
    }

    @Path("/chat/getQuestion")
    @GET
    fun chatBetweenVillagers(@RestQuery firstVillager: String,
                             @RestQuery secondVillager: String): String {
        return villageService.getQuestion(firstVillager, secondVillager)
    }

    @Path("/init")
    @GET
    fun initVillage(): Set<Villager> {
        val response = villageService.initVillage()
        Log.info { "Returning ${response.size} villagers" }
        return response
    }

    @Path("/ask")
    @GET
    fun askQuestion(@RestQuery question : String): String {
        return villageService.ask(question)
    }

    @Path("/image/orcTranscribe")
    @POST
    @Produces(SERVER_SENT_EVENTS)
    fun orcTranscribe(image: ImageBody): Flow<String> {
        return villageService.orcishTranscribe(image.image, image.name)
    }

    @Path("/image/transcribe")
    @POST
    fun transcribeImage(imageBase64: ImageBody): String {
        return villageService.transcribe(imageBase64.image)
    }

    @Path("/image")
    @GET
    fun getImage(@RestQuery message: String): String {
        return weaviateRepository.searchImageNearText(message)
    }

    @Path("/image/withImage")
    @POST
    fun getImageWithImage(imageBase64: ImageBody): String {
        return weaviateRepository.searchImageNearImage(imageBase64.image)
    }

    @Path("/audio")
    @GET
    fun getAudioFilename(@RestQuery message: String): String {
        return villageService.searchForAudio(message)
    }

    @Path("/chat/bobhu")
    @GET
    @Produces(SERVER_SENT_EVENTS)
    fun chatWithBobhu(@RestQuery message: String): Flow<String> {
        return villageService.chatWithBobhu(message)
    }

    @Path("/chat/shopkeep")
    @GET
    @Produces(SERVER_SENT_EVENTS)
    fun chatWithShopkeep(@RestQuery message: String): Flow<String> {
        return villageService.chatWithShopkeep(message)
    }

    @Path("/chat/metalhead")
    @GET
    @Produces(SERVER_SENT_EVENTS)
    fun chatWithTheMetalhead(@RestQuery message: String): Flow<String> {
        return villageService.chatWithTheMetalhead(message)
    }

    @Path("/chat/metalhead/sing")
    @GET
    @Produces(SERVER_SENT_EVENTS)
    fun metalheadSinger(@RestQuery message: String, @RestQuery clipName: String): Flow<String> {
        return villageService.createLyricsForAudio(message, clipName)
    }
}
