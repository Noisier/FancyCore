package tk.fancystore.noisier.plugin.logger;

import java.util.logging.Level;
import java.util.logging.LogRecord;

public interface CoreLogger {

  void info(String message);

  void warning(String message);

  void severe(String message);

  void run(Level level, String method, Runnable runnable);

  void log(LogRecord logRecord);

  void log(Level level, String message);

  void log(Level level, String message, Throwable throwable);

  CoreLogger getModule(String module);
}
