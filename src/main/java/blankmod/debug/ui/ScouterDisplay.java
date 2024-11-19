package blankmod.debug.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

import basemod.patches.com.megacrit.cardcrawl.helpers.TipHelper.HeaderlessTip;

// TODO: find a way to make a tool that's not packaged into final product

public class ScouterDisplay {

    public static boolean renderDisplay = false;

    private static int x;
    private static int y;

    private static String text = "";

    public static void update() {
        if (!renderDisplay)
            return;

        x = (int)(InputHelper.mX + 75.0F * Settings.scale);
        if (x <= Settings.WIDTH * 0.05F)
            x = (int)(Settings.WIDTH * 0.05F);
        else if (x >= Settings.WIDTH * 0.83F)
            x = (int)(Settings.WIDTH * 0.83F);

        y = (int)(InputHelper.mY - 75.0F * Settings.scale);
        if (y <= Settings.HEIGHT * 0.1F)
            y = (int)(Settings.HEIGHT * 0.1F);
        else if (y >= Settings.HEIGHT * 0.9F)
            y = (int)(Settings.HEIGHT * 0.9F);

        float xCoord = ((float)InputHelper.mX / (float)Settings.WIDTH);
        float yCoord = ((float)InputHelper.mY / (float)Settings.HEIGHT);

        text = "X: " + InputHelper.mX + " NL Y:" + InputHelper.mY + " NL (" + String.format("%.4f", xCoord) + ", " + String.format("%.4f", yCoord) + ")";
    }

    public static void render(SpriteBatch sb) {
        if (!renderDisplay)
            return;
        HeaderlessTip.renderHeaderlessTip(x, y, text);
        sb.setColor(Color.RED);
        sb.draw(ImageMaster.DEBUG_HITBOX_IMG, InputHelper.mX - 10.0F, InputHelper.mY - 10.0F, 20.0F, 20.F);
    }
}
