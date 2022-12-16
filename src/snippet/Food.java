package snippet;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Food {
    private Point coordFood;

    public Food(Point coordFood) {
        this.coordFood = coordFood;
    }

    public Boolean hasSnakeEatenFood(Snake snake) {
        return this.coordFood.equals(snake.getHead().getCoord());
    }

    public Point getCoordFood() {
        return coordFood;
    }

    public void setCoordFood(Point coordFood) {
        this.coordFood = coordFood;
    }
}
