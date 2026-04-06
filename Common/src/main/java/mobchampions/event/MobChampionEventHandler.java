package mobchampions.event;

import java.util.List;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.phys.AABB;

import mobchampions.MobChampions;
import mobchampions.common.Translations;
import mobchampions.config.ConfigHandler;
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
        String entityTypeString = getEntityTypeString(livingEntity);

        if (ConfigHandler.Common.getChampionWhitelist().contains(entityTypeString)) {
            if (ConfigHandler.Common.isBabyChampionsDisabled() && livingEntity.isBaby()) {
                return;
            }
            Services.PLATFORM.getMobChampionData(livingEntity).ifPresent(data -> {
                if (data.getEntityId() == -1) {
                    int randomWeight = MobChampions.RANDOM.nextInt(ConfigHandler.Common.getTotalWeight());

                    // Update entity ID
                    data.setEntityId(livingEntity.getId());

                    // Randomly assign rank
                    MobChampion.Rank newRank = ConfigHandler.Common.getChampionByWeight(randomWeight);
                    data.setRank(newRank);

                    // Apply champion effects/attributes if uncommon or higher
                    MobChampionBuilder.build(livingEntity, newRank);

                    if (ConfigHandler.Common.enableSpawnMessage() && newRank.ordinal() >= ConfigHandler.Common.getSpawnMessageMinimumRank()) {
                        AABB aabb = new AABB(livingEntity.blockPosition()).inflate(ConfigHandler.Common.getSpawnMessageRange());
                        List<ServerPlayer> list = livingEntity.level().getEntitiesOfClass(ServerPlayer.class, aabb, player -> true);

                        for (ServerPlayer serverPlayer : list) {
                            serverPlayer.sendSystemMessage(
                                Component.translatable(
                                    Translations.ANNOUNCEMENT_KEY + "spawn",
                                    Component.translatable(
                                        Translations.getTitleByRank(newRank)
                                    ).withColor(ConfigHandler.Common.getChampionColor(newRank))
                                )
                            , true);
                        }
                    }
                }

                Services.PLATFORM.syncMobChampionData(null, livingEntity);
            });
        }
    }

    public static void updateChampionArrowDamage(LivingEntity livingEntity, AbstractArrow abstractArrow, double baseDamage) {
        /*
         * If entity is a mob champion, ensure its projectile damage is updated.
         * This is done because projectiles are separate entities and don't inherit attributes automatically.
         */
        String entityTypeString = getEntityTypeString(livingEntity);

        if (ConfigHandler.Common.getChampionWhitelist().contains(entityTypeString)) {
            Services.PLATFORM.getMobChampionData(livingEntity).ifPresent(data -> {
                if (data.getEntityId() != -1 && data.getRank() != MobChampion.Rank.COMMON) {
                    double damageModifier = ConfigHandler.Common.getProjectileDamageModifier(data.getRank());
                    double newDamage = baseDamage + (baseDamage * damageModifier);

                    abstractArrow.setBaseDamage(newDamage);
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
        String entityTypeString = getEntityTypeString(livingEntity);

        if (ConfigHandler.Common.getChampionWhitelist().contains(entityTypeString)) {
            Services.PLATFORM.getMobChampionData(livingEntity).ifPresent(data -> {
                // Update entity ID
                data.setEntityId(livingEntity.getId());

                // Set rank to common mob
                data.setRank(MobChampion.Rank.COMMON);

                Services.PLATFORM.syncMobChampionData(null, livingEntity);
            });
        }
    }

    private static String getEntityTypeString(LivingEntity livingEntity) {
        EntityType<?> entityType = livingEntity.getType();

        return BuiltInRegistries.ENTITY_TYPE.getKey(entityType).toString();
    }

}
