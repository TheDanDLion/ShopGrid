package blankmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

public class RemoveFromMasterDeckAction extends AbstractGameAction {

    private AbstractCard card;

    public RemoveFromMasterDeckAction(AbstractCard card) {
        this.card = card;
    }

    @Override
    public void update() {
        for (AbstractCard masterDeckCard : AbstractDungeon.player.masterDeck.group)
            if (this.card.uuid.equals(masterDeckCard.uuid)) {
                AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(masterDeckCard, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                AbstractDungeon.player.masterDeck.removeCard(masterDeckCard);
                break;
            }
        this.isDone = true;
    }
}
