package tk.fancystore.noisier.database.tables;

import tk.fancystore.noisier.database.Database;
import tk.fancystore.noisier.database.data.DataContainer;
import tk.fancystore.noisier.database.data.DataTable;
import tk.fancystore.noisier.database.data.interfaces.DataTableInfo;

import java.util.LinkedHashMap;
import java.util.Map;

@DataTableInfo(
    name = "FancyCoreProfile",
    create = "CREATE TABLE IF NOT EXISTS `FancyCoreProfile` (`name` VARCHAR(32), `cash` LONG, `role` TEXT, `level` TEXT, `clan` TEXT, `social` TEXT, `deliveries` TEXT, `preferences` TEXT, `titles` TEXT, `boosters` TEXT, `achievements` TEXT, `selected` TEXT, `created` LONG, `lastlogin` LONG, PRIMARY KEY(`name`)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;",
    select = "SELECT * FROM `FancyCoreProfile` WHERE LOWER(`name`) = ?",
    insert = "INSERT INTO `FancyCoreProfile` VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
    update = "UPDATE `FancyCoreProfile` SET `cash` = ?, `role` = ?, `level` = ?, `clan` = ?, `social` = ?, `deliveries` = ?, `preferences` = ?, `titles` = ?, `boosters` = ?, `achievements` = ?, `selected` = ?, `created` = ?, `lastlogin` = ? WHERE LOWER(`name`) = ?"
)
public class ProfileTable extends DataTable {

  @Override
  public void init(Database database) {
  }

  public Map<String, DataContainer> getDefaultValues() {
    Map<String, DataContainer> defaultValues = new LinkedHashMap<>();
    defaultValues.put("cash", new DataContainer(0L));
    defaultValues.put("role", new DataContainer("Membro"));
    defaultValues.put("level", new DataContainer("{\"level\": 1, \"exp\": 0, \"claimed\": []}"));
    defaultValues.put("clan", new DataContainer(""));
    defaultValues.put("social", new DataContainer("{}"));
    defaultValues.put("deliveries", new DataContainer("{}"));
    defaultValues.put("preferences", new DataContainer("{\"pv\": 0, \"cm\": 0, \"mt\": 0, \"pm\": 0, \"bg\": 1, \"pl\": 0, \"fr\": 0, \"pr\": 0}"));
    defaultValues.put("titles", new DataContainer("[]"));
    defaultValues.put("boosters", new DataContainer("{}"));
    defaultValues.put("achievements", new DataContainer("[]"));
    defaultValues.put("selected", new DataContainer("{\"title\": \"0\", \"icon\": \"0\"}"));
    defaultValues.put("created", new DataContainer(System.currentTimeMillis()));
    defaultValues.put("lastlogin", new DataContainer(System.currentTimeMillis()));
    return defaultValues;
  }
}
