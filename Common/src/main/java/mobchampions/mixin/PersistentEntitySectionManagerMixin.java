package mobchampions.mixin;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.level.entity.PersistentEntitySectionManager;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mobchampions.MobChampions;
import mobchampions.config.ConfigHandler;

@Mixin(PersistentEntitySectionManager.class)
public abstract class PersistentEntitySectionManagerMixin<T extends EntityAccess> {

    @Inject(method = "addEntity", at = @At("RETURN"))
    private void mc$addEntity(T entity, boolean worldGenSpawned, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) {
            /*
             * Whitelist is based on a config list of entity types.
             * Randomly generate a mob champion rank if not already present.
             * If it is a not champion, apply effects, modify attributes, etc.
             */
            EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.byId(entity.getId());
            String entityTypeString = BuiltInRegistries.ENTITY_TYPE.getKey(entityType).toString();

            if (ConfigHandler.Common.getChampionWhitelist().contains(entityTypeString)) {
                MobChampions.LOGGER.warn("added whitelist entity of type {} to world", entityTypeString);
            }
        }
    }
}
