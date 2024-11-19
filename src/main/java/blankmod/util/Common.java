package blankmod.util;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class Common {

    public static boolean isInCombat() {
        AbstractRoom currRoom = AbstractDungeon.getCurrRoom();
        return !currRoom.isBattleOver
            && currRoom.monsters != null
            && currRoom.monsters.monsters != null
            && currRoom.monsters.monsters.size() > 0
            && !AbstractDungeon.getMonsters().areMonstersBasicallyDead();
    }

}
