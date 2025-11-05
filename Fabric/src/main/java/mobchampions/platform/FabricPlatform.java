package mobchampions.platform;

import java.util.Optional;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

import mobchampions.components.MobChampionsCardinalComponents;
import mobchampions.network.IMobChampion;
import mobchampions.network.LaunchFireworksPacket;
import mobchampions.platform.services.IPlatform;

public class FabricPlatform implements IPlatform {

    @Override
    public void sendLaunchFireworksPacket(LivingEntity entity) {
        for (ServerPlayer sp : PlayerLookup.tracking(entity)) {
            ServerPlayNetworking.send(sp, new LaunchFireworksPacket(entity.getEyePosition().toVector3f()));
        }
    }

    @Override
    public Optional<? extends IMobChampion> getMobChampionData(LivingEntity entity) {
        return MobChampionsCardinalComponents.MOB_CHAMPION_DATA.maybeGet(entity);
    }

    @Override
    public void syncMobChampionData(ServerPlayer player, LivingEntity entity) {
        // No-op: Fabric handles syncing automatically via Cardinal Components
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

}

