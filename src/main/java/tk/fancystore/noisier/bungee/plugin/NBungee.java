package tk.fancystore.noisier.bungee.plugin;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.YamlConfiguration;
import tk.fancystore.noisier.bungee.plugin.logger.BungeeLogger;
import tk.fancystore.noisier.plugin.CorePlugin;
import tk.fancystore.noisier.plugin.config.FileUtils;
import tk.fancystore.noisier.plugin.config.NConfig;
import tk.fancystore.noisier.plugin.config.NWriter;
import tk.fancystore.noisier.plugin.logger.CoreLogger;
import tk.fancystore.noisier.reflection.Accessors;
import tk.fancystore.noisier.reflection.acessors.FieldAccessor;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class NBungee extends Plugin implements CorePlugin {

  private final FileUtils FILE_UTILS;
  private static final FieldAccessor<Logger> LOGGER_ACCESSOR = Accessors.getField(Plugin.class, "logger", Logger.class);

  public NBungee() {
    LOGGER_ACCESSOR.set(this, new BungeeLogger(this));
    this.FILE_UTILS = new FileUtils(this);
    this.start();
  }

  public abstract void start();

  public abstract void load();

  public abstract void enable();

  public abstract void disable();

  public void onLoad() {
    this.load();
  }

  public void onEnable() {
    this.enable();
  }

  public void onDisable() {
    this.disable();
  }

  public void delayedLogger(CoreLogger logger, Level level, String message) {
    ProxyServer.getInstance().getScheduler().runAsync(this, () -> logger.log(level, message));
  }

  public CoreLogger getCoreLogger() {
    return (BungeeLogger) this.getLogger();
  }

  public String getName() {
    return getDescription().getName();
  }

  public String getVersion() {
    return getDescription().getVersion();
  }

  public InputStream getResources(String name) {
    return this.getResourceAsStream(name);
  }

  public FileUtils getFileUtils() {
    return this.FILE_UTILS;
  }

  public NWriter getWriter(File file) {
    return this.getWriter(file, "");
  }

  public NWriter getWriter(File file, String header) {
    return new NWriter(this.getCoreLogger(), file, header);
  }

  private Configuration config;

  public void saveDefaultConfig() {
    File file = new File("plugins/" + this.getDescription().getName() + "/config.yml");
    if (!file.exists()) {
      //noinspection ResultOfMethodCallIgnored
      file.getParentFile().mkdirs();
      this.getFileUtils().copyFile(this.getResourceAsStream("config.yml"), file);
    }

    try {
      this.config = YamlConfiguration.getProvider(YamlConfiguration.class).load(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    }

  }

  public Configuration getConfig() {
    return this.config;
  }

  public NConfig getConfig(String name) {
    return this.getConfig("", name);
  }

  public NConfig getConfig(String path, String name) {
    return NConfig.getConfig(this, "plugins/" + this.getName() + "/" + path, name);
  }
}
