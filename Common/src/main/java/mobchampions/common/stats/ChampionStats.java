package mobchampions.common.stats;

public class ChampionStats {

    private final float healthMultiplier;
    private final int armorAddition;
    private final float movementSpeedMultiplier;
    private final float attackDamageMultiplier;
    private final float arrowDamageMultiplier;
    private final float knockbackResistanceAddition;

    public ChampionStats(float healthMultiplier, int armorAddition, float movementSpeedMultiplier,
            float attackDamageMultiplier, float arrowDamageMultiplier, float knockbackResistanceAddition) {
        this.healthMultiplier = healthMultiplier;
        this.armorAddition = armorAddition;
        this.movementSpeedMultiplier = movementSpeedMultiplier;
        this.attackDamageMultiplier = attackDamageMultiplier;
        this.arrowDamageMultiplier = arrowDamageMultiplier;
        this.knockbackResistanceAddition = knockbackResistanceAddition;
    }

    public float getHealthMultiplier() {
        return healthMultiplier;
    }

    public int getArmorAddition() {
        return armorAddition;
    }

    public float getMovementSpeedMultiplier() {
        return movementSpeedMultiplier;
    }

    public float getAttackDamageMultiplier() {
        return attackDamageMultiplier;
    }

    public float getArrowDamageMultiplier() {
        return arrowDamageMultiplier;
    }

    public float getKnockbackResistanceAddition() {
        return knockbackResistanceAddition;
    }

    public String toString() {
        return "ChampionStats{healthMultiplier=" + healthMultiplier + ", armorAddition=" + armorAddition
            + ", movementSpeedMultiplier=" + movementSpeedMultiplier + ", attackDamageMultiplier="
            + attackDamageMultiplier + ", arrowDamageMultiplier=" + arrowDamageMultiplier
            + ", knockbackResistanceAddition=" + knockbackResistanceAddition + "}";
}

}
