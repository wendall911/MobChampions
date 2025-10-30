package mobchampions.util;

import java.awt.Color;

public class ColorHelper {

    public static Color decode(String color) {
        try {
            return Color.decode(color);
        }
        catch (NumberFormatException e) {
            return Color.WHITE;
        }
    }

}
