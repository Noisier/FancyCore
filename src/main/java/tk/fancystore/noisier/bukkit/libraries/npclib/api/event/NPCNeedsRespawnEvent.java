package tk.fancystore.noisier.bukkit.libraries.npclib.api.event;

import org.bukkit.event.HandlerList;
import tk.fancystore.noisier.bukkit.libraries.npclib.api.npc.NPC;

public class NPCNeedsRespawnEvent extends NPCEvent {

  private static final HandlerList HANDLER_LIST = new HandlerList();
  private final NPC npc;

  public NPCNeedsRespawnEvent(NPC npc) {
    this.npc = npc;
  }

  public static HandlerList getHandlerList() {
    return HANDLER_LIST;
  }

  public NPC getNPC() {
    return npc;
  }

  @Override
  public HandlerList getHandlers() {
    return HANDLER_LIST;
  }
}
