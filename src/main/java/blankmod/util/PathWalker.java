package blankmod.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import blankmod.ModInitializer;

// https://stackoverflow.com/questions/18590694/how-to-convert-list-of-filepaths-to-a-hireachial-tree-in-java
// my own messed up version of a path walker

public class PathWalker {
    public static class Node {
        private final Map<String, Node> children = new TreeMap<>();
        public String key;
        public Node parent;
        public int layer;

        public Node(String key, Node parent, int layer) {
            this.key = key;
            this.parent = parent;
            this.layer = layer;
        }

        public Node getChild(String name) {
            if (children.containsKey(name))
                return children.get(name);
            Node result = new Node(name, this, this.layer + 1);
            children.put(name, result);
            return result;
        }

        public boolean isDirectory() {
            return !children.isEmpty() && !key.contains(".");
        }

        public Map<String, Node> getChildren() {
            return Collections.unmodifiableMap(children);
        }

        public String getPath() {
            if (parent == null)
                return key;
            return parent.getPath() + "/" + key;
        }
    }

    public final Node root = new Node("", null, 0);

    private static final Pattern PATH_SEPARATOR = Pattern.compile("/");
    public void addPath(String path) {
        String[] names = PATH_SEPARATOR.split(path);
        String[] names2 = Pattern.compile("\\\\").split(path);
        for (String name : names2)
            ModInitializer.logger.info("name2: " + name);
        for (String name : names)
            ModInitializer.logger.info("name: " + name);
        Node node = root;
        for (String name : names)
            node = node.getChild(name);
    }

    public String[] getPaths() {
        return getPaths(root);
    }

    private String[] getPaths(Node node) {
        Map<String, Node> children = node.getChildren();
        if (children.isEmpty())
            return new String[] {};
        ArrayList<String> paths = new ArrayList<>();
        for (Map.Entry<String, Node> child : children.entrySet()) {
            String[] childPaths = getPaths(child.getValue());
            if (childPaths.length == 0) {
                paths.add(child.getValue().getPath());
            } else {
                for (String childPath : childPaths)
                    paths.add(childPath);
            }
        }
        return (String[])paths.toArray(new String[paths.size()]);
    }

    public void traverseTopLayer(Consumer<Node> action) {
        traverseTopLayer(root, action);
    }

    public void traverseTopLayer(Node node, Consumer<Node> action) {
        Map<String, Node> children = node.getChildren();
        for (Map.Entry<String, Node> child : children.entrySet()) {
            action.accept(child.getValue());
        }
    }
}