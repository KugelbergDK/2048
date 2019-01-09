package sample;

import javafx.scene.paint.Color;

import java.util.HashMap;


/**
 * This class holds the value, the valueColor for a tile
 */
public class TileValue{


    // Index 0: background color | Index 1: Font color
    protected static Color tileColors[][] = {
            {Color.rgb(238,228,218), Color.rgb(119,110,101)},   // Tile with value 2
            {Color.rgb(236,224,200), Color.rgb(119,110,101)},   // Tile with value 4
            {Color.rgb(242,177,121), Color.rgb(255,255,255)},   // Tile with value 8
            {Color.rgb(236,141,83), Color.rgb(255,255,255)},   // Tile with value 16
            {Color.rgb(245,124,95), Color.rgb(255,255,255)},   // Tile with value 32
            {Color.rgb(233,89,55), Color.rgb(255,255,255)},   // Tile with value 64
            {Color.rgb(243,217,107), Color.rgb(255,255,255)},   // Tile with value 128
            {Color.rgb(241,208,75), Color.rgb(255,255,255)},   // Tile with value 256
    };
    protected int value;
    protected int fontSize;

    public TileValue(){

    }


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    /**
     *
     * @return a Color list, where index 0 is the background and index 1 is font color
     */
    public Color[] getColor(){

        // Convert tileValue.value to Color list index so it matchup.
        // v for value and i for index
        HashMap<Integer, Integer> valueToColorIndex = new HashMap<>();
        for (int v = 2, i = 0; v <= 2048; v*=2, i++) {
            valueToColorIndex.put(v, i);
        }

        // Here i can return which index the colors are listed by getting the tile value, for an example, if i want to return the bg and font color with the tile value of 2, then i will get index at 0.
        return tileColors[valueToColorIndex.get(this.getValue())];

    }


    /**
     *
     * @return The font size for the defined value.
     */
    public int getFontSize() {
        HashMap<Integer, Integer> fontSize = new HashMap<>();

        fontSize.put(2,45);
        fontSize.put(4,45);
        fontSize.put(8,45);
        fontSize.put(16,45);
        fontSize.put(32,45);
        fontSize.put(64,45);
        fontSize.put(128,38);
        fontSize.put(256,38);
        fontSize.put(512,38);
        fontSize.put(1024,28);
        fontSize.put(2048,28);

        return fontSize.get(this.value);


    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }
}
