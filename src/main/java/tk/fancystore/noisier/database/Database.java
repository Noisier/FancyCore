package tk.fancystore.noisier.database;

import tk.fancystore.noisier.Manager;
import tk.fancystore.noisier.booster.NetworkBooster;
import tk.fancystore.noisier.database.cache.RoleCache;
import tk.fancystore.noisier.database.data.DataContainer;
import tk.fancystore.noisier.database.exception.ProfileLoadException;
import tk.fancystore.noisier.plugin.logger.CoreLogger;

import javax.sql.rowset.CachedRowSet;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public abstract class Database {

  public static CoreLogger LOGGER;
  private static Database instance;

  public static void setupDatabase(String type, String mysqlHost, String mysqlPort, String mysqlDbname, String mysqlUsername, String mysqlPassword, boolean mariadb) {
    LOGGER = Manager.getCorePlugin().getCoreLogger().getModule("DATABASE");
    if (type.equalsIgnoreCase("hikari")) {
      instance = new HikariDatabase(mysqlHost, mysqlPort, mysqlDbname, mysqlUsername, mysqlPassword, mariadb);
    } else {
      instance = new MySQLDatabase(mysqlHost, mysqlPort, mysqlDbname, mysqlUsername, mysqlPassword, mariadb);
    }

    // Limpeza do Cache de Rank/Nome da classe de Role.
    new Timer().scheduleAtFixedRate(RoleCache.clearCache(), TimeUnit.SECONDS.toMillis(60), TimeUnit.SECONDS.toMillis(60));
  }

  public static Database getInstance() {
    return instance;
  }

  public void setupBoosters() {
  }

  public abstract void setBooster(String minigame, String booster, double multiplier, long expires);

  public abstract NetworkBooster getBooster(String minigame);

  public abstract String getRankAndName(String player);

  public abstract int getPreference(String player, String id, int def);

  public abstract List<String[]> getLeaderBoard(String table, String... columns);

  public abstract void close();

  public abstract Map<String, Map<String, DataContainer>> load(String name) throws ProfileLoadException;

  public abstract void save(String name, Map<String, Map<String, DataContainer>> tableMap);

  public abstract void saveSync(String name, Map<String, Map<String, DataContainer>> tableMap);

  public abstract void saveTable(String name, String table, Map<String, DataContainer> rows, boolean async);

  public abstract String exists(String name);

  public abstract void update(String sql, Object... vars);

  public abstract void execute(String sql, Object... vars);

  public abstract int updateWithInsertId(String sql, Object... vars);

  public abstract CachedRowSet query(String query, Object... vars);
}
