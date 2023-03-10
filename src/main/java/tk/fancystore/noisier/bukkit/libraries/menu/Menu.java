package tk.fancystore.noisier.bukkit.libraries.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Menu {

  private final Inventory inventory;
  private final List<Integer> slots = new ArrayList<>();

  public Menu(String title, int rows) {
    this.inventory = Bukkit.createInventory(null, Math.min(Math.max(1, rows), 6) * 9, title);
    for (int i = 0; i < this.inventory.getSize(); i++) {
      this.slots.add(i);
    }
  }

  public int responsiveSlot(int i) {
    int size = this.inventory.getSize();
    return i < 0 ? Math.max(size + i, 0) : Math.min(i, size);
  }

  public static int responsiveRows(int i, int amount) {
    return i < 1 ? (amount < 8 ? 4 : amount < 15 ? 5 : 6) : i;
  }

  public void setItem(int slot, ItemStack item) {
    this.inventory.setItem(slot, item);
  }

  public void setItems(List<ItemStack> items) {
    for (int i = 0; i < this.slots.size(); i++) {
      if (i >= items.size()) {
        break;
      }

      ItemStack item = items.get(i);
      this.inventory.setItem(this.slots.get(i), item);
    }
  }

  public void remove(int slot) {
    this.inventory.setItem(slot, new ItemStack(Material.AIR));
  }

  public void removeAll() {
    this.slots.forEach(slot -> this.inventory.setItem(slot, new ItemStack(Material.AIR)));
  }

  public void clear() {
    this.inventory.clear();
  }

  public ItemStack getItem(int slot) {
    return this.inventory.getItem(slot);
  }

  public ItemStack[] getContents() {
    return this.inventory.getContents();
  }

  public Inventory getInventory() {
    return this.inventory;
  }

  public List<Integer> getSlots() {
    return this.slots;
  }
}
