package blankmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class BetterReducePowerAction extends AbstractGameAction {

    private AbstractCreature source;

    private AbstractPower power;

    private int amount;

    public BetterReducePowerAction(AbstractCreature s, AbstractPower p, int amt) {
        source = s;
        power = p;
        amount = amt;
    }

    @Override
    public void update() {
        if ((power.amount - amount <= 0 && !power.canGoNegative) || (power.amount - amount == 0 && power.canGoNegative))
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(power.owner, source, power));
        else
            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(power.owner, source, power, amount));
        this.isDone = true;
    }
    
}
