package mobchampions.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;

import net.minecraft.core.Holder.Reference;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.item.equipment.Equippable;

import org.apache.commons.lang3.tuple.Pair;

import technology.roughness.whitenoise.config.WhiteNoiseConfigSpec;

import mobchampions.MobChampions;
import mobchampions.common.Translations;
import mobchampions.common.stats.ChampionStatsManager;
import mobchampions.network.MobChampion;
import mobchampions.network.MobChampion.Rank;
import mobchampions.platform.Services;
import mobchampions.util.ColorHelper;
import mobchampions.util.MobChampionBuilder;

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

        if (Services.PLATFORM.isDevelopmentEnvironment()) {
            MobChampions.LOGGER.warn("Configured champion rank weights: {} {}", Common.championWeightMap, Common.getTotalWeight());
            MobChampions.LOGGER.warn("Odds: Common: {}%, Uncommon: {}%, Rare: {}%, Epic: {}%, Legendary: {}%",
                (COMMON.commonMobWeight.get() * 100.0F) / Common.getTotalWeight(),
                (COMMON.uncommonWeight.get() * 100.0F) / Common.getTotalWeight(),
                (COMMON.rareWeight.get() * 100.0F) / Common.getTotalWeight(),
                (COMMON.epicWeight.get() * 100.0F) / Common.getTotalWeight(),
                (COMMON.legendaryWeight.get() * 100.0F) / Common.getTotalWeight()
            );
        }

        // Initialize spawn type blacklist enums
        Common.spawnReasonBlacklistEnums.clear();
        COMMON.spawnTypeBlacklistSource.get().forEach(
            (spawnTypeString) -> Common.spawnReasonBlacklistEnums.add(
                Enum.valueOf(EntitySpawnReason.class, spawnTypeString)));

        // Initialize weapon items maps
        Common.uncommonWeaponItemsMap.clear();
        Common.rareWeaponItemsMap.clear();
        Common.epicWeaponItemsMap.clear();
        Common.legendaryWeaponItemsMap.clear();

        final int[] lastUncommonWeight = {0};
        final int[] lastRareWeight = {0};
        final int[] lastEpicWeight = {0};
        final int[] lastLegendaryWeight = {0};

        COMMON.weapons.get().forEach((weaponString) -> {
            String[] weaponParts = weaponString.split("-", 3);
            Rank rank = switch (weaponParts[0]) {
                case "2" -> Rank.RARE;
                case "3" -> Rank.EPIC;
                case "4" -> Rank.LEGENDARY;
                default -> Rank.UNCOMMON;
            };
            int weight = Integer.parseInt(weaponParts[1]);
            Optional<Reference<Item>> optionalItemReference = BuiltInRegistries.ITEM.get(Identifier.parse(weaponParts[2]));

            if (optionalItemReference.isEmpty()) {
                MobChampions.LOGGER.warn("Invalid item '{}' in weapons list, skipping...", weaponParts[2]);
                return;
            }

            ItemStack itemStack = new ItemStack(optionalItemReference.get().value());

            switch (rank) {
                case UNCOMMON -> {
                    Common.uncommonWeaponItemsMap.put(
                        lastUncommonWeight[0],
                        itemStack
                    );
                    lastUncommonWeight[0] += weight;
                }
                case RARE -> {
                    Common.rareWeaponItemsMap.put(
                        lastRareWeight[0],
                        itemStack
                    );
                    lastRareWeight[0] += weight;
                }
                case EPIC -> {
                    Common.epicWeaponItemsMap.put(
                        lastEpicWeight[0],
                        itemStack
                    );
                    lastEpicWeight[0] += weight;
                }
                case LEGENDARY -> {
                    Common.legendaryWeaponItemsMap.put(
                        lastLegendaryWeight[0],
                        itemStack
                    );
                    lastLegendaryWeight[0] += weight;
                }
            }
        });
        Common.totalUncommonWeaponWeight = lastUncommonWeight[0];
        Common.totalRareWeaponWeight = lastRareWeight[0];
        Common.totalEpicWeaponWeight = lastEpicWeight[0];
        Common.totalLegendaryWeaponWeight = lastLegendaryWeight[0];

        if (Services.PLATFORM.isDevelopmentEnvironment()) {
            MobChampions.LOGGER.warn("Configured uncommon weapon items: {} of: {}", Common.uncommonWeaponItemsMap, Common.totalUncommonWeaponWeight);
            MobChampions.LOGGER.warn("Configured rare weapon items: {} of: {}", Common.rareWeaponItemsMap, Common.totalRareWeaponWeight);
            MobChampions.LOGGER.warn("Configured epic weapon items: {} of: {}", Common.epicWeaponItemsMap, Common.totalEpicWeaponWeight);
            MobChampions.LOGGER.warn("Configured legendary weapon items: {} of: {}", Common.legendaryWeaponItemsMap, Common.totalLegendaryWeaponWeight);
        }

        final Map<EquipmentSlot, Integer> lastUncommonWeights = new HashMap<>();
        final Map<EquipmentSlot, Integer> lastRareWeights = new HashMap<>();
        final Map<EquipmentSlot, Integer> lastEpicWeights = new HashMap<>();
        final Map<EquipmentSlot, Integer> lastLegendaryWeights = new HashMap<>();

        // Initialize armor items maps
        for (EquipmentSlot slot : MobChampionBuilder.getArmorSlots()) {
            for (Rank rank : Rank.values()) {
                switch (rank) {
                    case UNCOMMON -> {
                        Common.totalUncommonArmorWeight.put(slot, 0);
                        lastUncommonWeights.put(slot, 0);
                        Common.uncommonArmorItemsMap.put(slot, new TreeMap<>());
                    }
                    case RARE -> {
                        Common.totalRareArmorWeight.put(slot, 0);
                        lastRareWeights.put(slot, 0);
                        Common.rareArmorItemsMap.put(slot, new TreeMap<>());
                    }
                    case EPIC -> {
                        Common.totalEpicArmorWeight.put(slot, 0);
                        lastEpicWeights.put(slot, 0);
                        Common.epicArmorItemsMap.put(slot, new TreeMap<>());
                    }
                    case LEGENDARY -> {
                        Common.totalLegendaryArmorWeight.put(slot, 0);
                        lastLegendaryWeights.put(slot, 0);
                        Common.legendaryArmorItemsMap.put(slot, new TreeMap<>());
                    }
                    default -> {}
                }
            }
        }

        COMMON.armors.get().forEach((armorString) -> {
            String[] armorParts = armorString.split("-", 3);
            Rank rank = switch (armorParts[0]) {
                case "2" -> Rank.RARE;
                case "3" -> Rank.EPIC;
                case "4" -> Rank.LEGENDARY;
                default -> Rank.UNCOMMON;
            };
            int weight = Integer.parseInt(armorParts[1]);
            Optional<Reference<Item>> optionalItemReference = BuiltInRegistries.ITEM.get(Identifier.parse(armorParts[2]));

            if (optionalItemReference.isEmpty()) {
                MobChampions.LOGGER.warn("Invalid item '{}' in armors list, skipping...", armorParts[2]);
                return;
            }

            ItemStack itemStack = new ItemStack(optionalItemReference.get().value());
            Equippable equippable = itemStack.get(DataComponents.EQUIPPABLE);

            // Check if item is equipable
            if (equippable == null) {
                MobChampions.LOGGER.warn("Item '{}' in armors list is not equipable, skipping...", armorParts[2]);
                return;
            }

            EquipmentSlot slot = equippable.slot();

            switch (rank) {
                case UNCOMMON -> {
                    Common.uncommonArmorItemsMap.get(slot).put(
                        lastUncommonWeights.get(slot),
                        itemStack
                    );
                    lastUncommonWeights.put(slot, lastUncommonWeights.get(slot) + weight);
                }
                case RARE -> {
                    Common.rareArmorItemsMap.get(slot).put(
                        lastRareWeights.get(slot),
                        itemStack
                    );
                    lastRareWeights.put(slot, lastRareWeights.get(slot) + weight);
                }
                case EPIC -> {
                    Common.epicArmorItemsMap.get(slot).put(
                        lastEpicWeights.get(slot),
                        itemStack
                    );
                    lastEpicWeights.put(slot, lastEpicWeights.get(slot) + weight);
                }
                case LEGENDARY -> {
                    Common.legendaryArmorItemsMap.get(slot).put(
                        lastLegendaryWeights.get(slot),
                        itemStack
                    );
                    lastLegendaryWeights.put(slot, lastLegendaryWeights.get(slot) + weight);
                }
            }
        });

        for (EquipmentSlot slot : MobChampionBuilder.getArmorSlots()) {
            Common.totalUncommonArmorWeight.put(slot, lastUncommonWeights.get(slot));
            Common.totalRareArmorWeight.put(slot, lastRareWeights.get(slot));
            Common.totalEpicArmorWeight.put(slot, lastEpicWeights.get(slot));
            Common.totalLegendaryArmorWeight.put(slot, lastLegendaryWeights.get(slot));

            if (Services.PLATFORM.isDevelopmentEnvironment()) {
                MobChampions.LOGGER.warn("Configured uncommon armor {} items: {} of: {}", slot, Common.uncommonArmorItemsMap.get(slot), Common.totalUncommonArmorWeight.get(slot));
                MobChampions.LOGGER.warn("Configured rare armor {} items: {} of: {}", slot, Common.rareArmorItemsMap.get(slot), Common.totalRareArmorWeight.get(slot));
                MobChampions.LOGGER.warn("Configured epic armor {} items: {} of: {}", slot, Common.epicArmorItemsMap.get(slot), Common.totalEpicArmorWeight.get(slot));
                MobChampions.LOGGER.warn("Configured legendary armor {} items: {} of: {}", slot, Common.legendaryArmorItemsMap.get(slot), Common.totalLegendaryArmorWeight.get(slot));
            }
        }

    }

    public static class Client {

        private static final String[] colorStrings = new String[]{"#FFDB00", "#F49000", "#D27C00", "#BD6F00"};
        private static final List<String> colorsList = List.of("colors");
        private static final IntList decodedColors = new IntArrayList();
        private static final Predicate<Object> hexValidator = s -> s instanceof String
            && ((String) s).matches("#[a-fA-F\\d]{6}");
        private static final List<String> shapes = Stream.of(FireworkExplosion.Shape.values()).map(Enum::name).toList();


        private final WhiteNoiseConfigSpec.DoubleValue fireworksChance;
        private final WhiteNoiseConfigSpec.ConfigValue<List<? extends String>> fireworksColors;
        private final WhiteNoiseConfigSpec.BooleanValue fireworksFlicker;
        private final WhiteNoiseConfigSpec.BooleanValue fireworksTrail;
        private final WhiteNoiseConfigSpec.EnumValue<FireworkExplosion.Shape> fireworksShape;
        private final WhiteNoiseConfigSpec.IntValue fireworksHeight;

        public Client(WhiteNoiseConfigSpec.Builder builder) {
            builder.push("visuals");


            fireworksChance = builder
                .comment(getTranslation("fireworkschance"))
                .defineInRange("fireworksChance", 1.0, 0, 1.0);
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
                .defineInRange("fireworksHeight", 3, 0, 32);
        }

        public static double fireworksChance() {
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
        private final WhiteNoiseConfigSpec.ConfigValue<String> uncommonColor;
        private final WhiteNoiseConfigSpec.ConfigValue<String> rareColor;
        private final WhiteNoiseConfigSpec.ConfigValue<String> epicColor;
        private final WhiteNoiseConfigSpec.ConfigValue<String> legendaryColor;
        private final WhiteNoiseConfigSpec.BooleanValue fireworksOnDeath;
        private final WhiteNoiseConfigSpec.EnumValue<MobChampion.Rank> fireworksMinimumRank;
        private final WhiteNoiseConfigSpec.BooleanValue disableBabyChampions;
        private final WhiteNoiseConfigSpec.BooleanValue enableSpawnMessage;
        private final WhiteNoiseConfigSpec.EnumValue<MobChampion.Rank> spawnMessageMinimumRank;
        private final WhiteNoiseConfigSpec.IntValue spawnMessageRange;
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
            "minecraft:husk",
            "minecraft:stray",
            "minecraft:wither_skeleton",
            "minecraft:drowned",
            "minecraft:pillager"
        };
        private final Predicate<Object> resourceLocationValidator = s -> s instanceof String
            && ((String) s).matches("[a-z]+[:]{1}[a-z_]+");
        private final WhiteNoiseConfigSpec.ConfigValue<List<? extends String>> spawnTypeBlacklistSource;
        private static final List<Enum<EntitySpawnReason>> spawnReasonBlacklistEnums = new ArrayList<>();
        private static final List<String> spawnTypeBlacklist =  List.of("spawnTypeBlacklist");
        private static final String[] defaultSpawnReasonsBlacklist = {
            EntitySpawnReason.BREEDING.name(),
            EntitySpawnReason.BUCKET.name(),
            EntitySpawnReason.CHUNK_GENERATION.name(),
            EntitySpawnReason.DISPENSER.name(),
            EntitySpawnReason.PATROL.name(),
            EntitySpawnReason.SPAWNER.name(),
            EntitySpawnReason.STRUCTURE.name(),
            EntitySpawnReason.TRIAL_SPAWNER.name()
        };
        private final Predicate<Object> spawnTypeValidator = s -> s instanceof String
            && Arrays.stream(EntitySpawnReason.values())
                .map(Enum::name)
                .anyMatch(name -> name.equals(s));
        private final WhiteNoiseConfigSpec.IntValue uncommonDifficulty;
        private final WhiteNoiseConfigSpec.IntValue rareDifficulty;
        private final WhiteNoiseConfigSpec.IntValue epicDifficulty;
        private final WhiteNoiseConfigSpec.IntValue legendaryDifficulty;
        private final WhiteNoiseConfigSpec.EnumValue<MobChampion.Rank> glowingEffectMinimumRank;
        private final WhiteNoiseConfigSpec.IntValue glowingEffectDuration;
        private final WhiteNoiseConfigSpec.EnumValue<MobChampion.Rank> infestedEffectMinimumRank;
        private final WhiteNoiseConfigSpec.DoubleValue infestedEffectChance;
        private final WhiteNoiseConfigSpec.EnumValue<MobChampion.Rank> weavingEffectMinimumRank;
        private final WhiteNoiseConfigSpec.DoubleValue weavingEffectChance;
        private final WhiteNoiseConfigSpec.EnumValue<MobChampion.Rank> windChargedEffectMinimumRank;
        private final WhiteNoiseConfigSpec.DoubleValue windChargedEffectChance;
        private final WhiteNoiseConfigSpec.EnumValue<MobChampion.Rank> fireResistanceEffectMinimumRank;
        private final WhiteNoiseConfigSpec.DoubleValue fireResistanceEffectChance;
        private final WhiteNoiseConfigSpec.DoubleValue legendaryEffectBonusMultiplier;
        private final WhiteNoiseConfigSpec.DoubleValue creeperExplosionRadiusMultiplier;
        private final WhiteNoiseConfigSpec.DoubleValue uncommonStandardWeaponSpawnChance;
        private final WhiteNoiseConfigSpec.DoubleValue rareStandardWeaponSpawnChance;
        private final WhiteNoiseConfigSpec.DoubleValue epicStandardWeaponSpawnChance;
        private final WhiteNoiseConfigSpec.DoubleValue legendaryStandardWeaponSpawnChance;
        private final WhiteNoiseConfigSpec.DoubleValue uncommonLootableWeaponSpawnChance;
        private final WhiteNoiseConfigSpec.DoubleValue rareLootableWeaponSpawnChance;
        private final WhiteNoiseConfigSpec.DoubleValue epicLootableWeaponSpawnChance;
        private final WhiteNoiseConfigSpec.DoubleValue legendaryLootableWeaponSpawnChance;
        private final WhiteNoiseConfigSpec.ConfigValue<List<? extends String>> weapons;
        private static final List<String> weaponList = List.of("weaponList");
        private static final String[] defaultWeapons = {
            "1-10-minecraft:stone_sword",
            "1-10-minecraft:wooden_sword",
            "1-10-minecraft:stone_axe",
            "1-10-minecraft:wooden_axe",
            "2-10-minecraft:iron_sword",
            "2-10-minecraft:iron_axe",
            "2-20-minecraft:golden_sword",
            "2-20-minecraft:golden_axe",
            "3-20-minecraft:iron_sword",
            "3-20-minecraft:iron_axe",
            "3-10-minecraft:diamond_sword",
            "3-10-minecraft:diamond_axe",
            "4-30-minecraft:diamond_sword",
            "4-30-minecraft:diamond_axe",
            "4-10-minecraft:netherite_sword",
            "4-10-minecraft:netherite_axe"
        };
        private static final Predicate<Object> armorAndEquipmentValidator = s -> s instanceof String
            && ((String) s).matches("[1-4]-[0-9]{0,3}-[a-z]+:[a-z_]+");
        private static int totalUncommonWeaponWeight = 0;
        private static int totalRareWeaponWeight = 0;
        private static int totalEpicWeaponWeight = 0;
        private static int totalLegendaryWeaponWeight = 0;
        private static final NavigableMap<Integer, ItemStack> uncommonWeaponItemsMap = new TreeMap<>();
        private static final NavigableMap<Integer, ItemStack> rareWeaponItemsMap = new TreeMap<>();
        private static final NavigableMap<Integer, ItemStack> epicWeaponItemsMap = new TreeMap<>();
        private static final NavigableMap<Integer, ItemStack> legendaryWeaponItemsMap = new TreeMap<>();
        private final WhiteNoiseConfigSpec.DoubleValue uncommonStandardArmorSpawnChance;
        private final WhiteNoiseConfigSpec.DoubleValue rareStandardArmorSpawnChance;
        private final WhiteNoiseConfigSpec.DoubleValue epicStandardArmorSpawnChance;
        private final WhiteNoiseConfigSpec.DoubleValue legendaryStandardArmorSpawnChance;
        private final WhiteNoiseConfigSpec.DoubleValue uncommonLootableArmorSpawnChance;
        private final WhiteNoiseConfigSpec.DoubleValue rareLootableArmorSpawnChance;
        private final WhiteNoiseConfigSpec.DoubleValue epicLootableArmorSpawnChance;
        private final WhiteNoiseConfigSpec.DoubleValue legendaryLootableArmorSpawnChance;
        private final WhiteNoiseConfigSpec.ConfigValue<List<? extends String>> armors;
        private static final List<String> armorList = List.of("armorList");
        private static final String[] defaultArmors = {
            "1-10-minecraft:golden_helmet",
            "1-10-minecraft:leather_helmet",
            "1-10-minecraft:golden_chestplate",
            "1-10-minecraft:leather_chestplate",
            "1-10-minecraft:golden_leggings",
            "1-10-minecraft:leather_leggings",
            "1-10-minecraft:golden_boots",
            "1-10-minecraft:leather_boots",
            "2-20-minecraft:chainmail_helmet",
            "2-10-minecraft:iron_helmet",
            "2-20-minecraft:chainmail_chestplate",
            "2-10-minecraft:iron_chestplate",
            "2-20-minecraft:chainmail_leggings",
            "2-10-minecraft:iron_leggings",
            "2-20-minecraft:chainmail_boots",
            "2-10-minecraft:iron_boots",
            "3-10-minecraft:diamond_helmet",
            "3-20-minecraft:iron_helmet",
            "3-10-minecraft:diamond_chestplate",
            "3-20-minecraft:iron_chestplate",
            "3-10-minecraft:diamond_leggings",
            "3-20-minecraft:iron_leggings",
            "3-10-minecraft:diamond_boots",
            "3-20-minecraft:iron_boots",
            "4-80-minecraft:diamond_helmet",
            "4-40-minecraft:netherite_helmet",
            "4-10-minecraft:diamond_chestplate",
            "4-5-minecraft:netherite_chestplate",
            "4-60-minecraft:diamond_leggings",
            "4-30-minecraft:netherite_leggings",
            "4-100-minecraft:diamond_boots",
            "4-50-minecraft:netherite_boots"
        };
        private static final Map<EquipmentSlot, Integer> totalUncommonArmorWeight = new HashMap<>();
        private static final Map<EquipmentSlot, Integer> totalRareArmorWeight = new HashMap<>();
        private static final Map<EquipmentSlot, Integer> totalEpicArmorWeight = new HashMap<>();
        private static final Map<EquipmentSlot, Integer> totalLegendaryArmorWeight = new HashMap<>();
        private static final Map<EquipmentSlot, NavigableMap<Integer, ItemStack>> uncommonArmorItemsMap = new HashMap<>();
        private static final Map<EquipmentSlot, NavigableMap<Integer, ItemStack>> rareArmorItemsMap = new HashMap<>();
        private static final Map<EquipmentSlot, NavigableMap<Integer, ItemStack>> epicArmorItemsMap = new HashMap<>();
        private static final Map<EquipmentSlot, NavigableMap<Integer, ItemStack>> legendaryArmorItemsMap = new HashMap<>();
        private final WhiteNoiseConfigSpec.DoubleValue uncommonExperienceMultiplier;
        private final WhiteNoiseConfigSpec.DoubleValue rareExperienceMultiplier;
        private final WhiteNoiseConfigSpec.DoubleValue epicExperienceMultiplier;
        private final WhiteNoiseConfigSpec.DoubleValue legendaryExperienceMultiplier;
        private final WhiteNoiseConfigSpec.DoubleValue standardArmorDropChance;
        private final WhiteNoiseConfigSpec.DoubleValue standardWeaponDropChance;
        private final WhiteNoiseConfigSpec.BooleanValue allowElytraDrops;

        public Common(WhiteNoiseConfigSpec.Builder builder) {

            builder.push("color"); // color

            uncommonColor = builder
                .comment(getTranslation("uncommoncolor"))
                .define("uncommonColor", "#00F403", Client.hexValidator);
            rareColor = builder
                .comment(getTranslation("rarecolor"))
                .define("rareColor", "#3600FF", Client.hexValidator);
            epicColor = builder
                .comment(getTranslation("epiccolor"))
                .define("epicColor", "#EF00F4", Client.hexValidator);
            legendaryColor = builder
                .comment(getTranslation("legendarycolor"))
                .define("legendaryColor", "#F49000", Client.hexValidator);

            builder.pop(); // color

            builder.push("mobdeath").comment(getTranslation("mobdeath")); // mobkill

            fireworksOnDeath = builder
                .comment(getTranslation("fireworksondeath"))
                .define("fireworksOnDeath", true);
            fireworksMinimumRank = builder
                .comment(getTranslation("fireworksminimumrank"))
                .defineEnum("fireworksMinimumRank", MobChampion.Rank.LEGENDARY);

            builder.pop(); // mobkill

            builder.push("spawning").comment(getTranslation("spawning")); // spawning

            disableBabyChampions = builder
                .comment(getTranslation("disablebabychampions"))
                .define("disableBabyChampions", false);
            enableSpawnMessage = builder
                .comment(getTranslation("enablespawnmessage"))
                .define("enableSpawnMessage", true);
            spawnMessageMinimumRank = builder
                .comment(getTranslation("spawnmessageminimumrank"))
                .defineEnum("spawnMessageMinimumRank", MobChampion.Rank.LEGENDARY);
            spawnMessageRange = builder
                .comment(getTranslation("spawnmessagerange"))
                .defineInRange("spawnMessageRange", 64, 0, 256);
            commonMobWeight = builder
                .comment(getTranslation("commonmobweight"))
                .defineInRange("commonMobWeight", 200, 0, 5000);
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
                .defineListAllowEmpty(championWhitelist, getFields(defaultWhitelist), resourceLocationValidator);
            spawnTypeBlacklistSource = builder
                .comment(getTranslation("spawntypeblacklist"))
                .defineListAllowEmpty(spawnTypeBlacklist, getFields(defaultSpawnReasonsBlacklist), spawnTypeValidator);

            builder.push("stats").comment(getTranslation("stats")); // spawning.stats
            uncommonDifficulty = builder
                .comment(getTranslation("uncommondifficulty"))
                .defineInRange("uncommonDifficulty", 5, 1, 10);
            rareDifficulty = builder
                .comment(getTranslation("raredifficulty"))
                .defineInRange("rareDifficulty", 5, 1, 10);
            epicDifficulty = builder
                .comment(getTranslation("epicdifficulty"))
                .defineInRange("epicDifficulty", 5, 1, 10);
            legendaryDifficulty = builder
                .comment(getTranslation("legendarydifficulty"))
                .defineInRange("legendaryDifficulty", 5, 1, 10);
            builder.pop(); // spawning.stats

            builder.push("effects").comment(getTranslation("effects")); // spawning.effects

            glowingEffectMinimumRank = builder
                .comment(getTranslation("glowingeffectminimumrank"))
                .defineEnum("glowingEffectMinimumRank", MobChampion.Rank.LEGENDARY);
            glowingEffectDuration = builder
                .comment(getTranslation("glowingeffectduration"))
                .defineInRange("glowingEffectDuration", 15, -1, 60);
            infestedEffectMinimumRank = builder
                .comment(getTranslation("infestedeffectminimumrank"))
                .defineEnum("infestedEffectMinimumRank", MobChampion.Rank.EPIC);
            infestedEffectChance = builder
                .comment(getTranslation("infestedeffectchance"))
                .defineInRange("infestedEffectChance", 0.35, 0.0, 1.0);
            weavingEffectMinimumRank = builder
                .comment(getTranslation("weavingeffectminimumrank"))
                .defineEnum("weavingEffectMinimumRank", MobChampion.Rank.RARE);
            weavingEffectChance = builder
                .comment(getTranslation("weavingeffectchance"))
                .defineInRange("weavingEffectChance", 0.60, 0.0, 1.0);
            windChargedEffectMinimumRank = builder
                .comment(getTranslation("windchargedeffectminimumrank"))
                .defineEnum("windChargedEffectMinimumRank", MobChampion.Rank.EPIC);
            windChargedEffectChance = builder
                .comment(getTranslation("windchargedeffectchance"))
                .defineInRange("windChargedEffectChance", 0.20, 0.0, 1.0);
            fireResistanceEffectMinimumRank = builder
                .comment(getTranslation("fireresistanceeffectminimumrank"))
                .defineEnum("fireResistanceEffectMinimumRank", MobChampion.Rank.UNCOMMON);
            fireResistanceEffectChance = builder
                .comment(getTranslation("fireresistanceeffectchance"))
                .defineInRange("fireResistanceEffectChance", 1.0, 0.0, 1.0);
            legendaryEffectBonusMultiplier = builder
                .comment(getTranslation("legendaryeffectbonusmultiplier"))
                .defineInRange("legendaryEffectBonusMultiplier", 0.5, 0.0, 1.0);
            creeperExplosionRadiusMultiplier = builder
                .comment(getTranslation("creeperexplosionradiusmultiplier"))
                .defineInRange("creeperExplosionRadiusMultiplier", 0.25, 0.0, 1.0);

            builder.pop(); // spawning.effects
            builder.push("equipment").comment(getTranslation("equipment")); // spawning.equipment

            uncommonStandardWeaponSpawnChance = builder
                .comment(getTranslation("uncommonstandardweaponspawnchance"))
                .defineInRange("uncommonStandardWeaponSpawnChance", 0.7, 0.0, 1.0);
            rareStandardWeaponSpawnChance = builder
                .comment(getTranslation("rarestandardweaponspawnchance"))
                .defineInRange("rareStandardWeaponSpawnChance", 0.8, 0.0, 1.0);
            epicStandardWeaponSpawnChance = builder
                .comment(getTranslation("epicstandardweaponspawnchance"))
                .defineInRange("epicStandardWeaponSpawnChance", 0.9, 0.0, 1.0);
            legendaryStandardWeaponSpawnChance = builder
                .comment(getTranslation("legendarystandardweaponspawnchance"))
                .defineInRange("legendaryStandardWeaponSpawnChance", 0.95, 0.0, 1.0);
            uncommonLootableWeaponSpawnChance = builder
                .comment(getTranslation("uncommonlootableweaponspawnchance"))
                .defineInRange("uncommonLootableWeaponSpawnChance", 0.02, 0.0, 1.0);
            rareLootableWeaponSpawnChance = builder
                .comment(getTranslation("rarelootableweaponspawnchance"))
                .defineInRange("rareLootableWeaponSpawnChance", 0.05, 0.0, 1.0);
            epicLootableWeaponSpawnChance = builder
                .comment(getTranslation("epiclootableweaponspawnchance"))
                .defineInRange("epicLootableWeaponSpawnChance", 0.1, 0.0, 1.0);
            legendaryLootableWeaponSpawnChance = builder
                .comment(getTranslation("legendarylootableweaponspawnchance"))
                .defineInRange("legendaryLootableWeaponSpawnChance", 0.25, 0.0, 1.0);
            weapons = builder
                .comment(getTranslation("weaponlist"))
                .defineListAllowEmpty(weaponList, getFields(defaultWeapons), armorAndEquipmentValidator);
            uncommonStandardArmorSpawnChance = builder
                .comment(getTranslation("uncommonstandardarmorspawnchance"))
                .defineInRange("uncommonStandardArmorSpawnChance", 0.7, 0.0, 1.0);
            rareStandardArmorSpawnChance = builder
                .comment(getTranslation("rarestandardarmorspawnchance"))
                .defineInRange("rareStandardArmorSpawnChance", 0.8, 0.0, 1.0);
            epicStandardArmorSpawnChance = builder
                .comment(getTranslation("epicstandardarmorspawnchance"))
                .defineInRange("epicStandardArmorSpawnChance", 0.9, 0.0, 1.0);
            legendaryStandardArmorSpawnChance = builder
                .comment(getTranslation("legendarystandardarmorspawnchance"))
                .defineInRange("legendaryStandardArmorSpawnChance", 0.95, 0.0, 1.0);
            uncommonLootableArmorSpawnChance = builder
                .comment(getTranslation("uncommonlootablearmorspawnchance"))
                .defineInRange("uncommonLootableArmorSpawnChance", 0.05, 0.0, 1.0);
            rareLootableArmorSpawnChance = builder
                .comment(getTranslation("rarelootablearmorspawnchance"))
                .defineInRange("rareLootableArmorSpawnChance", 0.1, 0.0, 1.0);
            epicLootableArmorSpawnChance = builder
                .comment(getTranslation("epiclootablearmorspawnchance"))
                .defineInRange("epicLootableArmorSpawnChance", 0.25, 0.0, 1.0);
            legendaryLootableArmorSpawnChance = builder
                .comment(getTranslation("legendarylootablearmorspawnchance"))
                .defineInRange("legendaryLootableArmorSpawnChance", 0.50, 0.0, 1.0);
            armors = builder
                .comment(getTranslation("armorlist"))
                .defineListAllowEmpty(armorList, getFields(defaultArmors), armorAndEquipmentValidator);

            builder.pop(); // spawning.equipment
            builder.pop(); // spawning

            builder.push("experience").comment(getTranslation("experience")); // experience

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

            builder.pop(); // experience

            builder.push("lootdrops").comment(getTranslation("lootdrops")); // lootdrops

            standardWeaponDropChance = builder
                .comment(getTranslation("standardweapondropchance"))
                .defineInRange("standardWeaponDropChance", 0.0, 0.0, 1.0);

            standardArmorDropChance = builder
                .comment(getTranslation("standardarmordropchance"))
                .defineInRange("standardArmorDropChance", 0.0, 0.0, 1.0);

            allowElytraDrops = builder
                .comment(getTranslation("allowelytradrops"))
                .define("allowElytraDrops", true);
        }

        private static Supplier<List<? extends String>> getFields(String[] strings) {
            return () -> Arrays.asList(strings);
        }

        public static int getUncommonColor() {
            return ColorHelper.hexToARGB(COMMON.uncommonColor.get());
        }

        public static int getRareColor() {
            return ColorHelper.hexToARGB(COMMON.rareColor.get());
        }

        public static int getEpicColor() {
            return ColorHelper.hexToARGB(COMMON.epicColor.get());
        }

        public static int getLegendaryColor() {
            return ColorHelper.hexToARGB(COMMON.legendaryColor.get());
        }

        public static int getChampionColor(Rank rank) {
            return switch (rank) {
                case RARE -> getRareColor();
                case EPIC -> getEpicColor();
                case LEGENDARY -> getLegendaryColor();
                default -> getUncommonColor();
            };
        }

        public static boolean fireworksOnDeath() {
            return COMMON.fireworksOnDeath.get();
        }

        public static MobChampion.Rank getFireworksMinimumRank() {
            return COMMON.fireworksMinimumRank.get();
        }

        public static boolean isBabyChampionsDisabled() {
            return COMMON.disableBabyChampions.get();
        }

        public static boolean enableSpawnMessage() {
            return COMMON.enableSpawnMessage.get();
        }

        public static int getSpawnMessageMinimumRank() {
            return COMMON.spawnMessageMinimumRank.get().ordinal();
        }

        public static int getSpawnMessageRange() {
            return COMMON.spawnMessageRange.get();
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

        public static List<Enum<EntitySpawnReason>> getSpawnReasonBlacklistEnums() {
            return spawnReasonBlacklistEnums;
        }

        public static int getDifficultyForRank(Rank rank) {
            return switch (rank) {
                case RARE -> COMMON.rareDifficulty.get();
                case EPIC -> COMMON.epicDifficulty.get();
                case LEGENDARY -> COMMON.legendaryDifficulty.get();
                default -> COMMON.uncommonDifficulty.get();
            };
        }

        public static double getProjectileDamageModifier(Rank rank) {
            return ChampionStatsManager.getStatsForRank(rank).getArrowDamageMultiplier();
        }

        public static MobChampion.Rank getGlowingEffectMinimumRank() {
            return COMMON.glowingEffectMinimumRank.get();
        }

        public static int getGlowingEffectDuration() {
            return COMMON.glowingEffectDuration.get() * 20;
        }

        public static MobChampion.Rank getInfestedEffectMinimumRank() {
            return COMMON.infestedEffectMinimumRank.get();
        }

        public static double getInfestedEffectChance() {
            return COMMON.infestedEffectChance.get();
        }

        public static MobChampion.Rank getWeavingEffectMinimumRank() {
            return COMMON.weavingEffectMinimumRank.get();
        }

        public static double getWeavingEffectChance() {
            return COMMON.weavingEffectChance.get();
        }

        public static MobChampion.Rank getWindChargedEffectMinimumRank() {
            return COMMON.windChargedEffectMinimumRank.get();
        }

        public static double getWindChargedEffectChance() {
            return COMMON.windChargedEffectChance.get();
        }

        public static MobChampion.Rank getFireResistanceEffectMinimumRank() {
            return COMMON.fireResistanceEffectMinimumRank.get();
        }

        public static double getFireResistanceEffectChance() {
            return COMMON.fireResistanceEffectChance.get();
        }

        public static double getLegendaryEffectBonusMultiplier() {
            return COMMON.legendaryEffectBonusMultiplier.get();
        }

        public static double getCreeperExplosionRadiusMultiplier(Rank rank) {
            return 1 + COMMON.creeperExplosionRadiusMultiplier.get() * rank.ordinal();
        }

        public static ItemStack getWeaponForRank(Rank rank) {
            int randomWeight;

            if (MobChampions.RANDOM.nextFloat() > getStandardWeaponSpawnChanceForRank(rank)) {
                return null;
            }

            switch (rank) {
                case UNCOMMON -> {
                    randomWeight = MobChampions.RANDOM.nextInt(Common.totalUncommonWeaponWeight);
                    return getRandomUncommonWeaponItem(randomWeight);
                }
                case RARE -> {
                    randomWeight = MobChampions.RANDOM.nextInt(Common.totalRareWeaponWeight);
                    return getRandomRareWeaponItem(randomWeight);
                }
                case EPIC -> {
                    randomWeight = MobChampions.RANDOM.nextInt(Common.totalEpicWeaponWeight);
                    return getRandomEpicWeaponItem(randomWeight);
                }
                case LEGENDARY -> {
                    randomWeight = MobChampions.RANDOM.nextInt(Common.totalLegendaryWeaponWeight);
                    return getRandomLegendaryWeaponItem(randomWeight);
                }
                default -> {
                    return null;
                }
            }
        }

        private static double getStandardWeaponSpawnChanceForRank(Rank rank) {
            return switch (rank) {
                case UNCOMMON -> COMMON.uncommonStandardWeaponSpawnChance.get();
                case RARE -> COMMON.rareStandardWeaponSpawnChance.get();
                case EPIC -> COMMON.epicStandardWeaponSpawnChance.get();
                case LEGENDARY -> COMMON.legendaryStandardWeaponSpawnChance.get();
                default -> 0.0;
            };
        }

        public static double getLootableWeaponSpawnChanceForRank(Rank rank) {
            return switch (rank) {
                case UNCOMMON -> COMMON.uncommonLootableWeaponSpawnChance.get();
                case RARE -> COMMON.rareLootableWeaponSpawnChance.get();
                case EPIC -> COMMON.epicLootableWeaponSpawnChance.get();
                case LEGENDARY -> COMMON.legendaryLootableWeaponSpawnChance.get();
                default -> 0.0;
            };
        }

        public static ItemStack getRandomUncommonWeaponItem(int weight) {
            return uncommonWeaponItemsMap.floorEntry(weight).getValue();
        }

        public static ItemStack getRandomRareWeaponItem(int weight) {
            return rareWeaponItemsMap.floorEntry(weight).getValue();
        }

        public static ItemStack getRandomEpicWeaponItem(int weight) {
            return epicWeaponItemsMap.floorEntry(weight).getValue();
        }

        public static ItemStack getRandomLegendaryWeaponItem(int weight) {
            return legendaryWeaponItemsMap.floorEntry(weight).getValue();
        }

        public static ItemStack getArmorForRankAndSlot(Rank rank, EquipmentSlot slot) {
            int randomWeight;

            if (MobChampions.RANDOM.nextFloat() > getStandardArmorSpawnChanceForRank(rank)) {
                return null;
            }

            switch (rank) {
                case UNCOMMON -> {
                    randomWeight = MobChampions.RANDOM.nextInt(Common.totalUncommonArmorWeight.get(slot));
                    return getRandomUncommonArmorItem(slot, randomWeight);
                }
                case RARE -> {
                    randomWeight = MobChampions.RANDOM.nextInt(Common.totalRareArmorWeight.get(slot));
                    return getRandomRareArmorItem(slot, randomWeight);
                }
                case EPIC -> {
                    randomWeight = MobChampions.RANDOM.nextInt(Common.totalEpicArmorWeight.get(slot));
                    return getRandomEpicArmorItem(slot, randomWeight);
                }
                case LEGENDARY -> {
                    randomWeight = MobChampions.RANDOM.nextInt(Common.totalLegendaryArmorWeight.get(slot));
                    return getRandomLegendaryArmorItem(slot, randomWeight);
                }
                default -> {
                    return null;
                }
            }
        }

        private static double getStandardArmorSpawnChanceForRank(Rank rank) {
            return switch (rank) {
                case UNCOMMON -> COMMON.uncommonStandardArmorSpawnChance.get();
                case RARE -> COMMON.rareStandardArmorSpawnChance.get();
                case EPIC -> COMMON.epicStandardArmorSpawnChance.get();
                case LEGENDARY -> COMMON.legendaryStandardArmorSpawnChance.get();
                default -> 0.0F;
            };
        }

        public static double getLootableArmorSpawnChanceForRank(Rank rank) {
            return switch (rank) {
                case UNCOMMON -> COMMON.uncommonLootableArmorSpawnChance.get();
                case RARE -> COMMON.rareLootableArmorSpawnChance.get();
                case EPIC -> COMMON.epicLootableArmorSpawnChance.get();
                case LEGENDARY -> COMMON.legendaryLootableArmorSpawnChance.get();
                default -> 0.0F;
            };
        }

        public static ItemStack getRandomUncommonArmorItem(EquipmentSlot slot, int weight) {
            return uncommonArmorItemsMap.get(slot).floorEntry(weight).getValue();
        }

        public static ItemStack getRandomRareArmorItem(EquipmentSlot slot, int weight) {
            return rareArmorItemsMap.get(slot).floorEntry(weight).getValue();
        }

        public static ItemStack getRandomEpicArmorItem(EquipmentSlot slot, int weight) {
            return epicArmorItemsMap.get(slot).floorEntry(weight).getValue();
        }

        public static ItemStack getRandomLegendaryArmorItem(EquipmentSlot slot, int weight) {
            return legendaryArmorItemsMap.get(slot).floorEntry(weight).getValue();
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

        public static double getStandardWeaponDropChance() {
            return COMMON.standardWeaponDropChance.get();
        }

        public static double getStandardArmorDropChance() {
            return COMMON.standardArmorDropChance.get();
        }

        public static boolean allowElytraDrops() {
            return COMMON.allowElytraDrops.get();
        }

        /*
         * Calculate the chance that a mob champion of the given rank will drop loot.
         * This is calculated as:
         * 1 - (rank weight / total weight)
         * So rarity increases the chance of dropping loot.
         */
        public static float getLootDropChance(Rank rank) {
             int weight = switch (rank) {
                case UNCOMMON -> COMMON.uncommonWeight.get();
                case RARE -> COMMON.rareWeight.get();
                case EPIC -> COMMON.epicWeight.get();
                case LEGENDARY -> COMMON.legendaryWeight.get();
                default -> 0;
             };
             int totalWeight = Common.getTotalWeight();

             if (weight == 0 || totalWeight == 0) {
                return 0F;
             }
             else if (weight >= totalWeight) {
                return 1F;
             }

             return 1F - ((float) weight / (float) totalWeight);
        }

    }

    private static String getTranslation(String key) {
        return Translations.get(key);
    }

    private static String getTranslation(String key, String... values) {
        return Translations.get(key, values);
    }

}
