package mobchampions.platform;

import java.util.Optional;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Monster;
import net.neoforged.neoforge.network.PacketDistributor;

import org.joml.Vector3f;

import mobchampions.network.IMobChampion;
import mobchampions.network.LaunchFireworksPacket;
import mobchampions.platform.services.IPlatform;

public class NeoForgePlatform implements IPlatform {

    @Override
    public void sendLaunchFireworksPacket(Monster monster) {
        Vector3f location = monster.getEyePosition().toVector3f();

        PacketDistributor.sendToPlayersTrackingEntity(monster, new LaunchFireworksPacket(location));
    }

    @Override
    public Optional<? extends IMobChampion> getMobChampionData(Entity entity) {
        return Optional.empty();
    }

}
