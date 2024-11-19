package blankmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import blankmod.ModInitializer;
import blankmod.util.TextureLoader;

public abstract class AbstractBlankPower extends AbstractPower {

    public PowerStrings powerStrings;

    public AbstractBlankPower(
        String powerId,
        String powerIconPrefix,
        PowerType powerType,
        int amount,
        AbstractCreature owner
    ) {
        this.powerStrings = CardCrawlGame.languagePack.getPowerStrings(powerId);
        this.name = powerStrings.NAME;
        this.ID = powerId;
        this.amount = amount;
        this.owner = owner;
        this.type = powerType;

        this.region128 = new TextureAtlas.AtlasRegion(
            TextureLoader.getTexture(ModInitializer.makeImagePath("powers/" + powerIconPrefix + "_power84.png")),
            0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(
            TextureLoader.getTexture(ModInitializer.makeImagePath("powers/" + powerIconPrefix + "_power32.png")),
            0, 0, 32, 32);

        updateDescription();
    }

}
