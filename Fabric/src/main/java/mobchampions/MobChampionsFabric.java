package mobchampions;

import java.util.function.BiConsumer;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

import mobchampions.common.effect.MobChampionsEffects;
import mobchampions.network.LaunchFireworksPacket;
import mobchampions.network.SyncMobChampionData;

public class MobChampionsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        registryInit();
        MobChampions.init();
        PayloadTypeRegistry.playS2C().register(LaunchFireworksPacket.TYPE, LaunchFireworksPacket.STREAM_CODEC);
        PayloadTypeRegistry.playS2C().register(SyncMobChampionData.TYPE, SyncMobChampionData.STREAM_CODEC);
    }

    private void registryInit() {
        MobChampionsEffects.init(bind(BuiltInRegistries.MOB_EFFECT));
    }

    private static <T> BiConsumer<T, Identifier> bind(Registry<? super T> registry) {
        return (t, id) -> Registry.register(registry, id, t);
    }

}
