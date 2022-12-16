package snippet;

public class Main {

    public static void main(String[] args) {
        int x = 100;
        int y = 100;
        int step = 25;
        int length_snake = 3;
        int delay = 100;

        Gameplay gameplay = new Gameplay(x, y, step, length_snake, delay);
        FrameGameplay fg = new FrameGameplay(gameplay);
        fg.init();
    }

}
