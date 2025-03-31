package com.glycin.ragvillage.controllers

import com.glycin.ragvillage.services.DataImportService
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import org.jboss.resteasy.reactive.RestQuery

@Path("/api/import")
class DataImportController(private val importService: DataImportService) {

    @POST
    @Path("text")
    fun importText() {
        importService.importTextChunk()
    }

    @POST
    @Path("bobRoss")
    fun importBobRoss(@RestQuery dirPath: String) {
        importService.importBobRossPaintings(dirPath)
    }

    @POST
    @Path("audio")
    fun importAudio(@RestQuery dirPath: String) {
        importService.importAudioFiles(dirPath)
    }
}
