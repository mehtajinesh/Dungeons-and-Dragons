package controllertesting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import game.controller.DungeonControllerConsole;
import game.controller.DungeonControllerGui;
import game.controller.IConsoleDungeonController;
import game.controller.IDungeonControllerGui;
import game.model.DungeonsGame;
import game.model.DungeonsGameImpl;
import game.model.RandomNumberGenerator;
import game.model.RandomNumberGeneratorImpl;
import game.view.GameView;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;


/**
 * This class is used to test the controller functionalities of starting the game.
 * This includes testing different situations on how the player will play the game.
 */
public class DungeonControllerTest {

  /**
   * This function is used to show that a player can win a game.
   */
  @Test
  public void testPlayerWinGame() {
    StringBuffer output = new StringBuffer();
    Reader input = new StringReader("M WEST M WEST M WEST M "
            + "SOUTH M EAST S EAST 1 S EAST 1 M EAST M EAST");
    //create randomizer
    //description of randomizer values
    // row one and two -> path pick up in kruskal
    // row three -> start end selection
    // row four -> treasure values
    // row five -> monster creation
    // row six -> arrow creation
    RandomNumberGenerator randomizer = new RandomNumberGeneratorImpl(
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            3, 0,
            0, 1, 1, 1, 1, 2,
            5,
            0, 2, 1, 2, 2, 2, 3, 2
    );
    //create model
    DungeonsGame dungeonsGame = new DungeonsGameImpl(DungeonsGame.WrapType.NON_WRAPPING, 4, 4,
            0, 20, 2, randomizer);
    IConsoleDungeonController controller = new DungeonControllerConsole(input,
            output, dungeonsGame);
    controller.playGame();
    String expectedOutput =
            "Hey Player-1. Let's start the game.\n"
                    + "Player's Current Location: Cell-04\n"
                    + "Player's Current Location Type: CAVE\n"
                    + "Available Treasure:\n"
                    + "RUBIES\n"
                    + "Available Arrows:2\n"
                    + "No Monster Available\n"
                    + "Available Direction For Movement:\n"
                    + "WEST : Cell-03\n"
                    + "What would you like to do?\n"
                    + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
                    + "Which direction do you want to explore?\n"
                    + "Options - [WEST]?\n"
                    + "-----------------------------------------------\n"
                    + "Player's Current Location: Cell-03\n"
                    + "Player's Current Location Type: TUNNEL\n"
                    + "Available Treasure:\n"
                    + "None\n"
                    + "Available Arrows:2\n"
                    + "No Monster Available\n"
                    + "Available Direction For Movement:\n"
                    + "EAST : Cell-04\n"
                    + "WEST : Cell-02\n"
                    + "What would you like to do?\n"
                    + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
                    + "Which direction do you want to explore?\n"
                    + "Options - [EAST,WEST]?\n"
                    + "-----------------------------------------------\n"
                    + "Player's Current Location: Cell-02\n"
                    + "Player's Current Location Type: TUNNEL\n"
                    + "Available Treasure:\n"
                    + "None\n"
                    + "Available Arrows:2\n"
                    + "No Monster Available\n"
                    + "Available Direction For Movement:\n"
                    + "EAST : Cell-03\n"
                    + "WEST : Cell-01\n"
                    + "What would you like to do?\n"
                    + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
                    + "Which direction do you want to explore?\n"
                    + "Options - [EAST,WEST]?\n"
                    + "-----------------------------------------------\n"
                    + "Player's Current Location: Cell-01\n"
                    + "Player's Current Location Type: TUNNEL\n"
                    + "Available Treasure:\n"
                    + "None\n"
                    + "Available Arrows:2\n"
                    + "No Monster Available\n"
                    + "Available Direction For Movement:\n"
                    + "SOUTH : Cell-05\n"
                    + "EAST : Cell-02\n"
                    + "What would you like to do?\n"
                    + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
                    + "Which direction do you want to explore?\n"
                    + "Options - [EAST,SOUTH]?\n"
                    + "-----------------------------------------------\n"
                    + "Player's Current Location: Cell-05\n"
                    + "Player's Current Location Type: CAVE\n"
                    + "Available Treasure:\n"
                    + "SAPPHIRES\n"
                    + "Available Arrows:0\n"
                    + "No Monster Available\n"
                    + "Available Direction For Movement:\n"
                    + "NORTH : Cell-01\n"
                    + "SOUTH : Cell-09\n"
                    + "EAST : Cell-06\n"
                    + "What would you like to do?\n"
                    + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
                    + "Which direction do you want to explore?\n"
                    + "Options - [EAST,NORTH,SOUTH]?\n"
                    + "-----------------------------------------------\n"
                    + "Warning! Something looks fishy. Potential monsters nearby\n"
                    + "Player's Current Location: Cell-06\n"
                    + "Player's Current Location Type: TUNNEL\n"
                    + "Available Treasure:\n"
                    + "None\n"
                    + "Available Arrows:0\n"
                    + "No Monster Available\n"
                    + "Available Direction For Movement:\n"
                    + "EAST : Cell-07\n"
                    + "WEST : Cell-05\n"
                    + "What would you like to do?\n"
                    + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
                    + "Which direction to shoot arrow?\n"
                    + "Options - [NORTH,SOUTH,EAST,WEST]?What distance "
                    + "in range 1 to 10 inclusive?\n"
                    + "Nice shot. That's the sounds of success.\n"
                    + "\n"
                    + "-----------------------------------------------\n"
                    + "Warning! Something looks fishy. Potential monsters nearby\n"
                    + "Player's Current Location: Cell-06\n"
                    + "Player's Current Location Type: TUNNEL\n"
                    + "Available Treasure:\n"
                    + "None\n"
                    + "Available Arrows:0\n"
                    + "No Monster Available\n"
                    + "Available Direction For Movement:\n"
                    + "EAST : Cell-07\n"
                    + "WEST : Cell-05\n"
                    + "What would you like to do?\n"
                    + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
                    + "Which direction to shoot arrow?\n"
                    + "Options - [NORTH,SOUTH,EAST,WEST]?What distance "
                    + "in range 1 to 10 inclusive?\n"
                    + "Awesome shot. One monster less to deal with.\n"
                    + "\n"
                    + "-----------------------------------------------\n"
                    + "Player's Current Location: Cell-06\n"
                    + "Player's Current Location Type: TUNNEL\n"
                    + "Available Treasure:\n"
                    + "None\n"
                    + "Available Arrows:0\n"
                    + "No Monster Available\n"
                    + "Available Direction For Movement:\n"
                    + "EAST : Cell-07\n"
                    + "WEST : Cell-05\n"
                    + "What would you like to do?\n"
                    + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
                    + "Which direction do you want to explore?\n"
                    + "Options - [EAST,WEST]?\n"
                    + "-----------------------------------------------\n"
                    + "Player's Current Location: Cell-07\n"
                    + "Player's Current Location Type: TUNNEL\n"
                    + "Available Treasure:\n"
                    + "None\n"
                    + "Available Arrows:0\n"
                    + "No Monster Available\n"
                    + "Available Direction For Movement:\n"
                    + "EAST : Cell-08\n"
                    + "WEST : Cell-06\n"
                    + "What would you like to do?\n"
                    + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
                    + "Which direction do you want to explore?\n"
                    + "Options - [EAST,WEST]?\n"
                    + "-----------------------------------------------\n"
                    + "You are a winner. Here is a summary of the player:\n"
                    + "Player Name: Player-1\n"
                    + "Treasure Information: \n"
                    + "None\n"
                    + "Traversed Path: \n"
                    + "Cell-04\n"
                    + "Cell-03\n"
                    + "Cell-02\n"
                    + "Cell-01\n"
                    + "Cell-05\n"
                    + "Cell-06\n"
                    + "Cell-07\n"
                    + "Cell-08\n"
                    + "Available Arrows: 1\n";
    assertEquals(expectedOutput, output.toString());
  }

  /**
   * This function is used to show that a player can lose a game by
   * getting eaten up.
   */
  @Test
  public void testPlayerLoseGame() {
    StringBuffer output = new StringBuffer();
    Reader input = new StringReader("P Treasure M WEST M WEST M "
            + "WEST M SOUTH P Treasure M EAST M EAST M EAST");
    //create randomizer
    //description of randomizer values
    // row one and two -> path pick up in kruskal
    // row three -> start end selection
    // row four -> treasure values
    // row five -> monster creation
    // row six -> arrow creation
    RandomNumberGenerator randomizer = new RandomNumberGeneratorImpl(
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            3, 0,
            0, 1, 1, 1, 1, 2,
            5,
            0, 2, 1, 2, 2, 2, 3, 2
    );
    //create model
    DungeonsGame dungeonsGame = new DungeonsGameImpl(DungeonsGame.WrapType.NON_WRAPPING, 4, 4,
            0, 20, 2, randomizer);
    IConsoleDungeonController controller = new DungeonControllerConsole(input, output,
            dungeonsGame);
    controller.playGame();
    String expectedOutput =
            "Hey Player-1. Let's start the game.\n"
                    + "Player's Current Location: Cell-04\n"
                    + "Player's Current Location Type: CAVE\n"
                    + "Available Treasure:\n"
                    + "RUBIES\n"
                    + "Available Arrows:2\n"
                    + "No Monster Available\n"
                    + "Available Direction For Movement:\n"
                    + "WEST : Cell-03\n"
                    + "What would you like to do?\n"
                    + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
                    + "Which do you want to pick up?\n"
                    + "Options - [Treasure,Arrow]?Player successfully picked"
                    + " up all the treasures in the location\n"
                    + "\n"
                    + "-----------------------------------------------\n"
                    + "Player's Current Location: Cell-04\n"
                    + "Player's Current Location Type: CAVE\n"
                    + "Available Treasure:\n"
                    + "None\n"
                    + "Available Arrows:2\n"
                    + "No Monster Available\n"
                    + "Available Direction For Movement:\n"
                    + "WEST : Cell-03\n"
                    + "What would you like to do?\n"
                    + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
                    + "Which direction do you want to explore?\n"
                    + "Options - [WEST]?\n"
                    + "-----------------------------------------------\n"
                    + "Player's Current Location: Cell-03\n"
                    + "Player's Current Location Type: TUNNEL\n"
                    + "Available Treasure:\n"
                    + "None\n"
                    + "Available Arrows:2\n"
                    + "No Monster Available\n"
                    + "Available Direction For Movement:\n"
                    + "EAST : Cell-04\n"
                    + "WEST : Cell-02\n"
                    + "What would you like to do?\n"
                    + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
                    + "Which direction do you want to explore?\n"
                    + "Options - [EAST,WEST]?\n"
                    + "-----------------------------------------------\n"
                    + "Player's Current Location: Cell-02\n"
                    + "Player's Current Location Type: TUNNEL\n"
                    + "Available Treasure:\n"
                    + "None\n"
                    + "Available Arrows:2\n"
                    + "No Monster Available\n"
                    + "Available Direction For Movement:\n"
                    + "EAST : Cell-03\n"
                    + "WEST : Cell-01\n"
                    + "What would you like to do?\n"
                    + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
                    + "Which direction do you want to explore?\n"
                    + "Options - [EAST,WEST]?\n"
                    + "-----------------------------------------------\n"
                    + "Player's Current Location: Cell-01\n"
                    + "Player's Current Location Type: TUNNEL\n"
                    + "Available Treasure:\n"
                    + "None\n"
                    + "Available Arrows:2\n"
                    + "No Monster Available\n"
                    + "Available Direction For Movement:\n"
                    + "SOUTH : Cell-05\n"
                    + "EAST : Cell-02\n"
                    + "What would you like to do?\n"
                    + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
                    + "Which direction do you want to explore?\n"
                    + "Options - [EAST,SOUTH]?\n"
                    + "-----------------------------------------------\n"
                    + "Player's Current Location: Cell-05\n"
                    + "Player's Current Location Type: CAVE\n"
                    + "Available Treasure:\n"
                    + "SAPPHIRES\n"
                    + "Available Arrows:0\n"
                    + "No Monster Available\n"
                    + "Available Direction For Movement:\n"
                    + "NORTH : Cell-01\n"
                    + "SOUTH : Cell-09\n"
                    + "EAST : Cell-06\n"
                    + "What would you like to do?\n"
                    + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
                    + "Which do you want to pick up?\n"
                    + "Options - [Treasure]?Player successfully picked up "
                    + "all the treasures in the location\n"
                    + "\n"
                    + "-----------------------------------------------\n"
                    + "Player's Current Location: Cell-05\n"
                    + "Player's Current Location Type: CAVE\n"
                    + "Available Treasure:\n"
                    + "None\n"
                    + "Available Arrows:0\n"
                    + "No Monster Available\n"
                    + "Available Direction For Movement:\n"
                    + "NORTH : Cell-01\n"
                    + "SOUTH : Cell-09\n"
                    + "EAST : Cell-06\n"
                    + "What would you like to do?\n"
                    + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
                    + "Which direction do you want to explore?\n"
                    + "Options - [EAST,NORTH,SOUTH]?\n"
                    + "-----------------------------------------------\n"
                    + "Warning! Something looks fishy. Potential monsters nearby\n"
                    + "Player's Current Location: Cell-06\n"
                    + "Player's Current Location Type: TUNNEL\n"
                    + "Available Treasure:\n"
                    + "None\n"
                    + "Available Arrows:0\n"
                    + "No Monster Available\n"
                    + "Available Direction For Movement:\n"
                    + "EAST : Cell-07\n"
                    + "WEST : Cell-05\n"
                    + "What would you like to do?\n"
                    + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
                    + "Which direction do you want to explore?\n"
                    + "Options - [EAST,WEST]?\n"
                    + "-----------------------------------------------\n"
                    + "Red Alert!!! Monsters incoming\n"
                    + "Player's Current Location: Cell-07\n"
                    + "Player's Current Location Type: TUNNEL\n"
                    + "Available Treasure:\n"
                    + "None\n"
                    + "Available Arrows:0\n"
                    + "No Monster Available\n"
                    + "Available Direction For Movement:\n"
                    + "EAST : Cell-08\n"
                    + "WEST : Cell-06\n"
                    + "What would you like to do?\n"
                    + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
                    + "Which direction do you want to explore?\n"
                    + "Options - [EAST,WEST]?\n"
                    + "-----------------------------------------------\n"
                    + "Sorry. You are dead. Better luck next time. "
                    + "Here is a summary of the player:\n"
                    + "Player Name: Player-1\n"
                    + "Treasure Information: \n"
                    + "RUBIES:1\n"
                    + "SAPPHIRES:1\n"
                    + "Traversed Path: \n"
                    + "Cell-04\n"
                    + "Cell-03\n"
                    + "Cell-02\n"
                    + "Cell-01\n"
                    + "Cell-05\n"
                    + "Cell-06\n"
                    + "Cell-07\n"
                    + "Cell-08\n"
                    + "Available Arrows: 3\n";
    assertEquals(expectedOutput, output.toString());
  }

  /**
   * Test to show pick up treasure works.
   */
  @Test
  public void testPickupTreasure() {
    StringBuffer output = new StringBuffer();
    Reader input = new StringReader("P Treasure M WEST M WEST M "
            + "WEST M SOUTH P Treasure M EAST M EAST M EAST");
    //create randomizer
    //description of randomizer values
    // row one and two -> path pick up in kruskal
    // row three -> start end selection
    // row four -> treasure values
    // row five -> monster creation
    // row six -> arrow creation
    RandomNumberGenerator randomizer = new RandomNumberGeneratorImpl(
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            3, 0,
            0, 1, 1, 1, 1, 2,
            5,
            0, 2, 1, 2, 2, 2, 3, 2
    );
    //create model
    DungeonsGame dungeonsGame = new DungeonsGameImpl(DungeonsGame.WrapType.NON_WRAPPING, 4, 4,
            0, 20, 2, randomizer);
    IConsoleDungeonController controller = new DungeonControllerConsole(input, output,
            dungeonsGame);
    controller.playGame();
    String treasurePickup1ExpectedText = "Player's Current Location: Cell-04\n"
            + "Player's Current Location Type: CAVE\n"
            + "Available Treasure:\n"
            + "RUBIES\n"
            + "Available Arrows:2\n"
            + "No Monster Available\n"
            + "Available Direction For Movement:\n"
            + "WEST : Cell-03\n"
            + "What would you like to do?\n"
            + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
            + "Which do you want to pick up?\n"
            + "Options - [Treasure,Arrow]?Player successfully picked"
            + " up all the treasures in the location\n";
    assertTrue(output.toString().contains(treasurePickup1ExpectedText));
    String treasurePickup2ExpectedText = "Player's Current Location: Cell-05\n"
            + "Player's Current Location Type: CAVE\n"
            + "Available Treasure:\n"
            + "SAPPHIRES\n"
            + "Available Arrows:0\n"
            + "No Monster Available\n"
            + "Available Direction For Movement:\n"
            + "NORTH : Cell-01\n"
            + "SOUTH : Cell-09\n"
            + "EAST : Cell-06\n"
            + "What would you like to do?\n"
            + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
            + "Which do you want to pick up?\n"
            + "Options - [Treasure]?Player successfully picked up "
            + "all the treasures in the location\n";
    assertTrue(output.toString().contains(treasurePickup2ExpectedText));
    String playerSummaryExpected = "Here is a summary of the player:\n"
            + "Player Name: Player-1\n"
            + "Treasure Information: \n"
            + "RUBIES:1\n"
            + "SAPPHIRES:1\n";
    assertTrue(output.toString().contains(playerSummaryExpected));
  }

  /**
   * Test to show pick up arrow works.
   */
  @Test
  public void testPickupArrow() {
    StringBuffer output = new StringBuffer();
    Reader input = new StringReader("P Arrow M WEST P Arrow"
            + " M WEST P Arrow M WEST P Arrow M SOUTH M EAST M EAST"
            + " M EAST");
    //create randomizer
    //description of randomizer values
    // row one and two -> path pick up in kruskal
    // row three -> start end selection
    // row four -> treasure values
    // row five -> monster creation
    // row six -> arrow creation
    RandomNumberGenerator randomizer = new RandomNumberGeneratorImpl(
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            3, 0,
            0, 1, 1, 1, 1, 2,
            5,
            0, 2, 1, 2, 2, 2, 3, 2
    );
    //create model
    DungeonsGame dungeonsGame = new DungeonsGameImpl(DungeonsGame.WrapType.NON_WRAPPING, 4, 4,
            0, 20, 2, randomizer);
    IConsoleDungeonController controller = new DungeonControllerConsole(input, output,
            dungeonsGame);
    controller.playGame();
    String arrowPickup1ExpectedText = "Player's Current Location: Cell-04\n"
            + "Player's Current Location Type: CAVE\n"
            + "Available Treasure:\n"
            + "RUBIES\n"
            + "Available Arrows:2\n"
            + "No Monster Available\n"
            + "Available Direction For Movement:\n"
            + "WEST : Cell-03\n"
            + "What would you like to do?\n"
            + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
            + "Which do you want to pick up?\n"
            + "Options - [Treasure,Arrow]?Player successfully picked up "
            + "all the arrows in the location\n"
            + "\n";
    assertTrue(output.toString().contains(arrowPickup1ExpectedText));
  }

  /**
   * Test to show miss arrow works.
   */
  @Test
  public void testMissHitArrow() {
    StringBuffer output = new StringBuffer();
    Reader input = new StringReader("S NORTH 2 S SOUTH 2 S EAST 2 S"
            + " P Arrow M WEST P Arrow"
            + " M WEST P Arrow M WEST P Arrow M SOUTH M EAST M EAST"
            + " M EAST");
    //create randomizer
    //description of randomizer values
    // row one and two -> path pick up in kruskal
    // row three -> start end selection
    // row four -> treasure values
    // row five -> monster creation
    // row six -> arrow creation
    RandomNumberGenerator randomizer = new RandomNumberGeneratorImpl(
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            3, 0,
            0, 1, 1, 1, 1, 2,
            5,
            0, 2, 1, 2, 2, 2, 3, 2
    );
    //create model
    DungeonsGame dungeonsGame = new DungeonsGameImpl(DungeonsGame.WrapType.NON_WRAPPING, 4, 4,
            0, 20, 2, randomizer);
    IConsoleDungeonController controller = new DungeonControllerConsole(input, output,
            dungeonsGame);
    controller.playGame();
    String missedArrow1ExpectedText = "Player's Current Location: Cell-04\n"
            + "Player's Current Location Type: CAVE\n"
            + "Available Treasure:\n"
            + "RUBIES\n"
            + "Available Arrows:2\n"
            + "No Monster Available\n"
            + "Available Direction For Movement:\n"
            + "WEST : Cell-03\n"
            + "What would you like to do?\n"
            + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
            + "Which direction to shoot arrow?\n"
            + "Options - [NORTH,SOUTH,EAST,WEST]?What distance in range 1 to 10 inclusive?\n"
            + "Bad shot. Better luck next time.\n";
    assertTrue(output.toString().contains(missedArrow1ExpectedText));
  }

  /**
   * This function is used to test arrow shot if missed is working, arrow pick up is working
   * and arrow empty works correctly or not.
   */
  @Test
  public void testArrowMissPickupEmpty() {
    StringBuffer output = new StringBuffer();
    Reader input = new StringReader("S NORTH 2 S SOUTH 2 S EAST 2 S"
            + " P Arrow M WEST P Arrow"
            + " M WEST P Arrow M WEST P Arrow M SOUTH M EAST M EAST"
            + " M EAST");
    //create randomizer
    //description of randomizer values
    // row one and two -> path pick up in kruskal
    // row three -> start end selection
    // row four -> treasure values
    // row five -> monster creation
    // row six -> arrow creation
    RandomNumberGenerator randomizer = new RandomNumberGeneratorImpl(
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            3, 0,
            0, 1, 1, 1, 1, 2,
            5,
            0, 2, 1, 2, 2, 2, 3, 2
    );
    //create model
    DungeonsGame dungeonsGame = new DungeonsGameImpl(DungeonsGame.WrapType.NON_WRAPPING, 4, 4,
            0, 20, 2, randomizer);
    IConsoleDungeonController controller = new DungeonControllerConsole(input, output,
            dungeonsGame);
    controller.playGame();
    String expectedOutput =
            "Hey Player-1. Let's start the game.\n"
                    + "Player's Current Location: Cell-04\n"
                    + "Player's Current Location Type: CAVE\n"
                    + "Available Treasure:\n"
                    + "RUBIES\n"
                    + "Available Arrows:2\n"
                    + "No Monster Available\n"
                    + "Available Direction For Movement:\n"
                    + "WEST : Cell-03\n"
                    + "What would you like to do?\n"
                    + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
                    + "Which direction to shoot arrow?\n"
                    + "Options - [NORTH,SOUTH,EAST,WEST]?"
                    + "What distance in range 1 to 10 inclusive?\n"
                    + "Bad shot. Better luck next time.\n"
                    + "\n"
                    + "-----------------------------------------------\n"
                    + "Player's Current Location: Cell-04\n"
                    + "Player's Current Location Type: CAVE\n"
                    + "Available Treasure:\n"
                    + "RUBIES\n"
                    + "Available Arrows:2\n"
                    + "No Monster Available\n"
                    + "Available Direction For Movement:\n"
                    + "WEST : Cell-03\n"
                    + "What would you like to do?\n"
                    + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
                    + "Which direction to shoot arrow?\n"
                    + "Options - [NORTH,SOUTH,EAST,WEST]?"
                    + "What distance in range 1 to 10 inclusive?\n"
                    + "Bad shot. Better luck next time.\n"
                    + "\n"
                    + "-----------------------------------------------\n"
                    + "Player's Current Location: Cell-04\n"
                    + "Player's Current Location Type: CAVE\n"
                    + "Available Treasure:\n"
                    + "RUBIES\n"
                    + "Available Arrows:2\n"
                    + "No Monster Available\n"
                    + "Available Direction For Movement:\n"
                    + "WEST : Cell-03\n"
                    + "What would you like to do?\n"
                    + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
                    + "Which direction to shoot arrow?\n"
                    + "Options - [NORTH,SOUTH,EAST,WEST]?"
                    + "What distance in range 1 to 10 inclusive?\n"
                    + "Bad shot. Better luck next time.\n"
                    + "Looks like you are out of arrows. Start searching for more arrows.\n"
                    + "\n"
                    + "-----------------------------------------------\n"
                    + "Player's Current Location: Cell-04\n"
                    + "Player's Current Location Type: CAVE\n"
                    + "Available Treasure:\n"
                    + "RUBIES\n"
                    + "Available Arrows:2\n"
                    + "No Monster Available\n"
                    + "Available Direction For Movement:\n"
                    + "WEST : Cell-03\n"
                    + "What would you like to do?\n"
                    + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
                    + "Looks like you are out of arrows. "
                    + "Start searching for more arrows and then shoot.\n"
                    + "\n"
                    + "-----------------------------------------------\n"
                    + "Player's Current Location: Cell-04\n"
                    + "Player's Current Location Type: CAVE\n"
                    + "Available Treasure:\n"
                    + "RUBIES\n"
                    + "Available Arrows:2\n"
                    + "No Monster Available\n"
                    + "Available Direction For Movement:\n"
                    + "WEST : Cell-03\n"
                    + "What would you like to do?\n"
                    + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
                    + "Which do you want to pick up?\n"
                    + "Options - [Treasure,Arrow]?Player "
                    + "successfully picked up all the arrows in the location\n"
                    + "\n"
                    + "-----------------------------------------------\n"
                    + "Player's Current Location: Cell-04\n"
                    + "Player's Current Location Type: CAVE\n"
                    + "Available Treasure:\n"
                    + "RUBIES\n"
                    + "Available Arrows:0\n"
                    + "No Monster Available\n"
                    + "Available Direction For Movement:\n"
                    + "WEST : Cell-03\n"
                    + "What would you like to do?\n"
                    + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
                    + "Which direction do you want to explore?\n"
                    + "Options - [WEST]?\n"
                    + "-----------------------------------------------\n"
                    + "Player's Current Location: Cell-03\n"
                    + "Player's Current Location Type: TUNNEL\n"
                    + "Available Treasure:\n"
                    + "None\n"
                    + "Available Arrows:2\n"
                    + "No Monster Available\n"
                    + "Available Direction For Movement:\n"
                    + "EAST : Cell-04\n"
                    + "WEST : Cell-02\n"
                    + "What would you like to do?\n"
                    + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
                    + "Which do you want to pick up?\n"
                    + "Options - [Arrow]?Player successfully picked "
                    + "up all the arrows in the location\n"
                    + "\n"
                    + "-----------------------------------------------\n"
                    + "Player's Current Location: Cell-03\n"
                    + "Player's Current Location Type: TUNNEL\n"
                    + "Available Treasure:\n"
                    + "None\n"
                    + "Available Arrows:0\n"
                    + "No Monster Available\n"
                    + "Available Direction For Movement:\n"
                    + "EAST : Cell-04\n"
                    + "WEST : Cell-02\n"
                    + "What would you like to do?\n"
                    + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
                    + "Which direction do you want to explore?\n"
                    + "Options - [EAST,WEST]?\n"
                    + "-----------------------------------------------\n"
                    + "Player's Current Location: Cell-02\n"
                    + "Player's Current Location Type: TUNNEL\n"
                    + "Available Treasure:\n"
                    + "None\n"
                    + "Available Arrows:2\n"
                    + "No Monster Available\n"
                    + "Available Direction For Movement:\n"
                    + "EAST : Cell-03\n"
                    + "WEST : Cell-01\n"
                    + "What would you like to do?\n"
                    + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
                    + "Which do you want to pick up?\n"
                    + "Options - [Arrow]?Player successfully "
                    + "picked up all the arrows in the location\n"
                    + "\n"
                    + "-----------------------------------------------\n"
                    + "Player's Current Location: Cell-02\n"
                    + "Player's Current Location Type: TUNNEL\n"
                    + "Available Treasure:\n"
                    + "None\n"
                    + "Available Arrows:0\n"
                    + "No Monster Available\n"
                    + "Available Direction For Movement:\n"
                    + "EAST : Cell-03\n"
                    + "WEST : Cell-01\n"
                    + "What would you like to do?\n"
                    + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
                    + "Which direction do you want to explore?\n"
                    + "Options - [EAST,WEST]?\n"
                    + "-----------------------------------------------\n"
                    + "Player's Current Location: Cell-01\n"
                    + "Player's Current Location Type: TUNNEL\n"
                    + "Available Treasure:\n"
                    + "None\n"
                    + "Available Arrows:2\n"
                    + "No Monster Available\n"
                    + "Available Direction For Movement:\n"
                    + "SOUTH : Cell-05\n"
                    + "EAST : Cell-02\n"
                    + "What would you like to do?\n"
                    + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
                    + "Which do you want to pick up?\n"
                    + "Options - [Arrow]?Player successfully picked "
                    + "up all the arrows in the location\n"
                    + "\n"
                    + "-----------------------------------------------\n"
                    + "Player's Current Location: Cell-01\n"
                    + "Player's Current Location Type: TUNNEL\n"
                    + "Available Treasure:\n"
                    + "None\n"
                    + "Available Arrows:0\n"
                    + "No Monster Available\n"
                    + "Available Direction For Movement:\n"
                    + "SOUTH : Cell-05\n"
                    + "EAST : Cell-02\n"
                    + "What would you like to do?\n"
                    + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
                    + "Which direction do you want to explore?\n"
                    + "Options - [EAST,SOUTH]?\n"
                    + "-----------------------------------------------\n"
                    + "Player's Current Location: Cell-05\n"
                    + "Player's Current Location Type: CAVE\n"
                    + "Available Treasure:\n"
                    + "SAPPHIRES\n"
                    + "Available Arrows:0\n"
                    + "No Monster Available\n"
                    + "Available Direction For Movement:\n"
                    + "NORTH : Cell-01\n"
                    + "SOUTH : Cell-09\n"
                    + "EAST : Cell-06\n"
                    + "What would you like to do?\n"
                    + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
                    + "Which direction do you want to explore?\n"
                    + "Options - [EAST,NORTH,SOUTH]?\n"
                    + "-----------------------------------------------\n"
                    + "Warning! Something looks fishy. Potential monsters nearby\n"
                    + "Player's Current Location: Cell-06\n"
                    + "Player's Current Location Type: TUNNEL\n"
                    + "Available Treasure:\n"
                    + "None\n"
                    + "Available Arrows:0\n"
                    + "No Monster Available\n"
                    + "Available Direction For Movement:\n"
                    + "EAST : Cell-07\n"
                    + "WEST : Cell-05\n"
                    + "What would you like to do?\n"
                    + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
                    + "Which direction do you want to explore?\n"
                    + "Options - [EAST,WEST]?\n"
                    + "-----------------------------------------------\n"
                    + "Red Alert!!! Monsters incoming\n"
                    + "Player's Current Location: Cell-07\n"
                    + "Player's Current Location Type: TUNNEL\n"
                    + "Available Treasure:\n"
                    + "None\n"
                    + "Available Arrows:0\n"
                    + "No Monster Available\n"
                    + "Available Direction For Movement:\n"
                    + "EAST : Cell-08\n"
                    + "WEST : Cell-06\n"
                    + "What would you like to do?\n"
                    + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
                    + "Which direction do you want to explore?\n"
                    + "Options - [EAST,WEST]?\n"
                    + "-----------------------------------------------\n"
                    + "Sorry. You are dead. Better luck next time. "
                    + "Here is a summary of the player:\n"
                    + "Player Name: Player-1\n"
                    + "Treasure Information: \n"
                    + "None\n"
                    + "Traversed Path: \n"
                    + "Cell-04\n"
                    + "Cell-03\n"
                    + "Cell-02\n"
                    + "Cell-01\n"
                    + "Cell-05\n"
                    + "Cell-06\n"
                    + "Cell-07\n"
                    + "Cell-08\n"
                    + "Available Arrows: 8\n";
    assertEquals(expectedOutput, output.toString());
  }

  /**
   * This function is used to test strong and weak smell of the different locations.
   */
  @Test
  public void testStrongWeakSmell() {
    //description of randomizer values
    // row one and two -> path pick up in kruskal
    // row three -> start end selection
    // row four -> treasure values
    // row five -> monster creation
    // row six -> arrow creation
    RandomNumberGenerator randomizer = new RandomNumberGeneratorImpl(
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0,
            15, 7,
            0, 1, 1, 1, 1, 2,
            1,
            0, 2, 1, 2, 2, 2, 3, 2
    );
    DungeonsGame dungeonsGame = new DungeonsGameImpl(DungeonsGame.WrapType.NON_WRAPPING, 4, 4,
            0, 20, 2, randomizer);
    StringBuffer output = new StringBuffer();
    Reader input = new StringReader("M WEST M WEST M WEST M NORTH M EAST M EAST M EAST");
    IConsoleDungeonController controller = new DungeonControllerConsole(input, output,
            dungeonsGame);
    controller.playGame();
    String expectedOutput =
            "Hey Player-1. Let's start the game.\n"
                    + "Player's Current Location: Cell-16\n"
                    + "Player's Current Location Type: CAVE\n"
                    + "Available Treasure:\n"
                    + "None\n"
                    + "Available Arrows:0\n"
                    + "No Monster Available\n"
                    + "Available Direction For Movement:\n"
                    + "WEST : Cell-15\n"
                    + "What would you like to do?\n"
                    + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
                    + "Which direction do you want to explore?\n"
                    + "Options - [WEST]?\n"
                    + "-----------------------------------------------\n"
                    + "Player's Current Location: Cell-15\n"
                    + "Player's Current Location Type: TUNNEL\n"
                    + "Available Treasure:\n"
                    + "None\n"
                    + "Available Arrows:0\n"
                    + "No Monster Available\n"
                    + "Available Direction For Movement:\n"
                    + "EAST : Cell-16\n"
                    + "WEST : Cell-14\n"
                    + "What would you like to do?\n"
                    + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
                    + "Which direction do you want to explore?\n"
                    + "Options - [EAST,WEST]?\n"
                    + "-----------------------------------------------\n"
                    + "Player's Current Location: Cell-14\n"
                    + "Player's Current Location Type: TUNNEL\n"
                    + "Available Treasure:\n"
                    + "None\n"
                    + "Available Arrows:0\n"
                    + "No Monster Available\n"
                    + "Available Direction For Movement:\n"
                    + "EAST : Cell-15\n"
                    + "WEST : Cell-13\n"
                    + "What would you like to do?\n"
                    + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
                    + "Which direction do you want to explore?\n"
                    + "Options - [EAST,WEST]?\n"
                    + "-----------------------------------------------\n"
                    + "Player's Current Location: Cell-13\n"
                    + "Player's Current Location Type: TUNNEL\n"
                    + "Available Treasure:\n"
                    + "None\n"
                    + "Available Arrows:0\n"
                    + "No Monster Available\n"
                    + "Available Direction For Movement:\n"
                    + "NORTH : Cell-09\n"
                    + "EAST : Cell-14\n"
                    + "What would you like to do?\n"
                    + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
                    + "Which direction do you want to explore?\n"
                    + "Options - [EAST,NORTH]?\n"
                    + "-----------------------------------------------\n"
                    + "Player's Current Location: Cell-09\n"
                    + "Player's Current Location Type: TUNNEL\n"
                    + "Available Treasure:\n"
                    + "None\n"
                    + "Available Arrows:0\n"
                    + "No Monster Available\n"
                    + "Available Direction For Movement:\n"
                    + "SOUTH : Cell-13\n"
                    + "EAST : Cell-10\n"
                    + "What would you like to do?\n"
                    + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
                    + "Which direction do you want to explore?\n"
                    + "Options - [EAST,SOUTH]?\n"
                    + "-----------------------------------------------\n"
                    + "Red Alert!!! Monsters incoming\n"
                    + "Player's Current Location: Cell-10\n"
                    + "Player's Current Location Type: CAVE\n"
                    + "Available Treasure:\n"
                    + "None\n"
                    + "Available Arrows:0\n"
                    + "No Monster Available\n"
                    + "Available Direction For Movement:\n"
                    + "NORTH : Cell-06\n"
                    + "EAST : Cell-11\n"
                    + "WEST : Cell-09\n"
                    + "What would you like to do?\n"
                    + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
                    + "Which direction do you want to explore?\n"
                    + "Options - [EAST,NORTH,WEST]?\n"
                    + "-----------------------------------------------\n"
                    + "Red Alert!!! Monsters incoming\n"
                    + "Player's Current Location: Cell-11\n"
                    + "Player's Current Location Type: TUNNEL\n"
                    + "Available Treasure:\n"
                    + "None\n"
                    + "Available Arrows:0\n"
                    + "No Monster Available\n"
                    + "Available Direction For Movement:\n"
                    + "EAST : Cell-12\n"
                    + "WEST : Cell-10\n"
                    + "What would you like to do?\n"
                    + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
                    + "Which direction do you want to explore?\n"
                    + "Options - [EAST,WEST]?\n"
                    + "-----------------------------------------------\n"
                    + "Sorry. You are dead. Better luck next time. "
                    + "Here is a summary of the player:\n"
                    + "Player Name: Player-1\n"
                    + "Treasure Information: \n"
                    + "None\n"
                    + "Traversed Path: \n"
                    + "Cell-16\n"
                    + "Cell-15\n"
                    + "Cell-14\n"
                    + "Cell-13\n"
                    + "Cell-09\n"
                    + "Cell-10\n"
                    + "Cell-11\n"
                    + "Cell-12\n"
                    + "Available Arrows: 3\n";
    assertEquals(expectedOutput, output.toString());
  }

  /**
   * This function is used to validate the move functionality with mocking for testing controller.
   */
  @Test
  public void testValidateMoveWithMockModel() {
    StringBuffer output = new StringBuffer();
    Reader input = new StringReader("M WEST M WEST M WEST M "
            + "SOUTH Q");
    StringBuilder log = new StringBuilder();

    //create model
    DungeonsGame dungeonsGame = new MockModel(log, 12344321);
    IConsoleDungeonController controller = new DungeonControllerConsole(input, output,
            dungeonsGame);
    controller.playGame();
    assertEquals("Player Name: Player-1\n"
            + "Updating Game state to progress.\n"
            + "Getting Game State: PROGRESS\n"
            + "Movement Direction: WEST\n"
            + "Getting Game State: PROGRESS\n"
            + "Movement Direction: WEST\n"
            + "Getting Game State: PROGRESS\n"
            + "Movement Direction: WEST\n"
            + "Getting Game State: PROGRESS\n"
            + "Movement Direction: SOUTH\n"
            + "Getting Game State: PROGRESS\n", log.toString()); // input reaches model correctly
    String expectedOutput = "Hey Player-1. Let's start the game.\n"
            + "12344321"
            + "What would you like to do?\n"
            + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
            + "Which direction do you want to explore?\n"
            + "Options - [12344321,12344321,12344321,12344321]?\n"
            + "-----------------------------------------------\n"
            + "12344321"
            + "What would you like to do?\n"
            + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
            + "Which direction do you want to explore?\n"
            + "Options - [12344321,12344321,12344321,12344321]?\n"
            + "-----------------------------------------------\n"
            + "12344321What would you like to do?\n"
            + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
            + "Which direction do you want to explore?\n"
            + "Options - [12344321,12344321,12344321,12344321]?\n"
            + "-----------------------------------------------\n"
            + "12344321"
            + "What would you like to do?\n"
            + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
            + "Which direction do you want to explore?\n"
            + "Options - [12344321,12344321,12344321,12344321]?\n"
            + "-----------------------------------------------\n"
            + "12344321"
            + "What would you like to do?\n"
            + "Move, Pickup, or Shoot or Quit(M-P-S-Q)?\n"
            + "\n"
            + "-----------------------------------------------\n"
            + "Looks like you are quitting the game. Hope to see you soon.\n";
    assertEquals(expectedOutput, output.toString()); // output from model received correctly
  }

  /**
   * This function is used to test the IOException is working correctly or not.
   */
  @Test(expected = IllegalStateException.class)
  public void testFailingAppendable() {
    // Testing when something goes wrong with the Appendable
    // Here we are passing mock of an Appendable that always fails
    Appendable output = new FailingAppendable();
    Reader input = new StringReader("M WEST M WEST M WEST M "
            + "SOUTH M EAST S EAST 1 S EAST 1 M EAST M EAST");
    //create randomizer
    //description of randomizer values
    // row one and two -> path pick up in kruskal
    // row three -> start end selection
    // row four -> treasure values
    // row five -> monster creation
    // row six -> arrow creation
    RandomNumberGenerator randomizer = new RandomNumberGeneratorImpl(
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            3, 0,
            0, 1, 1, 1, 1, 2,
            5,
            0, 2, 1, 2, 2, 2, 3, 2
    );
    //create model
    DungeonsGame dungeonsGame = new DungeonsGameImpl(DungeonsGame.WrapType.NON_WRAPPING, 4, 4,
            0, 20, 2, randomizer);
    IConsoleDungeonController controller = new DungeonControllerConsole(input, output,
            dungeonsGame);
    controller.playGame();
  }

  /**
   * This function is used to test the IOException is working correctly or not for reading image.
   */
  @Test(expected = IllegalStateException.class)
  public void testFailingImageReading() {
    // Testing when something goes wrong with the Appendable
    // Here we are passing mock of an Appendable that always fails
    Appendable output = new FailingAppendable();
    Reader input = new StringReader("M WEST M WEST M WEST M "
            + "SOUTH M EAST S EAST 1 S EAST 1 M EAST M EAST");
    //create randomizer
    //description of randomizer values
    // row one and two -> path pick up in kruskal
    // row three -> start end selection
    // row four -> treasure values
    // row five -> monster creation
    // row six -> arrow creation
    RandomNumberGenerator randomizer = new RandomNumberGeneratorImpl(
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            3, 0,
            0, 1, 1, 1, 1, 2,
            5,
            0, 2, 1, 2, 2, 2, 3, 2
    );
    //create model
    DungeonsGame dungeonsGame = new DungeonsGameImpl(DungeonsGame.WrapType.NON_WRAPPING, 4, 4,
            0, 20, 2, randomizer);
    IConsoleDungeonController controller = new DungeonControllerConsole(input, output,
            dungeonsGame);
    controller.playGame();
  }

  /**
   * tests the start game functionality provided by controller.
   */
  @Test
  public void testStartGame() {
    //create game model
    StringBuilder modelLog = new StringBuilder();
    DungeonsGame dungeonsGameReadOnly = new MockModel(modelLog, 1234);
    StringBuilder viewLog = new StringBuilder();
    //create view
    GameView gameView = new MockView(viewLog);
    //create controller
    IDungeonControllerGui controller = new DungeonControllerGui(dungeonsGameReadOnly,
            gameView);
    controller.playGame();
    String expectedModelLog = "Player Name: Player-1\n"
            + "Updating Game state to progress.\n";
    String expectedViewLog = "Adding listener\n"
            + "Making view visible\n"
            + "Resetting focus\n"
            + "Updating player name to Player-1\n"
            + "Updating action result: Hey Player-1. Let's start the game. "
            + "Play a move or check out help to understand the game.\n";
    assertEquals(expectedModelLog, modelLog.toString());
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * tests the treasure pick up callback provided by controller.
   */
  @Test
  public void testHandleTreasurePickUpCurrentLocation() {

    StringBuilder modelLog = new StringBuilder();
    DungeonsGame dungeonsGameReadOnly = new MockModel(modelLog, 1234);
    StringBuilder viewLog = new StringBuilder();
    //create view
    GameView gameView = new MockView(viewLog);
    //create controller
    IDungeonControllerGui controller = new DungeonControllerGui(dungeonsGameReadOnly,
            gameView);
    controller.playGame();
    controller.handleTreasurePickUpCurrentLocation();
    String expectedModelLog = "Player Name: Player-1\n"
            + "Updating Game state to progress.\n"
            + "Picking up treasure from current location";
    String expectedViewLog = "Adding listener\n"
            + "Making view visible\n"
            + "Resetting focus\n"
            + "Updating player name to Player-1\n"
            + "Updating action result: Hey Player-1. Let's start the game. "
            + "Play a move or check out help to understand the game.\n"
            + "Updating action result: Player successfully picked up all the "
            + "treasures in the location.\n"
            + "Calling refresh\n"
            + "Resetting focus\n";
    assertEquals(expectedModelLog, modelLog.toString());
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * tests the pickup arrow callback provided by controller.
   */
  @Test
  public void testHandleArrowPickUpCurrentLocation() {
    //create game model
    StringBuilder modelLog = new StringBuilder();
    DungeonsGame dungeonsGameReadOnly = new MockModel(modelLog, 1234);
    StringBuilder viewLog = new StringBuilder();
    //create view
    GameView gameView = new MockView(viewLog);
    //create controller
    IDungeonControllerGui controller = new DungeonControllerGui(dungeonsGameReadOnly,
            gameView);
    controller.playGame();
    controller.handleArrowPickUpCurrentLocation();
    String expectedModelLog = "Player Name: Player-1\n"
            + "Updating Game state to progress.\n"
            + "Picking up arrow from current location";
    String expectedViewLog = "Adding listener\n"
            + "Making view visible\n"
            + "Resetting focus\n"
            + "Updating player name to Player-1\n"
            + "Updating action result: Hey Player-1. Let's start the game. "
            + "Play a move or check out help to understand the game.\n"
            + "Updating action result: Player successfully picked up all the "
            + "arrows in the location.\n"
            + "Calling refresh\n"
            + "Resetting focus\n";
    assertEquals(expectedModelLog, modelLog.toString());
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * tests the shoot arrow callback provided by controller.
   */
  @Test
  public void testHandleShootArrow() {
    //create game model
    StringBuilder modelLog = new StringBuilder();
    DungeonsGame dungeonsGameReadOnly = new MockModel(modelLog, 1234);
    StringBuilder viewLog = new StringBuilder();
    //create view
    GameView gameView = new MockView(viewLog);
    //create controller
    IDungeonControllerGui controller = new DungeonControllerGui(dungeonsGameReadOnly,
            gameView);
    controller.playGame();
    controller.handleShootArrow(DungeonsGame.Direction.EAST, 10);
    String expectedModelLog = "Player Name: Player-1\n"
            + "Updating Game state to progress.\n"
            + "Shoot Direction: EAST\n"
            + "Shoot Distance: 10\n";
    String expectedViewLog = "Adding listener\n"
            + "Making view visible\n"
            + "Resetting focus\n"
            + "Updating player name to Player-1\n"
            + "Updating action result: Hey Player-1. Let's start the game. "
            + "Play a move or check out help to understand the game.\n"
            + "Updating action result: Bad shot. Better luck next time.\n"
            + "Calling refresh\n"
            + "Resetting focus\n";
    assertEquals(expectedModelLog, modelLog.toString());
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * tests the movement callback provided by controller.
   */
  @Test
  public void testHandleMovePlayer() {
    //create game model
    StringBuilder modelLog = new StringBuilder();
    DungeonsGame dungeonsGameReadOnly = new MockModel(modelLog, 1234);
    StringBuilder viewLog = new StringBuilder();
    //create view
    GameView gameView = new MockView(viewLog);
    //create controller
    IDungeonControllerGui controller = new DungeonControllerGui(dungeonsGameReadOnly,
            gameView);
    controller.playGame();
    String expectedModelLog = "Player Name: Player-1\n"
            + "Updating Game state to progress.\n";
    String expectedViewLog = "Adding listener\n"
            + "Making view visible\n"
            + "Resetting focus\n"
            + "Updating player name to Player-1\n"
            + "Updating action result: Hey Player-1. Let's start the game. "
            + "Play a move or check out help to understand the game.\n";
    assertEquals(expectedModelLog, modelLog.toString());
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * tests the player information description callback provided by controller.
   */
  @Test
  public void testHandlePlayerDescriptionGeneration() {
    //create game model
    StringBuilder modelLog = new StringBuilder();
    DungeonsGame dungeonsGameReadOnly = new MockModel(modelLog, 1234);
    StringBuilder viewLog = new StringBuilder();
    //create view
    GameView gameView = new MockView(viewLog);
    //create controller
    IDungeonControllerGui controller = new DungeonControllerGui(dungeonsGameReadOnly,
            gameView);
    controller.playGame();
    controller.handlePlayerDescriptionGeneration();
    String expectedModelLog = "Player Name: Player-1\n"
            + "Updating Game state to progress.\n";
    String expectedViewLog = "Adding listener\n"
            + "Making view visible\n"
            + "Resetting focus\n"
            + "Updating player name to Player-1\n"
            + "Updating action result: Hey Player-1. Let's start the game. "
            + "Play a move or check out help to understand the game.\n"
            + "Showing player description: Player Name: 1234\n"
            + "Treasure Information: \n"
            + "None\n"
            + "Available Arrows: None\n"
            + "\n" + "Resetting focus\n";
    assertEquals(expectedModelLog, modelLog.toString());
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * tests the start new game callback provided by controller.
   */
  @Test
  public void testStartNewGame() {
    //create game model
    StringBuilder modelLog = new StringBuilder();
    DungeonsGame dungeonsGameReadOnly = new MockModel(modelLog, 1234);
    StringBuilder viewLog = new StringBuilder();
    //create view
    GameView gameView = new MockView(viewLog);
    //create controller
    IDungeonControllerGui controller = new DungeonControllerGui(dungeonsGameReadOnly,
            gameView);
    controller.playGame();
    controller.startNewGame(DungeonsGame.WrapType.WRAPPING, 4, 4,
            1, 30, 2,
            "Player-1");
    String expectedModelLog = "Player Name: Player-1\n"
            + "Updating Game state to progress.\n"
            + "Wrap Type: WRAPPING\n"
            + "Row Count: 4\n"
            + "Col Count: 4\n"
            + "InterC: 1\n"
            + "Treasure Percent: 30\n"
            + "Monster Count: 2\n"
            + "Player Name: Player-1\n"
            + "Updating Game state to progress.\n";
    String expectedViewLog = "Adding listener\n"
            + "Making view visible\n"
            + "Resetting focus\n"
            + "Updating player name to Player-1\n"
            + "Updating action result: Hey Player-1. Let's start the game. "
            + "Play a move or check out help to understand the game.\n"
            + "Updating player name to Player-1\n"
            + "New game setup\n"
            + "Updating action result: New game started. Please make an action to start the game.\n"
            + "Resetting focus\n";
    assertEquals(expectedModelLog, modelLog.toString());
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * tests the restart callback provided by controller.
   */
  @Test
  public void testRestartCurrentGame() {
    //create game model
    StringBuilder modelLog = new StringBuilder();
    DungeonsGame dungeonsGameReadOnly = new MockModel(modelLog, 1234);
    StringBuilder viewLog = new StringBuilder();
    //create view
    GameView gameView = new MockView(viewLog);
    //create controller
    IDungeonControllerGui controller = new DungeonControllerGui(dungeonsGameReadOnly,
            gameView);
    controller.playGame();
    controller.restartCurrentGame();
    String expectedModelLog = "Player Name: Player-1\n"
            + "Updating Game state to progress.\n"
            + "Resetting the modelPlayer Name: 1234\n"
            + "Updating Game state to progress.\n";
    String expectedViewLog = "Adding listener\n"
            + "Making view visible\n"
            + "Resetting focus\n"
            + "Updating player name to Player-1\n"
            + "Updating action result: Hey Player-1. Let's start the game. "
            + "Play a move or check out help to understand the game.\n"
            + "Resetting the game\n"
            + "Updating action result: Game restarted. Please make an action to start the game.\n"
            + "Resetting focus\n";
    assertEquals(expectedModelLog, modelLog.toString());
    assertEquals(expectedViewLog, viewLog.toString());
  }
}