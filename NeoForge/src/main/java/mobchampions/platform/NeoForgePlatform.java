package mobchampions.platform;

import java.util.Optional;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.network.PacketDistributor;

import org.joml.Vector3f;

import mobchampions.attachments.MobChampionDataAttachment;
import mobchampions.network.IMobChampion;
import mobchampions.network.LaunchFireworksPacket;
import mobchampions.network.SyncMobChampionData;
import mobchampions.platform.services.IPlatform;

public class NeoForgePlatform implements IPlatform {

    @Override
    public void sendLaunchFireworksPacket(LivingEntity entity) {
        Vector3f location = entity.getEyePosition().toVector3f();

        PacketDistributor.sendToPlayersTrackingEntity(entity, new LaunchFireworksPacket(location));
    }

    @Override
    public Optional<? extends IMobChampion> getMobChampionData(LivingEntity entity) {
        return MobChampionDataAttachment.getData(entity);
    }

    @Override
    public void syncMobChampionData(ServerPlayer player, LivingEntity entity) {
        getMobChampionData(entity).ifPresent(data -> {
            PacketDistributor.sendToAllPlayers(
                new SyncMobChampionData(data.getEntityId(), data.getRank())
            );
        });
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }

}
