package mobchampions.network;

import mobchampions.platform.Services;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import static mobchampions.MobChampions.prefix;

public class MobChampionData {

    public int rank;
    public int prefix;
    public int suffix;
    public static final ResourceLocation ID = prefix("mob_champion_data");

    public MobChampionData(int rank, int prefix, int suffix) {
        this.rank = rank;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public MobChampionData(FriendlyByteBuf buf) {
        rank = buf.readInt();
        prefix = buf.readInt();
        suffix = buf.readInt();
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(rank);
        buf.writeInt(prefix);
        buf.writeInt(suffix);
    }

    public static void process(Entity entity, CompoundTag tag) {
        Services.PLATFORM.getMobChampionData(entity).ifPresent(data -> {
            data.read(tag);
        });
    }

    public String toString() {
        return "rank: " + this.rank + " prefix: " + this.prefix + " suffix: " + this.suffix;
    }

}
