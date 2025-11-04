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
        translations.put("uncommoncolor.title", "Uncommon Champion Color");
        translations.put("uncommoncolor", "Color used for uncommon champions.");
        translations.put("rarecolor.title", "Rare Champion Color");
        translations.put("rarecolor", "Color used for rare champions.");
        translations.put("epiccolor.title", "Epic Champion Color");
        translations.put("epiccolor", "Color used for epic champions.");
        translations.put("legendarycolor.title", "Legendary Champion Color");
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
        translations.put("spawning.title", "Champion Spawning Settings");
        translations.put("spawning", joiner(
            "Relative weights determine the chance of each rank spawning.",
            "Default weights are set so there is a 50% chance for common mobs,",
            "27.5% chance for uncommon champions, 16.5% chance for rare champions,",
            "5% chance for epic champions, and 1% chance for legendary champions."
        ));
        translations.put("commonmobweight.title", "Common Mob Weight");
        translations.put("commonmobweight", "Relative weight for a normal or common mob to spawn.");
        translations.put("uncommonweight.title", "Uncommon Champion Weight");
        translations.put("uncommonweight", "Relative weight for uncommon champions to spawn.");
        translations.put("rareweight.title", "Rare Champion Weight");
        translations.put("rareweight", "Relative weight for rare champions to spawn.");
        translations.put("epicweight.title", "Epic Champion Weight");
        translations.put("epicweight", "Relative weight for epic champions to spawn.");
        translations.put("legendaryweight.title", "Legendary Champion Weight");
        translations.put("legendaryweight", "Relative weight for legendary champions to spawn.");
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
        translations.put("uncommonhealthmultiplier.title", "Uncommon Champion Health Multiplier");
        translations.put("uncommonhealthmultiplier", "Health multiplier applied to uncommon champions.");
        translations.put("uncommonarmoraddition.title", "Uncommon Champion Armor Addition");
        translations.put("uncommonarmoraddition", "Armor addition applied to uncommon champions.");
        translations.put("uncommonmovementspeedmultiplier.title", "Uncommon Champion Movement Speed Multiplier");
        translations.put("uncommonmovementspeedmultiplier", "Movement speed multiplier applied to uncommon champions.");
        translations.put("uncommonattackdamagemultiplier.title", "Uncommon Champion Attack Damage Multiplier");
        translations.put("uncommonattackdamagemultiplier", "Attack damage multiplier applied to uncommon champions.");
        translations.put("uncommonknockbackresistanceaddition.title", "Uncommon Champion Knockback Resistance Addition");
        translations.put("uncommonknockbackresistanceaddition", "Knockback resistance addition applied to uncommon champions.");
        translations.put("rarehealthmultiplier.title", "Rare Champion Health Multiplier");
        translations.put("rarehealthmultiplier", "Health multiplier applied to rare champions.");
        translations.put("rarearmoraddition.title", "Rare Champion Armor Addition");
        translations.put("rarearmoraddition", "Armor addition applied to rare champions.");
        translations.put("raremovementspeedmultiplier.title", "Rare Champion Movement Speed Multiplier");
        translations.put("raremovementspeedmultiplier", "Movement speed multiplier applied to rare champions.");
        translations.put("rareattackdamagemultiplier.title", "Rare Champion Attack Damage Multiplier");
        translations.put("rareattackdamagemultiplier", "Attack damage multiplier applied to rare champions.");
        translations.put("rareknockbackresistanceaddition.title", "Rare Champion Knockback Resistance Addition");
        translations.put("rareknockbackresistanceaddition", "Knockback resistance addition applied to rare champions.");
        translations.put("epichealthmultiplier.title", "Epic Champion Health Multiplier");
        translations.put("epichealthmultiplier", "Health multiplier applied to epic champions.");
        translations.put("epicarmoraddition.title", "Epic Champion Armor Addition");
        translations.put("epicarmoraddition", "Armor addition applied to epic champions.");
        translations.put("epicmovementspeedmultiplier.title", "Epic Champion Movement Speed Multiplier");
        translations.put("epicmovementspeedmultiplier", "Movement speed multiplier applied to epic champions.");
        translations.put("epicattackdamagemultiplier.title", "Epic Champion Attack Damage Multiplier");
        translations.put("epicattackdamagemultiplier", "Attack damage multiplier applied to epic champions.");
        translations.put("epicknockbackresistanceaddition.title", "Epic Champion Knockback Resistance Addition");
        translations.put("epicknockbackresistanceaddition", "Knockback resistance addition applied to epic champions.");
        translations.put("legendaryhealthmultiplier.title", "Legendary Champion Health Multiplier");
        translations.put("legendaryhealthmultiplier", "Health multiplier applied to legendary champions.");
        translations.put("legendaryarmoraddition.title", "Legendary Champion Armor Addition");
        translations.put("legendaryarmoraddition", "Armor addition applied to legendary champions.");
        translations.put("legendarymovementspeedmultiplier.title", "Legendary Champion Movement Speed Multiplier");
        translations.put("legendarymovementspeedmultiplier", "Movement speed multiplier applied to legendary champions.");
        translations.put("legendaryattackdamagemultiplier.title", "Legendary Champion Attack Damage Multiplier");
        translations.put("legendaryattackdamagemultiplier", "Attack damage multiplier applied to legendary champions.");
        translations.put("legendaryknockbackresistanceaddition.title", "Legendary Champion Knockback Resistance Addition");
        translations.put("legendaryknockbackresistanceaddition", "Knockback resistance addition applied to legendary champions.");
        translations.put("experience.title", "Champion Experience Settings");
        translations.put("experience", joiner(
            "Settings that modify the experience dropped by champions based on their rank."
        ));
        translations.put("uncommonexperiencemultiplier.title", "Uncommon Champion Experience Multiplier");
        translations.put("uncommonexperiencemultiplier", "Experience multiplier applied to uncommon champions.");
        translations.put("rareexperiencemultiplier.title", "Rare Champion Experience Multiplier");
        translations.put("rareexperiencemultiplier", "Experience multiplier applied to rare champions.");
        translations.put("epicexperiencemultiplier.title", "Epic Champion Experience Multiplier");
        translations.put("epicexperiencemultiplier", "Experience multiplier applied to epic champions.");
        translations.put("legendaryexperiencemultiplier.title", "Legendary Champion Experience Multiplier");
        translations.put("legendaryexperiencemultiplier", "Experience multiplier applied to legendary champions.");
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
