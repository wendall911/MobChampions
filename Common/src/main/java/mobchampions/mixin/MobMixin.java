package mobchampions.mixin;

import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.ServerLevelAccessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mobchampions.config.ConfigHandler;
import mobchampions.event.MobChampionEventHandler;
import mobchampions.network.MobChampion;
import mobchampions.platform.Services;

@Mixin(Mob.class)
public abstract class MobMixin {

    @Inject(method = "finalizeSpawn", at = @At("RETURN"))
    public void mobchampions$onFinalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
            EntitySpawnReason spawnReason, SpawnGroupData spawnGroupData, CallbackInfoReturnable<SpawnGroupData> cir) {
        // Only process if spawn type is not blacklisted
        if (!ConfigHandler.Common.getSpawnReasonBlacklistEnums().contains(spawnReason)) {
            LivingEntity livingEntity = (LivingEntity) (Object) this;

            MobChampionEventHandler.addRandomChampion(livingEntity);
        }
        else { // For blacklisted spawn types, ensure mob is set to COMMON so we can track it
            LivingEntity livingEntity = (LivingEntity) (Object) this;

            MobChampionEventHandler.addNormalMob(livingEntity);
        }
    }

    @Inject(method = "getBaseExperienceReward", at = @At("RETURN"), cancellable = true)
    public void mobchampions$modifyExperienceReward(CallbackInfoReturnable<Integer> cir) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        int baseXP = cir.getReturnValue();

        if (baseXP <= 0) {
            return; // No XP to modify
        }

        Services.PLATFORM.getMobChampionData(livingEntity).ifPresent(data -> {
            if (data.getEntityId() != -1 && data.getRank() != MobChampion.Rank.COMMON) {
                // Modify XP based on champion rank multiplier
                int modifiedXP = (int) (baseXP * ConfigHandler.Common.getExperienceMultiplierForRank(data.getRank()));

                cir.setReturnValue(modifiedXP);
            }
        });
    }

}
