package game.model;

/**
 * This interface is used to represent the game history of the game.
 * It includes information regarding various events along with the
 * dungeon locations.
 */
public interface IGameHistory {

  /**
   * adding an event to the history along with the location.
   *
   * @param event        event to be added in history
   * @param locationName name of location where the event happened
   */
  void addToHistory(DungeonsGame.GameEvents event, String locationName)
          throws IllegalArgumentException;

  /**
   * get the event from the history based on the index provided.
   *
   * @param index location in the history
   * @return event at the given index
   */
  DungeonsGame.GameEvents getEventFromHistory(int index) throws IllegalArgumentException;

  /**
   * gets the location from the history based on the index provided.
   *
   * @param index index to be queried from the history
   * @return location name at the index in the history
   */
  String getLocationFromHistory(int index) throws IllegalArgumentException;

  /**
   * gets the size of the history.
   *
   * @return size of the history.
   */
  int size();

  /**
   * clears the game history.
   */
  void clear();
}
