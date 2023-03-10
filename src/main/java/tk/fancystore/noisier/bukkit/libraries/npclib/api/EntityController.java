package tk.fancystore.noisier.bukkit.libraries.npclib.api;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import tk.fancystore.noisier.bukkit.libraries.npclib.api.npc.NPC;

public interface EntityController {

  void spawn(Location location, NPC npc);

  void remove();

  Entity getBukkitEntity();
}
