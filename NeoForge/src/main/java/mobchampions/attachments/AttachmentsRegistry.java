package mobchampions.attachments;

import java.util.function.Supplier;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys;

import mobchampions.MobChampions;
import mobchampions.network.MobChampion;

public class AttachmentsRegistry {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPE_DEFERRED_REGISTER =
        DeferredRegister.create(Keys.ATTACHMENT_TYPES, MobChampions.MODID);

    public static final Supplier<AttachmentType<? extends MobChampion>> MOB_CHAMPION_DATA_ATTACHMENT =
        ATTACHMENT_TYPE_DEFERRED_REGISTER.register("mob_champion_data_attachment",
            () -> AttachmentType.serializable(MobChampionDataAttachment.MobChampionDataProvider::new).build());

    public static void init(IEventBus bus) {
        ATTACHMENT_TYPE_DEFERRED_REGISTER.register(bus);
    }

}
