package sample;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.settings.GameSettings;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;



/**
 * @author Lucas Kugelberg
 * @version 0.1
 *
 */

public class Game2048 extends GameApplication {

    public final static int CORNER_VALUE = 10;
    private Entity startGameText;
    public static ArrayList<Tile> tiles = new ArrayList<>();




    public enum Type {
        TILE, NEWTILE;
    }
    public Entity tile;



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


    }



    protected Entity spawnNew(){

        int randomX = ThreadLocalRandom.current().nextInt(0,3+1);
        int randomY = ThreadLocalRandom.current().nextInt(0,3+1);

        if (tiles.size() == 16){
            System.out.println("All tiles are filled.");
            return null;
        }
        // If randoms equals a tiles location, generate new randoms and start over until random finds a available spot in the grid.
        for (int i = 0; i < tiles.size(); i++) {

            if ((tiles.get(i).getX() == randomX) && (tiles.get(i).getY() == randomY)){
                // Generate new randoms and start over
                System.out.println("\n\nTHEY ARE THE SAME!");
                System.out.println("randomX = " + randomX);
                System.out.println("randomY = " + randomY);
                System.out.println("tiles X = " + tiles.get(i).getX());
                System.out.println("tiles Y = " + tiles.get(i).getY());

                randomX = ThreadLocalRandom.current().nextInt(0,3+1);
                randomY = ThreadLocalRandom.current().nextInt(0,3+1);
                // start over - Minus 1 is because, when loop starts over, it start by adding 1 to it, then (i) will be 0.
                i = -1;
            }

        }

        Tile tile = new Tile(randomX,randomY, Math.random() < 0.9 ? 2 : 4);
        System.out.println("Created new tile at X=" + randomX + ", Y=" + randomY);
        int tileX = tile.getUICoordinates()[0];
        int tileY = tile.getUICoordinates()[1];

        this.tile = Entities.builder().at(tileX, tileY).viewFromNode(tile.createTile()).buildAndAttach(getGameWorld());
        return this.tile;

    }

    protected Entity spawnTile(Tile tile){

        int tileX = tile.getUICoordinates()[0];
        int tileY = tile.getUICoordinates()[1];
        return Entities.builder().at(tileX, tileY).viewFromNode(tile.createTile()).buildAndAttach(getGameWorld());

    }



    protected void info(){


        System.out.println("\nLængden af tiles: " + tiles.size());


/*
        Tile tile = new Tile(2,0,2);
        int tileX = tile.getUICoordinates()[0];
        int tileY = tile.getUICoordinates()[1];
        this.tile = Entities.builder().at(tileX, tileY).viewFromNode(tile.createTile()).buildAndAttach(getGameWorld());
*/
        // MOVING!!!!!
        //this.tile.setPosition(200,200);

        // TODO: FIX THIS
        for (Tile entiTile : tiles){
            System.out.println("\nX = " + entiTile.getXPos());
            System.out.println("Y = " + entiTile.getYPos());
            System.out.println("\n");

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



        UserAction info = new UserAction("info") {
            @Override
            protected void onActionBegin() {
                super.onActionBegin();
                info();




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
        input.addAction(info,KeyCode.I);


        UserAction startGame = new UserAction("startgame") {
            @Override
            protected void onActionBegin() {
                super.onActionBegin();
                // tiles.clear();
                // Remove start text
                startGameText.removeFromWorld();
                // Spawn first 2 tiles
                spawnNew();

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
        input.addAction(startGame, KeyCode.SPACE);



        UserAction random = new UserAction("random") {
            @Override
            protected void onActionBegin() {
                // Empty the arraylist for old tiles
                tiles.clear();

                int[] availInts = {2,4,8,16,32,64,128,256};
                super.onActionBegin();
                System.out.println("Begin");
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        Random rnd = new Random();
                        int delayIndex = rnd.nextInt(availInts.length);
                        Tile tile = new Tile(i,j,availInts[delayIndex]);
                        spawnTile(tile);
                    }
                }

            }

            @Override
            protected void onAction() {
                super.onAction();
            }

            @Override
            protected void onActionEnd() {
                super.onActionEnd();

                for (int i = 1; i < tiles.size(); i++) {

                    if (tiles.get(i-1).getX() < tiles.get(i).getX()){
                        System.out.println("test");

                    }

                }




            }
        };

        input.addAction(random, KeyCode.R);

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

        Text welcomeText = new Text("Press \"space\" to start the game!");
        welcomeText.setFill(Color.web("#6E6E64"));
        welcomeText.setStyle("-fx-font: 16px bold; -fx-font-family: 'Arial Rounded MT Bold'");
        startGameText = Entities.builder().at(15,172).viewFromNode(welcomeText).buildAndAttach(getGameWorld());




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