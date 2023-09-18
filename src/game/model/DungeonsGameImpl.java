package game.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * This class is used to implement the dungeons game. It provides the client to access
 * various functionalities of the game. This includes movement of player, starting the game
 * with a player and much more.
 */
public class DungeonsGameImpl implements DungeonsGame {

  private List<List<DungeonCell>> gameGrid;
  private GameState gameState;
  private String startCellName;
  private String endCellName;
  private Player currentPlayer;
  private WrapType wrapType;
  private int rowCount;
  private int colCount;
  private int interConnectivity;
  private int distributionPercent;
  private int monsterCount;
  private RandomNumberGenerator randomizer;
  private IGameHistory gameHistory;

  /**
   * This function is used to create a dungeons game based on provided user inputs.
   *
   * @param wrapType            type of wrap
   * @param rowCount            number of rows in dungeons game
   * @param columnCount         number of columns in dungeons game
   * @param interConnectivity   interconnectivity number
   * @param distributionPercent distribution percentage for treasure and arrow to be present in game
   * @param monsterCount        number of monsters to be created
   * @param randomizer          randomizer instance
   * @throws IllegalArgumentException if any row or column count are invalid or other args are null
   */
  public DungeonsGameImpl(WrapType wrapType, int rowCount, int columnCount,
                          int interConnectivity, int distributionPercent, int monsterCount,
                          RandomNumberGenerator randomizer)
          throws IllegalArgumentException {
    setupDungeon(wrapType, rowCount, columnCount, interConnectivity,
            distributionPercent, monsterCount, randomizer);
  }

  private void setupDungeon(WrapType wrapType, int rowCount, int columnCount,
                            int interConnectivity, int distributionPercent, int monsterCount,
                            RandomNumberGenerator randomizer) throws IllegalArgumentException {
    if (wrapType == null || randomizer == null) {
      throw new IllegalArgumentException("Input parameters cannot be null");
    }
    if (rowCount < 0 || columnCount < 0) {
      throw new IllegalArgumentException("Row or column cannot be negative");
    }
    if (rowCount < 4 || columnCount < 4) {
      throw new IllegalArgumentException("Row or column less than 4 is not a valid input."
              + " Please provide a dimension input of at least 4*4.");
    }
    if (interConnectivity < 0 || distributionPercent < 0) {
      throw new IllegalArgumentException("Neither Treasure Percent or "
              + "Interconnectivity cannot be less than zero");
    }
    if (distributionPercent > 100) {
      throw new IllegalArgumentException("Treasure Percent cannot be more than 100");
    }
    if (monsterCount < 1) {
      throw new IllegalArgumentException("Number of monsters should be at least one");
    }
    //save data to model
    this.wrapType = wrapType;
    this.rowCount = rowCount;
    this.colCount = columnCount;
    this.interConnectivity = interConnectivity;
    this.distributionPercent = distributionPercent;
    this.monsterCount = monsterCount;
    this.randomizer = randomizer;
    currentPlayer = null;
    gameHistory = new GameHistory();
    gameGrid = new ArrayList<>();
    startCellName = "";
    endCellName = "";
    gameState = GameState.NOT_STARTED;
    createGrid(wrapType, rowCount, columnCount, interConnectivity);
    classifyLocations();
    generateStartEndPoints();
    createAndAssignTreasures(distributionPercent);
    createAndAssignMonsters(monsterCount);
    createAndAssignArrows(distributionPercent);
  }

  /**
   * This function is used to create a grid based on the provided inputs.
   *
   * @param wrapType          type of wrap
   * @param rowCount          number of rows in dungeons game
   * @param columnCount       number of columns in dungeons game
   * @param interConnectivity interconnectivity number
   * @throws IllegalArgumentException if the interconnectivity > left over paths
   */
  private void createGrid(WrapType wrapType, int rowCount, int columnCount,
                          int interConnectivity) throws IllegalArgumentException {
    //get the linkage map between cell and a group
    Map<String, Integer> mapCellGroup = createAllDungeonCells(rowCount, columnCount);
    // create a list for left over paths
    // Set of "Location A", "Location B"
    List<Set<String>> uniqueLeftOverPaths = new ArrayList<>();
    // get the list of potential paths
    List<Set<String>> listPotentialPaths = findAllPotentialPaths(wrapType);
    // iterate through all the potential paths and create a 0 interconnectivity graph
    while (listPotentialPaths.size() != 0) {
      // select a path randomly from the potential paths available
      int randomIndex = randomizer.generateRandomValueForRange(0, listPotentialPaths.size());
      Set<String> chosenPotentialPath = listPotentialPaths.get(randomIndex);
      List<String> listChosenPotentialPath = new ArrayList<>(chosenPotentialPath);
      String firstCellName = listChosenPotentialPath.get(0);
      int firstCellGroup = mapCellGroup.get(firstCellName);
      String secondCellName = listChosenPotentialPath.get(1);
      int secondCellGroup = mapCellGroup.get(secondCellName);
      // check if both the cells are already part of the same group
      if (firstCellGroup == secondCellGroup) {
        // both cells are part of the same group, so add them to left over path
        uniqueLeftOverPaths.add(chosenPotentialPath);
      } else {
        // both cells are part of different group, so make them part of one group
        // Note: I am taking group of first cell and moving the second cell group to
        // first cell group.
        // Also find all the cells which are part of group 2 and make it part of group 1.
        for (String cellName : mapCellGroup.keySet()) {
          if (secondCellGroup == mapCellGroup.get(cellName)) {
            mapCellGroup.replace(cellName, firstCellGroup);
          }
        }
        // validate potential neighbours to actual neighbours for both the cells
        DungeonCell firstCell = getCellFromGrid(firstCellName);
        DungeonCell secondCell = getCellFromGrid(secondCellName);
        firstCell.activateNeighbour(secondCell);
        secondCell.activateNeighbour(firstCell);
      }
      // remove the selected path from the potential list
      listPotentialPaths.remove(randomIndex);
    }
    // now we have a grid with zero interconnectivity.
    if (interConnectivity > uniqueLeftOverPaths.size()) {
      throw new IllegalArgumentException("Interconnectivity "
              + "provided is not possible with the current grid");
    }
    int selectionCount = 0;
    while (selectionCount != interConnectivity) {
      int randomIndex = randomizer.generateRandomValueForRange(0, uniqueLeftOverPaths.size());
      Set<String> chosenPotentialPath = uniqueLeftOverPaths.get(randomIndex);
      List<String> listChosenPotentialPath = new ArrayList<>(chosenPotentialPath);
      String firstCellName = listChosenPotentialPath.get(0);
      String secondCellName = listChosenPotentialPath.get(1);
      // validate potential neighbours to actual neighbours for both the cells
      DungeonCell firstCell = getCellFromGrid(firstCellName);
      DungeonCell secondCell = getCellFromGrid(secondCellName);
      firstCell.activateNeighbour(secondCell);
      secondCell.activateNeighbour(firstCell);
      uniqueLeftOverPaths.remove(randomIndex);
      selectionCount++;
    }

  }

  private DungeonCell getCellFromGrid(String cellName) {
    DungeonCell dungeonCell = null;
    for (List<DungeonCell> rowDungeonCells : this.gameGrid) {
      for (DungeonCell columnDungeonCell : rowDungeonCells) {
        if (columnDungeonCell.getCellName().equals(cellName)) {
          dungeonCell = columnDungeonCell;
          break;
        }
      }
    }
    return dungeonCell;
  }

  private Map<String, Integer> createAllDungeonCells(int rowCount, int columnCount) {
    //create a map to store linkage between cell and a group
    Map<String, Integer> mapGroupCells = new HashMap<>();
    // create a counter for groups
    int groupCounter = 0;
    // create a 2d grid for given row and columns
    for (int row = 0; row < rowCount; row++) {
      // create a list for row cells
      List<DungeonCell> listOfRowCells = new ArrayList<>();
      for (int column = 0; column < columnCount; column++) {
        //create a cell/location with a unique name for the game
        String cellName = String.format("Cell-%02d",
                ++groupCounter);
        DungeonCell dungeonCell = new DungeonCellImpl(cellName);
        // add cell name with the corresponding group
        mapGroupCells.put(cellName, groupCounter);
        listOfRowCells.add(dungeonCell);
      }
      this.gameGrid.add(listOfRowCells);
    }
    return mapGroupCells;
  }

  private List<Set<String>> findAllPotentialPaths(WrapType wrapType) {
    // create a set for potential paths
    // Set of "Location A", "Location B"
    List<Set<String>> potentialPaths = new ArrayList<>();
    // Left to Right Traversal
    for (List<DungeonCell> rowDungeonCells : this.gameGrid) {
      for (int columnIndex = 0; columnIndex < rowDungeonCells.size(); columnIndex++) {
        // get the current cell
        DungeonCell currentDungeonCell = rowDungeonCells.get(columnIndex);
        // check we have reached towards the end of the column list
        if ((columnIndex != (rowDungeonCells.size() - 1))) {
          DungeonCell nextNeighbourDungeonCell = rowDungeonCells.get(columnIndex + 1);
          List<DungeonCell> dungeonCellList = new ArrayList<>();
          dungeonCellList.add(currentDungeonCell);
          dungeonCellList.add(nextNeighbourDungeonCell);
          List<Direction> directionList = new ArrayList<>();
          directionList.add(Direction.EAST);
          directionList.add(Direction.WEST);
          Set<String> potentialPath = createPotentialPath(dungeonCellList, directionList);
          potentialPaths.add(potentialPath);
        } else {
          // we have reached the end of the column list
          // check if wrapping is allowed or not, if not then don't do anything and move to next
          if (wrapType == WrapType.WRAPPING) {
            // then we add the current cell and next cell into the potential list
            DungeonCell wrappingNeighbourDungeonCell = rowDungeonCells.get(0);
            List<DungeonCell> dungeonCellList = new ArrayList<>();
            dungeonCellList.add(currentDungeonCell);
            dungeonCellList.add(wrappingNeighbourDungeonCell);
            List<Direction> directionList = new ArrayList<>();
            directionList.add(Direction.EAST);
            directionList.add(Direction.WEST);
            Set<String> potentialWrappingPath = createPotentialPath(dungeonCellList, directionList);
            potentialPaths.add(potentialWrappingPath);
          }
        }
      }
    }
    // Up to Down Traversal
    for (int row = 0; row < this.gameGrid.size(); row++) {
      // get current row dungeon cells (column cells)
      List<DungeonCell> currentRowDungeonCells = this.gameGrid.get(row);
      // check if we have reached towards the end
      if (row != (this.gameGrid.size() - 1)) {
        // get neighbouring row dungeon cells (column cells)
        List<DungeonCell> neighbourRowDungeonCells = this.gameGrid.get(row + 1);
        // make potential paths for each item in current row dungeon cell
        // with neighbouring row cells via index mapping (see example below)
        // Current -> A,B,C and Neighbour -> D,E,F then potential paths will be
        // A->D, B->E, C->F.
        potentialPaths.addAll(createPotentialPathForRowCells(currentRowDungeonCells,
                neighbourRowDungeonCells));
      } else {
        //we have reached the end
        // check if wrapping is allowed or not, if not then don't do anything
        if (wrapType == WrapType.WRAPPING) {
          // get neighbouring row dungeon cells (column cells). first row as we have reached end
          List<DungeonCell> neighbourRowDungeonCells = this.gameGrid.get(0);
          // then we add the current row cells and neighbouring cell into the potential list
          potentialPaths.addAll(createPotentialPathForRowCells(currentRowDungeonCells,
                  neighbourRowDungeonCells));
        }
      }
    }
    return potentialPaths;
  }

  private List<Set<String>> createPotentialPathForRowCells(
          List<DungeonCell> currentRowDungeonCells, List<DungeonCell> neighbourRowDungeonCells) {
    List<Set<String>> potentialRowPaths = new ArrayList<>();
    for (int index = 0; index < currentRowDungeonCells.size(); index++) {
      List<DungeonCell> dungeonCellList = new ArrayList<>();
      dungeonCellList.add(currentRowDungeonCells.get(index));
      dungeonCellList.add(neighbourRowDungeonCells.get(index));
      List<Direction> directionList = new ArrayList<>();
      directionList.add(Direction.SOUTH);
      directionList.add(Direction.NORTH);
      Set<String> potentialPath = createPotentialPath(dungeonCellList, directionList);
      potentialRowPaths.add(potentialPath);
    }
    return potentialRowPaths;
  }

  private Set<String> createPotentialPath(List<DungeonCell> dungeonCells,
                                          List<Direction> directions) {
    Set<String> potentialPath = new HashSet<>();
    for (int index = 0; index < dungeonCells.size(); index++) {
      DungeonCell dungeonCell = dungeonCells.get(index);
      potentialPath.add(dungeonCell.getCellName());
      if (index == dungeonCells.size() - 1) {
        dungeonCell.addPotentialNeighbour(dungeonCells.get(0).getCellName(),
                directions.get(index));
      } else {
        dungeonCell.addPotentialNeighbour(dungeonCells.get(index + 1).getCellName(),
                directions.get(index));
      }
    }
    return potentialPath;
  }

  private void generateStartEndPoints() {
    List<DungeonCell> allDungeonLocations = getAllLocations();
    boolean validEndNodeFound = false;
    List<Integer> alreadyVisitedNodes = new ArrayList<>();
    while ((!validEndNodeFound) && (alreadyVisitedNodes.size() != allDungeonLocations.size())) {
      // select a start point at random
      int randomStartIndex = randomizer.generateRandomValueForRange(0, allDungeonLocations.size());
      if (alreadyVisitedNodes.contains(randomStartIndex)) {
        //node already visited so move to next one.
        continue;
      }
      // check if there is any end point which has at least 5 nodes of distance
      // gather all those nodes and add it to a list
      List<DungeonCell> listValidNodes = getNodesWithDistanceMoreThanFour(randomStartIndex);
      if (listValidNodes.size() == 0) {
        // no end point available. Try another node
        alreadyVisitedNodes.add(randomStartIndex);
      } else {
        //found at least on valid node
        // update start cell name
        DungeonCell startNode = allDungeonLocations.get(randomStartIndex);
        this.startCellName = startNode.getCellName();
        //choose a random end node from the list and break out of the loop
        int randomEndIndex = randomizer.generateRandomValueForRange(0, listValidNodes.size());
        DungeonCell endNode = listValidNodes.get(randomEndIndex);
        //update end cell name
        this.endCellName = endNode.getCellName();
        validEndNodeFound = true;
      }
    }
    if (!validEndNodeFound) {
      throw new IllegalArgumentException("Provided dimensions for dungeons are "
              + "not sufficient to find a valid start and end location");
    }
  }

  private void classifyLocations() {
    //Left to Right Traversal and Up to Down Traversal
    for (List<DungeonCell> rowCells : gameGrid) {
      for (DungeonCell dungeonCell : rowCells) {
        if (dungeonCell.getActualNeighbours().size() == 2) {
          dungeonCell.updateCellType(DungeonCell.CellType.TUNNEL);
        } else {
          dungeonCell.updateCellType(DungeonCell.CellType.CAVE);
        }
      }
    }
  }

  private void createAndAssignTreasures(int treasurePercent) {
    //get number of canvas needed for dumping some treasure
    // number of caves = ceil(total canvas * (treasurePercent/100))
    List<DungeonCell> caves = getAllCaves();
    int numberOfTreasureCaves = (int) Math.ceil(caves.size() * treasurePercent * 0.01);
    int countTreasureAssigned = 0;
    List<Integer> alreadyVisitedIndexes = new ArrayList<>();
    while (countTreasureAssigned != numberOfTreasureCaves) {
      int randomCaveIndex = randomizer.generateRandomValueForRange(0, caves.size());
      if (alreadyVisitedIndexes.contains(randomCaveIndex)) {
        continue;
      }
      DungeonCell cell = caves.get(randomCaveIndex);
      List<TreasureType> generatedTreasures = generateRandomTreasure();
      cell.addTreasureToCell(generatedTreasures);
      alreadyVisitedIndexes.add(randomCaveIndex);
      countTreasureAssigned++;
    }
  }

  private void createAndAssignMonsters(int monsterCount) {
    //get all caves
    List<DungeonCell> caves = getAllCaves();
    if (monsterCount > caves.size()) {
      throw new IllegalArgumentException(
              "Number of monsters cannot be greater than number of caves.");
    }
    for (int count = 0; count < monsterCount; count++) {
      Monster monster = new Otyughs("Monster-" + count);
      DungeonCell cell;
      if (count == 0) {
        // first iteration, so create a monster and assign it to end node as it is mandatory
        cell = getCellFromGrid(endCellName);
      } else {
        // for all other iterations, for other caves apart from starting cave
        // randomly choose a cave and assign the newly created monster
        String selectedCaveName;
        while (true) {
          int randomIndex = randomizer.generateRandomValueForRange(0, caves.size());
          DungeonCell potentialCave = caves.get(randomIndex);
          if (potentialCave.getCellName().equals(startCellName)) {
            // monster cannot be added to start cave
            continue;
          }
          if (potentialCave.getMonster() != null) {
            // monster cannot be added to the cave which already has a monster
            continue;
          }
          selectedCaveName = potentialCave.getCellName();
          break;
        }
        cell = getCellFromGrid(selectedCaveName);
      }
      cell.assignMonster(monster);
    }

  }

  private void createAndAssignArrows(int arrowPercent) {
    //get number of locations needed for dumping some treasure
    // number of locations = ceil(total locations * (arrowPercent/100))
    List<DungeonCell> dungeonLocations = getAllLocations();
    int numberOfArrowLocations = (int) Math.ceil(dungeonLocations.size() * arrowPercent * 0.01);
    int countArrowAssigned = 0;
    List<Integer> alreadyVisitedIndexes = new ArrayList<>();
    while (countArrowAssigned != numberOfArrowLocations) {
      int randomLocationIndex = randomizer.generateRandomValueForRange(0, dungeonLocations.size());
      if (alreadyVisitedIndexes.contains(randomLocationIndex)) {
        continue;
      }
      DungeonCell dungeonCell = dungeonLocations.get(randomLocationIndex);
      int randomNumberOfArrows = randomizer.generateRandomValueForRange(1, 4);
      dungeonCell.createNumberOfArrows(randomNumberOfArrows);
      alreadyVisitedIndexes.add(randomLocationIndex);
      countArrowAssigned++;
    }
  }

  private List<DungeonCell> getNodesWithDistanceMoreThanFour(int startNodeIndex) {
    List<DungeonCell> allDungeonLocations = getAllLocations();
    List<DungeonCell> endNodes = new ArrayList<>();
    int totalLocations = allDungeonLocations.size();

    // visited[n] for keeping track of visited
    // node in BFS
    Map<Integer, Boolean> visitedNodes = new HashMap<>();
    for (int counter = 0; counter < totalLocations; counter++) {
      visitedNodes.put(counter, false);
    }
    // Initialize distances as 0
    Map<Integer, Integer> distanceNode = new HashMap<>();
    for (int counter = 0; counter < totalLocations; counter++) {
      distanceNode.put(counter, 0);
    }
    Queue<Integer> nodeQueue = new LinkedList<>();
    nodeQueue.add(startNodeIndex);
    visitedNodes.put(startNodeIndex, true);
    while (!nodeQueue.isEmpty()) {
      int nodeSearchIndex = nodeQueue.peek();
      nodeQueue.poll();
      Map<Direction, DungeonCell> actualNeighbours = allDungeonLocations.get(
              nodeSearchIndex).getActualNeighbours();
      List<String> neighbourNames = new ArrayList<>();
      for (DungeonCell cell : actualNeighbours.values()) {
        neighbourNames.add(cell.getCellName());
      }
      for (String neighbourName : neighbourNames) {
        int neighbourIndex = allDungeonLocations.indexOf(getCellFromGrid(neighbourName));
        if (visitedNodes.get(neighbourIndex)) {
          continue;
        }
        // update distance for current search index
        distanceNode.put(neighbourIndex, distanceNode.get(nodeSearchIndex) + 1);
        nodeQueue.add(neighbourIndex);
        visitedNodes.put(neighbourIndex, true);
      }
    }
    //check in distance for all nodes which have distance of at least 5
    for (int index : distanceNode.keySet()) {
      int distanceValue = distanceNode.get(index);
      DungeonCell cell = allDungeonLocations.get(index);
      if (distanceValue > 4 && cell.getCellType() == DungeonCell.CellType.CAVE) {
        endNodes.add(cell);
      }
    }
    return endNodes;
  }

  private List<TreasureType> generateRandomTreasure() {
    List<TreasureType> generatedTreasures = new ArrayList<>();
    List<TreasureType> allTreasureTypes = List.of(TreasureType.values());
    int randomTreasureListSize = randomizer.generateRandomValueForRange(1,
            allTreasureTypes.size() + 1);
    for (int count = 0; count < randomTreasureListSize; count++) {
      int randomTreasureIndex = randomizer.generateRandomValueForRange(0, allTreasureTypes.size());
      generatedTreasures.add(allTreasureTypes.get(randomTreasureIndex));
    }
    return generatedTreasures;
  }

  private List<DungeonCell> getAllCaves() {
    List<DungeonCell> caves = new ArrayList<>();
    for (List<DungeonCell> rowCells : gameGrid) {
      for (DungeonCell dungeonCell : rowCells) {
        if (dungeonCell.getCellType() == DungeonCell.CellType.CAVE) {
          caves.add(dungeonCell);
        }
      }
    }
    return caves;
  }

  private List<DungeonCell> getAllLocations() {
    List<DungeonCell> dungeonCellList = new ArrayList<>();
    for (List<DungeonCell> rowDungeonCells : gameGrid) {
      dungeonCellList.addAll(rowDungeonCells);
    }
    return dungeonCellList;
  }

  @Override
  public List<List<DungeonCell>> getDungeonCells() {
    List<List<DungeonCell>> cells = new ArrayList<>();
    for (List<DungeonCell> rowDungeonCells : this.gameGrid) {
      List<DungeonCell> rowItems = new ArrayList<>();
      for (DungeonCell columnDungeonCell : rowDungeonCells) {
        DungeonCell copiedCell = new DungeonCellImpl(columnDungeonCell);
        rowItems.add(copiedCell);
      }
      cells.add(rowItems);
    }
    return cells;
  }

  @Override
  public IGameHistory getGameHistory() {
    // game history is a map of immutable data types.
    return new GameHistory(gameHistory);
  }

  @Override
  public void startGameForPlayer(String playerName) throws IllegalArgumentException {
    if (playerName == null || playerName.isEmpty()) {
      throw new IllegalArgumentException("Invalid player");
    }
    if (currentPlayer == null) {
      currentPlayer = new PlayerImpl(playerName);
    }
    currentPlayer.addCellAsVisited(startCellName);
    gameHistory.addToHistory(GameEvents.MOVE, startCellName);
    gameState = GameState.PROGRESS;
  }

  @Override
  public MovementStates movePlayerToLocation(Direction direction)
          throws IllegalArgumentException, IllegalStateException {
    if (gameState != GameState.PROGRESS) {
      throw new IllegalStateException("Game has not been started yet. "
              + "Please start the game first.");
    }
    String currentCellName = currentPlayer.getCurrentDungeonLocation();
    if (direction == null) {
      throw new IllegalArgumentException("Direction provided cannot be null or empty");
    }
    DungeonCell dungeonCurrentCell = getCellFromGrid(currentCellName);
    Map<Direction, DungeonCell> availableDirectionsForNeighbours = dungeonCurrentCell
            .getActualNeighbours();
    boolean neighbourValid = availableDirectionsForNeighbours.containsKey(direction);
    if (!neighbourValid) {
      throw new IllegalArgumentException("No such direction/neighbour exists. "
              + "Please check the grid and try again.");
    }
    String dungeonLocationName = availableDirectionsForNeighbours.get(direction).getCellName();
    currentPlayer.addCellAsVisited(dungeonLocationName);
    gameHistory.addToHistory(GameEvents.MOVE, dungeonLocationName);
    DungeonCell nextCell = getCellFromGrid(dungeonLocationName);
    //check if monster exists
    Monster monster = nextCell.getMonster();
    if (monster != null) {
      Monster.Health monsterHealth = monster.getHealth();
      if (monsterHealth == Monster.Health.FULL) {
        gameState = GameState.LOST;
        return MovementStates.PLAYER_EATEN;
      } else if (monsterHealth == Monster.Health.HALF) {
        int survivalChance = randomizer.generateRandomValueForRange(0, 2);
        if (survivalChance == 0) {
          gameState = GameState.LOST;
          return MovementStates.PLAYER_EATEN;
        } else {
          if (dungeonLocationName.equals(endCellName)) {
            gameState = GameState.WON;
          }
          return MovementStates.ESCAPE_MONSTER;
        }
      }
    }
    if (dungeonLocationName.equals(endCellName)) {
      gameState = GameState.WON;
    }
    return MovementStates.MOVE_SUCCESS;
  }

  @Override
  public String getGameGrid() {
    List<List<String>> leftRightAllLinesDirection = new ArrayList<>();
    List<List<String>> topAllLinesDirection = new ArrayList<>();
    List<String> bottomDirection = new ArrayList<>();
    //Left to Right Traversal and Up to Down Traversal
    for (int row = 0; row < gameGrid.size(); row++) {
      List<String> leftRightDirection = new ArrayList<>();
      List<String> topDirection = new ArrayList<>();
      List<DungeonCell> rowCells = gameGrid.get(row);
      for (int column = 0; column < rowCells.size(); column++) {
        DungeonCell dungeonCell = rowCells.get(column);
        Map<Direction, DungeonCell> currentCellNeighbours =
                dungeonCell.getActualNeighbours();
        // check for Left (West)
        DungeonCell westNeighbour = currentCellNeighbours.get(Direction.WEST);
        if (westNeighbour == null) {
          // no west neighbour, so put a wall
          leftRightDirection.add(String.format("%30s", "WALL"));
        } else {
          // bidirectional connection
          leftRightDirection.add(String.format("%30s", "<--------->"));
        }
        StringBuilder cellInformation = new StringBuilder();
        if (currentPlayer != null) {
          //if the game has started
          // add * to show the current location of the player
          String currentPlayerLocation = currentPlayer.getCurrentDungeonLocation();
          if (currentPlayerLocation.equals(dungeonCell.getCellName())) {
            cellInformation.append("*");
          }
        }
        cellInformation.append(dungeonCell.getCellName());
        cellInformation.append(String.format("{%s}", dungeonCell.getCellType()));
        List<TreasureType> treasures = dungeonCell.getAvailableTreasures();
        cellInformation.append("[");
        String prefix = "";
        for (TreasureType treasure : treasures) {
          cellInformation.append(prefix);
          prefix = ",";
          if (treasure == TreasureType.DIAMONDS) {
            cellInformation.append("D");
          } else if (treasure == TreasureType.RUBIES) {
            cellInformation.append("R");
          } else {
            cellInformation.append("S");
          }
        }
        cellInformation.append("]<");
        Monster monster = dungeonCell.getMonster();
        if (monster != null) {
          cellInformation.append(monster.getName());
        }
        cellInformation.append(">");
        cellInformation.append("(");
        prefix = "";
        List<ArrowType> arrowTypes = dungeonCell.getAvailableArrows();
        for (int count = 0; count < arrowTypes.size(); count++) {
          cellInformation.append(prefix);
          prefix = ",";
          cellInformation.append("A");
        }
        cellInformation.append(")");
        leftRightDirection.add(String.format("%30s", cellInformation));
        // check for Right (East) when last column
        if (column == (rowCells.size() - 1)) {
          DungeonCell eastNeighbour = currentCellNeighbours.get(Direction.EAST);
          if (eastNeighbour == null) {
            // no east neighbour, so put a wall
            leftRightDirection.add(String.format("%30s", "WALL"));
          } else {
            // bidirectional connection
            leftRightDirection.add(String.format("%30s", "<--------->"));
          }
        }
        topDirection.add(String.format("%30s", " "));
        // check for Up (North)
        DungeonCell northNeighbour = currentCellNeighbours.get(Direction.NORTH);
        if (northNeighbour == null) {
          // no north neighbour, so put a wall
          topDirection.add(String.format("%30s", "WALL"));
        } else {
          // bidirectional connection
          topDirection.add(String.format("%30s", "|"));
        }
        //check for last row
        if (row == (gameGrid.size() - 1)) {
          bottomDirection.add(String.format("%30s", " "));
          // check for down (South)
          DungeonCell southNeighbour = currentCellNeighbours.get(Direction.SOUTH);
          if (southNeighbour == null) {
            // no south neighbour, so put a wall
            bottomDirection.add(String.format("%30s", "WALL"));
          } else {
            // bidirectional connection
            bottomDirection.add(String.format("%30s", "|"));
          }
        }
      }
      leftRightAllLinesDirection.add(leftRightDirection);
      topAllLinesDirection.add(topDirection);
    }
    // finally, make the string
    StringBuilder fullGrid = new StringBuilder();
    for (int rowCount = 0; rowCount < gameGrid.size(); rowCount++) {
      List<String> topLine = topAllLinesDirection.get(rowCount);
      fullGrid.append(String.join(" ", topLine));
      fullGrid.append("\n");
      List<String> middleLine = leftRightAllLinesDirection.get(rowCount);
      fullGrid.append(String.join(" ", middleLine));
      fullGrid.append("\n");
    }
    // put last row grid information
    fullGrid.append(String.join(" ", bottomDirection));
    fullGrid.append("\n");
    return fullGrid.toString();
  }

  @Override
  public GameState getGameStatus() {
    return gameState;
  }

  @Override
  public ShootResultStates shootArrowForPlayer(Direction direction, int distance)
          throws IllegalArgumentException, IllegalStateException {
    if (gameState != GameState.PROGRESS) {
      throw new IllegalStateException("Game has not been started yet. "
              + "Please start the game first.");
    }
    if (direction == null) {
      throw new IllegalArgumentException("Invalid direction provided by the player");
    }
    if (distance < 0 || distance > 10) {
      throw new IllegalArgumentException("Distance cannot be less than 0 "
              + "nor can it be more than 10");
    }
    //check if the player has at least one arrow left to shoot
    boolean anyArrowsLeft = currentPlayer.checkForAtLeastOneArrow();
    if (!anyArrowsLeft) {
      // if no, then throw an exception, that no arrows left
      throw new IllegalStateException("Player doesn't have any arrows to shoot");
    }
    currentPlayer.reduceAvailableArrowByOne();
    // if there is at least one arrow, then make the arrow traverse from current cell
    String currentLocation = currentPlayer.getCurrentDungeonLocation();
    gameHistory.addToHistory(GameEvents.ARROW_SHOOT, currentLocation);
    DungeonCell currentCell = getCellFromGrid(currentLocation);
    Map<Direction, DungeonCell> neighbours = currentCell.getActualNeighbours();
    DungeonCell nextNeighbour = neighbours.get(direction);
    if (nextNeighbour == null) {
      return ShootResultStates.MONSTER_MISSED;
    }
    if (distance > 0) {
      if (nextNeighbour.getCellType() == DungeonCell.CellType.CAVE) {
        distance = distance - 1;
      }
    }
    IArrowShootResult result;
    switch (direction) {
      case EAST:
        result = nextNeighbour.traverseArrow(Direction.WEST, distance);
        break;
      case WEST:
        result = nextNeighbour.traverseArrow(Direction.EAST, distance);
        break;
      case NORTH:
        result = nextNeighbour.traverseArrow(Direction.SOUTH, distance);
        break;
      case SOUTH:
        result = nextNeighbour.traverseArrow(Direction.NORTH, distance);
        break;
      default:
        result = new ArrowShootResult(ShootResultStates.MONSTER_MISSED, currentLocation);
        break;
    }
    if (result.getShootState() != ShootResultStates.MONSTER_MISSED) {
      gameHistory.addToHistory(GameEvents.MONSTER_HIT, result.getCellName());
    }
    return result.getShootState();
  }

  @Override
  public String getPlayerCurrentLocationInformation() throws IllegalStateException {
    if (gameState == GameState.NOT_STARTED) {
      throw new IllegalStateException("Game has not been started yet. "
              + "Please start the game first.");
    }
    String currentCellName = currentPlayer.getCurrentDungeonLocation();
    StringBuilder playerCurrentLocationInformation = new StringBuilder();
    //current location
    playerCurrentLocationInformation.append(String.format("Player's Current Location: %s\n",
            currentCellName));
    DungeonCell currentCell = getCellFromGrid(currentCellName);
    //current location type
    playerCurrentLocationInformation.append(String.format("Player's Current Location Type: %s\n",
            currentCell.getCellType()));
    // treasure information
    List<TreasureType> availableTreasure = currentCell.getAvailableTreasures();
    playerCurrentLocationInformation.append("Available Treasure:\n");
    if (availableTreasure.size() == 0) {
      playerCurrentLocationInformation.append("None\n");
    } else {
      for (TreasureType treasure : availableTreasure) {
        playerCurrentLocationInformation.append(treasure.toString()).append("\n");
      }
    }
    // arrow information
    List<ArrowType> availableArrow = currentCell.getAvailableArrows();
    playerCurrentLocationInformation.append(
            String.format("Available Arrows:%s\n", availableArrow.size()));
    // monster information
    Monster monster = currentCell.getMonster();
    if (monster == null) {
      playerCurrentLocationInformation.append("No Monster Available\n");
    } else {
      playerCurrentLocationInformation.append(
              String.format("Monster Name:%s\n", monster.getName()));
      playerCurrentLocationInformation.append(
              String.format("Monster Health:%s\n", monster.getHealth()));
    }
    // available directions
    playerCurrentLocationInformation.append("Available Direction For Movement:\n");
    Map<Direction, DungeonCell> neighbours = currentCell.getActualNeighbours();
    for (Direction direction : neighbours.keySet()) {
      playerCurrentLocationInformation.append(String.format("%s : %s\n", direction.toString(),
              neighbours.get(direction).getCellName()));
    }
    return playerCurrentLocationInformation.toString();
  }

  @Override
  public PlayerData getPlayerInformation() throws IllegalStateException {
    if (currentPlayer == null) {
      throw new IllegalStateException("Game has not been started yet. "
              + "Please start the game first.");
    }
    return new PlayerDataImpl(currentPlayer.getName(), currentPlayer.getTreasure(),
            currentPlayer.getTravelledPath(), currentPlayer.getAvailableArrows());
  }

  @Override
  public void pickUpTreasureCurrentLocation() throws IllegalStateException {
    if (gameState == GameState.NOT_STARTED) {
      throw new IllegalStateException("Game has not been started yet. "
              + "Please start the game first.");
    }
    String currentCellLocation = currentPlayer.getCurrentDungeonLocation();
    DungeonCell dungeonCell = getCellFromGrid(currentCellLocation);
    List<TreasureType> availableTreasures = dungeonCell.getAvailableTreasures();
    if (availableTreasures.size() == 0) {
      throw new IllegalStateException("No treasure available for pick up. "
              + "Please move the location of the player.");
    }
    for (TreasureType treasure : availableTreasures) {
      currentPlayer.captureTreasure(treasure);
    }
    dungeonCell.clearCellTreasure();
    gameHistory.addToHistory(GameEvents.TREASURE_PICKUP, currentCellLocation);
  }

  @Override
  public void pickUpArrowCurrentLocation() throws IllegalStateException {
    if (gameState == GameState.NOT_STARTED) {
      throw new IllegalStateException("Game has not been started yet. "
              + "Please start the game first.");
    }
    String currentCellLocation = currentPlayer.getCurrentDungeonLocation();
    DungeonCell dungeonCell = getCellFromGrid(currentCellLocation);
    List<ArrowType> availableArrows = dungeonCell.getAvailableArrows();
    if (availableArrows.size() == 0) {
      throw new IllegalStateException("No arrows available for pick up. "
              + "Please move the location of the player.");
    }
    for (int count = 0; count < availableArrows.size(); count++) {
      currentPlayer.addArrow();
    }
    dungeonCell.clearCellArrows();
    gameHistory.addToHistory(GameEvents.ARROW_PICKUP, currentCellLocation);
  }

  @Override
  public void reset() {
    randomizer.setRandomSeed(randomizer.getRandomSeed());
    //clear data
    clearData();
    setupDungeon(wrapType, rowCount, colCount, interConnectivity,
            distributionPercent, monsterCount, randomizer);
  }

  private void clearData() {
    gameGrid.clear();
    startCellName = "";
    endCellName = "";
    gameHistory.clear();
  }

  @Override
  public void setupNewGame(WrapType wrapType, int rowCount, int columnCount, int interConnectivity,
                           int distributionPercent, int monsterCount)
          throws IllegalArgumentException {
    int existingSeed = randomizer.getRandomSeed();
    int newSeed;
    do {
      newSeed = randomizer.generateRandomValueForRange(0, 10000);
    }
    while (newSeed == existingSeed);
    randomizer.setRandomSeed(newSeed);
    // validation of all arguments happen in setupDungeon function
    setupDungeon(wrapType, rowCount, columnCount,
            interConnectivity, distributionPercent,
            monsterCount, randomizer);
  }

  @Override
  public SmellType getCurrentLocationSmell() throws IllegalStateException {
    if (currentPlayer == null) {
      throw new IllegalStateException("Game has not been started yet. "
              + "Please start the game first.");
    }
    DungeonCell location = getCellFromGrid(currentPlayer.getCurrentDungeonLocation());
    // check for current cell
    Monster monster = location.getMonster();
    if ((monster != null) && (monster.getHealth() != Monster.Health.ZERO)) {
      return SmellType.STRONG;
    }
    // check for 1-layer neighbours
    Map<Direction, DungeonCell> neighbours = location.getActualNeighbours();
    List<String> neighbourNames = new ArrayList<>();
    for (DungeonCell cell : neighbours.values()) {
      neighbourNames.add(cell.getCellName());
    }
    SmellType detectedSmell = null;
    for (String neighbourName : neighbourNames) {
      DungeonCell neighbourCave = getCellFromGrid(neighbourName);
      Monster neighbourCaveMonster = neighbourCave.getMonster();
      if ((neighbourCaveMonster != null)
              && (neighbourCaveMonster.getHealth() != Monster.Health.ZERO)) {
        // if number of monster is present -> Strong Smell
        detectedSmell = SmellType.STRONG;
        break;
      }
    }
    if (detectedSmell == null) {
      // check for two layers
      int monsterCount = 0;
      for (String neighbourName : neighbourNames) {
        DungeonCell neighbourCave = getCellFromGrid(neighbourName);
        Map<Direction, DungeonCell> neighbourNeighbours = neighbourCave
                .getActualNeighbours();
        List<String> neighbourNeighbourNames = new ArrayList<>();
        for (DungeonCell cell : neighbourNeighbours.values()) {
          neighbourNeighbourNames.add(cell.getCellName());
        }
        for (String neighbourNeighbourName : neighbourNeighbourNames) {
          DungeonCell neighbourNeighbourCave = getCellFromGrid(neighbourNeighbourName);
          Monster neighbourNeighbourCaveMonster = neighbourNeighbourCave.getMonster();
          if ((neighbourNeighbourCaveMonster != null)
                  && (neighbourNeighbourCaveMonster.getHealth() != Monster.Health.ZERO)) {
            // if number of monster is present -> Strong Smell
            monsterCount++;
            if (monsterCount > 1) {
              break;
            }
          }
        }
      }
      if (monsterCount == 1) {
        detectedSmell = SmellType.WEAK;
      } else if (monsterCount > 1) {
        detectedSmell = SmellType.STRONG;
      } else {
        detectedSmell = SmellType.NO_SMELL;
      }
    }
    return detectedSmell;
  }

  @Override
  public DungeonCell getPlayerCurrentLocation() throws IllegalStateException {
    if (currentPlayer == null) {
      throw new IllegalStateException("Game has not been started yet. "
              + "Please start the game first.");
    }
    return new DungeonCellImpl(getCellFromGrid(currentPlayer.getCurrentDungeonLocation()));
  }

  @Override
  public List<String> getAvailableMovements() throws IllegalStateException {
    if (currentPlayer == null) {
      throw new IllegalStateException("Game has not been started yet. "
              + "Please start the game first.");
    }
    List<String> availableDirection = new ArrayList<>();
    DungeonCell currentCell = getCellFromGrid(currentPlayer.getCurrentDungeonLocation());
    Map<Direction, DungeonCell> neighbours = currentCell.getActualNeighbours();
    for (Direction direction : neighbours.keySet()) {
      availableDirection.add(direction.toString());
    }
    Collections.sort(availableDirection);
    return availableDirection;
  }
}
