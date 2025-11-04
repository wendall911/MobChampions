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
        addTranslation(builder, "uncommoncolor");
        addTranslation(builder, "rarecolor");
        addTranslation(builder, "epiccolor");
        addTranslation(builder, "legendarycolor");
        addTranslation(builder, "fireworkschance");
        addTranslation(builder, "colors");
        addTranslation(builder, "fireworksflicker");
        addTranslation(builder, "fireworkstrail");
        addTranslation(builder, "fireworksshape");
        addTranslation(builder, "fireworksheight");
        addTranslation(builder, "spawning");
        addTranslation(builder, "commonmobweight");
        addTranslation(builder, "uncommonweight");
        addTranslation(builder, "rareweight");
        addTranslation(builder, "epicweight");
        addTranslation(builder, "legendaryweight");
        addTranslation(builder, "championwhitelist");
        addTranslation(builder, "spawntypeblacklist");
        addTranslation(builder, "stats");
        addTranslation(builder, "uncommonhealthmultiplier");
        addTranslation(builder, "uncommonarmoraddition");
        addTranslation(builder, "uncommonmovementspeedmultiplier");
        addTranslation(builder, "uncommonattackdamagemultiplier");
        addTranslation(builder, "uncommonknockbackresistanceaddition");
        addTranslation(builder, "rarehealthmultiplier");
        addTranslation(builder, "rarearmoraddition");
        addTranslation(builder, "raremovementspeedmultiplier");
        addTranslation(builder, "rareattackdamagemultiplier");
        addTranslation(builder, "rareknockbackresistanceaddition");
        addTranslation(builder, "epichealthmultiplier");
        addTranslation(builder, "epicarmoraddition");
        addTranslation(builder, "epicmovementspeedmultiplier");
        addTranslation(builder, "epicattackdamagemultiplier");
        addTranslation(builder, "epicknockbackresistanceaddition");
        addTranslation(builder, "legendaryhealthmultiplier");
        addTranslation(builder, "legendaryarmoraddition");
        addTranslation(builder, "legendarymovementspeedmultiplier");
        addTranslation(builder, "legendaryattackdamagemultiplier");
        addTranslation(builder, "legendaryknockbackresistanceaddition");
        addTranslation(builder, "experience");
        addTranslation(builder, "uncommonexperiencemultiplier");
        addTranslation(builder, "rareexperiencemultiplier");
        addTranslation(builder, "epicexperiencemultiplier");
        addTranslation(builder, "legendaryexperiencemultiplier");
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
