package com.glycin.ragvillage.repositories

import com.glycin.ragvillage.model.Personality
import com.glycin.ragvillage.model.Villager
import com.glycin.ragvillage.model.VillagerState
import jakarta.inject.Singleton

@Singleton
class VillagerRepository {

    private val villagers = mutableMapOf<String, Villager>()

    fun getVillager(name: String) = villagers[name]!!

    fun getAllVillagers() = villagers.values.toSet()

    init {
        val npcs = listOf(
            Villager(
                name = "Gorbag",
                job = "Blacksmith",
                age = 45,
                personality = Personality.BRAVE,
                state = VillagerState.IDLE,
                description = """
                    Gorbag, the dedicated blacksmith, takes pride in forging exceptional weapons and armor. 
                    Gorbag enjoys the camaraderie with fellow blacksmiths like Goroth and values their technical exchanges. 
                    However, Gorbag is frustrated by traders like Lugdush, who push for less meticulous work in favor of profit.
                """.trimIndent(),
                actions = mutableListOf()
            ),
            Villager(
                name = "Shagrat",
                job = "Hunter",
                age = 32,
                personality = Personality.ADVENTUROUS,
                state = VillagerState.IDLE,
                description = """
                    Shagrat loves the excitement of the hunt and exploring the wilderness. 
                    This orc enjoys a friendly rivalry with Snaga, often comparing their catches. 
                    Shagrat's adventurous nature sometimes conflicts with the more laid-back villagers like Ogol, who prefer a more sedentary lifestyle.
                """.trimIndent(),
                actions = mutableListOf()
            ),
            Villager(
                name = "Ugluk",
                job = "Warrior",
                age = 50,
                personality = Personality.HONEST,
                state = VillagerState.IDLE,
                description = """
                    Known for honesty and integrity, Ugluk is a reliable warrior and advisor. 
                    This orc has a strong bond with Muzgash, sharing a no-nonsense approach to combat. 
                    Ugluk’s straightforward manner can sometimes clash with the cunning strategies of Ufthak.
                """.trimIndent(),
                actions = mutableListOf(),
            ),
            Villager(
                name = "Mauhúr",
                job = "Shaman",
                age = 60,
                personality = Personality.WISE,
                state = VillagerState.IDLE,
                description = """ 
                    Mauhúr, the wise shaman, enjoys deep, contemplative discussions and sharing ancient knowledge. 
                    Mauhúr respects Lagduf and Azog for their spiritual insights but finds younger orcs like Shagrat to be less appreciative of traditional wisdom.
                """.trimIndent(),
                actions = mutableListOf()
            ),
            Villager(
                name = "Grishnakh",
                job = "Farmer",
                age = 35,
                personality = Personality.KIND,
                state = VillagerState.IDLE,
                description = """
                    Grishnakh finds joy in nurturing the land and is known for a generous and patient nature. 
                    This orc has a harmonious relationship with the younger helpers on the farm but often struggles with the laziness of Ogol, whose lack of enthusiasm impedes progress.
                """.trimIndent(),
                actions = mutableListOf()
            ),
            Villager(
                name = "Snaga",
                job = "Fisherman",
                age = 28,
                personality = Personality.CURIOUS,
                state = VillagerState.IDLE,
                description = """
                    Snaga, the curious fisherman, delights in discovering new fishing techniques and enjoys sharing tips with Shagrat. 
                    Snaga is sometimes annoyed by Gorbagol’s playful antics, which can distract from serious fishing tasks.
                """.trimIndent(),
                actions = mutableListOf()
            ),
            Villager(
                name = "Lugdush",
                job = "Trader",
                age = 40,
                personality = Personality.GREEDY,
                state = VillagerState.IDLE,
                description = """
                 Lugdush is driven by profit and excels in negotiations. 
                 This orc often clashes with villagers like Grishnakh, who view Lugdush’s practices as unfair. 
                 Despite these tensions, Lugdush respects other traders like Throk for their similar drive.
                """.trimIndent(),
                actions = mutableListOf()
            ),
            Villager(
                name = "Gorbul",
                job = "Builder",
                age = 38,
                personality = Personality.HARDWORKING,
                state = VillagerState.IDLE,
                description = """ 
                    Gorbul, a hardworking builder, takes great pride in improving the village infrastructure. 
                    Gorbul appreciates the support of fellow builders like Marzgul but can be frustrated by those who don’t understand the complexities of construction, such as Grishnakh. 
                """.trimIndent(),
                actions = mutableListOf()
            ),
            Villager(
                name = "Bolg",
                job = "Warrior",
                age = 50,
                personality = Personality.WISE,
                state = VillagerState.IDLE,
                description = """
                    Bolg values strategic planning and enjoys discussing tactics with Ugluk and Muzgash. 
                    This orc often finds the more spontaneous actions of Shagrat to be at odds with a carefully thought-out approach.
                """.trimIndent(),
                actions = mutableListOf()
            ),
            Villager(
                name = "Goroth",
                job = "Blacksmith",
                age = 47,
                personality = Personality.FRIENDLY,
                state = VillagerState.IDLE,
                description = """
                    Goroth, the friendly blacksmith, enjoys working alongside Gorbag and fostering a positive work environment. 
                    This orc dislikes the cutthroat attitude of traders like Lugdush and prefers a more supportive and cooperative community.
                """.trimIndent(),
                actions = mutableListOf()
            ),
            Villager(
                name = "Ufthak",
                job = "Hunter",
                age = 30,
                personality = Personality.CUNNING,
                state = VillagerState.IDLE,
                description = """
                    Ufthak thrives on outsmarting opponents and enjoys strategizing. 
                    This orc has a competitive relationship with Shagrat and sometimes finds it challenging to work with the more straightforward Ugluk and Muzgash, who favor direct approaches.
                """.trimIndent(),
                actions = mutableListOf()
            ),
            Villager(
                name = "Azog",
                job = "Shaman",
                age = 75,
                personality = Personality.MYSTERIOUS,
                state = VillagerState.IDLE,
                description = """
                    Azog, the mysterious shaman, revels in exploring ancient orcish magic and sharing cryptic insights with Mauhúr and Lagduf. 
                    Azog is often aloof from practical-minded villagers and prefers to engage with those who appreciate esoteric knowledge.
                """.trimIndent(),
                actions = mutableListOf()
            ),
            Villager(
                name = "Ogol",
                job = "Farmer",
                age = 33,
                personality = Personality.LAZY,
                state = VillagerState.IDLE,
                description = """
                    Ogol prefers a leisurely lifestyle and avoids strenuous work. 
                    This orc’s laid-back attitude often frustrates hardworking villagers like Grishnakh but is appreciated by those who share a slower pace.
                """.trimIndent(),
                actions = mutableListOf()
            ),
            Villager(
                name = "Gorbagol",
                job = "Fisherman",
                age = 25,
                personality = Personality.JOKER,
                state = VillagerState.IDLE,
                description = """
                    Gorbagol, the joker, enjoys making others laugh and playing pranks. 
                    This orc’s playful nature often irritates more serious villagers like Muzgul but brings levity to the village and a friendly rivalry with Snaga over fishing stories.
                """.trimIndent(),
                actions = mutableListOf()
            ),
            Villager(
                name = "Throk",
                job = "Trader",
                age = 39,
                personality = Personality.DECEPTIVE,
                state = VillagerState.IDLE,
                description = """
                    Throk is adept at deceit and enjoys manipulating situations for personal gain. 
                    This orc often clashes with honest villagers like Ugluk and Muzgash, who disapprove of Throk’s underhanded tactics.
                """.trimIndent(),
                actions = mutableListOf()
            ),
            Villager(
                name = "Marzgul",
                job = "Builder",
                age = 44,
                personality = Personality.VINDICTIVE,
                state = VillagerState.IDLE,
                description = """
                    Marzgul, driven by a desire to prove oneself, often seeks validation from other builders. 
                    This orc’s vindictive streak can create tension with peers like Gorbul, who Marzgul feels overshadows their contributions.
                """.trimIndent(),
                actions = mutableListOf()
            ),
            Villager(
                name = "Muzgash",
                job = "Warrior",
                age = 55,
                personality = Personality.HONEST,
                state = VillagerState.IDLE,
                description = """
                    Muzgash values honesty and straightforwardness and has a strong bond with Ugluk.
                    This orc often finds it difficult to relate to more deceitful orcs like Throk, who prioritize cunning over integrity.
                """.trimIndent(),
                actions = mutableListOf()
            ),
            Villager(
                name = "Gashnakh",
                job = "Blacksmith",
                age = 49,
                personality = Personality.HARDWORKING,
                state = VillagerState.IDLE,
                description = """
                    Gashnakh is dedicated to blacksmithing and values the respect of peers. 
                    This orc dislikes the frivolous nature of villagers like Gorbagol and prefers to focus on serious craftsmanship.
                """.trimIndent(),
                actions = mutableListOf()
            ),
            Villager(
                name = "Lagduf",
                job = "Shaman",
                age = 63,
                personality = Personality.WISE,
                state = VillagerState.IDLE,
                description = """
                    Lagduf enjoys sharing deep spiritual knowledge and philosophical discussions with Mauhúr and Azog. 
                    This orc can be frustrated by more practical-minded orcs who don’t fully appreciate the value of traditional wisdom.
                """.trimIndent(),
                actions = mutableListOf()
            ),
            Villager(
                name = "Muzgul",
                job = "Guard",
                age = 52,
                personality = Personality.GRUMPY,
                state = VillagerState.IDLE,
                description = """
                    Muzgul, the grumpy guard, is known for a strict sense of duty and a preference for solitude. 
                    This orc is often irritated by the lighthearted antics of villagers like Gorbagol but remains reliable and committed to the role.
                """.trimIndent(),
                actions = mutableListOf()
            ),
            Villager(
                name = "Lugdak",
                job = "Cook",
                age = 27,
                personality = Personality.SHY,
                state = VillagerState.IDLE,
                description = """
                    Lugdak, the shy cook, enjoys the peace and quiet of cooking duties. 
                    This orc avoids the chaos of village politics and dislikes the loud and aggressive nature of some villagers, preferring a more tranquil environment.
                """.trimIndent(),
                actions = mutableListOf()
            ),
            Villager(
                name = "Grishnákh",
                job = "Assassin",
                age = 45,
                personality = Personality.CURIOUS,
                state = VillagerState.IDLE,
                description = """
                    Grishnákh is driven by curiosity and enjoys gathering information. 
                    This orc’s inquisitive nature often puts them at odds with more straightforward villagers like Ugluk, who value transparency.
                """.trimIndent(),
                actions = mutableListOf()
            ),
            Villager(
                name = "Morgul",
                job = "Spy",
                age = 38,
                personality = Personality.MANIPULATIVE,
                state = VillagerState.IDLE,
                description = """
                    Morgul, the manipulative spy, enjoys influencing situations from the shadows. 
                    This orc often creates tension with straightforward villagers like Ugluk and Grishnakh, who find Morgul’s manipulations unsettling.
                """.trimIndent(),
                actions = mutableListOf()
            )
        )

        npcs.forEach {
            villagers.putIfAbsent(it.name, it)
        }
    }
}
