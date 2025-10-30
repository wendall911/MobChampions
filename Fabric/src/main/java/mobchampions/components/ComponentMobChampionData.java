package mobchampions.components;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

import org.ladysnake.cca.api.v3.component.Component;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

import mobchampions.network.MobChampion;
import mobchampions.network.MobChampionData;

public class ComponentMobChampionData extends MobChampion implements Component, AutoSyncedComponent {

    @Override
    public void readFromNbt(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registryLookup) {
        this.read(tag);
    }

    @Override
    public void writeToNbt(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registryLookup) {
        this.write(tag);
    }

    @Override
    public void writeSyncPacket(RegistryFriendlyByteBuf buf, ServerPlayer recipient) {
        MobChampionData mobChampionData = new MobChampionData(getRank(), getPrefix(), getSuffix());

        mobChampionData.write(buf);
    }

    @Override
    public void applySyncPacket(RegistryFriendlyByteBuf buf) {
        MobChampionData mobChampionData = new MobChampionData(buf);

        this.setRank(mobChampionData.rank);
        this.setPrefix(mobChampionData.prefix);
        this.setSuffix(mobChampionData.suffix);
    }

}
