package sample;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.settings.GameSettings;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.util.*;


/**
 * @author Lucas Kugelberg
 * @version 1.0
 *
 * This is the Main class. This is where the game starts.
 *
 * The game 2048 is kinda like a strategy game. Join the numbers and get the tile of 2048.
 * This is how it works:
 * You start the game and press space.
 * Then you use your arrows to move the tiles to the desired direction of your choice.
 * When you are moving, lets say a tile there hold a value of 2, is on the same path as another tile with the same value,
 * they will merge together as 1 tile. Now you score will update with a value of 4. Because that is the new value you just created.
 *
 * This is a very entertaining game, but also pretty difficult.
 *
 *
 *
 *
 */

public class Game2048 extends GameApplication {

    /**
     * This is the arc value for rectangles.
     */
    public final static int CORNER_VALUE = 10;
    /**
     * The game store its current tiles in a Arraylist object which there is called tileTable.
     * This tiletable is always updated. Whether the tile is added or merged, tileTable will always be a reference to see what tiles there is on the screen and being used.
     * The tileTable holds a list of objects. The first[0] element in the list is the Entity object and the second[1] holds the Tile object.
     */
    public List<Object[]> tileTable = new ArrayList<>();
    /**
     * This is the welcome text. On how to get started playing.
     */
    private Entity startGameText;
    /**
     * When a merge is happening, there is need for a temporary tile there can hold the new value. TODO: Can maybe be optimized
     */
    public Tile tempNewTile;
    /**
     * This is our score object. This holds all the UI the score boxes and also holds the values of our currentScore and the highestScore.
     */
    public Score score = new Score();
    /**
     * When er merge is happening, we need to store a temporary Entity.
     */
    public Entity tempNewEntity = new Entity();
    /**
     * the tileEntity is used whenever a spawn is happening.
     */
    public Entity tileEntity = new Entity();
    /**
     * Our gameover Entity. This is used to tell the user, if the game is over.
     */
    public Node gameOver;
    /**
     * This is used to verify if the game has been restarted. If the software did check, it will be calling gameOver every time a user press a arrow.
     */
    public boolean haveRestartedGame = false;
    /**
     * This boolean is used to verify if a tile has been move or merged. This is mostly used when we want to spawn a new tile.
     */
    boolean haveMoved = false;

    /**
     * Initial settings for the game, such as width, height, version, appIcon and title.
     * @param settings the FXGL game setting
     */
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(360);
        settings.setHeight(600);
        settings.setTitle("2048 Game");
        settings.setVersion("1.0");
        settings.setAppIcon("ui/icons/logo.png");
    }

    /**
     * Initialize the game itself with a Override annotation.
     */
    @Override
    protected void initGame(){
        initBackground();

    }

    /**
     * Initialize all of the inputs and assign them a action.
     * For instance, if the user press space, to start the game, it will remove the startGameText from screen and calling method generateNewTile.
     *
     */
    @Override
    protected void initInput(){
        Input input = getInput();

        // Start the game by pressing space
        UserAction startGame = new UserAction("start_game") {
            @Override
            protected void onActionBegin() {
                super.onActionBegin();

                // Remove start text
                startGameText.removeFromWorld();
                // Generate 2 tiles, because this is the first spawn
                generateNewTile(true);

            }
        };
        input.addAction(startGame, KeyCode.SPACE);

        // Play again by pressing enter
        UserAction playAgain = new UserAction("play_again") {
            @Override
            protected void onActionBegin() {
                super.onActionBegin();

                // Remove all entity tiles from the world.
                for (Object[] obj : tileTable){
                    Entity enti = (Entity) obj[0];
                    enti.removeFromWorld();
                }

                // Reset score
                score.setCurrentScore(0);
                getGameState().setValue("currentScoreValue", 0);

                // Empty our tileTable
                tileTable.clear();
                // Remove gameOver overlay from UI
                getGameScene().removeUINode(gameOver);
                // Generate 2 tiles, because this is the first spawn
                generateNewTile(true);

                // Revert boolean to false. We are starting a new game.
                if (haveRestartedGame) haveRestartedGame = false;

            }
        };
        input.addAction(playAgain, KeyCode.ENTER);

        // Get all the tiles by pressing i
        UserAction info = new UserAction("info") {
            @Override
            protected void onActionBegin() {
                super.onActionBegin();
                System.out.println("\n\n================ INFO ================");
                System.out.println("[INFO] SIZE: " + tileTable.size());
                // Iterate all tiles and print their values.
                for (Object[] entiTile : tileTable){
                    Tile tile = (Tile) entiTile[1];
                    // Printing values.
                    System.out.println(tile.toString());
                }

                System.out.println("================ INFO ================\n\n");

            }
        }; input.addAction(info, KeyCode.I);


        // Move tiles up by pressing Arrow-Up
        UserAction moveUp = new UserAction("move_up") {
            @Override
            protected void onActionBegin() {
                super.onActionBegin();
                moveUp();
                updateScore();

                // Because spawnTile is about 100ms to spawn a tile and add it to tiletable, we need to slowdown the isGameOver check.
                getMasterTimer().runOnceAfter(()-> {
                    if (isGameOver()) gameOver();
                }, Duration.millis(150));

            }
        };
        input.addAction(moveUp, KeyCode.UP);


        // Move tiles to the right by pressing Arrow-Right
        UserAction moveRight = new UserAction("move_right") {
            @Override
            protected void onActionBegin() {
                super.onActionBegin();
                moveRight();
                updateScore();

                // Because spawnTile is about 100ms to spawn a tile and add it to tiletable, we need to slowdown the isGameOver check.
                getMasterTimer().runOnceAfter(()-> {
                    if (isGameOver()) gameOver();
                }, Duration.millis(150));
            }


        };
        input.addAction(moveRight, KeyCode.RIGHT);


        // Move tiles down by pressing Arrow-Down
        UserAction moveDown = new UserAction("move_down") {
            @Override
            protected void onActionBegin() {
                super.onActionBegin();
                moveDown();
                updateScore();

                // Because spawnTile is about 100ms to spawn a tile and add it to tiletable, we need to slowdown the isGameOver check.
                getMasterTimer().runOnceAfter(()-> {
                    if (isGameOver()) gameOver();
                }, Duration.millis(150));
            }
        };
        input.addAction(moveDown, KeyCode.DOWN);


        // Move tiles to the left by pressing Arrow-Left
        UserAction moveLeft = new UserAction("move_left") {
            @Override
            protected void onActionBegin() {
                super.onActionBegin();
                moveLeft();
                updateScore();

                // Because spawnTile is about 100ms to spawn a tile and add it to tiletable, we need to slowdown the isGameOver check.
                getMasterTimer().runOnceAfter(()-> {
                    if (isGameOver()) gameOver();
                }, Duration.millis(150));
            }
        };
        input.addAction(moveLeft, KeyCode.LEFT);


    }

    /**
     * Background initialize
     * The background is the original RGB color from the real game.
     */
    protected void initBackground(){
        // Init background
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

        // Init welcometext and add it the the view.
        Text welcomeText = new Text("Press \"space\" to start the game!");
        welcomeText.setFill(Color.web("#6E6E64"));
        welcomeText.setStyle("-fx-font: 16px bold; -fx-font-family: 'Arial Rounded MT Bold'");
        startGameText = Entities.builder().at(15,172).viewFromNode(welcomeText).buildAndAttach(getGameWorld());

    }

    /**
     * This is a map for our global values. Like the high -and currentscore.
     *
     */
    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("currentScoreValue", 0);
        vars.put("highestScoreValue", 0);
    }

    /**
     * Move up method
     *
     * <h3>Nested loop</h3>
     * The iteration starts at the following coordinates:
     * <li>X=0, Y=0</li>
     * <li>X=0, Y=1</li>
     * <li>X=0, Y=2</li>
     * <li>...</li>
     * <li>X=1, Y=0</li>
     * <li>X=1, Y=1</li>
     * <li>X=1, Y=2</li>
     *
     * This is because, it allows to move the tile upwards, without it needing to do it all over-again, until the tiles cannot move anymore.
     * In the XY nested iteration, there is also a for loop inside of that.
     * The nested-nested (inside of the XY nested loop) is a for loop to loop through all of the tiles there is active.
     * If there exist a tile at the given XY coordinate, verify if it can move up.
     * - Update the tile objects values to the new coordinates
     * - While it can move up, move the tile.
     *
     * There is added a intervalTimer to the translateY. This is because it allows the tile to make a gliding effect.
     * 1 tile width or height is 77px.
     * Math: 77px / 4 = 19.25px
     * Move 19.25px and do it max 4 times.
     *
     *
     * <h3>Do while Loop</h3>
     * First, iterate all of the tiles, XY values and move the tiles upwards.
     * Then merge upwards.
     * Then iterate and move again.
     *
     *
     * <h3>HaveMoved value</h3>
     * If a tile has been move or merged, then spawn a new tile to the board.
     */
    protected void moveUp(){
        // Initialize iteration integer
        int i = 0;
        // Initialize the switcher
        boolean mergeSwitch = true;
        // Assign the haveMoved check.
        haveMoved = false;
        do {
            // Iterate over every tile combination
            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {
                    // Iterate over each tile in tileTable
                    for (Object[] entiTile : tileTable){
                        Entity enti = (Entity) entiTile[0]; // Cast the object to Entity
                        Tile tile = (Tile) entiTile[1];     // Cast the object to Tile

                        // If tile match with the iterated XY value:
                        if ((tile.getYPos() == y) && (tile.getXPos() == x)){

                            // If can move, then update tile object and entity
                            while(canMove(tile.getXPos(), tile.getYPos()-1)){
                                tile.setYPos(tile.getYPos()-1);

                                /*
                                Create sliding effect.
                                Math: 77px / 4 = 19.25px
                                Move 19.25px and do it max 4 times.
                                */
                                getMasterTimer().runAtInterval(() -> {
                                    enti.translateY(-19.25);
                                }, Duration.millis(1), 4);
                                haveMoved = true;

                            }
                        }

                    }
                }
            }
            // Only make the merge once, and make it between the loop which there iterate 2x
            if (mergeSwitch) {
                // Somehow, the software needs to move all the tiles, then merge, then move again.
                mergeUp();
            }

            // Switch the merge
            mergeSwitch = false;

            // Add 1 to iteration integer.
            i++;
        } while (i<2);

        if (haveMoved) generateNewTile(false);


    }

    /**
     * Move Right method
     *
     * <h3>Nested loop</h3>
     * The iteration starts at the following coordinates:
     * <li>X=3, Y=0</li>
     * <li>X=2, Y=0</li>
     * <li>X=1, Y=0</li>
     * <li>...</li>
     * <li>X=3, Y=1</li>
     * <li>X=2, Y=1</li>
     * <li>X=1, Y=1</li>
     *
     * This is because, it allows to move the tile to the right, without it needing to do it all over-again, until the tiles cannot move anymore.
     * In the XY nested iteration, there is also a for loop inside of that.
     * The nested-nested (inside of the XY nested loop) is a for loop to loop through all of the tiles there is active.
     * If there exist a tile at the given XY coordinate, verify if it can move to the right.
     * - Update the tile objects values to the new coordinates
     * - While it can move to the right, move the tile.
     *
     * There is added a intervalTimer to the translateX. This is because it allows the tile to make a gliding effect.
     *
     *
     * <h3>Do while Loop</h3>
     * First, iterate all of the tiles, XY values and move the tiles to the right.
     * Then merge to the right.
     * Then iterate and move again.
     *
     *
     * <h3>HaveMoved value</h3>
     * If a tile has been move or merged, then spawn a new tile to the board.
     */
    protected void moveRight(){
        int i = 0;
        boolean mergeSwitch = true;
        haveMoved = false;
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

                                getMasterTimer().runAtInterval(() -> {
                                    enti.translateX(+19.25);
                                }, Duration.millis(1), 4);

                                haveMoved = true;

                            }



                        }
                    }
                }
            }
            // Only make the merge once, and make it between the loop which there iterate 2x
            if (mergeSwitch){
                // Somehow, the software needs to move all the tiles, then merge, then move again.
                mergeRight();
            }

            mergeSwitch = false;

            i++;
        } while (i<2);

        if (haveMoved) generateNewTile(false);
    }

    /**
     * Move Down method
     *
     * <h3>Nested loop</h3>
     * The iteration starts at the following coordinates:
     * <li>X=0, Y=3</li>
     * <li>X=0, Y=2</li>
     * <li>X=0, Y=1</li>
     * <li>...</li>
     * <li>X=1, Y=3</li>
     * <li>X=1, Y=2</li>
     * <li>X=1, Y=1</li>
     *
     * This is because, it allows to move the tile downwards, without it needing to do it all over-again, until the tiles cannot move anymore.
     * In the XY nested iteration, there is also a for loop inside of that.
     * The nested-nested (inside of the XY nested loop) is a for loop to loop through all of the tiles there is active.
     * If there exist a tile at the given XY coordinate, verify if it can move down.
     * - Update the tile objects values to the new coordinates
     * - While it can move down, move the tile.
     *
     * There is added a intervalTimer to the translateY. This is because it allows the tile to make a gliding effect.
     *
     *
     * <h3>Do while Loop</h3>
     * First, iterate all of the tiles, XY values and move the tiles down.
     * Then merge downwards.
     * Then iterate and move again.
     *
     *
     * <h3>HaveMoved value</h3>
     * If a tile has been move or merged, then spawn a new tile to the board.
     */
    protected void moveDown(){

        int i = 0;
        boolean mergeSwitch = true;
        haveMoved = false;
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

                                getMasterTimer().runAtInterval(() -> {
                                    enti.translateY(+19.25);
                                }, Duration.millis(1), 4);

                                haveMoved = true;

                            }



                        }
                    }
                }
            }
            // Only make the merge once, and make it between the loop which there iterate 2x
            if (mergeSwitch){
                // Somehow, the software needs to move all the tiles, then merge, then move again.
                mergeDown();
            }


            mergeSwitch = false;
            i++;
        } while (i<2);

        if (haveMoved) generateNewTile(false);

    }

    /**
     * Move Left method
     *
     * <h3>Nested loop</h3>
     * The iteration starts at the following coordinates:
     * <li>X=0, Y=0</li>
     * <li>X=1, Y=0</li>
     * <li>X=2, Y=0</li>
     * <li>...</li>
     * <li>X=1, Y=1</li>
     * <li>X=2, Y=1</li>
     * <li>X=3, Y=1</li>
     *
     * This is because, it allows to move the tile to the left, without it needing to do it all over-again, until the tiles cannot move anymore.
     * In the XY nested iteration, there is also a for loop inside of that.
     * The nested-nested (inside of the XY nested loop) is a for loop to loop through all of the tiles there is active.
     * If there exist a tile at the given XY coordinate, verify if it can move to the left.
     * - Update the tile objects values to the new coordinates
     * - While it can move to the left, move the tile.
     *
     * There is added a intervalTimer to the translateX. This is because it allows the tile to make a gliding effect.
     *
     *
     * <h3>Do while Loop</h3>
     * First, iterate all of the tiles, XY values and move the tiles to the left.
     * Then merge to the left.
     * Then iterate and move again.
     *
     *
     * <h3>HaveMoved value</h3>
     * If a tile has been move or merged, then spawn a new tile to the board.
     */
    protected void moveLeft(){


        int i = 0;
        boolean mergeSwitch = true;
        haveMoved = false;
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

                                getMasterTimer().runAtInterval(() -> {
                                    enti.translateX(-19.25);
                                }, Duration.millis(1), 4);

                                haveMoved = true;

                            }




                        }
                    }
                }
            }
            // Only make the merge once, and make it between the loop which there iterate 2x
            if (mergeSwitch){
                // Somehow, the software needs to move all the tiles, then merge, then move again.
                mergeLeft();
            }

            mergeSwitch = false;
            i++;
        } while (i<2);

        if (haveMoved) generateNewTile(false);


    }

    /**
     * Only called if there is no tiles available
     * @return true or false if tiles can merge
     */
    protected boolean canMerge(){

        // Checking if tiles on X can merge
        for (Object[] objX : tileTable) {
            Tile tileX = (Tile) objX[1];

            for (Object[] objX1 : tileTable) {
                Tile tileX1 = (Tile) objX1[1];

                if (tileX.getXPos() + 1 == 4) {
                    break;
                }

                if (tileX.getXPos() + 1 == tileX1.getXPos() && tileX.getYPos() == tileX1.getYPos() && tileX.getTv().getValue() == tileX1.getTv().getValue()) {
                    return true;
                }
            }
        }


        // Checking if tiles on Y can merge
        for (Object[] objY : tileTable){
            Tile tileY = (Tile) objY[1];

            for (Object[] objY1 : tileTable){
                Tile tileY1 = (Tile) objY1[1];

                if (tileY.getYPos()+1 == 4){
                    break;
                }

                if (tileY.getYPos()+1 == tileY1.getYPos() && tileY.getXPos() == tileY1.getXPos() && tileY.getTv().getValue() == tileY1.getTv().getValue()){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This method is using the same nested loop for searching for the correct tile, as it is using in the moveUp method.
     * In this case, before it can merge, the tile below and its current tile needs to share the same value.
     * So the first 3 for loops is searching for the correct tile. Then the next loop(the 4th) is trying to find a tile, there is below, and share the same value.
     * If it can be found, then begin to merge.
     *
     * First, it will make a temporary Object List. The element at index 0 is containing a new Entity, and the element at index 1 is containing the new Tile.
     * When the object[] has been created and added to tileTable, then remove the old objects.
     * Now, tell the global datafield, that a move has been made. Then it allows to generate a new tile.
     *
     */
    public void mergeUp(){
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                for (int i = 0; i < tileTable.size(); i++) {

                    // Get the iterated object list
                    Object[] currentObj = tileTable.get(i);
                    // Cast index 0 to Entity object
                    Entity enti = (Entity) currentObj[0];
                    // Cast index 1 to Tile object
                    Tile tile = (Tile) currentObj[1];

                    // Find a existing tile
                    if ((tile.getXPos() == x) && (tile.getYPos() == y)){

                        // Iterate every tile in tileTable to find a merge
                        for (int j = 0; j < tileTable.size(); j++) {
                            Object[] checkObj = tileTable.get(j);
                            Entity checkEnti = (Entity) checkObj[0];
                            Tile checkTile = (Tile) checkObj[1];


                            // Just double checking
                            if (((tile.getYPos() + 1) == checkTile.getYPos()) && (tile.getXPos() == checkTile.getXPos()) && (tile.getTv().getValue() == checkTile.getTv().getValue())){
                                // Now skip the tile, there is going to be merged's spot.
                                y++;

                                // merge the objects
                                int newValue = tile.getTv().getValue() + checkTile.getTv().getValue();
                                score.setCurrentScore(score.getCurrentScore()+newValue);

                                // Make temporary Tile
                                tempNewTile = new Tile(tile.getXPos(), tile.getYPos(), newValue);
                                // Make temporary Entity
                                tempNewEntity = tempNewTile.spawnTile();
                                // Add the objecs to the tileTable
                                tileTable.add(new Object[]{tempNewEntity, tempNewTile});

                                // Remove the old tiles from the world
                                enti.removeFromWorld();
                                checkEnti.removeFromWorld();
                                // Remove the olds from tileTable
                                tileTable.remove(currentObj);
                                tileTable.remove(checkObj);

                                // Tell program that a move has been made
                                haveMoved = true;

                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * This method is using the same nested loop for searching for the correct tile, as it is using in the moveRight method.
     * In this case, before it can merge, the tile to the left and its current tile needs to share the same value.
     * So the first 3 for loops is searching for the correct tile. Then the next loop(the 4th) is trying to find a tile, there is to the left, and share the same value.
     * If it can be found, then begin to merge.
     *
     * First, it will make a temporary Object List. The element at index 0 is containing a new Entity, and the element at index 1 is containing the new Tile.
     * When the object[] has been created and added to tileTable, then remove the old objects.
     * Now, tell the global datafield, that a move has been made. Then it allows to generate a new tile.
     *
     */
    public void mergeRight(){
        for (int y = 0; y < 4; y++) {
            for (int x = 3; x >= 0; x--) {
                for (int i = 0; i < tileTable.size(); i++) {

                    // Get the iterated object list
                    Object[] currentObj = tileTable.get(i);
                    // Cast index 0 to Entity object
                    Entity enti = (Entity) currentObj[0];
                    // Cast index 1 to Tile object
                    Tile tile = (Tile) currentObj[1];

                    // Find a existing tile
                    if ((tile.getXPos() == x) && (tile.getYPos() == y)){

                        // Iterate every tile in tileTable to find a merge
                        for (int j = 0; j < tileTable.size(); j++) {
                            Object[] checkObj = tileTable.get(j);
                            Entity checkEnti = (Entity) checkObj[0];
                            Tile checkTile = (Tile) checkObj[1];

                            // This checks if the tile-1 (left) is available, and they share the same Ypos and value
                            if (((tile.getXPos() - 1) == checkTile.getXPos()) && (tile.getYPos() == checkTile.getYPos()) && (tile.getTv().getValue() == checkTile.getTv().getValue())){
                                // Now skip the tile, there is going to be merged's spot.
                                x--;


                                // merge the objects
                                int newValue = tile.getTv().getValue() + checkTile.getTv().getValue();
                                score.setCurrentScore(score.getCurrentScore()+newValue);

                                // Make temporary Tile
                                tempNewTile = new Tile(tile.getXPos(), tile.getYPos(), newValue);
                                // Make temporary Entity
                                tempNewEntity = tempNewTile.spawnTile();
                                // Add the objects to tileTable
                                tileTable.add(new Object[]{tempNewEntity, tempNewTile});

                                // Remove the old tiles from the world
                                enti.removeFromWorld();
                                checkEnti.removeFromWorld();
                                // Remove the old tiles from tileTable
                                tileTable.remove(currentObj);
                                tileTable.remove(checkObj);

                                // Tell program that a move has been made
                                haveMoved = true;

                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * This method is using the same nested loop for searching for the correct tile, as it is using in the moveDown method.
     * In this case, before it can merge, the tile above and its current tile needs to share the same value.
     * So the first 3 for loops is searching for the correct tile. Then the next loop(the 4th) is trying to find a tile, there is above, and share the same value.
     * If it can be found, then begin to merge.
     *
     * First, it will make a temporary Object List. The element at index 0 is containing a new Entity, and the element at index 1 is containing the new Tile.
     * When the object[] has been created and added to tileTable, then remove the old objects.
     * Now, tell the global datafield, that a move has been made. Then it allows to generate a new tile.
     *
     */
    public void mergeDown(){
        for (int x = 0; x < 4; x++) {
            for (int y = 3; y >= 0; y--) {

                for (int i = 0; i < tileTable.size(); i++) {
                    // Get the iterated object list
                    Object[] currentObj = tileTable.get(i);
                    // Cast index 0 to Entity object
                    Entity enti = (Entity) currentObj[0];
                    // Cast index 1 to Tile object
                    Tile tile = (Tile) currentObj[1];

                    // Find a existing tile
                    if ((tile.getXPos() == x) && (tile.getYPos() == y)){

                        // Iterate every tile in tileTable to find a merge
                        for (int j = 0; j < tileTable.size(); j++) {
                            Object[] checkObj = tileTable.get(j);
                            Entity checkEnti = (Entity) checkObj[0];
                            Tile checkTile = (Tile) checkObj[1];

                            // This checks if the tile1 (above) is available, and they share the same X-value and number
                            if (((tile.getYPos() - 1) == checkTile.getYPos()) && (tile.getXPos() == checkTile.getXPos()) && (tile.getTv().getValue() == checkTile.getTv().getValue())){
                                // Now skip the tile, there is going to be merged's spot.
                                y--;


                                // merge the objects
                                int newValue = tile.getTv().getValue() + checkTile.getTv().getValue();
                                score.setCurrentScore(score.getCurrentScore()+newValue);

                                // Make temporary tile
                                tempNewTile = new Tile(tile.getXPos(), tile.getYPos(), newValue);
                                // Make temporary Entity
                                tempNewEntity = tempNewTile.spawnTile();
                                // Add the objects to to tileTable
                                tileTable.add(new Object[]{tempNewEntity, tempNewTile});

                                // Remove old tiles from the world
                                enti.removeFromWorld();
                                checkEnti.removeFromWorld();
                                // Remove old tiles from tileTable
                                tileTable.remove(currentObj);
                                tileTable.remove(checkObj);

                                // Tell program that a move has been made
                                haveMoved = true;

                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * This method is using the same nested loop for searching for the correct tile, as it is using in the moveLeft method.
     * In this case, before it can merge, the tile to the right and its current tile needs to share the same value.
     * So the first 3 for loops is searching for the correct tile. Then the next loop(the 4th) is trying to find a tile, there is to the right, and share the same value.
     * If it can be found, then begin to merge.
     *
     * First, it will make a temporary Object List. The element at index 0 is containing a new Entity, and the element at index 1 is containing the new Tile.
     * When the object[] has been created and added to tileTable, then remove the old objects.
     * Now, tell the global datafield, that a move has been made. Then it allows to generate a new tile.
     *
     */
    public void mergeLeft(){
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                for (int i = 0; i < tileTable.size(); i++) {

                    // Get the iterated object list
                    Object[] currentObj = tileTable.get(i);
                    // Cast index 0 to Entity object
                    Entity enti = (Entity) currentObj[0];
                    // Cast index 1 to Tile object
                    Tile tile = (Tile) currentObj[1];

                    // Find a existing tile
                    if ((tile.getXPos() == x) && (tile.getYPos() == y)){

                        // Iterate every tile in tileTable to find a merge
                        for (int j = 0; j < tileTable.size(); j++) {
                            Object[] checkObj = tileTable.get(j);
                            Entity checkEnti = (Entity) checkObj[0];
                            Tile checkTile = (Tile) checkObj[1];

                            // This checks if the tile+1 (right) is available, and they share the same Ypos and value
                            if (((tile.getXPos() + 1) == checkTile.getXPos()) && (tile.getYPos() == checkTile.getYPos()) && (tile.getTv().getValue() == checkTile.getTv().getValue())){
                                x++;
                                // merge the objects
                                int newValue = tile.getTv().getValue() + checkTile.getTv().getValue();
                                score.setCurrentScore(score.getCurrentScore()+newValue);

                                // Make temporary Tile
                                tempNewTile = new Tile(tile.getXPos(), tile.getYPos(), newValue);
                                // Make temporary Entity
                                tempNewEntity = tempNewTile.spawnTile();
                                // Add the objects to tileTable
                                tileTable.add(new Object[]{tempNewEntity, tempNewTile});

                                // Remove old tiles from world
                                enti.removeFromWorld();
                                checkEnti.removeFromWorld();
                                // Remove old tiles from tileTable
                                tileTable.remove(currentObj);
                                tileTable.remove(checkObj);

                                // Tell program that a move has been made
                                haveMoved = true;

                            }
                        }
                    }
                }
            }
        }
    }

    /**
     *
     * @param toX The wanted x value you want to move to.
     * @param toY The wanted y value you want to move to.
     * @return true if tile can move there and false is the spot has been taken.
     */
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

    /**
     * If board is filled with 16 tiles, it cannot merge anymore and the game hasn't been restarted yet.
     * @return true if the game is over and false if user can either move or merge.
     */
    public boolean isGameOver(){
        if (tileTable.size() == 16 && !canMerge() && !haveRestartedGame){
            return true;
        }
        return tileTable.stream().map(obj -> (Tile) obj[1]).anyMatch(tile -> tile.getTv().getValue() == 2048);
    }

    /**
     * Creates a overlay to tell the user, that the game is over.
     */
    public void gameOver(){
        // Not yet, it is a check in initInput, so isGameOver isn't being called if the user tries to move a tile, when game is over.
        haveRestartedGame = true;
        Rectangle gameOverRect = new Rectangle(360,600, Color.rgb(255,255,255,0.8));
        Text gameOverText = new Text("Game over... \nPress enter to play again");
        gameOverText.setTextAlignment(TextAlignment.CENTER);
        gameOverText.setStyle("-fx-font: 26px bold; -fx-font-family: 'Arial Rounded MT Bold'");
        gameOverText.setFill(Color.rgb(236,196,0));

        StackPane stack = new StackPane();
        stack.getChildren().addAll(gameOverRect, gameOverText);
        gameOver = stack;
        // add stackpane to UI.
        getGameScene().addUINode(gameOver);

    }

    /**
     * First, transfer all of the active tiles to a takenTiles Array.
     *
     * Then add all of the tile-spots combinations to an array called tilesAvailable
     * Then check if there already exist a tile in the available spot.
     * If there exist a tile, then remove the available option and tell that this spot is already taken.
     *
     *
     * @return an ArrayList for available spots, where new tiles can be spawned to.
     */
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

    /**
     * Generate a new tile object and Entity object, then add it to our tileTable ArrayList.
     * @param isStarting Is this a starting call? Do it need to spawn 2 tiles or just 1. True for 2 tiles and false for 1 tile.
     */
    public void generateNewTile(boolean isStarting){

        getMasterTimer().runOnceAfter(() -> {
            int max = 1;
            if (isStarting) max = 2;
            int rndValue = new Random().nextDouble() < 0.9 ? 2 : 4;

            for (int i = 0; i < max; i++) {
                if (!getAvailableSpots().isEmpty()){
                    int rndI = new Random().nextInt(getAvailableSpots().size());
                    Object[] rndObj = getAvailableSpots().get(rndI);

                        // code to run once after 1 second
                        Tile tile = new Tile((int) rndObj[0], (int) rndObj[1], 2);
                        tileEntity = tile.spawnTile();
                        tileTable.add(new Object[]{tileEntity, tile});
                }
            }
        }, Duration.millis(100));










    }

    /**
     * Update the current and highscore
     */
    public void updateScore(){
        getGameState().setValue("currentScoreValue", score.getCurrentScore());
        getGameState().setValue("highestScoreValue", score.getHighScore());
    }

    /**
     * Launch the game.
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}