package snippet;

import snippet.utils.ImageEntity;
import snippet.utils.Directions;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;
import java.util.Objects;
import javax.swing.*;

public class Gameplay extends JPanel implements KeyListener, ActionListener
{
    public static final int LIMIT_WIDTH_BOARD = 850;
    private static final int LIMIT_HEIGHT_BOARD = 625;

    private final transient Snake snake;
    private final int resetLengthOfSnake;
    private transient Food food;

    private final Map<String, ImageIcon> images = Map.ofEntries(
            Map.entry(ImageEntity.SNAKE.name(), new ImageIcon(Objects.requireNonNull(Main.class.getResource("/resources/snake.png")))),
            Map.entry(ImageEntity.APPLE.name(), new ImageIcon(Objects.requireNonNull(Main.class.getResource("/resources/apple.png")))),
            Map.entry(ImageEntity.RANDOM.name(), new ImageIcon(Objects.requireNonNull(Main.class.getResource("/resources/random.png"))))
    );

    private Timer timer = null;
    private final int delay;
    private int score = 0;

    Font fontArial = new Font("arial",Font.PLAIN, 14);
    Font fontGameOver = new Font("Times",Font.BOLD, 50);
    Font fontRestartGame = new Font("Times",Font.BOLD, 20);

    private final Point initSnake;

    private boolean isGameOver = false;

    public Gameplay(int x, int y, int step, int lengthSnake, int delay) {
        this.initSnake = new Point(x, y);

        this.snake = new Snake(step);
        this.resetLengthOfSnake = lengthSnake;

        this.food = FoodFactory.createFood();

        this.delay = delay;
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        this.resetGame();
        this.startGame();
    }

    public void resetGame() {
        this.snake.initSnake(this.initSnake, this.resetLengthOfSnake, Directions.RIGHT);
        score = 0;
        isGameOver = false;
    }

    public void startGame() {
        if(this.timer == null) {
            timer = new Timer(this.delay, this);
        }
        if(this.timer.isRunning()){
            return;
        }
        this.timer.start();
    }

    public void stopGame() {
        if(this.timer.isRunning()) {
            this.timer.stop();
        }
    }

    public Boolean findIfGameOver() {
        Point headCoord = this.snake.getHead().getCoord();
        for(int b = 1; b<this.snake.getSize(); b++) {
            if(!this.snake.getBody(b).getCoord().equals(headCoord)) continue;
            return true;
        }
        return false;
    }

    @Override
    public void repaint() {
        // MUST HAVE when using a Mac :)
        paint(this.getGraphics());
    }

    public void drawHeaderGameplay(Graphics g) {
        //draw title image border
        g.setColor(Color.WHITE);
        g.drawRect(24, 10, 851, 51);

        //draw the title image
        this.images.get(ImageEntity.RANDOM.name()).paintIcon(this, g, this.snake.getStep(), 11);

        //draw scores
        g.clearRect(780, 20, 75, 35);
        g.setColor(Color.WHITE);
        g.setFont(fontArial);
        g.drawString("Scores: "+score, 780, 30);

        g.setColor(Color.WHITE);
        g.setFont(fontArial);
        g.drawString("Length: "+this.snake.getSize(), 780, 50);
    }

    public void drawBoard(Graphics g) {
        //draw border for gameplay
        g.setColor(Color.WHITE);
        g.drawRect(24, 74, 851, 577);

        //draw background for the gameplay
        g.setColor(Color.black);
        g.fillRect(this.snake.getStep(), 75, LIMIT_WIDTH_BOARD, 575);
    }

    public void drawSnake(Graphics g) {
        // draw snake
        for(int a = 0; a < this.snake.getSize(); a++) {
            this.images.get(ImageEntity.SNAKE.name()).paintIcon(this, g, this.snake.getBody(a).getCoord().x, this.snake.getBody(a).getCoord().y);
        }
    }

    public void drawFood(Graphics g) {
        this.images.get(ImageEntity.APPLE.name()).paintIcon(this, g, this.food.getCoordFood().x, this.food.getCoordFood().y);
    }

    public void drawEndGame(Graphics g) {
        if(isGameOver) {
            g.setColor(Color.WHITE);
            g.setFont(fontGameOver);
            g.drawString("GAME OVER", 300, 300);

            g.setFont(fontRestartGame);
            g.drawString("Press Space to Restart", 350, 340);
            this.stopGame();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        this.drawHeaderGameplay(g);
        this.drawBoard(g);
        this.drawSnake(g);
        this.drawFood(g);
        this.drawEndGame(g);
        g.dispose();
    }

    @Override
    public void keyTyped(KeyEvent ke) {}

    @Override
    public void keyReleased(KeyEvent ke) {}

    @Override
    public void keyPressed(KeyEvent e)
    {
        // Move the snake !
        // Cannot go the opposite way when moving
        Directions positionHead = this.snake.getHead().getActualDirection();
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT ->
                    this.snake.setNextDirectionFromUserInput(positionHead.equals(Directions.LEFT) ? this.snake.getNextDirectionFromUserInput() : Directions.RIGHT);
            case KeyEvent.VK_LEFT ->
                    this.snake.setNextDirectionFromUserInput(positionHead.equals(Directions.RIGHT) ? this.snake.getNextDirectionFromUserInput() : Directions.LEFT);
            case KeyEvent.VK_UP ->
                    this.snake.setNextDirectionFromUserInput(positionHead.equals(Directions.DOWN) ? this.snake.getNextDirectionFromUserInput() : Directions.UP);
            case KeyEvent.VK_DOWN ->
                    this.snake.setNextDirectionFromUserInput(positionHead.equals(Directions.UP) ? this.snake.getNextDirectionFromUserInput() : Directions.DOWN);
            case KeyEvent.VK_SPACE -> {
                this.resetGame();
                this.startGame();
                repaint();
            }
            case KeyEvent.VK_ESCAPE -> System.exit(0);
            default -> {}
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Teleportation Snake when touching borders
        this.snake.moveSnakeWithCollision(LIMIT_WIDTH_BOARD, LIMIT_HEIGHT_BOARD);

        // Does the snake has eaten food ?
        if(this.food.hasSnakeEatenFood(this.snake)) {
            score++;
            this.food = FoodFactory.createFood();
            this.snake.addTail();
        }

        // Game over when snake has eaten itself
        this.isGameOver = this.findIfGameOver();
        repaint();
    }
}