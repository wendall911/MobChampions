package mobchampions.data;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableProvider.SubProviderEntry;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import mobchampions.data.loot.MobChampionsWeaponLoot;
import mobchampions.data.loot.MobChampionsWearableLoot;
import mobchampions.data.loot.MobChampionsGenericLoot;

public class MobChampionsLootTableGenerator {

    public static LootTableProvider create(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> provider) {
        return new LootTableProvider(
            packOutput,
            Set.of(),
            List.of(
                new SubProviderEntry(MobChampionsWeaponLoot::new, LootContextParamSets.EQUIPMENT),
                new SubProviderEntry(MobChampionsWearableLoot::new, LootContextParamSets.EQUIPMENT),
                new SubProviderEntry(MobChampionsGenericLoot::new, LootContextParamSets.ALL_PARAMS)
            ),
            provider
        );
    }

}
