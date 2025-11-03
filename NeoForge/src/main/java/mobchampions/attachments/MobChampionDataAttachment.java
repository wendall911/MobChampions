package mobchampions.attachments;

import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.common.util.INBTSerializable;

import mobchampions.network.MobChampion;

public class MobChampionDataAttachment {

    public static Optional<MobChampion> getData(Entity entity) {
        return Optional.of(entity.getData(AttachmentsRegistry.MOB_CHAMPION_DATA_ATTACHMENT.get()));
    }

    public static class MobChampionDataProvider extends MobChampion implements INBTSerializable<ListTag> {

        public MobChampionDataProvider() {}

        @Override
        public @UnknownNullability ListTag serializeNBT(@NotNull Provider provider) {
            return write();
        }

        @Override
        public void deserializeNBT(@NotNull Provider provider, @NotNull ListTag tags) {
            read(tags);
        }

    }

}
