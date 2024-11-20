package mobchampions.util;

import java.util.ArrayList;
import java.util.List;

import it.unimi.dsi.fastutil.ints.IntList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.FireworkParticles;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.world.item.component.FireworkExplosion;

import org.joml.Vector3f;

import mobchampions.MobChampions;
import mobchampions.config.ConfigHandler;

public class FireworksHelper {

    public static void launchFireworks(ClientLevel level, Vector3f location) {
        if (showFireworks()) {
            ParticleEngine particleEngine = Minecraft.getInstance().particleEngine;

            particleEngine.add(
                new FireworkParticles.Starter(
                    level,
                    location.x(),
                    location.y() + ConfigHandler.Client.getFireworksHeight(),
                    location.z(),
                    0, 0, 0,
                    particleEngine,
                    getConfiguredFirework()
                )
            );
        }
    }

    private static List<FireworkExplosion> getConfiguredFirework() {
        List<FireworkExplosion> explosionList = new ArrayList<>();

        explosionList.add(
            new FireworkExplosion(
                ConfigHandler.Client.getFireworksShape(),
                ConfigHandler.Client.getColorsList(),
                IntList.of(),
                ConfigHandler.Client.fireworksTrail(),
                ConfigHandler.Client.fireworksFlicker()
            )
        );

        return explosionList;
    }

    private static boolean showFireworks() {
        int fireworksChance = ConfigHandler.Client.fireworksChance();

        return fireworksChance > 0 && MobChampions.RANDOM.nextInt(100) <= fireworksChance;
    }

}
