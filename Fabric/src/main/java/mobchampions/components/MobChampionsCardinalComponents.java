package mobchampions.components;

import net.minecraft.world.entity.LivingEntity;

import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;

import static mobchampions.MobChampions.prefix;

public class MobChampionsCardinalComponents implements EntityComponentInitializer {

    public static final ComponentKey<ComponentMobChampionData> MOB_CHAMPION_DATA =
            ComponentRegistry.getOrCreate(prefix("mob_champion_data_provider"), ComponentMobChampionData.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerFor(LivingEntity.class, MOB_CHAMPION_DATA, entity -> new ComponentMobChampionData());
    }

}
