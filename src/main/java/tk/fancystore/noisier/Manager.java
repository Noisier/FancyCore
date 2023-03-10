package tk.fancystore.noisier;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import tk.fancystore.noisier.bukkit.fake.FakeManager;
import tk.fancystore.noisier.bukkit.libraries.profile.Mojang;
import tk.fancystore.noisier.plugin.CorePlugin;
import tk.fancystore.noisier.reflection.Accessors;
import tk.fancystore.noisier.reflection.acessors.MethodAccessor;
import tk.fancystore.noisier.role.Role;

public class Manager {

  public static boolean BUNGEE;

  private static Object PROXY_SERVER;

  private static MethodAccessor GET_NAME;
  private static MethodAccessor GET_PLAYER;
  private static MethodAccessor GET_SPIGOT;
  private static MethodAccessor HAS_PERMISSION;
  private static MethodAccessor SEND_MESSAGE;
  private static MethodAccessor SEND_MESSAGE_COMPONENTS;

  private static MethodAccessor IS_FAKE;
  private static MethodAccessor GET_CURRENT;
  private static MethodAccessor GET_FAKE;
  private static MethodAccessor GET_FAKE_ROLE;

  private static CorePlugin corePlugin;

  public static void setupManager(CorePlugin corePlugin) {
    Manager.corePlugin = corePlugin;

    try {
      Class<?> proxyServer = Class.forName("net.md_5.bungee.api.ProxyServer");
      Class<?> proxiedPlayer = Class.forName("net.md_5.bungee.api.connection.ProxiedPlayer");
      Class<?> fakeManager = Class.forName("tk.fancystore.noisier.bungee.fake.FakeManager");
      PROXY_SERVER = Accessors.getMethod(proxyServer, "getInstance").invoke(null);
      GET_NAME = Accessors.getMethod(proxiedPlayer, "getName");
      GET_PLAYER = Accessors.getMethod(proxyServer, "getPlayer", String.class);
      HAS_PERMISSION = Accessors.getMethod(proxiedPlayer, "hasPermission", String.class);
      SEND_MESSAGE_COMPONENTS = Accessors.getMethod(proxiedPlayer, "sendMessage", BaseComponent[].class);
      IS_FAKE = Accessors.getMethod(fakeManager, "isFake", String.class);
      GET_CURRENT = Accessors.getMethod(fakeManager, "getCurrent", String.class);
      GET_FAKE = Accessors.getMethod(fakeManager, "getFake", String.class);
      GET_FAKE_ROLE = Accessors.getMethod(fakeManager, "getRole", String.class);
      BUNGEE = true;
    } catch (ClassNotFoundException ignore) {
      try {
        Class<?> player = Class.forName("org.bukkit.entity.Player");
        Class<?> spigot = Class.forName("org.bukkit.entity.Player$Spigot");
        Class<?> fakeManager = Class.forName("tk.fancystore.noisier.bukkit.fake.FakeManager");
        GET_NAME = Accessors.getMethod(player, "getName");
        GET_PLAYER = Accessors.getMethod(Class.forName("tk.fancystore.noisier.bukkit.player.Profile"), "findCached", String.class);
        HAS_PERMISSION = Accessors.getMethod(player, "hasPermission", String.class);
        SEND_MESSAGE = Accessors.getMethod(player, "sendMessage", String.class);
        GET_SPIGOT = Accessors.getMethod(player, "spigot");
        SEND_MESSAGE_COMPONENTS = Accessors.getMethod(spigot, "sendMessage", BaseComponent[].class);
        IS_FAKE = Accessors.getMethod(fakeManager, "isFake", String.class);
        GET_CURRENT = Accessors.getMethod(fakeManager, "getCurrent", String.class);
        GET_FAKE = Accessors.getMethod(fakeManager, "getFake", String.class);
        GET_FAKE_ROLE = Accessors.getMethod(fakeManager, "getRole", String.class);
      } catch (ClassNotFoundException ex) {
        ex.printStackTrace();
      }
    }
  }

  public static CorePlugin getCorePlugin() {
    return corePlugin;
  }

  public static String getSkin(String player, String type) {
    try {
      String profileID = Mojang.getProfileID(player);
      if (profileID != null) {
        String[] textures = Mojang.getProperties(profileID);
        if (textures != null) {
          return textures[type.equalsIgnoreCase("value") ? 1 : 2];
        }
      }
    } catch (Exception ignore) {
    }

    return BUNGEE ? tk.fancystore.noisier.bungee.fake.FakeManager.STEVE : FakeManager.ALEX;
  }

  public static void sendMessage(Object player, String message) {
    if (BUNGEE) {
      sendMessage(player, TextComponent.fromLegacyText(message));
      return;
    }

    SEND_MESSAGE.invoke(player, message);
  }

  public static void sendMessage(Object player, BaseComponent... components) {
    SEND_MESSAGE_COMPONENTS.invoke(BUNGEE ? player : GET_SPIGOT.invoke(player), new Object[]{components});
  }

  public static String getName(Object player) {
    return (String) GET_NAME.invoke(player);
  }

  public static Object getPlayer(String name) {
    return GET_PLAYER.invoke(BUNGEE ? PROXY_SERVER : null, name);
  }

  public static String getCurrent(String playerName) {
    return (String) GET_CURRENT.invoke(null, playerName);
  }

  public static String getFake(String playerName) {
    return (String) GET_FAKE.invoke(null, playerName);
  }

  public static Role getFakeRole(String playerName) {
    return (Role) GET_FAKE_ROLE.invoke(null, playerName);
  }

  public static boolean hasPermission(Object player, String permission) {
    return (boolean) HAS_PERMISSION.invoke(player, permission);
  }

  public static boolean isFake(String playerName) {
    return (boolean) IS_FAKE.invoke(null, playerName);
  }
}
