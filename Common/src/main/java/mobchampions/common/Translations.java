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
        translations.put("fireworkschance.title", "Fireworks Chance");
        translations.put("fireworkschance", "Chance of fireworks after champion defeat.");
        translations.put("fireworksflicker.title", "Fireworks Flicker");
        translations.put("fireworksflicker", "Fireworks flicker setting as it relates to Minecraft fireworks.");
        translations.put("fireworkstrail.title", "Fireworks Trail");
        translations.put("fireworkstrail", "Fireworks trail setting as it relates to Minecraft fireworks.");
        translations.put("fireworksshape.title", "Fireworks Shape");
        translations.put("fireworksshape", "Fireworks shape setting as it relates to Minecraft fireworks.");
        translations.put("fireworksheight.title", "Fireworks Height");
        translations.put("fireworksheight", "Height above champion that fireworks explode.");
        translations.put("colors.title", "Fireworks Colors");
        translations.put("colors", "Colors to use in fireworks. Requires hex formatted colors.");
        translations.put("general.title", "General Settings");
        translations.put("spawning", "Configuration options related to champion spawning.");
        translations.put("commonchampionweight.title", "Common Champion Weight");
        translations.put("commonchampionweight", "Relative weight for common champions to spawn.");
        translations.put("uncommonchampionweight.title", "Uncommon Champion Weight");
        translations.put("uncommonchampionweight", "Relative weight for uncommon champions to spawn.");
        translations.put("rarechampionweight.title", "Rare Champion Weight");
        translations.put("rarechampionweight", "Relative weight for rare champions to spawn.");
        translations.put("epicchampionweight.title", "Epic Champion Weight");
        translations.put("epicchampionweight", "Relative weight for epic champions to spawn.");
        translations.put("legendarychampionweight.title", "Legendary Champion Weight");
        translations.put("legendarychampionweight", "Relative weight for legendary champions to spawn.");
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
