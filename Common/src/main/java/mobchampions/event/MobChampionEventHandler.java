package mobchampions.event;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

import mobchampions.config.ConfigHandler;
import mobchampions.MobChampions;
import mobchampions.network.MobChampion;
import mobchampions.platform.Services;
import mobchampions.util.MobChampionBuilder;

public class MobChampionEventHandler {

    public static void addRandomChampion(LivingEntity livingEntity) {
        /*
         * Whitelist is based on a config list of entity types.
         * Randomly generate a mob champion rank if not already present.
         * If it is a champion uncommon rank or higher, apply effects, modify attributes, etc.
         */
        EntityType<?> entityType = livingEntity.getType();
        String entityTypeString = BuiltInRegistries.ENTITY_TYPE.getKey(entityType).toString();

        if (ConfigHandler.Common.getChampionWhitelist().contains(entityTypeString)) {
            Services.PLATFORM.getMobChampionData(livingEntity).ifPresent(data -> {
                if (data.getEntityId() == -1) {
                    int randomWeight = MobChampions.RANDOM.nextInt(ConfigHandler.Common.getTotalWeight());

                    // Update entity ID
                    data.setEntityId(livingEntity.getId());

                    // Randomly assign rank
                    MobChampion.Rank newRank = ConfigHandler.Common.getChampionByWeight(randomWeight);
                    data.setRank(newRank);

                    // Apply champion effects/attributes if uncommon or higher
                    MobChampionBuilder.applyChampionAttributesAndEffects(livingEntity, newRank);
                }
            });
        }
    }

    public static void addNormalMob(LivingEntity livingEntity) {
        /*
         * If entity is spawned via blacklisted spawn type, or other means,
         * ensure it is set as a normal mob.
         * Set entity as normal mob so we don't process again later.
         */
        EntityType<?> entityType = livingEntity.getType();
        String entityTypeString = BuiltInRegistries.ENTITY_TYPE.getKey(entityType).toString();

        if (ConfigHandler.Common.getChampionWhitelist().contains(entityTypeString)) {
            Services.PLATFORM.getMobChampionData(livingEntity).ifPresent(data -> {
                // Update entity ID
                data.setEntityId(livingEntity.getId());

                // Set rank to common mob
                data.setRank(MobChampion.Rank.COMMON);
            });
        }
    }

}
