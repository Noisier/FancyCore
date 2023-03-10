package messaging;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import tk.fancystore.noisier.bungee.plugin.NBungee;

import java.util.Collection;

public abstract class BungeeMessageChannel implements Listener {

  protected String channel;

  public BungeeMessageChannel(NBungee plugin) {
    this(plugin, plugin.getName());
  }

  public BungeeMessageChannel(NBungee plugin, String channel) {
    this.channel = channel;

    ProxyServer.getInstance().registerChannel(channel);
    ProxyServer.getInstance().getPluginManager().registerListener(plugin, this);
  }

  public abstract void receivedMessage(ProxiedPlayer receiver, ByteArrayDataInput input);

  @EventHandler
  public void onPluginMessageReceived(PluginMessageEvent evt) {
    if (evt.getTag().equals(this.channel)) {
      if (evt.getSender() instanceof Server) {
        ProxiedPlayer receiver = null;

        if (evt.getReceiver() instanceof ProxiedPlayer) {
          receiver = (ProxiedPlayer) evt.getReceiver();
        }

        //noinspection UnstableApiUsage
        ByteArrayDataInput input = ByteStreams.newDataInput(evt.getData());

        this.receivedMessage(receiver, input);
      }
    }
  }

  public void sendData(ProxiedPlayer receiver, String subChannel, String... args) {
    //noinspection UnstableApiUsage
    ByteArrayDataOutput out = ByteStreams.newDataOutput();

    out.writeUTF(subChannel);
    for (String arg : args) {
      out.writeUTF(arg);
    }

    receiver.sendData(this.channel, out.toByteArray());
  }

  public void sendData(String subChannel, String... values) {
    sendData(ProxyServer.getInstance().getServers().values(), subChannel, values);
  }

  public void sendData(Collection<ServerInfo> servers, String subChannel, String... args) {
    //noinspection UnstableApiUsage
    ByteArrayDataOutput out = ByteStreams.newDataOutput();

    out.writeUTF(subChannel);
    for (String arg : args) {
      out.writeUTF(arg);
    }

    servers.forEach(info -> info.sendData(this.channel, out.toByteArray()));
  }
}
