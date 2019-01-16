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
    public Tile tile;
    public Entity tileEntity = new Entity();
    // Used for storing Entities, so i can move them etc.
    public ArrayList<Entity> tileEntities = new ArrayList<>();
    // Used for storing tiles, so i can update their values, then move them afterwards
    public ArrayList<Tile> tiles = new ArrayList<>();

    // Trying to add Entity and Tile objects to a hashmap, because a Entity holds a Tile
    HashMap<Entity, Tile> tileMap = new HashMap<>();



    public enum Type {
        TILE
    }



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

    protected void info(){

        ArrayList<Integer> possibleTileValues = new ArrayList<>();
        for (int i = 2; i <= 256 ; i*=2) {
            possibleTileValues.add(i);
        }

        int rnd = 0;
        for (int x = 3; x >= 0; x--) {
            for (int y = 3; y >= 0; y--) {
                /*
                rnd = new Random().nextInt(possibleTileValues.size());
                Entity tile = new Tile(x,y, possibleTileValues.get(rnd)).spawnTile();
                */
                System.out.println("[X=" + x + ", Y=" + y + "]");
            }
        }
    }



    @Override
    protected void initInput(){
        Input input = getInput();

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
        input.addAction(info, KeyCode.I);

        UserAction startGame = new UserAction("startgame") {
            @Override
            protected void onActionBegin() {
                super.onActionBegin();
                // tiles.clear();
                // Remove start text
                startGameText.removeFromWorld();


                Tile tile1 = new Tile(0,0,2);
                Tile tile2 = new Tile(2,1,4);
                Tile tile3 = new Tile(1,3,8);
                Tile tile4 = new Tile(1,0,16);
                Tile tile5 = new Tile(1,2,32);
                Tile tile6 = new Tile(2,0,64);
                Tile tile7 = new Tile(0,3,128);
                Tile tile8 = new Tile(0,1,256);

                tileEntity = tile1.spawnTile();
                tileMap.put(tileEntity, tile1);

                tileEntity = tile2.spawnTile();
                tileMap.put(tileEntity, tile2);

                tileEntity = tile3.spawnTile();
                tileMap.put(tileEntity, tile3);

                tileEntity = tile4.spawnTile();
                tileMap.put(tileEntity, tile4);

                tileEntity = tile5.spawnTile();
                tileMap.put(tileEntity, tile5);

                tileEntity = tile6.spawnTile();
                tileMap.put(tileEntity, tile6);

                tileEntity = tile7.spawnTile();
                tileMap.put(tileEntity, tile7);

                tileEntity = tile8.spawnTile();
                tileMap.put(tileEntity, tile8);



                ArrayList<Integer> possibleTileValues = new ArrayList<>();
                for (int i = 2; i <= 256 ; i*=2) {
                    possibleTileValues.add(i);
                }


                int rnd;

                /*
                 * For the Up move
                 */
                /*
                rnd = 0;
                for (int y = 0; y < 4; y++) {
                    for (int x = 0; x < 4; x++) {
                        rnd = new Random().nextInt(possibleTileValues.size());
                        Entity tile = new Tile(x,y, possibleTileValues.get(rnd)).spawnTile();
                        System.out.println("\nCreated tile at: [X=" + x + ", Y=" + y + "]");
                    }
                }
                */


                /*
                 * For the right move
                */
                /*
                rnd = 0;
                for (int x = 3; x >= 0; x--) {
                    for (int y = 3; y >= 0; y--) {
                        rnd = new Random().nextInt(possibleTileValues.size());
                        Entity tile = new Tile(x,y, possibleTileValues.get(rnd)).spawnTile();
                        System.out.println("\nCreated tile at: [X=" + x + ", Y=" + y + "]");
                    }
                }
                */



                /*
                 * For the down move
                 */
                /*
                rnd = 0;
                for (int y = 3; y >= 0; y--) {
                    for (int x = 0; x < 4; x++) {
                        rnd = new Random().nextInt(possibleTileValues.size());
                        Entity tile = new Tile(x,y, possibleTileValues.get(rnd)).spawnTile();
                        System.out.println("\nCreated tile at: [X=" + x + ", Y=" + y + "]");
                    }
                }
                */


                /*
                 * For the Left move
                 */
                /*
                rnd = 0;
                for (int x = 0; x < 4; x++) {
                    for (int y = 0; y < 4; y++) {
                        rnd = new Random().nextInt(possibleTileValues.size());
                        Entity tile = new Tile(x,y, possibleTileValues.get(rnd)).spawnTile();
                        System.out.println("\nCreated tile at: [X=" + x + ", Y=" + y + "]\n");
                    }
                }
                */




            }
            @Override
            protected void onAction() {
                super.onAction();
            }

            @Override
            protected void onActionEnd() {
                super.onActionEnd();



            // Now i will move all of them to the right

                /*
                    NOTE: Get all tile objects from hashmap, then move the entity
                    SUGGESTION:
                    How about searching if there is a tile from the right side of, then goes to the left?
                    Then if search finds a tile, then move that to the right
                 */


                System.out.println("================================");
                System.out.println("TILE LOCATED AT");
                for (Tile tile : tileMap.values()){
                    System.out.println(tile.toString());
                }
                System.out.println("================================");


                for (int x = 3; x >= 0; x--) {
                    for (int y = 0; y < 4; y++) {
                        System.out.println("Searching for [X="+x + ",Y=" + y + "]");

                        for (Entity enti : tileMap.keySet()){
                            Tile tile = tileMap.get(enti);

                            // Is there any tile there exist in this iteration?
                            if ((tile.getXPos() == x) && (tile.getYPos() == y)){

                                // Oh god, we found 1!!!
                                // Now update xy values and move it to the right.

                                System.out.println("Found 1 in grid with : " + tile.toString());
                                System.out.println("Can it move? " + canMove(tile));
                                while (canMove(tile)){
                                        System.out.println("\nMOVING!");
                                        tile.setXPos(tile.getXPos() + 1);
                                        enti.setPosition(tile.getUICoordinates()[0], tile.getUICoordinates()[1]);
                                        System.out.println("I JUST MOVED");
                                        System.out.println(tile.toString() + "\n");

                                }







                            }

                            // If we enter x value 2,

                        }

                    }
                }

/*
                for (Entity enti: tileMap.keySet()){
                    Tile currentTile = tileMap.get(enti);
                    System.out.println("\nBEFORE");
                    System.out.println(currentTile.toString());
                    System.out.println("Moving until i find the last spot in X");
                    while (currentTile.getXPos() != 3){

                        int currTileNextXPos = currentTile.getXPos()+1;
                        // Is there someone to the right of me?
                        for (Tile checkTile : tileMap.values()){

                            if (checkTile.getXPos() == currTileNextXPos){

                            }
                        }

                        currentTile.setXPos(currentTile.getXPos()+1);
                        System.out.println("Moved 1 to the right");
                        System.out.println(currentTile.toString());
                    }
                    System.out.println("FINAL POS FOR TILE ");
                    System.out.println(currentTile.toString() + "\n");

                    // Now update the entity
                    enti.setPosition(currentTile.getUICoordinates()[0], currentTile.getUICoordinates()[1]);

                }
                */



            }
        };
        input.addAction(startGame, KeyCode.SPACE);



    }

    protected void moveRight(){

    }

    protected boolean canMove(Tile tile){
        for (Tile checktile : tileMap.values()){
            // Check if param tile x+1 is conflicting with any existing tiles.
            if (((tile.getXPos() + 1) == checktile.getXPos()) && (tile.getYPos() == checktile.getYPos())){
                return false;
            }
            if (tile.getXPos() == 3){
                return false;
            }
        }
        return true;
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