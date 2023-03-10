package tk.fancystore.noisier.bungee.plugin.logger;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginLogger;
import tk.fancystore.noisier.plugin.logger.CoreLogger;
import tk.fancystore.noisier.plugin.logger.LoggerLevel;

import java.util.logging.Level;
import java.util.logging.LogRecord;

public class BungeeLogger extends PluginLogger implements CoreLogger {

  private final Plugin plugin;
  private final String prefix;
  private final CommandSender sender;

  public BungeeLogger(Plugin plugin) {
    this(plugin, "[" + plugin.getDescription().getName() + "] ");
  }

  public BungeeLogger(Plugin plugin, String prefix) {
    super(plugin);
    this.plugin = plugin;
    this.prefix = prefix;
    this.sender = ProxyServer.getInstance().getConsole();
  }

  public void run(Level level, String method, Runnable runnable) {
    try {
      runnable.run();
    } catch (Exception ex) {
      this.log(level, method.replace("${n}", this.plugin.getDescription().getName()).replace("${v}", this.plugin.getDescription().getVersion()), ex);
    }
  }

  @Override
  public void log(LogRecord logRecord) {
    LoggerLevel level = LoggerLevel.fromName(logRecord.getLevel().getName());
    if (level == null) {
      return;
    }

    String message = logRecord.getMessage();

    StringBuilder result = new StringBuilder(this.prefix + message);
    if (logRecord.getThrown() != null) {
      result.append("\n").append(logRecord.getThrown().getLocalizedMessage());
      for (StackTraceElement ste : logRecord.getThrown().getStackTrace()) {
        result.append("\n").append(ste.toString());
      }
    }

    this.sender.sendMessage(new TextComponent(level.format(result.toString())));
  }

  public BungeeLogger getModule(String module) {
    return new BungeeLogger(this.plugin, this.prefix + module + ": ");
  }
}
