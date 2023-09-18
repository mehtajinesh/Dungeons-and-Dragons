package modeltesting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import game.model.DungeonsGame;
import game.model.DungeonsGameImpl;
import game.model.PlayerData;
import game.model.RandomNumberGenerator;
import game.model.RandomNumberGeneratorImpl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * This class is used to perform testing of different dungeon game functionalities.
 * This includes getting the game grid, movement of the player and much more.
 */
public class DungeonsGameTest {

  /**
   * This test is used to show that invalid input row dimension throws exception or not.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidInputRowDimension() {
    RandomNumberGenerator randomizer = new RandomNumberGeneratorImpl();
    DungeonsGame dungeonsGame = new DungeonsGameImpl(DungeonsGame.WrapType.NON_WRAPPING,
            -1, 5, 2, 20, 10, randomizer);
  }

  /**
   * This test is used to show that invalid input column dimension throws exception or not.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidInputColumnDimension() {
    RandomNumberGenerator randomizer = new RandomNumberGeneratorImpl();
    DungeonsGame dungeonsGame = new DungeonsGameImpl(DungeonsGame.WrapType.NON_WRAPPING,
            4, -5, 2, 20, 10, randomizer);
  }

  /**
   * This test is used to show that invalid input for wrap type throws exception or not.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidInputWrapType() {
    RandomNumberGenerator randomizer = new RandomNumberGeneratorImpl();
    DungeonsGame dungeonsGame = new DungeonsGameImpl(null,
            4, 4, 2, 20, 10, randomizer);
  }

  /**
   * This test is used to show that invalid input for interconnectivity throws exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidInputInterConnectivity() {
    RandomNumberGenerator randomizer = new RandomNumberGeneratorImpl();
    DungeonsGame dungeonsGame = new DungeonsGameImpl(DungeonsGame.WrapType.NON_WRAPPING,
            4, 4, -2, 20, 10, randomizer);
  }

  /**
   * This function is used to show that invalid input percentage throws exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidTreasurePercent() {
    RandomNumberGenerator randomizer = new RandomNumberGeneratorImpl();
    DungeonsGame dungeonsGame = new DungeonsGameImpl(DungeonsGame.WrapType.NON_WRAPPING,
            4, 4, 2, -20, 10, randomizer);
  }

  /**
   * This function is used to show that invalid monster count throws exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidMonsterCountPercent() {
    RandomNumberGenerator randomizer = new RandomNumberGeneratorImpl();
    DungeonsGame dungeonsGame = new DungeonsGameImpl(DungeonsGame.WrapType.NON_WRAPPING,
            4, 4, 2, -20, -10, randomizer);
  }

  /**
   * This test is used to show that invalid randomizer throws exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidRandomizerInput() {
    RandomNumberGenerator randomizer = new RandomNumberGeneratorImpl();
    DungeonsGame dungeonsGame = new DungeonsGameImpl(DungeonsGame.WrapType.NON_WRAPPING,
            4, 4, 2, 20, 10, null);
  }

  /**
   * This test is used to show that the game throws an exception if interconnectivity is more
   * than what the game can handle.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidInterConnectivityMoreThanAvailablePaths() {
    RandomNumberGenerator randomizer = new RandomNumberGeneratorImpl();
    DungeonsGame dungeonsGame = new DungeonsGameImpl(DungeonsGame.WrapType.NON_WRAPPING,
            4, 4, 50, 20, 10, randomizer);
  }

  /**
   * This test is used to show that right to left wrapping works.
   */
  @Test
  public void testWrapRightLeftWorking() {
    //description of randomizer values
    // row one and two -> path pick up in kruskal
    // row three -> start end selection
    // row four -> treasure values
    // row five -> monster creation
    // row six -> arrow creation
    RandomNumberGenerator randomizer = new RandomNumberGeneratorImpl(
            0, 0, 1, 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 7, 10,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            3, 0,
            0, 1, 1, 1, 1, 1,
            0, 5, 6, 7,
            0, 2, 1, 2, 2, 2, 3, 2
    );
    DungeonsGame dungeonsGame = new DungeonsGameImpl(DungeonsGame.WrapType.WRAPPING, 4, 4,
            0, 20, 5, randomizer);
    dungeonsGame.startGameForPlayer("Player-1");
    //check player location before wrap node on right
    String expectedCurrentLocationBeforeWrapMovement = "Player's Current Location: Cell-04";
    String playerInformationBeforeWrapMovement = dungeonsGame.getPlayerCurrentLocationInformation();
    boolean currentLocationBeforeWrapMovement = playerInformationBeforeWrapMovement.contains(
            expectedCurrentLocationBeforeWrapMovement);
    assertTrue(currentLocationBeforeWrapMovement);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.EAST);
    //check player location after movement wrap node left
    String expectedCurrentLocationAfterMovement = "Player's Current Location: Cell-01";
    String playerInformationAfterMovement = dungeonsGame.getPlayerCurrentLocationInformation();
    boolean currentLocationAfterMovement = playerInformationAfterMovement.contains(
            expectedCurrentLocationAfterMovement);
    assertTrue(currentLocationAfterMovement);
  }

  /**
   * This function is used to test if four entrance cave is working or not.
   */
  @Test
  public void testCaveFourEntranceWorking() {
    //description of randomizer values
    // row one and two -> path pick up in kruskal
    // row three -> start end selection
    // row four -> treasure values
    // row five -> arrow creation
    RandomNumberGenerator randomizer = new RandomNumberGeneratorImpl(
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0,
            3, 2,
            0, 1, 1, 1, 1, 2,
            0, 2, 1, 2, 2, 2, 3, 2
    );
    DungeonsGame dungeonsGame = new DungeonsGameImpl(DungeonsGame.WrapType.NON_WRAPPING, 4, 4,
            0, 20, 1, randomizer);
    dungeonsGame.startGameForPlayer("Player-1");
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.SOUTH);
    String[] processedText = dungeonsGame.getPlayerCurrentLocationInformation().split(
            "Available Direction For Movement:\n");
    List<String> neighbours = new ArrayList<>();
    Collections.addAll(neighbours, processedText[processedText.length - 1].split("\n"));
    String neighbour1 = "WEST : Cell-05";
    assertTrue(neighbours.contains(neighbour1));
    String neighbour2 = "NORTH : Cell-02";
    assertTrue(neighbours.contains(neighbour2));
    String neighbour3 = "EAST : Cell-07";
    assertTrue(neighbours.contains(neighbour3));
    String neighbour4 = "SOUTH : Cell-10";
    assertTrue(neighbours.contains(neighbour4));
    assertEquals(4, neighbours.size());
  }

  /**
   * This test is used to show if the player is able to move that can lead to an end of game.
   */
  @Test
  public void testValidPlayerMovementToLocationGameEnded() {
    //description of randomizer values
    // row one and two -> path pick up in kruskal
    // row three -> start end selection
    // row four -> treasure values
    // row five -> arrow creation
    RandomNumberGenerator randomizer = new RandomNumberGeneratorImpl(
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0,
            3, 0,
            0, 1, 1, 1, 1, 2,
            0, 2, 1, 2, 2, 2, 3, 2
    );
    DungeonsGame dungeonsGame = new DungeonsGameImpl(DungeonsGame.WrapType.NON_WRAPPING, 4, 4,
            0, 20, 1, randomizer);
    dungeonsGame.startGameForPlayer("Player-1");
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.SOUTH);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.EAST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.EAST);
    //game ended as monster ate the player
    assertEquals(DungeonsGame.GameState.LOST, dungeonsGame.getGameStatus());
  }

  /**
   * This test is used to show if the start end path has at least a distance of five.
   */
  @Test
  public void testStartEndPathMoreThanFour() {
    //description of randomizer values
    // row one and two -> path pick up in kruskal
    // row three -> start end selection
    // row four -> treasure values
    // row five -> arrow creation
    RandomNumberGenerator randomizer = new RandomNumberGeneratorImpl(
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0,
            3, 2,
            0, 1, 1, 1, 1, 2,
            0, 2, 1, 2, 2, 2, 3, 2
    );
    DungeonsGame dungeonsGame = new DungeonsGameImpl(DungeonsGame.WrapType.NON_WRAPPING, 4, 4,
            0, 20, 1, randomizer);
    dungeonsGame.startGameForPlayer("Player-1");
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.SOUTH);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.SOUTH);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.EAST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.SOUTH);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.EAST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.EAST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.EAST);
    assertEquals(DungeonsGame.GameState.LOST, dungeonsGame.getGameStatus());
    assertTrue(dungeonsGame.getPlayerInformation().getTraversedPath().size() > 4);
  }

  /**
   * This test is used to show that start game works for player and drops the player
   * at the start cell.
   */
  @Test
  public void testStartGameForPlayerGame() {
    //description of randomizer values
    // row one and two -> path pick up in kruskal
    // row three -> start end selection
    // row four -> treasure values
    // row five -> arrow creation
    RandomNumberGenerator randomizer = new RandomNumberGeneratorImpl(
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0,
            3, 2,
            0, 1, 1, 1, 1, 2,
            0, 2, 1, 2, 2, 2, 3, 2
    );
    DungeonsGame dungeonsGame = new DungeonsGameImpl(DungeonsGame.WrapType.NON_WRAPPING, 4, 4,
            0, 20, 1, randomizer);
    dungeonsGame.startGameForPlayer("Player-1");
    assertEquals("Cell-04", dungeonsGame.getPlayerCurrentLocation().getCellName());
  }

  /**
   * This test is used to show if there is at least one path which exists between every cave.
   */
  @Test
  public void testPathExistsBetweenEveryCave() {
    //description of randomizer values
    // row one and two -> path pick up in kruskal
    // row three -> start end selection
    // row four -> treasure values
    // row five -> arrow creation
    RandomNumberGenerator randomizer = new RandomNumberGeneratorImpl(
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0,
            3, 2,
            0, 1, 1, 1, 1, 2,
            0, 2, 1, 2, 2, 2, 3, 2
    );
    DungeonsGame dungeonsGame = new DungeonsGameImpl(DungeonsGame.WrapType.NON_WRAPPING, 4, 4,
            0, 20, 1, randomizer);
    dungeonsGame.startGameForPlayer("Player-1");
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.EAST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.SOUTH);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.EAST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.EAST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.EAST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.SOUTH);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.EAST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.EAST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.SOUTH);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.EAST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.EAST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.EAST);
    PlayerData playerInformation = dungeonsGame.getPlayerInformation();
    List<String> processedText = playerInformation.getTraversedPath();
    String expectedOutput = "Cell-04\n"
            + "Cell-03\n"
            + "Cell-02\n"
            + "Cell-01\n"
            + "Cell-02\n"
            + "Cell-06\n"
            + "Cell-07\n"
            + "Cell-08\n"
            + "Cell-07\n"
            + "Cell-06\n"
            + "Cell-05\n"
            + "Cell-06\n"
            + "Cell-10\n"
            + "Cell-11\n"
            + "Cell-12\n"
            + "Cell-11\n"
            + "Cell-10\n"
            + "Cell-09\n"
            + "Cell-13\n"
            + "Cell-14\n"
            + "Cell-15\n"
            + "Cell-16";
    assertEquals(expectedOutput, String.join("\n", processedText));
  }

  /**
   * This test is used to get the player information with some treasure.
   */
  @Test
  public void testGetPlayerInformationWithSomeTreasure() {
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
    DungeonsGame dungeonsGame = new DungeonsGameImpl(DungeonsGame.WrapType.NON_WRAPPING, 4, 4,
            0, 20, 2, randomizer);
    dungeonsGame.startGameForPlayer("Player-1");
    dungeonsGame.pickUpTreasureCurrentLocation();
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.SOUTH);
    dungeonsGame.pickUpTreasureCurrentLocation();
    Set<DungeonsGame.TreasureType> actualTreasure = dungeonsGame
            .getPlayerInformation().getAcquiredTreasures().keySet();
    Set<DungeonsGame.TreasureType> expectedTreasure = new HashSet<>();
    expectedTreasure.add(DungeonsGame.TreasureType.RUBIES);
    expectedTreasure.add(DungeonsGame.TreasureType.SAPPHIRES);
    assertEquals(expectedTreasure, actualTreasure);
  }

  /**
   * This test is used to show that the treasure distribution is working correctly.
   */
  @Test
  public void testTreasureDistributionPercentWorking() {
    //description of randomizer values
    // row one and two -> path pick up in kruskal
    // row three -> start end selection
    // row four -> treasure values
    // row five -> arrow creation
    RandomNumberGenerator randomizer = new RandomNumberGeneratorImpl(
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0,
            3, 2,
            0, 1, 1, 1, 1, 2,
            0, 2, 1, 2, 2, 2, 3, 2
    );
    DungeonsGame dungeonsGame = new DungeonsGameImpl(DungeonsGame.WrapType.NON_WRAPPING, 4, 4,
            0, 20, 1, randomizer);
    double caveCount = (dungeonsGame.getGameGrid().split("\\{CAVE}", -1).length) - 1;
    int treasureCaveCount = (dungeonsGame.getGameGrid().split("\\{CAVE}\\[.+]", -1).length) - 1;
    double treasurePercent = (treasureCaveCount / caveCount) * 100.0;
    assertTrue(treasurePercent > 20);
  }

  /**
   * This test is used to show if the player is able to slay a monster completely.
   */
  @Test
  public void testPlayerAbleToSlayMonster() {
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
    DungeonsGame dungeonsGame = new DungeonsGameImpl(DungeonsGame.WrapType.NON_WRAPPING, 4, 4,
            0, 20, 2, randomizer);
    dungeonsGame.startGameForPlayer("Player-1");
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.SOUTH);
    DungeonsGame.ShootResultStates firstArrowResult = dungeonsGame.shootArrowForPlayer(
            DungeonsGame.Direction.EAST, 1);
    assertEquals(DungeonsGame.ShootResultStates.MONSTER_HIT_PARTIAL_DAMAGE, firstArrowResult);
    DungeonsGame.ShootResultStates secondArrowResult = dungeonsGame.shootArrowForPlayer(
            DungeonsGame.Direction.EAST, 1);
    assertEquals(DungeonsGame.ShootResultStates.MONSTER_HIT_KILLED, secondArrowResult);
  }

  /**
   * This test is used to show if the player information is working fine after game ends.
   */
  @Test
  public void testGetPlayerInformationEndGame() {
    //description of randomizer values
    // row one and two -> path pick up in kruskal
    // row three -> start end selection
    // row four -> treasure values
    // row five -> arrow creation
    RandomNumberGenerator randomizer = new RandomNumberGeneratorImpl(
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0,
            3, 0,
            0, 1, 1, 1, 1, 2,
            0, 2, 1, 2, 2, 2, 3, 2
    );
    DungeonsGame dungeonsGame = new DungeonsGameImpl(DungeonsGame.WrapType.NON_WRAPPING, 4, 4,
            0, 20, 1, randomizer);
    dungeonsGame.startGameForPlayer("Player-1");
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.pickUpTreasureCurrentLocation();
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.SOUTH);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.EAST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.EAST);
    //game ended as monster ate the player
    assertEquals(DungeonsGame.GameState.LOST, dungeonsGame.getGameStatus());
    String expectedPlayerPath = "Cell-04\n"
            + "Cell-03\n"
            + "Cell-02\n"
            + "Cell-06\n"
            + "Cell-07\n"
            + "Cell-08";
    List<String> traversedPath = dungeonsGame.getPlayerInformation().getTraversedPath();
    assertEquals(expectedPlayerPath, String.join("\n", traversedPath));
    assertEquals("Player-1", dungeonsGame.getPlayerInformation().getPlayerName());
    assertEquals(3, dungeonsGame.getPlayerInformation().getAcquiredArrows().size());
    assertTrue(dungeonsGame.getPlayerInformation().getAcquiredTreasures().containsKey(
            DungeonsGame.TreasureType.SAPPHIRES));
    assertEquals(1, (int) dungeonsGame.getPlayerInformation().getAcquiredTreasures().get(
            DungeonsGame.TreasureType.SAPPHIRES));
    assertEquals("Player-1", dungeonsGame.getPlayerInformation().getPlayerName());

  }

  /**
   * This test is used to show if the player is able to win the game.
   */
  @Test
  public void testPlayerWinGame() {
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
    DungeonsGame dungeonsGame = new DungeonsGameImpl(DungeonsGame.WrapType.NON_WRAPPING, 4, 4,
            0, 20, 2, randomizer);
    dungeonsGame.startGameForPlayer("Player-1");
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.SOUTH);
    DungeonsGame.ShootResultStates firstArrowResult = dungeonsGame.shootArrowForPlayer(
            DungeonsGame.Direction.EAST, 1);
    assertEquals(DungeonsGame.ShootResultStates.MONSTER_HIT_PARTIAL_DAMAGE, firstArrowResult);
    DungeonsGame.ShootResultStates secondArrowResult = dungeonsGame.shootArrowForPlayer(
            DungeonsGame.Direction.EAST, 1);
    assertEquals(DungeonsGame.ShootResultStates.MONSTER_HIT_KILLED, secondArrowResult);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.EAST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.EAST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.EAST);
    DungeonsGame.GameState gameResult = dungeonsGame.getGameStatus();
    assertEquals(DungeonsGame.GameState.WON, gameResult);
  }

  /**
   * This test is used to show if the player is losing the game.
   */
  @Test
  public void testPlayerLostGame() {
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
    DungeonsGame dungeonsGame = new DungeonsGameImpl(DungeonsGame.WrapType.NON_WRAPPING, 4, 4,
            0, 20, 2, randomizer);
    dungeonsGame.startGameForPlayer("Player-1");
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.SOUTH);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.EAST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.EAST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.EAST);
    DungeonsGame.GameState gameResult = dungeonsGame.getGameStatus();
    assertEquals(DungeonsGame.GameState.LOST, gameResult);
  }

  /**
   * This test is used to show that there is a monster present in the end cave.
   */
  @Test
  public void testMonsterPresentEndCaveAlways() {
    //description of randomizer values
    // row one and two -> path pick up in kruskal
    // row three -> start end selection
    // row four -> treasure values
    // row six -> arrow creation
    RandomNumberGenerator randomizer = new RandomNumberGeneratorImpl(
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            3, 0,
            0, 1, 1, 1, 1, 2,
            0, 2, 1, 2, 2, 2, 3, 2
    );
    DungeonsGame dungeonsGame = new DungeonsGameImpl(DungeonsGame.WrapType.NON_WRAPPING, 4, 4,
            0, 20, 1, randomizer);
    //another way to prove this
    dungeonsGame.startGameForPlayer("Player-1");
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.SOUTH);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.EAST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.EAST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.EAST);
    DungeonsGame.GameState gameResult = dungeonsGame.getGameStatus();
    assertEquals(DungeonsGame.GameState.LOST, gameResult);
  }

  /**
   * This test shows that the crooked behavior works for tunnel.
   */
  @Test
  public void testArrowTraversalTunnelCaveWorking() {
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
            3,
            0, 2, 1, 2, 2, 2, 3, 2
    );
    DungeonsGame dungeonsGame = new DungeonsGameImpl(DungeonsGame.WrapType.NON_WRAPPING, 4, 4,
            0, 20, 2, randomizer);
    dungeonsGame.startGameForPlayer("Player-1");
    DungeonsGame.ShootResultStates firstArrow =
            dungeonsGame.shootArrowForPlayer(DungeonsGame.Direction.WEST, 2);
    assertEquals(DungeonsGame.ShootResultStates.MONSTER_HIT_PARTIAL_DAMAGE, firstArrow);
    DungeonsGame.ShootResultStates secondArrow =
            dungeonsGame.shootArrowForPlayer(DungeonsGame.Direction.WEST, 2);
    assertEquals(DungeonsGame.ShootResultStates.MONSTER_HIT_KILLED, secondArrow);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.SOUTH);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.SOUTH);
    String locationInformation = dungeonsGame.getPlayerCurrentLocationInformation();
    String monsterHealth = locationInformation.split("\n")[6];
    assertEquals("Monster Health:ZERO", monsterHealth);
  }

  /**
   * This test is used to check if the wrapping of up down is working.
   */
  @Test
  public void testWrapUpDownWorking() {
    //description of randomizer values
    // row one and two -> path pick up in kruskal
    // row three -> start end selection
    // row four -> treasure values
    // row five -> monster creation
    // row six -> arrow creation
    RandomNumberGenerator randomizer = new RandomNumberGeneratorImpl(
            0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3,
            5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
            10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 2,
            0, 1, 1, 1, 1, 2,
            3,
            0, 2, 1, 2, 2, 2, 3, 2
    );
    DungeonsGame dungeonsGame = new DungeonsGameImpl(DungeonsGame.WrapType.WRAPPING, 4, 5,
            0, 20, 2, randomizer);
    dungeonsGame.startGameForPlayer("Player-1");
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.EAST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.EAST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.EAST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.EAST);
    //check player location before wrap node on top
    String expectedCurrentLocationBeforeWrapMovement = "Player's Current Location: Cell-05";
    String playerInformationBeforeWrapMovement = dungeonsGame.getPlayerCurrentLocationInformation();
    boolean currentLocationBeforeWrapMovement = playerInformationBeforeWrapMovement.contains(
            expectedCurrentLocationBeforeWrapMovement);
    assertTrue(currentLocationBeforeWrapMovement);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.NORTH);
    //check player location after movement wrap node down
    String expectedCurrentLocationAfterMovement = "Player's Current Location: Cell-20";
    String playerInformationAfterMovement = dungeonsGame.getPlayerCurrentLocationInformation();
    boolean currentLocationAfterMovement = playerInformationAfterMovement.contains(
            expectedCurrentLocationAfterMovement);
    assertTrue(currentLocationAfterMovement);
  }

  /**
   * This test is used to show that requested number of monsters are getting created.
   */
  @Test
  public void testCreateMonsterWorking() {
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
    DungeonsGame dungeonsGame = new DungeonsGameImpl(DungeonsGame.WrapType.NON_WRAPPING, 4, 4,
            0, 20, 2, randomizer);
    int monsterCount = dungeonsGame.getGameGrid().split("<Monster-", -1).length - 1;
    assertEquals(2, monsterCount);
  }

  /**
   * This test is used to show that the game highlights a high smell when player goes extremely
   * near a monster.
   */
  @Test
  public void testSmellWorkingOneLocationAway() {
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
    DungeonsGame dungeonsGame = new DungeonsGameImpl(DungeonsGame.WrapType.NON_WRAPPING, 4, 4,
            0, 20, 2, randomizer);
    dungeonsGame.startGameForPlayer("Player-1");
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.SOUTH);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.EAST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.EAST);
    DungeonsGame.SmellType currentLocationSmell = dungeonsGame.getCurrentLocationSmell();
    assertEquals(DungeonsGame.SmellType.STRONG, currentLocationSmell);
  }

  /**
   * This test is used to show that the game highlights a high smell when there are more than
   * two monsters present within 2 cell radius.
   */
  @Test
  public void testSmellWorkingTwoLocationManyMonster() {
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
    dungeonsGame.startGameForPlayer("Player-1");
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.NORTH);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.EAST);
    DungeonsGame.SmellType currentLocationSmell = dungeonsGame.getCurrentLocationSmell();
    assertEquals(DungeonsGame.SmellType.STRONG, currentLocationSmell);
  }

  /**
   * This test is used to show that there can be some caves which have monster and treasure both.
   */
  @Test
  public void testMonsterCaveTreasure() {
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
            0, 1, 1, 2, 1, 2,
            5,
            0, 2, 1, 2, 2, 2, 3, 2
    );
    DungeonsGame dungeonsGame = new DungeonsGameImpl(DungeonsGame.WrapType.NON_WRAPPING, 4, 4,
            0, 20, 2, randomizer);
    int monsterTreasureCount = dungeonsGame.getGameGrid().split(
            "\\[.*?]<Monster-", -1).length - 1;
    assertEquals(2, monsterTreasureCount);
  }

  /**
   * This function is used to test if the arrow pick up works for tunnel or not.
   */
  @Test
  public void testPlayerArrowPickupTunnel() {
    //description of randomizer values
    // row one and two -> path pick up in kruskal
    // row three -> start end selection
    // row four -> treasure values
    // row six -> arrow creation
    RandomNumberGenerator randomizer = new RandomNumberGeneratorImpl(
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            3, 0,
            0, 1, 1, 1, 1, 2,
            0, 2, 1, 2, 2, 2, 3, 2
    );
    DungeonsGame dungeonsGame = new DungeonsGameImpl(DungeonsGame.WrapType.NON_WRAPPING, 4, 4,
            0, 20, 1, randomizer);
    dungeonsGame.startGameForPlayer("Player-1");
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    //verify player arrow count before pickup
    PlayerData playerInformation = dungeonsGame.getPlayerInformation();
    int arrowCount = playerInformation.getAcquiredArrows().size();
    assertEquals(3, arrowCount);
    //verify player location is a tunnel and two arrows are available
    String playerLocationInformation = dungeonsGame.getPlayerCurrentLocationInformation();
    String location = playerLocationInformation.split("\n")[1];
    assertEquals("Player's Current Location Type: TUNNEL", location);
    String caveArrows = playerLocationInformation.split("\n")[4];
    assertEquals("Available Arrows:2", caveArrows);
    dungeonsGame.pickUpArrowCurrentLocation();
    //verify arrow count after pickup
    playerInformation = dungeonsGame.getPlayerInformation();
    arrowCount = playerInformation.getAcquiredArrows().size();
    assertEquals(5, arrowCount);
    //verify arrow count in tunnel after pickup
    playerLocationInformation = dungeonsGame.getPlayerCurrentLocationInformation();
    caveArrows = playerLocationInformation.split("\n")[4];
    assertEquals("Available Arrows:0", caveArrows);
  }

  /**
   * This test shows that the arrow is blocked by a wall when an arrow is fired in that direction.
   */
  @Test
  public void testArrowBlockingCave() {
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
            3,
            0, 2, 1, 2, 2, 2, 3, 2
    );
    DungeonsGame dungeonsGame = new DungeonsGameImpl(DungeonsGame.WrapType.NON_WRAPPING, 4, 4,
            0, 20, 2, randomizer);
    dungeonsGame.startGameForPlayer("Player-1");
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.SOUTH);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.EAST);
    DungeonsGame.ShootResultStates firstArrow =
            dungeonsGame.shootArrowForPlayer(DungeonsGame.Direction.WEST, 2);
    assertEquals(DungeonsGame.ShootResultStates.MONSTER_MISSED, firstArrow);
  }

  /**
   * This test shows that the player did survive with an injured monster in the location.
   */
  @Test
  public void testPlayerSurviveForInjuredMonster() {
    //description of randomizer values
    // row one and two -> path pick up in kruskal
    // row three -> start end selection
    // row four -> treasure values
    // row five -> monster creation
    // row six -> arrow creation
    // row seven -> survival chance
    RandomNumberGenerator randomizer = new RandomNumberGeneratorImpl(
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            3, 0,
            0, 1, 1, 1, 1, 2,
            3,
            0, 2, 1, 2, 2, 2, 3, 2,
            1
    );
    DungeonsGame dungeonsGame = new DungeonsGameImpl(DungeonsGame.WrapType.NON_WRAPPING, 4, 4,
            0, 20, 2, randomizer);
    dungeonsGame.startGameForPlayer("Player-1");
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.WEST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.SOUTH);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.EAST);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.EAST);
    DungeonsGame.ShootResultStates firstArrow =
            dungeonsGame.shootArrowForPlayer(DungeonsGame.Direction.EAST, 1);
    assertEquals(DungeonsGame.ShootResultStates.MONSTER_HIT_PARTIAL_DAMAGE, firstArrow);
    dungeonsGame.movePlayerToLocation(DungeonsGame.Direction.EAST);
    DungeonsGame.GameState gameState = dungeonsGame.getGameStatus();
    assertEquals(DungeonsGame.GameState.WON, gameState);
  }

  /**
   * This test is used to show that a restart game with same parameters is working.
   */
  @Test
  public void testGameResetSameGameWorking() {
    //description of randomizer values
    // row one and two -> path pick up in kruskal
    // row three -> start end selection
    // row four -> treasure values
    // row five -> monster creation
    // row six -> arrow creation
    // row seven -> survival chance
    // row eight -> seed value
    // row nine to sixteen repeat
    RandomNumberGenerator randomizer = new RandomNumberGeneratorImpl(
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            3, 0,
            0, 1, 1, 1, 1, 2,
            3,
            0, 2, 1, 2, 2, 2, 3, 2,
            1,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            3, 0,
            0, 1, 1, 1, 1, 2,
            3,
            0, 2, 1, 2, 2, 2, 3, 2,
            1
    );
    DungeonsGame dungeonsGame = new DungeonsGameImpl(DungeonsGame.WrapType.NON_WRAPPING, 4, 4,
            0, 20, 2, randomizer);
    dungeonsGame.startGameForPlayer("Player-1");
    String firstGameGrid = dungeonsGame.getGameGrid();
    //reset same game
    dungeonsGame.reset();
    dungeonsGame.startGameForPlayer("Player-1");
    String secondGameGrid = dungeonsGame.getGameGrid();
    assertEquals(firstGameGrid, secondGameGrid);
  }

  /**
   * This test is used to show that a new game with different parameters is working.
   */
  @Test
  public void testNewGameSetupWorking() {
    //description of randomizer values
    // row one and two -> path pick up in kruskal
    // row three -> start end selection
    // row four -> treasure values
    // row five -> monster creation
    // row six -> arrow creation
    // row seven -> survival chance
    // row eight -> seed value
    // row nine to sixteen repeat (taken from true randomizer)
    RandomNumberGenerator randomizer = new RandomNumberGeneratorImpl(
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            3, 0,
            0, 1, 1, 1, 1, 2,
            3,
            0, 2, 1, 2, 2, 2, 3, 2,
            12,
            30, 12, 17, 32, 31, 30, 1, 19, 22, 17, 21, 6, 19, 0,
            16, 16, 15, 4, 12, 10, 16, 14, 0, 10, 6, 10, 6, 10, 4,
            8, 5, 2, 0, 1, 0, 3, 2, 2, 0, 0, 5, 4, 0, 2, 0, 2, 13, 1, 2,
            10, 3, 2, 2, 0, 0, 3, 3, 1, 0, 2, 11, 1, 1, 12, 2, 1, 2, 2, 2,
            2, 6, 7, 1, 14, 1, 5, 3, 23, 1, 11, 2, 15, 2, 1, 1, 14, 13, 2,
            21, 2, 3, 3
    );
    DungeonsGame dungeonsGame = new DungeonsGameImpl(DungeonsGame.WrapType.NON_WRAPPING, 4, 4,
            0, 20, 2, randomizer);
    dungeonsGame.startGameForPlayer("Player-1");
    String firstGameGrid = dungeonsGame.getGameGrid();
    //reset same game
    dungeonsGame.setupNewGame(DungeonsGame.WrapType.NON_WRAPPING, 5, 5,
            0, 40, 3);
    dungeonsGame.startGameForPlayer("Player-1");
    String secondGameGrid = dungeonsGame.getGameGrid();
    assertNotEquals(firstGameGrid, secondGameGrid);
    String expectedGrid = "                                                         WAL"
            + "L                                                          W"
            + "ALL                                                         "
            + " WALL                                                       "
            + "   WALL                                                     "
            + "     WALL\n                          WALL          Cell-01{T"
            + "UNNEL}[]<>()                    <--------->        Cell-02{C"
            + "AVE}[D,S]<>(A)                    <--------->          Cell-"
            + "03{TUNNEL}[]<>()                           WALL       Cell-0"
            + "4{CAVE}[]<>(A,A,A)                           WALL   Cell-05{"
            + "CAVE}[]<Monster-1>()                           WALL\n       "
            + "                                                     |      "
            + "                                                       |    "
            + "                                                         |  "
            + "                                                           |"
            + "                                                            "
            + " |\n                          WALL    *Cell-06{TUNNEL}[]<>(A"
            + ",A,A)                           WALL          Cell-07{TUNNEL"
            + "}[]<>()                           WALL      Cell-08{CAVE}[R,"
            + "D,S]<>(A)                    <--------->            Cell-09{"
            + "CAVE}[]<>()                    <--------->            Cell-1"
            + "0{CAVE}[]<>()                           WALL\n              "
            + "                                              |             "
            + "                                                |           "
            + "                                                  |         "
            + "                                                 WALL       "
            + "                                                      |\n   "
            + "                       WALL          Cell-11{TUNNEL}[]<>()  "
            + "                         WALL Cell-12{CAVE}[]<Monster-2>(A,A"
            + ")                           WALL   Cell-13{CAVE}[]<Monster-0"
            + ">()                           WALL         Cell-14{CAVE}[]<>"
            + "(A,A)                           WALL         Cell-15{TUNNEL}"
            + "[]<>(A)                           WALL\n                    "
            + "                                        |                   "
            + "                                       WALL                 "
            + "                                         WALL               "
            + "                                              |             "
            + "                                                |\n         "
            + "                 WALL         Cell-16{CAVE}[]<>(A,A)        "
            + "            <--------->       Cell-17{CAVE}[S,S,D]<>()      "
            + "              <--------->           Cell-18{CAVE}[R]<>()    "
            + "                       WALL          Cell-19{TUNNEL}[]<>()  "
            + "                  <--------->          Cell-20{TUNNEL}[]<>()"
            + "                           WALL\n                           "
            + "                                 |                          "
            + "                                   |                        "
            + "                                  WALL                      "
            + "                                    WALL                    "
            + "                                      WALL\n                "
            + "          WALL         Cell-21{CAVE}[R,S]<>()               "
            + "            WALL       Cell-22{TUNNEL}[]<>(A,A)             "
            + "       <--------->          Cell-23{TUNNEL}[]<>()           "
            + "         <--------->         Cell-24{TUNNEL}[]<>(A)         "
            + "           <--------->           Cell-25{CAVE}[S]<>()       "
            + "                    WALL\n                                  "
            + "                       WALL                                 "
            + "                         WALL                               "
            + "                           WALL                             "
            + "                             WALL                           "
            + "                               WALL\n";
    assertEquals(expectedGrid, secondGameGrid);
  }
}