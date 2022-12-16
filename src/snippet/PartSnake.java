package snippet;

import snippet.utils.Directions;

import java.awt.Point;
import java.util.Objects;

public class PartSnake {
    private Point coord;
    private Directions actualDirection;

    public PartSnake(Point coord, Directions snake_dir) {
        this.coord = coord;
        this.actualDirection = snake_dir;
    }

    public Point getCoord() {
        return this.coord;
    }

    public Directions getActualDirection() {
        return actualDirection;
    }

    public void setActualDirection(Directions actualDirection) {
        this.actualDirection = actualDirection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PartSnake partSnake = (PartSnake) o;
        return Objects.equals(coord, partSnake.coord) && actualDirection == partSnake.actualDirection;
    }

    @Override
    public int hashCode() {
        return Objects.hash(coord, actualDirection);
    }

    @Override
    public String toString() {
        return "PartSnake{" +
                "coord=" + coord +
                ", snake_dir=" + actualDirection +
                '}';
    }
}