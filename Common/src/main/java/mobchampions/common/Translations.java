package mobchampions.common;

import java.util.Arrays;
import java.util.Map;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;

import net.minecraft.world.entity.MobSpawnType;
import org.slf4j.helpers.MessageFormatter;

public class Translations {

    private static final Joiner LINE_JOINER = Joiner.on("\n");
    private static final Map<String, String> translations = Maps.newHashMap();

    static {
        translations.put("visuals.title", "Rendering Settings");
        translations.put("visuals", "All settings related to the rendering of fireworks when a creeper explodes.");
        translations.put("uncommonchampioncolor.title", "Uncommon Champion Color");
        translations.put("uncommonchampioncolor", "Color used for uncommon champions.");
        translations.put("rarechampioncolor.title", "Rare Champion Color");
        translations.put("rarechampioncolor", "Color used for rare champions.");
        translations.put("epicchampioncolor.title", "Epic Champion Color");
        translations.put("epicchampioncolor", "Color used for epic champions.");
        translations.put("legendarychampioncolor.title", "Legendary Champion Color");
        translations.put("legendarychampioncolor", "Color used for legendary champions.");
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
        translations.put("spawning.title", "Champion Spawning Settings");
        translations.put("spawning", joiner(
            "Relative weights determine the chance of each rank spawning.",
            "Default weights are set so there is a 50% chance for common mobs,",
            "27.5% chance for uncommon champions, 16.5% chance for rare champions,",
            "5% chance for epic champions, and 1% chance for legendary champions."
        ));
        translations.put("commonmobweight.title", "Common Mob Weight");
        translations.put("commonmobweight", "Relative weight for a normal or common mob to spawn.");
        translations.put("uncommonchampionweight.title", "Uncommon Champion Weight");
        translations.put("uncommonchampionweight", "Relative weight for uncommon champions to spawn.");
        translations.put("rarechampionweight.title", "Rare Champion Weight");
        translations.put("rarechampionweight", "Relative weight for rare champions to spawn.");
        translations.put("epicchampionweight.title", "Epic Champion Weight");
        translations.put("epicchampionweight", "Relative weight for epic champions to spawn.");
        translations.put("legendarychampionweight.title", "Legendary Champion Weight");
        translations.put("legendarychampionweight", "Relative weight for legendary champions to spawn.");
        translations.put("championwhitelist.title", "Champion Entity Type Whitelist");
        translations.put("championwhitelist", joiner(
            "Whitelist of entities that can spawn as champions.",
            "If the whitelist is empty, the mod is effectively disabled."
        ));
        translations.put("spawntypeblacklist.title", "Champion Spawn Type Blacklist");
        translations.put("spawntypeblacklist", joiner(
            "Blacklist of spawn types that prevent champions from spawning.",
            "Spawn types must be one of: " + Arrays.toString(Arrays.stream(MobSpawnType.values()).map(Enum::name).toArray())
        ));
        translations.put("stats.title", "Champion Stats Settings");
        translations.put("stats", joiner(
            "Settings that modify the stats and abilities of champions based on their rank.",
            "All settings in this section require a restart to take effect.",
            "Changes do not apply to already spawned champions."
        ));
        translations.put("uncommonchampionhealthmultiplier.title", "Uncommon Champion Health Multiplier");
        translations.put("uncommonchampionhealthmultiplier", "Health multiplier applied to uncommon champions.");
        translations.put("uncommonchampionarmoraddition.title", "Uncommon Champion Armor Addition");
        translations.put("uncommonchampionarmoraddition", "Armor addition applied to uncommon champions.");
        translations.put("uncommonchampionmovementspeedmultiplier.title", "Uncommon Champion Movement Speed Multiplier");
        translations.put("uncommonchampionmovementspeedmultiplier", "Movement speed multiplier applied to uncommon champions.");
        translations.put("uncommonchampionattackdamagemultiplier.title", "Uncommon Champion Attack Damage Multiplier");
        translations.put("uncommonchampionattackdamagemultiplier", "Attack damage multiplier applied to uncommon champions.");
        translations.put("uncommonchampionknockbackresistanceaddition.title", "Uncommon Champion Knockback Resistance Addition");
        translations.put("uncommonchampionknockbackresistanceaddition", "Knockback resistance addition applied to uncommon champions.");
        translations.put("rarechampionhealthmultiplier.title", "Rare Champion Health Multiplier");
        translations.put("rarechampionhealthmultiplier", "Health multiplier applied to rare champions.");
        translations.put("rarechampionarmoraddition.title", "Rare Champion Armor Addition");
        translations.put("rarechampionarmoraddition", "Armor addition applied to rare champions.");
        translations.put("rarechampionmovementspeedmultiplier.title", "Rare Champion Movement Speed Multiplier");
        translations.put("rarechampionmovementspeedmultiplier", "Movement speed multiplier applied to rare champions.");
        translations.put("rarechampionattackdamagemultiplier.title", "Rare Champion Attack Damage Multiplier");
        translations.put("rarechampionattackdamagemultiplier", "Attack damage multiplier applied to rare champions.");
        translations.put("rarechampionknockbackresistanceaddition.title", "Rare Champion Knockback Resistance Addition");
        translations.put("rarechampionknockbackresistanceaddition", "Knockback resistance addition applied to rare champions.");
        translations.put("epicchampionhealthmultiplier.title", "Epic Champion Health Multiplier");
        translations.put("epicchampionhealthmultiplier", "Health multiplier applied to epic champions.");
        translations.put("epicchampionarmoraddition.title", "Epic Champion Armor Addition");
        translations.put("epicchampionarmoraddition", "Armor addition applied to epic champions.");
        translations.put("epicchampionmovementspeedmultiplier.title", "Epic Champion Movement Speed Multiplier");
        translations.put("epicchampionmovementspeedmultiplier", "Movement speed multiplier applied to epic champions.");
        translations.put("epicchampionattackdamagemultiplier.title", "Epic Champion Attack Damage Multiplier");
        translations.put("epicchampionattackdamagemultiplier", "Attack damage multiplier applied to epic champions.");
        translations.put("epicchampionknockbackresistanceaddition.title", "Epic Champion Knockback Resistance Addition");
        translations.put("epicchampionknockbackresistanceaddition", "Knockback resistance addition applied to epic champions.");
        translations.put("legendarychampionhealthmultiplier.title", "Legendary Champion Health Multiplier");
        translations.put("legendarychampionhealthmultiplier", "Health multiplier applied to legendary champions.");
        translations.put("legendarychampionarmoraddition.title", "Legendary Champion Armor Addition");
        translations.put("legendarychampionarmoraddition", "Armor addition applied to legendary champions.");
        translations.put("legendarychampionmovementspeedmultiplier.title", "Legendary Champion Movement Speed Multiplier");
        translations.put("legendarychampionmovementspeedmultiplier", "Movement speed multiplier applied to legendary champions.");
        translations.put("legendarychampionattackdamagemultiplier.title", "Legendary Champion Attack Damage Multiplier");
        translations.put("legendarychampionattackdamagemultiplier", "Attack damage multiplier applied to legendary champions.");
        translations.put("legendarychampionknockbackresistanceaddition.title", "Legendary Champion Knockback Resistance Addition");
        translations.put("legendarychampionknockbackresistanceaddition", "Knockback resistance addition applied to legendary champions.");
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

}
