package tk.fancystore.noisier.bukkit.menus;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import tk.fancystore.noisier.bukkit.Core;
import tk.fancystore.noisier.bukkit.deliveries.Delivery;
import tk.fancystore.noisier.bukkit.libraries.menu.UpdatablePlayerMenu;
import tk.fancystore.noisier.bukkit.player.Profile;
import tk.fancystore.noisier.database.data.container.DeliveriesContainer;
import tk.fancystore.noisier.utils.BukkitUtils;
import tk.fancystore.noisier.utils.enums.EnumSound;

import java.util.HashMap;
import java.util.Map;

public class MenuDeliveries extends UpdatablePlayerMenu {

  private Profile profile;
  private final boolean back;
  private Map<ItemStack, Delivery> deliveries;

  public MenuDeliveries(Profile profile) {
    this(profile, false);
  }

  public MenuDeliveries(Profile profile, boolean back) {
    super(profile.getPlayer(), "Entregas", back ? 6 : 5);
    this.profile = profile;
    this.back = back;
    this.deliveries = new HashMap<>();

    if (back) {
      this.setItem(49, BukkitUtils.deserializeItemStack("INK_SACK:1 : 1 : name>&cVoltar"));
    }

    this.update();
    this.register(Core.getInstance(), 20);
    this.open();
  }

  @EventHandler
  public void onInventoryClick(InventoryClickEvent evt) {
    if (evt.getInventory().equals(this.getInventory())) {
      evt.setCancelled(true);

      if (evt.getWhoClicked().equals(this.player)) {
        if (evt.getClickedInventory() != null && evt.getClickedInventory().equals(this.getInventory())) {
          ItemStack item = evt.getCurrentItem();

          if (item != null && item.getType() != Material.AIR) {
            Delivery delivery = this.deliveries.get(item);
            if (evt.getSlot() == 49) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuProfile(profile);
            } else if (delivery != null) {
              DeliveriesContainer container = this.profile.getDeliveriesContainer();
              if (container.alreadyClaimed(delivery.getId()) || !delivery.hasPermission(player)) {
                EnumSound.ENDERMAN_TELEPORT.play(this.player, 0.5F, 1.0F);
              } else {
                EnumSound.LEVEL_UP.play(this.player, 1.0F, 1.0F);
                container.claimDelivery(delivery.getId(), delivery.getDays());
                delivery.listRewards().forEach(reward -> reward.dispatch(this.profile));
                this.player.sendMessage(delivery.getMessage());
                new MenuDeliveries(profile, back);
                //this.player.closeInventory();
              }
            }
          }
        }
      }
    }
  }

  @Override
  public void update() {
    this.deliveries.clear();
    for (Delivery delivery : Delivery.listDeliveries()) {
      ItemStack item = delivery.getIcon(this.profile);
      this.setItem(delivery.getSlot(), item);
      this.deliveries.put(item, delivery);
    }

    this.player.updateInventory();
  }

  public void cancel() {
    super.cancel();
    HandlerList.unregisterAll(this);
    this.profile = null;
    this.deliveries.clear();
    this.deliveries = null;
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
