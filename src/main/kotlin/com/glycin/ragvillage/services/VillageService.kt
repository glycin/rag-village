package com.glycin.ragvillage.services

import com.glycin.ragvillage.ai.*
import com.glycin.ragvillage.clients.AudioServerClient
import com.glycin.ragvillage.model.*
import com.glycin.ragvillage.repositories.VillagerRepository
import com.glycin.ragvillage.repositories.WeaviateRepository
import dev.langchain4j.service.TokenStream
import io.quarkus.logging.Log
import jakarta.inject.Singleton
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.eclipse.microprofile.rest.client.inject.RestClient

private const val BOBHU = "BOBHU"
private const val SHOPKEEP = "SHOPKEEPER"
private const val THE_METALHEAD = "THEMETALHEAD"

@Singleton
class VillageService(
    private val villagerAssistant: VillagerAssistant,
    private val commandJudge: CommandJudge,
    private val questionJudge: QuestionJudge,
    private val theEye: OllamaVisionService,
    private val villagerRepository: VillagerRepository,
    private val weaviate: WeaviateRepository,
    @RestClient private val audioServerClient: AudioServerClient,
) {

    private lateinit var villageState : VillageState

    fun commandVillager(name: String): VillagerCommand {
        if(!this::villageState.isInitialized) { initVillage() }
        val villager = villagerRepository.getVillager(name)
        Log.info { "Commanding $name" }
        val potentialCommand = villagerAssistant.commandVillager(villager.name, VillagerCommandPrompt(villager.toPrompt(), villageState))
        Log.info { potentialCommand }
        val command = commandJudge.judgeCommand(potentialCommand)

        if(command.wait) {
            villager.state = VillagerState.IDLE
            villager.actions.add(VillagerAction("waiting"))
        }else if (command.talkTo != null) {
            villager.state = VillagerState.TALKING
            villager.actions.add(VillagerAction("talking"))
        }else {
            villager.state = VillagerState.WALKING
            villager.actions.add(VillagerAction("walking"))
        }

        return command
    }

    fun chat(name: String, message: String): Flow<String> {
        if(!this::villageState.isInitialized) { initVillage() }

        val villager = villagerRepository.getVillager(name)
        val context = weaviate.searchForSimpleText(message)

        return chatFlow("Completed chat with $name") {
            villagerAssistant.chat(villager.name, VillagerChatPrompt(villager,context, message))
        }
    }

    fun chatBetween(first: String, second: String, message: String): String {
        if(!this::villageState.isInitialized) { initVillage() }
        Log.info { "Chatting between $first and $second" }

        val from = villagerRepository.getVillager(first)
        val to = villagerRepository.getVillager(second)
        val context = weaviate.searchForSimpleText(message)
        val prompt = BetweenVillagersChatPrompt(from, to, context, message)
        return villagerAssistant.chatBetween(from.name, prompt)
    }

    fun getQuestion(first: String, second: String): String {
        if(!this::villageState.isInitialized) { initVillage() }
        Log.info { "Getting question for $first addressed to $second" }

        val from = villagerRepository.getVillager(first)
        val to = villagerRepository.getVillager(second)
        val prompt = GenerateQuestionPrompt(from, to)
        return villagerAssistant.getChat(from.name, prompt)
    }

    fun initVillage(): Set<Villager> {
        Log.info { "initializing village" }
        val allVillagers = villagerRepository.getAllVillagers()
        villageState = VillageState(
            allVillagers.map { it.name }.toSet(),
            VillageLocation.entries.map { it.name }.toSet(),
            "10:00",
        )
        return villagerRepository.getAllVillagers()
    }

    fun orcishTranscribe(base64Image: String, orcName: String): Flow<String> {
        Log.info { "transcribing an image as an orc..." }
        return when(orcName.uppercase()) {
            SHOPKEEP -> {
                val description = theEye.transcribe(base64Image)
                chatFlow("The shopkeep is selling you a painting!") {
                    villagerAssistant.describeArtAsShopkeep(SHOPKEEP, description)
                }
            }
            BOBHU -> {
                weaviate.addImage(base64Image)
                val description = theEye.transcribe(base64Image)
                chatFlow("You showed your painting to Bobhu!") {
                    villagerAssistant.describeArtAsBobhu(BOBHU, description)
                }
            }
            else -> {
                val description = theEye.transcribe(base64Image)
                chatFlow("You showed your painting to Bobhu!") {
                    villagerAssistant.describeArtAsBobhu(BOBHU, description)
                }
            }
        }
    }

    fun transcribe(base64Image: String): String {
        Log.info { "transcribing an image..." }
        return theEye.transcribe(base64Image)
    }

    fun searchForAudio(message: String): String {
        Log.info { "getting audio embeddings for text..." }
        val textAudioEmbeddings = audioServerClient.getTextEmbedding(message)
        Log.info { "found embeddings, querying weaviate for audio" }
        return weaviate.searchVector(textAudioEmbeddings.embedding)
    }

    fun chatWithBobhu(message:String): Flow<String> {
        Log.info { "chatting with the one and only Bobhu Rogosh..." }
        return chatFlow("Completed initial chat with Bobhu") {
            villagerAssistant.bobhu(BOBHU, message)
        }
    }

    fun chatWithShopkeep(message:String): Flow<String> {
        if(!this::villageState.isInitialized) { initVillage() }
        Log.info { "Chatting with the shopkeeper" }
        val questionType = QuestionType.fromValueOrDefault(questionJudge.judgeQuestion(message))
        Log.info { "Received a $questionType question" }
        val newMessage = when (questionType) {
            QuestionType.CHAT -> message
            QuestionType.HISTORY -> message // TODO: Do weaviate call to find context
            QuestionType.SHOPPING -> "This is the message you received: $message. Let the customer know you only have paintings"
            QuestionType.SHOPPING_PAINTING -> """
                The customer wants some paintings. Ask the customer, enthusiastically, what kind of painting he is looking for!
                The final word of your response should always be "KACHING".
            """.trimIndent()
            else -> message
        }
        return chatFlow("Completed chat with the shopkeeper") {
            villagerAssistant.shopKeeper(SHOPKEEP, newMessage)
        }
    }

    fun chatWithTheMetalhead(message: String): Flow<String> {
        if(!this::villageState.isInitialized) { initVillage() }
        Log.info { "Chatting with the metalhead" }
        val questionType = QuestionType.fromValueOrDefault(questionJudge.judgeQuestion(message))
        Log.info { "Received a $questionType question" }
        val newMessage = when (questionType) {
            QuestionType.CHAT -> message
            QuestionType.MUSIC -> """
                The person you are talkin to wants to hear some music! Ask them, what kind of music they want to hear!
                The final word of your response should always be "BRUTAL".
            """.trimIndent()
            else -> message
        }
        return chatFlow("Completed chat with the metalhead") {
            villagerAssistant.metalhead(THE_METALHEAD, newMessage)
        }
    }

    fun createLyricsForAudio(message: String, clipName: String): Flow<String> {
        if(!this::villageState.isInitialized) { initVillage() }
        Log.info { "Metalhead is singing for you" }
        val newMessage = """
            You, the Metalhead, are playing a song for the user that is matching the following description: $message. The name of the song is $clipName
            Generate lyrics that match that song that fit your character! Keep the lyrics short, to one verse and a chorus.
        """.trimIndent()
        return chatFlow("Completed chat with the metalhead") {
            villagerAssistant.metalhead(THE_METALHEAD, newMessage)
        }
    }

    fun ask(q: String): String {
        return villagerAssistant.ask(q)
    }

    private fun chatFlow(
        onCompleteMessage: String,
        streamProvider: () -> TokenStream,
    ): Flow<String> {
        return callbackFlow {
            val stream = streamProvider()
            stream.onNext {
                trySend(it).isSuccess
            }.onComplete {
                Log.info { onCompleteMessage }
                close()
            }.onError { error ->
                Log.error { "${error.message}\n${error.stackTrace}" }
                close(error)
            }.start()

            awaitClose { }
        }
    }
}
