package mobchampions.util;

import java.awt.Color;

import net.minecraft.util.FastColor;

public class ColorHelper extends technology.roughness.whitenoise.util.ColorHelper {

    public static Color decode(String color) {
        try {
            return Color.decode(color);
        }
        catch (NumberFormatException e) {
            return Color.WHITE;
        }
    }

    public static int hexToARGB(String hex) {
        Color color = decode(hex);

        return FastColor.ARGB32.color(color.getAlpha(), color.getRed(), color.getGreen(), color.getBlue());
    }

}
