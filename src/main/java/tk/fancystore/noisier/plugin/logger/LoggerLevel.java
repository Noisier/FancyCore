package tk.fancystore.noisier.plugin.logger;

import java.util.Arrays;

public enum LoggerLevel {
  INFO("§a"),
  WARNING("§e"),
  SEVERE("§c");

  private final String color;

  LoggerLevel(String color) {
    this.color = color;
  }

  public static LoggerLevel fromName(String name) {
    return Arrays.stream(values()).filter(level -> level.name().equalsIgnoreCase(name)).findFirst().orElse(null);

  }

  public String format(String message) {
    return this.color + message;
  }
}
