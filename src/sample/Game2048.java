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
    public Entity tileEntity = new Entity();
    public Entity tempTileEnti = new Entity();
    public Tile tempTile;

    // Every Entity haves a tile
    public HashMap<Entity, Tile> tileMap = new HashMap<>();

    public ArrayList<Tile> tilesToBeRemoved = new ArrayList<>();


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



    @Override
    protected void initInput(){
        Input input = getInput();


        UserAction startGame = new UserAction("start_game") {
            @Override
            protected void onActionBegin() {
                super.onActionBegin();
                // tiles.clear();
                // Remove start text
                startGameText.removeFromWorld();
/*

                Tile tile1 = new Tile(0,0,2);
                Tile tile2 = new Tile(2,1,4);
                Tile tile3 = new Tile(1,3,8);
                Tile tile4 = new Tile(1,0,16);
                Tile tile5 = new Tile(1,2,32);
                Tile tile55 = new Tile(0,2,32);
                Tile tile6 = new Tile(2,0,64);
                Tile tile7 = new Tile(0,3,128);
                Tile tile8 = new Tile(3,1,256);

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
*/

                Tile tile1 = new Tile(0,0,2);
                Tile tile2 = new Tile(2,1,2);
                Tile tile3 = new Tile(1,3,4);
                Tile tile4 = new Tile(1,0,4);
                tileEntity = tile1.spawnTile();
                tileMap.put(tileEntity, tile1);

                tileEntity = tile2.spawnTile();
                tileMap.put(tileEntity, tile2);

                tileEntity = tile3.spawnTile();
                tileMap.put(tileEntity, tile3);

                tileEntity = tile4.spawnTile();
                tileMap.put(tileEntity, tile4);


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
        };
        input.addAction(startGame, KeyCode.SPACE);

        UserAction info = new UserAction("info") {
            @Override
            protected void onActionBegin() {
                super.onActionBegin();

                System.out.println(tileMap.size());

            }
        }; input.addAction(info, KeyCode.I);


        UserAction moveUp = new UserAction("move_up") {
            @Override
            protected void onActionBegin() {
                super.onActionBegin();
                moveUp();
            }

        };
        input.addAction(moveUp, KeyCode.UP);


        UserAction moveRight = new UserAction("move_right") {
            @Override
            protected void onActionBegin() {
                super.onActionBegin();
                moveRight();
            }

        };
        input.addAction(moveRight, KeyCode.RIGHT);


        UserAction moveDown = new UserAction("move_down") {
            @Override
            protected void onActionBegin() {
                super.onActionBegin();
                moveDown();
            }

        };
        input.addAction(moveDown, KeyCode.DOWN);


        UserAction moveLeft = new UserAction("move_left") {
            @Override
            protected void onActionBegin() {
                super.onActionBegin();
                moveLeft();
            }

        };
        input.addAction(moveLeft, KeyCode.LEFT);


    }

    protected void moveUp(){

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                for (Entity enti : tileMap.keySet()){
                    Tile tile = tileMap.get(enti);

                    while (canMove(tile, "up")){
                        tile.setYPos(tile.getYPos() - 1);
                        enti.setPosition(tile.getUICoordinates()[0], tile.getUICoordinates()[1]);

                    }
                }

                // Time to remove the old tiles and entities
                removeTiles();


            }
        }
    }

    protected void moveRight(){

        // Now i will move all of them to the right
        for (int x = 3; x >= 0; x--) {
            for (int y = 0; y < 4; y++) {
                for (Entity enti : tileMap.keySet()){
                    Tile tile = tileMap.get(enti);

                    // Is there any tile there exist in this iteration?
                    if ((tile.getXPos() == x) && (tile.getYPos() == y)){

                        // Oh god, we found 1!!!
                        // Now update xy values and move it to the right.
                        while (canMove(tile, "right")){
                            tile.setXPos(tile.getXPos() + 1);
                            enti.setPosition(tile.getUICoordinates()[0], tile.getUICoordinates()[1]);
                        }
                    }
                }
            }
        }
    }

    protected void moveDown(){

        for (int y = 3; y >= 0; y--) {
            for (int x = 0; x < 4; x++) {
                for (Entity enti : tileMap.keySet()){
                    Tile tile = tileMap.get(enti);

                    // Is there any tile there exist in this iteration?
                    if ((tile.getYPos() == y) && (tile.getXPos() == x)){

                        // Oh god, we found 1!!!
                        // Now update xy values and move it to the right.
                        while (canMove(tile, "down")){
                            tile.setYPos(tile.getYPos() + 1);
                            enti.setPosition(tile.getUICoordinates()[0], tile.getUICoordinates()[1]);

                        }
                    }
                }
            }
        }
    }

    protected void moveLeft(){

        // Now i will move all of them to the Left
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                for (Entity enti : tileMap.keySet()){
                    Tile tile = tileMap.get(enti);

                    // Is there any tile there exist in this iteration?
                    if ((tile.getXPos() == x) && (tile.getYPos() == y)){

                        // Oh god, we found 1!!!
                        // Now update xy values and move it to the right.
                        while (canMove(tile, "left")){
                            tile.setXPos(tile.getXPos() - 1);
                            enti.setPosition(tile.getUICoordinates()[0], tile.getUICoordinates()[1]);
                        }
                    }
                }
            }
        }
    }

    protected boolean canMove(Tile tile, String direction){

        if (direction.toLowerCase() == "up"){

            for (Tile checktile : tileMap.values()){

                if (canMerge(tile, checktile, "up")){
                    merge(tile,checktile,"up");
                }

                if (((tile.getYPos() -1 ) == checktile.getYPos()) && (tile.getXPos() == checktile.getXPos())){
                    return false;
                }
                if (tile.getYPos() == 0){
                    return false;
                }
            }
            return true;
        }

        if (direction.toLowerCase() == "right"){

            for (Tile checktile : tileMap.values()){

                if (canMerge(tile, checktile, "right")){

                }

                if (((tile.getXPos() + 1) == checktile.getXPos()) && (tile.getYPos() == checktile.getYPos())){
                    return false;
                }
                if (tile.getXPos() == 3){
                    return false;
                }
            }
            return true;
        }

        if (direction.toLowerCase() == "down"){

            for (Tile checktile : tileMap.values()){

                if (canMerge(tile, checktile, "down")){

                }

                if (((tile.getYPos() +1 ) == checktile.getYPos()) && (tile.getXPos() == checktile.getXPos())){
                    return false;
                }
                if (tile.getYPos() == 3){
                    return false;
                }
            }
            return true;
        }

        if (direction.toLowerCase() == "left"){

            for (Tile checktile : tileMap.values()){

                if (canMerge(tile, checktile, "left")){

                }

                if (((tile.getXPos() - 1) == checktile.getXPos()) && (tile.getYPos() == checktile.getYPos())){
                    return false;
                }
                if (tile.getXPos() == 0){
                    return false;
                }
            }
            return true;
        }



        System.out.println("Didn't go as planned, I ended up in the last return... Try looking at direction");
        return false;


    }

    protected boolean canMerge(Tile tile1, Tile tile2, String direction){
        if (direction.equals("up")){
            // If there is a tile upon the current tile
            if (((tile1.getYPos() - 1) == tile2.getYPos()) && (tile1.getXPos() == tile2.getXPos())){
                // If the value match
                if (tile1.getTv().getValue() == tile2.getTv().getValue()){
                    System.out.println("[+] " + tile1.toString() + " AND " + tile2.toString() + " CAN MERGE!");
                    // Return true
                    return true;
                }
            }
        }


        return false;
    }

    protected void merge(Tile tile1, Tile tile2, String direction){

        // Get direction
        if (direction.equals("up")){


            /*
                My idea, is to create a new temporary tile and Entity,
                then when it has been created with the new walue,
                I will remove the old tiles and entities, and add the new tile and entity to tilemap

                The reason that I not will put and remove to tilemap, is because all the move methods are in iteration of tilemap.
                And this method is called within the move methods
            */

            // I use tile2's XY values, because it is the one highest to the direction it is going.
            tempTile = new Tile(tile2.getXPos(), tile2.getYPos(), ((tile1.getTv().getValue()) + (tile2.getTv().getValue())));
            tempTileEnti = tempTile.spawnTile();

            // Add tile1 and tile2 to a toBeRemovedArray
            tilesToBeRemoved.add(tile1);
            tilesToBeRemoved.add(tile2);


        }
    }

    public void removeTiles(){

        Iterator<Entity> it = tileMap.keySet().iterator();

        while(it.hasNext()){
            Entity enti = it.next();

            for (Tile tile : tilesToBeRemoved){
                Tile mapTile = tileMap.get(enti);

                if (mapTile == tile){
                    it.remove();
                    enti.removeFromWorld();
                    System.out.println("[+] Object has been removed");
                }

            }

        }

        // Finally add the temporary tiles to tilemap
        tileMap.put(tempTileEnti, tempTile);

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