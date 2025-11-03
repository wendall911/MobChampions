package mobchampions.platform.services;

import java.util.Optional;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

import mobchampions.network.IMobChampion;

public interface IPlatform {

    /*
     * Send a LaunchFireworksPacket to all players tracking the given entity.
     * This is used to trigger fireworks effects on the client side when a Mob Champion is defeated.
     */
    void sendLaunchFireworksPacket(LivingEntity entity);

    /*
     * Get the Mob Champion data for the given entity, if it exists.
     */
    Optional<? extends IMobChampion> getMobChampionData(LivingEntity entity);

    /*
     * Sync the Mob Champion data for the given entity to the given player.
     * This is triggered when a player first starts tracking an entity.
     */
    void syncMobChampionData(ServerPlayer player, LivingEntity entity);

}
