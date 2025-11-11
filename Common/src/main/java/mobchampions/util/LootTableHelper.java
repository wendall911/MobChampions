package mobchampions.util;

import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.equipment.trim.ArmorTrim;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.SetComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetEnchantmentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetLoreFunction;
import net.minecraft.world.level.storage.loot.functions.SetNameFunction;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public class LootTableHelper {

    public static LootPoolSingletonContainer.Builder<?> addLore(LootPoolSingletonContainer.Builder<?> builder) {
        return builder.apply(
            SetLoreFunction.setLore().addLine(
                TranslationHelper.getLootComponent("title", ChatFormatting.DARK_GREEN)
            )
        );
    }

    public static LootPoolSingletonContainer.Builder<?> addItemEnchantments(LootPoolSingletonContainer.Builder<?> builder,
            HolderLookup.RegistryLookup<Enchantment> enchantmentRegistryLookup, List<ItemEnchantment> enchantments) {
        SetEnchantmentsFunction.Builder enchantmentBuilder = new SetEnchantmentsFunction.Builder();

        for (ItemEnchantment itemEnchantment : enchantments) {
            enchantmentBuilder = enchantmentBuilder.withEnchantment(
                enchantmentRegistryLookup.getOrThrow(itemEnchantment.enchantment()),
                itemEnchantment.numberGenerator()
            );
        }

        return builder.apply(enchantmentBuilder);
    }

    public static LootPoolSingletonContainer.Builder<?> addItem(Item item, String id, int weight, ChatFormatting color,
            List<ItemEnchantment> enchantments, HolderLookup.RegistryLookup<Enchantment> enchantmentRegistryLookup) {
        LootPoolSingletonContainer.Builder<?> builder = LootItem.lootTableItem(item).apply(
                SetNameFunction.setName(
                    TranslationHelper.getLootComponent(id, color),
                    SetNameFunction.Target.ITEM_NAME
                )
            )
            .setWeight(weight);

        builder = addItemEnchantments(builder, enchantmentRegistryLookup, enchantments);

        return addLore(builder);
    }

    public static LootPoolSingletonContainer.Builder<?> addDyedItem(Item item, String id, int weight, ChatFormatting color, int dyedColor,
            List<ItemEnchantment> enchantments, HolderLookup.RegistryLookup<Enchantment> enchantmentRegistryLookup) {
        return addItem(item, id, weight, color, enchantments, enchantmentRegistryLookup)
            .apply(setDyedColor(dyedColor));
    }

    public static LootPoolSingletonContainer.Builder<?> addEffectsItem(Item item, String id, int weight, ChatFormatting color,
            List<MobEffectInstance> effects, List<ItemEnchantment> enchantments,
            HolderLookup.RegistryLookup<Enchantment> enchantmentRegistryLookup) {
        LootPoolSingletonContainer.Builder<?> builder = addItem(item, id, weight, color, enchantments, enchantmentRegistryLookup);

        return addEffects(builder, effects);
    }

    public static List<? extends LootPoolSingletonContainer.Builder<?>> createDyedArmorSet(ArmorTrim trim, String prefix, int weight,
            ChatFormatting color, List<Item> armorItems, List<ItemEnchantment> enchantments,
            HolderLookup.RegistryLookup<Enchantment> enchantmentRegistryLookup, int dyedColor) {
        return createArmorSet(trim, prefix, weight, color, armorItems, enchantments, enchantmentRegistryLookup, dyedColor);
    }

    public static List<? extends LootPoolSingletonContainer.Builder<?>> createArmorSet(ArmorTrim trim, String prefix, int weight,
        ChatFormatting color, List<Item> armorItems, List<ItemEnchantment> enchantments,
        HolderLookup.RegistryLookup<Enchantment> enchantmentRegistryLookup) {
        return createArmorSet(trim, prefix, weight, color, armorItems, enchantments, enchantmentRegistryLookup, -1);
    }

    public static List<? extends LootPoolSingletonContainer.Builder<?>> createArmorSet(ArmorTrim trim, String prefix, int weight,
            ChatFormatting color, List<Item> armorItems, List<ItemEnchantment> enchantments,
            HolderLookup.RegistryLookup<Enchantment> enchantmentRegistryLookup, int dyedColor) {
        return armorItems.stream().map(item -> {
                String suffix = BuiltInRegistries.ITEM.getKey(item).getPath().split("_")[1];
                LootPoolSingletonContainer.Builder<?> builder = addItem(
                    item,
                    prefix + "_" + suffix,
                    weight,
                    color,
                    enchantments,
                    enchantmentRegistryLookup
                ).apply(SetComponentsFunction.setComponent(DataComponents.TRIM, trim));
                if (dyedColor != -1) {
                    builder = builder.apply(setDyedColor(dyedColor));
                }
                return builder;
            }
        ).toList();
    }

    public static LootItemConditionalFunction.Builder<?> setDyedColor(int color) {
        return SetComponentsFunction.setComponent(DataComponents.DYED_COLOR, new DyedItemColor(color));
    }

    public static LootPoolSingletonContainer.Builder<?> addEffects(LootPoolSingletonContainer.Builder<?> builder, List<MobEffectInstance> effects) {
        for (MobEffectInstance effect : effects) {
            builder = builder.apply(
                SetComponentsFunction.setComponent(
                    DataComponents.POTION_CONTENTS,
                    PotionContents.EMPTY.withEffectAdded(effect)
                )
            );
        }

        return builder;
    }

    public record ItemEnchantment(ResourceKey<Enchantment> enchantment, NumberProvider numberGenerator) {}

}
