package mobchampions.attachments;

import java.util.Optional;

import org.jetbrains.annotations.NotNull;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import net.neoforged.neoforge.common.util.ValueIOSerializable;

import mobchampions.network.MobChampion;

public class MobChampionDataAttachment {

    public static Optional<MobChampion> getData(Entity entity) {
        return Optional.of(entity.getData(AttachmentsRegistry.MOB_CHAMPION_DATA_ATTACHMENT.get()));
    }

    public static class MobChampionDataProvider extends MobChampion implements ValueIOSerializable {

        public MobChampionDataProvider() {}

        @Override
        public void serialize(@NotNull ValueOutput valueOutput) {
            write(valueOutput);
        }

        @Override
        public void deserialize(@NotNull ValueInput valueInput) {
            read(valueInput);
        }

    }

}
