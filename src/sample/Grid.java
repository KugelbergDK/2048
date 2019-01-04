package sample;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


import static sample.Game2048.CORNER_VALUE;

public class Grid extends GridPane {

    protected Rectangle gameGridBgRect;
    protected GridPane grid;




    public Grid(){

        initGridBg();
        initEmptyTiles();
    }

    public void initGridBg(){
        // Initialize standard grid Background
        gameGridBgRect = new Rectangle(315, 315, Color.rgb(158,146, 130));
        gameGridBgRect.setArcHeight(CORNER_VALUE);
        gameGridBgRect.setArcWidth(CORNER_VALUE);
        Entity gameGridBg = Entities.builder().at(22, 218).viewFromNode(gameGridBgRect).buildAndAttach(FXGL.getApp().getGameWorld());
    }

    public void initEmptyTiles(){
        GridPane grid = new GridPane();
        grid.setMinSize(300,300);

        // Padding around the grid
        grid.setPadding(new Insets(6,6,6,6));
        grid.setHgap(6);
        grid.setVgap(6);

        /*
          This nested for-loop is filling out the first columns row, then goes the second column and fills out all of the rows etc..
          I value is for the X
          J value is for the Y
         */
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                grid.add(new Tile().tileBg(),i,j);
            }
        }

        Entity entireGrid = Entities.builder().at(22,218).viewFromNode(grid).buildAndAttach(FXGL.getApp().getGameWorld());


    }

}
