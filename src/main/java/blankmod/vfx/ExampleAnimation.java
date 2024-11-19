package blankmod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.esotericsoftware.spine.SkeletonMeshRenderer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ExampleAnimation {

    public static final String ATLAS_URL = "blankModResources/resources/";
    public static final String SKELETON_URL = "blankModResources/resources/";

    private static SkeletonMeshRenderer sr;
    private static TextureAtlas atlas;

    private Skeleton skeleton;
    private AnimationStateData stateData;
    private AnimationState state;

    public float x, y;
    public float scale = 1F;
    public float timeScale = 1F;
    public String startingAnimation = "idle";
    public boolean loop = true;

    public static void init(String atlasUrl) {
        sr = new SkeletonMeshRenderer();
        atlas = new TextureAtlas(Gdx.files.internal(atlasUrl));
    }

    public ExampleAnimation() {
        x = AbstractDungeon.player.drawX;
        y = AbstractDungeon.player.drawY;
        SkeletonJson json = new SkeletonJson(atlas);
        json.setScale(scale);
        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal(SKELETON_URL));
        skeleton = new Skeleton(skeletonData);
        skeleton.setColor(Color.WHITE);
        stateData = new AnimationStateData(skeletonData);
        state = new AnimationState(stateData);
        state.setAnimation(0, startingAnimation, true);
        state.setTimeScale(timeScale);
    }

    public void update() {}

    public void render(SpriteBatch sb) {
        if (atlas != null) {
            state.update(Gdx.graphics.getDeltaTime());
            state.apply(skeleton);
            skeleton.updateWorldTransform();
            skeleton.setPosition(x, y);
            skeleton.setColor(Color.WHITE);
            skeleton.setFlip(false, false);
            sb.end();
            CardCrawlGame.psb.begin();
            sr.draw(CardCrawlGame.psb, skeleton);
            CardCrawlGame.psb.end();
            sb.begin();
        }
    }

}
