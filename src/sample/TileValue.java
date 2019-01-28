package sample;

import javafx.scene.paint.Color;

import java.util.HashMap;


/**
 * <p>This class primarily contains the UI stuff to a tile. Like the background and font color and the font size.</p>
 * @author Lucas Kugelberg (Github: github.com/KugelbergDK)
 *
 *
 */
public class TileValue{

    /**
     * This is a static Color list.
     * Index 0 is background color, Index 1 is the font color
     *
     * They are sorted by value. Lowest first.
     */
    private static Color tileColors[][] = {
            {Color.rgb(238,228,218), Color.rgb(119,110,101)},   // TileComponent with value 2
            {Color.rgb(236,224,200), Color.rgb(119,110,101)},   // TileComponent with value 4
            {Color.rgb(242,177,121), Color.rgb(255,255,255)},   // TileComponent with value 8
            {Color.rgb(236,141,83), Color.rgb(255,255,255)},    // TileComponent with value 16
            {Color.rgb(245,124,95), Color.rgb(255,255,255)},    // TileComponent with value 32
            {Color.rgb(233,89,55), Color.rgb(255,255,255)},     // TileComponent with value 64
            {Color.rgb(243,217,107), Color.rgb(255,255,255)},   // TileComponent with value 128
            {Color.rgb(241,208,75), Color.rgb(255,255,255)},    // TileComponent with value 256
            {Color.rgb(228,192,42), Color.rgb(255,255,255)},    // TileComponent with value 512
            {Color.rgb(227,186,20), Color.rgb(255,255,255)},   // TileComponent with value 1024
            {Color.rgb(236,196,0), Color.rgb(255,255,255)},   // TileComponent with value 2048
    };
    /**
     * This tile value. This is always multiplied by 2 when changed.
     */
    protected int value;
    /**
     * This tile fontsize. Fontsize is getting smaller when increasing value.
     */
    protected int fontSize;


    /**
     *
     * @return This objects value.
     */
    public int getValue() {
        return value;
    }

    /**
     *
     * @param value Set a new value for this object. New should always be multiplied by 2.
     */
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
     * Fontsize is contained within a Hashmap, where the key is the tilevalue.
     * So by inserting the tiles value, we can return the correct fontsize for this tule.
     * @return A int to the font size.
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

}
