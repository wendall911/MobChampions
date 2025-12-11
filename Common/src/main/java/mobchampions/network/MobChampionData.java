package mobchampions.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;

import mobchampions.network.MobChampion.Rank;
import mobchampions.platform.Services;

import static mobchampions.MobChampions.prefix;

public class MobChampionData {

    public int entityId;
    public Rank rank;
    public static final Identifier ID = prefix("mob_champion_data");

    public MobChampionData(int entityId, Rank rank) {
        this.entityId = entityId;
        this.rank = rank;
    }

    public MobChampionData(FriendlyByteBuf buf) {
        entityId = buf.readInt();
        rank = Rank.values()[buf.readInt()];
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeInt(rank.ordinal());
    }

    public static void process(LivingEntity entity, CompoundTag tag) {
        Services.PLATFORM.getMobChampionData(entity).ifPresent(data -> {
            data.read(tag);
        });
    }

    public String toString() {
        return "entityid: " + this.entityId + " rank: " + this.rank;
    }

}
