package mobchampions;

import java.util.Random;

import com.illusivesoulworks.spectrelib.config.SpectreConfig;
import com.illusivesoulworks.spectrelib.config.SpectreConfigLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mobchampions.config.ConfigHandler;
import mobchampions.platform.Services;

public class MobChampions {

    public static final String MODID = "mobchampions";
    public static final String MOD_NAME = "Mob Champions";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);
    public static final Random RANDOM = new Random();

    public static void init() {
        if (Services.PLATFORM.isPhysicalClient()) {
            SpectreConfig clientConfig = SpectreConfigLoader.add(SpectreConfig.Type.CLIENT, ConfigHandler.CLIENT_SPEC, MODID);
            clientConfig.addLoadListener((config, flag) -> ConfigHandler.init());
        }

        SpectreConfigLoader.add(SpectreConfig.Type.COMMON, ConfigHandler.COMMON_SPEC, MODID);
    }

}
