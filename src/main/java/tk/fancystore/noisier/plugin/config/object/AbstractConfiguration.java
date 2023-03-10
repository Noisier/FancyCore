package tk.fancystore.noisier.plugin.config.object;

import java.io.File;
import java.util.Collection;
import java.util.List;

public abstract class AbstractConfiguration {

  protected File file;

  public AbstractConfiguration(File file) {
    this.file = file;
  }

  public abstract boolean createSection(String path);

  public abstract boolean set(String path, Object value);

  public abstract boolean contains(String path);

  public abstract Object get(String path);

  public abstract int getInt(String path);

  public abstract int getInt(String path, int def);

  public abstract long getLong(String path);

  public abstract long getLong(String path, long def);

  public abstract double getDouble(String path);

  public abstract double getDouble(String path, double def);

  public abstract String getString(String path);

  public abstract boolean getBoolean(String path);

  public abstract List<String> getStringList(String path);

  public abstract List<Integer> getIntegerList(String path);

  public abstract Collection<String> getKeys();

  public abstract Collection<String> getKeys(String path);

  public abstract boolean reload();

  public abstract boolean save();
}
