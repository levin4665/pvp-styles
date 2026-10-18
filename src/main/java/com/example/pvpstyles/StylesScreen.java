package com.example.pvpstyles;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class StylesScreen extends Screen {
    private static final int W = 200, H = 20, GAP = 4;

    public StylesScreen() {
        super(Text.literal("PvP Styles"));
    }

    @Override
    protected void init() {
        int x = this.width / 2 - W / 2;
        int y = 40;

        // --- Styles (Texturen) ---
        for (StyleManager.Style s : StyleManager.STYLES) {
            ButtonWidget[] ref = new ButtonWidget[1];
            ref[0] = ButtonWidget.builder(styleLabel(s), b -> {
                StyleManager.toggle(s);
                ref[0].setMessage(styleLabel(s));
            }).dimensions(x, y, W, H).build();
            addDrawableChild(ref[0]);
            y += H + GAP;
        }

        y += 8;

        // --- Extras ---
        addDrawableChild(toggleButton(x, y, "FPS anzeigen",
            () -> Settings.INSTANCE.showFps, v -> Settings.INSTANCE.showFps = v));
        y += H + GAP;
        addDrawableChild(toggleButton(x, y, "CPS anzeigen",
            () -> Settings.INSTANCE.showCps, v -> Settings.INSTANCE.showCps = v));
        y += H + GAP;
        addDrawableChild(toggleButton(x, y, "Weniger Partikel",
            () -> Settings.INSTANCE.lessParticles, v -> {
                Settings.INSTANCE.lessParticles = v;
                MinecraftClient mc = MinecraftClient.getInstance();
                var particles = mc.options.getParticles();
                var modes = particles.getValue().getClass().getEnumConstants();
                particles.setValue(v ? modes[modes.length - 1] : modes[0]);
                mc.options.write();
            }));
        y += H + GAP + 8;

        addDrawableChild(ButtonWidget.builder(Text.literal("Fertig"), b -> close())
            .dimensions(x, y, W, H).build());
    }

    private static Text styleLabel(StyleManager.Style s) {
        return Text.literal(s.label() + ": " + (StyleManager.isEnabled(s) ? "AN" : "AUS"));
    }

    private ButtonWidget toggleButton(int x, int y, String name,
                                      java.util.function.BooleanSupplier get,
                                      java.util.function.Consumer<Boolean> set) {
        ButtonWidget[] ref = new ButtonWidget[1];
        ref[0] = ButtonWidget.builder(label(name, get.getAsBoolean()), b -> {
            set.accept(!get.getAsBoolean());
            Settings.save();
            ref[0].setMessage(label(name, get.getAsBoolean()));
        }).dimensions(x, y, W, H).build();
        return ref[0];
    }

    private static Text label(String name, boolean on) {
        return Text.literal(name + ": " + (on ? "AN" : "AUS"));
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        super.render(ctx, mouseX, mouseY, delta);
        ctx.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 18, 0xFFFFFF);
    }
}
