package mobchampions.data.loot;

import java.util.List;
import java.util.function.BiConsumer;

import org.jetbrains.annotations.NotNull;

import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BannerPatterns;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.SetBannerPatternFunction;
import net.minecraft.world.level.storage.loot.functions.SetComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetNameFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import mobchampions.loot.MobChampionsLootTables;
import mobchampions.util.LootTableHelper;
import mobchampions.util.LootTableHelper.ItemEnchantment;
import mobchampions.util.TranslationHelper;

public class MobChampionsWeaponLoot implements LootTableSubProvider {

    private final HolderLookup.RegistryLookup<Enchantment> enchantmentRegistryLookup;
    private final HolderLookup.RegistryLookup<BannerPattern> bannerPatternLookup;

    public MobChampionsWeaponLoot(HolderLookup.Provider lookupProvider) {
        this.enchantmentRegistryLookup = lookupProvider.lookupOrThrow(Registries.ENCHANTMENT);
        this.bannerPatternLookup = lookupProvider.lookupOrThrow(Registries.BANNER_PATTERN);
    }

    @Override
    public void generate(@NotNull BiConsumer<ResourceKey<LootTable>, Builder> consumer) {
        generateUncommonWeaponLoot(consumer);
        generateRareWeaponLoot(consumer);
        generateEpicWeaponLoot(consumer);
        generateLegendaryWeaponLoot(consumer);
    }

    private void generateUncommonWeaponLoot(BiConsumer<ResourceKey<LootTable>, Builder> consumer) {
        LootPool.Builder lootPool = LootPool.lootPool().setRolls(ConstantValue.exactly(1))
            .add(
                addShieldItem(
                    "riot_shield",
                    100,
                    ChatFormatting.BLUE,
                    DyeColor.WHITE,
                    List.of(
                        new ShieldPattern(BannerPatterns.GRADIENT_UP, DyeColor.CYAN),
                        new ShieldPattern(BannerPatterns.HALF_HORIZONTAL_MIRROR, DyeColor.LIGHT_GRAY),
                        new ShieldPattern(BannerPatterns.STRIPE_MIDDLE, DyeColor.GRAY),
                        new ShieldPattern(BannerPatterns.BORDER, DyeColor.GRAY)
                    ),
                    List.of(
                        new ItemEnchantment(Enchantments.UNBREAKING, UniformGenerator.between(3, 5))
                    )
                )
            )
            .add(
                LootTableHelper.addItem(
                    Items.STICK,
                    "knockback_stick",
                    100,
                    ChatFormatting.GOLD,
                    List.of(
                        new ItemEnchantment(Enchantments.KNOCKBACK, UniformGenerator.between(2, 4))
                    ),
                    enchantmentRegistryLookup
                )
            )
            .add(
                LootTableHelper.addItem(
                    Items.GOLDEN_AXE,
                    "greed",
                    80,
                    ChatFormatting.GOLD,
                    List.of(
                        new ItemEnchantment(Enchantments.LOOTING, UniformGenerator.between(1, 4))
                    ),
                    enchantmentRegistryLookup
                )
            )
            .add(
                LootTableHelper.addItem(
                    Items.WOODEN_SWORD,
                    "matchstick",
                    100,
                    ChatFormatting.RED,
                    List.of(
                        new ItemEnchantment(Enchantments.FIRE_ASPECT, UniformGenerator.between(1, 4))
                    ),
                    enchantmentRegistryLookup
                )
            )
            .add(
                LootTableHelper.addItem(
                    Items.CROSSBOW,
                    "multiplier",
                    90,
                    ChatFormatting.LIGHT_PURPLE,
                    List.of(
                        new ItemEnchantment(Enchantments.PIERCING, UniformGenerator.between(1, 4)),
                        new ItemEnchantment(Enchantments.MULTISHOT, ConstantValue.exactly(1))
                    ),
                    enchantmentRegistryLookup
                )
            )
            .add(
                LootTableHelper.addItem(
                    Items.IRON_AXE,
                    "mjolnir",
                    90,
                    ChatFormatting.YELLOW,
                    List.of(
                        new ItemEnchantment(Enchantments.SMITE, UniformGenerator.between(2, 5))
                    ),
                    enchantmentRegistryLookup
                )
            )
            .add(
                LootTableHelper.addItem(
                    Items.STONE_AXE,
                    "primitive_chainsaw",
                    90,
                    ChatFormatting.DARK_GRAY,
                    List.of(
                        new ItemEnchantment(Enchantments.EFFICIENCY, UniformGenerator.between(3, 6))
                    ),
                    enchantmentRegistryLookup
                )
            )
            .add(
                LootTableHelper.addItem(
                    Items.GOLDEN_PICKAXE,
                    "primitive_mining_drill",
                    90,
                    ChatFormatting.GRAY,
                    List.of(
                        new ItemEnchantment(Enchantments.EFFICIENCY, UniformGenerator.between(3, 6))
                    ),
                    enchantmentRegistryLookup
                )
            );
        consumer.accept(
            MobChampionsLootTables.UNCOMMON_WEAPON_LOOT,
            LootTable.lootTable().withPool(lootPool)
        );
    }

    private void generateRareWeaponLoot(BiConsumer<ResourceKey<LootTable>, Builder> consumer) {
        LootPool.Builder lootPool = LootPool.lootPool().setRolls(ConstantValue.exactly(1))
            .add(
                LootTableHelper.addItem(
                    Items.IRON_SWORD,
                    "apocalypse_blade",
                    100,
                    ChatFormatting.RED,
                    List.of(
                        new ItemEnchantment(Enchantments.SHARPNESS, UniformGenerator.between(2, 5)),
                        new ItemEnchantment(Enchantments.SMITE, UniformGenerator.between(2, 5))
                    ),
                    enchantmentRegistryLookup
                )
            )
            .add(
                LootTableHelper.addItem(
                    Items.IRON_AXE,
                    "chainsaw",
                    90,
                    ChatFormatting.DARK_GRAY,
                    List.of(
                        new ItemEnchantment(Enchantments.EFFICIENCY, UniformGenerator.between(5, 6)),
                        new ItemEnchantment(Enchantments.UNBREAKING, UniformGenerator.between(3, 5))
                    ),
                    enchantmentRegistryLookup
                )
            )
            .add(
                LootTableHelper.addItem(
                    Items.IRON_PICKAXE,
                    "mining_drill",
                    90,
                    ChatFormatting.GRAY,
                    List.of(
                        new ItemEnchantment(Enchantments.EFFICIENCY, UniformGenerator.between(5, 6)),
                        new ItemEnchantment(Enchantments.UNBREAKING, UniformGenerator.between(3, 5))
                    ),
                    enchantmentRegistryLookup
                )
            )
            .add(
                LootTableHelper.addItem(
                    Items.IRON_PICKAXE,
                    "gem_magnet",
                    70,
                    ChatFormatting.AQUA,
                    List.of(
                        new ItemEnchantment(Enchantments.FORTUNE, UniformGenerator.between(2, 4))
                    ),
                    enchantmentRegistryLookup
                )
            );
        consumer.accept(
            MobChampionsLootTables.RARE_WEAPON_LOOT,
            LootTable.lootTable().withPool(lootPool)
        );
    }

    private void generateEpicWeaponLoot(BiConsumer<ResourceKey<LootTable>, Builder> consumer) {
        LootPool.Builder lootPool = LootPool.lootPool().setRolls(ConstantValue.exactly(1))
            .add(
                LootTableHelper.addItem(
                    Items.BOW,
                    "cornucopia_bow",
                    30,
                    ChatFormatting.DARK_GREEN,
                    List.of(
                        new ItemEnchantment(Enchantments.LOOTING, UniformGenerator.between(2, 4)),
                        new ItemEnchantment(Enchantments.PUNCH, UniformGenerator.between(1, 2))
                    ),
                    enchantmentRegistryLookup
                )
            )
            .add(
                addShieldItem(
                    "basher",
                    30,
                    ChatFormatting.GOLD,
                    DyeColor.YELLOW,
                    List.of(
                        new ShieldPattern(BannerPatterns.PIGLIN, DyeColor.ORANGE),
                        new ShieldPattern(BannerPatterns.BORDER, DyeColor.ORANGE)
                    ),
                    List.of(
                        new ItemEnchantment(Enchantments.UNBREAKING, ConstantValue.exactly(5)),
                        new ItemEnchantment(Enchantments.MENDING, ConstantValue.exactly(1))
                    )
                )
            )
            .add(
                LootTableHelper.addItem(
                    Items.TRIDENT,
                    "ripper",
                    20,
                    ChatFormatting.DARK_PURPLE,
                    List.of(
                        new ItemEnchantment(Enchantments.RIPTIDE, UniformGenerator.between(3, 5))
                    ),
                    enchantmentRegistryLookup
                )
            )
            .add(
                LootTableHelper.addItem(
                    Items.NETHERITE_HOE,
                    "reapers_scythe",
                    70,
                    ChatFormatting.RED,
                    List.of(
                        new ItemEnchantment(Enchantments.SHARPNESS, UniformGenerator.between(3, 6)),
                        new ItemEnchantment(Enchantments.VANISHING_CURSE, ConstantValue.exactly(1))
                    ),
                    enchantmentRegistryLookup
                )
            )
            .add(
                LootTableHelper.addItem(
                    Items.TRIDENT,
                    "poseidons_trident",
                    30,
                    ChatFormatting.DARK_AQUA,
                    List.of(
                        new ItemEnchantment(Enchantments.SHARPNESS, UniformGenerator.between(2, 5)),
                        new ItemEnchantment(Enchantments.LOYALTY, UniformGenerator.between(1, 3))
                    ),
                    enchantmentRegistryLookup
                )
            );
        consumer.accept(
            MobChampionsLootTables.EPIC_WEAPON_LOOT,
            LootTable.lootTable().withPool(lootPool)
        );
    }

    private void generateLegendaryWeaponLoot(BiConsumer<ResourceKey<LootTable>, Builder> consumer) {
        LootPool.Builder lootPool = LootPool.lootPool().setRolls(ConstantValue.exactly(1))
            .add(
                LootTableHelper.addItem(
                    Items.NETHERITE_SWORD,
                    "excalibur",
                    10,
                    ChatFormatting.GOLD,
                    List.of(
                        new ItemEnchantment(Enchantments.SHARPNESS, ConstantValue.exactly(5)),
                        new ItemEnchantment(Enchantments.UNBREAKING, ConstantValue.exactly(5)),
                        new ItemEnchantment(Enchantments.MENDING, ConstantValue.exactly(1))
                    ),
                    enchantmentRegistryLookup
                )
            )
            .add(
                LootTableHelper.addItem(
                    Items.BOW,
                    "robins_bow",
                    20,
                    ChatFormatting.DARK_GREEN,
                    List.of(
                        new ItemEnchantment(Enchantments.MENDING, ConstantValue.exactly(1)),
                        new ItemEnchantment(Enchantments.LOOTING, UniformGenerator.between(3, 4)),
                        new ItemEnchantment(Enchantments.INFINITY, ConstantValue.exactly(1))
                    ),
                    enchantmentRegistryLookup
                )
            );
        consumer.accept(
            MobChampionsLootTables.LEGENDARY_WEAPON_LOOT,
            LootTable.lootTable().withPool(lootPool)
        );
    }

    private LootPoolSingletonContainer.Builder<?> addShieldItem(String id, int weight, ChatFormatting color,
            DyeColor baseColor, List<ShieldPattern> patterns, List<ItemEnchantment> enchantments) {
        LootPoolSingletonContainer.Builder<?> builder = LootItem.lootTableItem(Items.SHIELD).apply(
                SetNameFunction.setName(
                    TranslationHelper.getLootComponent(id, color),
                    SetNameFunction.Target.ITEM_NAME
                )
            )
            .apply(SetComponentsFunction.setComponent(DataComponents.BASE_COLOR, baseColor))
            .setWeight(weight);

        builder = addShieldPatterns(builder, patterns);
        builder = LootTableHelper.addItemEnchantments(builder, enchantmentRegistryLookup, enchantments);

        return LootTableHelper.addLore(builder);
    }

    private LootPoolSingletonContainer.Builder<?> addShieldPatterns(LootPoolSingletonContainer.Builder<?> builder,
            List<ShieldPattern> patterns) {
        SetBannerPatternFunction.Builder patternBuilder = SetBannerPatternFunction.setBannerPattern(true);
        for (ShieldPattern shieldPattern : patterns) {
            patternBuilder.addPattern(
                bannerPatternLookup.getOrThrow(shieldPattern.pattern),
                shieldPattern.color
            );
        }

        builder = builder.apply(patternBuilder);

        return builder;
    }

    private static class ShieldPattern {
        public ResourceKey<BannerPattern> pattern;
        public DyeColor color;

        public ShieldPattern(ResourceKey<BannerPattern> pattern, DyeColor color) {
            this.pattern = pattern;
            this.color = color;
        }
    }

}
