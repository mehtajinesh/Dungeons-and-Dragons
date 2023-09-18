# About/Overview

The world for our game consists of a dungeon, a network of tunnels and caves that are interconnected
so that player can explore the entire world by traveling from cave to cave through the tunnels that
connect them. According to Wikipedia in their description of an adventure
game (<https://en.wikipedia.org/wiki/Adventure_game>), "the player takes on the role of the
protagonist in an interactive story driven by exploration and/or puzzle solving". This game is a
text-based (<https://en.wikipedia.org/wiki/Text-based_game>) game, the way the game is played is
through printable text data, usually through a console.

## List of features

   1. The game can create both wrapping and non-wrapping dungeons with different degrees of
      interconnectivity
   2. The game provides support for three types of treasure: diamonds, rubies, and sapphires.
   3. The client will add treasure to 20% of the caves randomly.
   4. The client will add arrows to 20% of the locations randomly.
   5. The game allows a player to enter the dungeon at the start.
   6. The game provides a description of the player that includes name, path traversed and a
      description of what treasure the player has collected, arrows collected.
   7. The game provides a description of the player's location that includes a description of treasure
      in the room and the possible moves (north, east, south, west) that the player can make from their
      current location, available treasure and available arrows in the location.
   8. The game allows a player to move from their current location.
   9. The game allows a player to pick up treasure that is located in their same location.
   10. The game allows a player to slay a monster with bow and arrows.
   11. The client can select the difficulty of the game by adding more monster to the game.
   12. The game allows the player to smell across the two layer neighbouring location to get an
      estimated danger.

## How To Run

1. Run the "TextBasedAdventure.jar" file to test the program with the following command. Check the
   following section for usage information:

```console
   java -jar TextBasedAdventure.jar 0 4 4 0 20 2
```

## How To Use

1. Download the zip package.
2. Go to the res folder.
3. There is a java available called "Dungeons_Revised.jar" which can be used to play the game.
4. We need to pass the following command line args in the provided order:
    1. Wrap Type - 0 for Non-Wrap and 1 for Wrap.
    2. Row Count - Integer Number greater than 3.
    3. Column Count - Integer Number greater than 3.
    4. Interconnectivity - Integer Number greater than -1.
    5. Treasure Percentage - Integer Number between range 0 and 100.
    6. Monster Count - Integer Number between range 0 and 20.

## Examples

1. PlayerArrowPickupLosingGame.txt
   1. Showing welcome message.
   2. Player-1 starts at Cell-04 with available direction to move: West.
   3. Player-1 tries to pickup two arrows available in the cave.
   4. Player-1 acquires the two arrows and in the next display of current cell, we see that there is no arrows available.
   5. Player-1 moves to West direction.
   6. Player-1 reached at Cell-03 with available direction to move: East, West.
   7. Player-1 tries to pickup two arrows available in the cave.
   8. Player-1 acquires the two arrows and in the next display of current cell, we see that there is no arrows available.
   9. Player-1 moves to West direction.
   10. Player-1 reached at Cell-02 with available direction to move: East, West.
   11. Player-1 tries to pickup two arrows available in the cave.
   12. Player-1 acquires the two arrows and in the next display of current cell, we see that there is no arrows available.
   13. Player-1 moves to West direction.
   14. Player-1 reached at Cell-01 with available direction to move: East, South.
   15. Player-1 tries to pickup two arrows available in the cave.
   16. Player-1 acquires the two arrows and in the next display of current cell, we see that there is no arrows available.
   17. Player-1 shoots an arrow in South direction with distance 1.
   18. Player-1 misses the shot.
   19. Player-1 moves to South direction.
   20. Player-1 reached at Cell-05 with available direction to move: North, East, South.
   21. Game warns player about a weak smell.
   22. Player-1 moves to East direction.
   23. Player-1 reached at Cell-06 with available direction to move: East, West.
   24. Game warns player about a strong smell.
   25. Player-1 moves to East direction.
   26. Player-1 reached at Cell-07 with available direction to move: East, West.
   27. Player-1 moves to East direction.
   28. Player-1 gets eaten up by monster.
   29. Game ended with player summary.

2. PlayerEatenByMonster.txt
   1. Showing welcome message.
   2. Player-1 starts at Cell-04 with available direction to move: West.
   3. Player-1 tries to pickup two arrows available in the cave.
   4. Player-1 acquires the two arrows and in the next display of current cell, we see that there is no arrows available.
   5. Player-1 moves to West direction.
   6. Player-1 reached at Cell-03 with available direction to move: East, West.
   7. Player-1 tries to pickup two arrows available in the cave.
   8. Player-1 acquires the two arrows and in the next display of current cell, we see that there is no arrows available.
   9. Player-1 moves to West direction.
   10. Player-1 reached at Cell-02 with available direction to move: East, West.
   11. Player-1 tries to pickup two arrows available in the cave.
   12. Player-1 acquires the two arrows and in the next display of current cell, we see that there is no arrows available.
   13. Player-1 moves to West direction.
   14. Player-1 reached at Cell-01 with available direction to move: East, South.
   15. Player-1 tries to pickup two arrows available in the cave.
   16. Player-1 acquires the two arrows and in the next display of current cell, we see that there is no arrows available.
   17. Player-1 shoots an arrow in South direction with distance 1.
   18. Player-1 misses the shot.
   19. Player-1 moves to South direction.
   20. Player-1 reached at Cell-05 with available direction to move: North, East, South.
   21. Game warns player about a weak smell.
   22. Player-1 moves to East direction.
   23. Player-1 reached at Cell-06 with available direction to move: East, West.
   24. Game warns player about a strong smell.
   25. Player-1 moves to East direction.
   26. Player-1 reached at Cell-07 with available direction to move: East, West.
   27. Player-1 moves to East direction.
   28. Player-1 gets eaten up by monster.
   29. Game ended with player summary.

3. PlayerWinningReachingEnd.txt:
   1. Showing welcome message.
   2. Player-1 starts at Cell-04 with available direction to move: West.
   3. Player-1 moves in West direction.
   4. Player-1 reaches Cell-03 with available direction to move: West, East.
   5. Player-1 moves in West direction.
   6. Player-1 reaches Cell-02 with available direction to move: West, East.
   7. Player-1 moves in West direction.
   8. Player-1 reaches Cell-01 with available direction to move: South, East.
   9. Player-1 moves in South direction.
   10. Player-1 reaches Cell-05 with available direction to move: North, South, East.
   11. Player-1 moves in East direction.
   12. Player-1 reaches Cell-06 with available direction to move: West, East.
   13. Game notifies player about a weak smell of monster.
   14. Player-1 shoots an arrow in East direction at a distance of 1.
   15. The arrow gets successfully hit. Monster injured.
   16. Game notifies player about a weak smell of monster.
   17. Player-1 shoots an arrow in East direction at a distance of 1.
   18. The arrow gets successfully hit. Monster dead.
   19. Player-1 moves to East direction.
   20. Player-1 reaches Cell-07 with available direction to move: West, East.
   21. Player-1 moves to East direction.
   22. As this is the end cave, the player wins.
   23. A summary of the player is shown in the end.

4. Combining "player navigating through the dungeon" and "player winning the game by reaching the end" into PlayerKillingMonsterWinningReachingEnd.txt:
   1. Showing welcome message.
   2. Player-1 starts at Cell-04 with available direction to move: West.
   3. Player-1 moves in West direction.
   4. Player-1 reaches Cell-03 with available direction to move: West, East.
   5. Player-1 moves in West direction.
   6. Player-1 reaches Cell-02 with available direction to move: West, East.
   7. Player-1 moves in West direction.
   8. Player-1 reaches Cell-01 with available direction to move: South, East.
   9. Player-1 moves in South direction.
   10. Player-1 reaches Cell-05 with available direction to move: North, South, East.
   11. Player-1 moves in East direction.
   12. Player-1 reaches Cell-06 with available direction to move: West, East.
   13. Game notifies player about a weak smell of monster.
   14. Player-1 shoots an arrow in East direction at a distance of 1.
   15. The arrow gets successfully hit. Monster injured.
   16. Game notifies player about a weak smell of monster.
   17. Player-1 shoots an arrow in East direction at a distance of 1.
   18. The arrow gets successfully hit. Monster dead.
   19. Player-1 moves to East direction.
   20. Player-1 reaches Cell-07 with available direction to move: West, East.
   21. Player-1 moves to East direction.
   22. As this is the end cave, the player wins.
   23. A summary of the player is shown in the end.

## Design/Model Changes

1. Move the traverse logic for arrow from game and moved it into cell.
2. Added private methods.
3. Structured the design by creating packages for model and controller. (Done in folders and
   intellij packages)

## Assumptions

1. A Treasure can have at max three types of treasure and a minimum of one.
2. A Cave can only have treasure.
3. A minimum size of 3*3 is expected in the dungeon.
4. All the treasure in the room is picked up at once.
5. In the grid, '*' show current location of player and [D,R,S] represents the treasure in the cell.
    1. D- Diamonds
    2. R- Rubies
    3. S- Sapphires
6. A player cannot kill himself/herself using the arrow.
7. A location can have at arrow count from 1 to 3 inclusive.
8. All the arrows in a location are picked up at once.
9. A player can fire only one arrow in a turn.
10. A cave can only have one monster.
11. Commands in the terminal when entering options are case-sensitive.

## Limitations/Future Scope

- Provide GUI based dungeon to play the game rather than user based console game.

## Citations

- Algorithm Reference
  - <https://www.geeksforgeeks.org/count-occurrences-elements-list-java/>
  - <https://www.geeksforgeeks.org/minimum-number-of-edges-between-two-vertices-of-a-graph/>
  - <https://www.geeksforgeeks.org/find-distance-between-two-nodes-of-a-binary-tree/>
- Others:
  - <https://www.w3schools.com/java/java_regex.asp>
  - <https://stackoverflow.com/questions/604424/how-to-get-an-enum-value-from-a-string-value-in-java>
