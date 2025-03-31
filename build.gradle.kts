plugins {
	val kotlinVersion = "2.0.10"

	base
	idea

	kotlin("jvm") version kotlinVersion
	kotlin("plugin.allopen") version kotlinVersion
	id("io.quarkus")
}

group = "com.glycin"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(22)
	}
}

repositories {
	mavenCentral()
}

val weaviateVersion = "4.8.2"
val apacheCommonsVersion = "2.16.1"
val quarkusLangChain4jVersion = "0.20.3"

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

dependencies {
	implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))

	//Spring
	implementation("io.quarkus:quarkus-kotlin")
	implementation("io.quarkus:quarkus-rest-jackson")
	implementation("io.quarkus:quarkus-rest-client-jackson")

	//Langchain4j
	implementation("io.quarkiverse.langchain4j:quarkus-langchain4j-ollama:$quarkusLangChain4jVersion")

	//Weaviate
	implementation("io.weaviate:client:$weaviateVersion")

	// Misc
	implementation("commons-io:commons-io:$apacheCommonsVersion")

	//Test
	testImplementation("io.quarkus:quarkus-junit5")
	testImplementation("io.rest-assured:rest-assured")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
}

allOpen {
	annotation("jakarta.ws.rs.Path")
	annotation("jakarta.enterprise.context.ApplicationScoped")
	annotation("jakarta.persistence.Entity")
	annotation("io.quarkus.test.junit.QuarkusTest")
}

kotlin {
	compilerOptions {
		javaParameters = true
	}
}
