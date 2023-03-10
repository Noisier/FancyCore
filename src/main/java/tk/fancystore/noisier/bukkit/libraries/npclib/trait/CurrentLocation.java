package tk.fancystore.noisier.bukkit.libraries.npclib.trait;

import org.bukkit.Location;
import tk.fancystore.noisier.bukkit.libraries.npclib.api.npc.NPC;

public class CurrentLocation extends NPCTrait {

  private Location location = new Location(null, 0, 0, 0);

  public CurrentLocation(NPC npc) {
    super(npc);
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }
}
