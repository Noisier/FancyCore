package tk.fancystore.noisier.bukkit.menus.profile;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import tk.fancystore.noisier.bukkit.Core;
import tk.fancystore.noisier.bukkit.achievements.Achievement;
import tk.fancystore.noisier.bukkit.achievements.types.BedWarsAchievement;
import tk.fancystore.noisier.bukkit.achievements.types.SkyWarsAchievement;
import tk.fancystore.noisier.bukkit.achievements.types.TheBridgeAchievement;
import tk.fancystore.noisier.bukkit.libraries.menu.PlayerMenu;
import tk.fancystore.noisier.bukkit.menus.MenuProfile;
import tk.fancystore.noisier.bukkit.menus.profile.achievements.MenuAchievementsList;
import tk.fancystore.noisier.bukkit.player.Profile;
import tk.fancystore.noisier.utils.BukkitUtils;
import tk.fancystore.noisier.utils.enums.EnumSound;

import java.util.List;

public class MenuAchievements extends PlayerMenu {

  public MenuAchievements(Profile profile) {
    super(profile.getPlayer(), "Desafios", 4);

    List<BedWarsAchievement> bedwars = Achievement.listAchievements(BedWarsAchievement.class);
    long max = bedwars.size();
    long completed = bedwars.stream().filter(achievement -> achievement.isCompleted(profile)).count();
    String color = (completed == max) ? "&a" : (completed > max / 2) ? "&7" : "&c";
    bedwars.clear();
    this.setItem(11, BukkitUtils.deserializeItemStack("BED : 1 : name>&aBed Wars : lore>&fDesafios: " + color + completed + "/" + max + "\n \n&eClique para visualizar!"));

    List<SkyWarsAchievement> skywars = Achievement.listAchievements(SkyWarsAchievement.class);
    max = skywars.size();
    completed = skywars.stream().filter(achievement -> achievement.isCompleted(profile)).count();
    color = (completed == max) ? "&a" : (completed > max / 2) ? "&7" : "&c";
    skywars.clear();
    this.setItem(13, BukkitUtils.deserializeItemStack("GRASS : 1 : name>&aSky Wars : lore>&fDesafios: " + color + completed + "/" + max + "\n \n&eClique para visualizar!"));

    List<TheBridgeAchievement> thebridge = Achievement.listAchievements(TheBridgeAchievement.class);
    max = thebridge.size();
    completed = thebridge.stream().filter(achievement -> achievement.isCompleted(profile)).count();
    color = (completed == max) ? "&a" : (completed > max / 2) ? "&7" : "&c";
    thebridge.clear();
    this.setItem(15,
        BukkitUtils.deserializeItemStack("STAINED_CLAY:11 : 1 : name>&aThe Bridge : lore>&fDesafios: " + color + completed + "/" + max + "\n \n&eClique para visualizar!"));

    this.setItem(31, BukkitUtils.deserializeItemStack("INK_SACK:1 : 1 : name>&cVoltar"));

    this.register(Core.getInstance());
    this.open();
  }

  @EventHandler
  public void onInventoryClick(InventoryClickEvent evt) {
    if (evt.getInventory().equals(this.getInventory())) {
      evt.setCancelled(true);

      if (evt.getWhoClicked().equals(this.player)) {
        Profile profile = Profile.getProfile(this.player.getName());
        if (profile == null) {
          this.player.closeInventory();
          return;
        }

        if (evt.getClickedInventory() != null && evt.getClickedInventory().equals(this.getInventory())) {
          ItemStack item = evt.getCurrentItem();

          if (item != null && item.getType() != Material.AIR) {
            if (evt.getSlot() == 11) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuAchievementsList<>(profile, "Bed Wars", BedWarsAchievement.class);
            } else if (evt.getSlot() == 13) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuAchievementsList<>(profile, "Sky Wars", SkyWarsAchievement.class);
            } else if (evt.getSlot() == 15) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuAchievementsList<>(profile, "The Bridge", TheBridgeAchievement.class);
            } else if (evt.getSlot() == 31) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuProfile(profile);
            }
          }
        }
      }
    }
  }

  public void cancel() {
    HandlerList.unregisterAll(this);
  }

  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent evt) {
    if (evt.getPlayer().equals(this.player)) {
      this.cancel();
    }
  }

  @EventHandler
  public void onInventoryClose(InventoryCloseEvent evt) {
    if (evt.getPlayer().equals(this.player) && evt.getInventory().equals(this.getInventory())) {
      this.cancel();
    }
  }
}
