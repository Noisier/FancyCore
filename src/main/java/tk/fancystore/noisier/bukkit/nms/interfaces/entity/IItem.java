package tk.fancystore.noisier.bukkit.nms.interfaces.entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import tk.fancystore.noisier.bukkit.libraries.holograms.api.HologramLine;

public interface IItem {

  public void setPassengerOf(Entity entity);

  public void setItemStack(ItemStack item);

  public void setLocation(double x, double y, double z);

  public boolean isDead();

  public void killEntity();

  public Item getEntity();

  public HologramLine getLine();
}
