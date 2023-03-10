package tk.fancystore.noisier.bukkit.plugin.logger;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLogger;
import tk.fancystore.noisier.plugin.logger.CoreLogger;
import tk.fancystore.noisier.plugin.logger.LoggerLevel;

import java.util.logging.Level;
import java.util.logging.LogRecord;

public class BukkitLogger extends PluginLogger implements CoreLogger {

  private final Plugin plugin;
  private final String prefix;
  private final CommandSender sender;

  public BukkitLogger(Plugin plugin) {
    this(plugin, "[" + plugin.getName() + "] ");
  }

  public BukkitLogger(Plugin plugin, String prefix) {
    super(plugin);
    this.plugin = plugin;
    this.prefix = prefix;
    this.sender = Bukkit.getConsoleSender();
  }

  public void run(Level level, String method, Runnable runnable) {
    try {
      runnable.run();
    } catch (Exception ex) {
      this.log(level, method.replace("${n}", plugin.getName()).replace("${v}", plugin.getDescription().getVersion()), ex);
    }
  }

  @Override
  public void log(LogRecord logRecord) {
    LoggerLevel level = LoggerLevel.fromName(logRecord.getLevel().getName());
    if (level == null) {
      return;
    }

    String message = logRecord.getMessage();

    // Removendo mensagem de aviso chata
    if (message.equals("Default system encoding may have misread config.yml from plugin jar")) {
      return;
    }

    StringBuilder result = new StringBuilder(this.prefix + message);
    if (logRecord.getThrown() != null) {
      result.append("\n").append(logRecord.getThrown().getLocalizedMessage());
      for (StackTraceElement ste : logRecord.getThrown().getStackTrace()) {
        result.append("\n").append(ste.toString());
      }
    }

    this.sender.sendMessage(level.format(result.toString()));
  }

  public BukkitLogger getModule(String module) {
    return new BukkitLogger(this.plugin, this.prefix + module + ": ");
  }
}