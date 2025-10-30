package mobchampions.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

import mobchampions.MobChampions;

public class FabricDatagenInitializer implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator gen) {
        FabricDataGenerator.Pack pack = gen.createPack();

        if (System.getProperty(MobChampions.MODID + ".common_datagen") != null) {
            configureCommonDatagen(pack);
        }
    }

    public static void configureCommonDatagen(FabricDataGenerator.Pack pack) {
        pack.addProvider(MobChampionsLanguageProvider::new);
    }

}
