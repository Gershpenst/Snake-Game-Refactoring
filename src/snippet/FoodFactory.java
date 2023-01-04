package snippet;

import java.awt.Point;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class FoodFactory {

    private static final int STEP = 25;
    private static final List<Point> foodsPoint = initFoodPos(new Point(25, 75), new Point(850,625));
    private static final Random random = new Random();
    private static final Food food = new Food(generateNextFood());

    private FoodFactory() {}

    public static Food createFood() {
        Point generateNextPositionFood = generateNextFood();
        food.setCoordFood(generateNextPositionFood);
        return food;
    }

    private static List<Point> initFoodPos(Point min, Point max) {
        List<Point> foodsPos = new ArrayList<>();
        for(int x = min.x; x <= max.x; x+=STEP) {
            for(int y = min.y; y <= max.y; y+=STEP) {
                foodsPos.add(new Point(x, y));
            }
        }
        return foodsPos;
    }

    public static Point generateNextFood() {
        return foodsPoint.get(random.nextInt(foodsPoint.size()));
    }
}
