package mobchampions;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.RegisterEvent;

import mobchampions.attachments.AttachmentsRegistry;
import mobchampions.common.effect.MobChampionsEffects;
import mobchampions.event.ServerEventListener;
import mobchampions.network.MobChampionsNeoForgeNetwork;
import mobchampions.network.LaunchFireworksPacket;
import mobchampions.network.SyncMobChampionData;

@Mod(MobChampions.MODID)
public class MobChampionsNeoForge {

    public MobChampionsNeoForge(IEventBus eventBus) {
        registryInit(eventBus);
        MobChampions.init();
        AttachmentsRegistry.init(eventBus);
        MobChampions.initConfig();
        eventBus.addListener(this::registerPayloadHandler);
        eventBus.addListener(this::setup);
    }

    private void registerPayloadHandler(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(MobChampions.MODID).versioned("1.0");

        registrar.playToClient(LaunchFireworksPacket.TYPE, LaunchFireworksPacket.STREAM_CODEC,
            MobChampionsNeoForgeNetwork.getInstance()::handleFireworksPacket);
        registrar.playToClient(SyncMobChampionData.TYPE, SyncMobChampionData.STREAM_CODEC,
            MobChampionsNeoForgeNetwork.getInstance()::processMobChampionData);
    }

    private void setup(final FMLCommonSetupEvent event) {
        NeoForge.EVENT_BUS.register(ServerEventListener.class);
    }

    private void registryInit(IEventBus bus) {
        bind(bus, Registries.MOB_EFFECT, MobChampionsEffects::init);
    }

    private static <T> void bind(IEventBus bus, ResourceKey<Registry<T>> registry, Consumer<BiConsumer<T, ResourceLocation>> source) {
        bus.addListener((RegisterEvent event) -> {
            if (registry.equals(event.getRegistryKey())) {
                source.accept((t, rl) -> event.register(registry, rl, () -> t));
            }
        });
    }

}
