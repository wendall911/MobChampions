package mobchampions.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.phys.EntityHitResult;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mobchampions.event.MobChampionEventHandler;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin {

    /*
     * Check to see if the projectile is owned by a mob champion on launch.
     * If so, apply any special effects or damage modifications here.
     */
    @Inject(method = "onHitEntity", at = @At(value = "HEAD"))
    private void mobchampions$beforeHit(EntityHitResult result, CallbackInfo ci) {
        AbstractArrow abstractArrow = (AbstractArrow)(Object)this;
        Entity projectileOwner = abstractArrow.getOwner();

        if (projectileOwner instanceof LivingEntity livingEntity) {
            MobChampionEventHandler.updateChampionArrowDamage(livingEntity, abstractArrow);
        }
    }

}
