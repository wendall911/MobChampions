package mobchampions.network;

import org.jspecify.annotations.NonNull;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class MobChampion implements IMobChampion {

    private int entityId = -1;
    private Rank rank = Rank.COMMON;

    @Override
    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    @Override
    public void setRank(Rank rank) {
        this.rank = rank;
    }

    @Override
    public int getEntityId() {
        return this.entityId;
    }

    @Override
    public Rank getRank() {
        return this.rank;
    }

    @Override
    public ValueOutput write(@NonNull ValueOutput valueOutput) {
        valueOutput.putInt("entityId", this.getEntityId());
        valueOutput.putInt("rank", this.getRank().ordinal());

        return valueOutput;
    }

    @Override
    public void read(@NonNull ValueInput valueInput) {
        this.setEntityId(valueInput.getIntOr("entityId", -1));
        this.setRank(Rank.values()[valueInput.getIntOr("rank", 0)]);
    }

    @Override
    public CompoundTag write(CompoundTag tag) {
        tag.putInt("entityId", this.getEntityId());
        tag.putInt("rank", this.getRank().ordinal());

        return tag;
    }

    @Override
    public void read(CompoundTag tag) {
        this.setEntityId(tag.getInt("entityId").orElseThrow());
        this.setRank(Rank.values()[tag.getInt("rank").orElseThrow()]);
    }

    public enum Rank {
        COMMON,
        UNCOMMON,
        RARE,
        EPIC,
        LEGENDARY
    }

}
