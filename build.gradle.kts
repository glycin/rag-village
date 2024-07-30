plugins {
	val kotlinVersion = "2.0.0"
	val springBootVersion = "3.3.2"
	val springDependencyManagementVersion = "1.1.6"

	base
	idea

	id("org.springframework.boot") version springBootVersion
	id("io.spring.dependency-management") version springDependencyManagementVersion
	kotlin("jvm") version kotlinVersion
	kotlin("plugin.spring") version kotlinVersion
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

val springCloudVersion = "2023.0.3"
val langChainVersion = "0.33.0"
val kotlinLoggingVersion = "3.0.5"

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
	}
}

dependencies {
	//Spring
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	//Langchain4j
	implementation("dev.langchain4j:langchain4j-ollama-spring-boot-starter:$langChainVersion")
	implementation("dev.langchain4j:langchain4j-spring-boot-starter:$langChainVersion")

	//Kotlin
	implementation("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")

	//Test
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
