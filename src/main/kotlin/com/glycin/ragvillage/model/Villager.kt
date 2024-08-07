package com.glycin.ragvillage.model

data class Villager(
    val name: String,
    val job: String,
    val age: Int,
    val personality: Personality,
    var state: VillagerState,
    val description: String,
    val actions: MutableList<VillagerAction>,
)
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
