package com.memeasaur.saturationdisplayFabric.client;

import com.memeasaur.saturationdisplayFabric.SaturationDisplayConfig;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
// codex start
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper; // codex (import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;)
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback; // codex (import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;)
// codex end
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
// codex start
import net.minecraft.client.gui.GuiGraphics; // codex (import net.minecraft.client.gui.GuiGraphicsExtractor;)
// codex end
import net.minecraft.client.player.LocalPlayer;

public class SaturationdisplayFabricClient implements ClientModInitializer {
    // codex start
    // codex (import net.minecraft.resources.Identifier;)
    // codex (private static final Identifier HUD_ID = Identifier.fromNamespaceAndPath("saturationdisplay", "saturation");)
    // codex (private static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(Identifier.fromNamespaceAndPath("saturationdisplay", "general"));)
    // codex end
    private static KeyMapping optionsKey;

    @Override
    public void onInitializeClient() {
        SaturationDisplayConfig.load();
        // codex start
        optionsKey = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.saturationdisplay.options", InputConstants.Type.KEYSYM, InputConstants.KEY_O, "key.categories.saturationdisplay")); // codex (optionsKey = KeyMappingHelper.registerKeyMapping(new KeyMapping("key.saturationdisplay.options", InputConstants.Type.KEYBOARD, InputConstants.KEY_O, CATEGORY));)
        HudRenderCallback.EVENT.register(this::renderHud); // codex (HudElementRegistry.addLast(HUD_ID, this::renderHud);)
        // codex end
        ClientTickEvents.END_CLIENT_TICK.register(this::handleKeybind);
    }

    private void handleKeybind(Minecraft minecraft) {
        while (optionsKey.consumeClick()) {
            // codex start
            minecraft.setScreen(new SaturationOptionsScreen()); // codex (minecraft.setScreenAndShow(new SaturationOptionsScreen());)
            // codex end
        }
    }

    // codex start
    private void renderHud(GuiGraphics graphics, net.minecraft.client.DeltaTracker tickCounter) { // codex (private void renderHud(GuiGraphicsExtractor graphics, net.minecraft.client.DeltaTracker tickCounter) {)
    // codex end
        Minecraft minecraft = Minecraft.getInstance();
        // codex start
        if (!SaturationDisplayConfig.enabled || minecraft.options.hideGui || minecraft.player == null // codex (if (!SaturationDisplayConfig.enabled || minecraft.player == null)
        // codex end
                || minecraft.player.isPassenger()) {
            return;
        }

        LocalPlayer player = minecraft.player;
        float saturation = player.getFoodData().getSaturationLevel();
        int x = graphics.guiWidth() / 2;
        int y = graphics.guiHeight() - 47;
        String text = Integer.toString((int) Math.ceil(saturation));
        int color = SaturationDisplayConfig.coloredNumber ? saturationColor(saturation) : 0xFFFFFFFF;

        if (SaturationDisplayConfig.outlineHungerBar) {
            // codex start
            graphics.renderOutline(x + 9, graphics.guiHeight() - 40, 82, 10, 0xAAFFFFFF); // codex (graphics.outline(x + 9, graphics.guiHeight() - 40, 82, 10, 0xAAFFFFFF);)
            // codex end
        }
        if (SaturationDisplayConfig.showNumber) {
            // codex start
            graphics.drawCenteredString(minecraft.font, text, x, y, color); // codex (graphics.centeredText(minecraft.font, text, x, y, color);)
            // codex end
        }
    }

    private static int saturationColor(float saturation) {
        if (saturation >= 14.0F) return 0xFF55FF55;
        if (saturation >= 6.0F) return 0xFFFFFF55;
        return 0xFFFF5555;
    }
}
