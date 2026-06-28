package mobchampions.data.loot;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import org.jspecify.annotations.NonNull;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorMaterials;
import net.minecraft.world.item.equipment.trim.ArmorTrim;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import net.minecraft.world.item.equipment.trim.TrimMaterials;
import net.minecraft.world.item.equipment.trim.TrimPattern;
import net.minecraft.world.item.equipment.trim.TrimPatterns;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import technology.roughness.whitenoise.util.ColorHelper;

import mobchampions.loot.MobChampionsLootTables;
import mobchampions.util.LootTableHelper;
import mobchampions.util.LootTableHelper.ItemEnchantment;

public class MobChampionsWearableLoot implements LootTableSubProvider {

    private final HolderLookup.RegistryLookup<Enchantment> enchantmentRegistryLookup;
    private final HolderLookup.RegistryLookup<TrimPattern> patternRegistry;
    private final HolderLookup.RegistryLookup<TrimMaterial> materialRegistry;

    private final Map<ArmorMaterial, List<Item>> armorItems = Map.of(
        ArmorMaterials.LEATHER, List.of(Items.LEATHER_HELMET, Items.LEATHER_CHESTPLATE, Items.LEATHER_LEGGINGS, Items.LEATHER_BOOTS),
        ArmorMaterials.CHAINMAIL, List.of(Items.CHAINMAIL_HELMET, Items.CHAINMAIL_CHESTPLATE, Items.CHAINMAIL_LEGGINGS, Items.CHAINMAIL_BOOTS),
        ArmorMaterials.IRON, List.of(Items.IRON_HELMET, Items.IRON_CHESTPLATE, Items.IRON_LEGGINGS, Items.IRON_BOOTS),
        ArmorMaterials.GOLD, List.of(Items.GOLDEN_HELMET, Items.GOLDEN_CHESTPLATE, Items.GOLDEN_LEGGINGS, Items.GOLDEN_BOOTS),
        ArmorMaterials.DIAMOND, List.of(Items.DIAMOND_HELMET, Items.DIAMOND_CHESTPLATE, Items.DIAMOND_LEGGINGS, Items.DIAMOND_BOOTS),
        ArmorMaterials.NETHERITE, List.of(Items.NETHERITE_HELMET, Items.NETHERITE_CHESTPLATE, Items.NETHERITE_LEGGINGS, Items.NETHERITE_BOOTS)
    );

    public MobChampionsWearableLoot(HolderLookup.Provider lookupProvider) {
        this.enchantmentRegistryLookup = lookupProvider.lookupOrThrow(Registries.ENCHANTMENT);
        this.patternRegistry = lookupProvider.lookupOrThrow(Registries.TRIM_PATTERN);
        this.materialRegistry = lookupProvider.lookupOrThrow(Registries.TRIM_MATERIAL);
    }

    @Override
    public void generate(@NonNull BiConsumer<ResourceKey<LootTable>, Builder> consumer) {
        generateUncommonWearableLoot(consumer);
        generateRareWearableLoot(consumer);
        generateEpicWearableLoot(consumer);
        generateLegendaryWearableLoot(consumer);
    }

    private void generateUncommonWearableLoot(@NonNull BiConsumer<ResourceKey<LootTable>, Builder> consumer) {
        LootPool.Builder lootPool = LootPool.lootPool().setRolls(ConstantValue.exactly(1))
            .add(
                LootTableHelper.addDyedItem(
                    Items.LEATHER_HELMET,
                    "propeller_hat",
                    100,
                    ChatFormatting.GREEN,
                    DyeColor.RED.getTextColor(),
                    List.of(
                        new ItemEnchantment(Enchantments.FEATHER_FALLING, UniformGenerator.between(1, 4))
                    ),
                    enchantmentRegistryLookup
                )
            )
            .add(
                LootTableHelper.addItem(
                    Items.GOLDEN_CHESTPLATE,
                    "cactus_armor",
                    100,
                    ChatFormatting.DARK_GREEN,
                    List.of(
                        new ItemEnchantment(Enchantments.THORNS, UniformGenerator.between(2, 5))
                    ),
                    enchantmentRegistryLookup
                )
            );
        List<? extends LootPoolSingletonContainer.Builder<?>> leatherArmorSet = LootTableHelper.createDyedArmorSet(
            getTrim(
                TrimPatterns.WILD,
                TrimMaterials.COPPER
            ),
            "wildling",
            80,
            ChatFormatting.DARK_GREEN,
            armorItems.get(ArmorMaterials.LEATHER),
            List.of(
                new ItemEnchantment(Enchantments.PROTECTION, UniformGenerator.between(1, 2)),
                new ItemEnchantment(Enchantments.UNBREAKING, UniformGenerator.between(1, 2))
            ),
            enchantmentRegistryLookup,
            ColorHelper.Colors.DARK_GREEN.toRGBA()
        );
        for (LootPoolSingletonContainer.Builder<?> armorPiece : leatherArmorSet) {
            lootPool.add(armorPiece);
        }
        consumer.accept(
            MobChampionsLootTables.UNCOMMON_WEARABLE_LOOT,
            LootTable.lootTable().withPool(lootPool)
        );
    }

    private void generateRareWearableLoot(@NonNull BiConsumer<ResourceKey<LootTable>, Builder> consumer) {
        LootPool.Builder lootPool = LootPool.lootPool().setRolls(ConstantValue.exactly(1))
            .add(
                LootTableHelper.addItem(
                    Items.GOLDEN_CHESTPLATE,
                    "sticky_chestplate",
                    80,
                    ChatFormatting.YELLOW,
                    List.of(
                        new ItemEnchantment(Enchantments.PROTECTION, UniformGenerator.between(4, 7)),
                        new ItemEnchantment(Enchantments.BINDING_CURSE, ConstantValue.exactly(1))
                    ),
                    enchantmentRegistryLookup
                )
            )
            .add(
                LootTableHelper.addItem(
                    Items.GOLDEN_LEGGINGS,
                    "sticky_leggings",
                    80,
                    ChatFormatting.YELLOW,
                    List.of(
                        new ItemEnchantment(Enchantments.PROTECTION, UniformGenerator.between(4, 7)),
                        new ItemEnchantment(Enchantments.BINDING_CURSE, ConstantValue.exactly(1))
                    ),
                    enchantmentRegistryLookup
                )
            )
            .add(
                LootTableHelper.addItem(
                    Items.IRON_BOOTS,
                    "long_fall_boots",
                    80,
                    ChatFormatting.YELLOW,
                    List.of(
                        new ItemEnchantment(Enchantments.FEATHER_FALLING, UniformGenerator.between(5, 6))
                    ),
                    enchantmentRegistryLookup
                )
            )
            .add(
                LootTableHelper.addItem(
                    Items.CHAINMAIL_BOOTS,
                    "soul_walkers",
                    100,
                    ChatFormatting.GRAY,
                    List.of(
                        new ItemEnchantment(Enchantments.SOUL_SPEED, UniformGenerator.between(4, 5)),
                        new ItemEnchantment(Enchantments.UNBREAKING, ConstantValue.exactly(2))
                    ),
                    enchantmentRegistryLookup
                )
            )
            .add(
                LootTableHelper.addItem(
                    Items.LEATHER_BOOTS,
                    "winter_flippers",
                    90,
                    ChatFormatting.DARK_AQUA,
                    List.of(
                        new ItemEnchantment(Enchantments.FROST_WALKER, UniformGenerator.between(1, 2)),
                        new ItemEnchantment(Enchantments.DEPTH_STRIDER, UniformGenerator.between(1, 3))
                    ),
                    enchantmentRegistryLookup
                )
            )
            .add(
                LootTableHelper.addItem(
                    Items.TURTLE_HELMET,
                    "diving_helmet",
                    100,
                    ChatFormatting.AQUA,
                    List.of(
                        new ItemEnchantment(Enchantments.RESPIRATION, UniformGenerator.between(2, 4)),
                        new ItemEnchantment(Enchantments.DEPTH_STRIDER, UniformGenerator.between(1, 3))
                    ),
                    enchantmentRegistryLookup
                )
            )
            .add(
                LootTableHelper.addItem(
                    Items.CHAINMAIL_CHESTPLATE,
                    "bulletproof_vest",
                    100,
                    ChatFormatting.DARK_RED,
                    List.of(
                        new ItemEnchantment(Enchantments.PROJECTILE_PROTECTION, UniformGenerator.between(4, 6))
                    ),
                    enchantmentRegistryLookup
                )
            );
        List<? extends LootPoolSingletonContainer.Builder<?>> ironArmorSet = LootTableHelper.createArmorSet(
            getTrim(
                TrimPatterns.SENTRY,
                TrimMaterials.EMERALD
            ),
            "knight",
            80,
            ChatFormatting.GRAY,
            armorItems.get(ArmorMaterials.IRON),
            List.of(
                new ItemEnchantment(Enchantments.PROTECTION, UniformGenerator.between(3, 4)),
                new ItemEnchantment(Enchantments.UNBREAKING, UniformGenerator.between(3, 4))
            ),
            enchantmentRegistryLookup
        );
        for (LootPoolSingletonContainer.Builder<?> armorPiece : ironArmorSet) {
            lootPool.add(armorPiece);
        }
        consumer.accept(
            MobChampionsLootTables.RARE_WEARABLE_LOOT,
            LootTable.lootTable().withPool(lootPool)
        );
    }

    private void generateEpicWearableLoot(@NonNull BiConsumer<ResourceKey<LootTable>, Builder> consumer) {
        LootPool.Builder lootPool = LootPool.lootPool().setRolls(ConstantValue.exactly(1));

        List<? extends LootPoolSingletonContainer.Builder<?>> diamondArmorSet = LootTableHelper.createArmorSet(
            getTrim(
                TrimPatterns.WARD,
                TrimMaterials.QUARTZ
            ),
            "shiny",
            20,
            ChatFormatting.DARK_AQUA,
            armorItems.get(ArmorMaterials.DIAMOND),
            List.of(
                new ItemEnchantment(Enchantments.PROTECTION, UniformGenerator.between(4, 5)),
                new ItemEnchantment(Enchantments.BLAST_PROTECTION, UniformGenerator.between(4, 5)),
                new ItemEnchantment(Enchantments.FIRE_PROTECTION, UniformGenerator.between(4, 5)),
                new ItemEnchantment(Enchantments.UNBREAKING, UniformGenerator.between(4, 5))
            ),
            enchantmentRegistryLookup
        );
        for (LootPoolSingletonContainer.Builder<?> armorPiece : diamondArmorSet) {
            lootPool.add(armorPiece);
        }
        consumer.accept(
            MobChampionsLootTables.EPIC_WEARABLE_LOOT,
            LootTable.lootTable().withPool(lootPool)
        );
    }

    private void generateLegendaryWearableLoot(@NonNull BiConsumer<ResourceKey<LootTable>, Builder> consumer) {
        LootPool.Builder lootPool = LootPool.lootPool().setRolls(ConstantValue.exactly(1))
            .add(
                LootTableHelper.addItem(
                    Items.ELYTRA,
                    "plated_elytra",
                    10,
                    ChatFormatting.WHITE,
                    List.of(
                        new ItemEnchantment(Enchantments.PROTECTION, UniformGenerator.between(2, 4))
                    ),
                    enchantmentRegistryLookup
                )
            )
            .add(
                LootTableHelper.addItem(
                    Items.DIAMOND_CHESTPLATE,
                    "warding_chestplate",
                    20,
                    ChatFormatting.LIGHT_PURPLE,
                    List.of(
                        new ItemEnchantment(Enchantments.PROTECTION, UniformGenerator.between(2, 4)),
                        new ItemEnchantment(Enchantments.BLAST_PROTECTION, UniformGenerator.between(2, 4)),
                        new ItemEnchantment(Enchantments.PROJECTILE_PROTECTION, UniformGenerator.between(2, 4)),
                        new ItemEnchantment(Enchantments.FIRE_PROTECTION, UniformGenerator.between(2, 4))
                    ),
                    enchantmentRegistryLookup
                )
            );
        List<? extends LootPoolSingletonContainer.Builder<?>> netheriteArmorSet = LootTableHelper.createArmorSet(
            getTrim(
                TrimPatterns.SNOUT,
                TrimMaterials.GOLD
            ),
            "champions",
            10,
            ChatFormatting.GOLD,
            armorItems.get(ArmorMaterials.NETHERITE),
            List.of(
                new ItemEnchantment(Enchantments.PROTECTION, UniformGenerator.between(6, 7)),
                new ItemEnchantment(Enchantments.BLAST_PROTECTION, UniformGenerator.between(6, 7)),
                new ItemEnchantment(Enchantments.FIRE_PROTECTION, UniformGenerator.between(6, 7)),
                new ItemEnchantment(Enchantments.PROJECTILE_PROTECTION, UniformGenerator.between(6, 7)),
                new ItemEnchantment(Enchantments.UNBREAKING, UniformGenerator.between(6, 7))
            ),
            enchantmentRegistryLookup
        );
        for (LootPoolSingletonContainer.Builder<?> armorPiece : netheriteArmorSet) {
            lootPool.add(armorPiece);
        }
        consumer.accept(
            MobChampionsLootTables.LEGENDARY_WEARABLE_LOOT,
            LootTable.lootTable().withPool(lootPool)
        );
    }

    private ArmorTrim getTrim(ResourceKey<TrimPattern> pattern, ResourceKey<TrimMaterial> material) {
        Holder.Reference<TrimPattern> tidePattern = patternRegistry.get(pattern).orElseThrow();
        Holder.Reference<TrimMaterial> goldMaterial = materialRegistry.get(material).orElseThrow();
        return new ArmorTrim(goldMaterial, tidePattern);
    }

}
