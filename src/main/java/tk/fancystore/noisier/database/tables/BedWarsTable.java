package tk.fancystore.noisier.database.tables;

import tk.fancystore.noisier.database.Database;
import tk.fancystore.noisier.database.data.DataContainer;
import tk.fancystore.noisier.database.data.DataTable;
import tk.fancystore.noisier.database.data.interfaces.DataTableInfo;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

@DataTableInfo(name = "FancyCoreBedWars",
    create = "CREATE TABLE IF NOT EXISTS `FancyCoreBedWars` (`name` VARCHAR(32), `" +
        "1v1kills` LONG, `1v1deaths` LONG, `1v1assists` LONG, `1v1games` LONG, `1v1bedsdestroyeds` LONG, `1v1bedslosteds` LONG, `1v1finalkills` LONG, `1v1finaldeaths` LONG, `1v1wins` LONG, `2v2kills` LONG, `2v2deaths` LONG, `2v2assists` LONG, `2v2games` LONG, `2v2bedsdestroyeds` LONG, `2v2bedslosteds` LONG, `2v2finalkills` LONG, `2v2finaldeaths` LONG, `2v2wins` LONG, `4v4kills` LONG," +
        " `4v4deaths` LONG, `4v4assists` LONG, `4v4games` LONG, `4v4bedsdestroyeds` LONG, `4v4bedslosteds` LONG, `4v4finalkills` LONG, `4v4finaldeaths` LONG, `4v4wins` LONG, `monthlykills` LONG, `monthlydeaths` LONG, `monthlyassists` LONG, `monthlybeds` LONG, `monthlywins` LONG, `monthlygames` LONG, `month` TEXT, `coins` DOUBLE," +
        " `lastmap` LONG, `cosmetics` TEXT, `selected` TEXT, `favorites` TEXT, PRIMARY KEY(`name`)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;",
    select = "SELECT * FROM `FancyCoreBedWars` WHERE LOWER(`name`) = ?",
    insert = "INSERT INTO `FancyCoreBedWars` VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
    update = "UPDATE `FancyCoreBedWars` SET `1v1kills` = ?, `1v1deaths` = ?, `1v1assists` = ?, `1v1games` = ?, `1v1bedsdestroyeds` = ?, `1v1bedslosteds` = ?, `1v1finalkills` = ?, `1v1finaldeaths` = ?, `1v1wins` = ?`, `2v2kills` = ?, `2v2deaths` = ?, `2v2assists` = ?, `2v2games` = ?, `2v2bedsdestroyeds` = ?, `2v2bedslosteds` = ?, `2v2finalkills` = ?, `2v2finaldeaths` = ?, `2v2wins` = ?`, `4v4kills` = ?, `4v4deaths` = ?, `4v4assists` = ?, `4v4games` = ?, `4v4bedsdestroyeds` = ?, `4v4bedslosteds` = ?, `4v4finalkills` = ?, `4v4finaldeaths` = ?, `monthlykills` = ?, `montlhydeaths` = ?, `monthlyassists` = ?, `monthlybeds` = ?, `monthlywins` = ?, `monthlygames` = ?, `month` = ?, `4v4wins` = ?, `coins` = ?, `lastmap` = ?, `cosmetics` = ?, `selected` = ?, `kitconfig` = ? WHERE LOWER(`name`) = ?")
public class BedWarsTable extends DataTable {

  @Override
  public void init(Database database) {
      if (database.query("SHOW COLUMNS FROM `FancyCoreBedWars` LIKE 'lastmap'") == null) {
        database.execute(
            "ALTER TABLE `FancyCoreBedWars` ADD `lastmap` LONG DEFAULT 0 AFTER `coins`, ADD `favorites` TEXT AFTER `selected`");
      }
  }

  public Map<String, DataContainer> getDefaultValues() {
    Map<String, DataContainer> defaultValues = new LinkedHashMap<>();
    for (String key : new String[]{"1v1", "2v2", "4v4"}) {
      defaultValues.put(key + "kills", new DataContainer(0L));
      defaultValues.put(key + "deaths", new DataContainer(0L));
      defaultValues.put(key + "games", new DataContainer(0L));
      defaultValues.put(key + "assists", new DataContainer(0L));
      defaultValues.put(key + "bedsdestroyeds", new DataContainer(0L));
      defaultValues.put(key + "bedslosteds", new DataContainer(0L));
      defaultValues.put(key + "finalkills", new DataContainer(0L));
      defaultValues.put(key + "finaldeaths", new DataContainer(0L));
      defaultValues.put(key + "wins", new DataContainer(0L));
    }
    for (String key : new String[]{"kills", "deaths", "assists", "beds", "wins", "games"}) {
      defaultValues.put("monthly" + key, new DataContainer(0L));
    }
    defaultValues.put("month", new DataContainer((Calendar.getInstance().get(Calendar.MONTH) + 1) + "/" + Calendar.getInstance().get(Calendar.YEAR)));
    defaultValues.put("coins", new DataContainer(0.0D));
    defaultValues.put("lastmap", new DataContainer(0L));
    defaultValues.put("cosmetics", new DataContainer("{}"));
    defaultValues.put("selected", new DataContainer("{}"));
    defaultValues.put("favorites", new DataContainer("{\"quickBuy\":{\"33\":\"4:axe\",\"22\":\"7:goldenapple\",\"34\":\"7:fireball\",\"23\":\"6:speed2\",\"24\":\"4:pickaxe\",\"25\":\"7:tnt\",\"37\":\"1:obsidian\",\"28\":\"1:oakwoodplanks\",\"29\":\"2:iron\",\"19\":\"1:wool\",\"40\":\"5:bow\",\"30\":\"3:iron\",\"20\":\"2:stone\",\"31\":\"5:arrow\",\"21\":\"3:chainmail\",\"43\":\"7:dreamdefensor\",\"32\":\"6:invisibility1\"}}"));
    return defaultValues;
  }
}