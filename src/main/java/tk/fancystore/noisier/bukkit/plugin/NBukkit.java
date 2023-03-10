package tk.fancystore.noisier.bukkit.plugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;
import tk.fancystore.noisier.bukkit.plugin.logger.BukkitLogger;
import tk.fancystore.noisier.plugin.CorePlugin;
import tk.fancystore.noisier.plugin.config.FileUtils;
import tk.fancystore.noisier.plugin.config.NConfig;
import tk.fancystore.noisier.plugin.config.NWriter;
import tk.fancystore.noisier.plugin.logger.CoreLogger;
import tk.fancystore.noisier.reflection.Accessors;
import tk.fancystore.noisier.reflection.acessors.FieldAccessor;

import java.io.File;
import java.io.InputStream;
import java.util.logging.Level;

public abstract class NBukkit extends JavaPlugin implements CorePlugin {

  private final FileUtils FILE_UTILS;
  private static final FieldAccessor<PluginLogger> LOGGER_ACCESSOR = Accessors.getField(JavaPlugin.class, "logger", PluginLogger.class);

  public NBukkit() {
    LOGGER_ACCESSOR.set(this, new BukkitLogger(this));
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
    Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> logger.log(level, message));
  }

  public CoreLogger getCoreLogger() {
    return (BukkitLogger) this.getLogger();
  }

  public String getVersion() {
    return this.getDescription().getVersion();
  }

  public FileUtils getFileUtils() {
    return this.FILE_UTILS;
  }

  public InputStream getResources(String name) {
    return this.getResource(name);
  }

  public NWriter getWriter(File file) {
    return this.getWriter(file, "");
  }

  public NWriter getWriter(File file, String header) {
    return new NWriter((BukkitLogger) this.getLogger(), file, header);
  }

  public NConfig getConfig(String name) {
    return this.getConfig("", name);
  }

  public NConfig getConfig(String path, String name) {
    return NConfig.getConfig(this, "plugins/" + this.getName() + "/" + path, name);
  }
}
