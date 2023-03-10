package tk.fancystore.noisier.bungee.plugin.config;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.YamlConfiguration;
import tk.fancystore.noisier.plugin.config.object.AbstractConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class BungeeYamlConfiguration extends AbstractConfiguration {

  private Configuration config;

  public BungeeYamlConfiguration(File file) throws IOException {
    super(file);
    this.config = net.md_5.bungee.config.YamlConfiguration.getProvider(net.md_5.bungee.config.YamlConfiguration.class).load(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
  }

  public boolean createSection(String path) {
    return this.set(path, new HashMap<>());
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
    return this.config.getDouble(path, 0.0D);
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
    return this.config.getIntList(path);
  }

  public Collection<String> getKeys() {
    return this.config.getKeys();
  }

  public Collection<String> getKeys(String section) {
    return this.config.getSection(section).getKeys();
  }

  public boolean reload() {
    try {
      this.config = YamlConfiguration.getProvider(YamlConfiguration.class).load(new InputStreamReader(new FileInputStream(this.file), StandardCharsets.UTF_8));
      return true;
    } catch (IOException ignored) {
      return false;
    }
  }

  public boolean save() {
    try {
      YamlConfiguration.getProvider(YamlConfiguration.class).save(this.config, this.file);
      return true;
    } catch (IOException ignored) {
      return false;
    }
  }
}
