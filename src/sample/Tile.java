package sample;

import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.PositionComponent;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;



public class Tile {

    private PositionComponent position;
    protected Rectangle tileBoxRectBg;
    protected TileValue tv = new TileValue();
    protected int x;
    protected int y;
    protected boolean isEmpty;


    /**
     * The non-arg constructor, mostly used when initializing a blank tile
     */
    public Tile(int x, int y){
        // Add tile to arraylist
        this.isEmpty = true;
        tv.setValue(0);



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
        System.out.println("New Tile created with value: " + tv.getValue());

        this.x = x;
        this.y = y;
        this.isEmpty = false;

    }

    public Entity spawnTile(){
        int x = getUICoordinates()[0];
        int y = getUICoordinates()[1];

        return Entities.builder()
                .at(x, y)
                .viewFromNode(createTile())
                .buildAndAttach();
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


        /*
            I have taken the X position value from my prototype design, but it seems not to fit perfectly, so therefore i have pulled 1 off from the X-value
         */
        // First row
        if (x == 0 && y == 0) uiCoordinates = new int[]{29-1, 224};
        if (x == 1 && y == 0) uiCoordinates = new int[]{106-1, 224};
        if (x == 2 && y == 0) uiCoordinates = new int[]{183-1, 224};
        if (x == 3 && y == 0) uiCoordinates = new int[]{260-1, 224};

        // Second row
        if (x == 0 && y == 1) uiCoordinates = new int[]{29-1, 301};
        if (x == 1 && y == 1) uiCoordinates = new int[]{106-1, 301};
        if (x == 2 && y == 1) uiCoordinates = new int[]{183-1, 301};
        if (x == 3 && y == 1) uiCoordinates = new int[]{260-1, 301};

        // Third row
        if (x == 0 && y == 2) uiCoordinates = new int[]{29-1, 378};
        if (x == 1 && y == 2) uiCoordinates = new int[]{106-1, 378};
        if (x == 2 && y == 2) uiCoordinates = new int[]{183-1, 378};
        if (x == 3 && y == 2) uiCoordinates = new int[]{260-1, 378};

        // Fourth row
        if (x == 0 && y == 3) uiCoordinates = new int[]{29-1, 455};
        if (x == 1 && y == 3) uiCoordinates = new int[]{106-1, 455};
        if (x == 2 && y == 3) uiCoordinates = new int[]{183-1, 455};
        if (x == 3 && y == 3) uiCoordinates = new int[]{260-1, 455};




        return uiCoordinates;

    }

    public String toString(){
        return "[X = " + this.x + ", Y = " + this.y +", VALUE = " + this.tv.value + "]";
    }


    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public TileValue getTv() {
        return tv;
    }

    public void setTv(TileValue tv) {
        this.tv = tv;
    }

    public int getXPos() {
        return x;
    }

    public void setXPos(int x) {
        this.x = x;
    }

    public int getYPos() {
        return y;
    }

    public void setYPos(int y) {
        this.y = y;
    }

}
