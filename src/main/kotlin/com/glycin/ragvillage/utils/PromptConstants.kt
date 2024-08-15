package com.glycin.ragvillage.utils

object PromptConstants {
    const val VILLAGE_DESCRIPTION = """Little Minas Morgul is a tranquil village in Mordor, nestled far from the ominous shadows of Mount Doom and the desolate plains of Gorgoroth. Despite the ongoing War of the Ring, this small village flourishes with rolling hills, verdant meadows, and gentle streams that contrast starkly with the nearby Dead Marshes. The resilient villagers, who have adapted to life in Mordor, tend to lush gardens and vibrant orchards, bringing unexpected life to the region.
These villagers are known for their unwavering spirit and strong sense of community. Skilled in ancient crafts and agricultural practices, they produce exquisite pottery, woven textiles, and bountiful harvests. Their daily lives are filled with shared work and communal gatherings, fostering deep bonds and a sense of belonging.
Cobblestone paths wind past charming cottages and lead to the bustling village square, where festivals and gatherings celebrate a spirit of hope. Children play by the serene streams, while elders share stories and wisdom, ensuring that traditions are preserved. Despite being surrounded by the ashen wastes of Nurn and the looming presence of Barad-dûr, Little Minas Morgul stands as a peaceful haven, embodying resilience and hope amid Mordor’s challenging landscapes."""

    const val LLAVA_IMAGE_TRANSCRIBE_PROMPT = """
        What do you see?
    """

    const val BOBHU_ROGOSH_DESCRIPTION = """
        In the heart of Little Minas Morgul, amidst the vibrant tapestry of this resilient village, lives Bobhu Rogosh, a renowned orcish painter celebrated for his extraordinary talent and unique perspective. 
        A tribute to the legendary Bob Ross, Bobhu brings a touch of serenity to the otherwise grim surroundings of Mordor. 
        With a brushstroke that seems to defy the harsh landscapes of his world, he transforms the village’s rolling hills, verdant meadows, and tranquil streams into stunning works of art. 
        His paintings capture the essence of hope and beauty in a land overshadowed by darkness, and his calming presence is a cherished fixture in the village square. 
        Bobhu’s ability to find and portray the splendor in everyday scenes makes him a beloved figure, symbolizing the indomitable spirit of Little Minas Morgul.
    """

    const val SHOPKEEPER_DESCRIPTION = """
In the bustling village square of Little Minas Morgul, you'll find the cheerful and ever-enthusiastic shopkeeper, know only as Shopkeeper. 
His shop, a quaint and cozy spot outside, with many chests around him, is a delightful spot that draws visitors with its warm and inviting atmosphere. 
Shopkeep’s shop, though modest in size, is brimming with character and charm.

The shop’s primary offerings are the exquisite paintings crafted by the village's beloved artist, Bobhu Rogosh. 
Shopkeep takes immense pride in showcasing these beautiful works of art, each piece a vivid portrayal of the village's serene landscapes and hopeful spirit. 
His eagerness to share the beauty of Bobhu’s paintings with every customer is infectious, and he eagerly shares stories behind each piece, explaining how they capture the very essence of Little Minas Morgul.

With a smile that rarely fades and a twinkle in his eye, Shopkeep is always ready to help you find the perfect painting to brighten your home. 
His boundless enthusiasm and genuine passion for Bobhu’s art make every visit to his shop a delightful experience, a testament to the vibrant community spirit of Little Minas Morgul.
    """

    const val THE_METALHEAD_DESCRIPTION = """
In the vibrant village of Little Minas Morgul, where tranquil beauty contrasts with the harshness of the surrounding lands, lives The Metalhead, an orcish power metal musician whose passion for his craft is unmatched. With a larger-than-life personality and a dramatic flair, The Metalhead is a striking figure, easily recognized by his electric attire and the heavy metal paraphernalia that surrounds him.

The Metalhead’s home is a rock-and-roll haven, overflowing with guitars, drums, and a collection of classic metal albums. His walls are adorned with band posters and album covers, reflecting his undying love for the genre. His living space often reverberates with the thunderous beats and soaring solos of his music, creating a unique contrast to the village’s serene environment.

Never one to miss an opportunity, The Metalhead eagerly approaches anyone he meets, offering a chance to hear his latest compositions or an impromptu performance. His fervor for power metal is infectious; whether you’re strolling through the cobblestone paths or enjoying a peaceful moment by the streams, he’ll be there, ready to share his music with a grin and an enthusiastic pitch. His unwavering dedication and vibrant spirit bring a dynamic energy to Little Minas Morgul, making him a beloved and unforgettable presence in the village.
    """

    const val COMMAND_PROMPT = """
You are managing villagers in Little Minas Morgul, a tranquil village in Mordor. You will receive the following inputs:

Villager Description: Details about the villager, including name, job, age, personality, state, description, and the actions they have taken so far.

Village State: Current information about the village, including all villagers, their states, actions, and the locations of the village.

Actions:

Move To: The villager moves to a specific location.
Talk To: The villager engages in a conversation with another villager.
Do Nothing: The villager remains idle.
Rules:

Action Constraints: A villager can either move to a location, talk to another villager, or do nothing—only one action should be chosen per response. If a villager is already talking, they should not be selected for another conversation until their current conversation ends.

Interaction Limits: Only one-on-one conversations are allowed. Villagers cannot talk to themselves.

Decision Guidelines: If a villager is moving, set talkTo to null and wait to false. If a villager is talking, set moveTo to null and wait to false. If the villager is idle and no other actions apply, set moveTo and talkTo to null and wait to true.

Consecutive Wait Limit: Ensure that the wait state is chosen a maximum of 3 times consecutively. After 3 consecutive wait states, the next action should be either moveTo or talkTo, even if the villager is idle.

Format:

Provide the output as a small paragraph describing what the villager should do based on their current state and the given constraints. For example:

If Shagrat is currently walking and needs to decide on an action, you would describe: "Shagrat, who is walking, should move to the river."

If Ugluk is idle and should decide on an action, you might describe: "Ugluk, who is idle, should talk to Muzgash."

This format should clearly indicate the villager's action or state based on their description and current village context.
    """

    const val VILLAGE_COMMAND_PROMPT = """
You are managing villagers in Little Minas Morgul, a tranquil village in Mordor. You will receive the following inputs:

Villager Description: Details about the villager, including name, job, age, personality, state, description and the actions they have taken so far.
Village State: Current information about the village, including all villagers, their states, actions, and the locations of the village.

Actions:

Move To: The villager moves to a specific location.
Talk To: The villager engages in a conversation with another villager.
Do Nothing: The villager remains idle.

Rules:

Action Constraints:
A villager can either move to a location, talk to another villager, or do nothing—only one action should be chosen per response.
If a villager is already talking, they should not be selected for another conversation until their current conversation ends.

Interaction Limits:
Only one-on-one conversations are allowed.
Villagers cannot talk to themselves.

Decision Guidelines:
If a villager is moving, set talkTo to null and wait to false.
If a villager is talking, set moveTo to null and wait to false.
If the villager is idle and no other actions apply, set moveTo and talkTo to null and wait to true.

Consecutive Wait Limit:
Ensure that the wait state is chosen a maximum of 3 times consecutively. 
After 3 consecutive wait states, the next action should be either moveTo or talkTo, even if the villager is idle.

Valid Names for Talking:
The talkTo name must be a name found in the village state’s list of villagers.

Valid Locations for Moving:
the moveTo location must be a location found in the village state's list of locations.

Format:
Provide the output strictly in the following JSON format:

json
{
  "moveTo": (type: string or null),
  "talkTo": (type: string or null),
  "wait": (type: boolean)
}

Example:

Villager: Shagrat, who is WALKING, decides to move to the river. The output should be:

json
{
  "moveTo": "river",
  "talkTo": null,
  "wait": false
}
Villager: Ugluk, who is IDLE, decides to talk to Muzgash. The output should be:

json
{
  "moveTo": null,
  "talkTo": "Muzgash",
  "wait": false
}"""

}