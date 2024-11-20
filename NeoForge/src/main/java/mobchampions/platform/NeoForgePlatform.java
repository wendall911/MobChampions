package mobchampions.platform;

import net.minecraft.world.entity.monster.Monster;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.network.PacketDistributor;

import org.joml.Vector3f;

import mobchampions.network.LaunchFireworksPacket;
import mobchampions.platform.services.IPlatform;

public class NeoForgePlatform implements IPlatform {

    @Override
    public boolean isModLoaded(String name) {
        return ModList.get().isLoaded(name);
    }

    @Override
    public boolean isPhysicalClient() {
        return FMLLoader.getDist() == Dist.CLIENT;
    }

    @Override
    public void sendLaunchFireworksPacket(Monster monster) {
        Vector3f location = monster.getEyePosition().toVector3f();

        PacketDistributor.sendToPlayersTrackingEntity(monster, new LaunchFireworksPacket(location));
    }

}

