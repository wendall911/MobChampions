package mobchampions.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

import mobchampions.network.MobChampion.Rank;

public interface IMobChampion {

    void setEntityId(int entityId);

    void setRank(Rank rank);

    void setPrefix(int prefix);

    void setSuffix(int suffix);

    int getEntityId();

    Rank getRank();

    int getPrefix();

    int getSuffix();

    ListTag write();

    void read(ListTag tag);

    CompoundTag write(CompoundTag tag);

    void read(CompoundTag tag);

}
