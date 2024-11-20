package mobchampions.platform.services;

import net.minecraft.world.entity.monster.Monster;

public interface IPlatform {

    boolean isModLoaded(String name);

    boolean isPhysicalClient();

    void sendLaunchFireworksPacket(Monster monster);

}
