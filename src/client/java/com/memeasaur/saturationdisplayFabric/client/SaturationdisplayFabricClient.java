package com.memeasaur.saturationdisplayFabric.client;

import com.memeasaur.saturationdisplayFabric.SaturationDisplayConfig;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.Identifier;

public class SaturationdisplayFabricClient implements ClientModInitializer {
    private static final Identifier HUD_ID = Identifier.fromNamespaceAndPath("saturationdisplay", "saturation");
    private static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(
            Identifier.fromNamespaceAndPath("saturationdisplay", "general"));
    private static KeyMapping optionsKey;

    @Override
    public void onInitializeClient() {
        SaturationDisplayConfig.load();
        optionsKey = KeyMappingHelper.registerKeyMapping(new KeyMapping("key.saturationdisplay.options", InputConstants.Type.KEYBOARD, InputConstants.KEY_O, CATEGORY));
        // A final HUD element is always extracted. Vanilla's individual status-bar
        // elements are conditional and may not run when their bar is absent.
        HudElementRegistry.addLast(HUD_ID, this::renderHud);
        ClientTickEvents.END_CLIENT_TICK.register(this::handleKeybind);
    }

    private void handleKeybind(Minecraft minecraft) {
        while (optionsKey.consumeClick()) {
            minecraft.setScreenAndShow(new SaturationOptionsScreen());
        }
    }

    private void renderHud(GuiGraphicsExtractor graphics, net.minecraft.client.DeltaTracker tickCounter) {
        Minecraft minecraft = Minecraft.getInstance();
        if (!SaturationDisplayConfig.enabled || minecraft.player == null
                || minecraft.player.isPassenger()) {
            return;
        }

        LocalPlayer player = minecraft.player;
        float saturation = player.getFoodData().getSaturationLevel();
        int x = graphics.guiWidth() / 2;
        int y = graphics.guiHeight() - 47;
        String text = Integer.toString((int) Math.ceil(saturation));
        // GuiGraphicsExtractor colors are ARGB. RGB-only values have an alpha of
        // zero, so the text is technically drawn but completely transparent.
        int color = SaturationDisplayConfig.coloredNumber ? saturationColor(saturation) : 0xFFFFFFFF;

        if (SaturationDisplayConfig.outlineHungerBar) {
            graphics.outline(x + 9, graphics.guiHeight() - 40, 82, 10, 0xAAFFFFFF);
        }
        if (SaturationDisplayConfig.showNumber) {
            graphics.centeredText(minecraft.font, text, x, y, color);
        }
    }

    private static int saturationColor(float saturation) {
        if (saturation >= 14.0F) return 0xFF55FF55;
        if (saturation >= 6.0F) return 0xFFFFFF55;
        return 0xFFFF5555;
    }
}
