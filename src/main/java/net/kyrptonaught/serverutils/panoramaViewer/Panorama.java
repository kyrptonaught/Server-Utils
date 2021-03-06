package net.kyrptonaught.serverutils.panoramaViewer;

import com.google.gson.JsonParseException;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.Objects;

public class Panorama {
    public String panoramaName;

    public Text text;

    public FrameCounter frameCounter;
    public Padder padder;

    public Panorama(String panorama, String text, FrameCounter frameCounter, Padder padder) {
        this.panoramaName = panorama;

        this.text = parseToText(text);
        this.frameCounter = frameCounter;
        this.padder = padder;
    }

    public void tickFrameCounter() {
        frameCounter.tick();
        padder.updatePadding(frameCounter);
    }

    private MutableText parseToText(String text) {
        try {
            return Objects.requireNonNullElseGet(Text.Serializer.fromJson(text), () -> new LiteralText(text));
        } catch (JsonParseException var4) {
            return new LiteralText(text);
        }
    }
}
