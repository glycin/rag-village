package com.glycin.ragvillage.model

data class Villager(
    val name: String,
    val job: String,
    val age: Int,
    val personality: Personality,
    var state: VillagerState,
    val description: String,
    val actions: MutableList<VillagerAction>,
){
    //TODO: I would like to propose we add a new override to the `Object`, a toPrompt()
    fun toPrompt(): String {
        return """
            Villager with name $name is $age years old and is working as a $job. Their personality is $personality.
            $description
            They are currently $state. ${if (actions.isNotEmpty()) "The latest action they have taken is: ${actions.last().action}" else ""}
        """.trimIndent()
    }
}
enum class VillagerState {
    IDLE,
    WALKING,
    TALKING,
}

enum class Personality {
    FRIENDLY,
    GRUMPY,
    SHY,
    BRAVE,
    CURIOUS,
    WISE,
    JOKER,
    HARDWORKING,
    LAZY,
    KIND,
    MYSTERIOUS,
    GREEDY,
    HONEST,
    DECEPTIVE,
    ADVENTUROUS,
    CRUEL, // The LLM's dont like this personality :(
    VINDICTIVE,
    MANIPULATIVE,
    MALICIOUS, // The LLM's dont like this personality :(
    CUNNING
}
