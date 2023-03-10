package tk.fancystore.noisier.bukkit.hook;

import tk.fancystore.noisier.database.Database;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;

public class FriendsHook {

  private static final boolean friends;

  static {
    friends = Database.getInstance().query("SELECT * FROM INFORMATION_SCHEMA.STATISTICS WHERE table_name = 'FancyFriends'") != null;
  }

  public static String getFriends(String player) {
    if (friends) {
      CachedRowSet rs = Database.getInstance().query("SELECT `friends` FROM `FancyFriends` WHERE LOWER(`name`) = ?", player.toLowerCase());
      if (rs != null) {
        try {
          return rs.getString("friends");
        } catch (SQLException ignored) {}
      }
    }

    return "[]";
  }
}