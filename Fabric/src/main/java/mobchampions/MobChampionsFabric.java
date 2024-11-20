package mobchampions;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

import mobchampions.network.LaunchFireworksPacket;

public class MobChampionsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        PayloadTypeRegistry.playS2C().register(LaunchFireworksPacket.TYPE, LaunchFireworksPacket.STREAM_CODEC);
    }

}

