package tk.fancystore.noisier.bukkit.hook.protocollib;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import tk.fancystore.noisier.bukkit.libraries.holograms.HologramLibrary;
import tk.fancystore.noisier.bukkit.libraries.holograms.api.Hologram;
import tk.fancystore.noisier.bukkit.libraries.npclib.NPCLibrary;
import tk.fancystore.noisier.bukkit.nms.NMS;

public class HologramAdapter extends PacketAdapter {

  public HologramAdapter() {
    super(params().plugin(NPCLibrary.getPlugin()).types(PacketType.Play.Server.SPAWN_ENTITY,
        PacketType.Play.Server.SPAWN_ENTITY_LIVING, PacketType.Play.Server.ENTITY_METADATA));
  }

  @Override
  public void onPacketSending(PacketEvent evt) {
    PacketContainer packet = evt.getPacket();

    Player player = evt.getPlayer();
    Entity entity = HologramLibrary.getHologramEntity(packet.getIntegers().read(0));

    Hologram hologram;
    if (entity == null || !HologramLibrary.isHologramEntity(entity) || (hologram = HologramLibrary.getHologram(entity)) == null) {
      hologram = NMS.getPreHologram(packet.getIntegers().read(0));
    }

    if (hologram == null || hologram.canSee(player)) {
      return;
    }

    evt.setCancelled(true);
  }

  @Override
  public void onPacketReceiving(PacketEvent evt) {
  }
}
