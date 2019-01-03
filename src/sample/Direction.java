package sample;

/**
 * @author Lucas Kugelberg
 *
 * This enum dicticts which direction a tile goes.
 */

public enum Direction {

    UP(0,1), RIGHT(1,0), DOWN(0,-1), LEFT(-1,0);

    private final int x;
    private final int y;

    Direction(int x, int y){
        this.y = y;
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString(){
        return "Direction = " + name() + "[x = " + this.x + ", y =  " + this.y + "]";
    }
}
