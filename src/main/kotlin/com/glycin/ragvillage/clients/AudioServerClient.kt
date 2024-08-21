package com.glycin.ragvillage.clients

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(value = "AudioServerClient", url = "http://127.0.0.1:8000/embedding")
interface AudioServerClient {

    @GetMapping("/text")
    fun getTextEmbedding(@RequestParam("text") text: String): AudioServerDto

    @GetMapping("/audio")
    fun getAudioEmbedding(@RequestParam("filePath") filePath: String): AudioServerDto
}