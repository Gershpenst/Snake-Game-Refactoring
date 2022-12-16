package snippet.utils;

import snippet.Main;

import javax.swing.*;

public enum Position {
    UP(1, new ImageIcon(Main.class.getResource("/resources/random.png"))),
    RIGHT(2, new ImageIcon(Main.class.getResource("/resources/random.png"))),
    DOWN(3, new ImageIcon(Main.class.getResource("/resources/random.png"))),
    LEFT(4, new ImageIcon(Main.class.getResource("/resources/random.png")));

    private final int position;
    private final ImageIcon image;

    private Position(int position, ImageIcon image) {
        this.position = position;
        this.image = image;
    }

    public int getPosition() {
        return this.position;
    }

    public ImageIcon getImage() {
        return this.image;
    }

}
