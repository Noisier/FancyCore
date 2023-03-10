package tk.fancystore.noisier.database.data;

import tk.fancystore.noisier.database.Database;
import tk.fancystore.noisier.database.data.interfaces.DataTableInfo;
import tk.fancystore.noisier.database.tables.BedWarsTable;
import tk.fancystore.noisier.database.tables.ProfileTable;
import tk.fancystore.noisier.database.tables.SkyWarsTable;
import tk.fancystore.noisier.database.tables.TheBridgeTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@SuppressWarnings("StaticInitializerReferencesSubClass")
public abstract class DataTable {

  private static final List<DataTable> TABLES = new ArrayList<>();

  static {
    TABLES.add(new ProfileTable());
    TABLES.add(new BedWarsTable());
    TABLES.add(new SkyWarsTable());
    TABLES.add(new TheBridgeTable());
  }

  public static void registerTable(DataTable table) {
    TABLES.add(table);
  }

  public static Collection<DataTable> listTables() {
    return TABLES;
  }

  public abstract void init(Database database);

  public abstract Map<String, DataContainer> getDefaultValues();

  public DataTableInfo getInfo() {
    return this.getClass().getAnnotation(DataTableInfo.class);
  }
}
