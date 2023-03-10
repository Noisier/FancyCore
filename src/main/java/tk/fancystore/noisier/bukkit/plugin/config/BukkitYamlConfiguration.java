package tk.fancystore.noisier.bukkit.plugin.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.jline.internal.InputStreamReader;
import tk.fancystore.noisier.plugin.config.object.AbstractConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class BukkitYamlConfiguration extends AbstractConfiguration {

  private FileConfiguration config;

  public BukkitYamlConfiguration(File file) throws IOException {
    super(file);
    this.config = YamlConfiguration.loadConfiguration(new InputStreamReader(new FileInputStream(file), "UTF-8"));
  }

  public boolean createSection(String path) {
    this.config.createSection(path);
    return this.save();
  }

  public boolean set(String path, Object obj) {
    this.config.set(path, obj);
    return this.save();
  }

  public boolean contains(String path) {
    return this.config.contains(path);
  }

  public Object get(String path) {
    return this.config.get(path);
  }

  public int getInt(String path) {
    return this.getInt(path, 0);
  }

  public int getInt(String path, int def) {
    return this.config.getInt(path, def);
  }

  public long getLong(String path) {
    return this.config.getLong(path, 0);
  }

  public long getLong(String path, long def) {
    return this.config.getLong(path, def);
  }

  public double getDouble(String path) {
    return this.getDouble(path, 0.0D);
  }

  public double getDouble(String path, double def) {
    return this.config.getDouble(path, def);
  }

  public String getString(String path) {
    return this.config.getString(path);
  }

  public boolean getBoolean(String path) {
    return this.config.getBoolean(path);
  }

  public List<String> getStringList(String path) {
    return this.config.getStringList(path);
  }

  @Override
  public List<Integer> getIntegerList(String path) {
    return this.config.getIntegerList(path);
  }

  public Set<String> getKeys() {
    return this.config.getKeys(false);
  }

  public Collection<String> getKeys(String section) {
    return this.config.getConfigurationSection(section).getKeys(false);
  }

  public boolean reload() {
    try {
      this.config = YamlConfiguration.loadConfiguration(new InputStreamReader(new FileInputStream(this.file), "UTF-8"));
      return true;
    } catch (IOException ignored) {
      return false;
    }
  }

  public boolean save() {
    try {
      this.config.save(this.file);
      return true;
    } catch (IOException ignored) {
      return false;
    }
  }
}
