package com.memeasaur.saturationdisplayFabric.client;

import com.memeasaur.saturationdisplayFabric.SaturationDisplayConfig;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

final class SaturationOptionsScreen extends Screen {
    SaturationOptionsScreen() {
        super(Component.translatable("screen.saturationdisplay.title"));
    }

    @Override
    protected void init() {
        int x = this.width / 2 - 100;
        int y = this.height / 4;
        addRenderableWidget(Button.builder(label("screen.saturationdisplay.enabled", SaturationDisplayConfig.enabled), button -> {
            SaturationDisplayConfig.enabled = !SaturationDisplayConfig.enabled;
            button.setMessage(label("screen.saturationdisplay.enabled", SaturationDisplayConfig.enabled));
        }).bounds(x, y, 200, 20).build());
        addRenderableWidget(Button.builder(label("screen.saturationdisplay.number", SaturationDisplayConfig.showNumber), button -> {
            SaturationDisplayConfig.showNumber = !SaturationDisplayConfig.showNumber;
            button.setMessage(label("screen.saturationdisplay.number", SaturationDisplayConfig.showNumber));
        }).bounds(x, y + 24, 200, 20).build());
        addRenderableWidget(Button.builder(label("screen.saturationdisplay.color", SaturationDisplayConfig.coloredNumber), button -> {
            SaturationDisplayConfig.coloredNumber = !SaturationDisplayConfig.coloredNumber;
            button.setMessage(label("screen.saturationdisplay.color", SaturationDisplayConfig.coloredNumber));
        }).bounds(x, y + 48, 200, 20).build());
        addRenderableWidget(Button.builder(label("screen.saturationdisplay.outline", SaturationDisplayConfig.outlineHungerBar), button -> {
            SaturationDisplayConfig.outlineHungerBar = !SaturationDisplayConfig.outlineHungerBar;
            button.setMessage(label("screen.saturationdisplay.outline", SaturationDisplayConfig.outlineHungerBar));
        }).bounds(x, y + 72, 200, 20).build());
        addRenderableWidget(Button.builder(Component.translatable("gui.done"), button -> onClose())
                .bounds(x, y + 110, 200, 20).build());
    }

    @Override
    public void onClose() {
        SaturationDisplayConfig.save();
        super.onClose();
    }

//    @Override
//    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
//        extractBackground(graphics, mouseX, mouseY, partialTick);
//        graphics.centeredText(this.font, this.title, this.width / 2, 40, 0xFFFFFF);
//        super.extractRenderState(graphics, mouseX, mouseY, partialTick);
//    }

    private static Component label(String key, boolean value) {
        return Component.translatable(key, Component.translatable(value ? "options.on" : "options.off"));
    }
}
