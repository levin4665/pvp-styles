package com.example.pvpstyles;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayDeque;
import java.util.Deque;

/** FPS- und CPS-Anzeige. Rein visuell, es wird nichts an den Server geschickt. */
public final class HudOverlay {
    private static final Deque<Long> LEFT = new ArrayDeque<>();
    private static final Deque<Long> RIGHT = new ArrayDeque<>();
    private static boolean leftDown, rightDown;

    public static void register() {
        HudRenderCallback.EVENT.register(HudOverlay::render);
    }

    private static void render(DrawContext ctx, net.minecraft.client.render.RenderTickCounter tick) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.options.hudHidden) return;

        pollClicks(mc);

        int y = 5;
        if (Settings.INSTANCE.showFps) {
            ctx.drawTextWithShadow(mc.textRenderer, "FPS: " + mc.getCurrentFps(), 5, y, 0xFFFFFFFF);
            y += 11;
        }
        if (Settings.INSTANCE.showCps) {
            ctx.drawTextWithShadow(mc.textRenderer, "CPS: " + count(LEFT) + " | " + count(RIGHT), 5, y, 0xFFFFFFFF);
        }
    }

    private static void pollClicks(MinecraftClient mc) {
        if (mc.currentScreen != null) {
            leftDown = rightDown = false;
            return;
        }
        long window = mc.getWindow().getHandle();
        boolean l = GLFW.glfwGetMouseButton(window, GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS;
        boolean r = GLFW.glfwGetMouseButton(window, GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS;
        long now = System.currentTimeMillis();
        if (l && !leftDown) LEFT.addLast(now);
        if (r && !rightDown) RIGHT.addLast(now);
        leftDown = l;
        rightDown = r;
    }

    private static int count(Deque<Long> q) {
        long cutoff = System.currentTimeMillis() - 1000;
        while (!q.isEmpty() && q.peekFirst() < cutoff) q.pollFirst();
        return q.size();
    }
}
