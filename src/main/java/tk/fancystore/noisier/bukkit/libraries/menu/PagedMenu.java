package tk.fancystore.noisier.bukkit.libraries.menu;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import tk.fancystore.noisier.utils.BukkitUtils;

import java.util.*;

public class PagedMenu {

  public int previousPage = 45, nextPage = 53;
  public String previousStack = "INK_SACK:8 : 1 : name>&aPágina {page}", nextStack = "INK_SACK:10 : 1 : name>&aPágina {page}";

  protected List<Menu> menus = new ArrayList<>();
  protected Map<Menu, Integer> id = new HashMap<>();
  protected Map<Integer, ItemStack> slots = new HashMap<>();

  protected int rows;
  protected String name;
  protected int currentPage = 1;
  protected int lastListSize = -1;

  public PagedMenu(String name, int rows) {
    this.rows = Math.max(rows, 1);
    this.name = name;
  }

  public int responsiveSlot(int i) {
    int size = (this.rows * 9);
    return i < 0 ? Math.max(size + i, 0) : Math.min(i, size);
  }

  public static int responsiveRows(int i, int amount) {
    return i < 1 ? (amount < 8 ? 4 : amount < 15 ? 5 : 6) : i;
  }

  public void open(Player player) {
    player.openInventory(menus.get(0).getInventory());
  }

  public void openPrevious(Player player, Inventory inv) {
    int currentPage = id.get(getCurrent(inv));
    if (currentPage == 1) {
      return;
    }

    player.openInventory(menus.get(currentPage - 2).getInventory());
  }

  public void openNext(Player player, Inventory inv) {
    int currentPage = id.get(getCurrent(inv));
    if (currentPage + 1 > menus.size()) {
      return;
    }

    player.openInventory(menus.get(currentPage).getInventory());
  }

  public void onlySlots(Integer... slots) {
    onlySlots(Arrays.asList(slots));
  }

  public void onlySlots(List<Integer> slots) {
    for (int slot = 0; slot < rows * 9; slot++) {
      if (!slots.contains(slot)) {
        this.slots.put(slot, null);
      }
    }
  }

  public void removeSlots(int... slots) {
    removeSlotsWith(null, slots);
  }

  public void removeSlotsWith(ItemStack item, int... slots) {
    for (int slot : slots) {
      this.slots.put(slot, item);
    }
  }

  public void setItems(List<ItemStack> items) {
    if (items.size() == lastListSize) {
      updateItems(items);
      return;
    }

    this.menus.forEach(menu -> menu.getInventory().getViewers().forEach(HumanEntity::closeInventory));
    this.menus.clear();
    this.lastListSize = items.size();
    List<List<ItemStack>> splitted = split(items);
    if (splitted.isEmpty()) {
      splitted.add(new ArrayList<>());
    }

    for (int i = 0; i < splitted.size(); i++) {
      List<ItemStack> list = splitted.get(i);
      Menu menu = new Menu(name, this.rows);
      this.slots.forEach((key, value) -> {
        menu.getSlots().remove(key);
        if (value != null) {
          menu.setItem(key, value);
        }
      });

      menu.setItems(list);
      if (splitted.size() > 1) {
        if (i > 0 && previousPage != -1) {
          menu.setItem(previousPage, BukkitUtils.deserializeItemStack(previousStack.replace("{page}", String.valueOf(i))));
        }
        if (i + 1 != splitted.size() && nextPage != -1) {
          menu.setItem(nextPage, BukkitUtils.deserializeItemStack(nextStack.replace("{page}", String.valueOf(i + 2))));
        }
      }
      this.menus.add(menu);
      this.id.put(menu, i + 1);
    }
  }

  public void updateItems(List<ItemStack> items) {
    List<List<ItemStack>> splitted = split(items);
    if (splitted.isEmpty()) {
      splitted.add(new ArrayList<>());
    }

    for (int i = 0; i < splitted.size(); i++) {
      Menu menu = menus.get(i);
      this.slots.forEach((key, value) -> {
        if (value != null) {
          menu.setItem(key, value);
        }
      });

      menu.setItems(splitted.get(i));
    }
  }

  public Menu getCurrent(Inventory inv) {
    for (Menu menu : menus) {
      if (menu.getInventory().equals(inv)) {
        return menu;
      }
    }

    return menus.get(0);
  }

  public Inventory getCurrentInventory() {
    return menus.get(currentPage - 1).getInventory();
  }

  private List<List<ItemStack>> split(List<ItemStack> items) {
    List<List<ItemStack>> list = new ArrayList<>();

    List<ItemStack> toadd = new ArrayList<>();
    for (int size = 1; size - 1 < items.size(); size++) {
      toadd.add(items.get(size - 1));
      if (size % ((this.rows * 9) - this.slots.size()) == 0) {
        list.add(toadd);
        toadd = new ArrayList<>();
      }

      if (size == items.size()) {
        if (!toadd.isEmpty()) {
          list.add(toadd);
        }
        break;
      }
    }

    return list;
  }
}
