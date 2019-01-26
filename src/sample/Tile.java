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

/**
 *
 * <p>This class is about creating a tile with XY values<br>
 * Besides that, this is where it converts the grid XY values to scene (UI) grid XY. So when i want a tile at 0,0, it will place the tile correctly at the right ui coordinates.
 * </p>
 *
 * @author Lucas Kugelberg (Github: github.com/KugelbergDK)
 *
 *
 *
 *
 *
 */

public class Tile {

    /**
     * A Rectangle node for the empty tiles
     */
    protected Rectangle tileBoxRectBg;
    /**
     * A TileValue object. Each tile has its own TileValue object.
     */
    protected TileValue tv = new TileValue();
    /**
     * The game X value.
     */
    protected int x;
    /**
     * The game Y value.
     */
    protected int y;
    /**
     * A boolean if the tile is empty.
     */
    protected boolean isEmpty;


    /**
     * The x,y constructor, mostly used when initializing a blank tile
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
        this.x = x;
        this.y = y;
        this.isEmpty = false;

        tv = new TileValue();
        tv.setValue(newValue);
        System.out.println("New Tile created with values: " + this.toString());

    }

    /**
     * Converting game XY to User Interface XY value.
     *
     * @return A visible Entity tile on the gameboard.
     */
    public Entity spawnTile(){
        int x = getUICoordinates()[0];
        int y = getUICoordinates()[1];

        return Entities.builder()
                .at(x, y)
                .viewFromNode(createTile())
                .buildAndAttach();
    }

    /**
     *
     * @return A stackpane, where it contains a colorful background and text corresponding to the tiles value.
     */
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


    /**
     *
     * @return A Rectangle there is empty - just a blank rectangle.
     */
    public Node emptyTile(){
        this.tileBoxRectBg = new Rectangle(71, 71, Color.rgb(178,165, 149));
        this.tileBoxRectBg.setArcHeight(Game2048.CORNER_VALUE);
        this.tileBoxRectBg.setArcWidth(Game2048.CORNER_VALUE);
        return this.tileBoxRectBg;

    }

    /**
     * This method converts the Tile's XY values to the User Interface XY.
     * Lets say we want to spawn a tile at 0,0 (On the game board). Without this method, it will be spawned at the top corner, that not what we want.
     *
     * When i designed the prototype in AdobeXD, I took the UI coordinates from there and inserted to the belonged tiles.
     *
     *
     * @return XY coordinates to this tile. Index 0 is X-value, Index 1 is Y-value.
     */
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

    /**
     *
     * @return A string where it outputs this tile's values.
     */
    public String toString(){
        return "[X = " + this.x + ", Y = " + this.y +", VALUE = " + this.tv.value + "]";
    }


    /**
     *
     * @return true if this tile is empty, false if it's not empty
     */
    public boolean isEmpty() {
        return isEmpty;
    }

    /**
     *
     * @param empty Set true if tile is empty, false if it is not.
     */
    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    /**
     *
     * @return This tile's TileValue Object
     */
    public TileValue getTv() {
        return tv;
    }

    /**
     *
     * @param tv TileValue object.
     */
    public void setTv(TileValue tv) {
        this.tv = tv;
    }

    /**
     *
     * @return This X-Position
     */
    public int getXPos() {
        return x;
    }

    /**
     *
     * @param x Set new X-Position
     */
    public void setXPos(int x) {
        this.x = x;
    }

    /**
     *
     * @return This Y-Position
     */
    public int getYPos() {
        return y;
    }

    /**
     *
     * @param y Set new Y-Position
     */
    public void setYPos(int y) {
        this.y = y;
    }

}
