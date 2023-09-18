package game.model;

import java.util.List;
import java.util.Map;

/**
 * This interface is used to represent a dungeon cell in dungeons game.
 * It includes information on the type of cell, the available directions,
 * neighbour cells and the treasures available in the cell.
 */
public interface DungeonCell {

  /**
   * This enum represents the type of cell used by a dungeon cell.
   */
  enum CellType {
    CAVE,
    TUNNEL
  }

  /**
   * This function is used to the name of the cell.
   *
   * @return name of the cell
   */
  String getCellName();

  /**
   * This function is used to the type of cell.
   *
   * @return type of cell
   */
  CellType getCellType();

  /**
   * This function is used to update the cell type of the current cell.
   *
   * @param cellType new type of cell.
   * @throws IllegalArgumentException if the cell type is invalid.
   */
  void updateCellType(CellType cellType) throws IllegalArgumentException;

  /**
   * This function is used to add potential neighbour with the corresponding direction.
   *
   * @param neighbourCellName neighbour cell name
   * @param direction     direction of the neighbour with respect to current cell
   * @throws IllegalArgumentException if input parameters are invalid (null)
   */
  void addPotentialNeighbour(String neighbourCellName, DungeonsGame.Direction direction)
          throws IllegalArgumentException;

  /**
   * This function is used to activate a neighbour from potential to actual.
   *
   * @param neighbourCell neighbour cell
   * @throws IllegalArgumentException if the neighbour cell is null
   */
  void activateNeighbour(DungeonCell neighbourCell) throws IllegalArgumentException;

  /**
   * This function is used to get the available neighbours along with directions
   * for the current cell.
   *
   * @return list of actual neighbours along with direction
   */
  Map<DungeonsGame.Direction, DungeonCell> getActualNeighbours();

  /**
   * This function is used to get the potential neighbours along with directions
   * for the current cell.
   *
   * @return list of potential neighbours along with direction
   */
  Map<String, DungeonsGame.Direction> getPotentialNeighbours();

  /**
   * This function is used to get a list of available treasures in the current cell.
   *
   * @return list of available treasures.
   */
  List<DungeonsGame.TreasureType> getAvailableTreasures();

  /**
   * This function is used to clear cell treasure once acquired.
   */
  void clearCellTreasure();

  /**
   * This function is used to add given treasures to the cell.
   *
   * @param treasures treasures to be added
   * @throws IllegalArgumentException if the treasure provided is invalid.
   */
  void addTreasureToCell(List<DungeonsGame.TreasureType> treasures) throws IllegalArgumentException;

  /**
   * This function is used to assign a given monster to the cave.
   *
   * @param monster monster
   * @throws IllegalArgumentException if the given monster is not valid.
   */
  void assignMonster(Monster monster) throws IllegalArgumentException;

  /**
   * This function is used to get assigned monster.
   *
   * @return returns monster instance if present, else null
   */
  Monster getMonster();

  /**
   * This function is used to get a list of available arrows in the current cell.
   *
   * @return list of available arrows.
   */
  List<DungeonsGame.ArrowType> getAvailableArrows();

  /**
   * This function is used to clear cell arrows once acquired.
   */
  void clearCellArrows();

  /**
   * This function is used to create number of arrows for this cell.
   *
   * @param numberOfArrows number of arrows to be added into the cell
   * @throws IllegalArgumentException if the number of arrows is less than zero
   */
  void createNumberOfArrows(int numberOfArrows) throws IllegalArgumentException;

  /**
   * This function is used to traverse an arrow in the given direction and for particular distance.
   *
   * @param direction direction of the arrow
   * @param distance  distance to fire
   * @return resulting state of the arrow fired
   * @throws IllegalArgumentException if the direction or distance is invalid
   */
  IArrowShootResult traverseArrow(DungeonsGame.Direction direction, int distance)
          throws IllegalArgumentException;

}
