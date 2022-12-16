package snippet;

import java.awt.Point;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class FoodFactory {
    private static final List<Point> foodsPoint = initFoodPos(25,850,75,625,25);
    private static final Random random = new Random();
    private static Food food = new Food(generateNextFood());

    public static Food createFood() {
        Point generateNextPositionFood = generateNextFood();
        food.setCoordFood(generateNextPositionFood);
        return food;
    }

    private static List<Point> initFoodPos(int min_x, int max_x, int min_y, int max_y, int step) {
        List<Point> foodsPos = new ArrayList<>();
        for(int x = min_x; x <= max_x; x+=step) {
            for(int y = min_y; y <= max_y; y+=step) {
                foodsPos.add(new Point(x, y));
            }
        }
        return foodsPos;
    }

    public static Point generateNextFood() {
        return foodsPoint.get(random.nextInt(foodsPoint.size()));
    }
}
