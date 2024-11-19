package blankmod.debug;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import basemod.BaseMod;
import basemod.devcommands.ConsoleCommand;
import basemod.interfaces.PostInitializeSubscriber;
import basemod.interfaces.PostRenderSubscriber;
import basemod.interfaces.PostUpdateSubscriber;
import blankmod.ModInitializer;
import blankmod.debug.commands.MousePositionCommand;
import blankmod.debug.ui.ScouterDisplay;

public class DebugInitializer implements
    PostInitializeSubscriber,
    PostRenderSubscriber,
    PostUpdateSubscriber {

    public static final Logger logger = LogManager.getLogger(DebugInitializer.class.getName());

    private static boolean initialized = false;

    // =============== SUBSCRIBE, INITIALIZE =================

    public DebugInitializer() {
        logger.info("Subscribe to Debugger hooks");
        BaseMod.subscribe(this);
        logger.info("Done subscribing");
    }

    // =============== /SUBSCRIBE, INITIALIZE/ =================

    public static void initialize(){
        logger.info(ModInitializer.getModInfo().ModVersion.toString());
        if (ModInitializer.getModInfo().ModVersion.toString().contains("debug")) {
            logger.warn("========== DEBUGGER IS ON, MAKE SURE NOT TO PUSH THIS JAR TO PROD =========");
            logger.info("========================= Initializing Debugger. ==========================");
            new DebugInitializer();
            logger.info("========================= /Debugger Initialized./ =========================");
        }
    }

    // =============== POST-INITIALIZE =================

    @Override
    public void receivePostInitialize() {
        logger.info("Loading commands");
        ConsoleCommand.addCommand(MousePositionCommand.NAME, MousePositionCommand.class);
        initialized = true;
        logger.info("Done loadign commands");
    }

    // =============== /POST-INITIALIZE/ =================


    // ================ UPDATE & RENDER ===================

        @Override
        public void receivePostRender(SpriteBatch sb) {
            if (initialized)
                ScouterDisplay.render(sb);
        }

        @Override
        public void receivePostUpdate() {
            if (initialized)
                ScouterDisplay.update();
        }

    // ================ /UPDATE & RENDER/ ===================
}
