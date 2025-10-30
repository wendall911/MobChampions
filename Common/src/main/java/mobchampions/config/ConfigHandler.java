package mobchampions.config;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;

import net.minecraft.world.item.component.FireworkExplosion;

import org.apache.commons.lang3.tuple.Pair;

import technology.roughness.whitenoise.config.WhiteNoiseConfigSpec;

import mobchampions.common.Translations;
import mobchampions.util.ColorHelper;

public class ConfigHandler {

    public static final WhiteNoiseConfigSpec CLIENT_SPEC;
    public static final WhiteNoiseConfigSpec COMMON_SPEC;

    private static final Client CLIENT;
    private static final Common COMMON;

    static {
        final Pair<Client, WhiteNoiseConfigSpec> specPairClient = new WhiteNoiseConfigSpec.Builder().configure(Client::new);
        final Pair<Common, WhiteNoiseConfigSpec> specPairCommon = new WhiteNoiseConfigSpec.Builder().configure(Common::new);

        CLIENT_SPEC = specPairClient.getRight();
        CLIENT = specPairClient.getLeft();
        COMMON_SPEC = specPairCommon.getRight();
        COMMON = specPairCommon.getLeft();
    }

    public static void init() {
        Client.decodedColors.clear();

        CLIENT.fireworksColors.get().forEach((colorString) -> {
            Client.decodedColors.add(ColorHelper.decode(colorString).getRGB());
        });
    }

    public static class Client {

        private static final String[] colorStrings = new String[]{"#3B511A", "#41CD34"};
        private static final List<String> colorsList = List.of("colors");
        private static final IntList decodedColors = new IntArrayList();
        private static final Predicate<Object> hexValidator = s -> s instanceof String
            && ((String) s).matches("#[a-zA-Z\\d]{6}");
        private static final List<String> shapes = Stream.of(FireworkExplosion.Shape.values()).map(Enum::name).toList();

        private final WhiteNoiseConfigSpec.IntValue fireworksChance;
        private final WhiteNoiseConfigSpec.ConfigValue<List<? extends String>> fireworksColors;
        private final WhiteNoiseConfigSpec.BooleanValue fireworksFlicker;
        private final WhiteNoiseConfigSpec.BooleanValue fireworksTrail;
        private final WhiteNoiseConfigSpec.EnumValue<FireworkExplosion.Shape> fireworksShape;
        private final WhiteNoiseConfigSpec.IntValue fireworksHeight;

        public Client(WhiteNoiseConfigSpec.Builder builder) {
            builder.push("visuals");

            fireworksChance = builder
                .comment(getTranslation("fireworkschance"))
                .defineInRange("fireworksChance", 100, 0, 100);
            fireworksColors = builder
                .comment(
                    getTranslation("colors"),
                    "Default: [\"" + String.join("\", \"", colorStrings) + "\"]"
                )
                .defineListAllowEmpty(colorsList, getColors(), hexValidator);
            fireworksFlicker = builder
                .comment(getTranslation("fireworksflicker"))
                .define("fireworksFlicker", true);
            fireworksTrail = builder
                .comment(getTranslation("fireworkstrail"))
                .define("fireworksTrail", true);
            fireworksShape = builder
                .comment(getTranslation("fireworksshape"), "One of: " + shapes)
                .defineEnum("fireworksShape", FireworkExplosion.Shape.CREEPER);
            fireworksHeight = builder
                .comment(getTranslation("fireworksheight"), "Default 5")
                .defineInRange("fireworksHeight", 5, 0, 32);
        }

        public static int fireworksChance() {
            return CLIENT.fireworksChance.get();
        }

        public static IntList getColorsList() {
            return Client.decodedColors;
        }

        public static boolean fireworksFlicker() {
            return CLIENT.fireworksFlicker.get();
        }

        public static boolean fireworksTrail() {
            return CLIENT.fireworksTrail.get();
        }

        public static FireworkExplosion.Shape getFireworksShape() {
            return CLIENT.fireworksShape.get();
        }

        public static float getFireworksHeight() {
            return (float) CLIENT.fireworksHeight.get();
        }

        private static Supplier<List<? extends String>> getColors() {
            return () -> Arrays.asList(Client.colorStrings);
        }

    }

    public static class Common {

        public Common(WhiteNoiseConfigSpec.Builder builder) {
            builder.push("general");

        }

    }

    private static String getTranslation(String key) {
        return Translations.get(key);
    }

    private static String getTranslation(String key, String... values) {
        return Translations.get(key, values);
    }

}
