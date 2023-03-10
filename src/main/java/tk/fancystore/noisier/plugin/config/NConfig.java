package tk.fancystore.noisier.plugin.config;

import tk.fancystore.noisier.Manager;
import tk.fancystore.noisier.bukkit.plugin.config.BukkitYamlConfiguration;
import tk.fancystore.noisier.bungee.plugin.config.BungeeYamlConfiguration;
import tk.fancystore.noisier.plugin.CorePlugin;
import tk.fancystore.noisier.plugin.config.object.AbstractConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class NConfig {

  private final CorePlugin plugin;

  private final String path;
  private final String name;
  private final String section;

  private final File file;
  private AbstractConfiguration config;

  private static final Map<String, NConfig> cache_configs = new HashMap<>();
  private static final Map<String, NConfig> cache_sections = new HashMap<>();

  public NConfig(CorePlugin plugin, String path, String name) {
    this(plugin, path, name, null);
  }

  public NConfig(CorePlugin plugin, String path, String name, String section) {
    this.plugin = plugin;
    this.path = path;
    this.name = name;
    this.section = section;

    this.file = new File(path + "/" + name + ".yml");
    if (!this.file.exists()) {
      //noinspection ResultOfMethodCallIgnored
      this.file.getParentFile().mkdirs();

      InputStream in = plugin.getResources(name + ".yml");
      if (in != null) {
        plugin.getFileUtils().copyFile(in, this.file);
      } else {
        try {
          //noinspection ResultOfMethodCallIgnored
          this.file.createNewFile();
        } catch (IOException ex) {
          plugin.getCoreLogger().log(Level.SEVERE, "Unexpected error ocurred creating file " + this.file.getName() + ": ", ex);
        }
      }
    }

    try {
      this.config = Manager.BUNGEE ? new BungeeYamlConfiguration(this.file) : new BukkitYamlConfiguration(this.file);
    } catch (IOException ex) {
      plugin.getCoreLogger().log(Level.SEVERE, "Unexpected error ocurred creating config " + this.file.getName() + ": ", ex);
    }
  }

  public static NConfig getConfig(CorePlugin plugin, String path, String name) {
    String key = path + "/" + name;

    if (!cache_configs.containsKey(key)) {
      cache_configs.put(key, new NConfig(plugin, path, name));
    }

    return cache_configs.get(key);
  }

  public NConfig getSection(String path) {
    String key = this.path + "/" + this.name + ".yml" + "." + path;

    if (!cache_sections.containsKey(key)) {
      if (this.contains(path)) {
        cache_sections.put(key, new NConfig(this.plugin, this.path, this.name, section != null ? section + "." + path : path));
      } else {
        return null;
      }
    }

    return cache_sections.get(key);
  }

  public boolean createSection(String path) {
    return this.config.createSection(section != null ? section + "." + path : path);
  }

  public boolean set(String path, Object obj) {
    return this.config.set(section != null ? section + "." + path : path, obj);
  }

  public boolean contains(String path) {
    return this.config.contains(section != null ? section + "." + path : path);
  }

  public Object get(String path) {
    return this.config.get(section != null ? section + "." + path : path);
  }

  public int getInt(String path) {
    return this.config.getInt(section != null ? section + "." + path : path);
  }

  public int getInt(String path, int def) {
    return this.config.getInt(section != null ? section + "." + path : path, def);
  }

  public long getLong(String path) {
    return this.config.getLong(section != null ? section + "." + path : path);
  }

  public long getLong(String path, long def) {
    return this.config.getLong(section != null ? section + "." + path : path, def);
  }

  public double getDouble(String path) {
    return this.config.getDouble(section != null ? section + "." + path : path);
  }

  public double getDouble(String path, double def) {
    return this.config.getDouble(section != null ? section + "." + path : path, def);
  }

  public String getString(String path) {
    return this.config.getString(section != null ? section + "." + path : path);
  }

  public String getString(String path, String def) {
    String s = section != null ? section + "." + path : path;
    return !this.config.contains(s) ? def : this.config.getString(s);
  }

  public boolean getBoolean(String path) {
    return this.config.getBoolean(section != null ? section + "." + path : path);
  }

  public boolean getBoolean(String path, boolean def) {
    String s = section != null ? section + "." + path : path;
    return !this.config.contains(s) ? def : this.config.getBoolean(s);
  }

  public List<String> getStringList(String path) {
    return this.config.getStringList(section != null ? section + "." + path : path);
  }

  public List<Integer> getIntegerList(String path) {
    return this.config.getIntegerList(section != null ? section + "." + path : path);
  }

  public Collection<String> getKeys() {
    return section != null ? this.config.getKeys(section) : this.config.getKeys();
  }

  public Collection<String> getKeys(String section) {
    return this.config.getKeys(section);
  }

  public boolean reload() {
    return this.config.reload();
  }

  public boolean save() {
    return this.config.save();
  }

  public File getFile() {
    return this.file;
  }
}
