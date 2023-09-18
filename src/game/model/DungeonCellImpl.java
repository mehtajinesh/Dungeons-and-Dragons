package game.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * This class is used to represent the dungeon cell in the dungeons game.
 * This includes properties like cell name, cell type, neighbour information
 * and much more.
 */
public class DungeonCellImpl implements DungeonCell {
  private final String cellName;
  private CellType cellType;
  private final Map<String, DungeonsGame.Direction> potentialNeighbours;
  private final Map<DungeonsGame.Direction, DungeonCell> actualNeighbours;
  private List<DungeonsGame.TreasureType> availableTreasures;
  private Monster currentMonster;
  private final List<DungeonsGame.ArrowType> availableArrows;
  private static final Map<DungeonsGame.Direction, DungeonsGame.Direction> entryExitMapping
          = new HashMap<>();

  static {
    entryExitMapping.put(DungeonsGame.Direction.EAST, DungeonsGame.Direction.WEST);
    entryExitMapping.put(DungeonsGame.Direction.WEST, DungeonsGame.Direction.EAST);
    entryExitMapping.put(DungeonsGame.Direction.NORTH, DungeonsGame.Direction.SOUTH);
    entryExitMapping.put(DungeonsGame.Direction.SOUTH, DungeonsGame.Direction.NORTH);
  }

  /**
   * This function is used to create a dungeon cell for the game with
   * the provided parameters.
   *
   * @param cellName name of the cell
   * @throws IllegalArgumentException if the parameters provided are invalid (null)
   */
  public DungeonCellImpl(String cellName) throws IllegalArgumentException {
    if (cellName == null) {
      throw new IllegalArgumentException("Invalid input parameters provided");
    }
    this.cellName = cellName;
    this.cellType = CellType.CAVE;
    this.potentialNeighbours = new HashMap<>();
    this.actualNeighbours = new TreeMap<>();
    this.availableTreasures = new ArrayList<>();
    currentMonster = null;
    availableArrows = new ArrayList<>();
  }

  /**
   * copy constructor.
   *
   * @param cell cell to be copied.
   */
  public DungeonCellImpl(DungeonCell cell) {
    this.cellName = cell.getCellName();
    this.cellType = cell.getCellType();
    this.actualNeighbours = cell.getActualNeighbours();
    this.potentialNeighbours = cell.getPotentialNeighbours();
    this.availableTreasures = cell.getAvailableTreasures();
    this.currentMonster = cell.getMonster();
    this.availableArrows = cell.getAvailableArrows();
  }

  @Override
  public String getCellName() {
    return cellName;
  }

  @Override
  public CellType getCellType() {
    return cellType;
  }

  @Override
  public void clearCellTreasure() {
    availableTreasures.clear();
  }

  @Override
  public List<DungeonsGame.ArrowType> getAvailableArrows() {
    return availableArrows;
  }

  @Override
  public void clearCellArrows() {
    availableArrows.clear();
  }

  @Override
  public Map<DungeonsGame.Direction, DungeonCell> getActualNeighbours() {
    return actualNeighbours;
  }

  @Override
  public Map<String, DungeonsGame.Direction> getPotentialNeighbours() {
    return potentialNeighbours;
  }

  @Override
  public void addPotentialNeighbour(String neighbourCellName,
                                    DungeonsGame.Direction direction)
          throws IllegalArgumentException {
    if (neighbourCellName == null || direction == null) {
      throw new IllegalArgumentException("Invalid neighbouring cell name or direction");
    }
    potentialNeighbours.put(neighbourCellName, direction);
  }

  @Override
  public void activateNeighbour(DungeonCell neighbour) throws IllegalArgumentException {
    if (neighbour == null) {
      throw new IllegalArgumentException("neighbouring cell cannot be null");
    }
    for (String neighbourName : potentialNeighbours.keySet()) {
      if (neighbour.getCellName().equals(neighbourName)) {
        actualNeighbours.put(potentialNeighbours.get(neighbourName), neighbour);
      }
    }
  }

  @Override
  public void updateCellType(CellType cellType) throws IllegalArgumentException {
    if (cellType == null) {
      throw new IllegalArgumentException("Invalid new cell type");
    }
    this.cellType = cellType;
  }

  @Override
  public List<DungeonsGame.TreasureType> getAvailableTreasures() {
    return availableTreasures;
  }

  @Override
  public void addTreasureToCell(List<DungeonsGame.TreasureType> treasure)
          throws IllegalArgumentException {
    if (treasure == null) {
      throw new IllegalArgumentException("Invalid treasure provided");
    }
    availableTreasures = treasure;
  }

  @Override
  public void assignMonster(Monster monster) throws IllegalArgumentException {
    if (monster == null) {
      throw new IllegalArgumentException("Invalid Monster provided");
    }
    currentMonster = monster;
  }

  @Override
  public Monster getMonster() {
    if (currentMonster == null) {
      return null;
    }
    return new Otyughs(currentMonster);
  }

  @Override
  public void createNumberOfArrows(int numberOfArrows) throws IllegalArgumentException {
    if (numberOfArrows < 0) {
      throw new IllegalArgumentException("Arrow count cannot be negative");
    }
    for (int count = 0; count < numberOfArrows; count++) {
      availableArrows.add(DungeonsGame.ArrowType.CROOKED_ARROW);
    }
  }

  @Override
  public IArrowShootResult traverseArrow(DungeonsGame.Direction entryDirection,
                                        int distance)
          throws IllegalArgumentException {
    if (entryDirection == null || distance < 0) {
      throw new IllegalArgumentException("Invalid entry direction or distance");
    }
    // check if the distance has reached
    if (distance == 0) {
      // check if there is monster present in the cave
      if (currentMonster == null) {
        //missed
        return new ArrowShootResult(DungeonsGame.ShootResultStates.MONSTER_MISSED, cellName);
      }
      // get monster health and check if already damaged.
      Monster.Health health = currentMonster.getHealth();
      if (health == Monster.Health.FULL) {
        currentMonster.updateHealth(Monster.Health.HALF);
        return new ArrowShootResult(DungeonsGame.ShootResultStates.MONSTER_HIT_PARTIAL_DAMAGE,
                cellName);
      } else if (health == Monster.Health.HALF) {
        currentMonster.updateHealth(Monster.Health.ZERO);
        return new ArrowShootResult(DungeonsGame.ShootResultStates.MONSTER_HIT_KILLED,
                cellName);
      } else {
        return new ArrowShootResult(DungeonsGame.ShootResultStates.MONSTER_MISSED, cellName);
      }
    }
    DungeonCell exitCell;
    DungeonsGame.Direction exitDirection;
    if (cellType == CellType.TUNNEL) {
      // if the location type is tunnel
      // get the other exit apart from the entry
      List<DungeonsGame.Direction> allNeighboursDirection =
              new ArrayList<>(actualNeighbours.keySet());
      allNeighboursDirection.remove(entryDirection);
      exitDirection = allNeighboursDirection.get(0);
    } else {
      // if the location type is cave
      exitDirection = entryExitMapping.get(entryDirection);
      if (!actualNeighbours.containsKey(exitDirection)) {
        // no exit present
        return new ArrowShootResult(DungeonsGame.ShootResultStates.MONSTER_MISSED, cellName);
      }
    }
    DungeonsGame.Direction newDirection = entryExitMapping.get(exitDirection);
    exitCell = actualNeighbours.get(exitDirection);
    if (exitCell.getCellType() == CellType.TUNNEL) {
      return exitCell.traverseArrow(newDirection, distance);
    } else {
      return exitCell.traverseArrow(newDirection, distance - 1);
    }
  }
}
