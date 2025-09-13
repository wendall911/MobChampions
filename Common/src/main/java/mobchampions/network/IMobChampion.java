package mobchampions.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

public interface IMobChampion {

    void setRank(int rank);

    void setPrefix(int prefix);

    void setSuffix(int suffix);

    int getRank();

    int getPrefix();

    int getSuffix();

    ListTag write();

    void read(ListTag tag);

    CompoundTag write(CompoundTag tag);

    void read(CompoundTag tag);

}
