package mobchampions.mixin;

import org.jetbrains.annotations.Nullable;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mobchampions.config.ConfigHandler;
import mobchampions.loot.MobChampionsLootTables;
import mobchampions.network.MobChampion;
import mobchampions.platform.Services;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow public abstract boolean wasExperienceConsumed();

    @Shadow protected abstract boolean isAlwaysExperienceDropper();

    @Shadow protected int lastHurtByPlayerTime;

    @Shadow public abstract boolean shouldDropExperience();

    @Shadow
    @Nullable
    protected Player lastHurtByPlayer;

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    /*
     * Inject into loot table drops to add mob champion bonus loot.
     * Only applies if killed by a player.
     */
    @Inject(method = "dropFromLootTable", at = @At("TAIL"))
    public void mc$dropFromLootTable(DamageSource damageSource, boolean hitByPlayer, CallbackInfo ci) {
        if (!hitByPlayer || this.lastHurtByPlayer == null) {
            return;
        }

        LivingEntity livingEntity = (LivingEntity) (Object) this;

        Services.PLATFORM.getMobChampionData(livingEntity).ifPresent(data -> {
            if (data.getEntityId() != -1 && data.getRank() != MobChampion.Rank.COMMON) {
                ServerLevel level = (ServerLevel) livingEntity.level();
                LootParams params = mobChampions$createEquipmentParams(livingEntity, level, damageSource, hitByPlayer);
                float lootChance = ConfigHandler.Common.getLootDropChance(data.getRank());

                if (lootChance > 0 && level.getRandom().nextFloat() < lootChance) {
                    ResourceKey<LootTable> weaponLootTableKey = MobChampionsLootTables.getWeaponLootTable(data.getRank());
                    ResourceKey<LootTable> wearableLootTableKey = MobChampionsLootTables.getWearableLootTable(data.getRank());
                    ResourceKey<LootTable> genericLootTableKey = MobChampionsLootTables.getGenericLootTable(data.getRank());

                    LootTable weaponLootTable = level.getServer().reloadableRegistries().getLootTable(weaponLootTableKey);
                    LootTable wearableLootTable = level.getServer().reloadableRegistries().getLootTable(wearableLootTableKey);
                    LootTable genericLootTable = level.getServer().reloadableRegistries().getLootTable(genericLootTableKey);

                    weaponLootTable.getRandomItems(params).forEach(livingEntity::spawnAtLocation);
                    wearableLootTable.getRandomItems(params).forEach(livingEntity::spawnAtLocation);
                    genericLootTable.getRandomItems(params).forEach(livingEntity::spawnAtLocation);
                }
            }
        });

    }

    @Unique
    private LootParams mobChampions$createEquipmentParams(LivingEntity livingEntity, ServerLevel level, DamageSource damageSource, boolean hitByPlayer) {
        LootParams.Builder lootparams$builder = (new LootParams.Builder(level)).withParameter(
            LootContextParams.THIS_ENTITY,
            livingEntity
        ).withParameter(
            LootContextParams.ORIGIN,
            livingEntity.position()
        ).withParameter(
            LootContextParams.DAMAGE_SOURCE,
            damageSource
        ).withOptionalParameter(
            LootContextParams.ATTACKING_ENTITY,
            damageSource.getEntity()
        ).withOptionalParameter(
            LootContextParams.DIRECT_ATTACKING_ENTITY,
            damageSource.getDirectEntity()
        );

        if (hitByPlayer && this.lastHurtByPlayer != null) {
            lootparams$builder = lootparams$builder.withParameter(
                LootContextParams.LAST_DAMAGE_PLAYER,
                this.lastHurtByPlayer
            ).withLuck(this.lastHurtByPlayer.getLuck());
        }

        return lootparams$builder.create(LootContextParamSets.ENTITY);
    }

}
