package mobchampions.network;

import org.jetbrains.annotations.NotNull;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import org.joml.Vector3f;

import mobchampions.MobChampions;

public record LaunchFireworksPacket(Vector3f location) implements CustomPacketPayload {

    public static final Type<LaunchFireworksPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(MobChampions.MODID, "launch_fireworks"));
    public static final StreamCodec<FriendlyByteBuf, LaunchFireworksPacket> STREAM_CODEC =
        StreamCodec.composite(
            ByteBufCodecs.VECTOR3F,
            LaunchFireworksPacket::location,
            LaunchFireworksPacket::new
        );

    public static void handle(Vector3f location) {

    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}
