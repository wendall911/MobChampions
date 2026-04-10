package mobchampions.util;

import org.jspecify.annotations.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import mobchampions.common.Translations;

public class TranslationHelper {

    public static Component getLootComponent(String id, @Nullable ChatFormatting... formatting) {
        return translatable(Translations.LOOT_KEY_BASE + id, formatting);
    }

    public static Component translatable(final String translationKey, @Nullable ChatFormatting... formatting) {
        return Component.translatable(translationKey).withStyle(formatting);
    }

}
