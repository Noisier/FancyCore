package tk.fancystore.noisier.bukkit.menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import tk.fancystore.noisier.booster.Booster;
import tk.fancystore.noisier.booster.NetworkBooster;
import tk.fancystore.noisier.bukkit.Core;
import tk.fancystore.noisier.bukkit.libraries.menu.UpdatablePlayerMenu;
import tk.fancystore.noisier.bukkit.player.Profile;
import tk.fancystore.noisier.bukkit.servers.ServerItem;
import tk.fancystore.noisier.role.Role;
import tk.fancystore.noisier.utils.BukkitUtils;
import tk.fancystore.noisier.utils.StringUtils;
import tk.fancystore.noisier.utils.TimeUtils;

public class MenuServers extends UpdatablePlayerMenu {

  public MenuServers(Profile profile) {
    super(profile.getPlayer(), ServerItem.CONFIG.getString("title"), ServerItem.CONFIG.getInt("rows"));

    this.update();
    this.register(Core.getInstance(), 20);
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
            if (ServerItem.DISABLED_SLOTS.contains(evt.getSlot())) {
              this.player.sendMessage("§cVocê já está conectado a este servidor.");
              return;
            }

            ServerItem.listServers().stream().filter(s -> s.getSlot() == evt.getSlot()).findFirst().ifPresent(serverItem -> {
              String actions = serverItem.getActions();
              if (actions == null) {
                serverItem.connect(profile);
              } else {
                for (String action : actions.split("</then>")) {
                  if (action.startsWith("PLAYER_EXECUTE>")) {
                    Bukkit.getServer().dispatchCommand(player, action.replaceFirst("PLAYER_EXECUTE>", "").replace("{player}", player.getName()));
                  } else if (action.startsWith("CONSOLE_EXECUTE>")) {
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), action.replaceFirst("CONSOLE_EXECUTE>", "").replace("{player}", player.getName()));
                  } else if (action.startsWith("SEND_MESSAGE>")) {
                    player.spigot().sendMessage(StringUtils.toTextComponent(action.replaceFirst("SEND_MESSAGE>", "")));
                  } else if (action.startsWith("CLOSE")) {
                    player.closeInventory();
                  }
                }
              }
            });
          }
        }
      }
    }
  }

  @Override
  public void update() {
    for (ServerItem serverItem : ServerItem.listServers()) {
      NetworkBooster booster = Booster.getNetworkBooster(serverItem.getBooster());

      this.setItem(serverItem.getSlot(), BukkitUtils.deserializeItemStack(serverItem.getIcon()
              .replace("{players}", StringUtils.formatNumber(ServerItem.getServerCount(serverItem)))
              .replace("{booster}", booster == null ? "" : "\n \n&7Mutiplicador de Coins: &6" + booster.getMultiplier() + "\n&8 ➟ Ativado por " + Role.getColored(booster.getBooster()) + "&8 (" + TimeUtils.getTimeUntil(booster.getExpires()) + ")")
          )
      );
    }
  }

  public void cancel() {
    super.cancel();
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