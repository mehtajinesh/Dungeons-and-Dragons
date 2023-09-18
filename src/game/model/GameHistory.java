package game.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the game history interface. This includes implementation for
 * adding events to the history and fetching the same from the history.
 */
public class GameHistory implements IGameHistory {
  private final List<DungeonsGame.GameEvents> events;
  private final List<String> locations;

  /**
   * Constructor for Game History.
   */
  public GameHistory() {
    this.events = new ArrayList<>();
    this.locations = new ArrayList<>();
  }

  /**
   * Copy Constructor for Game History.
   */
  public GameHistory(IGameHistory instance) {
    this.events = new ArrayList<>();
    this.locations = new ArrayList<>();
    for (int index = 0; index < instance.size(); index++) {
      this.events.add(instance.getEventFromHistory(index));
      this.locations.add(instance.getLocationFromHistory(index));
    }
  }

  @Override
  public void addToHistory(DungeonsGame.GameEvents event, String locationName)
          throws IllegalArgumentException {
    if (event == null || locationName == null) {
      throw new IllegalArgumentException("Invalid arguments provided");
    }
    events.add(event);
    locations.add(locationName);
  }

  @Override
  public DungeonsGame.GameEvents getEventFromHistory(int index) throws IllegalArgumentException {
    if (index < 0 || index >= events.size()) {
      throw new IllegalArgumentException("Invalid index provided");
    }
    return events.get(index);
  }

  @Override
  public String getLocationFromHistory(int index) throws IllegalArgumentException {
    if (index < 0 || index >= locations.size()) {
      throw new IllegalArgumentException("Invalid index provided");
    }
    return locations.get(index);
  }

  @Override
  public int size() {
    return events.size();
  }

  @Override
  public void clear() {
    events.clear();
    locations.clear();
  }
}
