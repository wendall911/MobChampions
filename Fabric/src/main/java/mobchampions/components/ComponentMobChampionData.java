package mobchampions.components;

import org.jetbrains.annotations.NotNull;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import org.ladysnake.cca.api.v3.component.Component;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

import mobchampions.network.MobChampion;
import mobchampions.network.MobChampionData;

public class ComponentMobChampionData extends MobChampion implements Component, AutoSyncedComponent {

    @Override
    public void readData(@NotNull ValueInput valueInput) {
        this.read(valueInput);
    }

    @Override
    public void writeData(@NotNull ValueOutput valueOutput) {
        this.write(valueOutput);
    }

    @Override
    public void writeSyncPacket(RegistryFriendlyByteBuf buf, ServerPlayer recipient) {
        MobChampionData mobChampionData = new MobChampionData(getEntityId(), getRank());

        mobChampionData.write(buf);
    }

    @Override
    public void applySyncPacket(RegistryFriendlyByteBuf buf) {
        MobChampionData mobChampionData = new MobChampionData(buf);

        this.setEntityId(mobChampionData.entityId);
        this.setRank(mobChampionData.rank);
    }

}
