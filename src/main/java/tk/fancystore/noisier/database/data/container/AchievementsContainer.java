package tk.fancystore.noisier.database.data.container;

import org.json.simple.JSONArray;
import tk.fancystore.noisier.bukkit.achievements.Achievement;
import tk.fancystore.noisier.database.data.DataContainer;
import tk.fancystore.noisier.database.data.interfaces.AbstractContainer;

@SuppressWarnings("unchecked")
public class AchievementsContainer extends AbstractContainer {

  public AchievementsContainer(DataContainer dataContainer) {
    super(dataContainer);
  }

  public void complete(Achievement achievement) {
    JSONArray achievements = this.dataContainer.getAsJsonArray();
    achievements.add(achievement.getId());
    this.dataContainer.set(achievements.toString());
    achievements.clear();
  }

  public boolean isCompleted(Achievement achievement) {
    return this.dataContainer.getAsJsonArray().contains(achievement.getId());
  }
}
