package mobchampions.common.stats;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import mobchampions.MobChampions;
import mobchampions.config.ConfigHandler;
import mobchampions.network.MobChampion.Rank;
import mobchampions.platform.Services;

public class ChampionStatsManager {

    private static final Map<Integer, ChampionStats> UNCOMMON = new HashMap<>(10);
    private static final Map<Integer, ChampionStats> RARE = new HashMap<>(10);
    private static final Map<Integer, ChampionStats> EPIC = new HashMap<>(10);
    private static final Map<Integer, ChampionStats> LEGENDARY = new HashMap<>(10);

    private static final ChampionStats uncommonBase = new ChampionStats(
        0.5F,
        2,
        0.05F,
        0.1F,
        0.7F,
        0.03F
    );
    private static final ChampionStats rareBase = new ChampionStats(
        0.9F,
        4,
        0.15F,
        0.4F,
        1.4F,
        0.1F
    );
    private static final ChampionStats epicBase = new ChampionStats(
        1.8F,
        8,
        0.3F,
        0.7F,
        2.2F,
        0.5F
    );
    private static final ChampionStats legendaryBase = new ChampionStats(
        2.7F,
        15,
        0.5F,
        0.9F,
        2.9F,
        1.0F
    );

    public static void init() {
        for (int level = 1; level <= 10; level++) {
            UNCOMMON.put(level, new ChampionStats(
                scaleStat(uncommonBase.getHealthMultiplier(), level),
                scaleStat(uncommonBase.getArmorAddition(), level),
                scaleStat(uncommonBase.getMovementSpeedMultiplier(), level),
                scaleStat(uncommonBase.getAttackDamageMultiplier(), level),
                scaleStat(uncommonBase.getArrowDamageMultiplier(), level),
                scaleStat(uncommonBase.getKnockbackResistanceAddition(), level)
            ));
            RARE.put(level, new ChampionStats(
                scaleStat(rareBase.getHealthMultiplier(), level),
                scaleStat(rareBase.getArmorAddition(), level),
                scaleStat(rareBase.getMovementSpeedMultiplier(), level),
                scaleStat(rareBase.getAttackDamageMultiplier(), level),
                scaleStat(rareBase.getArrowDamageMultiplier(), level),
                scaleStat(rareBase.getKnockbackResistanceAddition(), level)
            ));
            EPIC.put(level, new ChampionStats(
                scaleStat(epicBase.getHealthMultiplier(), level),
                scaleStat(epicBase.getArmorAddition(), level),
                scaleStat(epicBase.getMovementSpeedMultiplier(), level),
                scaleStat(epicBase.getAttackDamageMultiplier(), level),
                scaleStat(epicBase.getArrowDamageMultiplier(), level),
                scaleStat(epicBase.getKnockbackResistanceAddition(), level)
            ));
            LEGENDARY.put(level, new ChampionStats(
                scaleStat(legendaryBase.getHealthMultiplier(), level),
                scaleStat(legendaryBase.getArmorAddition(), level),
                scaleStat(legendaryBase.getMovementSpeedMultiplier(), level),
                scaleStat(legendaryBase.getAttackDamageMultiplier(), level),
                scaleStat(legendaryBase.getArrowDamageMultiplier(), level),
                scaleStat(legendaryBase.getKnockbackResistanceAddition(), level)
            ));
        }

        if (Services.PLATFORM.isDevelopmentEnvironment()) {
            for (Entry<Integer, ChampionStats> entry : UNCOMMON.entrySet()) {
                MobChampions.LOGGER.warn("Uncommon Level {}: {}", entry.getKey(), entry.getValue().toString());
            }
            for (Entry<Integer, ChampionStats> entry : RARE.entrySet()) {
                MobChampions.LOGGER.warn("Rare Level {}: {}", entry.getKey(), entry.getValue().toString());
            }
            for (Entry<Integer, ChampionStats> entry : EPIC.entrySet()) {
                MobChampions.LOGGER.warn("Epic Level {}: {}", entry.getKey(), entry.getValue().toString());
            }
            for (Entry<Integer, ChampionStats> entry : LEGENDARY.entrySet()) {
                MobChampions.LOGGER.warn("Legendary Level {}: {}", entry.getKey(), entry.getValue().toString());
            }
        }
    }

    private static float scaleStat(float base, int level) {
        return (base / 5) * level;
    }

    private static int scaleStat(int base, int level) {
        return (int) Math.ceil(((double) base / 5) * level);
    }

    public static ChampionStats getStatsForRank(Rank rank) {
        Map<Integer, ChampionStats> statsMap = switch (rank) {
            case RARE -> RARE;
            case EPIC -> EPIC;
            case LEGENDARY -> LEGENDARY;
            default -> UNCOMMON;
        };

        return statsMap.get(ConfigHandler.Common.getDifficultyForRank(rank));
    }

}
