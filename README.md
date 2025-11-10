# MobChampions [![Project](http://cf.way2muchnoise.eu/full_mobchampions_downloads.svg)](https://www.curseforge.com/minecraft/mc-mods/mobchampions) [![Project](https://modrinth.roughness.technology/full_mobchampions_downloads.svg)](https://modrinth.com/mod/mobchampions)
[![](https://modrinth.roughness.technology/versions/mobchampions.svg)](https://modrinth.com/mod/mobchampions/versions)
[![](https://img.shields.io/badge/NeoForge-21.1+-orange.svg?longCache=true&style=flat)](https://www.curseforge.com/minecraft/mc-mods/mobchampions/files?gameVersionTypeId=6)
[![](https://img.shields.io/badge/Fabric-0.109.0+-yellowgreen.svg?longCache=true&style=flat)](https://www.curseforge.com/minecraft/mc-mods/mobchampions/files?gameVersionTypeId=4)
![MIT](https://img.shields.io/badge/license-MIT-blue.svg?longCache=true&style=flat)

Introduces elite champion mobs to Minecraft, while leveraging vanilla game
mechanics, providing a highly configurable and challenging experience. Notable
features include configurable champion levels, enhanced
stats, effects and rare loot drops. The goal is to create a challenging and rewarding
experience for players seeking more excitement in their Minecraft adventures.

Highly configurable alternative to Champions / Mob Captains / Infernal Mobs
mods for modern versions of Minecraft.

## Features
 - Any whitelisted mob type can spawn as a Champion mob with enhanced stats and effects.
   - All enhanced stats and effects are configurable via configuration.
   - Different levels of champions can spawn, with higher levels having better stats and more effects.
 - Champions can drop rare loot and unobtainable items.
 - Supports both NeoForge and Fabric mod loaders.
 
## Mob Champions
Mob champions are special elite versions of regular mobs that spawn with armor, weapons, enhanced stats and effects. They can be configured to spawn at different levels, with higher-level champions being more powerful. Champion mobs can drop rare loot and unobtainable items upon defeat.

There are four different ranks of Mob Champions:
- **Uncommon**
- **Rare**
- **Epic**
- **Legendary**

### Spawning
 - Spawn chance weight of each mob champion rank can be configured.
 - Spawning can be limited to specific spawn types (e.g., natural spawns, spawner spawns, etc.).
   - By configuration default, spawner spawns are excluded to not clog mob grinders.
 
Relative weights determine the chance of each rank spawning. Default weights
are set so there is a 69% chance for common mobs, 17.2% chance for uncommon
champions, 10.3% chance for rare champions, 3.1% chance for epic champions, and
0.7% chance for legendary champions.

### Champion Armor and Weapons
Champion mobs can spawn with standard or special armor and weapons based on their level.
- Can be changed via configuration.
- Can be configured to drop on defeat.
  - Standard armor and weapons do not drop by default. (configurable)
  - Special armor and weapons have a chance to drop on defeat. (configurable)

### Mob Champion Stats
- **Increased Health**: Champion mobs have increased health based on their level.
- **Increased Armor**: Champion mobs have increased armor based on their level.
- **Speed Boost**: Champion mobs move faster than regular mobs.
- **Increased Damage**: Champion mobs deal more damage than their regular counterparts.
- **Knockback Resistance**: Champion mobs can regenerate health over time.
- **Fall Resistance**: Champion mobs have significantly reduced fall damage.

### Mob Champion Effects
- By default, **Legendary** champions spawn with glowing effect for a few seconds, and are announced to nearby players.
- Trial chamber effects can be applied to champion mobs. Chance and rank affected by configuration.
  - **Wind Charged**
  - **Weaving**
  - **Infested** 
- **Fire Resistance**: Champion mobs are immune to fire damage.

### Mob Champion Loot
- Armor
  - Some pieces may have special enchantments or attributes.
  - Each rank has a custom set of armor that can be equipped by the champion mobs, or dropped on defeat.
- Weapons
  - Special weapons with unique enchantments or attributes.
- Rare Items

## Links of Interest

+ [MobChampions Curseforge Page](https://www.curseforge.com/minecraft/mc-mods/mobchampions)
+ [MobChampions Modrinth Page](https://modrinth.com/project/mobchampions)
