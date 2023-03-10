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

@DataTableInfo(
    name = "FancyCoreSkyWars",
    create = "CREATE TABLE IF NOT EXISTS `FancyCoreSkyWars` (`name` VARCHAR(32), `1v1kills` LONG, `1v1deaths` LONG, `1v1assists` LONG, `1v1games` LONG, `1v1wins` LONG, `2v2kills` LONG, `2v2deaths` LONG, `2v2assists` LONG, `2v2games` LONG, `2v2wins` LONG, `rankedkills` LONG, `rankeddeaths` LONG, `rankedassists` LONG, `rankedgames` LONG, `rankedwins` LONG, `rankedpoints` LONG, `monthlykills` LONG, `monthlydeaths` LONG, `monthlypoints` LONG, `monthlyassists` LONG, `monthlywins` LONG, `monthlygames` LONG, `month` TEXT, `coins` DOUBLE, `lastmap` LONG, `cosmetics` TEXT, `selected` TEXT, `kitconfig` TEXT, `leveling` TEXT, PRIMARY KEY(`name`)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;",
    select = "SELECT * FROM `FancyCoreSkyWars` WHERE LOWER(`name`) = ?",
    insert = "INSERT INTO `FancyCoreSkyWars` VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
    update = "UPDATE `FancyCoreSkyWars` SET `1v1kills` = ?, `1v1deaths` = ?, `1v1assists` = ?, `1v1games` = ?, `1v1wins` = ?, `2v2kills` = ?, `2v2deaths` = ?, `2v2assists` = ?, `2v2games` = ?, `2v2wins` = ?, `rankedkills` = ?, `rankeddeaths` = ?, `rankedassists` = ?, `rankedgames` = ?, `rankedwins` = ?, `rankedpoints` = ?, `monthlykills` = ?, `montlhydeaths` = ?, `monthlypoints` = ?, `monthlyassists` = ?, `monthlywins` = ?, `monthlygames` = ?, `month` = ?, `coins` = ?, `lastmap` = ?, `cosmetics` = ?, `selected` = ?, `kitconfig` = ?, `leveling` = ? WHERE LOWER(`name`) = ?"
)
public class SkyWarsTable extends DataTable {

  @Override
  public void init(Database database) {
    if (database.query("SHOW COLUMNS FROM `FancyCoreSkyWars` LIKE 'leveling'") == null) {
      database.execute(
          "ALTER TABLE `FancyCoreSkyWars` ADD `leveling` TEXT AFTER `kitconfig`");
    }
  }

  public Map<String, DataContainer> getDefaultValues() {
    Map<String, DataContainer> defaultValues = new LinkedHashMap<>();
    defaultValues.put("1v1kills", new DataContainer(0L));
    defaultValues.put("1v1deaths", new DataContainer(0L));
    defaultValues.put("1v1assists", new DataContainer(0L));
    defaultValues.put("1v1games", new DataContainer(0L));
    defaultValues.put("1v1wins", new DataContainer(0L));
    defaultValues.put("2v2kills", new DataContainer(0L));
    defaultValues.put("2v2deaths", new DataContainer(0L));
    defaultValues.put("2v2assists", new DataContainer(0L));
    defaultValues.put("2v2games", new DataContainer(0L));
    defaultValues.put("2v2wins", new DataContainer(0L));
    defaultValues.put("rankedkills", new DataContainer(0L));
    defaultValues.put("rankeddeaths", new DataContainer(0L));
    defaultValues.put("rankedassists", new DataContainer(0L));
    defaultValues.put("rankedgames", new DataContainer(0L));
    defaultValues.put("rankedwins", new DataContainer(0L));
    defaultValues.put("rankedpoints", new DataContainer(0L));
    for (String key : new String[]{"kills", "deaths", "points", "assists", "wins", "games"}) {
      defaultValues.put("monthly" + key, new DataContainer(0L));
    }
    defaultValues.put("month", new DataContainer((Calendar.getInstance().get(Calendar.MONTH) + 1) + "/" +
        Calendar.getInstance().get(Calendar.YEAR)));
    defaultValues.put("coins", new DataContainer(0L));
    defaultValues.put("lastmap", new DataContainer(0L));
    defaultValues.put("cosmetics", new DataContainer("{}"));
    defaultValues.put("selected", new DataContainer("{}"));
    defaultValues.put("kitconfig", new DataContainer("{}"));
    defaultValues.put("leveling", new DataContainer("{}"));
    return defaultValues;
  }
}
