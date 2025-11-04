package mobchampions.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

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
    public ListTag write() {
        ListTag listTag = new ListTag();
        CompoundTag tag = new CompoundTag();

        write(tag);
        listTag.add(tag);

        return listTag;
    }

    @Override
    public void read(ListTag tag) {
        read(tag.getCompound(0));
    }

    @Override
    public CompoundTag write(CompoundTag tag) {
        tag.putInt("entityId", this.getEntityId());
        tag.putInt("rank", this.getRank().ordinal());

        return tag;
    }

    @Override
    public void read(CompoundTag tag) {
        this.setEntityId(tag.getInt("entityId"));
        this.setRank(Rank.values()[tag.getInt("rank")]);
    }

    public enum Rank {
        COMMON,
        UNCOMMON,
        RARE,
        EPIC,
        LEGENDARY
    }

}
