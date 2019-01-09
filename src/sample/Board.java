package sample;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static sample.Game2048.CORNER_VALUE;

public class Board {

    protected static GridPane grid = new GridPane();
    protected static Rectangle bg;

    public Board(){

        // Initialize Background
        bg = new Rectangle(315, 315, Color.rgb(158,146, 130));
        bg.setArcHeight(CORNER_VALUE);
        bg.setArcWidth(CORNER_VALUE);
        Entity gameGridBg = Entities.builder().at(22, 218).viewFromNode(bg).buildAndAttach(FXGL.getApp().getGameWorld());

        // Construct empty tiles
        // Padding around the grid
        grid.setPadding(new Insets(6,6,6,6));
        grid.setHgap(6);
        grid.setVgap(6);

        /*
          This nested for loop is filling out all the rows in the columns first, then goes to second column and fills out all of the rows.
          I value is for the X
          J value is for the Y
         */
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                grid.add(new Tile().emptyTile(),i,j);
            }
        }

        Entity entireGrid = Entities.builder().at(22,218).viewFromNode(grid).buildAndAttach(FXGL.getApp().getGameWorld());


    }
}
