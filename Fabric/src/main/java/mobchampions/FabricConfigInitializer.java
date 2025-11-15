package mobchampions;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

import technology.roughness.whitenoise.config.WhiteNoiseConfigInitializer;

import mobchampions.config.ConfigHandler;

public class FabricConfigInitializer implements WhiteNoiseConfigInitializer {

    @Override
    public void onInitializeConfig() {
        MobChampions.initConfig();

        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            ConfigHandler.commonInit();
        });
    }

}
