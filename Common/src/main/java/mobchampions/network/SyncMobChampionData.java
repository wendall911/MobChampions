package mobchampions.network;

import org.jetbrains.annotations.NotNull;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public class SyncMobChampionData extends MobChampion implements CustomPacketPayload {

    public static final Type<SyncMobChampionData> TYPE = new Type<>(MobChampionData.ID);
    public static final StreamCodec<FriendlyByteBuf, SyncMobChampionData> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.COMPOUND_TAG,
        SyncMobChampionData::getData,
        SyncMobChampionData::new
    );
    private final MobChampionData mobChampionData;
    private final CompoundTag data;

    public SyncMobChampionData(int entityId, Rank rank) {
        mobChampionData = new MobChampionData(entityId, rank);

        this.setEntityId(mobChampionData.entityId);
        this.setRank(mobChampionData.rank);

        data = this.write(new CompoundTag());
    }

    public SyncMobChampionData(CompoundTag tag) {
        this.read(tag);
        data = this.write(new CompoundTag());
        mobChampionData = new MobChampionData(getEntityId(), getRank());
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
