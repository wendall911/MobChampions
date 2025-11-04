package mobchampions.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;

import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.component.FireworkExplosion;

import org.apache.commons.lang3.tuple.Pair;

import technology.roughness.whitenoise.config.WhiteNoiseConfigSpec;

import mobchampions.MobChampions;
import mobchampions.common.Translations;
import mobchampions.network.MobChampion.Rank;
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

    public static void clientInit() {
        Client.decodedColors.clear();
        CLIENT.fireworksColors.get().forEach((colorString) -> {
            Client.decodedColors.add(ColorHelper.decode(colorString).getRGB());
        });
    }

    public static void commonInit() {
        // Initialize total weight
        Common.totalWeight = COMMON.commonMobWeight.get()
            + COMMON.uncommonChampionWeight.get()
            + COMMON.rareChampionWeight.get()
            + COMMON.epicChampionWeight.get()
            + COMMON.legendaryChampionWeight.get();

        // Initialize champion weight map
        Common.championWeightMap.clear();

        // Generate ranges based on weights
        for (Rank rank : Rank.values()) {
            switch (rank) {
                case COMMON -> {
                    if (COMMON.commonMobWeight.get() >= 0) {
                        Common.championWeightMap.put(
                            0,
                            rank
                        );
                    }
                }
                case UNCOMMON -> {
                    if (COMMON.commonMobWeight.get() >= 0) {
                        Common.championWeightMap.put(
                            COMMON.commonMobWeight.get(),
                            rank
                        );
                    }
                }
                case RARE -> {
                    if (COMMON.commonMobWeight.get() >= 0) {
                        Common.championWeightMap.put(
                            COMMON.commonMobWeight.get()
                                + COMMON.uncommonChampionWeight.get(),
                            rank
                        );
                    }
                }
                case EPIC -> {
                    if (COMMON.commonMobWeight.get() >= 0) {
                        Common.championWeightMap.put(
                            COMMON.commonMobWeight.get()
                                + COMMON.uncommonChampionWeight.get()
                                + COMMON.rareChampionWeight.get(),
                            rank
                        );
                    }
                }
                case LEGENDARY -> {
                    if (COMMON.commonMobWeight.get() >= 0) {
                        Common.championWeightMap.put(
                            COMMON.commonMobWeight.get()
                                + COMMON.uncommonChampionWeight.get()
                                + COMMON.rareChampionWeight.get()
                                + COMMON.epicChampionWeight.get(),
                            rank
                        );
                    }
                }
            }
        }

        MobChampions.LOGGER.warn("Configured champion rank weights: {} {}", Common.championWeightMap, Common.getTotalWeight());

        int randomWeight = MobChampions.RANDOM.nextInt(Common.getTotalWeight());
        Rank selectedChampion = Common.getChampionByWeight(randomWeight);
        MobChampions.LOGGER.warn("Random weight: {}, Selected Champion Rank: {}", randomWeight, selectedChampion);
        MobChampions.LOGGER.warn("Odds: Common: {}%, Uncommon: {}%, Rare: {}%, Epic: {}%, Legendary: {}%",
            (COMMON.commonMobWeight.get() * 100.0F) / Common.getTotalWeight(),
            (COMMON.uncommonChampionWeight.get() * 100.0F) / Common.getTotalWeight(),
            (COMMON.rareChampionWeight.get() * 100.0F) / Common.getTotalWeight(),
            (COMMON.epicChampionWeight.get() * 100.0F) / Common.getTotalWeight(),
            (COMMON.legendaryChampionWeight.get() * 100.0F) / Common.getTotalWeight()
        );

        Common.spawnTypeBlacklistEnums.clear();
        COMMON.spawnTypeBlacklistSource.get().forEach(
            (spawnTypeString) -> Common.spawnTypeBlacklistEnums.add(
                Enum.valueOf(MobSpawnType.class, spawnTypeString)));
    }

    public static class Client {

        private static final String[] colorStrings = new String[]{"#FFDB00", "#F49000", "#D27C00", "#BD6F00"};
        private static final List<String> colorsList = List.of("colors");
        private static final IntList decodedColors = new IntArrayList();
        private static final Predicate<Object> hexValidator = s -> s instanceof String
            && ((String) s).matches("#[a-fA-F\\d]{6}");
        private static final List<String> shapes = Stream.of(FireworkExplosion.Shape.values()).map(Enum::name).toList();

        private final WhiteNoiseConfigSpec.ConfigValue<String> uncommonChampionColor;
        private final WhiteNoiseConfigSpec.ConfigValue<String> rareChampionColor;
        private final WhiteNoiseConfigSpec.ConfigValue<String> epicChampionColor;
        private final WhiteNoiseConfigSpec.ConfigValue<String> legendaryChampionColor;
        private final WhiteNoiseConfigSpec.IntValue fireworksChance;
        private final WhiteNoiseConfigSpec.ConfigValue<List<? extends String>> fireworksColors;
        private final WhiteNoiseConfigSpec.BooleanValue fireworksFlicker;
        private final WhiteNoiseConfigSpec.BooleanValue fireworksTrail;
        private final WhiteNoiseConfigSpec.EnumValue<FireworkExplosion.Shape> fireworksShape;
        private final WhiteNoiseConfigSpec.IntValue fireworksHeight;

        public Client(WhiteNoiseConfigSpec.Builder builder) {
            builder.push("visuals");

            uncommonChampionColor = builder
                .comment(getTranslation("uncommonchampioncolor"))
                .define("uncommonChampionColor", "#00F403", hexValidator);
            rareChampionColor = builder
                .comment(getTranslation("rarechampioncolor"))
                .define("rareChampionColor", "#3600FF", hexValidator);
            epicChampionColor = builder
                .comment(getTranslation("epicchampioncolor"))
                .define("epicChampionColor", "#EF00F4", hexValidator);
            legendaryChampionColor = builder
                .comment(getTranslation("legendarychampioncolor"))
                .define("legendaryChampionColor", "#F49000", hexValidator);
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
                .defineEnum("fireworksShape", FireworkExplosion.Shape.BURST);
            fireworksHeight = builder
                .comment(getTranslation("fireworksheight"), "Default 5")
                .defineInRange("fireworksHeight", 5, 0, 32);
        }

        public static int getUncommonChampionColor() {
            return ColorHelper.decode(CLIENT.uncommonChampionColor.get()).getRGB();
        }

        public static int getRareChampionColor() {
            return ColorHelper.decode(CLIENT.rareChampionColor.get()).getRGB();
        }

        public static int getEpicChampionColor() {
            return ColorHelper.decode(CLIENT.epicChampionColor.get()).getRGB();
        }

        public static int getLegendaryChampionColor() {
            return ColorHelper.decode(CLIENT.legendaryChampionColor.get()).getRGB();
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

        private static int totalWeight;
        private static final NavigableMap<Integer, Rank> championWeightMap = new TreeMap<>();
        private final WhiteNoiseConfigSpec.IntValue commonMobWeight;
        private final WhiteNoiseConfigSpec.IntValue uncommonChampionWeight;
        private final WhiteNoiseConfigSpec.IntValue rareChampionWeight;
        private final WhiteNoiseConfigSpec.IntValue epicChampionWeight;
        private final WhiteNoiseConfigSpec.IntValue legendaryChampionWeight;
        private final WhiteNoiseConfigSpec.ConfigValue<List<? extends String>> championWhitelistMobs;
        private static final List<String> championWhitelist = List.of("championWhitelist");
        private static final String[] defaultWhitelist = {
            "minecraft:bogged",
            "minecraft:cave_spider",
            "minecraft:zombie",
            "minecraft:zombified_piglin",
            "minecraft:skeleton",
            "minecraft:creeper",
            "minecraft:spider",
            "minecraft:enderman",
            "minecraft:witch",
            "minecraft:husk",
            "minecraft:stray",
            "minecraft:wither_skeleton",
            "minecraft:drowned",
            "minecraft:pillager"
        };
        private final Predicate<Object> entityTypeValidator = s -> s instanceof String
            && ((String) s).matches("[a-z]+[:]{1}[a-z_]+");
        private final WhiteNoiseConfigSpec.ConfigValue<List<? extends String>> spawnTypeBlacklistSource;
        private static final List<Enum<MobSpawnType>> spawnTypeBlacklistEnums = new ArrayList<>();
        private static final List<String> spawnTypeBlacklist =  List.of("spawnTypeBlacklist");
        private static final String[] defaultSpawnTypeBlacklist = {
            MobSpawnType.BREEDING.name(),
            MobSpawnType.BUCKET.name(),
            MobSpawnType.CHUNK_GENERATION.name(),
            MobSpawnType.DISPENSER.name(),
            MobSpawnType.PATROL.name(),
            MobSpawnType.SPAWNER.name(),
            MobSpawnType.STRUCTURE.name(),
            MobSpawnType.TRIAL_SPAWNER.name()
        };
        private final Predicate<Object> spawnTypeValidator = s -> s instanceof String
            && Arrays.stream(MobSpawnType.values())
                .map(Enum::name)
                .anyMatch(name -> name.equals(s));
        private final WhiteNoiseConfigSpec.DoubleValue uncommonChampionHealthMultiplier;
        private final WhiteNoiseConfigSpec.IntValue uncommonChampionArmorAddition;
        private final WhiteNoiseConfigSpec.DoubleValue uncommonChampionMovementSpeedMultiplier;
        private final WhiteNoiseConfigSpec.DoubleValue uncommonChampionAttackDamageMultiplier;
        private final WhiteNoiseConfigSpec.DoubleValue uncommonChampionKnockbackResistanceAddition;
        private final WhiteNoiseConfigSpec.DoubleValue rareChampionHealthMultiplier;
        private final WhiteNoiseConfigSpec.IntValue rareChampionArmorAddition;
        private final WhiteNoiseConfigSpec.DoubleValue rareChampionMovementSpeedMultiplier;
        private final WhiteNoiseConfigSpec.DoubleValue rareChampionAttackDamageMultiplier;
        private final WhiteNoiseConfigSpec.DoubleValue rareChampionKnockbackResistanceAddition;
        private final WhiteNoiseConfigSpec.DoubleValue epicChampionHealthMultiplier;
        private final WhiteNoiseConfigSpec.IntValue epicChampionArmorAddition;
        private final WhiteNoiseConfigSpec.DoubleValue epicChampionMovementSpeedMultiplier;
        private final WhiteNoiseConfigSpec.DoubleValue epicChampionAttackDamageMultiplier;
        private final WhiteNoiseConfigSpec.DoubleValue epicChampionKnockbackResistanceAddition;
        private final WhiteNoiseConfigSpec.DoubleValue legendaryChampionHealthMultiplier;
        private final WhiteNoiseConfigSpec.IntValue legendaryChampionArmorAddition;
        private final WhiteNoiseConfigSpec.DoubleValue legendaryChampionMovementSpeedMultiplier;
        private final WhiteNoiseConfigSpec.DoubleValue legendaryChampionAttackDamageMultiplier;
        private final WhiteNoiseConfigSpec.DoubleValue legendaryChampionKnockbackResistanceAddition;

        public Common(WhiteNoiseConfigSpec.Builder builder) {
            builder.push("spawning").comment(getTranslation("spawning")); // spawning

            commonMobWeight = builder
                .comment(getTranslation("commonmobweight"))
                .defineInRange("commonMobWeight", 91, 0, 100);
            uncommonChampionWeight = builder
                .comment(getTranslation("uncommonchampionweight"))
                .defineInRange("uncommonChampionWeight", 50, 0, 100);
            rareChampionWeight = builder
                .comment(getTranslation("rarechampionweight"))
                .defineInRange("rareChampionWeight", 30, 0, 100);
            epicChampionWeight = builder
                .comment(getTranslation("epicchampionweight"))
                .defineInRange("epicChampionWeight", 9, 0, 100);
            legendaryChampionWeight = builder
                .comment(getTranslation("legendarychampionweight"))
                .defineInRange("legendaryChampionWeight", 2, 0, 100);
            championWhitelistMobs = builder
                .comment(
                    getTranslation("championwhitelist", String.join(", ", defaultWhitelist)),
                    "Entity types must be in the format modid:entity_name"
                )
                .defineListAllowEmpty(championWhitelist, getFields(defaultWhitelist), entityTypeValidator);
            spawnTypeBlacklistSource = builder
                .comment(getTranslation("spawntypeblacklist"))
                .defineListAllowEmpty(spawnTypeBlacklist, getFields(defaultSpawnTypeBlacklist), spawnTypeValidator);

            builder.push("stats").comment(getTranslation("stats")); // spawning.stats
            // Uncommon Champion Stats
            uncommonChampionHealthMultiplier = builder
                .comment(getTranslation("uncommonchampionhealthmultiplier"))
                .defineInRange("uncommonChampionHealthMultiplier", 0.5, 0.1, 20.0);
            uncommonChampionArmorAddition = builder
                .comment(getTranslation("uncommonchampionarmoraddition"))
                .defineInRange("uncommonChampionArmorAddition", 2, 1, 20);
            uncommonChampionMovementSpeedMultiplier = builder
                .comment(getTranslation("uncommonchampionmovementspeedmultiplier"))
                .defineInRange("uncommonChampionMovementSpeedMultiplier", 0.15, 0.01, 5.0);
            uncommonChampionAttackDamageMultiplier = builder
                .comment(getTranslation("uncommonchampionattackdamagemultiplier"))
                .defineInRange("uncommonChampionAttackDamageMultiplier", 0.7, 0.01, 10.0);
            uncommonChampionKnockbackResistanceAddition = builder
                .comment(getTranslation("uncommonchampionknockbackresistanceaddition"))
                .defineInRange("uncommonChampionKnockbackResistanceAddition", 0.03, 0.01, 1.0);
            // Rare Champion Stats
            rareChampionHealthMultiplier = builder
                .comment(getTranslation("rarechampionhealthmultiplier"))
                .defineInRange("rareChampionHealthMultiplier", 0.9, 0.1, 20.0);
            rareChampionArmorAddition = builder
                .comment(getTranslation("rarechampionarmoraddition"))
                .defineInRange("rareChampionArmorAddition", 4, 1, 20);
            rareChampionMovementSpeedMultiplier = builder
                .comment(getTranslation("rarechampionmovementspeedmultiplier"))
                .defineInRange("rareChampionMovementSpeedMultiplier", 0.25, 0.01, 5.0);
            rareChampionAttackDamageMultiplier = builder
                .comment(getTranslation("rarechampionattackdamagemultiplier"))
                .defineInRange("rareChampionAttackDamageMultiplier", 2.1, 0.01, 10.0);
            rareChampionKnockbackResistanceAddition = builder
                .comment(getTranslation("rarechampionknockbackresistanceaddition"))
                .defineInRange("rareChampionKnockbackResistanceAddition", 0.1, 0.01, 1.0);
            // Epic Champion Stats
            epicChampionHealthMultiplier = builder
                .comment(getTranslation("epicchampionhealthmultiplier"))
                .defineInRange("epicChampionHealthMultiplier", 1.8, 0.1, 20.0);
            epicChampionArmorAddition = builder
                .comment(getTranslation("epicchampionarmoraddition"))
                .defineInRange("epicChampionArmorAddition", 8, 1, 20);
            epicChampionMovementSpeedMultiplier = builder
                .comment(getTranslation("epicchampionmovementspeedmultiplier"))
                .defineInRange("epicChampionMovementSpeedMultiplier", 0.4, 0.01, 5.0);
            epicChampionAttackDamageMultiplier = builder
                .comment(getTranslation("epicchampionattackdamagemultiplier"))
                .defineInRange("epicChampionAttackDamageMultiplier", 2.1, 0.01, 10.0);
            epicChampionKnockbackResistanceAddition = builder
                .comment(getTranslation("epicchampionknockbackresistanceaddition"))
                .defineInRange("epicChampionKnockbackResistanceAddition", 0.5, 0.01, 1.0);
            // Legendary Champion Stats
            legendaryChampionHealthMultiplier = builder
                .comment(getTranslation("legendarychampionhealthmultiplier"))
                .defineInRange("legendaryChampionHealthMultiplier", 2.7, 0.1, 20.0);
            legendaryChampionArmorAddition = builder
                .comment(getTranslation("legendarychampionarmoraddition"))
                .defineInRange("legendaryChampionArmorAddition", 15, 1, 20);
            legendaryChampionMovementSpeedMultiplier = builder
                .comment(getTranslation("legendarychampionmovementspeedmultiplier"))
                .defineInRange("legendaryChampionMovementSpeedMultiplier", 0.8, 0.8, 5.0);
            legendaryChampionAttackDamageMultiplier = builder
                .comment(getTranslation("legendarychampionattackdamagemultiplier"))
                .defineInRange("legendaryChampionAttackDamageMultiplier", 3.0, 0.01, 10.0);
            legendaryChampionKnockbackResistanceAddition = builder
                .comment(getTranslation("legendarychampionknockbackresistanceaddition"))
                .defineInRange("legendaryChampionKnockbackResistanceAddition", 1.0, 0.01, 1.0);
        }

        private static Supplier<List<? extends String>> getFields(String[] strings) {
            return () -> Arrays.asList(strings);
        }

        public static int getTotalWeight() {
            return totalWeight;
        }

        public static Rank getChampionByWeight(int weight) {
            return championWeightMap.floorEntry(weight).getValue();
        }

        public static List<? extends String> getChampionWhitelist() {
            return COMMON.championWhitelistMobs.get();
        }

        public static List<Enum<MobSpawnType>> getSpawnTypeBlacklistEnums() {
            return spawnTypeBlacklistEnums;
        }

        public static double getUncommonChampionHealthMultiplier() {
            return COMMON.uncommonChampionHealthMultiplier.get();
        }

        public static int getUncommonChampionArmorAddition() {
            return COMMON.uncommonChampionArmorAddition.get();
        }

        public static double getUncommonChampionMovementSpeedMultiplier() {
            return COMMON.uncommonChampionMovementSpeedMultiplier.get();
        }

        public static double getUncommonChampionAttackDamageMultiplier() {
            return COMMON.uncommonChampionAttackDamageMultiplier.get();
        }

        public static double getUncommonChampionKnockbackResistanceAddition() {
            return COMMON.uncommonChampionKnockbackResistanceAddition.get();
        }

        public static double getRareChampionHealthMultiplier() {
            return COMMON.rareChampionHealthMultiplier.get();
        }

        public static int getRareChampionArmorAddition() {
            return COMMON.rareChampionArmorAddition.get();
        }

        public static double getRareChampionMovementSpeedMultiplier() {
            return COMMON.rareChampionMovementSpeedMultiplier.get();
        }

        public static double getRareChampionAttackDamageMultiplier() {
            return COMMON.rareChampionAttackDamageMultiplier.get();
        }

        public static double getRareChampionKnockbackResistanceAddition() {
            return COMMON.rareChampionKnockbackResistanceAddition.get();
        }

        public static double getEpicChampionHealthMultiplier() {
            return COMMON.epicChampionHealthMultiplier.get();
        }

        public static int getEpicChampionArmorAddition() {
            return COMMON.epicChampionArmorAddition.get();
        }

        public static double getEpicChampionMovementSpeedMultiplier() {
            return COMMON.epicChampionMovementSpeedMultiplier.get();
        }

        public static double getEpicChampionAttackDamageMultiplier() {
            return COMMON.epicChampionAttackDamageMultiplier.get();
        }

        public static double getEpicChampionKnockbackResistanceAddition() {
            return COMMON.epicChampionKnockbackResistanceAddition.get();
        }

        public static double getLegendaryChampionHealthMultiplier() {
            return COMMON.legendaryChampionHealthMultiplier.get();
        }

        public static int getLegendaryChampionArmorAddition() {
            return COMMON.legendaryChampionArmorAddition.get();
        }

        public static double getLegendaryChampionMovementSpeedMultiplier() {
            return COMMON.legendaryChampionMovementSpeedMultiplier.get();
        }

        public static double getLegendaryChampionAttackDamageMultiplier() {
            return COMMON.legendaryChampionAttackDamageMultiplier.get();
        }

        public static double getLegendaryChampionKnockbackResistanceAddition() {
            return COMMON.legendaryChampionKnockbackResistanceAddition.get();
        }

    }

    private static String getTranslation(String key) {
        return Translations.get(key);
    }

    private static String getTranslation(String key, String... values) {
        return Translations.get(key, values);
    }

}
