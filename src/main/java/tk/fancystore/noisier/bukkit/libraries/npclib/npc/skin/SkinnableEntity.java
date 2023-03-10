package tk.fancystore.noisier.bukkit.libraries.npclib.npc.skin;

import org.bukkit.entity.Player;
import tk.fancystore.noisier.bukkit.libraries.npclib.api.npc.NPC;

public interface SkinnableEntity {

  NPC getNPC();

  Player getEntity();

  SkinPacketTracker getSkinTracker();

  Skin getSkin();

  void setSkin(Skin skin);

  void setSkinFlags(byte flags);
}
