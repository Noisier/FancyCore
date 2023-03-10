package tk.fancystore.noisier.bukkit;

import com.comphenix.protocol.ProtocolLibrary;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;
import tk.fancystore.noisier.Manager;
import tk.fancystore.noisier.booster.Booster;
import tk.fancystore.noisier.bukkit.achievements.Achievement;
import tk.fancystore.noisier.bukkit.cmd.Commands;
import tk.fancystore.noisier.bukkit.deliveries.Delivery;
import tk.fancystore.noisier.bukkit.fake.FakeManager;
import tk.fancystore.noisier.bukkit.hook.CoreExpansion;
import tk.fancystore.noisier.bukkit.hook.protocollib.FakeAdapter;
import tk.fancystore.noisier.bukkit.hook.protocollib.HologramAdapter;
import tk.fancystore.noisier.bukkit.hook.protocollib.NPCAdapter;
import tk.fancystore.noisier.bukkit.libraries.MinecraftVersion;
import tk.fancystore.noisier.bukkit.libraries.holograms.HologramLibrary;
import tk.fancystore.noisier.bukkit.libraries.npclib.NPCLibrary;
import tk.fancystore.noisier.bukkit.listeners.Listeners;
import tk.fancystore.noisier.bukkit.listeners.PluginMessageListener;
import tk.fancystore.noisier.bukkit.nms.NMS;
import tk.fancystore.noisier.bukkit.player.Profile;
import tk.fancystore.noisier.bukkit.plugin.NBukkit;
import tk.fancystore.noisier.bukkit.servers.ServerItem;
import tk.fancystore.noisier.bukkit.titles.Title;
import tk.fancystore.noisier.database.Database;
import tk.fancystore.noisier.bukkit.player.level.PlayerLevel;
import tk.fancystore.noisier.role.Role;
import tk.fancystore.noisier.utils.queue.Queue;
import tk.fancystore.noisier.utils.queue.QueuePlayer;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

@SuppressWarnings("unchecked")
public class Core extends NBukkit {

  private static Core instance;
  private static Location lobby;
  public static boolean validInit;

  public static boolean FancyCosmetics;

  public static String minigame = "";
  public static final List<String> minigames = Arrays.asList("Bed Wars", "Sky Wars", "The Bridge");

  public static Location getLobby() {
    return lobby;
  }

  public static void setLobby(Location location) {
    lobby = location;
  }

  public static void sendServer(Profile profile, String name) {
    if (!Core.getInstance().isEnabled()) {
      return;
    }

    Player player = profile.getPlayer();
    if (Core.getInstance().getConfig("utils").getBoolean("queue")) {
      if (player != null) {
        player.closeInventory();
        Queue queue = player.hasPermission("fancycore.queue") ? Queue.VIP : Queue.MEMBER;
        QueuePlayer qp = queue.getQueuePlayer(player);
        if (qp != null) {
          if (qp.server.equalsIgnoreCase(name)) {
            qp.player.sendMessage("§cVocê já está na fila de conexão!");
          } else {
            qp.server = name;
          }
          return;
        }

        queue.queue(player, profile, name);
      }
    } else {
      if (player != null) {
        Bukkit.getScheduler().runTask(Core.getInstance(), () -> {
          if (player.isOnline()) {
            player.closeInventory();
            NMS.sendActionBar(player, "");
            player.sendMessage("§aConectando...");
            //noinspection UnstableApiUsage
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF(name);
            player.sendPluginMessage(Core.getInstance(), "BungeeCord", out.toByteArray());
          }
        });
      }
    }
  }

  @Override
  public void start() {
    instance = this;
  }

  @Override
  public void load() {
  }

  @Override
  public void enable() {
    if (!NMS.setupNMS()) {
      this.setEnabled(false);
      this.getLogger().warning("A sua versao nao e compativel com o plugin, utilize a versao 1_8_R3 (Atual: " + MinecraftVersion.getCurrentVersion().getVersion() + ")");
      return;
    }

    saveDefaultConfig();
    Manager.setupManager(this);
    lobby = Bukkit.getWorlds().get(0).getSpawnLocation();

    FancyCosmetics = Bukkit.getPluginManager().getPlugin("FancyCosmetics") != null;

    // Remover o spawn-protection-size
    if (Bukkit.getSpawnRadius() != 0) {
      Bukkit.setSpawnRadius(0);
    }

    // Remover /reload
    try {
      SimpleCommandMap simpleCommandMap = (SimpleCommandMap) Bukkit.getServer().getClass().getDeclaredMethod("getCommandMap").invoke(Bukkit.getServer());
      Field field = simpleCommandMap.getClass().getDeclaredField("knownCommands");
      field.setAccessible(true);
      Map<String, Command> knownCommands = (Map<String, Command>) field.get(simpleCommandMap);
      knownCommands.remove("rl");
      knownCommands.remove("reload");
      knownCommands.remove("bukkit:rl");
      knownCommands.remove("bukkit:reload");
    } catch (ReflectiveOperationException ex) {
      getLogger().log(Level.SEVERE, "Cannot remove reload command: ", ex);
    }

    if (!PlaceholderAPIPlugin.getInstance().getDescription().getVersion().equals("2.10.5")) {
      Bukkit.getConsoleSender().sendMessage(
          " \n §6§lAVISO IMPORTANTE\n \n §7Utilize a versão 2.10.5 do PlaceHolderAPI, você está utilizando a v" +
              PlaceholderAPIPlugin.getInstance().getDescription().getVersion() + "\n ");
      System.exit(0);
      return;
    }

    PlaceholderAPI.registerExpansion(new CoreExpansion());

    Database.setupDatabase(
        getConfig().getString("database.type"),
        getConfig().getString("database.mysql.host"),
        getConfig().getString("database.mysql.port"),
        getConfig().getString("database.mysql.name"),
        getConfig().getString("database.mysql.user"),
        getConfig().getString("database.mysql.password"),
        getConfig().getBoolean("database.mysql.mariadb", false)
    );

    NPCLibrary.setupNPCs(this);
    HologramLibrary.setupHolograms(this);

    Role.setupRoles();
    FakeManager.setupFake();
    Title.setupTitles();
    Booster.setupBoosters();
    Delivery.setupDeliveries();
    ServerItem.setupServers();
    Achievement.setupAchievements();
    PlayerLevel.setupLevels();

    Commands.setupCommands();
    Listeners.setupListeners();

    ProtocolLibrary.getProtocolManager().addPacketListener(new FakeAdapter());
    ProtocolLibrary.getProtocolManager().addPacketListener(new NPCAdapter());
    ProtocolLibrary.getProtocolManager().addPacketListener(new HologramAdapter());

    getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    getServer().getMessenger().registerOutgoingPluginChannel(this, "FancyCore");
    getServer().getMessenger().registerIncomingPluginChannel(this, "FancyCore", new PluginMessageListener());

    validInit = true;

    this.getLogger().info("O plugin foi ativado.");
  }

  @Override
  public void disable() {
    if (validInit) {
      Bukkit.getOnlinePlayers().forEach(player -> {
        Profile profile = Profile.unloadProfile(player.getName());
        if (profile != null) {
          profile.saveSync();
          this.getLogger().info("O perfil " + profile.getName() + " foi salvo!");
          profile.destroy();
        }
      });
      Database.getInstance().close();
    }
    this.getLogger().info("O plugin foi desativado.");
  }

  public static Core getInstance() {
    return instance;
  }
}
