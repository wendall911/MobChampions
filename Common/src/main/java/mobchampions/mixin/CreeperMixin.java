package mobchampions.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mobchampions.config.ConfigHandler;
import mobchampions.platform.Services;

@Mixin(Creeper.class)
public abstract class CreeperMixin {

    @Shadow
    private int explosionRadius;

    @Inject(method = "explodeCreeper", at = @At("HEAD"))
    private void mobchampions$explodeCreeper(CallbackInfo ci) {
        LivingEntity livingEntity = (LivingEntity)(Object)this;

        if (!livingEntity.level().isClientSide()) {
            Services.PLATFORM.getMobChampionData(livingEntity).ifPresent(data -> {
                if (data.getRank().ordinal() > 0) {
                    this.explosionRadius = (int) (this.explosionRadius * ConfigHandler.Common.getCreeperExplosionRadiusMultiplier(data.getRank()));
                }
            });
        }
    }

    @Inject(method = "spawnLingeringCloud", at = @At("HEAD"), cancellable = true)
    private void mobchampions$spawnLingeringCloud(CallbackInfo ci) {
        LivingEntity livingEntity = (LivingEntity)(Object) this;

        if (!livingEntity.level().isClientSide()) {
            Services.PLATFORM.getMobChampionData(livingEntity).ifPresent(data -> {
                if (data.getRank().ordinal() > 0) {
                    ci.cancel();
                }
            });
        }
    }

}
