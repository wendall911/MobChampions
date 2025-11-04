package mobchampions.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;

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

    public static void applyChampionAttributesAndEffects(LivingEntity entity, Rank rank) {
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

}
