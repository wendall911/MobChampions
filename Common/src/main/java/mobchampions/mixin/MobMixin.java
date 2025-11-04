package mobchampions.mixin;

import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.ServerLevelAccessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mobchampions.config.ConfigHandler;
import mobchampions.event.MobChampionEventHandler;

@Mixin(Mob.class)
public abstract class MobMixin {

    @Inject(method = "finalizeSpawn", at = @At("RETURN"))
    public void mobchampions$onFinalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, SpawnGroupData spawnGroupData, CallbackInfoReturnable<SpawnGroupData> cir) {
        // Only process if spawn type is not blacklisted
        if (!ConfigHandler.Common.getSpawnTypeBlacklistEnums().contains(spawnType)) {
            LivingEntity livingEntity = (LivingEntity) (Object) this;

            MobChampionEventHandler.addRandomChampion(livingEntity);
        }
        else { // For blacklisted spawn types, ensure mob is normal
            LivingEntity livingEntity = (LivingEntity) (Object) this;

            MobChampionEventHandler.addNormalMob(livingEntity);
        }
    }

}
