package messaging;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import tk.fancystore.noisier.bukkit.plugin.NBukkit;

public abstract class BukkitMessageChannel implements PluginMessageListener {

  protected NBukkit plugin;
  protected String channel;

  public BukkitMessageChannel(NBukkit plugin) {
    this(plugin, plugin.getName());
  }

  public BukkitMessageChannel(NBukkit plugin, String channel) {
    this.plugin = plugin;
    this.channel = channel;

    Bukkit.getMessenger().registerOutgoingPluginChannel(plugin, channel);
    Bukkit.getMessenger().registerIncomingPluginChannel(plugin, channel, this);
  }

  public abstract void receivedMessage(Player receiver, ByteArrayDataInput input);

  @Override
  public void onPluginMessageReceived(String channel, Player receiver, byte[] data) {
    if (channel.equals(this.channel)) {

      //noinspection UnstableApiUsage
      ByteArrayDataInput input = ByteStreams.newDataInput(data);

      this.receivedMessage(receiver, input);
    }
  }

  public void sendData(Player player, String subChannel, String... args) {
    //noinspection UnstableApiUsage
    ByteArrayDataOutput out = ByteStreams.newDataOutput();

    out.writeUTF(subChannel);
    for (String arg : args) {
      out.writeUTF(arg);
    }

    player.sendPluginMessage(this.plugin, this.channel, out.toByteArray());
  }

  public void sendData(String subChannel, String... args) {
    //noinspection UnstableApiUsage
    ByteArrayDataOutput out = ByteStreams.newDataOutput();

    out.writeUTF(subChannel);
    for (String arg : args) {
      out.writeUTF(arg);
    }

    Bukkit.getServer().sendPluginMessage(this.plugin, this.channel, out.toByteArray());
  }
}
