package mobchampions.common;

import java.util.Map;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;

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
        translations.put("spawning", "Configuration options related to champion spawning.");
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
