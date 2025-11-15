package mobchampions;

import java.util.Random;

import net.minecraft.resources.ResourceLocation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import technology.roughness.whitenoise.config.WhiteNoiseConfig;
import technology.roughness.whitenoise.config.WhiteNoiseConfigLoader;
import technology.roughness.whitenoise.platform.Services;

import mobchampions.common.stats.ChampionStatsManager;
import mobchampions.config.ConfigHandler;
import mobchampions.loot.MobChampionsLootTables;

import static technology.roughness.whitenoise.util.ResourceLocationHelper.loc;

public class MobChampions {

    public static final String MODID = "mobchampions";
    public static final String MOD_NAME = "Mob Champions";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);
    public static final Random RANDOM = new Random();

    public static void init() {
        MobChampionsLootTables.init();
    }

    public static void initConfig() {
        ChampionStatsManager.init();

        if (Services.PLATFORM.isPhysicalClient()) {
            WhiteNoiseConfig clientConfig = WhiteNoiseConfigLoader.add(WhiteNoiseConfig.Type.CLIENT, ConfigHandler.CLIENT_SPEC, MODID);
            clientConfig.addLoadListener((config, flag) -> ConfigHandler.clientInit());
        }

        WhiteNoiseConfigLoader.add(WhiteNoiseConfig.Type.COMMON, ConfigHandler.COMMON_SPEC, MODID);
    }

    public static ResourceLocation prefix(String path) {
        return loc(MODID, path);
    }

}
