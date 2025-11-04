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
            + COMMON.uncommonWeight.get()
            + COMMON.rareWeight.get()
            + COMMON.epicWeight.get()
            + COMMON.legendaryWeight.get();

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
                                + COMMON.uncommonWeight.get(),
                            rank
                        );
                    }
                }
                case EPIC -> {
                    if (COMMON.commonMobWeight.get() >= 0) {
                        Common.championWeightMap.put(
                            COMMON.commonMobWeight.get()
                                + COMMON.uncommonWeight.get()
                                + COMMON.rareWeight.get(),
                            rank
                        );
                    }
                }
                case LEGENDARY -> {
                    if (COMMON.commonMobWeight.get() >= 0) {
                        Common.championWeightMap.put(
                            COMMON.commonMobWeight.get()
                                + COMMON.uncommonWeight.get()
                                + COMMON.rareWeight.get()
                                + COMMON.epicWeight.get(),
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
            (COMMON.uncommonWeight.get() * 100.0F) / Common.getTotalWeight(),
            (COMMON.rareWeight.get() * 100.0F) / Common.getTotalWeight(),
            (COMMON.epicWeight.get() * 100.0F) / Common.getTotalWeight(),
            (COMMON.legendaryWeight.get() * 100.0F) / Common.getTotalWeight()
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

        private final WhiteNoiseConfigSpec.ConfigValue<String> uncommonColor;
        private final WhiteNoiseConfigSpec.ConfigValue<String> rareColor;
        private final WhiteNoiseConfigSpec.ConfigValue<String> epicColor;
        private final WhiteNoiseConfigSpec.ConfigValue<String> legendaryColor;
        private final WhiteNoiseConfigSpec.IntValue fireworksChance;
        private final WhiteNoiseConfigSpec.ConfigValue<List<? extends String>> fireworksColors;
        private final WhiteNoiseConfigSpec.BooleanValue fireworksFlicker;
        private final WhiteNoiseConfigSpec.BooleanValue fireworksTrail;
        private final WhiteNoiseConfigSpec.EnumValue<FireworkExplosion.Shape> fireworksShape;
        private final WhiteNoiseConfigSpec.IntValue fireworksHeight;

        public Client(WhiteNoiseConfigSpec.Builder builder) {
            builder.push("visuals");

            uncommonColor = builder
                .comment(getTranslation("uncommoncolor"))
                .define("uncommonColor", "#00F403", hexValidator);
            rareColor = builder
                .comment(getTranslation("rarecolor"))
                .define("rareColor", "#3600FF", hexValidator);
            epicColor = builder
                .comment(getTranslation("epiccolor"))
                .define("epicColor", "#EF00F4", hexValidator);
            legendaryColor = builder
                .comment(getTranslation("legendarycolor"))
                .define("legendaryColor", "#F49000", hexValidator);
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

        public static int getUncommonColor() {
            return ColorHelper.decode(CLIENT.uncommonColor.get()).getRGB();
        }

        public static int getRareColor() {
            return ColorHelper.decode(CLIENT.rareColor.get()).getRGB();
        }

        public static int getEpicColor() {
            return ColorHelper.decode(CLIENT.epicColor.get()).getRGB();
        }

        public static int getLegendaryColor() {
            return ColorHelper.decode(CLIENT.legendaryColor.get()).getRGB();
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
        private final WhiteNoiseConfigSpec.IntValue uncommonWeight;
        private final WhiteNoiseConfigSpec.IntValue rareWeight;
        private final WhiteNoiseConfigSpec.IntValue epicWeight;
        private final WhiteNoiseConfigSpec.IntValue legendaryWeight;
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
        private final WhiteNoiseConfigSpec.DoubleValue uncommonHealthMultiplier;
        private final WhiteNoiseConfigSpec.IntValue uncommonArmorAddition;
        private final WhiteNoiseConfigSpec.DoubleValue uncommonMovementSpeedMultiplier;
        private final WhiteNoiseConfigSpec.DoubleValue uncommonAttackDamageMultiplier;
        private final WhiteNoiseConfigSpec.DoubleValue uncommonKnockbackResistanceAddition;
        private final WhiteNoiseConfigSpec.DoubleValue rareHealthMultiplier;
        private final WhiteNoiseConfigSpec.IntValue rareArmorAddition;
        private final WhiteNoiseConfigSpec.DoubleValue rareMovementSpeedMultiplier;
        private final WhiteNoiseConfigSpec.DoubleValue rareAttackDamageMultiplier;
        private final WhiteNoiseConfigSpec.DoubleValue rareKnockbackResistanceAddition;
        private final WhiteNoiseConfigSpec.DoubleValue epicHealthMultiplier;
        private final WhiteNoiseConfigSpec.IntValue epicArmorAddition;
        private final WhiteNoiseConfigSpec.DoubleValue epicMovementSpeedMultiplier;
        private final WhiteNoiseConfigSpec.DoubleValue epicAttackDamageMultiplier;
        private final WhiteNoiseConfigSpec.DoubleValue epicKnockbackResistanceAddition;
        private final WhiteNoiseConfigSpec.DoubleValue legendaryHealthMultiplier;
        private final WhiteNoiseConfigSpec.IntValue legendaryArmorAddition;
        private final WhiteNoiseConfigSpec.DoubleValue legendaryMovementSpeedMultiplier;
        private final WhiteNoiseConfigSpec.DoubleValue legendaryAttackDamageMultiplier;
        private final WhiteNoiseConfigSpec.DoubleValue legendaryKnockbackResistanceAddition;
        private final WhiteNoiseConfigSpec.DoubleValue uncommonExperienceMultiplier;
        private final WhiteNoiseConfigSpec.DoubleValue rareExperienceMultiplier;
        private final WhiteNoiseConfigSpec.DoubleValue epicExperienceMultiplier;
        private final WhiteNoiseConfigSpec.DoubleValue legendaryExperienceMultiplier;

        public Common(WhiteNoiseConfigSpec.Builder builder) {
            builder.push("spawning").comment(getTranslation("spawning")); // spawning

            commonMobWeight = builder
                .comment(getTranslation("commonmobweight"))
                .defineInRange("commonMobWeight", 91, 0, 100);
            uncommonWeight = builder
                .comment(getTranslation("uncommonweight"))
                .defineInRange("uncommonWeight", 50, 0, 100);
            rareWeight = builder
                .comment(getTranslation("rareweight"))
                .defineInRange("rareWeight", 30, 0, 100);
            epicWeight = builder
                .comment(getTranslation("epicweight"))
                .defineInRange("epicWeight", 9, 0, 100);
            legendaryWeight = builder
                .comment(getTranslation("legendaryweight"))
                .defineInRange("legendaryWeight", 2, 0, 100);
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
            uncommonHealthMultiplier = builder
                .comment(getTranslation("uncommonhealthmultiplier"))
                .defineInRange("uncommonHealthMultiplier", 0.5, 0.1, 20.0);
            uncommonArmorAddition = builder
                .comment(getTranslation("uncommonarmoraddition"))
                .defineInRange("uncommonArmorAddition", 2, 1, 20);
            uncommonMovementSpeedMultiplier = builder
                .comment(getTranslation("uncommonmovementspeedmultiplier"))
                .defineInRange("uncommonMovementSpeedMultiplier", 0.15, 0.01, 5.0);
            uncommonAttackDamageMultiplier = builder
                .comment(getTranslation("uncommonattackdamagemultiplier"))
                .defineInRange("uncommonAttackDamageMultiplier", 0.7, 0.01, 10.0);
            uncommonKnockbackResistanceAddition = builder
                .comment(getTranslation("uncommonknockbackresistanceaddition"))
                .defineInRange("uncommonKnockbackResistanceAddition", 0.03, 0.01, 1.0);
            // Rare Champion Stats
            rareHealthMultiplier = builder
                .comment(getTranslation("rarehealthmultiplier"))
                .defineInRange("rareHealthMultiplier", 0.9, 0.1, 20.0);
            rareArmorAddition = builder
                .comment(getTranslation("rarearmoraddition"))
                .defineInRange("rareArmorAddition", 4, 1, 20);
            rareMovementSpeedMultiplier = builder
                .comment(getTranslation("raremovementspeedmultiplier"))
                .defineInRange("rareMovementSpeedMultiplier", 0.25, 0.01, 5.0);
            rareAttackDamageMultiplier = builder
                .comment(getTranslation("rareattackdamagemultiplier"))
                .defineInRange("rareAttackDamageMultiplier", 2.1, 0.01, 10.0);
            rareKnockbackResistanceAddition = builder
                .comment(getTranslation("rareknockbackresistanceaddition"))
                .defineInRange("rareKnockbackResistanceAddition", 0.1, 0.01, 1.0);
            // Epic Champion Stats
            epicHealthMultiplier = builder
                .comment(getTranslation("epichealthmultiplier"))
                .defineInRange("epicHealthMultiplier", 1.8, 0.1, 20.0);
            epicArmorAddition = builder
                .comment(getTranslation("epicarmoraddition"))
                .defineInRange("epicArmorAddition", 8, 1, 20);
            epicMovementSpeedMultiplier = builder
                .comment(getTranslation("epicmovementspeedmultiplier"))
                .defineInRange("epicMovementSpeedMultiplier", 0.4, 0.01, 5.0);
            epicAttackDamageMultiplier = builder
                .comment(getTranslation("epicattackdamagemultiplier"))
                .defineInRange("epicAttackDamageMultiplier", 2.1, 0.01, 10.0);
            epicKnockbackResistanceAddition = builder
                .comment(getTranslation("epicknockbackresistanceaddition"))
                .defineInRange("epicKnockbackResistanceAddition", 0.5, 0.01, 1.0);
            // Legendary Champion Stats
            legendaryHealthMultiplier = builder
                .comment(getTranslation("legendaryhealthmultiplier"))
                .defineInRange("legendaryHealthMultiplier", 2.7, 0.1, 20.0);
            legendaryArmorAddition = builder
                .comment(getTranslation("legendaryarmoraddition"))
                .defineInRange("legendaryArmorAddition", 15, 1, 20);
            legendaryMovementSpeedMultiplier = builder
                .comment(getTranslation("legendarymovementspeedmultiplier"))
                .defineInRange("legendaryMovementSpeedMultiplier", 0.8, 0.8, 5.0);
            legendaryAttackDamageMultiplier = builder
                .comment(getTranslation("legendaryattackdamagemultiplier"))
                .defineInRange("legendaryAttackDamageMultiplier", 3.0, 0.01, 10.0);
            legendaryKnockbackResistanceAddition = builder
                .comment(getTranslation("legendaryknockbackresistanceaddition"))
                .defineInRange("legendaryKnockbackResistanceAddition", 1.0, 0.01, 1.0);

            builder.pop(); // spawning.stats
            builder.pop(); // spawning
            builder.push("experience"); // experience

            uncommonExperienceMultiplier = builder
                .comment(getTranslation("uncommonexperiencemultiplier"))
                .defineInRange("uncommonExperienceMultiplier", 2.0, 0, 50.0);
            rareExperienceMultiplier = builder
                .comment(getTranslation("rareexperiencemultiplier"))
                .defineInRange("rareExperienceMultiplier", 5.0, 0, 50.0);
            epicExperienceMultiplier = builder
                .comment(getTranslation("epicexperiencemultiplier"))
                .defineInRange("epicExperienceMultiplier", 11.0, 0, 50.0);
            legendaryExperienceMultiplier = builder
                .comment(getTranslation("legendaryexperiencemultiplier"))
                .defineInRange("legendaryExperienceMultiplier", 20.0, 0, 50.0);
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

        public static double getUncommonHealthMultiplier() {
            return COMMON.uncommonHealthMultiplier.get();
        }

        public static int getUncommonArmorAddition() {
            return COMMON.uncommonArmorAddition.get();
        }

        public static double getUncommonMovementSpeedMultiplier() {
            return COMMON.uncommonMovementSpeedMultiplier.get();
        }

        public static double getUncommonAttackDamageMultiplier() {
            return COMMON.uncommonAttackDamageMultiplier.get();
        }

        public static double getUncommonKnockbackResistanceAddition() {
            return COMMON.uncommonKnockbackResistanceAddition.get();
        }

        public static double getRareHealthMultiplier() {
            return COMMON.rareHealthMultiplier.get();
        }

        public static int getRareArmorAddition() {
            return COMMON.rareArmorAddition.get();
        }

        public static double getRareMovementSpeedMultiplier() {
            return COMMON.rareMovementSpeedMultiplier.get();
        }

        public static double getRareAttackDamageMultiplier() {
            return COMMON.rareAttackDamageMultiplier.get();
        }

        public static double getRareKnockbackResistanceAddition() {
            return COMMON.rareKnockbackResistanceAddition.get();
        }

        public static double getEpicHealthMultiplier() {
            return COMMON.epicHealthMultiplier.get();
        }

        public static int getEpicArmorAddition() {
            return COMMON.epicArmorAddition.get();
        }

        public static double getEpicMovementSpeedMultiplier() {
            return COMMON.epicMovementSpeedMultiplier.get();
        }

        public static double getEpicAttackDamageMultiplier() {
            return COMMON.epicAttackDamageMultiplier.get();
        }

        public static double getEpicKnockbackResistanceAddition() {
            return COMMON.epicKnockbackResistanceAddition.get();
        }

        public static double getLegendaryHealthMultiplier() {
            return COMMON.legendaryHealthMultiplier.get();
        }

        public static int getLegendaryArmorAddition() {
            return COMMON.legendaryArmorAddition.get();
        }

        public static double getLegendaryMovementSpeedMultiplier() {
            return COMMON.legendaryMovementSpeedMultiplier.get();
        }

        public static double getLegendaryAttackDamageMultiplier() {
            return COMMON.legendaryAttackDamageMultiplier.get();
        }

        public static double getLegendaryKnockbackResistanceAddition() {
            return COMMON.legendaryKnockbackResistanceAddition.get();
        }

        public static double getExperienceMultiplierForRank(Rank rank) {
            return switch (rank) {
                case UNCOMMON -> COMMON.uncommonExperienceMultiplier.get();
                case RARE -> COMMON.rareExperienceMultiplier.get();
                case EPIC -> COMMON.epicExperienceMultiplier.get();
                case LEGENDARY -> COMMON.legendaryExperienceMultiplier.get();
                default -> 1.0;
            };
        }

    }

    private static String getTranslation(String key) {
        return Translations.get(key);
    }

    private static String getTranslation(String key, String... values) {
        return Translations.get(key, values);
    }

}
