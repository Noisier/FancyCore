package tk.fancystore.noisier.bukkit.achievements.types;

import org.bukkit.inventory.ItemStack;
import tk.fancystore.noisier.bukkit.achievements.Achievement;
import tk.fancystore.noisier.bukkit.player.Profile;
import tk.fancystore.noisier.bukkit.titles.Title;
import tk.fancystore.noisier.utils.BukkitUtils;
import tk.fancystore.noisier.utils.StringUtils;

public class BedWarsAchievement extends Achievement {

  protected BedWarsReward reward;
  protected String icon;
  protected String[] stats;
  protected int reach;

  public BedWarsAchievement(BedWarsReward reward, String id, String name, String desc, int reach, String... stats) {
    super("bw-" + id, name);
    this.reward = reward;
    this.icon = "%material% : 1 : name>%name% : lore>" + desc + "\n \n&fProgresso: %progress%";
    this.stats = stats;
    this.reach = reach;
  }

  public static void setupAchievements() {
    Achievement.addAchievement(
        new BedWarsAchievement(new CoinsReward(100), "2k1", "Assasino (Duplas)", "&7Abata um total de %reach%\n&7jogadores para receber:\n \n &8• &6100 Coins",
            50, "2v2kills"));
    Achievement.addAchievement(
        new BedWarsAchievement(new CoinsReward(500), "2k2", "Assasino Mestre (Duplas)", "&7Abata um total de %reach%\n&7jogadores para receber:\n \n &8• &6500 Coins",
            250, "2v2kills"));
    Achievement.addAchievement(
        new BedWarsAchievement(new CoinsReward(250), "2w1", "Vitorioso (Duplas)", "&7Vença um total de %reach%\n&7partidas para receber:\n \n &8• &6250 Coins",
            50, "2v2wins"));
    Achievement.addAchievement(
        new BedWarsAchievement(new CoinsReward(1000), "2w2", "Vitorioso Mestre (Duplas)", "&7Vença um total de %reach%\n&7partidas para receber:\n \n &8• &61.000 Coins",
            200, "2v2wins"));
    Achievement.addAchievement(
        new BedWarsAchievement(new CoinsReward(250), "2d1", "Destruidor (Duplas)", "&7Vença um total de %reach%\n&7partidas para receber:\n \n &8• &6250 Coins", 250, "2v2bedsdestroyeds"));
    Achievement.addAchievement(
        new BedWarsAchievement(new CoinsReward(1000), "2d2", "Destruidor Mestre (Duplas)", "&7Vença um total de %reach%\n&7partidas para receber:\n \n &8• &61.000 Coins", 1000, "2v2bedsdestroyeds"));
    Achievement.addAchievement(
        new BedWarsAchievement(new CoinsReward(250), "2g1", "Persistente (Duplas)", "&7Jogue um total de %reach%\n&7partidas para receber:\n \n &8• &6250 Coins", 1000, "2v2games"));
    Achievement.addAchievement(
        new BedWarsAchievement(new CoinsReward(100), "4k1", "Assasino (Quartetos)", "&7Abata um total de %reach%\n&7jogadores para receber:\n \n &8• &6100 Coins", 50, "4v4kills"));
    Achievement.addAchievement(
        new BedWarsAchievement(new CoinsReward(500), "4k2", "Assasino Mestre (Quartetos)", "&7Abata um total de %reach%\n&7jogadores para receber:\n \n &8• &6500 Coins", 250, "4v4kills"));
    Achievement.addAchievement(
        new BedWarsAchievement(new CoinsReward(250), "4w1", "Vitorioso (Quartetos)", "&7Vença um total de %reach%\n&7partidas para receber:\n \n &8• &6250 Coins", 50, "4v4wins"));
    Achievement.addAchievement(
        new BedWarsAchievement(new CoinsReward(1000), "4w2", "Vitorioso Mestre (Quartetos)", "&7Vença um total de %reach%\n&7partidas para receber:\n \n &8• &61.000 Coins", 200, "4v4wins"));
    Achievement.addAchievement(
        new BedWarsAchievement(new CoinsReward(250), "4d1", "Destruidor (Quartetos)", "&7Vença um total de %reach%\n&7partidas para receber:\n \n &8• &6250 Coins", 250, "4v4bedsdestroyeds"));
    Achievement.addAchievement(
        new BedWarsAchievement(new CoinsReward(1000), "4d2", "Destruidor Mestre (Quartetos)", "&7Vença um total de %reach%\n&7partidas para receber:\n \n &8• &61.000 Coins", 1000, "4v4bedsdestroyeds"));
    Achievement.addAchievement(
        new BedWarsAchievement(new CoinsReward(250), "4g1", "Persistente (Quartetos)", "&7Jogue um total de %reach%\n&7partidas para receber:\n \n &8• &6250 Coins", 1000, "4v4games"));

    Achievement.addAchievement(
        new BedWarsAchievement(new TitleReward("bwk"), "bwk", "Asasino a espreita", "&7Abata um total de %reach%\n&7jogadores para receber:\n \n &8• &fTítulo: &cDestruidor de Sonhos", 500,
            "2v2kills", "4v4kills"));
    Achievement.addAchievement(
        new BedWarsAchievement(new TitleReward("bww"), "bww", "Protetor de Camas", "&7Vença um total de %reach%\n&7partidas para receber:\n \n &8• &fTítulo: &6Anjo Sonolento", 400,
            "2v2wins", "4v4wins"));
    Achievement.addAchievement(
        new BedWarsAchievement(new TitleReward("bwp"), "bwp", "Freddy Krueger", "&7Destrua um total de %reach%\n&7camas para receber:\n \n &8• &fTítulo: &4Pesadelo", 2000,
            "2v2bedsdestroyeds", "4v4bedsdestroyeds"));
  }

  @Override
  protected void give(Profile profile) {
    this.reward.give(profile);
  }

  @Override
  protected boolean check(Profile profile) {
    return profile.getStats("FancyCoreBedWars", this.stats) >= this.reach;
  }

  public ItemStack getIcon(Profile profile) {
    long current = profile.getStats("FancyCoreBedWars", this.stats);
    if (current > this.reach) {
      current = this.reach;
    }

    return BukkitUtils.deserializeItemStack(
        this.icon.replace("%material%", current == this.reach ? "ENCHANTED_BOOK" : "BOOK").replace("%name%", (current == this.reach ? "&a" : "&c") + this.getName())
            .replace("%current%", StringUtils.formatNumber(current)).replace("%reach%", StringUtils.formatNumber(this.reach))
            .replace("%progress%", (current == this.reach ? "&a" : current > this.reach / 2 ? "&7" : "&c") + current + "/" + this.reach));
  }

  interface BedWarsReward {
    void give(Profile profile);
  }

  static class CoinsReward implements BedWarsReward {

    private final double amount;

    public CoinsReward(double amount) {
      this.amount = amount;
    }

    @Override
    public void give(Profile profile) {
      profile.getDataContainer("FancyCoreBedWars", "coins").addDouble(this.amount);
    }
  }

  static class TitleReward implements BedWarsReward {
    private final String titleId;

    public TitleReward(String titleId) {
      this.titleId = titleId;
    }

    @Override
    public void give(Profile profile) {
      profile.getTitlesContainer().add(Title.getById(this.titleId));
    }
  }
}