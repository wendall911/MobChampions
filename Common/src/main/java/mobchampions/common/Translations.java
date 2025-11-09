package mobchampions.common;

import java.util.Arrays;
import java.util.Map;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;

import net.minecraft.world.entity.MobSpawnType;
import org.slf4j.helpers.MessageFormatter;

import mobchampions.MobChampions;
import mobchampions.network.MobChampion.Rank;

public class Translations {

    public static String MOD_NAME_KEY = MobChampions.MODID + ".configuration.title";
    public static String LOOT_KEY_BASE = MobChampions.MODID + ".lootitem.";
    public static String ANNOUNCEMENT_KEY = MobChampions.MODID + ".announcement.";
    public static String TYPE_KEY = MobChampions.MODID + ".type.";

    private static final Joiner LINE_JOINER = Joiner.on("\n");
    private static final Map<String, String> translations = Maps.newHashMap();

    static {
        translations.put("visuals.title", "Rendering Settings");
        translations.put("visuals", "All settings related to the rendering of fireworks when a creeper explodes.");
        translations.put("uncommoncolor.title", "Uncommon Color");
        translations.put("uncommoncolor", "Color used for uncommon champions.");
        translations.put("rarecolor.title", "Rare Color");
        translations.put("rarecolor", "Color used for rare champions.");
        translations.put("epiccolor.title", "Epic Color");
        translations.put("epiccolor", "Color used for epic champions.");
        translations.put("legendarycolor.title", "Legendary Color");
        translations.put("legendarycolor", "Color used for legendary champions.");
        translations.put("fireworkschance.title", "Legendary Fireworks Chance");
        translations.put("fireworkschance", "Chance of fireworks after legendary champion defeat. 0 disables fireworks.");
        translations.put("colors.title", "Legendary Fireworks Colors");
        translations.put("colors", "Colors to use in fireworks. Requires hex formatted colors.");
        translations.put("fireworksflicker.title", "Legendary Fireworks Flicker");
        translations.put("fireworksflicker", "Fireworks flicker setting as it relates to Minecraft fireworks.");
        translations.put("fireworkstrail.title", "Legendary Fireworks Trail");
        translations.put("fireworkstrail", "Fireworks trail setting as it relates to Minecraft fireworks.");
        translations.put("fireworksshape.title", "Legendary Fireworks Shape");
        translations.put("fireworksshape", "Fireworks shape setting as it relates to Minecraft fireworks.");
        translations.put("fireworksheight.title", "Legendary Fireworks Height");
        translations.put("fireworksheight", "Height above champion that fireworks explode.");
        translations.put("mobdeath.title", "Mob Death Settings");
        translations.put("mobdeath", joiner(
            "Settings that modify the behavior of mob deaths when killed by champions.",
            "All settings in this section require a restart to take effect."
        ));
        translations.put("fireworksondeath.title", "Fireworks on Death");
        translations.put("fireworksondeath", "If true, fireworks will spawn when a champion is killed by player.");
        translations.put("fireworksminimumrank.title", "Fireworks Minimum Rank");
        translations.put("fireworksminimumrank", "Minimum champion rank required to trigger fireworks on death.");
        translations.put("spawning.title", "Spawning Settings");
        translations.put("spawning", joiner(
            "Relative weights determine the chance of each rank spawning.",
            "Default weights are set so there is a 69% chance for common mobs,",
            "17.2% chance for uncommon champions, 10.3% chance for rare champions,",
            "3.1% chance for epic champions, and 0.7% chance for legendary champions."
        ));
        translations.put("disablebabychampions.title", "Disable Baby Champions");
        translations.put("disablebabychampions", "If true, baby variants of entities will not spawn as champions.");
        translations.put("enablespawnmessage.title", "Enable Spawn Message");
        translations.put("enablespawnmessage", "If true, a message will be broadcast to players in the configurable range when a champion spawns.");
        translations.put("spawnmessageminimumrank.title", "Spawn Message Minimum Rank");
        translations.put("spawnmessageminimumrank", "Minimum champion rank required to trigger a spawn message.");
        translations.put("spawnmessagerange.title", "Spawn Message Range Check");
        translations.put("spawnmessagerange", "Radius around the champion spawn point to check for players to send the spawn message to.");
        translations.put("commonmobweight.title", "Common Mob Weight");
        translations.put("commonmobweight", "Relative weight for a normal or common mob to spawn.");
        translations.put("uncommonweight.title", "Uncommon Weight");
        translations.put("uncommonweight", "Relative weight for uncommon champions to spawn.");
        translations.put("rareweight.title", "Rare Weight");
        translations.put("rareweight", "Relative weight for rare champions to spawn.");
        translations.put("epicweight.title", "Epic Weight");
        translations.put("epicweight", "Relative weight for epic champions to spawn.");
        translations.put("legendaryweight.title", "Legendary Weight");
        translations.put("legendaryweight", "Relative weight for legendary champions to spawn.");
        translations.put("championwhitelist.title", "Entity Type Whitelist");
        translations.put("championwhitelist", joiner(
            "Whitelist of entities that can spawn as champions.",
            "If the whitelist is empty, the mod is effectively disabled."
        ));
        translations.put("spawntypeblacklist.title", "Spawn Type Blacklist");
        translations.put("spawntypeblacklist", joiner(
            "Blacklist of spawn types that prevent champions from spawning.",
            "Spawn types must be one of: " + Arrays.toString(Arrays.stream(MobSpawnType.values()).map(Enum::name).toArray())
        ));
        translations.put("stats.title", "Stats Settings");
        translations.put("stats", joiner(
            "Settings that modify the stats and abilities of champions based on their rank.",
            "All settings in this section require a restart to take effect.",
            "Changes do not apply to already spawned champions."
        ));
        translations.put("uncommondifficulty.title", "Uncommon Difficulty");
        translations.put("uncommondifficulty", "Difficulty settings level applied to uncommon champions. Higher means stronger champions.");
        translations.put("raredifficulty.title", "Rare Difficulty");
        translations.put("raredifficulty", "Difficulty settings level applied to rare champions. Higher means stronger champions.");
        translations.put("epicdifficulty.title", "Epic Difficulty");
        translations.put("epicdifficulty", "Difficulty settings level applied to epic champions. Higher means stronger champions.");
        translations.put("legendarydifficulty.title", "Legendary Difficulty");
        translations.put("legendarydifficulty", "Difficulty settings level applied to legendary champions. Higher means stronger champions.");
        translations.put("effects.title", "Effects Settings");
        translations.put("effects", joiner(
            "Settings that modify the effects applied to champions based on their rank."
        ));
        translations.put("glowingeffectminimumrank.title", "Glowing Effect Minimum Rank");
        translations.put("glowingeffectminimumrank", "Minimum champion rank required to apply the glowing effect.");
        translations.put("glowingeffectduration.title", "Glowing Effect Duration");
        translations.put("glowingeffectduration", joiner(
            "Duration in seconds for the glowing effect applied to champions.",
            "Set to 0 to disable. Set to -1 for infinite duration."
        ));
        translations.put("infestedeffectminimumrank.title", "Infested Effect Minimum Rank");
        translations.put("infestedeffectminimumrank", "Minimum champion rank required to apply the infested effect.");
        translations.put("infestedeffectchance.title", "Infested Effect Chance");
        translations.put("infestedeffectchance", "Chance for the infested effect to be applied to eligible champions.");
        translations.put("weavingeffectminimumrank.title", "Weaving Effect Minimum Rank");
        translations.put("weavingeffectminimumrank", "Minimum champion rank required to apply the weaving effect.");
        translations.put("weavingeffectchance.title", "Weaving Effect Chance");
        translations.put("weavingeffectchance", "Chance for the weaving effect to be applied to eligible champions.");
        translations.put("windchargedeffectminimumrank.title", "Wind Charged Effect Minimum Rank");
        translations.put("windchargedeffectminimumrank", "Minimum champion rank required to apply the wind charged effect.");
        translations.put("windchargedeffectchance.title", "Wind Charged Effect Chance");
        translations.put("windchargedeffectchance", "Chance for the wind charged effect to be applied to eligible champions.");
        translations.put("legendaryeffectbonusmultiplier.title", "Legendary Effect Bonus Multiplier");
        translations.put("legendaryeffectbonusmultiplier", "Multiplier applied to all effect chances for legendary champions.");
        translations.put("creeperexplosionradiusmultiplier.title", "Creeper Explosion Radius Multiplier");
        translations.put("creeperexplosionradiusmultiplier", joiner(
            "Multiplier applied to the explosion radius of creeper champions by rank.",
            "For example, a value of 0.25 will double the explosion radius of a legendary (rank 4) creeper."
        ));
        translations.put("equipment.title", "Equipment Settings");
        translations.put("equipment", joiner(
            "Settings that modify the items equipped by champions based on their rank."
        ));
        translations.put("uncommonstandardweaponspawnchance.title", "Uncommon Standard Weapons Spawn Chance");
        translations.put("uncommonstandardweaponspawnchance", "Chance for uncommon champions to spawn with standard weapons.");
        translations.put("rarestandardweaponspawnchance.title", "Rare Standard Weapons Spawn Chance");
        translations.put("rarestandardweaponspawnchance", "Chance for rare champions to spawn with standard weapons.");
        translations.put("epicstandardweaponspawnchance.title", "Epic Standard Weapons Spawn Chance");
        translations.put("epicstandardweaponspawnchance", "Chance for epic champions to spawn with standard weapons.");
        translations.put("legendarystandardweaponspawnchance.title", "Legendary Standard Weapons Spawn Chance");
        translations.put("legendarystandardweaponspawnchance", "Chance for legendary champions to spawn with standard weapons.");
        translations.put("uncommonlootableweaponspawnchance.title", "Uncommon Lootable Weapons Spawn Chance");
        translations.put("uncommonlootableweaponspawnchance", "Chance for uncommon champions to spawn with lootable weapons.");
        translations.put("rarelootableweaponspawnchance.title", "Rare Lootable Weapons Spawn Chance");
        translations.put("rarelootableweaponspawnchance", "Chance for rare champions to spawn with lootable weapons.");
        translations.put("epiclootableweaponspawnchance.title", "Epic Lootable Weapons Spawn Chance");
        translations.put("epiclootableweaponspawnchance", "Chance for epic champions to spawn with lootable weapons.");
        translations.put("legendarylootableweaponspawnchance.title", "Legendary Lootable Weapons Spawn Chance");
        translations.put("weaponlist.title", "Weapon List");
        translations.put("weaponlist", joiner(
            "Format: <rarity>-<weight>-<modid:item_name>",
            "Rarity: 1 (Uncommon), 2 (Rare), 3 (Epic), 4 (Legendary)",
            "Weight: Integer value representing the relative chance of selection",
            "Example: 2-50-minecraft:diamond_sword"
        ));
        translations.put("uncommonstandardarmorspawnchance.title", "Uncommon Standard Armor Spawn Chance");
        translations.put("uncommonstandardarmorspawnchance", "Chance for uncommon champions to spawn with standard armor.");
        translations.put("rarestandardarmorspawnchance.title", "Rare Standard Armor Spawn Chance");
        translations.put("rarestandardarmorspawnchance", "Chance for rare champions to spawn with standard armor.");
        translations.put("epicstandardarmorspawnchance.title", "Epic Standard Armor Spawn Chance");
        translations.put("epicstandardarmorspawnchance", "Chance for epic champions to spawn with standard armor.");
        translations.put("legendarystandardarmorspawnchance.title", "Legendary Standard Armor Spawn Chance");
        translations.put("legendarystandardarmorspawnchance", "Chance for legendary champions to spawn with standard armor.");
        translations.put("uncommonlootablearmorspawnchance.title", "Uncommon Lootable Armor Spawn Chance");
        translations.put("uncommonlootablearmorspawnchance", "Chance for uncommon champions to spawn with upgraded loot table armor.");
        translations.put("rarelootablearmorspawnchance.title", "Rare Lootable Armor Spawn Chance");
        translations.put("rarelootablearmorspawnchance", "Chance for rare champions to spawn with upgraded loot table armor.");
        translations.put("epiclootablearmorspawnchance.title", "Epic Lootable Armor Spawn Chance");
        translations.put("epiclootablearmorspawnchance", "Chance for epic champions to spawn with upgraded loot table armor.");
        translations.put("legendarylootablearmorspawnchance.title", "Legendary Lootable Armor Spawn Chance");
        translations.put("legendarylootablearmorspawnchance", "Chance for legendary champions to spawn with upgraded loot table armor.");
        translations.put("armorlist.title", "Armor List");
        translations.put("armorlist", joiner(
            "Format: <rarity>-<weight>-<modid:item_name>",
            "Rarity: 1 (Uncommon), 2 (Rare), 3 (Epic), 4 (Legendary)",
            "Weight: Integer value representing the relative chance of selection",
            "Example: 3-30-minecraft:diamond_chestplate"
        ));
        translations.put("experience.title", "Experience Settings");
        translations.put("experience", joiner(
            "Settings that modify the experience dropped by champions based on their rank."
        ));
        translations.put("uncommonexperiencemultiplier.title", "Uncommon Experience Multiplier");
        translations.put("uncommonexperiencemultiplier", "Experience multiplier applied to uncommon champions.");
        translations.put("rareexperiencemultiplier.title", "Rare Experience Multiplier");
        translations.put("rareexperiencemultiplier", "Experience multiplier applied to rare champions.");
        translations.put("epicexperiencemultiplier.title", "Epic Experience Multiplier");
        translations.put("epicexperiencemultiplier", "Experience multiplier applied to epic champions.");
        translations.put("legendaryexperiencemultiplier.title", "Legendary Experience Multiplier");
        translations.put("legendaryexperiencemultiplier", "Experience multiplier applied to legendary champions.");
        translations.put("lootdrops.title", "Loot Drop Settings");
        translations.put("lootdrops", joiner(
            "Settings that modify the loot dropped by champions."
        ));
        translations.put("standardarmordropchance.title", "Standard Armor Drop Chance");
        translations.put("standardarmordropchance", "Chance for champions wearing standard armor to drop it on death.");
        translations.put("standardweapondropchance.title", "Standard Weapon Drop Chance");
        translations.put("standardweapondropchance", "Chance for champions wielding standard weapons to drop them on death.");
    }

    public static String get(String key) {
        return translations.getOrDefault(key, key);
    }

    public static String get(String key, String... values) {
        return MessageFormatter.arrayFormat(translations.getOrDefault(key, key), values).getMessage();
    }

    private static String joiner(String... string) {
        return LINE_JOINER.join(string);
    }

    public static String getTitleByRank(Rank rank) {
        return TYPE_KEY + rank.name().toLowerCase();
    }

}
