package com.memeasaur.saturationdisplayFabric.client;

import com.memeasaur.saturationdisplayFabric.SaturationDisplayConfig;
// codex start
import net.minecraft.client.gui.GuiGraphics; // codex (import net.minecraft.client.gui.GuiGraphicsExtractor;)
// codex end
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

    // codex start
    // codex (//    @Override)
    // codex (//    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {)
    // codex (//        extractBackground(graphics, mouseX, mouseY, partialTick);)
    // codex (//        graphics.centeredText(this.font, this.title, this.width / 2, 40, 0xFFFFFF);)
    // codex (//        super.extractRenderState(graphics, mouseX, mouseY, partialTick);)
    // codex (//    })
    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(graphics, mouseX, mouseY, partialTick);
        graphics.drawCenteredString(this.font, this.title, this.width / 2, 40, 0xFFFFFFFF);
        super.render(graphics, mouseX, mouseY, partialTick);
    }
    // codex end

    private static Component label(String key, boolean value) {
        return Component.translatable(key, Component.translatable(value ? "options.on" : "options.off"));
    }
}
