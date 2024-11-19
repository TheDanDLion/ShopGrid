package blankmod.actions;

import java.util.Iterator;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

public class AddCardsToHandAction extends AbstractGameAction {

    private CardGroup group;

    public AddCardsToHandAction(CardGroup group) {
        this.group = group;
        this.amount = group.size();
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {
        if (this.amount == 0) {
            this.isDone = true;
            return;
        }
        int discardAmount = 0;
        int handAmount = this.amount;
        if (this.amount + AbstractDungeon.player.hand.size() > 10) {
            AbstractDungeon.player.createHandIsFullDialog();
            discardAmount = this.amount + AbstractDungeon.player.hand.size() - 10;
            handAmount -= discardAmount;
        }
        addToHand(handAmount);
        addToDiscard(discardAmount);
        if (this.amount > 0)
            addToTop((AbstractGameAction)new WaitAction(0.8F));
        this.isDone = true;
    }

    private void addToHand(int handAmt) {
        Iterator<AbstractCard> iterator;
        for (iterator = group.group.iterator(); iterator.hasNext(); iterator.next()) {
            AbstractCard card = iterator.next();
            group.removeCard(card);
            AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(card));
        }
        // switch (this.amount) {
        //     case 0:
        //         return;
        //     case 1:
        //         if (handAmt == 1)
        //             if (this.isOtherCardInCenter) {
        //                 AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(
        //                     cards.get(0), Settings.WIDTH / 2.0F - PADDING + AbstractCard.IMG_WIDTH, Settings.HEIGHT / 2.0F));
        //             } else {
        //                 AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(displayCard));
        //             }
        //     case 2:
        //         if (handAmt == 1) {
        //             AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(
        //                 displayCard, Settings.WIDTH / 2.0F - PADDING + AbstractCard.IMG_WIDTH * 0.5F, Settings.HEIGHT / 2.0F));
        //         } else if (handAmt == 2) {
        //             AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(
        //                 displayCard, Settings.WIDTH / 2.0F + PADDING + AbstractCard.IMG_WIDTH, Settings.HEIGHT / 2.0F));
        //             AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(
        //                 displayCard, Settings.WIDTH / 2.0F - PADDING + AbstractCard.IMG_WIDTH, Settings.HEIGHT / 2.0F));
        //         }
        //     case 3:
        //         if (handAmt == 1) {
        //             AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(
        //                 displayCard, Settings.WIDTH / 2.0F - PADDING + AbstractCard.IMG_WIDTH, Settings.HEIGHT / 2.0F));
        //         } else if (handAmt == 2) {
        //             AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(
        //                 displayCard, Settings.WIDTH / 2.0F + PADDING + AbstractCard.IMG_WIDTH, Settings.HEIGHT / 2.0F));
        //             AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(
        //                 displayCard, Settings.WIDTH / 2.0F - PADDING + AbstractCard.IMG_WIDTH, Settings.HEIGHT / 2.0F));
        //         } else if (handAmt == 3) {
        //             for (int j = 0; j < this.amount; j++)
        //                 AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(displayCard));
        //         }
        // }
        // for (int i = 0; i < handAmt; i++) {

        // }
        //     AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(
        //         card,
        //         MathUtils.random(Settings.WIDTH * 0.2F, Settings.WIDTH * 0.8F),
        //         MathUtils.random(Settings.HEIGHT * 0.3F, Settings.HEIGHT * 0.7F)));
    }

    private void addToDiscard(int discardAmt) {
        Iterator<AbstractCard> iterator;
        for (iterator = group.group.iterator(); iterator.hasNext(); iterator.next()) {
            AbstractCard card = iterator.next();
            group.removeCard(card);
            AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(card));
        }

        // AbstractCard card = group.getTopCard();
        // AbstractCard displayCard = card.makeStatEquivalentCopy();
        // group.removeTopCard();
        // switch (this.amount) {
        //     case 0:
        //         return;
        //     case 1:
        //         if (discardAmt == 1)
        //             AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(
        //             displayCard, Settings.WIDTH / 2.0F + PADDING + AbstractCard.IMG_WIDTH, Settings.HEIGHT / 2.0F));
        //     case 2:
        //         if (discardAmt == 1) {
        //             AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(
        //                 displayCard, Settings.WIDTH * 0.5F - PADDING + AbstractCard.IMG_WIDTH * 0.5F, Settings.HEIGHT * 0.5F));
        //         } else if (discardAmt == 2) {
        //             AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(
        //                 displayCard, Settings.WIDTH * 0.5F - PADDING + AbstractCard.IMG_WIDTH * 0.5F, Settings.HEIGHT * 0.5F));
        //             AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(
        //                 displayCard, Settings.WIDTH * 0.5F + PADDING + AbstractCard.IMG_WIDTH * 0.5F, Settings.HEIGHT * 0.5F));
        //         }
        //     case 3:
        //         if (discardAmt == 1) {
        //             AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(
        //                 displayCard, Settings.WIDTH * 0.5F + PADDING + AbstractCard.IMG_WIDTH, Settings.HEIGHT * 0.5F));
        //         } else if (discardAmt == 2) {
        //             AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(
        //                 displayCard, Settings.WIDTH * 0.5F, Settings.HEIGHT * 0.5F));
        //             AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(
        //                 displayCard, Settings.WIDTH * 0.5F + PADDING + AbstractCard.IMG_WIDTH, Settings.HEIGHT * 0.5F));
        //         } else if (discardAmt == 3) {
        //             AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(
        //                 displayCard, Settings.WIDTH * 0.5F, Settings.HEIGHT * 0.5F));
        //             AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(
        //                 displayCard, Settings.WIDTH * 0.5F - PADDING + AbstractCard.IMG_WIDTH, Settings.HEIGHT * 0.5F));
        //             AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(
        //                 displayCard, Settings.WIDTH * 0.5F + PADDING + AbstractCard.IMG_WIDTH, Settings.HEIGHT * 0.5F));
        //         }
        //     }
        // for (int i = 0; i < discardAmt; i++) {
        //     AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(
        //         card,
        //         MathUtils.random(Settings.WIDTH * 0.2F, Settings.WIDTH * 0.8F),
        //         MathUtils.random(Settings.HEIGHT * 0.3F, Settings.HEIGHT * 0.7F)));
        // }
    }
}
