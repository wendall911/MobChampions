package mobchampions.network;

import org.jetbrains.annotations.NotNull;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public class NeoForgeMobChampionData extends MobChampion implements CustomPacketPayload {

    public static final Type<NeoForgeMobChampionData> TYPE = new Type<>(MobChampionData.ID);
    public static final StreamCodec<FriendlyByteBuf, NeoForgeMobChampionData> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.COMPOUND_TAG,
        NeoForgeMobChampionData::getData,
        NeoForgeMobChampionData::new
    );
    private final MobChampionData mobChampionData;
    private final CompoundTag data;

    public NeoForgeMobChampionData(Rank rank, int prefix, int suffix) {
        mobChampionData = new MobChampionData(rank, prefix, suffix);

        this.setRank(mobChampionData.rank);
        this.setPrefix(mobChampionData.prefix);
        this.setSuffix(mobChampionData.suffix);

        data = this.write(new CompoundTag());
    }

    public NeoForgeMobChampionData(CompoundTag tag) {
        this.read(tag);
        data = this.write(new CompoundTag());
        mobChampionData = new MobChampionData(getRank(), getPrefix(), getSuffix());
    }

    public MobChampionData getMobChampionData() {
        return mobChampionData;
    }

    public CompoundTag getData() {
        return data;
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}
