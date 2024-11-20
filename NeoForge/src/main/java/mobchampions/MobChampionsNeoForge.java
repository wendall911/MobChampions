package mobchampions;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import mobchampions.network.MobChampionsNeoForgeNetwork;
import mobchampions.network.LaunchFireworksPacket;

@Mod(MobChampions.MODID)
public class MobChampionsNeoForge {

    public MobChampionsNeoForge(IEventBus eventBus) {
        MobChampions.init();
    }

    private void registerPayloadHandler(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(MobChampions.MODID).versioned("1.0");

        registrar.playToClient(LaunchFireworksPacket.TYPE, LaunchFireworksPacket.STREAM_CODEC,
            MobChampionsNeoForgeNetwork.getInstance()::handleFireworksPacket);
    }

}

