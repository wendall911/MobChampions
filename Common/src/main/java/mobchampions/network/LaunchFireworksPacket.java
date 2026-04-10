package mobchampions.network;

import org.jspecify.annotations.NonNull;

import io.netty.buffer.ByteBuf;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

import org.joml.Vector3f;

import mobchampions.MobChampions;

public record LaunchFireworksPacket(Vector3f location) implements CustomPacketPayload {

    private static StreamCodec<ByteBuf, Vector3f> VECTOR3F = new StreamCodec<>() {
        @Override
        public void encode(@NonNull ByteBuf buf, @NonNull Vector3f value) {
            FriendlyByteBuf.writeVector3f(buf, value);
        }

        @Override
        public @NonNull Vector3f decode(@NonNull ByteBuf buf) {
            return FriendlyByteBuf.readVector3f(buf);
        }
    };
    public static final Type<LaunchFireworksPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(MobChampions.MODID, "launch_fireworks"));
    public static final StreamCodec<FriendlyByteBuf, LaunchFireworksPacket> STREAM_CODEC =
        StreamCodec.composite(
            VECTOR3F,
            LaunchFireworksPacket::location,
            LaunchFireworksPacket::new
        );

    public static void handle(Vector3f location) {

    }

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}
