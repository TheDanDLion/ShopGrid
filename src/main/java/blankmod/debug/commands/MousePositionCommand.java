package blankmod.debug.commands;

import basemod.devcommands.ConsoleCommand;
import blankmod.debug.ui.ScouterDisplay;

public class MousePositionCommand extends ConsoleCommand {

    public static final String NAME = "mouse";

    public MousePositionCommand () {
        maxExtraTokens = 0;
        minExtraTokens = 0;
        requiresPlayer = false;
        simpleCheck = true;
    }

    @Override
    protected void execute(String[] arg0, int arg1) {
        ScouterDisplay.renderDisplay = !ScouterDisplay.renderDisplay;
    }
}
