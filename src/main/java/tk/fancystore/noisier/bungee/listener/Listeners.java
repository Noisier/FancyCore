package tk.fancystore.noisier.bungee.listener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.ServerConnection;
import net.md_5.bungee.UserConnection;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.connection.InitialHandler;
import net.md_5.bungee.connection.LoginResult;
import net.md_5.bungee.event.EventHandler;
import tk.fancystore.noisier.bungee.Main;
import tk.fancystore.noisier.bungee.fake.FakeManager;
import tk.fancystore.noisier.bungee.party.BungeeParty;
import tk.fancystore.noisier.bungee.party.BungeePartyManager;
import tk.fancystore.noisier.database.Database;
import tk.fancystore.noisier.reflection.Accessors;
import tk.fancystore.noisier.reflection.acessors.FieldAccessor;
import tk.fancystore.noisier.utils.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Listeners implements Listener {

  private static final Map<String, LoginResult.Property[]> PROPERTY_BACKUP = new HashMap<>();
  private static final Map<String, Long> PROTECTION_LOBBY = new HashMap<>();
  private static final Map<String, Map<String, Integer>> PREFERENCE_CACHE = new HashMap<>();
  private static final FieldAccessor<Map> COMMAND_MAP = Accessors.getField(PluginManager.class, "commandMap", Map.class);

  public static void setupListeners() {
    Main.getInstance().getProxy().getPluginManager().registerListener(Main.getInstance(), new Listeners());
    Main.getInstance().getProxy().registerChannel("FancyCore");

    // Limpeza do Cache das preferências se o jogador estiver offline.
    new Timer().scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        Set<String> keySet = PREFERENCE_CACHE.keySet();

        keySet.forEach(key -> {
          ProxiedPlayer player = ProxyServer.getInstance().getPlayer(key);
          if (player == null) {
            PREFERENCE_CACHE.remove(key);
          }
        });

        keySet.clear();
      }
    }, TimeUnit.SECONDS.toMillis(300), TimeUnit.SECONDS.toMillis(300));
  }

  @EventHandler
  public void onPlayerDisconnect(PlayerDisconnectEvent evt) {
    PROTECTION_LOBBY.remove(evt.getPlayer().getName().toLowerCase());
    PROPERTY_BACKUP.remove(evt.getPlayer().getName().toLowerCase());
    PREFERENCE_CACHE.remove(evt.getPlayer().getName().toLowerCase());
  }

  /*@EventHandler
  public void onPostLogin(PostLoginEvent evt) {}*/

  @EventHandler
  public void onPluginMessage(PluginMessageEvent evt) {
    if (evt.getSender() instanceof ServerConnection && evt.getReceiver() instanceof ProxiedPlayer) {
      if (evt.getTag().equalsIgnoreCase("FancyCore")) {
        ProxiedPlayer player = (ProxiedPlayer) evt.getReceiver();

        //noinspection UnstableApiUsage
        ByteArrayDataInput in = ByteStreams.newDataInput(evt.getData());
        String subChannel = in.readUTF();
        if (subChannel.equalsIgnoreCase("UPDATE_PREFERENCE")) {
          PREFERENCE_CACHE.putIfAbsent(player.getName(), new HashMap<>());

          String id = in.readUTF();
          int ordinal = in.readInt();

          PREFERENCE_CACHE.get(player.getName()).put(id, ordinal);
        } else if (subChannel.equalsIgnoreCase("FAKE_SKIN")) {
          LoginResult profile = ((InitialHandler) player.getPendingConnection()).getLoginProfile();
          if (profile != null) {
            try {
              String[] data = in.readUTF().split(":");
              PROPERTY_BACKUP.put(player.getName().toLowerCase(), profile.getProperties());
              this.modifyProperties(profile, data);
            } catch (Exception ex) {
              LoginResult.Property[] properties = PROPERTY_BACKUP.remove(player.getName().toLowerCase());
              if (properties != null) {
                profile.setProperties(properties);
              }
            }
          }
        }
      }
    }
  }

  @EventHandler(priority = (byte) 128)
  public void onServerConnected(ServerConnectedEvent evt) {
    ProxiedPlayer player = evt.getPlayer();

    BungeeParty party = BungeePartyManager.getLeaderParty(player.getName());
    if (party != null) {
      party.sendData(evt.getServer().getInfo());
    }

    if (FakeManager.isFake(player.getName())) {
      String skin = FakeManager.getSkin(player.getName());
      // Enviar dados desse jogador que utiliza Fake para o servidor processar.
      ByteArrayDataOutput out = ByteStreams.newDataOutput();
      out.writeUTF("FAKE");
      out.writeUTF(player.getName());
      out.writeUTF(FakeManager.getFake(player.getName()));
      out.writeUTF(StringUtils.stripColors(FakeManager.getRole(player.getName()).getName()));
      out.writeUTF(skin);
      evt.getServer().sendData("FancyCore", out.toByteArray());


      LoginResult profile = ((InitialHandler) player.getPendingConnection()).getLoginProfile();
      if (profile != null) {
        this.modifyProperties(profile, skin.split(":"));
      }
    }
  }

  @EventHandler(priority = (byte) 128)
  public void onChat(ChatEvent evt) {
    if (evt.getSender() instanceof ProxiedPlayer) {
      if (evt.isCommand()) {
        ProxiedPlayer player = (UserConnection) evt.getSender();
        String[] args = evt.getMessage().replace("/", "").split(" ");

        String command = args[0];
        if (COMMAND_MAP.get(ProxyServer.getInstance().getPluginManager()).containsKey("lobby") && command.equals("lobby") && getPreferenceOrdinal(player.getName().toLowerCase(), "pl", 0) == 0) {
          long last = PROTECTION_LOBBY.getOrDefault(player.getName().toLowerCase(), 0L);
          if (last > System.currentTimeMillis()) {
            PROTECTION_LOBBY.remove(player.getName().toLowerCase());
            return;
          }

          evt.setCancelled(true);
          PROTECTION_LOBBY.put(player.getName().toLowerCase(), System.currentTimeMillis() + 3000);
          player.sendMessage(TextComponent.fromLegacyText("§aVocê tem certeza? Utilize /lobby novamente para voltar ao lobby."));
        } else if (COMMAND_MAP.get(ProxyServer.getInstance().getPluginManager()).containsKey("tell") && args.length > 1 && command.equals("tell") && !args[1].equalsIgnoreCase(player.getName())) {
          if (getPreferenceOrdinal(args[1].toLowerCase(), "pm", 0) != 0) {
            evt.setCancelled(true);
            player.sendMessage(TextComponent.fromLegacyText("§cEste usuário desativou as mensagens privadas."));
          }
        }
      }
    }
  }

  private void modifyProperties(LoginResult profile, String[] data) {
    List<LoginResult.Property> properties = new ArrayList<>();
    for (LoginResult.Property property : profile.getProperties() == null ? new ArrayList<LoginResult.Property>() : Arrays.asList(profile.getProperties())) {
      if (property.getName().equalsIgnoreCase("textures")) {
        continue;
      }

      properties.add(property);
    }

    properties.add(new LoginResult.Property("textures", data[0], data[1]));
    profile.setProperties(properties.toArray(new LoginResult.Property[0]));
  }

  public static int getPreferenceOrdinal(String playerName, String id) {
    return getPreferenceOrdinal(playerName, id, 0);
  }

  public static int getPreferenceOrdinal(String playerName, String id, int def) {
    PREFERENCE_CACHE.putIfAbsent(playerName, new HashMap<>());
    PREFERENCE_CACHE.get(playerName).putIfAbsent(id, Database.getInstance().getPreference(playerName, id, def));

    return PREFERENCE_CACHE.get(playerName).get(id);
  }
}
