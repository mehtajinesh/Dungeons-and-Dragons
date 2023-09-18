package controllertesting;

import game.model.DungeonCell;
import game.model.DungeonCellImpl;
import game.model.DungeonsGame;
import game.model.GameHistory;
import game.model.IGameHistory;
import game.model.PlayerData;
import game.model.PlayerDataImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * This class is used to mock the model so that the controller can be tested in isolation.
 */
public class MockModel implements DungeonsGame {

  private final StringBuilder log;
  private final int uniqueCode;
  private GameState gameState;

  /**
   * This function is used to create mock model for testing the controller in
   * isolation.
   *
   * @param log        log where the input is stored.
   * @param uniqueCode unique code to see the output is correct.
   * @throws IllegalArgumentException If the log is invalid
   */
  public MockModel(StringBuilder log, int uniqueCode) throws IllegalArgumentException {
    if (log == null) {
      throw new IllegalStateException("Invalid logger provided");
    }
    this.log = log;
    this.uniqueCode = uniqueCode;
  }

  @Override
  public void startGameForPlayer(String playerName) throws IllegalArgumentException {
    log.append("Player Name: ").append(playerName).append("\n");
    log.append("Updating Game state to progress.").append("\n");
    gameState = GameState.PROGRESS;
  }

  @Override
  public MovementStates movePlayerToLocation(Direction direction) throws IllegalArgumentException {
    log.append("Movement Direction: ").append(direction).append("\n");
    return MovementStates.MOVE_SUCCESS;
  }

  @Override
  public GameState getGameStatus() {
    log.append("Getting Game State: ").append(gameState).append("\n");
    return gameState;
  }

  @Override
  public List<List<DungeonCell>> getDungeonCells() {
    log.append("Getting Four Cells with designed unique code as name").append("\n");
    DungeonCell cell1 = new DungeonCellImpl(String.valueOf(uniqueCode));
    DungeonCell cell2 = new DungeonCellImpl(String.valueOf(uniqueCode));
    DungeonCell cell3 = new DungeonCellImpl(String.valueOf(uniqueCode));
    DungeonCell cell4 = new DungeonCellImpl(String.valueOf(uniqueCode));
    List<DungeonCell> cells = new ArrayList<>();
    cells.add(cell1);
    cells.add(cell2);
    cells.add(cell3);
    cells.add(cell4);
    List<List<DungeonCell>> total = new ArrayList<>();
    total.add(cells);
    return total;
  }

  @Override
  public IGameHistory getGameHistory() {
    log.append("Create two dummy game entries: move and monster hit").append("\n");
    IGameHistory gameHistory = new GameHistory();
    gameHistory.addToHistory(GameEvents.MOVE, String.valueOf(uniqueCode));
    gameHistory.addToHistory(GameEvents.MONSTER_HIT, String.valueOf(uniqueCode));
    return gameHistory;
  }

  @Override
  public String getGameGrid() {
    return String.valueOf(uniqueCode);
  }

  @Override
  public ShootResultStates shootArrowForPlayer(Direction direction, int distance)
          throws IllegalArgumentException, IllegalStateException {
    log.append("Shoot Direction: ").append(direction).append("\n");
    log.append("Shoot Distance: ").append(distance).append("\n");
    return ShootResultStates.MONSTER_MISSED;
  }

  @Override
  public String getPlayerCurrentLocationInformation() throws IllegalStateException {
    return String.valueOf(uniqueCode);
  }

  @Override
  public PlayerData getPlayerInformation() throws IllegalStateException {
    return new PlayerDataImpl(String.valueOf(uniqueCode), new HashMap<>(),
            new ArrayList<>(), new ArrayList<>());
  }

  @Override
  public void pickUpTreasureCurrentLocation() throws IllegalStateException {
    log.append("Picking up treasure from current location");
  }

  @Override
  public void pickUpArrowCurrentLocation() throws IllegalStateException {
    log.append("Picking up arrow from current location");
  }

  @Override
  public void reset() {
    log.append("Resetting the model");
  }

  @Override
  public void setupNewGame(WrapType wrapType, int rowCount, int columnCount, int interConnectivity,
                           int distributionPercent, int monsterCount) {
    log.append("Wrap Type: ").append(wrapType).append("\n");
    log.append("Row Count: ").append(rowCount).append("\n");
    log.append("Col Count: ").append(columnCount).append("\n");
    log.append("InterC: ").append(interConnectivity).append("\n");
    log.append("Treasure Percent: ").append(distributionPercent).append("\n");
    log.append("Monster Count: ").append(monsterCount).append("\n");
  }

  @Override
  public SmellType getCurrentLocationSmell() {
    return SmellType.NO_SMELL;
  }

  @Override
  public DungeonCell getPlayerCurrentLocation() throws IllegalStateException {
    return new DungeonCellImpl(String.valueOf(uniqueCode));
  }

  @Override
  public List<String> getAvailableMovements() {
    List<String> mockList = new ArrayList<>();
    mockList.add(String.valueOf(uniqueCode));
    mockList.add(String.valueOf(uniqueCode));
    mockList.add(String.valueOf(uniqueCode));
    mockList.add(String.valueOf(uniqueCode));
    return mockList;
  }
}
