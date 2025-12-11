package mobchampions.common.effect;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;

import mobchampions.MobChampions;
import mobchampions.network.MobChampion.Rank;

public class MobChampionsEffects {

    public static final MobEffect UNCOMMON_CHAMPION_EFFECT = new ChampionMobEffect(Rank.UNCOMMON);
    public static final Identifier UNCOMMON_CHAMPION_EFFECT_ID = MobChampions.prefix("uncommon_champion_effect");
    public static Holder<MobEffect> UNCOMMON_CHAMPION_EFFECT_HOLDER = null;
    public static final MobEffect RARE_CHAMPION_EFFECT = new ChampionMobEffect(Rank.RARE);
    public static final Identifier RARE_CHAMPION_EFFECT_ID = MobChampions.prefix("rare_champion_effect");
    public static Holder<MobEffect> RARE_CHAMPION_EFFECT_HOLDER = null;
    public static final MobEffect EPIC_CHAMPION_EFFECT = new ChampionMobEffect(Rank.EPIC);
    public static final Identifier EPIC_CHAMPION_EFFECT_ID = MobChampions.prefix("epic_champion_effect");
    public static Holder<MobEffect> EPIC_CHAMPION_EFFECT_HOLDER = null;
    public static final MobEffect LEGENDARY_CHAMPION_EFFECT = new ChampionMobEffect(Rank.LEGENDARY);
    public static final Identifier LEGENDARY_CHAMPION_EFFECT_ID = MobChampions.prefix("legendary_champion_effect");
    public static Holder<MobEffect> LEGENDARY_CHAMPION_EFFECT_HOLDER = null;
    public static final List<Holder<MobEffect>> ALL_CHAMPION_EFFECT_HOLDERS = new ArrayList<>(4);

    public static void init(BiConsumer<MobEffect, Identifier> consumer) {
        register(consumer, Rank.UNCOMMON);
        register(consumer, Rank.RARE);
        register(consumer, Rank.EPIC);
        register(consumer, Rank.LEGENDARY);
    }


    private static void register(BiConsumer<MobEffect, Identifier> consumer, Rank rank) {
        switch (rank) {
            case UNCOMMON -> {
                consumer.accept(UNCOMMON_CHAMPION_EFFECT, UNCOMMON_CHAMPION_EFFECT_ID);
                UNCOMMON_CHAMPION_EFFECT_HOLDER = BuiltInRegistries.MOB_EFFECT.wrapAsHolder(UNCOMMON_CHAMPION_EFFECT);
                ALL_CHAMPION_EFFECT_HOLDERS.add(UNCOMMON_CHAMPION_EFFECT_HOLDER);
            }
            case RARE -> {
                consumer.accept(RARE_CHAMPION_EFFECT, RARE_CHAMPION_EFFECT_ID);
                RARE_CHAMPION_EFFECT_HOLDER = BuiltInRegistries.MOB_EFFECT.wrapAsHolder(RARE_CHAMPION_EFFECT);
                ALL_CHAMPION_EFFECT_HOLDERS.add(RARE_CHAMPION_EFFECT_HOLDER);
            }
            case EPIC -> {
                consumer.accept(EPIC_CHAMPION_EFFECT, EPIC_CHAMPION_EFFECT_ID);
                EPIC_CHAMPION_EFFECT_HOLDER = BuiltInRegistries.MOB_EFFECT.wrapAsHolder(EPIC_CHAMPION_EFFECT);
                ALL_CHAMPION_EFFECT_HOLDERS.add(EPIC_CHAMPION_EFFECT_HOLDER);
            }
            case LEGENDARY -> {
                consumer.accept(LEGENDARY_CHAMPION_EFFECT, LEGENDARY_CHAMPION_EFFECT_ID);
                LEGENDARY_CHAMPION_EFFECT_HOLDER = BuiltInRegistries.MOB_EFFECT.wrapAsHolder(LEGENDARY_CHAMPION_EFFECT);
                ALL_CHAMPION_EFFECT_HOLDERS.add(LEGENDARY_CHAMPION_EFFECT_HOLDER);
            }
        }
    }

    public static Holder<MobEffect> getChampionEffectHolderByRank(Rank rank) {
        return switch (rank) {
            case COMMON -> null;
            case UNCOMMON -> UNCOMMON_CHAMPION_EFFECT_HOLDER;
            case RARE -> RARE_CHAMPION_EFFECT_HOLDER;
            case EPIC -> EPIC_CHAMPION_EFFECT_HOLDER;
            case LEGENDARY -> LEGENDARY_CHAMPION_EFFECT_HOLDER;
        };
    }

}
