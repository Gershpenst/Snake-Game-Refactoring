package snippet;

import java.awt.*;

public class Food {
    private Point coordFood;

    public Food(Point coordFood) {
        this.coordFood = coordFood;
    }

    public boolean hasSnakeEatenFood(Snake snake) {
        return this.coordFood.equals(snake.getHead().getCoord());
    }

    public Point getCoordFood() {
        return coordFood;
    }

    public void setCoordFood(Point coordFood) {
        this.coordFood = coordFood;
    }
}
