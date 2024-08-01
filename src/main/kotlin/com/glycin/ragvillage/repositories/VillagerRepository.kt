package com.glycin.ragvillage.repositories

import com.glycin.ragvillage.model.Personality
import com.glycin.ragvillage.model.Villager
import com.glycin.ragvillage.model.VillagerState
import org.springframework.stereotype.Repository
import java.time.LocalTime

@Repository
class VillagerRepository {

    private val villagers = mutableMapOf<String, Villager>()

    fun getVillager(name: String) = villagers[name]!!

    fun getAllVillagerNames() = villagers.keys.toSet()

    init {
        val npcs = listOf(
            Villager(
                name = "Gorbag",
                job = "Blacksmith",
                startWorkTime = "09:00",
                endWorkTime = "17:00",
                age = 45,
                personality = Personality.BRAVE,
                state = VillagerState.WORKING
            ),
            Villager(
                name = "Shagrat",
                job = "Hunter",
                startWorkTime = "06:00",
                endWorkTime = "14:00",
                age = 32,
                personality = Personality.ADVENTUROUS,
                state = VillagerState.WALKING
            ),
            Villager(
                name = "Ugluk",
                job = "Warrior",
                startWorkTime = "10:00",
                endWorkTime = "18:00",
                age = 50,
                personality = Personality.CRUEL,
                state = VillagerState.IDLE
            ),
            Villager(
                name = "Mauhúr",
                job = "Shaman",
                startWorkTime = "08:00",
                endWorkTime = "16:00",
                age = 60,
                personality = Personality.WISE,
                state = VillagerState.TALKING
            ),
            Villager(
                name = "Grishnakh",
                job = "Farmer",
                startWorkTime = "05:00",
                endWorkTime = "13:00",
                age = 35,
                personality = Personality.KIND,
                state = VillagerState.WORKING
            ),
            Villager(
                name = "Snaga",
                job = "Fisherman",
                startWorkTime = "06:00",
                endWorkTime = "14:00",
                age = 28,
                personality = Personality.CURIOUS,
                state = VillagerState.WORKING
            ),
            Villager(
                name = "Lugdush",
                job = "Trader",
                startWorkTime = "09:00",
                endWorkTime = "17:00",
                age = 40,
                personality = Personality.GREEDY,
                state = VillagerState.TALKING
            ),
            Villager(
                name = "Gorbul",
                job = "Builder",
                startWorkTime = "07:00",
                endWorkTime = "15:00",
                age = 38,
                personality = Personality.HARDWORKING,
                state = VillagerState.WORKING
            ),
            Villager(
                name = "Bolg",
                job = "Warrior",
                startWorkTime = "10:00",
                endWorkTime = "18:00",
                age = 50,
                personality = Personality.CRUEL,
                state = VillagerState.IDLE
            ),
            Villager(
                name = "Goroth",
                job = "Blacksmith",
                startWorkTime = "09:00",
                endWorkTime = "17:00",
                age = 47,
                personality = Personality.FRIENDLY,
                state = VillagerState.WORKING
            ),
            Villager(
                name = "Ufthak",
                job = "Hunter",
                startWorkTime = "06:00",
                endWorkTime = "14:00",
                age = 30,
                personality = Personality.CUNNING,
                state = VillagerState.WALKING
            ),
            Villager(
                name = "Azog",
                job = "Shaman",
                startWorkTime = "08:00",
                endWorkTime = "16:00",
                age = 75,
                personality = Personality.MYSTERIOUS,
                state = VillagerState.TALKING
            ),
            Villager(
                name = "Ogol",
                job = "Farmer",
                startWorkTime = "05:00",
                endWorkTime = "13:00",
                age = 33,
                personality = Personality.LAZY,
                state = VillagerState.IDLE
            ),
            Villager(
                name = "Gorbagol",
                job = "Fisherman",
                startWorkTime = "06:00",
                endWorkTime = "14:00",
                age = 25,
                personality = Personality.JOKER,
                state = VillagerState.WALKING
            ),
            Villager(
                name = "Throk",
                job = "Trader",
                startWorkTime = "09:00",
                endWorkTime = "17:00",
                age = 39,
                personality = Personality.DECEPTIVE,
                state = VillagerState.TALKING
            ),
            Villager(
                name = "Marzgul",
                job = "Builder",
                startWorkTime = "07:00",
                endWorkTime = "15:00",
                age = 44,
                personality = Personality.VINDICTIVE,
                state = VillagerState.WORKING
            ),
            Villager(
                name = "Muzgash",
                job = "Warrior",
                startWorkTime = "10:00",
                endWorkTime = "18:00",
                age = 55,
                personality = Personality.HONEST,
                state = VillagerState.IDLE
            ),
            Villager(
                name = "Gashnakh",
                job = "Blacksmith",
                startWorkTime = "09:00",
                endWorkTime = "17:00",
                age = 49,
                personality = Personality.HARDWORKING,
                state = VillagerState.WORKING
            ),
            Villager(
                name = "Mauhúr",
                job = "Hunter",
                startWorkTime = "06:00",
                endWorkTime = "14:00",
                age = 37,
                personality = Personality.BRAVE,
                state = VillagerState.WALKING
            ),
            Villager(
                name = "Lagduf",
                job = "Shaman",
                startWorkTime = "08:00",
                endWorkTime = "16:00",
                age = 63,
                personality = Personality.WISE,
                state = VillagerState.TALKING
            ),
            Villager(
                name = "Muzgul",
                job = "Guard",
                startWorkTime = "22:00",
                endWorkTime = "06:00",
                age = 52,
                personality = Personality.GRUMPY,
                state = VillagerState.WALKING
            ),
            Villager(
                name = "Lugdak",
                job = "Cook",
                startWorkTime = "08:00",
                endWorkTime = "16:00",
                age = 27,
                personality = Personality.SHY,
                state = VillagerState.WORKING
            ),
            Villager(
                name = "Grishnákh",
                job = "Assassin",
                startWorkTime = "19:00",
                endWorkTime = "03:00",
                age = 45,
                personality = Personality.MALICIOUS,
                state = VillagerState.IDLE
            ),
            Villager(
                name = "Morgul",
                job = "Spy",
                startWorkTime = "12:00",
                endWorkTime = "20:00",
                age = 38,
                personality = Personality.MANIPULATIVE,
                state = VillagerState.TALKING
            )
        )

        npcs.forEach {
            villagers.putIfAbsent(it.name, it)
        }
    }
}