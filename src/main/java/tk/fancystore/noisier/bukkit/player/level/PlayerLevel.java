package tk.fancystore.noisier.bukkit.player.level;

import com.google.common.collect.ImmutableList;
import org.bukkit.inventory.ItemStack;
import tk.fancystore.noisier.Manager;
import tk.fancystore.noisier.bukkit.player.Profile;
import tk.fancystore.noisier.database.data.container.LevelContainer;
import tk.fancystore.noisier.plugin.config.NConfig;
import tk.fancystore.noisier.plugin.logger.CoreLogger;
import tk.fancystore.noisier.utils.BukkitUtils;
import tk.fancystore.noisier.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class PlayerLevel {

  private final int index;
  private final int exp;
  private final String description;
  private final List<PlayerLevelReward> rewards;

  public PlayerLevel(int exp, String description, List<PlayerLevelReward> rewards) {
    this.index = levels.size() + 1;
    this.exp = exp;
    this.description = description;
    this.rewards = rewards;
  }

  public int getIndex() {
    return index;
  }

  public int getExp() {
    return exp;
  }

  public String getDescription() {
    return description;
  }

  public List<PlayerLevelReward> getRewards() {
    return rewards;
  }

  public int getExperienceUntil(int current) {
    PlayerLevel level = this.getNext();

    if (level == null) {
      return 0;
    }

    return level.getExp() - current;
  }

  public ItemStack getIcon(Profile profile) {
    return BukkitUtils.deserializeItemStack(profile.getLevelContainer().getLevel().getIndex() >= index ? (profile.getLevelContainer().isClaimed(index) ?
        "MINECART : 1 : name>&cRecompensa do Nível " + index + " : lore>" + description + "\n \n&cVocê já coletou esta recompensa!"  :
        "STORAGE_MINECART : 1 : name>&aRecompensa do Nível " + index + " : lore>" + description + "\n \n&eClique para coletar esta recmpensa!") :
        "STORAGE_MINECART : 1 : name>&cRecompensa do Nível " + index + " : lore>" + description + "\n \n&cVocê precisa atingir o nível " + index + " para coletar!"
    );
  }

  public PlayerLevel getNext() {
    return levels.stream().filter(level -> level.getIndex() == this.index + 1).findFirst().orElse(null);
  }

  public String getProgressBar(Profile profile) {
    return getProgressBar(profile, "▇", "§a", "§7");
  }

  public String getProgressBar(Profile profile, String symbol, String completedColor, String remainColor) {
    int exp = 0;

    PlayerLevel level = this.getNext();
    if (level != null) {
      exp = level.getExp();
    }

    return Utils.makeProgressBar(profile.getLevelContainer().getExp(), exp, 10, symbol, completedColor, remainColor);
  }

  public static final CoreLogger LOGGER = Manager.getCorePlugin().getCoreLogger().getModule("Level");
  private static final List<PlayerLevel> levels = new ArrayList<>();

  public static void setupLevels() {
    NConfig config = Manager.getCorePlugin().getConfig("levels");
    for (String key : config.getSection("levels").getKeys()) {
      int exp = config.getInt("levels." + key + ".exp");
      String description = config.getString("levels." + key + ".description");
      List<PlayerLevelReward> rewards = new ArrayList<>();
      for (String reward : config.getStringList("levels." + key + ".rewards")) {
        rewards.add(new PlayerLevelReward(reward));
      }

      levels.add(new PlayerLevel(exp, description, rewards));
    }
  }

  public static PlayerLevel getByLevel(int level) {
    return levels.get(level - 1);
  }

  public static List<PlayerLevel> listLevels() {
    return ImmutableList.copyOf(levels);
  }
}
