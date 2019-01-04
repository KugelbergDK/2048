package sample;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import static sample.Game2048.CORNER_VALUE;

public class Logo {

    Rectangle logoRect;

    // Construct logo
    public Logo(){

        logoRect = new Rectangle(113, 113, Color.rgb(236, 193, 0));
        logoRect.setArcHeight(CORNER_VALUE);
        logoRect.setArcWidth(CORNER_VALUE);

        Text logoText = new Text(27, 92, "2048");
        logoText.setFill(Color.WHITE);
        logoText.setStyle("-fx-font: 38px bold; -fx-font-family: 'Arial Rounded MT Bold'");


        FXGL.getApp().getGameScene().addUINode(logoText);
        Entity logo = Entities.builder().at(15,25).viewFromNode(logoRect).buildAndAttach(FXGL.getApp().getGameWorld());

    }
}
