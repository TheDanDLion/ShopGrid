package blankmod.ui.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.screens.options.ToggleButton;

public class CustomToggleButton extends ToggleButton {

    private static final float TXT_OFFSET = 30.0F * Settings.scale;
    private float x, y;

    private String label;

    public static class Enum {
        @SpireEnum
        public static ToggleBtnType CUSTOM;
    }

    public CustomToggleButton(String label, float x, float y) {
        super(x, y - Settings.HEIGHT / 2F, Settings.HEIGHT / 2F, Enum.CUSTOM);
        this.label = label;
        this.x = x;
        this.y = y;
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        FontHelper.renderFontLeft(sb, FontHelper.tipBodyFont, label, this.x + TXT_OFFSET, this.y + FontHelper.getHeight(FontHelper.tipBodyFont) / 2F, Color.WHITE);
    }
}
