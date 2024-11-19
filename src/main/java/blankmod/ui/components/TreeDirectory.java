package blankmod.ui.components;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Consumer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

import blankmod.util.PathWalker;

public class TreeDirectory {

    private ArrayList<TreeFolder> folders;

    private PathWalker walker;

    private float x;
    private float y;

    private Consumer<PathWalker.Node> onClickFile;
    public PathWalker.Node selectedNode;

    private static final float TAB_SPACE = 15.0F * Settings.scale;
    private static final float ROW_SPACE = FontHelper.getHeight(FontHelper.tipBodyFont) + 4.0F * Settings.scale;

    public TreeDirectory(PathWalker walker, float x, float y) {
        this.folders = new ArrayList<TreeFolder>();
        this.x = x;
        this.y = y;
        this.walker = walker;
        this.onClickFile = (n) -> {};

        for (Map.Entry<String, PathWalker.Node> node : walker.root.getChildren().entrySet()) {
            folders.add(new TreeFolder(node.getValue()));
        }
    }

    public void setOnClickFile(Consumer<PathWalker.Node> onClickFile) {
        this.onClickFile = onClickFile;
    }

    public float getHeight() {
        float height = 0.0F;
        for (TreeFolder folder : folders) {
            height += folder.getHeight();
        }
        return height;
    }

    public void update() {
        float y = this.y;
        for (TreeFolder folder : folders) {
            folder.update(x, y);
            y += folder.getHeight();
        }
    }

    public void render(SpriteBatch sb) {
        for (TreeFolder folder : folders) {
            folder.render(sb);
        }
    }

    private abstract class CustomTreeNode {

        protected PathWalker.Node node;
        protected float x;
        protected float y;
        protected int layer;
        protected String label;
        protected Hitbox hb;
        protected boolean hovered = false;

        public CustomTreeNode(PathWalker.Node node) {
            this.hb = new Hitbox(FontHelper.getWidth(FontHelper.tipBodyFont, label, 1.0F), FontHelper.getHeight(FontHelper.tipBodyFont, label, 1.0F));
            this.label = node.key;
            this.layer = node.layer;
        }

        public abstract void update(float x, float y);
        public abstract void render(SpriteBatch sb);
        public abstract float getHeight();
    }

    private class TreeFolder extends CustomTreeNode {

        private boolean expanded = false;

        private ArrayList<CustomTreeNode> nodes;

        public TreeFolder(PathWalker.Node node) {
            super(node);
            this.nodes = new ArrayList<CustomTreeNode>();
            walker.traverseTopLayer(node, (n) -> {
                if (n.isDirectory()) {
                    nodes.add(new TreeFolder(n));
                } else {
                    nodes.add(new TreeFile(n));
                }
            });
        }

        @Override
        public float getHeight() {
            float height = ROW_SPACE;
            if (expanded) {
                for (CustomTreeNode node : nodes) {
                    if (node.node.isDirectory())
                        height += ((TreeFolder) node).getHeight();
                    else
                        height += ROW_SPACE;
                }
            }
            return height;
        }

        @Override
        public void update(float x, float y) {
            this.x = x + TAB_SPACE * layer;
            this.y = y;
            this.hb.move(x, y);
            this.hb.update();
            if (this.hb.hovered && InputHelper.justClickedLeft) {
                expanded = !expanded;
            }
            float nextY = y;
            for (CustomTreeNode node : nodes) {
                node.update(x, nextY);
                nextY += node.getHeight();
            }
        }

        @Override
        public void render(SpriteBatch sb) {
            FontHelper.renderFontLeft(sb, FontHelper.tipBodyFont, label, this.x, this.y, Color.LIGHT_GRAY);
            if (expanded) {
                for (CustomTreeNode node : nodes) {
                    node.render(sb);
                }
            }
        }
    }

    private class TreeFile extends CustomTreeNode {

        private boolean selected = false;

        public TreeFile(PathWalker.Node node) {
            super(node);
        }

        @Override
        public void update(float x, float y) {
            this.x = x + TAB_SPACE * layer;
            this.y = y;
            this.hb.move(this.x, this.y);
            this.hb.update();
            if (this.hb.hovered) {
                this.hovered = true;
                if (InputHelper.justClickedLeft) {
                    onClickFile.accept(this.node);
                    this.selected = true;
                    selectedNode = this.node;
                }
            }
        }

        @Override
        public void render(SpriteBatch sb) {
            Color textColor = Color.WHITE;
            if (hovered)
                textColor = Settings.CREAM_COLOR;
            else if (selected)
                textColor = Settings.GREEN_TEXT_COLOR;
            FontHelper.renderFontLeft(sb, FontHelper.tipBodyFont, label, this.x, this.y, textColor);
            this.hb.render(sb);
        }

        @Override
        public float getHeight() {
            return ROW_SPACE;
        }
    }
}
