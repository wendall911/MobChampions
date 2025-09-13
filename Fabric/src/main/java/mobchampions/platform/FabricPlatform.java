package mobchampions.platform;

import java.util.Optional;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Monster;

import mobchampions.network.IMobChampion;
import mobchampions.network.LaunchFireworksPacket;
import mobchampions.platform.services.IPlatform;

public class FabricPlatform implements IPlatform {

    @Override
    public void sendLaunchFireworksPacket(Monster monster) {
        for (ServerPlayer sp : PlayerLookup.tracking(monster)) {
            ServerPlayNetworking.send(sp, new LaunchFireworksPacket(monster.getEyePosition().toVector3f()));
        }
    }

    @Override
    public Optional<? extends IMobChampion> getMobChampionData(Entity entity) {
        return Optional.empty();
    }

}

