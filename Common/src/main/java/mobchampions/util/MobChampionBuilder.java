package mobchampions.util;

import java.util.List;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.providers.VanillaEnchantmentProviders;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import mobchampions.MobChampions;
import mobchampions.config.ConfigHandler;
import mobchampions.network.MobChampion.Rank;

public class MobChampionBuilder {

    private static final AttributeModifier UNCOMMON_HEALTH_MODIFIER = new AttributeModifier(
        MobChampions.prefix("uncommon_health_multiplier"),
        ConfigHandler.Common.getUncommonHealthMultiplier(),
        AttributeModifier.Operation.ADD_MULTIPLIED_BASE
    );
    private static final AttributeModifier UNCOMMON_ARMOR_MODIFIER = new AttributeModifier(
        MobChampions.prefix("uncommon_armor_addition"),
        ConfigHandler.Common.getUncommonArmorAddition(),
        AttributeModifier.Operation.ADD_VALUE
    );
    private static final AttributeModifier UNCOMMON_MOVEMENT_SPEED_MODIFIER = new AttributeModifier(
        MobChampions.prefix("uncommon_movement_speed_multiplier"),
        ConfigHandler.Common.getUncommonMovementSpeedMultiplier(),
        Operation.ADD_MULTIPLIED_BASE
    );
    private static final AttributeModifier UNCOMMON_ATTACK_DAMAGE_MODIFIER = new AttributeModifier(
        MobChampions.prefix("uncommon_attack_damage_multiplier"),
        ConfigHandler.Common.getUncommonAttackDamageMultiplier(),
        Operation.ADD_MULTIPLIED_BASE
    );
    private static final AttributeModifier UNCOMMON_KNOCKBACK_RESISTANCE_MODIFIER = new AttributeModifier(
        MobChampions.prefix("uncommon_knockback_resistance_addition"),
        ConfigHandler.Common.getUncommonKnockbackResistanceAddition(),
        Operation.ADD_VALUE
    );
    private static final AttributeModifier RARE_HEALTH_MODIFIER = new AttributeModifier(
        MobChampions.prefix("rare_health_multiplier"),
        ConfigHandler.Common.getRareHealthMultiplier(),
        AttributeModifier.Operation.ADD_MULTIPLIED_BASE
    );
    private static final AttributeModifier RARE_ARMOR_MODIFIER = new AttributeModifier(
        MobChampions.prefix("rare_armor_addition"),
        ConfigHandler.Common.getRareArmorAddition(),
        AttributeModifier.Operation.ADD_VALUE
    );
    private static final AttributeModifier RARE_MOVEMENT_SPEED_MODIFIER = new AttributeModifier(
        MobChampions.prefix("rare_movement_speed_multiplier"),
        ConfigHandler.Common.getRareMovementSpeedMultiplier(),
        Operation.ADD_MULTIPLIED_BASE
    );
    private static final AttributeModifier RARE_ATTACK_DAMAGE_MODIFIER = new AttributeModifier(
        MobChampions.prefix("rare_attack_damage_multiplier"),
        ConfigHandler.Common.getRareAttackDamageMultiplier(),
        Operation.ADD_MULTIPLIED_BASE
    );
    private static final AttributeModifier RARE_KNOCKBACK_RESISTANCE_MODIFIER = new AttributeModifier(
        MobChampions.prefix("rare_knockback_resistance_addition"),
        ConfigHandler.Common.getRareKnockbackResistanceAddition(),
        Operation.ADD_VALUE
    );
    private static final AttributeModifier EPIC_HEALTH_MODIFIER = new AttributeModifier(
        MobChampions.prefix("epic_health_multiplier"),
        ConfigHandler.Common.getEpicHealthMultiplier(),
        AttributeModifier.Operation.ADD_MULTIPLIED_BASE
    );
    private static final AttributeModifier EPIC_ARMOR_MODIFIER = new AttributeModifier(
        MobChampions.prefix("epic_armor_addition"),
        ConfigHandler.Common.getEpicArmorAddition(),
        AttributeModifier.Operation.ADD_VALUE
    );
    private static final AttributeModifier EPIC_MOVEMENT_SPEED_MODIFIER = new AttributeModifier(
        MobChampions.prefix("epic_movement_speed_multiplier"),
        ConfigHandler.Common.getEpicMovementSpeedMultiplier(),
        Operation.ADD_MULTIPLIED_BASE
    );
    private static final AttributeModifier EPIC_ATTACK_DAMAGE_MODIFIER = new AttributeModifier(
        MobChampions.prefix("epic_attack_damage_multiplier"),
        ConfigHandler.Common.getEpicAttackDamageMultiplier(),
        Operation.ADD_MULTIPLIED_BASE
    );
    private static final AttributeModifier EPIC_KNOCKBACK_RESISTANCE_MODIFIER = new AttributeModifier(
        MobChampions.prefix("epic_knockback_resistance_addition"),
        ConfigHandler.Common.getEpicKnockbackResistanceAddition(),
        Operation.ADD_VALUE
    );
    private static final AttributeModifier LEGENDARY_HEALTH_MODIFIER = new AttributeModifier(
        MobChampions.prefix("legendary_health_multiplier"),
        ConfigHandler.Common.getLegendaryHealthMultiplier(),
        AttributeModifier.Operation.ADD_MULTIPLIED_BASE
    );
    private static final AttributeModifier LEGENDARY_ARMOR_MODIFIER = new AttributeModifier(
        MobChampions.prefix("legendary_armor_addition"),
        ConfigHandler.Common.getLegendaryArmorAddition(),
        AttributeModifier.Operation.ADD_VALUE
    );
    private static final AttributeModifier LEGENDARY_MOVEMENT_SPEED_MODIFIER = new AttributeModifier(
        MobChampions.prefix("legendary_movement_speed_multiplier"),
        ConfigHandler.Common.getLegendaryMovementSpeedMultiplier(),
        Operation.ADD_MULTIPLIED_BASE
    );
    private static final AttributeModifier LEGENDARY_ATTACK_DAMAGE_MODIFIER = new AttributeModifier(
        MobChampions.prefix("legendary_attack_damage_multiplier"),
        ConfigHandler.Common.getLegendaryAttackDamageMultiplier(),
        Operation.ADD_MULTIPLIED_BASE
    );
    private static final AttributeModifier LEGENDARY_KNOCKBACK_RESISTANCE_MODIFIER = new AttributeModifier(
        MobChampions.prefix("legendary_knockback_resistance_addition"),
        ConfigHandler.Common.getLegendaryKnockbackResistanceAddition(),
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
        updateMaxHealth(entity);
        applyGlowingEffectIfNeeded(entity, rank);
        applyInfestedEffectIfNeeded(entity, rank);
        applyOozingEffectIfNeeded(entity, rank);
        applyWeavingEffectIfNeeded(entity, rank);
        applyWindChargedEffectIfNeeded(entity, rank);
        equipChampionWeaponIfNeeded(entity, rank);
        equipChampionGearIfNeeded(entity, rank);
    }

    private static void equipChampionWeaponIfNeeded(LivingEntity entity, Rank rank) {
        boolean hasLootTableWeapon = false;
        /*
         * First check if weapon equipping is allowed for this entity.
         * Then check if the entity already has a weapon.
         * If not, attempt to equip a weapon based on the rank.
         */
        if (entity.canUseSlot(EquipmentSlot.MAINHAND) && entity.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
            ItemStack weapon = ConfigHandler.Common.getWeaponForRank(rank);

            if (weapon != null) {
                entity.setItemSlot(EquipmentSlot.MAINHAND, weapon);
            }
        }

        /*
         * Next we will check if there is a need to update the main weapon with a loot table weapon.
         * These will override the normal weapon if present.
         * These weapons will drop normally on death.
         */

        finalizeChampionWeapon(entity, rank, hasLootTableWeapon);
    }

    private static void finalizeChampionWeapon(LivingEntity entity, Rank rank, boolean hasLootTableWeapon) {
        /*
         * Only applies if the entity has a weapon equipped and is a Mob.
         * Applies drop chance to standard weapons.
         * Sets guaranteed drop for loot table weapons.
         */
        if (!entity.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty() && entity instanceof Mob mob) {
            Level level = entity.level();

            if (level instanceof ServerLevel serverLevel) {
                DifficultyInstance difficultyInstance = serverLevel.getCurrentDifficultyAt(entity.blockPosition());

                enchantSpawnedWeapon(mob, rank, serverLevel, serverLevel.getRandom(), difficultyInstance);
            }

            if (hasLootTableWeapon) {
                mob.setGuaranteedDrop(EquipmentSlot.MAINHAND);
            }
            else {
                mob.setDropChance(EquipmentSlot.MAINHAND, (float) ConfigHandler.Common.getStandardWeaponDropChance());
            }
        }
    }

    private static void equipChampionGearIfNeeded(LivingEntity entity, Rank rank) {
        boolean hasLootTableArmor = false;
        /*
         * First check if armor equipping is allowed for this entity.
         * Then check if the entity already has the armor item.
         * If not, attempt to equip an armor item based on the rank.
         */
        for (EquipmentSlot slot : getArmorSlots()) {
            if (entity.canUseSlot(slot) && entity.getItemBySlot(slot).isEmpty()) {
                ItemStack armor = ConfigHandler.Common.getArmorForRankAndSlot(rank, slot);

                if (armor != null) {
                    entity.setItemSlot(slot, armor);
                }
            }
        }

        /*
         * Finalize champion armor by enchanting it if needed.
         */
        finalizeChampionArmor(entity, rank, hasLootTableArmor);
    }

    private static void finalizeChampionArmor(LivingEntity entity, Rank rank, boolean hasLootTableArmor) {
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
                        if (hasLootTableArmor) {
                            mob.setGuaranteedDrop(slot);
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
        enchantSpawnedEquipment(entity, level, EquipmentSlot.MAINHAND, random, chance, difficulty);
    }

    private static void enchantSpawnedArmor(LivingEntity entity, Rank rank, ServerLevelAccessor level, RandomSource random, EquipmentSlot slot, DifficultyInstance difficulty) {
        float chance;

        switch (rank) {
            case RARE -> chance = 0.50F;
            case EPIC -> chance = 0.75F;
            case LEGENDARY -> chance = 1.0F;
            default -> chance = 0.25F;
        }
        enchantSpawnedEquipment(entity, level, slot, random, chance, difficulty);
    }

    private static void enchantSpawnedEquipment(LivingEntity entity, ServerLevelAccessor level, EquipmentSlot slot, RandomSource random, float enchantChance, DifficultyInstance difficulty) {
        ItemStack itemstack = entity.getItemBySlot(slot);

        if (!itemstack.isEmpty() && random.nextFloat() < enchantChance * difficulty.getSpecialMultiplier()) {
            EnchantmentHelper.enchantItemFromProvider(itemstack, level.registryAccess(), VanillaEnchantmentProviders.MOB_SPAWN_EQUIPMENT, difficulty, random);
            entity.setItemSlot(slot, itemstack);
        }

    }


    private static void applyGlowingEffectIfNeeded(LivingEntity entity, Rank rank) {
        int glowingEffectMinimumRankOrdinal = ConfigHandler.Common.getGlowingEffectMinimumRank().ordinal();

        if (rank.ordinal() >= glowingEffectMinimumRankOrdinal) {
            int glowingEffectDuration = ConfigHandler.Common.getGlowingEffectDuration();

            entity.addEffect(new MobEffectInstance(
                MobEffects.GLOWING,
                glowingEffectDuration
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

            if (MobChampions.RANDOM.nextFloat() < infestedEffectChance) {
                MobEffectInstance infestedEffect = new MobEffectInstance(MobEffects.INFESTED, -1);

                entity.addEffect(infestedEffect);
            }
        }
    }

    private static void applyOozingEffectIfNeeded(LivingEntity entity, Rank rank) {
        int oozingEffectMinimumRankOrdinal = ConfigHandler.Common.getOozingEffectMinimumRank().ordinal();

        if (rank.ordinal() >= oozingEffectMinimumRankOrdinal) {
            double oozingEffectChance = ConfigHandler.Common.getOozingEffectChance();
            double bonusMultiplier = 1 + ConfigHandler.Common.getLegendaryEffectBonusMultiplier();

            if (rank == Rank.LEGENDARY) {
                oozingEffectChance = oozingEffectChance * bonusMultiplier;
            }

            if (MobChampions.RANDOM.nextFloat() < oozingEffectChance) {
                MobEffectInstance oozingEffect = new MobEffectInstance(MobEffects.OOZING, -1);

                entity.addEffect(oozingEffect);
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

            if (MobChampions.RANDOM.nextFloat() < weavingEffectChance) {
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

            if (MobChampions.RANDOM.nextFloat() < windChargedEffectChance) {
                MobEffectInstance windChargedEffect = new MobEffectInstance(MobEffects.WIND_CHARGED, -1);

                entity.addEffect(windChargedEffect);
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
