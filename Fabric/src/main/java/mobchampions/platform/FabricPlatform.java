package mobchampions.platform;

import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;

import net.minecraft.server.level.ServerPlayer;

import mobchampions.network.LaunchFireworksPacket;
import mobchampions.platform.services.IPlatform;
import net.minecraft.world.entity.monster.Monster;

public class FabricPlatform implements IPlatform {

    @Override
    public boolean isModLoaded(String name) {
        return FabricLoader.getInstance().isModLoaded(name);
    }

	@Override
    public boolean isPhysicalClient() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    }

    @Override
    public void sendLaunchFireworksPacket(Monster monster) {
        for (ServerPlayer sp : PlayerLookup.tracking(monster)) {
            ServerPlayNetworking.send(sp, new LaunchFireworksPacket(monster.getEyePosition().toVector3f()));
        }
    }

}

