# PvP Styles (Fabric, Minecraft 1.21.4, nur Client)

Menue per **Rechte Shift-Taste** (in den Steuerungs-Optionen aenderbar).

## Features
- 5 Styles (Ressourcenpakete), per Klick live an/aus, Auswahl bleibt gespeichert
- FPS- und CPS-Anzeige (links/rechts)
- "Weniger Partikel" (setzt die Partikel-Einstellung auf Minimal)
- Alles rein visuell/clientseitig, nichts wird an den Server gesendet

## Bauen
1. JDK 21 und Gradle (>= 8.12) installieren (oder Projekt in IntelliJ IDEA oeffnen).
2. Im Projektordner: `gradle wrapper` (einmalig), dann `./gradlew build` (Windows: `gradlew.bat build`)
3. Die Jar liegt in `build/libs/pvp-styles-1.0.0.jar`, nicht die `-sources.jar`.
4. In den `mods`-Ordner legen. Benoetigt Fabric Loader + Fabric API fuer 1.21.4.

## Eigene Texturen einbauen
In `src/main/resources/resourcepacks/<style>/assets/minecraft/textures/...`
die PNG-Dateien aus deinem Texturpaket ablegen (gleiche Dateinamen wie in Vanilla).
Eine README.txt in jedem Ordner nennt die Pfade. Danach neu bauen.

## Styles hinzufuegen
1. Neuen Ordner unter `src/main/resources/resourcepacks/<id>/` mit `pack.mcmeta` anlegen
2. Eintrag in `StyleManager.STYLES` ergaenzen

## Bauen ohne Installation (GitHub)
1. Kostenloses Konto auf github.com anlegen, oben rechts "+" -> "New repository", Namen vergeben, "Create repository".
2. "uploading an existing file" waehlen und den INHALT des entpackten Ordners hineinziehen (inkl. dem Ordner .github).
3. Reiter "Actions" oeffnen, der Lauf "Mod bauen" startet automatisch (ca. 2-4 Minuten).
4. Auf den fertigen Lauf klicken, unten bei "Artifacts" pvp-styles-mod herunterladen, entpacken: die .jar ist die Mod.
