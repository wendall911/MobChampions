package mobchampions.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

import mobchampions.network.MobChampion.Rank;
import mobchampions.platform.Services;

import static mobchampions.MobChampions.prefix;

public class MobChampionData {

    public int entityId;
    public Rank rank;
    public int prefix;
    public int suffix;
    public static final ResourceLocation ID = prefix("mob_champion_data");

    public MobChampionData(int entityId, Rank rank, int prefix, int suffix) {
        this.entityId = entityId;
        this.rank = rank;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public MobChampionData(FriendlyByteBuf buf) {
        entityId = buf.readInt();
        rank = Rank.values()[buf.readInt()];
        prefix = buf.readInt();
        suffix = buf.readInt();
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeInt(rank.ordinal());
        buf.writeInt(prefix);
        buf.writeInt(suffix);
    }

    public static void process(LivingEntity entity, CompoundTag tag) {
        Services.PLATFORM.getMobChampionData(entity).ifPresent(data -> {
            data.read(tag);
        });
    }

    public String toString() {
        return "entityid: " + this.entityId + " rank: " + this.rank + " prefix: " + this.prefix + " suffix: " + this.suffix;
    }

}
