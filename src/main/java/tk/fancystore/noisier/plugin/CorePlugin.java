package tk.fancystore.noisier.plugin;

import tk.fancystore.noisier.plugin.config.FileUtils;
import tk.fancystore.noisier.plugin.config.NConfig;
import tk.fancystore.noisier.plugin.config.NWriter;
import tk.fancystore.noisier.plugin.logger.CoreLogger;

import java.io.File;
import java.io.InputStream;
import java.util.logging.Level;

public interface CorePlugin {

  void delayedLogger(CoreLogger logger, Level level, String message);

  CoreLogger getCoreLogger();

  String getName();

  String getVersion();

  FileUtils getFileUtils();

  InputStream getResources(String name);

  NWriter getWriter(File file);

  NWriter getWriter(File file, String header);

  NConfig getConfig(String name);

  NConfig getConfig(String path, String name);
}
