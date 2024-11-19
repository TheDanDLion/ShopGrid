package blankmod;

import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.abstracts.CustomSavableRaw;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import blankmod.util.IDCheckDontTouchPls;
import blankmod.util.TextureLoader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.Patcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.scannotation.AnnotationDB;

@SpireInitializer
public class ModInitializer implements
    EditRelicsSubscriber,
    EditStringsSubscriber,
    PostInitializeSubscriber {

    public static final Logger logger = LogManager.getLogger(ModInitializer.class.getName());
    private static ModInfo modInfo;
    private static String modID;

    // Mod-settings settings. This is if you want an on/off savable button
    public static Properties modProperties = new Properties();
    public static final String ENABLE_PLACEHOLDER_SETTINGS = "enablePlaceholder";
    public static boolean enablePlaceholder = true; // The boolean we'll be setting on/off (true/false)

    //This is for the in-game mod settings panel.
    private static String modName;
    private static String author;
    private static String description;

    // =============== INPUT TEXTURE LOCATION =================

    //Mod Badge - A small icon that appears in the mod settings menu next to your mod.
    public static String badgeImagePath;

    //Missing texture - the image that's displayed when a texture isn't found.
    public static String missingTexturePath;

    // =============== MAKE IMAGE PATHS =================

    public static String makeAnimationPath(String resourcePath) {
        return getModID() + "Resources/animations/" + resourcePath;
    }

    public static String makeImagePath(String resourcePath) {
        return getModID() + "Resources/images/" + resourcePath;
    }

    public static String makeLocalizationPath(String resourcePath) {
        return getModID() + "Resources/localization/eng/" + resourcePath;
    }

    // =============== /MAKE IMAGE PATHS/ =================


    // =============== SUBSCRIBE, INITIALIZE =================

    static {
        Optional<ModInfo> infos = Arrays.<ModInfo>stream(Loader.MODINFOS).filter(modInfo -> {
            AnnotationDB annotationDB = (AnnotationDB)Patcher.annotationDBMap.get(modInfo.jarURL);
            if (annotationDB == null)
                return false;
            Set<String> initializers = (Set<String>)annotationDB.getAnnotationIndex().getOrDefault(SpireInitializer.class.getName(), Collections.emptySet());
            return initializers.contains(ModInitializer.class.getName());
        }).findFirst();
        if (infos.isPresent()) {
            modInfo = infos.get();
            modName = modInfo.Name;
            author = modInfo.Authors[0];
            description = modInfo.Description;
            badgeImagePath = modInfo.ID + "Resources/images/badge.png";
            missingTexturePath = modInfo.ID + "Resources/images/missing_texture.png";
        } else {
            throw new RuntimeException("Failed to determine mod info/ID based on initializer.");
        }
    }

    public ModInitializer() {
        logger.info("Subscribe to " + modInfo.Name + " hooks");

        BaseMod.subscribe(this);
        setModID(modInfo.ID);

        logger.info("Done subscribing");

        logger.info("Adding mod settings");
        modProperties.setProperty(ENABLE_PLACEHOLDER_SETTINGS, "FALSE");
        try {
            SpireConfig config = new SpireConfig(getModID(), getModID() + "Config", modProperties); // ...right here
            // the "fileName" parameter is the name of the file MTS will create where it will save our setting.
            config.load(); // Load the setting and set the boolean to equal it
            enablePlaceholder = config.getBool(ENABLE_PLACEHOLDER_SETTINGS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Done adding mod settings");

        ///###---DEBUGGER---###///
        try {
            Class<?> cls = Class.forName("blankmod.debug.DebugInitializer");
            cls.getMethod("initialize").invoke(cls.getDeclaredConstructor().newInstance());
        } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
            | NoSuchMethodException | SecurityException | InstantiationException e) {
            logger.info("debug tools not found... well anyway");
        }
        ///###---DEBUGGER---###///
    }

    // ====== NO EDIT AREA ======
    // DON'T TOUCH THIS STUFF. IT IS HERE FOR STANDARDIZATION BETWEEN MODS AND TO ENSURE GOOD CODE PRACTICES.
    // IF YOU MODIFY THIS I WILL HUNT YOU DOWN AND DOWNVOTE YOUR MOD ON WORKSHOP

    public static void setModID(String ID) { // DON'T EDIT
        Gson coolG = new Gson(); // EY DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i hate u Gdx.files
        InputStream in = ModInitializer.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THIS ETHER
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // OR THIS, DON'T EDIT IT
        logger.info("You are attempting to set your mod ID as: " + ID); // NO WHY
        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) { // DO *NOT* CHANGE THIS ESPECIALLY, TO EDIT YOUR MOD ID, SCROLL UP JUST A LITTLE, IT'S JUST ABOVE
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION); // THIS ALSO DON'T EDIT
        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) { // NO
            modID = EXCEPTION_STRINGS.DEFAULTID; // DON'T
        } else { // NO EDIT AREA
            modID = ID; // DON'T WRITE OR CHANGE THINGS HERE NOT EVEN A LITTLE
        } // NO
        logger.info("Success! ID is " + modID); // WHY WOULD U WANT IT NOT TO LOG?? DON'T EDIT THIS.
    } // NO

    public static String getModID() { // NO
        return modID; // DOUBLE NO
    } // NU-UH

    private static void pathCheck() { // ALSO NO
        Gson coolG = new Gson(); // NOPE DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i still hate u btw Gdx.files
        InputStream in = ModInitializer.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THISSSSS
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // NAH, NO EDIT
        String packageName = ModInitializer.class.getPackage().getName(); // STILL NO EDIT ZONE
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources"); // PLEASE DON'T EDIT THINGS HERE, THANKS
        if (!modID.equals(EXCEPTION_STRINGS.DEVID)) { // LEAVE THIS EDIT-LESS
            if (!packageName.equals(getModID())) { // NOT HERE ETHER
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID()); // THIS IS A NO-NO
            } // WHY WOULD U EDIT THIS
            if (!resourcePathExists.exists()) { // DON'T CHANGE THIS
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources"); // NOT THIS
            }// NO
        }// NO
    }// NO

    // ====== YOU CAN EDIT AGAIN ======

    //Used by @SpireInitializer
    public static void initialize(){
        logger.info("========================= Initializing " + modInfo.Name + ". ==========================");
        //This creates an instance of our classes and gets our code going after BaseMod and ModTheSpire initialize.
        new ModInitializer();
        logger.info("========================= /" + modInfo.Name + " Initialized./ =========================");
    }

    // ============== /SUBSCRIBE, INITIALIZE/ =================


    // =============== POST-INITIALIZE =================

    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");
        Texture badgeTexture = TextureLoader.getTexture(badgeImagePath);
        ModPanel settingsPanel = new ModPanel();
        ModLabeledToggleButton enableNormalsButton = new ModLabeledToggleButton("This is the text which goes next to the checkbox.",
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, // Position (trial and error it), color, font
                enablePlaceholder,
                settingsPanel,
                (label) -> {},
                (button) -> {

            enablePlaceholder = button.enabled;
            try {
                SpireConfig config = new SpireConfig(getModID(), getModID() + "Config", modProperties);
                config.setBool(ENABLE_PLACEHOLDER_SETTINGS, enablePlaceholder);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        settingsPanel.addUIElement(enableNormalsButton); // Add the button to the settings panel. Button is a go.
        BaseMod.registerModBadge(badgeTexture, modName, author, description, settingsPanel);
        logger.info("Done loading badge Image and mod options");

        // =============== EVENTS =================
        // logger.info("Adding events");
        // =============== /EVENTS/ =================

        // =============== SCREENS =================
        // logger.info("Adding screens");
        // ============== /SCREENS/ ================

        // ============== POWERS ==================
        // logger.info("Adding powers");
        // ============== /POWERS/ ================

        // ============== TEXTURES ==================
        // logger.info ("Loading static texture");
        // ============== /TEXTURES/ ==================

        // ============== SAVE FIELDS ==================
        logger.info("Adding save fields");
        BaseMod.addSaveField("blankSaveField", new CustomSavableRaw() {
            @Override
            public void onLoadRaw(JsonElement json) {
                if (AbstractDungeon.player != null) {
                    logger.info(json);
                    if (json != null) {
                        JsonObject val = json.getAsJsonObject();
                        logger.info("value of blankField: " + val.getAsBoolean());
                    }
                }
            }

            @Override
            public JsonElement onSaveRaw() {
                JsonParser parser = new JsonParser();
                return parser.parse(
                    "{\"blankSaveField\":\""
                    + true
                    + "\", \"blankSaveField2\":\""
                    + false
                    + "\"}"
                );
            }
        });
        // ============== /SAVE FIELDS/ ==================
    }

    // =============== /POST-INITIALIZE/ =================


    // ================ LOAD THE TEXT ===================

    @Override
    public void receiveEditStrings() {
        logger.info("Beginning to edit strings for mod with ID: " + getModID());

        pathCheck();

        BaseMod.loadCustomStringsFile(CardStrings.class, makeLocalizationPath(getModID() + "-Card-Strings.json"));
        BaseMod.loadCustomStringsFile(PowerStrings.class, makeLocalizationPath(getModID() + "-Power-Strings.json"));
        BaseMod.loadCustomStringsFile(RelicStrings.class, makeLocalizationPath(getModID() + "-Relic-Strings.json"));
        BaseMod.loadCustomStringsFile(EventStrings.class, makeLocalizationPath(getModID() + "-Event-Strings.json"));
        BaseMod.loadCustomStringsFile(PotionStrings.class, makeLocalizationPath(getModID() + "-Potion-Strings.json"));
        BaseMod.loadCustomStringsFile(CharacterStrings.class, makeLocalizationPath(getModID() + "-Character-Strings.json"));
        BaseMod.loadCustomStringsFile(OrbStrings.class, makeLocalizationPath(getModID() + "-Orb-Strings.json"));
        BaseMod.loadCustomStringsFile(UIStrings.class, makeLocalizationPath(getModID() + "-UI-Strings.json"));

        logger.info("Done editing strings");
    }

    // ================ /LOAD THE TEXT/ ===================


    // ================ ADD RELICS ===================

    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");
        // TOOD: likely not working
        // new AutoAdd(modID)
        //     .packageFilter(AbstractBlankRelic.class)
        //     .any(CustomRelic.class, (info, relic) -> {
        //         logger.info(relic.relicId);
        //         if (relic instanceof AbstractBlankRelic) {
        //             if (((AbstractBlankRelic)relic).cardColor == null) {
        //                 BaseMod.addRelic((AbstractRelic)relic, RelicType.SHARED);
        //             } else {
        //                 BaseMod.addRelicToCustomPool((AbstractRelic)relic, ((AbstractBlankRelic)relic).cardColor);
        //             }
        //         }
        //         if (info.seen)
        //             UnlockTracker.markRelicAsSeen(relic.relicId);
        //     });
        logger.info("Done adding relics");
    }

    // ================ /ADD RELICS/ ===================


    public static ModInfo getModInfo() {
        return modInfo;
    }

    // this adds "ModName:" before the ID of any card/relic/power etc.
    // in order to avoid conflicts if any other mod uses the same ID.
    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }
}
