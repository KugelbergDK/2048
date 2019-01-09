package sample;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


public class Tile{

    protected Rectangle tileBoxRectBg;
    protected TileValue tv;
    protected int x;
    protected int y;
    protected boolean isEmpty;


    /**
     * The non-arg constructor, mostly used when initializing a blank tile
     */
    public Tile(){
        this.isEmpty = true;



    }

    /**
     * The positioning constructor, primarily used to make a new valuable tile, a tile with a number
     *
     * @param x The X coordinate for the grid
     * @param y The Y coordinate for the grid
     * @param newValue The new value for the object
     */
    public Tile(int x, int y, int newValue){

        tv = new TileValue();
        tv.setValue(newValue);
        System.out.println("A new tile has been created with a value of: " + tv.getValue());

        this.x = x;
        this.y = y;
        this.isEmpty = false;

    }

    public Node createTile(){

        // Get bg and font color from value
        Rectangle tile = new Rectangle(71,71, tv.getColor()[0]);
        tile.setArcWidth(Game2048.CORNER_VALUE);
        tile.setArcHeight(Game2048.CORNER_VALUE);

        Text number = new Text(String.valueOf(tv.getValue()));
        number.setFill(tv.getColor()[1]);
        number.setStyle("-fx-font: " + tv.getFontSize() +"px bold; -fx-font-family: 'Arial Rounded MT Bold'");
        number.setTranslateY(-2);

        StackPane stack = new StackPane();
        stack.getChildren().addAll(tile,number);


        return stack;
    }



    public Node emptyTile(){
        this.tileBoxRectBg = new Rectangle(71, 71, Color.rgb(178,165, 149));
        this.tileBoxRectBg.setArcHeight(Game2048.CORNER_VALUE);
        this.tileBoxRectBg.setArcWidth(Game2048.CORNER_VALUE);
        return this.tileBoxRectBg;

    }

    public int[] getUICoordinates(){

        int x = this.x;
        int y = this.y;
        int[] uiCoordinates = new int[2];

        // First row
        if (x == 0 && y == 0) uiCoordinates = new int[]{29, 224};
        if (x == 1 && y == 0) uiCoordinates = new int[]{106, 224};
        if (x == 2 && y == 0) uiCoordinates = new int[]{183, 224};
        if (x == 3 && y == 0) uiCoordinates = new int[]{260, 224};

        // Second row
        if (x == 0 && y == 1) uiCoordinates = new int[]{29, 301};
        if (x == 1 && y == 1) uiCoordinates = new int[]{106, 301};
        if (x == 2 && y == 1) uiCoordinates = new int[]{183, 301};
        if (x == 3 && y == 1) uiCoordinates = new int[]{260, 301};

        // Third row
        if (x == 0 && y == 2) uiCoordinates = new int[]{29, 378};
        if (x == 1 && y == 2) uiCoordinates = new int[]{106, 378};
        if (x == 2 && y == 2) uiCoordinates = new int[]{183, 378};
        if (x == 3 && y == 2) uiCoordinates = new int[]{260, 378};

        // Fourth row
        if (x == 0 && y == 3) uiCoordinates = new int[]{29, 455};
        if (x == 1 && y == 3) uiCoordinates = new int[]{106, 455};
        if (x == 2 && y == 3) uiCoordinates = new int[]{183, 455};
        if (x == 3 && y == 3) uiCoordinates = new int[]{260, 455};




        return uiCoordinates;

    }



    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
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
