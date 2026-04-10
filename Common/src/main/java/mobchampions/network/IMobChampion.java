package mobchampions.network;

import org.jspecify.annotations.NonNull;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import mobchampions.network.MobChampion.Rank;

public interface IMobChampion {

    void setEntityId(int entityId);

    void setRank(Rank rank);

    int getEntityId();

    Rank getRank();

    ValueOutput write(@NonNull ValueOutput valueOutput);

    void read(@NonNull ValueInput valueInput);

    CompoundTag write(CompoundTag tag);

    void read(CompoundTag tag);

}
