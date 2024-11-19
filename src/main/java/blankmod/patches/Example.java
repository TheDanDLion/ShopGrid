// For more patching info, see:
// https://github.com/kiooeht/ModTheSpire/wiki/SpirePatch2

package blankmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;

import blankmod.ModInitializer;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

@SpirePatch2(
    clz = CardCrawlGame.class,
    method = SpirePatch.CLASS
)
public class Example {
    public static SpireField<Boolean> testValue = new SpireField<>(() -> false);

    @SpirePatch2(
        clz = CardCrawlGame.class,
        method = "create"
        // modID = "modId"
    )
    public static class ExamplePatch {
        public static SpireReturn<Void> Prefix(@ByRef boolean[] ___fadeIn) {
            ModInitializer.logger.info("This is an example of a prefix patch");
            return SpireReturn.Continue();
        }

        public static void Postfix(CardCrawlGame __instance) {
            ModInitializer.logger.info("This is an example of a postfix patch");
        }

        @SpireInsertPatch(
            locator = Locator.class, // the best option
            localvars = { "buildSettings" }
            // loc = 69, // local to file
            // locs = [ 69, ... ],
            // rloc = 0, // local to start of function, second best option
            // rlocs = [ 0, ... ]
        )
        public static void Insert(Object buildSettings) { // you dont' need the exact type, you can use a supertype
            if (buildSettings == null)
                throw new NullPointerException();
            ModInitializer.logger.info("This is an example of an insert patch");
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ct) throws Exception {
                return LineFinder.findInOrder(ct, new Matcher.MethodCallMatcher(Settings.class, "initialize"));
            }
        }

        public static void logInstrumentCall(boolean after) {
            ModInitializer.logger.info("This is an example of an instrument patch " + (after ? "after" : "before") + " the line");
        }

        // javassist tutorial   - https://www.javassist.org/tutorial/tutorial2.html
        // javadoc              - https://www.javassist.org/html/index.html
        @SpireInstrumentPatch
        public static ExprEditor Editor() {
            return new ExprEditor() {
                public void edit(MethodCall m) {
                    try {
                        if (m.getMethodName().equals("saveMigration")) {
                            m.replace(""
                                + "{"
                                + "blankmod.patches.Example.ExamplePatch.logInstrumentCall(false);"
                                + "$_ = $proceed($$);"
                                + "blankmod.patches.Example.ExamplePatch.logInstrumentCall(true);"
                                + "}"
                            );
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
        }
    }
}
