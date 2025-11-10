package mobchampions.util;

import java.util.List;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.providers.VanillaEnchantmentProviders;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.apache.commons.lang3.tuple.Pair;

import mobchampions.MobChampions;
import mobchampions.common.effect.MobChampionsEffects;
import mobchampions.common.stats.ChampionStats;
import mobchampions.common.stats.ChampionStatsManager;
import mobchampions.config.ConfigHandler;
import mobchampions.loot.MobChampionsLootTables;
import mobchampions.network.MobChampion.Rank;

public class MobChampionBuilder {

    private static final ChampionStats uncommonStats = ChampionStatsManager.getStatsForRank(Rank.UNCOMMON);
    private static final ChampionStats rareStats = ChampionStatsManager.getStatsForRank(Rank.RARE);
    private static final ChampionStats epicStats = ChampionStatsManager.getStatsForRank(Rank.EPIC);
    private static final ChampionStats legendaryStats = ChampionStatsManager.getStatsForRank(Rank.LEGENDARY);

    private static final AttributeModifier UNCOMMON_HEALTH_MODIFIER = new AttributeModifier(
        MobChampions.prefix("uncommon_health_multiplier"),
        uncommonStats.getHealthMultiplier(),
        AttributeModifier.Operation.ADD_MULTIPLIED_BASE
    );
    private static final AttributeModifier UNCOMMON_ARMOR_MODIFIER = new AttributeModifier(
        MobChampions.prefix("uncommon_armor_addition"),
        uncommonStats.getArmorAddition(),
        AttributeModifier.Operation.ADD_VALUE
    );
    private static final AttributeModifier UNCOMMON_MOVEMENT_SPEED_MODIFIER = new AttributeModifier(
        MobChampions.prefix("uncommon_movement_speed_multiplier"),
        uncommonStats.getMovementSpeedMultiplier(),
        Operation.ADD_MULTIPLIED_BASE
    );
    private static final AttributeModifier UNCOMMON_ATTACK_DAMAGE_MODIFIER = new AttributeModifier(
        MobChampions.prefix("uncommon_attack_damage_multiplier"),
        uncommonStats.getAttackDamageMultiplier(),
        Operation.ADD_MULTIPLIED_BASE
    );
    private static final AttributeModifier UNCOMMON_KNOCKBACK_RESISTANCE_MODIFIER = new AttributeModifier(
        MobChampions.prefix("uncommon_knockback_resistance_addition"),
        uncommonStats.getKnockbackResistanceAddition(),
        Operation.ADD_VALUE
    );
    private static final AttributeModifier RARE_HEALTH_MODIFIER = new AttributeModifier(
        MobChampions.prefix("rare_health_multiplier"),
        rareStats.getHealthMultiplier(),
        AttributeModifier.Operation.ADD_MULTIPLIED_BASE
    );
    private static final AttributeModifier RARE_ARMOR_MODIFIER = new AttributeModifier(
        MobChampions.prefix("rare_armor_addition"),
        rareStats.getArmorAddition(),
        AttributeModifier.Operation.ADD_VALUE
    );
    private static final AttributeModifier RARE_MOVEMENT_SPEED_MODIFIER = new AttributeModifier(
        MobChampions.prefix("rare_movement_speed_multiplier"),
        rareStats.getMovementSpeedMultiplier(),
        Operation.ADD_MULTIPLIED_BASE
    );
    private static final AttributeModifier RARE_ATTACK_DAMAGE_MODIFIER = new AttributeModifier(
        MobChampions.prefix("rare_attack_damage_multiplier"),
        rareStats.getAttackDamageMultiplier(),
        Operation.ADD_MULTIPLIED_BASE
    );
    private static final AttributeModifier RARE_KNOCKBACK_RESISTANCE_MODIFIER = new AttributeModifier(
        MobChampions.prefix("rare_knockback_resistance_addition"),
        rareStats.getKnockbackResistanceAddition(),
        Operation.ADD_VALUE
    );
    private static final AttributeModifier EPIC_HEALTH_MODIFIER = new AttributeModifier(
        MobChampions.prefix("epic_health_multiplier"),
        epicStats.getHealthMultiplier(),
        AttributeModifier.Operation.ADD_MULTIPLIED_BASE
    );
    private static final AttributeModifier EPIC_ARMOR_MODIFIER = new AttributeModifier(
        MobChampions.prefix("epic_armor_addition"),
        epicStats.getArmorAddition(),
        AttributeModifier.Operation.ADD_VALUE
    );
    private static final AttributeModifier EPIC_MOVEMENT_SPEED_MODIFIER = new AttributeModifier(
        MobChampions.prefix("epic_movement_speed_multiplier"),
        epicStats.getMovementSpeedMultiplier(),
        Operation.ADD_MULTIPLIED_BASE
    );
    private static final AttributeModifier EPIC_ATTACK_DAMAGE_MODIFIER = new AttributeModifier(
        MobChampions.prefix("epic_attack_damage_multiplier"),
        epicStats.getAttackDamageMultiplier(),
        Operation.ADD_MULTIPLIED_BASE
    );
    private static final AttributeModifier EPIC_KNOCKBACK_RESISTANCE_MODIFIER = new AttributeModifier(
        MobChampions.prefix("epic_knockback_resistance_addition"),
        epicStats.getKnockbackResistanceAddition(),
        Operation.ADD_VALUE
    );
    private static final AttributeModifier LEGENDARY_HEALTH_MODIFIER = new AttributeModifier(
        MobChampions.prefix("legendary_health_multiplier"),
        legendaryStats.getHealthMultiplier(),
        AttributeModifier.Operation.ADD_MULTIPLIED_BASE
    );
    private static final AttributeModifier LEGENDARY_ARMOR_MODIFIER = new AttributeModifier(
        MobChampions.prefix("legendary_armor_addition"),
        legendaryStats.getArmorAddition(),
        AttributeModifier.Operation.ADD_VALUE
    );
    private static final AttributeModifier LEGENDARY_MOVEMENT_SPEED_MODIFIER = new AttributeModifier(
        MobChampions.prefix("legendary_movement_speed_multiplier"),
        legendaryStats.getMovementSpeedMultiplier(),
        Operation.ADD_MULTIPLIED_BASE
    );
    private static final AttributeModifier LEGENDARY_ATTACK_DAMAGE_MODIFIER = new AttributeModifier(
        MobChampions.prefix("legendary_attack_damage_multiplier"),
        legendaryStats.getAttackDamageMultiplier(),
        Operation.ADD_MULTIPLIED_BASE
    );
    private static final AttributeModifier LEGENDARY_KNOCKBACK_RESISTANCE_MODIFIER = new AttributeModifier(
        MobChampions.prefix("legendary_knockback_resistance_addition"),
        legendaryStats.getKnockbackResistanceAddition(),
        Operation.ADD_VALUE
    );
    private static final AttributeModifier SAFE_FALL_DISTANCE_MODIFIER = new AttributeModifier(
        MobChampions.prefix("safe_fall_distance_addition"),
        320.0,
        Operation.ADD_VALUE
    );

    public static void build(LivingEntity entity, Rank rank) {
        switch(rank) {
            case UNCOMMON -> {
                addChampionAttribute(entity.getAttribute(Attributes.MAX_HEALTH), UNCOMMON_HEALTH_MODIFIER);
                addChampionAttribute(entity.getAttribute(Attributes.ARMOR), UNCOMMON_ARMOR_MODIFIER);
                addChampionAttribute(entity.getAttribute(Attributes.MOVEMENT_SPEED), UNCOMMON_MOVEMENT_SPEED_MODIFIER);
                addChampionAttribute(entity.getAttribute(Attributes.ATTACK_DAMAGE), UNCOMMON_ATTACK_DAMAGE_MODIFIER);
                addChampionAttribute(entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE), UNCOMMON_KNOCKBACK_RESISTANCE_MODIFIER);
            }
            case RARE -> {
                addChampionAttribute(entity.getAttribute(Attributes.MAX_HEALTH), RARE_HEALTH_MODIFIER);
                addChampionAttribute(entity.getAttribute(Attributes.ARMOR), RARE_ARMOR_MODIFIER);
                addChampionAttribute(entity.getAttribute(Attributes.MOVEMENT_SPEED), RARE_MOVEMENT_SPEED_MODIFIER);
                addChampionAttribute(entity.getAttribute(Attributes.ATTACK_DAMAGE), RARE_ATTACK_DAMAGE_MODIFIER);
                addChampionAttribute(entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE), RARE_KNOCKBACK_RESISTANCE_MODIFIER);
            }
            case EPIC -> {
                addChampionAttribute(entity.getAttribute(Attributes.MAX_HEALTH), EPIC_HEALTH_MODIFIER);
                addChampionAttribute(entity.getAttribute(Attributes.ARMOR), EPIC_ARMOR_MODIFIER);
                addChampionAttribute(entity.getAttribute(Attributes.MOVEMENT_SPEED), EPIC_MOVEMENT_SPEED_MODIFIER);
                addChampionAttribute(entity.getAttribute(Attributes.ATTACK_DAMAGE), EPIC_ATTACK_DAMAGE_MODIFIER);
                addChampionAttribute(entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE), EPIC_KNOCKBACK_RESISTANCE_MODIFIER);
            }
            case LEGENDARY -> {
                addChampionAttribute(entity.getAttribute(Attributes.MAX_HEALTH), LEGENDARY_HEALTH_MODIFIER);
                addChampionAttribute(entity.getAttribute(Attributes.ARMOR), LEGENDARY_ARMOR_MODIFIER);
                addChampionAttribute(entity.getAttribute(Attributes.MOVEMENT_SPEED), LEGENDARY_MOVEMENT_SPEED_MODIFIER);
                addChampionAttribute(entity.getAttribute(Attributes.ATTACK_DAMAGE), LEGENDARY_ATTACK_DAMAGE_MODIFIER);
                addChampionAttribute(entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE), LEGENDARY_KNOCKBACK_RESISTANCE_MODIFIER);
            }
            default -> {
                // No modifications for COMMON rank
            }
        }
        addChampionAttribute(entity.getAttribute(Attributes.SAFE_FALL_DISTANCE), SAFE_FALL_DISTANCE_MODIFIER);
        updateMaxHealth(entity);
        applyChampionEffect(entity, rank);
        applyGlowingEffectIfNeeded(entity, rank);
        applyInfestedEffectIfNeeded(entity, rank);
        applyWeavingEffectIfNeeded(entity, rank);
        applyWindChargedEffectIfNeeded(entity, rank);
        applyFireResistanceEffectIfNeeded(entity, rank);
        equipChampionWeaponIfNeeded(entity, rank);
        equipChampionGearIfNeeded(entity, rank);
    }

    private static void equipChampionWeaponIfNeeded(LivingEntity entity, Rank rank) {
        EquipmentSlot weaponSlot = EquipmentSlot.MAINHAND;

        /*
         * First check if weapon equipping is allowed for this entity.
         * Then attempt to spawn a standard weapon based on the rank.
         * If needed, upgrade the standard weapon to a loot table weapon.
         */
        if (entity.canUseSlot(weaponSlot)) {
            ItemStack standardWeapon = ConfigHandler.Common.getWeaponForRank(rank);
            Pair<Boolean, ItemStack> maybeWeapon = maybeUpgradeWeaponToLootTableWeapon(entity, standardWeapon, rank);

            if (maybeWeapon.getLeft()) {
                if (maybeWeapon.getRight() != null && maybeWeapon.getRight().getItem() instanceof ShieldItem) {
                    weaponSlot = EquipmentSlot.OFFHAND;
                }
                entity.setItemSlot(weaponSlot, maybeWeapon.getRight());
            }
            else if(standardWeapon != null) {
                if (standardWeapon.getItem() instanceof ShieldItem) {
                    weaponSlot = EquipmentSlot.OFFHAND;
                }
                entity.setItemSlot(weaponSlot, standardWeapon);
            }

            /*
             * Finalize champion weapon by enchanting it if needed.
             */
            finalizeChampionWeapon(entity, rank, maybeWeapon.getLeft(), weaponSlot);
        }
    }

    private static Pair<Boolean, ItemStack> maybeUpgradeWeaponToLootTableWeapon(LivingEntity entity, ItemStack weapon, Rank rank) {
        double chanceToUpgrade = ConfigHandler.Common.getLootableWeaponSpawnChanceForRank(rank);
        ResourceKey<LootTable> equipmentLootTable = MobChampionsLootTables.getWeaponLootTable(rank);
        Level level = entity.level();

        if (level instanceof ServerLevel serverLevel) {
            LootTable lootTable = serverLevel.getServer().reloadableRegistries().getLootTable(equipmentLootTable);
            LootParams params = createEquipmentParams(entity, serverLevel);

            if (lootTable != LootTable.EMPTY && MobChampions.RANDOM.nextFloat() < chanceToUpgrade) {
                List<ItemStack> list = lootTable.getRandomItems(params, 0L);

                if (!list.isEmpty()) {
                    return Pair.of(true, list.getFirst());
                }
            }
        }

        return Pair.of(false, weapon);
    }

    private static LootParams createEquipmentParams(LivingEntity livingEntity, ServerLevel level) {
        return (new LootParams.Builder(level)).withParameter(
            LootContextParams.ORIGIN, livingEntity.position()
        ).withParameter(LootContextParams.THIS_ENTITY, livingEntity).create(LootContextParamSets.EQUIPMENT);
    }

    private static void finalizeChampionWeapon(LivingEntity entity, Rank rank, boolean hasLootTableWeapon, EquipmentSlot weaponSlot) {
        /*
         * Only applies if the entity has a weapon equipped and is a Mob.
         * Applies drop chance to standard weapons.
         * Sets guaranteed drop for loot table weapons.
         */
        if (!entity.getItemBySlot(weaponSlot).isEmpty() && entity instanceof Mob mob) {
            Level level = entity.level();

            if (level instanceof ServerLevel serverLevel) {
                DifficultyInstance difficultyInstance = serverLevel.getCurrentDifficultyAt(entity.blockPosition());

                enchantSpawnedWeapon(mob, rank, serverLevel, serverLevel.getRandom(), difficultyInstance);
            }

            if (hasLootTableWeapon) {
                mob.setDropChance(weaponSlot, ConfigHandler.Common.getLootDropChance(rank));
            }
            else {
                mob.setDropChance(weaponSlot, (float) ConfigHandler.Common.getStandardWeaponDropChance());
            }
        }
    }

    private static void equipChampionGearIfNeeded(LivingEntity entity, Rank rank) {
        /*
         * First check if armor equipping is allowed for this entity.
         * Then check if the entity already has the armor item.
         * If not, attempt to equip an armor item based on the rank.
         */
        for (EquipmentSlot slot : getArmorSlots()) {
            if (entity.canUseSlot(slot)) {
                ItemStack armor = ConfigHandler.Common.getArmorForRankAndSlot(rank, slot);

                if (armor != null) {
                    entity.setItemSlot(slot, armor);
                }
            }
        }

        Pair<EquipmentSlot, ItemStack> upgrade = maybeUpgradeArmorToLootTableArmor(entity, rank);

        if (upgrade.getLeft() != null) {
            entity.setItemSlot(upgrade.getLeft(), upgrade.getRight());
        }
        /*
         * Finalize champion armor by enchanting it if needed.
         */
        finalizeChampionArmor(entity, rank, upgrade.getLeft());

    }

    private static Pair<EquipmentSlot, ItemStack> maybeUpgradeArmorToLootTableArmor(LivingEntity livingEntity, Rank rank) {
        double chanceToUpgrade = ConfigHandler.Common.getLootableArmorSpawnChanceForRank(rank);
        ResourceKey<LootTable> equipmentLootTable = MobChampionsLootTables.getWearableLootTable(rank);
        Level level = livingEntity.level();

        if (level instanceof ServerLevel serverLevel) {
            LootTable lootTable = serverLevel.getServer().reloadableRegistries().getLootTable(equipmentLootTable);
            LootParams params = createEquipmentParams(livingEntity, serverLevel);

            if (lootTable != LootTable.EMPTY && MobChampions.RANDOM.nextFloat() < chanceToUpgrade) {
                List<ItemStack> list = lootTable.getRandomItems(params, 0L);

                if (!list.isEmpty()) {
                    ItemStack armorItem = list.getFirst();
                    Equipable equipable = Equipable.get(armorItem);

                    if (equipable != null) {
                        return Pair.of(equipable.getEquipmentSlot(), armorItem);
                    }
                }
            }
        }

        return Pair.of(null, null);
    }

    private static void finalizeChampionArmor(LivingEntity entity, Rank rank, EquipmentSlot lootTableArmorSlot) {
        /*
         * Only applies if the entity has armor equipped and is a Mob.
         * Applies drop chance to standard armor.
         * Sets guaranteed drop for loot table armor.
         */
        if (entity instanceof Mob mob) {
            Level level = entity.level();

            if (level instanceof ServerLevel serverLevel) {
                DifficultyInstance difficultyInstance = serverLevel.getCurrentDifficultyAt(entity.blockPosition());

                for (EquipmentSlot slot : getArmorSlots()) {
                    if (!entity.getItemBySlot(slot).isEmpty()) {
                        enchantSpawnedArmor(mob, rank, serverLevel, serverLevel.getRandom(), slot, difficultyInstance);
                        if (slot == lootTableArmorSlot) {
                            mob.setDropChance(slot, ConfigHandler.Common.getLootDropChance(rank));
                        }
                        else {
                            mob.setDropChance(slot, (float) ConfigHandler.Common.getStandardArmorDropChance());
                        }
                    }
                }
            }
        }
    }

    private static void enchantSpawnedWeapon(LivingEntity entity, Rank rank, ServerLevelAccessor level, RandomSource random, DifficultyInstance difficulty) {
        float chance;

        switch (rank) {
            case RARE -> chance = 0.30F;
            case EPIC -> chance = 0.60F;
            case LEGENDARY -> chance = 0.80F;
            default -> chance = 0.15F;
        }
        enchantSpawnedEquipment(entity, level, EquipmentSlot.MAINHAND, random, chance, difficulty, rank);
    }

    private static void enchantSpawnedArmor(LivingEntity entity, Rank rank, ServerLevelAccessor level, RandomSource random, EquipmentSlot slot, DifficultyInstance difficulty) {
        float chance;

        switch (rank) {
            case RARE -> chance = 0.50F;
            case EPIC -> chance = 0.75F;
            case LEGENDARY -> chance = 1.0F;
            default -> chance = 0.25F;
        }
        enchantSpawnedEquipment(entity, level, slot, random, chance, difficulty, rank);
    }

    private static void enchantSpawnedEquipment(LivingEntity entity, ServerLevelAccessor level, EquipmentSlot slot, RandomSource random, float enchantChance, DifficultyInstance difficulty, Rank rank) {
        ItemStack itemstack = entity.getItemBySlot(slot);

        if (!itemstack.isEmpty() && random.nextFloat() < enchantChance * difficulty.getSpecialMultiplier()) {
            EnchantmentHelper.enchantItemFromProvider(itemstack, level.registryAccess(), VanillaEnchantmentProviders.MOB_SPAWN_EQUIPMENT, difficulty, random);
            entity.setItemSlot(slot, itemstack);
        }
    }

    private static void applyChampionEffect(LivingEntity entity, Rank rank) {
        Holder<MobEffect> mobEffectHolder = MobChampionsEffects.getChampionEffectHolderByRank(rank);

        if (mobEffectHolder != null) {
            entity.forceAddEffect(new MobEffectInstance(
                mobEffectHolder, -1
            ), entity);
        }
    }

    private static void applyGlowingEffectIfNeeded(LivingEntity entity, Rank rank) {
        int glowingEffectMinimumRankOrdinal = ConfigHandler.Common.getGlowingEffectMinimumRank().ordinal();

        if (rank.ordinal() >= glowingEffectMinimumRankOrdinal) {
            int glowingEffectDuration = ConfigHandler.Common.getGlowingEffectDuration();

            if (glowingEffectDuration < 0) {
                glowingEffectDuration = Integer.MAX_VALUE;
            }

            entity.addEffect(new MobEffectInstance(
                MobEffects.GLOWING,
                glowingEffectDuration,
                1,
                false,
                false
            ));
        }
    }

    private static void applyInfestedEffectIfNeeded(LivingEntity entity, Rank rank) {
        int infestedEffectMinimumRankOrdinal = ConfigHandler.Common.getInfestedEffectMinimumRank().ordinal();

        if (rank.ordinal() >= infestedEffectMinimumRankOrdinal) {
            double infestedEffectChance = ConfigHandler.Common.getInfestedEffectChance();
            double bonusMultiplier = 1 + ConfigHandler.Common.getLegendaryEffectBonusMultiplier();

            if (rank == Rank.LEGENDARY) {
                infestedEffectChance = infestedEffectChance * bonusMultiplier;
            }

            if (MobChampions.RANDOM.nextFloat() <= infestedEffectChance) {
                MobEffectInstance infestedEffect = new MobEffectInstance(MobEffects.INFESTED, -1);

                entity.addEffect(infestedEffect);
            }
        }
    }

    private static void applyWeavingEffectIfNeeded(LivingEntity entity, Rank rank) {
        int weavingEffectMinimumRankOrdinal = ConfigHandler.Common.getWeavingEffectMinimumRank().ordinal();

        if (rank.ordinal() >= weavingEffectMinimumRankOrdinal) {
            double weavingEffectChance = ConfigHandler.Common.getWeavingEffectChance();
            double bonusMultiplier = 1 + ConfigHandler.Common.getLegendaryEffectBonusMultiplier();

            if (rank == Rank.LEGENDARY) {
                weavingEffectChance = weavingEffectChance * bonusMultiplier;
            }

            if (MobChampions.RANDOM.nextFloat() <= weavingEffectChance) {
                MobEffectInstance weavingEffect = new MobEffectInstance(MobEffects.WEAVING, -1);

                entity.addEffect(weavingEffect);
            }
        }
    }

    private static void applyWindChargedEffectIfNeeded(LivingEntity entity, Rank rank) {
        int windChargedEffectMinimumRankOrdinal = ConfigHandler.Common.getWindChargedEffectMinimumRank().ordinal();

        if (rank.ordinal() >= windChargedEffectMinimumRankOrdinal) {
            double windChargedEffectChance = ConfigHandler.Common.getWindChargedEffectChance();
            double bonusMultiplier = 1 + ConfigHandler.Common.getLegendaryEffectBonusMultiplier();

            if (rank == Rank.LEGENDARY) {
                windChargedEffectChance = windChargedEffectChance * bonusMultiplier;
            }

            if (MobChampions.RANDOM.nextFloat() <= windChargedEffectChance) {
                MobEffectInstance windChargedEffect = new MobEffectInstance(MobEffects.WIND_CHARGED, -1);

                entity.addEffect(windChargedEffect);
            }
        }
    }

    private static void applyFireResistanceEffectIfNeeded(LivingEntity entity, Rank rank) {
        int fireResistanceEffectMinimumRankOrdinal = ConfigHandler.Common.getFireResistanceEffectMinimumRank().ordinal();

        if (rank.ordinal() >= fireResistanceEffectMinimumRankOrdinal) {
            double fireResistanceEffectChance = ConfigHandler.Common.getFireResistanceEffectChance();

            if (MobChampions.RANDOM.nextFloat() <= fireResistanceEffectChance) {
                MobEffectInstance fireResistanceEffect = new MobEffectInstance(
                    MobEffects.FIRE_RESISTANCE,
                    -1,
                    1,
                    false,
                    false
                );

                entity.addEffect(fireResistanceEffect);
            }
        }
    }


    public static void updateMaxHealth(LivingEntity entity) {
        AttributeInstance maxHealthAttribute = entity.getAttribute(Attributes.MAX_HEALTH);

        if (maxHealthAttribute != null) {
            entity.setHealth((float) maxHealthAttribute.getValue());
        }
    }

    public static void addChampionAttribute(AttributeInstance attributeInstance, AttributeModifier modifier) {
        if (attributeInstance != null && !attributeInstance.hasModifier(modifier.id())) {
            attributeInstance.addPermanentModifier(modifier);
        }
    }

    public static List<EquipmentSlot> getArmorSlots() {
        return List.of(
            EquipmentSlot.HEAD,
            EquipmentSlot.CHEST,
            EquipmentSlot.LEGS,
            EquipmentSlot.FEET
        );
    }

}
