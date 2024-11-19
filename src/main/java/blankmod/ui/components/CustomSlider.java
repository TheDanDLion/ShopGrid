package blankmod.ui.components;

import java.util.function.Function;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.options.Slider;

import basemod.ReflectionHacks;

public class CustomSlider extends Slider {

    private String label;

    private Function<Float, String> valueFormatter;
    private Function<Float, Float> valueTransformer;

    private static final float SLIDE_W = ReflectionHacks.getPrivateStatic(Slider.class, "SLIDE_W");

    private float x;
    private float sliderX;
    private float y;
    private float percentage;

    public static class Enum {
        @SpireEnum
        public static SliderType CUSTOM;
    }

    public CustomSlider(String label, float x, float y, float percentage) {
        super(y, 0.0F, Enum.CUSTOM);
        this.x = x;
        this.sliderX = x + SLIDE_W * percentage - ImageMaster.OPTION_SLIDER.getWidth() / 2.0F;
        this.y = y;
        this.percentage = percentage;
        this.label = label;
        this.bgHb.move(this.x + SLIDE_W / 2F - ImageMaster.OPTION_SLIDER.getWidth() / 2F, this.y);
        setValueFormatter((value) -> value.toString());
        setValueTransformer((value) -> value);
    }

    public void setValueTransformer(Function<Float, Float> valueTransformer) {
        this.valueTransformer = valueTransformer;
    }

    public void setValueFormatter(Function<Float, String> valueFormatter) {
        this.valueFormatter = valueFormatter;
    }

    public void reset() {
        ReflectionHacks.setPrivate(this, Slider.class, "volume", this.percentage);
        this.sliderX = x + SLIDE_W * percentage;
    }

    @Override
    public void update() {
        this.hb.move(this.sliderX, this.y);
        this.hb.update();
        this.bgHb.update();
        boolean sliderGrabbed = ReflectionHacks.getPrivate(this, Slider.class, "sliderGrabbed");
        if (sliderGrabbed) {
            if (InputHelper.isMouseDown) {
                this.sliderX = MathHelper.fadeLerpSnap(this.sliderX, InputHelper.mX);
                if (this.sliderX < this.x)
                    this.sliderX = this.x;
                else if (this.sliderX > SLIDE_W + this.x)
                    this.sliderX = SLIDE_W + this.x;
                setPercentage((this.sliderX - this.x) / SLIDE_W);
            } else {
                ReflectionHacks.setPrivate(this, Slider.class, "sliderGrabbed", false);
            }
        } else if (InputHelper.justClickedLeft && (this.hb.hovered || this.bgHb.hovered)) {
            ReflectionHacks.setPrivate(this, Slider.class, "sliderGrabbed", true);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.OPTION_SLIDER_BG, this.x + ImageMaster.OPTION_SLIDER.getWidth() / 2F - 2.0F * Settings.scale, this.y - 12.0F, 125.0F, 12.0F, 250.0F, 24.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 250, 24, false, false);
        FontHelper.renderFontRightAligned(sb, FontHelper.tipBodyFont, label, this.x - 30.0F * Settings.scale, this.y, Color.WHITE);
        FontHelper.renderFontLeft(sb, FontHelper.tipBodyFont, valueFormatter.apply(getValue()), this.x + ImageMaster.OPTION_SLIDER_BG.getWidth() + 70.0F * Settings.scale, this.y, Settings.BLUE_TEXT_COLOR);
        sb.draw(ImageMaster.OPTION_SLIDER, this.sliderX - 22.0F, this.y - 22.0F, 22.0F, 22.0F, 44.0F, 44.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 44, 44, false, false);
        this.hb.render(sb);
        this.bgHb.render(sb);
    }

    public float getValue() {
        return valueTransformer.apply(getPercentage());
    }

    public float getPercentage() {
        return (float)ReflectionHacks.getPrivate(this, Slider.class, "volume");
    }

    public void setPercentage(float percentage) {
        ReflectionHacks.setPrivate(this, Slider.class, "volume", percentage);
    }
}
