package sample;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.settings.GameSettings;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.w3c.dom.css.Rect;

import java.util.Map;

/**
 * @author Lucas Kugelberg
 * @version 0.1
 *
 */

public class Game2048 extends GameApplication {

    private final int CORNER_VALUE = 10;
    private Entity player;


    /**
     * Initial settings for the game, such as width, height, version and title.
     * @param settings
     */
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(360);
        settings.setHeight(600);
        settings.setTitle("2048 Game");
        settings.setVersion("0.1");
    }


    /**
     * Initialize the game itself
     */
    @Override
    protected void initGame(){
        initBackground();






    }

    @Override
    protected void initInput(){
        Input input = getInput();


        input.addAction(new UserAction("Move Right") {
            @Override
            protected void onAction() {
                getGameState().increment("highestScoreValue", +5);
            }
        }, KeyCode.D);

        input.addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() {
                getGameState().increment("highestScoreValue", +5);
            }
        }, KeyCode.A);

        input.addAction(new UserAction("Move Up") {
            @Override
            protected void onAction() {
                getGameState().increment("highestScoreValue", +5);
            }
        }, KeyCode.W);

        input.addAction(new UserAction("Move Down") {
            @Override
            protected void onAction() {
                getGameState().increment("highestScoreValue", +5);
            }
        }, KeyCode.S);


    }

    /**
     * Background initialize
     * I am using the original RGB color from the real game
     */
    protected void initBackground(){
        // For the background
        getGameScene().setBackgroundColor(Color.rgb(240, 240, 228));
    }


    /**
     * Initialize the user interface.
     */
    @Override
    protected void initUI(){

        /*
            Setup rectangles for the UI
         */
        Rectangle logoRect = new Rectangle(113, 113, Color.rgb(236, 193, 0));
        Rectangle currentScoreRect = new Rectangle(78, 60, Color.rgb(158,146,130));
        Rectangle highScoreRect = new Rectangle(78, 60, Color.rgb(158,146,130));
        Rectangle gameGridBgRect = new Rectangle(315, 315, Color.rgb(158,146, 130));
        logoRect.setArcHeight(CORNER_VALUE);
        logoRect.setArcWidth(CORNER_VALUE);
        currentScoreRect.setArcHeight(CORNER_VALUE);
        currentScoreRect.setArcWidth(CORNER_VALUE);
        highScoreRect.setArcHeight(CORNER_VALUE);
        highScoreRect.setArcWidth(CORNER_VALUE);
        gameGridBgRect.setArcHeight(CORNER_VALUE);
        gameGridBgRect.setArcWidth(CORNER_VALUE);

        /*
            Adding some text to the rectangles
         */
        Text logoText = new Text(27, 92, "2048");
        Text currentScoreText = new Text(165, 42, "SCORE");
        Text highScoreText = new Text(276,42, "BEST");

        logoText.setFill(Color.WHITE);
        logoText.setStyle("-fx-font: 38px bold; -fx-font-family: 'Arial Rounded MT Bold'");
        currentScoreText.setFill(Color.rgb(225,225,225));
        currentScoreText.setStyle("-fx-font: 15px bold; -fx-font-family: 'Arial Rounded MT Bold'");
        highScoreText.setFill(Color.rgb(225,225,225));
        highScoreText.setStyle("-fx-font: 15px bold; -fx-font-family: 'Arial Rounded MT Bold'");

        // Adding current score value text to UI
        Text currentScoreValueText = new Text();
        currentScoreValueText.setTranslateX(165);
        currentScoreValueText.setTranslateY(68);
        currentScoreValueText.textProperty().bind(getGameState().intProperty("currentScoreValue").asString());
        currentScoreValueText.setFill(Color.WHITE);
        currentScoreValueText.setStyle("-fx-font: 20px bold; -fx-font-family: 'Arial Rounded MT Bold'");

        // Adding highest score value text to UI
        Text highestScoreValueText = new Text();
        highestScoreValueText.setTranslateX(276);
        highestScoreValueText.setTranslateY(68);
        highestScoreValueText.textProperty().bind(getGameState().intProperty("highestScoreValue").asString());
        highestScoreValueText.setFill(Color.WHITE);
        highestScoreValueText.setStyle("-fx-font: 20px bold; -fx-font-family: 'Arial Rounded MT Bold'");




        /*
            Add the text to the view
         */
        getGameScene().addUINode(logoText);
        getGameScene().addUINode(currentScoreText);
        getGameScene().addUINode(highScoreText);
        getGameScene().addUINode(currentScoreValueText);
        getGameScene().addUINode(highestScoreValueText);




        Entity logo = Entities.builder().at(15,25).viewFromNode(logoRect).buildAndAttach(getGameWorld());
        Entity score = Entities.builder().at(153,25).viewFromNode(highScoreRect).buildAndAttach(getGameWorld());
        score = Entities.builder().at(256, 25).viewFromNode(currentScoreRect).buildAndAttach(getGameWorld());
        Entity gameGridBg = Entities.builder().at(22, 218).viewFromNode(gameGridBgRect).buildAndAttach(getGameWorld());


    }
    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("currentScoreValue", 0);
        vars.put("highestScoreValue", 0);
    }









    public static void main(String[] args) {
        launch(args);
    }
}