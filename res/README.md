# About/Overview

The world for our game consists of a dungeon, a network of tunnels and caves that are interconnected
so that player can explore the entire world by traveling from cave to cave through the tunnels that
connect them. According to Wikipedia in their description of an adventure
game (<https://en.wikipedia.org/wiki/Adventure_game>), "the player takes on the role of the
protagonist in an interactive story driven by exploration and/or puzzle solving". This game is a
graphical-based game, the way the game is played is through printable text data, usually through a
console. Also, a graphical view is added on top of that, to make it look more user-friendly.

## List of features

1. The game can create both wrapping and non-wrapping dungeons with different degrees of
   interconnectivity
2. The game provides support for three types of treasure: diamonds, rubies, and sapphires.
3. The client will add treasure to x% of the caves randomly.
4. The client will add arrows to x% of the locations randomly.
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
13. For GUI, To Shoot Arrow: Use the arrows on the keyboard with 's' pressed simultaneously
14. For GUI, To Move Player: Use the arrows on the keyboard or click on the nearby cells.
15. For GUI, To Pickup Treasure: Use the 't' key to pickup treasure from current location
16. For GUI, To Pickup Arrow: Use the 'a' key to pickup arrow from current location
17. For GUI, click on Player Description Button to see player summary.

## How To Run

1. Run the "Dungeons.jar" file to test the program with the following command. Check the following
   section for usage information:
    1. For Console Based Game:

```console
   java -jar Dungeons.jar 0 4 4 0 20 2
```

2.For Graphical Based Game:

```console
   java -jar Dungeons.jar
```

## How To Use

1. Download the zip package.
2. Go to the res folder.
3. There is a java available called "Dungeons.jar" which can be used to play the game.
4. Two choices - if we want to play command line game, then
    1. We need to pass the following command line args in the provided order:
        1. Wrap Type - 0 for Non-Wrap and 1 for Wrap.
        2. Row Count - Integer Number greater than 3.
        3. Column Count - Integer Number greater than 3.
        4. Interconnectivity - Integer Number greater than -1.
        5. Treasure Percentage - Integer Number between range 0 and 100.
        6. Monster Count - Integer Number between range 0 and 20.
5. If the GUI game, just run the jar without any parameters.

## Examples

1. GameScreenshot.jpg Summary
    1. A dungeon of 5*5 is created and player started with a location.
    2. Next, player moved around the dungeon and killed a monster at the middle top of the game
       board.
    3. Also, when traversing the game, the player picked up two diamonds and three sapphires.
    4. Along with picking up the treasure, the player also picked up some arrows on the way.
    5. Finally, the player was roaming on the bottom part of the grid, the player found some smell
       coming. So, player killed one monster.
    6. However, there was still some strong smell coming. But in search of the monster, the player
       got killed.
    7. Hence, in the bottom of the JFrame, we can see that the game is ended.
    8. Next, on the click of Player Description button, a summary of player inventory is shown.

## Design/Model Changes

1. Initially, for the settings dialog, the plan was to use the gridlayout.
2. However, switched to spring layout based on java recommendations for input dialog.
3. Added private methods in all the class (not added in UML diagram to avoid cluttering)
4. Added two structures for passing data from model to view via controller:
    1. ArrowShootState
    2. GameHistory

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
12. If there are no arguments provided when the jar is executed, the GUI version is opened up
13. Else, the console version is opened (if all six required arguments are valid).

## Limitations/Future Scope

- Complete extra features like adding thief in the game, adding pit into the game and adding moving
  monster.

## Citations

- Algorithm Reference
    - <https://www.geeksforgeeks.org/count-occurrences-elements-list-java/>
    - <https://www.geeksforgeeks.org/minimum-number-of-edges-between-two-vertices-of-a-graph/>
    - <https://www.geeksforgeeks.org/find-distance-between-two-nodes-of-a-binary-tree/>
- Java UI
    - <https://docs.oracle.com/javase/tutorial/uiswing/layout/spring.html>
    - <https://docs.oracle.com/javase/tutorial/uiswing/layout/flow.html>
    - <https://docs.oracle.com/javase/7/docs/api/java/awt/event/ActionEvent.html>
    - <https://docs.oracle.com/javase/tutorial/uiswing/events/keylistener.html>
    - <https://docs.oracle.com/javase/8/docs/api/java/awt/event/InputEvent.html#getModifiersEx-->
- Others:
    - <https://www.w3schools.com/java/java_regex.asp>
    - <https://stackoverflow.com/questions/604424/how-to-get-an-enum-value-from-a-string-value-in-java>
