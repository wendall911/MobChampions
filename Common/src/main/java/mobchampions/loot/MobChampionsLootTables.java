package mobchampions.loot;

import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

import mobchampions.MobChampions;
import mobchampions.network.MobChampion.Rank;

public class MobChampionsLootTables {

    private static final Set<ResourceKey<LootTable>> ALL = Sets.newHashSet();

    public static final ResourceKey<LootTable> UNCOMMON_WEAPON_LOOT = register("uncommon_weapon_loot");
    public static final ResourceKey<LootTable> UNCOMMON_WEARABLE_LOOT = register("uncommon_wearable_loot");
    public static final ResourceKey<LootTable> UNCOMMON_GENERIC_LOOT = register("uncommon_generic_loot");
    public static final ResourceKey<LootTable> RARE_WEAPON_LOOT = register("rare_weapon_loot");
    public static final ResourceKey<LootTable> RARE_WEARABLE_LOOT = register("rare_wearable_loot");
    public static final ResourceKey<LootTable> RARE_GENERIC_LOOT = register("rare_generic_loot");
    public static final ResourceKey<LootTable> EPIC_WEAPON_LOOT = register("epic_weapon_loot");
    public static final ResourceKey<LootTable> EPIC_WEARABLE_LOOT = register("epic_wearable_loot");
    public static final ResourceKey<LootTable> EPIC_GENERIC_LOOT = register("epic_generic_loot");
    public static final ResourceKey<LootTable> LEGENDARY_WEAPON_LOOT = register("legendary_weapon_loot");
    public static final ResourceKey<LootTable> LEGENDARY_WEARABLE_LOOT = register("legendary_wearable_loot");
    public static final ResourceKey<LootTable> LEGENDARY_GENERIC_LOOT = register("legendary_generic_loot");

    public static void init() {}

    private static ResourceKey<LootTable> register(String id) {
        return register(ResourceKey.create(Registries.LOOT_TABLE, MobChampions.prefix(id)));
    }

    private static ResourceKey<LootTable> register(ResourceKey<LootTable> key) {
        if (ALL.add(key)) {
            return key;
        }
        else {
            throw new IllegalStateException("LootTable " + key.location() + " is already registered");
        }
    }

    public static ResourceKey<LootTable> getWeaponLootTable(Rank rank) {
        return switch (rank) {
            case RARE -> RARE_WEAPON_LOOT;
            case EPIC -> EPIC_WEAPON_LOOT;
            case LEGENDARY -> LEGENDARY_WEAPON_LOOT;
            default -> UNCOMMON_WEAPON_LOOT;
        };
    }

    public static ResourceKey<LootTable> getWearableLootTable(Rank rank) {
        return switch (rank) {
            case RARE -> RARE_WEARABLE_LOOT;
            case EPIC -> EPIC_WEARABLE_LOOT;
            case LEGENDARY -> LEGENDARY_WEARABLE_LOOT;
            default -> UNCOMMON_WEARABLE_LOOT;
        };
    }

    public static ResourceKey<LootTable> getGenericLootTable(Rank rank) {
        return switch (rank) {
            case RARE -> RARE_GENERIC_LOOT;
            case EPIC -> EPIC_GENERIC_LOOT;
            case LEGENDARY -> LEGENDARY_GENERIC_LOOT;
            default -> UNCOMMON_GENERIC_LOOT;
        };
    }

}
