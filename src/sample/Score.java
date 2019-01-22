package sample;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import static sample.Game2048.CORNER_VALUE;

public class Score {

    protected int currentScore;
    protected int highScore;
    protected Rectangle currentScoreRect;
    protected Rectangle highScoreRect;


    public void initCurrentScore(){
        currentScoreRect = new Rectangle(78, 60, Color.rgb(158,146,130));
        currentScoreRect.setArcHeight(CORNER_VALUE);
        currentScoreRect.setArcWidth(CORNER_VALUE);

        Text currentScoreText = new Text(165, 42, "SCORE");
        currentScoreText.setFill(Color.rgb(225,225,225));
        currentScoreText.setStyle("-fx-font: 15px bold; -fx-font-family: 'Arial Rounded MT Bold'");

        // Adding current score value text to UI
        Text currentScoreValueText = new Text();
        currentScoreValueText.setTranslateX(165);
        currentScoreValueText.setTranslateY(68);
        currentScoreValueText.textProperty().bind(FXGL.getApp().getGameState().intProperty("currentScoreValue").asString());
        currentScoreValueText.setFill(Color.WHITE);
        currentScoreValueText.setStyle("-fx-font: 20px bold; -fx-font-family: 'Arial Rounded MT Bold'");

        FXGL.getApp().getGameScene().addUINode(currentScoreText);
        FXGL.getApp().getGameScene().addUINode(currentScoreValueText);
        Entity score = Entities.builder().at(256, 25).viewFromNode(currentScoreRect).buildAndAttach(FXGL.getApp().getGameWorld());
    }

    public void initHighScore(){
        highScoreRect = new Rectangle(78, 60, Color.rgb(158,146,130));
        highScoreRect.setArcHeight(CORNER_VALUE);
        highScoreRect.setArcWidth(CORNER_VALUE);

        Text highScoreText = new Text(276,42, "BEST");
        highScoreText.setFill(Color.rgb(225,225,225));
        highScoreText.setStyle("-fx-font: 15px bold; -fx-font-family: 'Arial Rounded MT Bold'");

        // Adding highest score value text to UI
        Text highestScoreValueText = new Text();
        highestScoreValueText.setTranslateX(276);
        highestScoreValueText.setTranslateY(68);
        highestScoreValueText.textProperty().bind(FXGL.getApp().getGameState().intProperty("highestScoreValue").asString());
        highestScoreValueText.setFill(Color.WHITE);
        highestScoreValueText.setStyle("-fx-font: 20px bold; -fx-font-family: 'Arial Rounded MT Bold'");

        FXGL.getApp().getGameScene().addUINode(highScoreText);
        FXGL.getApp().getGameScene().addUINode(highestScoreValueText);
        Entity score = Entities.builder().at(153,25).viewFromNode(highScoreRect).buildAndAttach(FXGL.getApp().getGameWorld());



    }


    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
        if (this.currentScore > highScore){
            this.highScore = this.currentScore;
        }
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }
}
