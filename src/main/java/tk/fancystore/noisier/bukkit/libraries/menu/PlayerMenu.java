package tk.fancystore.noisier.bukkit.libraries.menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import tk.fancystore.noisier.bukkit.plugin.NBukkit;

public class PlayerMenu extends Menu implements Listener {

  protected Player player;

  public PlayerMenu(Player player, String title, int rows) {
    super(title, rows);
    this.player = player;
  }

  public void register(NBukkit plugin) {
    Bukkit.getPluginManager().registerEvents(this, plugin);
  }

  public void open() {
    this.player.openInventory(getInventory());
  }

  public Player getPlayer() {
    return player;
  }
}
