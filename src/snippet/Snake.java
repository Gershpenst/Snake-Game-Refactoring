package snippet;

import snippet.utils.Directions;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Snake {
    private Directions nextDirectionFromUserInput;
    private final List<PartSnake> snakeParts = new ArrayList<>();
    private final int step;
    public Snake(int step) {
        this.step = step;
    }

    public void initSnake(Point positionSnake, int length, Directions dir) {
        this.nextDirectionFromUserInput = dir;
        this.snakeParts.clear();
        for(int i = 0; i < length; i++) {
            addPartSnake(new Point(positionSnake.x - i * this.step, positionSnake.y), dir);
        }
    }

    public void addPartSnake(Point coord, Directions dir) {
        snakeParts.add(new PartSnake(coord, dir));
    }

    public PartSnake getBody(int idx) {
        return this.snakeParts.get(idx);
    }

    public PartSnake getHead() {
        return this.snakeParts.get(0);
    }

    public PartSnake getTail() {
        return this.snakeParts.get(this.getSize() - 1);
    }

    public int getSize() {
        return this.snakeParts.size();
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

    private record StepLogicSnake(int step, boolean isRightLeft, boolean isUpDown) {}
    private StepLogicSnake stepSnake(Directions nextPos) {
        int stepDirection = Directions.DOWN.equals(nextPos) || Directions.RIGHT.equals(nextPos) ? this.step : -1 * this.step;
        boolean isRightLeft = Directions.RIGHT.equals(nextPos) || Directions.LEFT.equals(nextPos);
        boolean isUpDown = Directions.UP.equals(nextPos) || Directions.DOWN.equals(nextPos);
        return new StepLogicSnake(stepDirection, isRightLeft, isUpDown);
    }
    public void addTail() {
        Point snakeTail = this.getTail().getCoord();
        Directions posTail = this.getTail().getActualDirection();
        StepLogicSnake stepLogic = stepSnake(posTail);

        int snakeTailX = snakeTail.x;
        int snakeTailY = snakeTail.y;

        if(stepLogic.isRightLeft) {
            this.snakeParts.add(new PartSnake(new Point(snakeTailX - stepLogic.step, snakeTailY), posTail));
        } else if(stepLogic.isUpDown) {
            this.snakeParts.add(new PartSnake(new Point(snakeTailX, snakeTailY - stepLogic.step), posTail));
        }
    }

    public void moveSnakeWithCollision(int limitWidthBoardEnd, int limitHeightBoardEnd) {
        for (int parts = this.getSize() - 1; parts > 0; parts--) {
            this.dirSnakeProcess(this.getBody(parts), this.getBody(parts-1).getActualDirection(), limitWidthBoardEnd, limitHeightBoardEnd);
        }
        dirSnakeProcess(this.getHead(), this.getNextDirectionFromUserInput(), limitWidthBoardEnd, limitHeightBoardEnd);
    }

    private void dirSnakeProcess(PartSnake partSnake, Directions nextPos, int limitWidthBoardEnd, int limitHeightBoardEnd) {
        StepLogicSnake stepLogic = stepSnake(nextPos);

        if(stepLogic.isRightLeft) {
            int posX = partSnake.getCoord().x;
            if (posX >= limitWidthBoardEnd && Directions.RIGHT.equals(nextPos)) {
                partSnake.getCoord().x = this.step;
            } else if (posX <= this.step && Directions.LEFT.equals(nextPos)) {
                partSnake.getCoord().x = limitWidthBoardEnd;
            } else {
                partSnake.getCoord().x = posX + stepLogic.step;
            }
        } else if(stepLogic.isUpDown) {
            int posY = partSnake.getCoord().y;
            if (posY <= 75 && Directions.UP.equals(nextPos)) {
                partSnake.getCoord().y = limitHeightBoardEnd;
            } else if (posY >= limitHeightBoardEnd && Directions.DOWN.equals(nextPos)) {
                partSnake.getCoord().y = 75;
            } else {
                partSnake.getCoord().y = posY + stepLogic.step;
            }
        }
        partSnake.setActualDirection(nextPos);
    }
}
