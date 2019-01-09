package sample;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.settings.GameSettings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lucas Kugelberg
 * @version 0.1
 *
 */

public class Game2048 extends GameApplication {

    public final static int CORNER_VALUE = 10;
    private Entity tile = new Entity();
    ArrayList<Tile> tiles = new ArrayList<>();



    /**
     * Initial settings for the game, such as width, height, version and title.
     * @param settings the FXGL game setting
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



        /* CODE EXAMPLE
        tiles.add(new Tile(0,0,2));
        tiles.add(new Tile(1,0,4));
        tiles.add(new Tile(2,0,8));

        for (Tile tile : tiles){
            if (tile.getX() == 0){
                System.out.println("Fandt en på x 0");
            }
        }
        */


        tile = Entities.builder().at(22+6,218+6).viewFromNode(new Tile(0,0,2).createTile()).buildAndAttach(getGameWorld());
        tile = Entities.builder().at(176+6,218+6).viewFromNode(new Tile(0,0,256).createTile()).buildAndAttach(getGameWorld());
        Tile test = new Tile(0,0,2);
        spawnTile(test);








    }

    protected Entity spawnTile(Tile tile){
        // Add tile to arraylist
        tiles.add(tile);
        int tileX = tile.getUICoordinates()[0];
        int tileY = tile.getUICoordinates()[1];
        System.out.println("X: "+ tileX);
        return Entities.builder().at(tileX, tileY).viewFromNode(tile.createTile()).buildAndAttach(getGameWorld());
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

        input.addAction(new UserAction("Move Right") {
            @Override
            protected void onAction() {
                getGameState().increment("currentScoreValue", +5);

            }
        }, KeyCode.D);

        input.addAction(new UserAction("Move Down") {
            @Override
            protected void onAction() {
                getGameState().increment("currentScoreValue", -5);
            }
        }, KeyCode.S);

        input.addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() {
                getGameState().increment("currentScoreValue", -5);
            }
        }, KeyCode.A);




        UserAction reset = new UserAction("reset") {
            @Override
            protected void onActionBegin() {
                super.onActionBegin();
                System.out.println("Begin");
                Tile tile = new Tile(1,3,128);
                Tile tile2 = new Tile(3,2,32);
                spawnTile(tile);
                spawnTile(tile2);

            }

            @Override
            protected void onAction() {
                super.onAction();
            }

            @Override
            protected void onActionEnd() {
                super.onActionEnd();
            }
        };

        input.addAction(reset, KeyCode.R);






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

        // Init logo
        Logo logo = new Logo();

        // Create new Score object
        Score score = new Score();
        // Initialize the currentScore
        score.initCurrentScore();
        score.initHighScore();

        // Init board
        Board board = new Board();




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