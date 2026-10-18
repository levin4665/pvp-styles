package com.example.pvpstyles;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/** Einfache Einstellungen, gespeichert in config/pvpstyles.json */
public final class Settings {
    public boolean showFps = true;
    public boolean showCps = true;
    public boolean lessParticles = false;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("pvpstyles.json");
    public static Settings INSTANCE = new Settings();

    public static void load() {
        try {
            if (Files.exists(FILE)) {
                Settings s = GSON.fromJson(Files.readString(FILE), Settings.class);
                if (s != null) INSTANCE = s;
            }
        } catch (Exception e) {
            System.err.println("[PvpStyles] Config konnte nicht gelesen werden: " + e);
        }
    }

    public static void save() {
        try {
            Files.writeString(FILE, GSON.toJson(INSTANCE));
        } catch (IOException e) {
            System.err.println("[PvpStyles] Config konnte nicht gespeichert werden: " + e);
        }
    }
}
