package mobchampions;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import mobchampions.attachments.AttachmentsRegistry;
import mobchampions.network.MobChampionsNeoForgeNetwork;
import mobchampions.network.LaunchFireworksPacket;
import mobchampions.network.SyncMobChampionData;

@Mod(MobChampions.MODID)
public class MobChampionsNeoForge {

    public MobChampionsNeoForge(IEventBus eventBus) {
        MobChampions.init();
        AttachmentsRegistry.init(eventBus);
        MobChampions.initConfig();
        eventBus.addListener(this::registerPayloadHandler);
    }

    private void registerPayloadHandler(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(MobChampions.MODID).versioned("1.0");

        registrar.playToClient(LaunchFireworksPacket.TYPE, LaunchFireworksPacket.STREAM_CODEC,
            MobChampionsNeoForgeNetwork.getInstance()::handleFireworksPacket);
        registrar.playToClient(SyncMobChampionData.TYPE, SyncMobChampionData.STREAM_CODEC,
            MobChampionsNeoForgeNetwork.getInstance()::processMobChampionData);
    }

}
