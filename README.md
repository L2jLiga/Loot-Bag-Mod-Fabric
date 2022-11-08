# Loot Bag Mod

Fabric version of the [original mod](https://github.com/CodersDownUnder/Loot-Bag-Mod).

## Description

This mod adds 1 item a bag that when held aloft and right-clicked will give you 10 random items from a list (which is shuffled every time a loot bag is used) of every item in the game (including mods) and is then destroyed).
Almost all mods should work with the Loot Bag Mod, though some items such as those with nbt/metadata/variants wont work as the mod has no way of knowing all the possible variants or combinations of metadata and nbt.

The Loot Bag isn't craftable and can only be found in treasure chests.

*Configuration options*:


| Option             | Description                                                                                      | Default value    |
|--------------------|--------------------------------------------------------------------------------------------------|------------------|
| StackSize          | Maximum possible stack size for dropped items                                                    | 15               |
| Drops              | How many items drop when a loot bag is used                                                      | 10               |
| EnableWhitelist    | Use the whitelist                                                                                | false            |
| BlackList          | Items that should never be dropped from loot bag                                                 | List of items    |
| WhiteList          | When whitelist enabled then only items from this list can be dropped                             | List of items    |
| Namespaces         | Mod IDs (namespaces) to include/exclude from drop list, depends on EnableWhitelist flag          | lootbagmod       |
| EnableContainsList | Enable Contains List                                                                             | false            |
| ContainsList       | List of words. Uses to include/exclude items from/to drop list if their identifier contains word | apples, creative |
| ChestDrops         | Enable Loot Bags spawns in treasure chests                                                       | true             |
| ChestsLists        | List of chests which can contain loot bag                                                        | ...              |
| MobDrops           | Enable Loot Bags drops from killed mobs                                                          | true             |
| DropChance         | Enable Loot Bags spawns in treasure chests                                                       | true             |
| AllHostileMobs     | Make Loot Bags droppable from all hostile Mobs                                                   | false            |
| AllNonHostileMobs  | Make Loot Bags droppable from all passive and neutral mobs                                       | false            |
| MobList            | List of mobs who drops Loot Bag                                                                  | ...              |
