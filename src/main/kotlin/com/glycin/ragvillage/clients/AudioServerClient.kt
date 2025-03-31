package com.glycin.ragvillage.clients

import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import org.jboss.resteasy.reactive.RestQuery

@RegisterRestClient(baseUri = "http://127.0.0.1:8000/embedding")
interface AudioServerClient {

    @Path("/text")
    @GET
    fun getTextEmbedding(@RestQuery text: String): AudioServerDto

    @Path("/audio")
    @GET
    fun getAudioEmbedding(@RestQuery filePath: String): AudioServerDto
}
