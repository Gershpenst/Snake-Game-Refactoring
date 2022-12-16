package snippet;

import snippet.utils.Directions;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Snake {
    private Directions nextDirectionFromUserInput;
    private final List<PartSnake> snake = new ArrayList<>();
    private final int step;
    public Snake(int step) {
        this.step = step;
    }

    public void initSnake(Point pos_snake, int length, Directions dir) {
        this.nextDirectionFromUserInput = dir;
        this.snake.clear();
        for(int i = 0; i < length; i++) {
            addPartSnake(new Point(pos_snake.x - i * this.step, pos_snake.y), dir);
        }
    }

    public void addPartSnake(Point coord, Directions dir) {
        snake.add(new PartSnake(coord, dir));
    }

    public PartSnake getBody(int idx) {
        return this.snake.get(idx);
    }

    public PartSnake getHead() {
        return this.snake.get(0);
    }

    public PartSnake getTail() {
        return this.snake.get(this.getSize() - 1);
    }

    public int getSize() {
        return this.snake.size();
    }

    public Directions getNextDirectionFromUserInput() {
        return nextDirectionFromUserInput;
    }

    public void setNextDirectionFromUserInput(Directions nextDirectionFromUserInput) {
        this.nextDirectionFromUserInput = nextDirectionFromUserInput;
    }

    public int getStep() {
        return step;
    }

    private record StepLogicSnake(Integer step, Boolean is_right_left, Boolean is_up_down) {}
    private StepLogicSnake stepSnake(Directions next_pos) {
        Integer step = Directions.DOWN.equals(next_pos) || Directions.RIGHT.equals(next_pos) ? this.step : -1 * this.step;
        Boolean is_right_left = Directions.RIGHT.equals(next_pos) || Directions.LEFT.equals(next_pos);
        Boolean is_up_down = Directions.UP.equals(next_pos) || Directions.DOWN.equals(next_pos);
        return new StepLogicSnake(step, is_right_left, is_up_down);
    }
    public void addTail() {
        Point snake_tail = this.getTail().getCoord();
        Directions pos_tail = this.getTail().getActualDirection();
        StepLogicSnake step_logic = stepSnake(pos_tail);

        int snake_tail_x = snake_tail.x;
        int snake_tail_y = snake_tail.y;

        if(step_logic.is_right_left) {
            this.snake.add(new PartSnake(new Point(snake_tail_x - step_logic.step, snake_tail_y), pos_tail));
        } else if(step_logic.is_up_down) {
            this.snake.add(new PartSnake(new Point(snake_tail_x, snake_tail_y - step_logic.step), pos_tail));
        }
    }

    public void moveSnakeWithCollision(int limit_width_board_end, int limit_height_board_end) {
        for (int parts = this.getSize() - 1; parts > 0; parts--) {
            this.dirSnakeProcess(this.getBody(parts), this.getBody(parts-1).getActualDirection(), limit_width_board_end, limit_height_board_end);
        }
        dirSnakeProcess(this.getHead(), this.getNextDirectionFromUserInput(), limit_width_board_end, limit_height_board_end);
    }

    private void dirSnakeProcess(PartSnake part_snake, Directions next_pos, int limit_width_board_end, int limit_height_board_end) {
        StepLogicSnake step_logic = stepSnake(next_pos);

        if(step_logic.is_right_left) {
            int pos_x = part_snake.getCoord().x;
            if (pos_x >= limit_width_board_end && Directions.RIGHT.equals(next_pos)) {
                part_snake.getCoord().x = this.step;
            } else if (pos_x <= this.step && Directions.LEFT.equals(next_pos)) {
                part_snake.getCoord().x = limit_width_board_end;
            } else {
                part_snake.getCoord().x = pos_x + step_logic.step;
            }
        } else if(step_logic.is_up_down) {
            int pos_y = part_snake.getCoord().y;
            if (pos_y <= 75 && Directions.UP.equals(next_pos)) {
                part_snake.getCoord().y = limit_height_board_end;
            } else if (pos_y >= limit_height_board_end && Directions.DOWN.equals(next_pos)) {
                part_snake.getCoord().y = 75;
            } else {
                part_snake.getCoord().y = pos_y + step_logic.step;
            }
        }
        part_snake.setActualDirection(next_pos);
    }
}
