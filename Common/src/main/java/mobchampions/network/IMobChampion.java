package mobchampions.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

import mobchampions.network.MobChampion.Rank;

public interface IMobChampion {

    void setEntityId(int entityId);

    void setRank(Rank rank);

    int getEntityId();

    Rank getRank();

    ListTag write();

    void read(ListTag tag);

    CompoundTag write(CompoundTag tag);

    void read(CompoundTag tag);

}
