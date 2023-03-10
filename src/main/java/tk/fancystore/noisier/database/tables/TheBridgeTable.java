package tk.fancystore.noisier.database.tables;

import tk.fancystore.noisier.database.Database;
import tk.fancystore.noisier.database.HikariDatabase;
import tk.fancystore.noisier.database.MySQLDatabase;
import tk.fancystore.noisier.database.data.DataContainer;
import tk.fancystore.noisier.database.data.DataTable;
import tk.fancystore.noisier.database.data.interfaces.DataTableInfo;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

@DataTableInfo(name = "FancyCoreTheBridge",
    create = "CREATE TABLE IF NOT EXISTS `FancyCoreTheBridge` (`name` VARCHAR(32), `1v1kills` LONG, `1v1deaths` LONG, `1v1games` LONG, `1v1points` LONG, `1v1wins` LONG, `2v2kills` LONG, `2v2deaths` LONG, `2v2games` LONG, `2v2points` LONG, `2v2wins` LONG, `monthlykills` LONG, `monthlydeaths` LONG, `monthlypoints` LONG, `monthlywins` LONG, `monthlygames` LONG, `month` TEXT, `coins` DOUBLE, `winstreak` LONG, `laststreak` LONG, `lastmap` LONG, `hotbar` TEXT, `cosmetics` TEXT, `selected` TEXT, PRIMARY KEY(`name`)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;",
    select = "SELECT * FROM `FancyCoreTheBridge` WHERE LOWER(`name`) = ?",
    insert = "INSERT INTO `FancyCoreTheBridge` VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
    update = "UPDATE `FancyCoreTheBridge` SET `1v1kills` = ?, `1v1deaths` = ?, `1v1games` = ?, `1v1points` = ?, `1v1wins` = ?, `2v2kills` = ?, `2v2deaths` = ?, `2v2games` = ?, `2v2points` = ?, `2v2wins` = ?, `monthlykills` = ?, `montlhydeaths` = ?, `monthlypoints` = ?, `monthlywins` = ?, `monthlygames` = ?, `month` = ?, `coins` = ?, `winstreak` = ?, `laststreak` = ?, `lastmap` = ?, `hotbar` = ?, `cosmetics` = ?, `selected` = ? WHERE LOWER(`name`) = ?")
public class TheBridgeTable extends DataTable {

  @Override
  public void init(Database database) {
    if (database.query("SHOW COLUMNS FROM `FancyCoreTheBridge` LIKE 'hotbar'") == null) {
      database.execute("ALTER TABLE `FancyCoreTheBridge` ADD `hotbar` TEXT AFTER `lastmap`");
    }
  }

  public Map<String, DataContainer> getDefaultValues() {
    Map<String, DataContainer> defaultValues = new LinkedHashMap<>();
    defaultValues.put("1v1kills", new DataContainer(0L));
    defaultValues.put("1v1deaths", new DataContainer(0L));
    defaultValues.put("1v1games", new DataContainer(0L));
    defaultValues.put("1v1points", new DataContainer(0L));
    defaultValues.put("1v1wins", new DataContainer(0L));
    defaultValues.put("2v2kills", new DataContainer(0L));
    defaultValues.put("2v2deaths", new DataContainer(0L));
    defaultValues.put("2v2games", new DataContainer(0L));
    defaultValues.put("2v2points", new DataContainer(0L));
    defaultValues.put("2v2wins", new DataContainer(0L));
    for (String key : new String[]{"kills", "deaths", "points", "wins", "games"}) {
      defaultValues.put("monthly" + key, new DataContainer(0L));
    }
    defaultValues.put("month", new DataContainer((Calendar.getInstance().get(Calendar.MONTH) + 1) + "/" +
        Calendar.getInstance().get(Calendar.YEAR)));
    defaultValues.put("coins", new DataContainer(0.0D));
    defaultValues.put("winstreak", new DataContainer(0L));
    defaultValues.put("laststreak", new DataContainer(System.currentTimeMillis()));
    defaultValues.put("lastmap", new DataContainer(0L));
    defaultValues.put("hotbar", new DataContainer("{}"));
    defaultValues.put("cosmetics", new DataContainer("{}"));
    defaultValues.put("selected", new DataContainer("{}"));
    return defaultValues;
  }
}
