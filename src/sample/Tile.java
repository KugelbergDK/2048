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

import static sample.Game2048.CORNER_VALUE;

public class Tile extends Component{

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
        Color bg = tv.getColor()[0];
        Color font = tv.getColor()[1];
        System.out.println(bg);

        this.x = x;
        this.y = y;
        this.isEmpty = false;

    }

    public Node createTile(){
        Rectangle tile = new Rectangle(71,71, Color.rgb(238,228,218));
        Text nummer = new Text("2");

        StackPane stack = new StackPane();
        stack.getChildren().addAll(tile,nummer);

        Entity entireTile = Entities.builder().at(15,218).viewFromNode(stack).buildAndAttach(FXGL.getApp().getGameWorld());
        return stack;
    }



    public Node emptyTile(){
        this.tileBoxRectBg = new Rectangle(71, 71, Color.rgb(178,165, 149));
        this.tileBoxRectBg.setArcHeight(Game2048.CORNER_VALUE);
        this.tileBoxRectBg.setArcWidth(Game2048.CORNER_VALUE);
        return this.tileBoxRectBg;

    }

    public Node tileTest(){
        this.tileBoxRectBg = new Rectangle(71, 71, Color.rgb(255,154, 149));
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


    public TileValue getTv() {
        return tv;
    }

    public void setTv(TileValue tv) {
        this.tv = tv;
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
