package sample;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.settings.GameSettings;
import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.Map;

/**
 * @author Lucas Kugelberg
 * @version 0.1
 *
 */

public class Game2048 extends GameApplication {

    public final static int CORNER_VALUE = 10;
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


    protected void onUpdate(){
        System.out.println(getGameState().getInt("currentScoreValue"));
        if (getGameState().getInt("highestScoreValue")> 100){
            System.out.println("NY REKORD!");
        }

        if (getGameState().getInt("currentScoreValue") > getGameState().getInt("highestScoreValue")){

            getGameState().setValue("highestScoreValue", 1000);

        }
    }


    @Override
    protected void initInput(){
        Input input = getInput();


        input.addAction(new UserAction("Move Up") {
            @Override
            protected void onAction() {
                getGameState().increment("currentScoreValue", +5);

            }
        }, KeyCode.W);

        input.addAction(new UserAction("Move Down") {
            @Override
            protected void onAction() {
                getGameState().increment("currentScoreValue", -5);
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

        // Init logo
        Logo logo = new Logo();

        // Create new Score object
        Score score = new Score();
        // Initialize the currentScore
        score.initCurrentScore();
        score.initHighScore();

        new Grid();


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

        Entity gridPane = Entities.builder().at(22,218).viewFromNode(grid).buildAndAttach(getGameWorld());



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