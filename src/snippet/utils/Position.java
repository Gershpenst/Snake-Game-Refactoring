package snippet.utils;

import snippet.Main;

import javax.swing.*;
import java.util.Objects;

public enum Position {

    UP(1, new ImageIcon(Objects.requireNonNull(Main.class.getResource("/resources/random.png")))),
    RIGHT(2, new ImageIcon(Objects.requireNonNull(Main.class.getResource("/resources/random.png")))),
    DOWN(3, new ImageIcon(Objects.requireNonNull(Main.class.getResource("/resources/random.png")))),
    LEFT(4, new ImageIcon(Objects.requireNonNull(Main.class.getResource("/resources/random.png"))));

    private final int direction;
    private final ImageIcon image;

    private Position(int direction, ImageIcon image) {
        this.direction = direction;
        this.image = image;
    }

    public int getDirection() {
        return this.direction;
    }

    public ImageIcon getImage() {
        return this.image;
    }

}
