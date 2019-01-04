package sample;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile {

    protected Rectangle tileBoxRectBg;
    protected int value;
    protected int x;
    protected int y;



    public Node tileBg(){
        this.tileBoxRectBg = new Rectangle(71, 71, Color.rgb(178,165, 149));
        this.tileBoxRectBg.setArcHeight(Game2048.CORNER_VALUE);
        this.tileBoxRectBg.setArcWidth(Game2048.CORNER_VALUE);
        return this.tileBoxRectBg;

    }

    public Rectangle getTileBoxRectBg() {
        return tileBoxRectBg;
    }

    public void setTileBoxRectBg(Rectangle tileBoxRectBg) {
        this.tileBoxRectBg = tileBoxRectBg;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
