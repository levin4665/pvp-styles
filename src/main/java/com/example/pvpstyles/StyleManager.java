package com.example.pvpstyles;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

/**
 * Jeder Style ist ein eingebautes Ressourcenpaket in
 * src/main/resources/resourcepacks/<id>/ und wird per Klick an/aus geschaltet.
 */
public final class StyleManager {
    public record Style(String id, String label) {}

    public static final List<Style> STYLES = List.of(
        new Style("low_fire", "Niedriges Feuer"),
        new Style("clear_crystal", "Durchsichtiger Kristall"),
        new Style("pvp_swords", "PvP-Schwerter"),
        new Style("clean_shield", "Klarer Schild"),
        new Style("clean_inventory", "Cleanes Inventar")
    );

    public static Identifier packId(Style s) {
        return Identifier.of(PvpStylesClient.MOD_ID, s.id());
    }

    /** Name, unter dem der ResourcePackManager das Paket kennt. */
    public static String profileId(Style s) {
        return packId(s).toString();
    }

    public static void registerPacks() {
        ModContainer container = FabricLoader.getInstance()
            .getModContainer(PvpStylesClient.MOD_ID).orElseThrow();
        for (Style s : STYLES) {
            ResourceManagerHelper.registerBuiltinResourcePack(
                packId(s), container, Text.literal(s.label()), ResourcePackActivationType.NORMAL);
        }
    }

    public static boolean isEnabled(Style s) {
        return MinecraftClient.getInstance().getResourcePackManager()
            .getEnabledIds().contains(profileId(s));
    }

    public static void toggle(Style s) {
        MinecraftClient mc = MinecraftClient.getInstance();
        ResourcePackManager manager = mc.getResourcePackManager();
        if (isEnabled(s)) {
            manager.disable(profileId(s));
        } else {
            manager.enable(profileId(s));
        }
        // Auswahl dauerhaft speichern und Texturen neu laden
        mc.options.resourcePacks.clear();
        mc.options.resourcePacks.addAll(manager.getEnabledIds());
        mc.options.write();
        mc.reloadResources();
    }
}
