package tk.fancystore.noisier.bukkit.menus.profile;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import tk.fancystore.noisier.bukkit.Core;
import tk.fancystore.noisier.bukkit.libraries.menu.PlayerMenu;
import tk.fancystore.noisier.bukkit.menus.MenuProfile;
import tk.fancystore.noisier.bukkit.player.Profile;
import tk.fancystore.noisier.utils.BukkitUtils;
import tk.fancystore.noisier.utils.enums.EnumSound;

public class MenuStatistics extends PlayerMenu {

  private String target;

  public MenuStatistics(Profile profile) {
    this(profile, profile.getName());
  }

  public MenuStatistics(Profile profile, String target) {
    super(profile.getPlayer(), "Estatísticas" + (profile.getName().equalsIgnoreCase(target) ? "" : " - " + target), 4);
    this.target = target;

    Player player = Bukkit.getPlayerExact(target);
    OfflinePlayer offlinePlayer = null;
    if (player == null) {
      //noinspection deprecation
      offlinePlayer = Bukkit.getOfflinePlayer(target);
    }

    this.setItem(11, BukkitUtils.deserializeItemStack(PlaceholderAPI.setPlaceholders(player == null ? offlinePlayer : player,
        "BED : 1 : name>&aBed Wars : lore>&eGeral:\n &8▪ &fPartidas: &7%FancyCore_BedWars_games%\n &8▪ &fAbates: &7%FancyCore_BedWars_kills%\n &8▪ &fMortes: &7%FancyCore_BedWars_deaths%\n &8▪ &fAbates Finais: &7%FancyCore_BedWars_finalkills%\n &8▪ &fMortes Finais: &7%FancyCore_BedWars_finaldeaths%\n &8▪ &fVitórias: &7%FancyCore_BedWars_wins%\n &8▪ &fCamas destruídas: &7%FancyCore_BedWars_bedsdestroyeds%\n &8▪ &fCamas perdidas: &7%FancyCore_BedWars_bedslosteds%\n \n&fCoins: &6%FancyCore_BedWars_coins%")));

    this.setItem(13, BukkitUtils.deserializeItemStack(PlaceholderAPI.setPlaceholders(player == null ? offlinePlayer : player,
        "GRASS : 1 : name>&aSky Wars : lore>&eSolo:\n &8▪ &fAbates: &7%FancyCore_SkyWars_1v1kills%\n &8▪ &fMortes: &7%FancyCore_SkyWars_1v1deaths%\n &8▪ &fVitórias: &7%FancyCore_SkyWars_1v1wins%\n &8▪ &fPartidas: &7%FancyCore_SkyWars_1v1games%\n &8▪ &fAssistências: &7%FancyCore_SkyWars_1v1assists%\n " + /*"\n&eDupla:\n &8▪ &fAbates: &7%FancyCore_SkyWars_2v2kills%\n &8▪ &fMortes: &7%FancyCore_SkyWars_2v2deaths%\n &8▪ &fVitórias: &7%FancyCore_SkyWars_2v2wins%\n &8▪ &fPartidas: &7%FancyCore_SkyWars_2v2games%\n &8▪ &fAssistências: &7%FancyCore_SkyWars_2v2assists%\n*/ "\n&eRanked:\n &8▪ &fAbates: &7%FancyCore_SkyWars_rankedkills%\n &8▪ &fMortes: &7%FancyCore_SkyWars_rankeddeaths%\n &8▪ &fVitórias: &7%FancyCore_SkyWars_rankedwins%\n &8▪ &fPartidas: &7%FancyCore_SkyWars_rankedgames%\n &8▪ &fPontos: &7%FancyCore_SkyWars_rankedpoints%\n \n&fCoins: &6%FancyCore_SkyWars_coins%")));

    this.setItem(15, BukkitUtils.deserializeItemStack(PlaceholderAPI.setPlaceholders(player == null ? offlinePlayer : player,
        "STAINED_CLAY:11 : 1 : name>&aThe Bridge : lore>&e1v1:\n &8▪ &fAbates: &7%FancyCore_TheBridge_1v1kills%\n &8▪ &fMortes: &7%FancyCore_TheBridge_1v1deaths%\n &8▪ &fPontos: &7%FancyCore_TheBridge_1v1points%\n &8▪ &fVitórias: &7%FancyCore_TheBridge_1v1wins%\n &8▪ &fPartidas: &7%FancyCore_TheBridge_1v1games%\n " + "\n&e2v2:\n &8▪ &fAbates: &7%FancyCore_TheBridge_2v2kills%\n &8▪ &fMortes: &7%FancyCore_TheBridge_2v2deaths%\n &8▪ &fPontos: &7%FancyCore_TheBridge_2v2points%\n &8▪ &fVitórias: &7%FancyCore_TheBridge_2v2wins%\n &8▪ &fPartidas: &7%FancyCore_TheBridge_2v2games%\n \n&eWinstreak:\n &8▪ &fDiário: &7%FancyCore_TheBridge_winstreak%\n \n&fCoins: &6%FancyCore_TheBridge_coins%")));

    if (this.player.getName().equalsIgnoreCase(target)) {
      this.setItem(31, BukkitUtils.deserializeItemStack("INK_SACK:1 : 1 : name>&cVoltar"));
    } else {
      this.setItem(31, BukkitUtils.deserializeItemStack("INK_SACK:1 : 1 : name>&cFechar"));
    }

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
            if (evt.getSlot() == 11 || evt.getSlot() == 13 || evt.getSlot() == 15) {
              EnumSound.ITEM_PICKUP.play(this.player, 0.5F, 2.0F);
            } else if (evt.getSlot() == 31) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              if (this.player.getName().equalsIgnoreCase(target)) {
                new MenuProfile(profile);
              } else {
                this.player.closeInventory();
              }
            }
          }
        }
      }
    }
  }

  public void cancel() {
    this.target = null;
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
