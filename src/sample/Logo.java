package sample;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import static sample.Game2048.CORNER_VALUE;

/**
 * This class is used to construct the Logo.
 * @author Lucas Kugelberg (Github: github.com/KugelbergDK))
 *
 *
 *
 */
public class Logo {

    Rectangle logoRect;

    /**
     * Create a new Rectangle node object with a given size and color.
     * Add som style to it and add it to the screen.
     */
    public Logo(){

        logoRect = new Rectangle(113, 113, Color.rgb(236, 193, 0));
        logoRect.setArcHeight(CORNER_VALUE);
        logoRect.setArcWidth(CORNER_VALUE);
        logoRect.setX(15);
        logoRect.setY(25);

        Text logoText = new Text(27, 92, "2048");
        logoText.setFill(Color.WHITE);
        logoText.setStyle("-fx-font: 38px bold; -fx-font-family: 'Arial Rounded MT Bold'");


        FXGL.getApp().getGameScene().addUINode(logoRect);
        FXGL.getApp().getGameScene().addUINode(logoText);


    }
}
