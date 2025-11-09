package mobchampions.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mobchampions.config.ConfigHandler;
import mobchampions.network.MobChampion;
import mobchampions.platform.Services;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Inject(method = "getTeamColor", at = @At("RETURN"), cancellable = true)
    private void mobchampions$modifyTeamColor(CallbackInfoReturnable<Integer> cir) {
        Entity entity = (Entity)(Object) this;

        if (entity instanceof LivingEntity livingEntity) {
            Services.PLATFORM.getMobChampionData(livingEntity).ifPresent(data -> {
                if (data.getRank().ordinal() > MobChampion.Rank.COMMON.ordinal()) {
                    cir.setReturnValue(ConfigHandler.Client.getChampionColor(data.getRank()));
                }
            });
        }
    }

}
