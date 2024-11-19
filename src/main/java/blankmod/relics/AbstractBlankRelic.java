package blankmod.relics;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.RelicStrings;

import basemod.abstracts.CustomRelic;
import blankmod.ModInitializer;
import blankmod.util.TextureLoader;

public class AbstractBlankRelic extends CustomRelic {

    public RelicStrings relicStrings;
    public boolean shared = false;

    public AbstractBlankRelic(String id, String name, RelicTier tier, LandingSound sfx, boolean shared) {
        super(
            id,
            TextureLoader.getTexture(ModInitializer.makeImagePath("relics/" + name + ".png")),
            TextureLoader.getTexture(ModInitializer.makeImagePath("relics/outline/" + name + ".png")),
            tier,
            sfx
        );
        this.shared = shared;

        relicStrings = CardCrawlGame.languagePack.getRelicStrings(id);
    }
}
