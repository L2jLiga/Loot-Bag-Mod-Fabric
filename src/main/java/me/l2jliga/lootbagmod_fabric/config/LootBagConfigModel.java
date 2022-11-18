package me.l2jliga.lootbagmod_fabric.config;

import io.wispforest.owo.config.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Modmenu(modId = "lootbagmod-fabric")
@Config(name = "lootbagmod", wrapperName = "LootBagConfig")
public class LootBagConfigModel {
    @SectionHeader("misc")
    @RangeConstraint(min = 1, max = 64)
    public Integer StackSize = 15;
    @RangeConstraint(min = 1, max = 1000)
    public Integer Drops = 10;

    @SectionHeader("black-and-white")
    @RestartRequired
    public boolean EnableWhitelist = false;
    @RestartRequired
    public List<String> BlackList = new ArrayList<>(List.of("lootbagmod:lootbag", "minecraft:air", "minecraft:command_block", "minecraft:structure_block", "minecraft:knowledge_book", "minecraft:chain_command_block", "minecraft:repeating_command_block", "minecraft:barrier", "minecraft:enchanted_book", "minecraft:written_book", "minecraft:potion", "minecraft:splash_potion", "minecraft:lingering_potion", "minecraft:spawn_egg", "minecraft:structure_void", "minecraft:mob_spawner", "minecraft:bedrock"));
    @RestartRequired
    public List<String> WhiteList = new ArrayList<>(List.of("minecraft:stone", "minecraft:nether_star"));
    @RestartRequired
    public List<String> Namespaces = new ArrayList<>(List.of("lootbagmod"));
    @RestartRequired
    public boolean EnableContainsList = false;
    @RestartRequired
    public List<String> ContainsList = new ArrayList<>(List.of("apples", "creative"));

    @SectionHeader("chest-drops")
    @RestartRequired
    public boolean ChestDrops = true;
    @RestartRequired
    public List<String> ChestsLists = new ArrayList<>(List.of("minecraft:chests/abandoned_mineshaft", "minecraft:chests/bastion_treasure", "minecraft:chests/desert_pyramid", "minecraft:chests/jungle_temple", "minecraft:chests/pillager_outpost", "minecraft:chests/ruined_portal", "minecraft:chests/shipwreck_treasure", "minecraft:chests/simple_dungeon", "minecraft:chests/stronghold_crossing", "minecraft:chests/stronghold_library", "minecraft:chests/stronghold_corridor", "minecraft:chests/underwater_ruin_big", "minecraft:chests/village/village_toolsmith", "minecraft:chests/village/village_weaponsmith", "repurposed_structures:chests/bastions/underground/treasure", "repurposed_structures:chests/dungeons/badlands", "repurposed_structures:chests/dungeons/dark_forest", "repurposed_structures:chests/dungeons/deep", "repurposed_structures:chests/dungeons/desert", "repurposed_structures:chests/dungeons/icy", "repurposed_structures:chests/dungeons/jungle", "repurposed_structures:chests/dungeons/mushroom", "repurposed_structures:chests/dungeons/nether", "repurposed_structures:chests/dungeons/ocean", "repurposed_structures:chests/dungeons/snow", "repurposed_structures:chests/dungeons/swamp", "repurposed_structures:chests/fortresses/jungle_center", "repurposed_structures:chests/mansions/birch_storage", "repurposed_structures:chests/mansions/desert_storage", "repurposed_structures:chests/mansions/jungle_storage", "repurposed_structures:chests/mansions/mangrove_storage", "repurposed_structures:chests/mansions/oak_storage", "repurposed_structures:chests/mansions/savanna_storage", "repurposed_structures:chests/mansions/snowy_storage", "repurposed_structures:chests/mansions/taiga_storage", "repurposed_structures:chests/mineshafts/birch", "repurposed_structures:chests/mineshafts/crimson", "repurposed_structures:chests/mineshafts/dark_forest", "repurposed_structures:chests/mineshafts/desert", "repurposed_structures:chests/mineshafts/end", "repurposed_structures:chests/mineshafts/icy", "repurposed_structures:chests/mineshafts/jungle", "repurposed_structures:chests/mineshafts/nether", "repurposed_structures:chests/mineshafts/ocean", "repurposed_structures:chests/mineshafts/savanna", "repurposed_structures:chests/mineshafts/stone", "repurposed_structures:chests/mineshafts/swamp", "repurposed_structures:chests/mineshafts/taiga", "repurposed_structures:chests/mineshafts/warped", "repurposed_structures:chests/outposts/badlands", "repurposed_structures:chests/outposts/birch", "repurposed_structures:chests/outposts/crimson", "repurposed_structures:chests/outposts/desert", "repurposed_structures:chests/outposts/giant_tree_taiga", "repurposed_structures:chests/outposts/icy", "repurposed_structures:chests/outposts/jungle", "repurposed_structures:chests/outposts/mangrove", "repurposed_structures:chests/outposts/nether_brick", "repurposed_structures:chests/outposts/oak", "repurposed_structures:chests/outposts/snowy", "repurposed_structures:chests/outposts/taiga", "repurposed_structures:chests/outposts/warped", "repurposed_structures:chests/pyramids/dark_forest", "repurposed_structures:chests/pyramids/end", "repurposed_structures:chests/pyramids/flower_forest", "repurposed_structures:chests/pyramids/giant_tree_taiga", "repurposed_structures:chests/pyramids/icy", "repurposed_structures:chests/pyramids/jungle", "repurposed_structures:chests/pyramids/mushroom", "repurposed_structures:chests/pyramids/snowy", "repurposed_structures:chests/ruined_portals/end/large_portal", "repurposed_structures:chests/ruined_portals/end/small_portal", "repurposed_structures:chests/shipwrecks/crimson/treasure", "repurposed_structures:chests/shipwrecks/end/treasure", "repurposed_structures:chests/shipwrecks/nether_bricks/treasure", "repurposed_structures:chests/shipwrecks/warped/treasure", "repurposed_structures:chests/strongholds/nether_storage_room", "repurposed_structures:chests/villages/crimson_weaponsmith", "repurposed_structures:chests/villages/dark_forest_house", "repurposed_structures:chests/villages/giant_taiga_house", "repurposed_structures:chests/villages/warped_weaponsmith"));

    @SectionHeader("mob-drops")
    @RestartRequired
    public boolean MobDrops = true;
    @RangeConstraint(min = 0, max = 100)
    @RestartRequired
    public Integer DropChance = 10;
    @RestartRequired
    public boolean AllHostileMobs = false;
    @RestartRequired
    public boolean AllNonHostileMobs = false;
    @RestartRequired
    public List<String> MobList = new ArrayList<>(List.of("minecraft:blaze", "minecraft:cave_spider", "minecraft:creeper", "minecraft:drowned", "minecraft:elder_guardian", "minecraft:enderman", "minecraft:endermite", "minecraft:evoker"));
}