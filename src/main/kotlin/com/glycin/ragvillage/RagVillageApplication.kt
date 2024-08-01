package com.glycin.ragvillage

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class RagVillageApplication

fun main(args: Array<String>) {
	runApplication<RagVillageApplication>(*args)
}
