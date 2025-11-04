package mobchampions.data;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

import net.minecraft.core.HolderLookup;

import mobchampions.common.Translations;
import mobchampions.MobChampions;

public class MobChampionsLanguageProvider extends FabricLanguageProvider {

    protected MobChampionsLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryFuture) {
        super(dataOutput, "en_us", registryFuture);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider provider, TranslationBuilder builder) {
        addTranslationTitle(builder, "Mob Champions");
        addTranslation(builder, "visuals");
        addTranslation(builder, "uncommonchampioncolor");
        addTranslation(builder, "rarechampioncolor");
        addTranslation(builder, "epicchampioncolor");
        addTranslation(builder, "legendarychampioncolor");
        addTranslation(builder, "fireworkschance");
        addTranslation(builder, "colors");
        addTranslation(builder, "fireworksflicker");
        addTranslation(builder, "fireworkstrail");
        addTranslation(builder, "fireworksshape");
        addTranslation(builder, "fireworksheight");
        addTranslation(builder, "spawning");
        addTranslation(builder, "commonmobweight");
        addTranslation(builder, "uncommonchampionweight");
        addTranslation(builder, "rarechampionweight");
        addTranslation(builder, "epicchampionweight");
        addTranslation(builder, "legendarychampionweight");
        addTranslation(builder, "championwhitelist");
        addTranslation(builder, "stats");
        addTranslation(builder, "uncommonchampionhealthmultiplier");
        addTranslation(builder, "uncommonchampionarmoraddition");
        addTranslation(builder, "uncommonchampionmovementspeedmultiplier");
        addTranslation(builder, "uncommonchampionattackdamagemultiplier");
        addTranslation(builder, "uncommonchampionknockbackresistanceaddition");
        addTranslation(builder, "rarechampionhealthmultiplier");
        addTranslation(builder, "rarechampionarmoraddition");
        addTranslation(builder, "rarechampionmovementspeedmultiplier");
        addTranslation(builder, "rarechampionattackdamagemultiplier");
        addTranslation(builder, "rarechampionknockbackresistanceaddition");
        addTranslation(builder, "epicchampionhealthmultiplier");
        addTranslation(builder, "epicchampionarmoraddition");
        addTranslation(builder, "epicchampionmovementspeedmultiplier");
        addTranslation(builder, "epicchampionattackdamagemultiplier");
        addTranslation(builder, "epicchampionknockbackresistanceaddition");
        addTranslation(builder, "legendarychampionhealthmultiplier");
        addTranslation(builder, "legendarychampionarmoraddition");
        addTranslation(builder, "legendarychampionmovementspeedmultiplier");
        addTranslation(builder, "legendarychampionattackdamagemultiplier");
        addTranslation(builder, "legendarychampionknockbackresistanceaddition");
    }

    private void addTranslationTitle(TranslationBuilder builder, String title) {
        builder.add(MobChampions.MODID + ".configuration.title", title);
    }

    private void addTranslation(TranslationBuilder builder, String id) {
        addTranslationName(builder, id);
        addTranslationDescription(builder, id);
    }

    private void addTranslationName(TranslationBuilder builder, String id) {
        builder.add(MobChampions.MODID + ".configuration." + id + ".name", Translations.get(id + ".title"));
    }

    private void addTranslationDescription(TranslationBuilder builder, String id) {
        builder.add(MobChampions.MODID + ".configuration." + id + ".description", Translations.get(id));
    }

    private void addTranslationDescription(TranslationBuilder builder, String id, String key) {
        builder.add(MobChampions.MODID + ".configuration." + id + ".description", Translations.get(key));
    }

}
