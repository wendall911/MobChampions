package mobchampions.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

public class MobChampion implements IMobChampion {

    private Rank rank;
    private int prefix;
    private int suffix;

    @Override
    public void setRank(Rank rank) {
        this.rank = rank;
    }

    @Override
    public void setPrefix(int prefix) {
        this.prefix = prefix;
    }

    @Override
    public void setSuffix(int suffix) {
        this.suffix = suffix;
    }

    @Override
    public Rank getRank() {
        return this.rank;
    }

    @Override
    public int getPrefix() {
        return this.prefix;
    }

    @Override
    public int getSuffix() {
        return this.suffix;
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
        tag.putInt("rank", this.getRank().ordinal());
        tag.putInt("prefix", this.getPrefix());
        tag.putInt("suffix", this.getSuffix());

        return tag;
    }

    @Override
    public void read(CompoundTag tag) {
        this.setRank(Rank.values()[tag.getInt("rank")]);
        this.setPrefix(tag.getInt("prefix"));
        this.setSuffix(tag.getInt("suffix"));
    }

    public enum Rank {
        NONE,
        COMMON,
        UNCOMMON,
        RARE,
        LEGENDARY
    }

}
