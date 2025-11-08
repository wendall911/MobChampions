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
        addTranslation(builder, "disablebabychampions");
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
        addTranslation(builder, "uncommonarrowdamagemultiplier");
        addTranslation(builder, "uncommonknockbackresistanceaddition");
        addTranslation(builder, "rarehealthmultiplier");
        addTranslation(builder, "rarearmoraddition");
        addTranslation(builder, "raremovementspeedmultiplier");
        addTranslation(builder, "rareattackdamagemultiplier");
        addTranslation(builder, "rarearrowdamagemultiplier");
        addTranslation(builder, "rareknockbackresistanceaddition");
        addTranslation(builder, "epichealthmultiplier");
        addTranslation(builder, "epicarmoraddition");
        addTranslation(builder, "epicmovementspeedmultiplier");
        addTranslation(builder, "epicattackdamagemultiplier");
        addTranslation(builder, "epicarrowdamagemultiplier");
        addTranslation(builder, "epicknockbackresistanceaddition");
        addTranslation(builder, "legendaryhealthmultiplier");
        addTranslation(builder, "legendaryarmoraddition");
        addTranslation(builder, "legendarymovementspeedmultiplier");
        addTranslation(builder, "legendaryattackdamagemultiplier");
        addTranslation(builder, "legendaryarrowdamagemultiplier");
        addTranslation(builder, "legendaryknockbackresistanceaddition");
        addTranslation(builder, "effects");
        addTranslation(builder, "glowingeffectminimumrank");
        addTranslation(builder, "glowingeffectduration");
        addTranslation(builder, "infestedeffectminimumrank");
        addTranslation(builder, "infestedeffectchance");
        addTranslation(builder, "oozingeffectminimumrank");
        addTranslation(builder, "oozingeffectchance");
        addTranslation(builder, "weavingeffectminimumrank");
        addTranslation(builder, "weavingeffectchance");
        addTranslation(builder, "windchargedeffectminimumrank");
        addTranslation(builder, "windchargedeffectchance");
        addTranslation(builder, "legendaryeffectbonusmultiplier");
        addTranslation(builder, "equipment");
        addTranslation(builder, "uncommonstandardweaponspawnchance");
        addTranslation(builder, "rarestandardweaponspawnchance");
        addTranslation(builder, "epicstandardweaponspawnchance");
        addTranslation(builder, "legendarystandardweaponspawnchance");
        addTranslation(builder, "uncommonlootableweaponspawnchance");
        addTranslation(builder, "rarelootableweaponspawnchance");
        addTranslation(builder, "epiclootableweaponspawnchance");
        addTranslation(builder, "legendarylootableweaponspawnchance");
        addTranslation(builder, "weaponlist");
        addTranslation(builder, "uncommonstandardarmorspawnchance");
        addTranslation(builder, "rarestandardarmorspawnchance");
        addTranslation(builder, "epicstandardarmorspawnchance");
        addTranslation(builder, "legendarystandardarmorspawnchance");
        addTranslation(builder, "uncommonlootablearmorspawnchance");
        addTranslation(builder, "rarelootablearmorspawnchance");
        addTranslation(builder, "epiclootablearmorspawnchance");
        addTranslation(builder, "legendarylootablearmorspawnchance");
        addTranslation(builder, "armorlist");
        addTranslation(builder, "experience");
        addTranslation(builder, "uncommonexperiencemultiplier");
        addTranslation(builder, "rareexperiencemultiplier");
        addTranslation(builder, "epicexperiencemultiplier");
        addTranslation(builder, "legendaryexperiencemultiplier");
        addTranslation(builder, "lootdrops");
        addTranslation(builder, "standardarmordropchance");
        addTranslation(builder, "standardweapondropchance");

        // Loot Item Translations
        addLootItemTranslation(builder, "medkit", "Medkit");
        addLootItemTranslation(builder, "advanced_medkit", "Advanced Medkit");
        addLootItemTranslation(builder, "elite_medkit", "Elite Medkit");
        addLootItemTranslation(builder, "legendary_medkit", "Legendary Medkit");
        addLootItemTranslation(builder, "adrenaline_shot", "Adrenaline Shot");
        addLootItemTranslation(builder, "energy_drink", "Energy Drink");
        addLootItemTranslation(builder, "riot_shield", "Riot Shield");
        addLootItemTranslation(builder, "knockback_stick", "Knockback Stick");
        addLootItemTranslation(builder, "apocalypse_blade", "Apocalypse Blade");
        addLootItemTranslation(builder, "cornucopia_bow", "Cornucopia Bow");
        addLootItemTranslation(builder, "axecalibur", "Axecalibur");
        addLootItemTranslation(builder, "yolt", "Fragile Totem of Undying");
        addLootItemTranslation(builder, "greed", "Greedy Axe");
        addLootItemTranslation(builder, "propeller_hat", "Propeller Hat");
        addLootItemTranslation(builder, "cactus_armor", "Cactus Chestplate");
        addLootItemTranslation(builder, "matchstick", "The Matchstick");
        addLootItemTranslation(builder, "multiplier", "The Multiplier");
        addLootItemTranslation(builder, "mjolnir", "Mj\u00f6lnir");
        addLootItemTranslation(builder, "primitive_chainsaw", "Primitive Chainsaw");
        addLootItemTranslation(builder, "primitive_mining_drill", "Primitive Mining Drill");
        addLootItemTranslation(builder, "chainsaw", "Chainsaw");
        addLootItemTranslation(builder, "mining_drill", "Mining Drill");
        addLootItemTranslation(builder, "sticky_chestplate", "Sticky Chestplate");
        addLootItemTranslation(builder, "sticky_leggings", "Sticky Leggings");
        addLootItemTranslation(builder, "gem_magnet", "Gem Magnet");
        addLootItemTranslation(builder, "long_fall_boots", "Long Fall Boots");
        addLootItemTranslation(builder, "soul_walkers", "Soul Walkers");
        addLootItemTranslation(builder, "winter_flippers", "Winter Flippers");
        addLootItemTranslation(builder, "diving_helmet", "Diving Helmet");
        addLootItemTranslation(builder, "bulletproof_vest", "Bulletproof Vest");
        addLootItemTranslation(builder, "basher", "Basher");
        addLootItemTranslation(builder, "ripper", "Tornado Trident");
        addLootItemTranslation(builder, "lucky_charm", "Lucky Charm");
        addLootItemTranslation(builder, "reapers_scythe", "Reaper's Scythe");
        addLootItemTranslation(builder, "poseidons_trident", "Poseidon's Trident");
        addLootItemTranslation(builder, "plated_elytra", "Plated Elytra");
        addLootItemTranslation(builder, "warding_chestplate", "Warding Chestplate");
        addLootItemTranslation(builder, "robins_bow", "Robin's Bow");
        addLootItemTranslation(builder, "wildling_helmet", "Wildling Helmet");
        addLootItemTranslation(builder, "wildling_chestplate", "Wildling Chestplate");
        addLootItemTranslation(builder, "wildling_leggings", "Wildling Leggings");
        addLootItemTranslation(builder, "wildling_boots", "Wildling Boots");
        addLootItemTranslation(builder, "knight_helmet", "Knight Helmet");
        addLootItemTranslation(builder, "knight_chestplate", "Knight Chestplate");
        addLootItemTranslation(builder, "knight_leggings", "Knight Leggings");
        addLootItemTranslation(builder, "knight_boots", "Knight Boots");
        addLootItemTranslation(builder, "shiny_helmet", "Shiny Helmet");
        addLootItemTranslation(builder, "shiny_chestplate", "Shiny Chestplate");
        addLootItemTranslation(builder, "shiny_leggings", "Shiny Leggings");
        addLootItemTranslation(builder, "shiny_boots", "Shiny Boots");
        addLootItemTranslation(builder, "champions_helmet", "Champion's Helmet");
        addLootItemTranslation(builder, "champions_chestplate", "Champion's Chestplate");
        addLootItemTranslation(builder, "champions_leggings", "Champion's Leggings");
        addLootItemTranslation(builder, "champions_boots", "Champion's Boots");
        addLootItemTranslation(builder, "title", "Mob Champion Loot");
    }

    private void addTranslationTitle(TranslationBuilder builder, String title) {
        builder.add(Translations.MOD_NAME_KEY, title);
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

    private void addLootItemTranslation(TranslationBuilder builder, String id, String name) {
        builder.add(Translations.LOOT_KEY_BASE + id, name);
    }

}
