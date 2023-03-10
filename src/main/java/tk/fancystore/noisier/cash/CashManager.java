package tk.fancystore.noisier.cash;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import tk.fancystore.noisier.Manager;
import tk.fancystore.noisier.bukkit.player.Profile;
import tk.fancystore.noisier.database.Database;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;

@SuppressWarnings("UnstableApiUsage")
public class CashManager {

  public static void addCash(String name, long amount) throws CashException {
    if (Manager.BUNGEE) {
      ProxiedPlayer player = ProxyServer.getInstance().getPlayer(name);

      if (player != null) {
        sendMessage(player, "add", amount);
        return;
      }
    } else {
      Profile profile = Profile.getProfile(name);
      if (profile != null) {
        profile.setStats("FancyCoreProfile", profile.getStats("FancyCoreProfile", "cash") + amount, "cash");
        return;
      }
    }

    setOfflineCash(name, getOfflineCash(name) + amount);
  }

  public static void removeCash(String name, long amount) throws CashException {
    if (Manager.BUNGEE) {
      ProxiedPlayer player = ProxyServer.getInstance().getPlayer(name);

      if (player != null) {
        sendMessage(player, "remove", amount);
        return;
      }
    } else {
      Profile profile = Profile.getProfile(name);
      if (profile != null) {
        profile.setStats("FancyCoreProfile", profile.getStats("FancyCoreProfile", "cash") - amount, "cash");
        return;
      }
    }

    setOfflineCash(name, getOfflineCash(name) - amount);
  }

  public static void setCash(String name, long amount) throws CashException {
    if (Manager.BUNGEE) {
      ProxiedPlayer player = ProxyServer.getInstance().getPlayer(name);

      if (player != null) {
        sendMessage(player, "set", amount);
        return;
      }
    } else {
      Profile profile = Profile.getProfile(name);
      if (profile != null) {
        profile.setStats("FancyCoreProfile", amount, "cash");
        return;
      }
    }

    setOfflineCash(name, amount);
  }

  public static void sendMessage(ProxiedPlayer player, String method) {
    sendMessage(player, method, 0);
  }

  public static void sendMessage(ProxiedPlayer player, String method, long amount) {
    ByteArrayDataOutput out = ByteStreams.newDataOutput();
    out.writeUTF("CASH_MANAGER");
    out.writeUTF(player.getName());
    out.writeUTF(method);
    out.writeLong(amount);
    player.getServer().sendData("FancyCore", out.toByteArray());
  }

  private static long getOfflineCash(String name) throws CashException {
    String playerName = Database.getInstance().exists(name);
    if (playerName == null) {
      throw new CashException("O usuário não existe!");
    }

    try (CachedRowSet rs = Database.getInstance().query("SELECT `cash` FROM `FancyCoreProfile` WHERE LOWER(`name`) = ?", playerName.toLowerCase())) {
      if (rs != null) {
        return rs.getLong("cash");
      }
    } catch (SQLException ignored) {}

    return 0L;
  }

  private static void setOfflineCash(String name, long amount) throws CashException {
    String playerName = Database.getInstance().exists(name);
    if (playerName == null) {
      throw new CashException("O usuário não existe!");
    }

    Database.getInstance().update("UPDATE `FancyCoreProfile` SET `cash` = ? WHERE LOWER(`name`) = ?", (amount < 0 ? 0 : amount), playerName.toLowerCase());
  }
}
