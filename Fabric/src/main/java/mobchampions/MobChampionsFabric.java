package mobchampions;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

import mobchampions.network.LaunchFireworksPacket;
import mobchampions.network.SyncMobChampionData;

public class MobChampionsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        PayloadTypeRegistry.playS2C().register(LaunchFireworksPacket.TYPE, LaunchFireworksPacket.STREAM_CODEC);
        PayloadTypeRegistry.playS2C().register(SyncMobChampionData.TYPE, SyncMobChampionData.STREAM_CODEC);
    }

}

