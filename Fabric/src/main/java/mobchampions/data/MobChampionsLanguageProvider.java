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
        addTypeTranslation(builder, "uncommon", "Uncommon");
        addTypeTranslation(builder, "rare", "Rare");
        addTypeTranslation(builder, "epic", "Epic");
        addTypeTranslation(builder, "legendary", "Legendary");
        addAnnouncementTranslation(builder, "message", "A %s champion has appeared nearby!");

        addConfigurationTitle(builder, "Mob Champions");
        addConfigTranslation(builder, "visuals");
        addConfigTranslation(builder, "uncommoncolor");
        addConfigTranslation(builder, "rarecolor");
        addConfigTranslation(builder, "epiccolor");
        addConfigTranslation(builder, "legendarycolor");
        addConfigTranslation(builder, "fireworkschance");
        addConfigTranslation(builder, "colors");
        addConfigTranslation(builder, "fireworksflicker");
        addConfigTranslation(builder, "fireworkstrail");
        addConfigTranslation(builder, "fireworksshape");
        addConfigTranslation(builder, "fireworksheight");
        addConfigTranslation(builder, "spawning");
        addConfigTranslation(builder, "disablebabychampions");
        addConfigTranslation(builder, "commonmobweight");
        addConfigTranslation(builder, "uncommonweight");
        addConfigTranslation(builder, "rareweight");
        addConfigTranslation(builder, "epicweight");
        addConfigTranslation(builder, "legendaryweight");
        addConfigTranslation(builder, "championwhitelist");
        addConfigTranslation(builder, "spawntypeblacklist");
        addConfigTranslation(builder, "stats");
        addConfigTranslation(builder, "uncommondifficulty");
        addConfigTranslation(builder, "raredifficulty");
        addConfigTranslation(builder, "epicdifficulty");
        addConfigTranslation(builder, "legendarydifficulty");
        addConfigTranslation(builder, "effects");
        addConfigTranslation(builder, "glowingeffectminimumrank");
        addConfigTranslation(builder, "glowingeffectduration");
        addConfigTranslation(builder, "infestedeffectminimumrank");
        addConfigTranslation(builder, "infestedeffectchance");
        addConfigTranslation(builder, "oozingeffectminimumrank");
        addConfigTranslation(builder, "oozingeffectchance");
        addConfigTranslation(builder, "weavingeffectminimumrank");
        addConfigTranslation(builder, "weavingeffectchance");
        addConfigTranslation(builder, "windchargedeffectminimumrank");
        addConfigTranslation(builder, "windchargedeffectchance");
        addConfigTranslation(builder, "legendaryeffectbonusmultiplier");
        addConfigTranslation(builder, "equipment");
        addConfigTranslation(builder, "uncommonstandardweaponspawnchance");
        addConfigTranslation(builder, "rarestandardweaponspawnchance");
        addConfigTranslation(builder, "epicstandardweaponspawnchance");
        addConfigTranslation(builder, "legendarystandardweaponspawnchance");
        addConfigTranslation(builder, "uncommonlootableweaponspawnchance");
        addConfigTranslation(builder, "rarelootableweaponspawnchance");
        addConfigTranslation(builder, "epiclootableweaponspawnchance");
        addConfigTranslation(builder, "legendarylootableweaponspawnchance");
        addConfigTranslation(builder, "weaponlist");
        addConfigTranslation(builder, "uncommonstandardarmorspawnchance");
        addConfigTranslation(builder, "rarestandardarmorspawnchance");
        addConfigTranslation(builder, "epicstandardarmorspawnchance");
        addConfigTranslation(builder, "legendarystandardarmorspawnchance");
        addConfigTranslation(builder, "uncommonlootablearmorspawnchance");
        addConfigTranslation(builder, "rarelootablearmorspawnchance");
        addConfigTranslation(builder, "epiclootablearmorspawnchance");
        addConfigTranslation(builder, "legendarylootablearmorspawnchance");
        addConfigTranslation(builder, "armorlist");
        addConfigTranslation(builder, "experience");
        addConfigTranslation(builder, "uncommonexperiencemultiplier");
        addConfigTranslation(builder, "rareexperiencemultiplier");
        addConfigTranslation(builder, "epicexperiencemultiplier");
        addConfigTranslation(builder, "legendaryexperiencemultiplier");
        addConfigTranslation(builder, "lootdrops");
        addConfigTranslation(builder, "standardarmordropchance");
        addConfigTranslation(builder, "standardweapondropchance");

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

    private void addConfigurationTitle(TranslationBuilder builder, String title) {
        builder.add(Translations.MOD_NAME_KEY, title);
    }

    private void addConfigTranslation(TranslationBuilder builder, String id) {
        addConfigName(builder, id);
        addConfigDescription(builder, id);
    }

    private void addConfigName(TranslationBuilder builder, String id) {
        builder.add(MobChampions.MODID + ".configuration." + id + ".name", Translations.get(id + ".title"));
    }

    private void addConfigDescription(TranslationBuilder builder, String id) {
        builder.add(MobChampions.MODID + ".configuration." + id + ".description", Translations.get(id));
    }

    private void addConfigDescription(TranslationBuilder builder, String id, String key) {
        builder.add(MobChampions.MODID + ".configuration." + id + ".description", Translations.get(key));
    }

    private void addLootItemTranslation(TranslationBuilder builder, String id, String name) {
        builder.add(Translations.LOOT_KEY_BASE + id, name);
    }

    private void addAnnouncementTranslation(TranslationBuilder builder, String id, String message) {
        builder.add(Translations.ANNOUNCEMENT_KEY + id, message);
    }

    private void addTypeTranslation(TranslationBuilder builder, String id, String name) {
        builder.add(Translations.TYPE_KEY + id, name);
    }

}
