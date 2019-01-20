package sample;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.settings.GameSettings;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.*;


/**
 * @author Lucas Kugelberg
 * @version 0.1
 *
 */

public class Game2048 extends GameApplication {

    public final static int CORNER_VALUE = 10;
    private Entity startGameText;
    public Tile tempNewTile;

    // Every Entity haves a tile
    public HashMap<Entity, Tile> tileMap = new HashMap<>();
    public ArrayList<Object[]> objectsToBeRemoved = new ArrayList<>();
    public Entity tempNewEntity = new Entity();
    public Entity tileEntity = new Entity();

    public Object[] tempNewObject;



    /*
        TESTING
     */
    public List<Object[]> tileTable = new ArrayList<>();




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
                generateNewTile();
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
*/

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
                System.out.println("\n\n================ INFO ================");
                System.out.println("[INFO] SIZE: " + tileTable.size());
                for (Object[] entiTile : tileTable){
                    Tile tile = (Tile) entiTile[1];
                    System.out.println(tile.toString());
                }

                System.out.println("================ INFO ================\n\n");

            }
        }; input.addAction(info, KeyCode.I);


        UserAction moveUp = new UserAction("move_up") {
            @Override
            protected void onActionBegin() {
                super.onActionBegin();
                moveUp();
            }

            @Override
            protected void onActionEnd() {
                super.onActionEnd();

            }

        };
        input.addAction(moveUp, KeyCode.UP);


        UserAction moveRight = new UserAction("move_right") {
            @Override
            protected void onActionBegin() {
                super.onActionBegin();
                moveRight();

            }

            @Override
            protected void onActionEnd() {
                super.onActionEnd();

            }

        };
        input.addAction(moveRight, KeyCode.RIGHT);


        UserAction moveDown = new UserAction("move_down") {
            @Override
            protected void onActionBegin() {
                super.onActionBegin();
                moveDown();
            }

            @Override
            protected void onActionEnd() {
                super.onActionEnd();


            }

        };
        input.addAction(moveDown, KeyCode.DOWN);


        UserAction moveLeft = new UserAction("move_left") {
            @Override
            protected void onActionBegin() {
                super.onActionBegin();
                //moveLeft();
            }

            @Override
            protected void onActionEnd() {
                super.onActionEnd();


            }
        };
        input.addAction(moveLeft, KeyCode.LEFT);


    }

    protected void moveUp(){


        int i = 0;
        boolean mergeSwitch = true;
        do {
            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {

                    for (Object[] entiTile : tileTable){

                        Entity enti = (Entity) entiTile[0]; // Cast the object to Entity
                        Tile tile = (Tile) entiTile[1];     // Cast the object to Tile

                        if ((tile.getYPos() == y) && (tile.getXPos() == x)){

                            // If can move, then update tile object and entity
                            while(canMove(entiTile, "up")){

                                tile.setYPos(tile.getYPos()-1);
                                enti.setPosition(tile.getUICoordinates()[0], tile.getUICoordinates()[1]);
                            }
                        }
                    }
                }
            }
            // Only make the merge once, and make it between the loop which there iterate 2x
            if (mergeSwitch){
                // Somehow, the software needs to move all the tiles, then merge, then move again.
                merge("up");
            }
            mergeSwitch = false;
            i++;
        } while (i<2);



    }

    protected void moveRight(){

        int i = 0;
        boolean mergeSwitch = true;
        do {
            for (int y = 0; y < 4; y++) {
                for (int x = 3; x >= 0; x--) {

                    for (Object[] entiTile : tileTable){

                        Entity enti = (Entity) entiTile[0]; // Cast the object to Entity
                        Tile tile = (Tile) entiTile[1];     // Cast the object to Tile

                        if ((tile.getXPos() == x) && (tile.getYPos() == y)){

                            // If can move, then update tile object and entity
                            while(canMove(entiTile, "right")){

                                tile.setXPos(tile.getXPos()+1);
                                enti.setPosition(tile.getUICoordinates()[0], tile.getUICoordinates()[1]);
                            }
                        }
                    }
                }
            }
            // Only make the merge once, and make it between the loop which there iterate 2x
            if (mergeSwitch){
                // Somehow, the software needs to move all the tiles, then merge, then move again.
                merge("right");
            }
            mergeSwitch = false;
            i++;
        } while (i<2);

    }

    protected void moveDown(){

        for (int x = 0; x < 4; x++) {
            for (int y = 3; y >= 0; y--) {

                for (Object[] entiTile : tileTable){
                    Entity enti = (Entity) entiTile[0]; // Cast the object to Entity
                    Tile tile = (Tile) entiTile[1];     // Cast the object to Tile

                    if ((tile.getXPos() == x) && (tile.getYPos() == y)){

                        // If can move, then update tile object and entity
                        while(canMove(entiTile, "down")){
                            tile.setYPos(tile.getYPos()+1);
                            enti.setPosition(tile.getUICoordinates()[0], tile.getUICoordinates()[1]);
                        }
                        //canMerge(entiTile, "down");


                    }
                }
            }
        }
        removeTiles();
/*
        for (int y = 3; y >= 0; y--) {
            for (int x = 0; x < 4; x++) {
                for (Entity enti : tileMap.keySet()){
                    Tile tile = tileMap.get(enti);

                    while (canMove(tile, "down")){
                        tile.setYPos(tile.getYPos() + 1);
                        enti.setPosition(tile.getUICoordinates()[0], tile.getUICoordinates()[1]);
                    }
                }
                generateNewTile();
            }
        }
        */
    }

/*
    protected void moveLeft(){

        // Now i will move all of them to the Left
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                for (Entity enti : tileMap.keySet()){
                    Tile tile = tileMap.get(enti);

                    while (canMove(tile, "left")){
                        tile.setXPos(tile.getXPos() - 1);
                        enti.setPosition(tile.getUICoordinates()[0], tile.getUICoordinates()[1]);
                    }
                }
                generateNewTile();
            }
        }
    }
*/


    // TODO: I am in a loop
    protected boolean canMove(Object[] entiTile, String direction){

        Entity enti = (Entity) entiTile[0];
        Tile tile = (Tile) entiTile[1];

        if (direction.toLowerCase() == "up"){

            // Cant go outside the grid
            if (tile.getYPos() == 0){
                return false;
            }

            for (Object[] checkEntiTile : tileTable){
                Entity checkEnti = (Entity) checkEntiTile[0];
                Tile checkTile = (Tile) checkEntiTile[1];

                if (tile.getYPos() - 1 == checkTile.getYPos() && tile.getXPos() == checkTile.getXPos())

                    return false;

            }
            return true;

        }



        if (direction.toLowerCase() == "right"){

            // Cant go outside the grid
            if (tile.getXPos()+1 == 4){
                return false;
            }

            for (Object[] checkEntiTile : tileTable){
                Entity checkEnti = (Entity) checkEntiTile[0];
                Tile checkTile = (Tile) checkEntiTile[1];

                if (((tile.getXPos() + 1) == checkTile.getXPos()) && (tile.getYPos() == checkTile.getYPos())){
                    return false;
                }
            }
            return true;

        }


        if (direction.toLowerCase() == "down"){

            for (Object[] checkEntiTile : tileTable){
                Entity checkEnti = (Entity) checkEntiTile[0];
                Tile checkTile = (Tile) checkEntiTile[1];

                // Cant go outside the grid
                if (tile.getYPos()+1 == 4){
                    return false;
                }

                if (tile.getYPos()+1 == checkTile.getYPos() && tile.getXPos() == checkTile.getXPos()){
                    return false;
                }
            }
            return true;
        }


        System.out.println("Didn't go as planned, I ended up in the last return... Try looking at direction");
        return false;


    }

    protected void merge(String direction){


        if (direction == "up"){
            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {
                    for (int i = 0; i < tileTable.size(); i++) {

                        Object[] currentObj = tileTable.get(i);
                        Entity enti = (Entity) currentObj[0];
                        Tile tile = (Tile) currentObj[1];

                        // Find a exissting tile
                        if ((tile.getXPos() == x) && (tile.getYPos() == y)){

                            for (int j = 0; j < tileTable.size(); j++) {
                                Object[] checkObj = tileTable.get(j);
                                Entity checkEnti = (Entity) checkObj[0];
                                Tile checkTile = (Tile) checkObj[1];

                                // This checks if the tile1 (downunder) is available, and they share the same X-value and number
                                if (((tile.getYPos() + 1) == checkTile.getYPos()) && (tile.getXPos() == checkTile.getXPos()) && (tile.getTv().getValue() == checkTile.getTv().getValue())){
                                    System.out.println("[TILE] " + tile.toString());
                                    System.out.println("[CHECKING] " + checkTile.toString());
                                    System.out.println();
                                    System.out.println("X = " +x);
                                    y++;
                                    System.out.println("X = " +x);

                                    // merge the objects
                                    int newValue = tile.getTv().getValue() + checkTile.getTv().getValue();

                                    tempNewTile = new Tile(tile.getXPos(), tile.getYPos(), newValue);
                                    tempNewEntity = tempNewTile.spawnTile();
                                    tileTable.add(new Object[]{tempNewEntity, tempNewTile});


                                    enti.removeFromWorld();
                                    checkEnti.removeFromWorld();
                                    tileTable.remove(currentObj);
                                    tileTable.remove(checkObj);

                                }
                            }
                        }
                    }
                }
            }
        }


        if (direction == "right"){
            for (int y = 0; y < 4; y++) {
                for (int x = 3; x >= 0; x--) {
                    for (int i = 0; i < tileTable.size(); i++) {

                        Object[] currentObj = tileTable.get(i);
                        Entity enti = (Entity) currentObj[0];
                        Tile tile = (Tile) currentObj[1];

                        // Find a exissting tile
                        if ((tile.getXPos() == x) && (tile.getYPos() == y)){

                            for (int j = 0; j < tileTable.size(); j++) {
                                Object[] checkObj = tileTable.get(j);
                                Entity checkEnti = (Entity) checkObj[0];
                                Tile checkTile = (Tile) checkObj[1];

                                // This checks if the tile-1 (left) is available, and they share the same Ypos and value
                                if (((tile.getXPos() - 1) == checkTile.getXPos()) && (tile.getYPos() == checkTile.getYPos()) && (tile.getTv().getValue() == checkTile.getTv().getValue())){
                                    System.out.println("[TILE] " + tile.toString());
                                    System.out.println("[CHECKING] " + checkTile.toString());
                                    System.out.println();
                                    System.out.println("X = " +x);
                                    x--;
                                    System.out.println("X = " +x);

                                    // merge the objects
                                    int newValue = tile.getTv().getValue() + checkTile.getTv().getValue();

                                    tempNewTile = new Tile(checkTile.getXPos(), checkTile.getYPos(), newValue);
                                    tempNewEntity = tempNewTile.spawnTile();
                                    tileTable.add(new Object[]{tempNewEntity, tempNewTile});


                                    enti.removeFromWorld();
                                    checkEnti.removeFromWorld();
                                    tileTable.remove(currentObj);
                                    tileTable.remove(checkObj);

                                }
                            }
                        }
                    }
                }
            }
        }
    }


    public void removeTiles(){


    }

    public void generateNewTile(){
        int rndValue = new Random().nextDouble() < 0.9 ? 2 : 4;
        /*
        boolean haveInitNewTile = false;

        if (tileMap.isEmpty()){
            System.out.println("tilemap is empty");
            Tile tile1 = new Tile(3,0, new Random().nextDouble() < 1.0 ? 2 : 4);
            Tile tile2 = new Tile(1,0, new Random().nextDouble() < 1.0 ? 2 : 4);
            Tile tile3 = new Tile(2,0, new Random().nextDouble() < 1.0 ? 2 : 4);
            Tile tile4 = new Tile(0,0, new Random().nextDouble() < 1.0 ? 2 : 4);
            tileEntity = tile1.spawnTile();
            tileMap.put(tileEntity, tile1);

            tileEntity = tile2.spawnTile();
            tileMap.put(tileEntity, tile2);

            tileEntity = tile3.spawnTile();
            tileMap.put(tileEntity, tile3);

            tileEntity = tile4.spawnTile();
            tileMap.put(tileEntity, tile4);
        }
        */

        /*
            TESTING
         */
        Tile tile1 = new Tile(0,0,4);
        Tile tile2 = new Tile(1,0,4);
        Tile tile3 = new Tile(2,0,8);
        Tile tile4 = new Tile(3,0,8);

        tileEntity = tile1.spawnTile();
        Object[] entiTileArr = {tileEntity, tile1};
        tileTable.add(entiTileArr);

        tileEntity = tile2.spawnTile();
        entiTileArr = new Object[]{tileEntity, tile2};
        tileTable.add(entiTileArr);

        tileEntity = tile3.spawnTile();
        entiTileArr = new Object[]{tileEntity, tile3};
        tileTable.add(entiTileArr);

        tileEntity = tile4.spawnTile();
        entiTileArr = new Object[]{tileEntity, tile4};
        tileTable.add(entiTileArr);



        Tile tile5 = new Tile(0,1,4);
        Tile tile6 = new Tile(1,1,4);
        Tile tile7 = new Tile(2,1,4);
        Tile tile8 = new Tile(3,1,4);

        tileEntity = tile5.spawnTile();
        entiTileArr = new Object[]{tileEntity, tile5};
        tileTable.add(entiTileArr);

        tileEntity = tile6.spawnTile();
        entiTileArr = new Object[]{tileEntity, tile6};
        tileTable.add(entiTileArr);

        tileEntity = tile7.spawnTile();
        entiTileArr = new Object[]{tileEntity, tile7};
        tileTable.add(entiTileArr);

        tileEntity = tile8.spawnTile();
        entiTileArr = new Object[]{tileEntity, tile8};
        tileTable.add(entiTileArr);



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