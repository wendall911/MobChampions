package mobchampions.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mobchampions.MobChampions;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow public abstract boolean wasExperienceConsumed();

    @Shadow protected abstract boolean isAlwaysExperienceDropper();

    @Shadow protected int lastHurtByPlayerTime;

    @Shadow public abstract boolean shouldDropExperience();

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "dropExperience", at = @At("HEAD"))
    public void mc$dropExperience(Entity entity, CallbackInfo ci) {
        if (this.level() instanceof ServerLevel serverLevel
            && !this.wasExperienceConsumed()
            && (this.isAlwaysExperienceDropper()
                || this.lastHurtByPlayerTime > 0
                && this.shouldDropExperience()
                && this.level().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)
            )
        ) {
            // Check if has mobChampion attachement, check level, add bonus loot
            MobChampions.LOGGER.warn("dropping xp");
        }
    }

}
