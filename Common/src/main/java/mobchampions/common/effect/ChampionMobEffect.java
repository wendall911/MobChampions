package mobchampions.common.effect;

import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

import mobchampions.config.ConfigHandler;
import mobchampions.network.MobChampion;

public class ChampionMobEffect extends MobEffect {

    private static MobChampion.Rank rank;

    protected ChampionMobEffect(MobChampion.Rank rank) {
        super(MobEffectCategory.NEUTRAL, -1, ColorParticleOption.create(
            ParticleTypes.ENTITY_EFFECT,
            ConfigHandler.Client.getChampionColor(rank)
        ));

        ChampionMobEffect.rank = rank;
    }

    @Override
    public int getColor() {
        return ConfigHandler.Client.getChampionColor(rank);
    }

}
