package mobchampions.platform.services;

import java.util.Optional;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Monster;

import mobchampions.network.IMobChampion;

public interface IPlatform {

    void sendLaunchFireworksPacket(Monster monster);

    Optional<? extends IMobChampion> getMobChampionData(Entity entity);

}
