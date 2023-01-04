package snippet;

import javax.swing.*;
import java.awt.*;

public class FrameGameplay extends JFrame {
    private final Gameplay gameplay;
    public FrameGameplay(Gameplay gameplay) {
        this.gameplay = gameplay;
    }
    public void init() {
        this.setBounds(10,10,905,700);
        this.setBackground(Color.DARK_GRAY);
        this.setResizable(false);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.add(gameplay);
        this.setVisible(true);
    }


}
