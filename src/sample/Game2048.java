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

import java.lang.reflect.Array;
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
        settings.setAppIcon("ui/icons/logo.png");
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
                generateNewTile(true);


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
                generateNewTile(false);
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
                generateNewTile(false);

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
                generateNewTile(false);
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
                moveLeft();
                generateNewTile(false);
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
                            while(canMove(tile.getXPos(), tile.getYPos()-1)){
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
                            while(canMove(tile.getXPos()+1, tile.getYPos())){
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

        int i = 0;
        boolean mergeSwitch = true;
        do {
            for (int x = 0; x < 4; x++) {
                for (int y = 3; y >= 0; y--) {

                    for (Object[] entiTile : tileTable){

                        Entity enti = (Entity) entiTile[0]; // Cast the object to Entity
                        Tile tile = (Tile) entiTile[1];     // Cast the object to Tile

                        if ((tile.getYPos() == y) && (tile.getXPos() == x)){

                            // If can move, then update tile object and entity
                            while(canMove(tile.getXPos(), tile.getYPos()+1)){

                                tile.setYPos(tile.getYPos()+1);
                                enti.setPosition(tile.getUICoordinates()[0], tile.getUICoordinates()[1]);
                            }
                        }
                    }
                }
            }
            // Only make the merge once, and make it between the loop which there iterate 2x
            if (mergeSwitch){
                // Somehow, the software needs to move all the tiles, then merge, then move again.
                merge("down");
            }
            mergeSwitch = false;
            i++;
        } while (i<2);

    }


    protected void moveLeft(){

        int i = 0;
        boolean mergeSwitch = true;
        do {
            for (int y = 0; y < 4; y++) {
                for (int x = 0; x < 4; x++) {

                    for (Object[] entiTile : tileTable){

                        Entity enti = (Entity) entiTile[0]; // Cast the object to Entity
                        Tile tile = (Tile) entiTile[1];     // Cast the object to Tile

                        if ((tile.getXPos() == x) && (tile.getYPos() == y)){

                            // If can move, then update tile object and entity
                            while(canMove(tile.getXPos()-1, tile.getYPos())){
                                tile.setXPos(tile.getXPos()-1);
                                enti.setPosition(tile.getUICoordinates()[0], tile.getUICoordinates()[1]);
                            }
                        }
                    }
                }
            }
            // Only make the merge once, and make it between the loop which there iterate 2x
            if (mergeSwitch){
                // Somehow, the software needs to move all the tiles, then merge, then move again.
                merge("left");
            }
            mergeSwitch = false;
            i++;
        } while (i<2);

    }

/*
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

                if (tile.getYPos() - 1 == checkTile.getYPos() && tile.getXPos() == checkTile.getXPos()){
                    return false;
                }
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

            // Cant go outside the grid
            if (tile.getYPos()+1 == 4){
                return false;
            }

            for (Object[] checkEntiTile : tileTable){
                Entity checkEnti = (Entity) checkEntiTile[0];
                Tile checkTile = (Tile) checkEntiTile[1];

                if (((tile.getYPos() + 1) == checkTile.getYPos()) && (tile.getXPos() == checkTile.getXPos())){
                    return false;
                }
            }
            return true;
        }

        if (direction.toLowerCase() == "left"){

            // Cant go outside the grid
            if (tile.getXPos() == 0){
                return false;
            }

            for (Object[] checkEntiTile : tileTable){
                Entity checkEnti = (Entity) checkEntiTile[0];
                Tile checkTile = (Tile) checkEntiTile[1];

                if (((tile.getXPos() - 1) == checkTile.getXPos()) && (tile.getYPos() == checkTile.getYPos())){
                    return false;
                }
            }
            return true;
        }


        System.out.println("Didn't go as planned, I ended up in the last return... Try looking at direction");
        return false;


    }
*/
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


                                // TODO: refactor, this is not a properly check if tile is available
                                // Maybe do something like having a static array, there can give a arraylist of available locations
                                // This checks if the tile1 (downunder) is available, and they share the same X-value and number
                                if (((tile.getYPos() + 1) == checkTile.getYPos()) && (tile.getXPos() == checkTile.getXPos()) && (tile.getTv().getValue() == checkTile.getTv().getValue())){
                                    y++;

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
                                    x--;


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


        if (direction == "down"){
            for (int x = 0; x < 4; x++) {
                for (int y = 3; y >= 0; y--) {
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

                                // This checks if the tile1 (above) is available, and they share the same X-value and number
                                if (((tile.getYPos() - 1) == checkTile.getYPos()) && (tile.getXPos() == checkTile.getXPos()) && (tile.getTv().getValue() == checkTile.getTv().getValue())){
                                    y--;


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

        if (direction == "left"){
            for (int y = 0; y < 4; y++) {
                for (int x = 0; x < 4; x++) {
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
                                if (((tile.getXPos() + 1) == checkTile.getXPos()) && (tile.getYPos() == checkTile.getYPos()) && (tile.getTv().getValue() == checkTile.getTv().getValue())){
                                    x++;
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



    }


    public boolean canMove(int toX, int toY){

        if (toX == 4 || toX == -1) return false;

        if (toY == 4 || toY == -1) return false;

        // Iterate over all possible locations
        for (Object[] obj : tileTable){
            Tile tile = (Tile) obj[1];

            // Check if there exist a tile at this location
            if (tile.getXPos() == toX && tile.getYPos() == toY){
                return false;
            }

        }

        return true;

    }

    public ArrayList<Object[]> getAvailableSpots(){
        ArrayList<Object[]> tilesAvailable = new ArrayList<>();
        ArrayList<Object[]> tilesTaken = new ArrayList<>();
        ArrayList<Object[]> xyToBeRemoved = new ArrayList<>();
        tilesTaken.addAll(tileTable);

        // Lets add all combinations to the array
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                tilesAvailable.add(new Object[]{x,y});
            }
        }

        // Then we check if an existing tile matching with a available tile, then we will add it to a removeArray
        for (Object[] taken : tilesTaken){
            Tile tile = (Tile) taken[1];

            for (Object[] available : tilesAvailable){
                int x = (int) available[0];
                int y = (int) available[1];

                // If they match, remove the available tile from array
                if ((tile.getXPos() == x) && (tile.getYPos() == y)){
                    xyToBeRemoved.add(available);
                }
            }

        }

        // Time to remove the objects from the original array
        for (int i = 0; i < xyToBeRemoved.size(); i++) {
            tilesAvailable.remove(xyToBeRemoved.get(i));
        }



        return tilesAvailable;

    }




    public void generateNewTile(boolean isStarting){

        int max = 1;
        if (isStarting) max = 2;
        int rndValue = new Random().nextDouble() < 0.9 ? 2 : 4;

        for (int i = 0; i < max; i++) {
            int rndI = new Random().nextInt(getAvailableSpots().size());
            Object[] rndObj = getAvailableSpots().get(rndI);



            Tile tile = new Tile((int) rndObj[0], (int) rndObj[1], 2);
            tileEntity = tile.spawnTile();
            tileTable.add(new Object[]{tileEntity, tile});

        }





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