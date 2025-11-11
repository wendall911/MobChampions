package mobchampions.data.loot;

import java.util.List;
import java.util.function.BiConsumer;

import org.jetbrains.annotations.NotNull;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetNameFunction;
import net.minecraft.world.level.storage.loot.functions.SetNameFunction.Target;
import net.minecraft.world.level.storage.loot.functions.SetPotionFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import mobchampions.loot.MobChampionsLootTables;
import mobchampions.util.LootTableHelper;
import mobchampions.util.TranslationHelper;

public class MobChampionsGenericLoot implements LootTableSubProvider {

    private final HolderLookup.Provider lookupProvider;
    private final HolderLookup.RegistryLookup<Enchantment> enchantmentRegistryLookup;

    public MobChampionsGenericLoot(HolderLookup.Provider lookupProvider) {
        this.lookupProvider = lookupProvider;
        this.enchantmentRegistryLookup = lookupProvider.lookupOrThrow(Registries.ENCHANTMENT);
    }

    @Override
    public void generate(@NotNull BiConsumer<ResourceKey<LootTable>, Builder> consumer) {
        generateUncommonGenericLoot(consumer);
        generateRareGenericLoot(consumer);
        generateEpicGenericLoot(consumer);
        generateLegendaryGenericLoot(consumer);
    }

    private void generateUncommonGenericLoot(BiConsumer<ResourceKey<LootTable>, Builder> consumer) {
        consumer.accept(
            MobChampionsLootTables.UNCOMMON_GENERIC_LOOT,
            LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                    .add(
                        createRandomBook(25)
                    )
                    .add(
                        createPotion(
                            "medkit",
                            75,
                            ChatFormatting.LIGHT_PURPLE,
                            Potions.STRONG_HEALING,
                            List.of(
                                new MobEffectInstance(MobEffects.REGENERATION, 200, 2, false, true)
                            )
                        )
                    )
            )
        );
    }

    private void generateRareGenericLoot(BiConsumer<ResourceKey<LootTable>, Builder> consumer) {
        consumer.accept(
            MobChampionsLootTables.RARE_GENERIC_LOOT,
            LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                    .add(
                        createBookWithLevels(25, 5, 15)
                    )
                    .add(
                        createPotion(
                            "advanced_medkit",
                            75,
                            ChatFormatting.LIGHT_PURPLE,
                            Potions.STRONG_HEALING,
                            List.of(
                                new MobEffectInstance(MobEffects.REGENERATION, 200, 3, false, true)
                            )
                        )
                    )
            )
        );
    }

    private void generateEpicGenericLoot(BiConsumer<ResourceKey<LootTable>, Builder> consumer) {
        consumer.accept(
            MobChampionsLootTables.EPIC_GENERIC_LOOT,
            LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                    .add(
                        createBookWithLevels(25, 15, 25)
                    )
                    .add(
                        createItemDrop(Items.DIAMOND, 30, 1, 4)
                    )
                    .add(
                        createPotion(
                            "elite_medkit",
                            40,
                            ChatFormatting.LIGHT_PURPLE,
                            Potions.STRONG_HEALING,
                            List.of(
                                new MobEffectInstance(MobEffects.INSTANT_HEALTH, 0, 3, false, true)
                            )
                        )
                    )
                    .add(
                        createPotion(
                            "adrenaline_shot",
                            30,
                            ChatFormatting.RED,
                            Potions.STRONG_SWIFTNESS,
                            List.of(
                                new MobEffectInstance(MobEffects.STRENGTH, 600, 2, false, true)
                            )
                        )
                    )
                    .add(
                        LootTableHelper.addEffectsItem(
                            Items.TOTEM_OF_UNDYING,
                            "lucky_charm",
                            10,
                            ChatFormatting.GREEN,
                            List.of(
                                new MobEffectInstance(MobEffects.LUCK, 6000)
                            ),
                            List.of(
                                new LootTableHelper.ItemEnchantment(Enchantments.PROTECTION, ConstantValue.exactly(1))
                            ),
                            enchantmentRegistryLookup
                        )
                    )
            )
        );
    }

    private void generateLegendaryGenericLoot(BiConsumer<ResourceKey<LootTable>, Builder> consumer) {
        consumer.accept(
            MobChampionsLootTables.LEGENDARY_GENERIC_LOOT,
            LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                    .add(
                        createBookWithLevels(50, 30, 50)
                    )
                    .add(
                        createItemDrop(Items.DIAMOND, 50, 2, 5)
                    )
                    .add(
                        createPotion(
                            "legendary_medkit",
                            40,
                            ChatFormatting.LIGHT_PURPLE,
                            Potions.STRONG_HEALING,
                            List.of(
                                new MobEffectInstance(MobEffects.INSTANT_HEALTH, 0, 5, false, true)
                            )
                        )
                    )
                    .add(
                        createPotion(
                            "energy_drink",
                            30,
                            ChatFormatting.GOLD,
                            Potions.STRONG_SWIFTNESS,
                            List.of(
                                new MobEffectInstance(MobEffects.HASTE, 3600, 3, false, true)
                            )
                        )
                    )
                    .add(
                        LootTableHelper.addItem(
                            Items.TOTEM_OF_UNDYING,
                            "yolt",
                            10,
                            ChatFormatting.DARK_RED,
                            List.of(
                                new LootTableHelper.ItemEnchantment(Enchantments.VANISHING_CURSE, UniformGenerator.between(1, 3))
                            ),
                            enchantmentRegistryLookup
                        )
                    )
            )
        );
    }

    private LootPoolSingletonContainer.Builder<?> createPotion(String name, int weight, ChatFormatting color,
            Holder<Potion> potion, List<MobEffectInstance> effects) {
        LootPoolSingletonContainer.Builder<?> builder = LootItem.lootTableItem(Items.POTION).apply(
            SetNameFunction.setName(
                TranslationHelper.getLootComponent(name, color),
                Target.ITEM_NAME
            )
        )
        .setWeight(weight)
        .apply(
            SetPotionFunction.setPotion(potion)
        );

        builder = LootTableHelper.addEffects(builder, effects);

        return LootTableHelper.addLore(builder);
    }

    private LootPoolSingletonContainer.Builder<?> createItemDrop(Item item, int weight, float min, float max) {
        return LootItem.lootTableItem(item).apply(
            SetItemCountFunction.setCount(UniformGenerator.between(min, max), true)
        )
        .setWeight(weight);
    }

    private LootPoolSingletonContainer.Builder<?> createRandomBook(int weight) {
        LootPoolSingletonContainer.Builder<?> builder = LootItem.lootTableItem(Items.BOOK).apply(
            EnchantRandomlyFunction.randomEnchantment()
        )
        .setWeight(weight);

        return LootTableHelper.addLore(builder);
    }

    private LootPoolSingletonContainer.Builder<?> createBookWithLevels(int weight, float min, float max) {
        LootPoolSingletonContainer.Builder<?> builder = LootItem.lootTableItem(Items.BOOK).apply(
            EnchantWithLevelsFunction.enchantWithLevels(this.lookupProvider, UniformGenerator.between(min, max))
        )
        .setWeight(weight);

        return LootTableHelper.addLore(builder);
    }


}
