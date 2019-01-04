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
    protected Entity gridPane;



    public Grid(){
        // Initialize standard grid Background
        gameGridBgRect = new Rectangle(315, 315, Color.rgb(158,146, 130));
        gameGridBgRect.setArcHeight(CORNER_VALUE);
        gameGridBgRect.setArcWidth(CORNER_VALUE);
        Entity gameGridBg = Entities.builder().at(22, 218).viewFromNode(gameGridBgRect).buildAndAttach(FXGL.getApp().getGameWorld());



    }

    public Grid(int width, int height, Color color){
        gameGridBgRect = new Rectangle(width, height, color);
        gameGridBgRect.setArcHeight(CORNER_VALUE);
        gameGridBgRect.setArcWidth(CORNER_VALUE);
        Entity gameGridBg = Entities.builder().at(22, 218).viewFromNode(gameGridBgRect).buildAndAttach(FXGL.getApp().getGameWorld());



    }

}
