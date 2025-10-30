package mobchampions.mixin;

import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.level.entity.PersistentEntitySectionManager;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mobchampions.MobChampions;

@Mixin(PersistentEntitySectionManager.class)
public abstract class PersistentEntitySectionManagerMixin<T extends EntityAccess> {

    @Inject(method = "addEntity", at = @At("RETURN"))
    private void mc$addEntity(T entity, boolean worldGenSpawned, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) {
            /*
             * Whitelist should likely be based on entity type tags, or a config list of entity types.
             * If whitelisted, check for mob champion spawn based on config chance.
             * Randomly generate an optional mob champion rank. If it is a
             * champion, apply effects, modify attributes, set custom name, etc.
             */
            MobChampions.LOGGER.warn("added entity to world");
        }
    }
}
