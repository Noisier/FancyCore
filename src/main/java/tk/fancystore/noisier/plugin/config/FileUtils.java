package tk.fancystore.noisier.plugin.config;

import tk.fancystore.noisier.plugin.CorePlugin;
import tk.fancystore.noisier.plugin.logger.CoreLogger;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

public class FileUtils {

  private final CoreLogger logger;

  public FileUtils(CorePlugin plugin) {
    this.logger = plugin.getCoreLogger();
  }

  public void deleteFile(File file) {
    if (!file.exists()) {
      return;
    }

    if (file.isDirectory()) {
      for (File f : Objects.requireNonNull(file.listFiles())) {
        deleteFile(f);
      }
    }

    //noinspection ResultOfMethodCallIgnored
    file.delete();
  }

  public void copyFiles(File in, File out, String... ignore) {
    List<String> list = Arrays.asList(ignore);
    if (in.isDirectory()) {
      if (!out.exists()) {
        //noinspection ResultOfMethodCallIgnored
        out.mkdirs();
      }

      for (File file : Objects.requireNonNull(in.listFiles())) {
        if (list.contains(file.getName())) {
          continue;
        }

        copyFiles(file, new File(out, file.getName()));
      }
    } else {
      try {
        copyFile(new FileInputStream(in), out);
      } catch (IOException ex) {
        logger.log(Level.WARNING, "Unexpected error ocurred copying file \"" + out.getName() + "\"!", ex);
      }
    }
  }

  public void copyFile(InputStream input, File out) {
    FileOutputStream ou = null;
    try {
      ou = new FileOutputStream(out);
      byte[] buff = new byte[1024];
      int len;
      while ((len = input.read(buff)) > 0) {
        ou.write(buff, 0, len);
      }
    } catch (IOException ex) {
      logger.log(Level.WARNING, "Unexpected error ocurred copying file \"" + out.getName() + "\"!", ex);
    } finally {
      try {
        if (ou != null) {
          ou.close();
        }
        if (input != null) {
          input.close();
        }
      } catch (IOException ignored) {
      }
    }
  }
}
