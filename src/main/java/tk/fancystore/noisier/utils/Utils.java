package tk.fancystore.noisier.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import tk.fancystore.noisier.utils.enums.EnumSound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Classe com utilitários diversos sem nicho predefinido.
 */
public class Utils {

  private static final Pattern VALID_DISCORD = Pattern.compile("^.{3,32}#[0-9]{4}$");
  private static final Pattern VALID_USERNAME = Pattern.compile("^[a-zA-Z0-9_]+$");

  public static float clampYaw(float yaw) {
    while (yaw < -180.0F) {
      yaw += 360.0F;
    }
    while (yaw >= 180.0F) {
      yaw -= 360.0F;
    }

    return yaw;
  }

  /**
   * Verifica se uma localização tem a chunk carregada no mundo.
   *
   * @param location localização para verificar
   * @return TRUE caso a localização tenha a chunk carregada, FALSE caso não tenha.
   */
  public static boolean isLoaded(Location location) {
    if (location == null || location.getWorld() == null) {
      return false;
    }

    int chunkX = location.getBlockX() >> 4;
    int chunkZ = location.getBlockZ() >> 4;
    return location.getWorld().isChunkLoaded(chunkX, chunkZ);
  }

  public static void playSound(Player player, String soundName, double v, double s) {
    try {
      float volume = (float) v;
      float speed = (float) s;
      EnumSound.valueOf(soundName).play(player, volume, speed);
    } catch (Exception ignored) {
    }
  }

  /**
   * Cria uma lista de páginas
   *
   * @param lines   linhas para organizar
   * @param perPage limite de linhas por páginas
   * @return Map com as páginas prontas
   */
  public static Map<Integer, List<String>> makePages(List<String> lines, int perPage) {
    Map<Integer, List<String>> pages = new HashMap<>();
    int index = perPage;
    for (String line : lines) {
      List<String> list = pages.computeIfAbsent(index / perPage, k -> new ArrayList<>());
      list.add(line);
      index++;
    }

    return pages;
  }

  /**
   * Cria uma barra de progresso
   *
   * @param progress       progresso completado
   * @param max            limite maximo do amount
   * @param bars           quantidade de barras no total
   * @param bar            simbolo da barra que você quer
   * @param progressColor  cor das barras de progresso completo
   * @param remainingColor cor das barras faltando
   * @return barra de progresso feita
   */
  public static String makeProgressBar(int progress, int max, int bars, String bar, String progressColor, String remainingColor) {
    if (progress == 0 || max == 0) {
      return remainingColor + StringUtils.repeat(bar, bars);
    }

    int percent = (progress * bars) / max;
    return progressColor + StringUtils.repeat(bar, percent) +
        remainingColor + StringUtils.repeat(bar, bars - percent);
  }

  /**
   * Verifica se o nome é valido
   *
   * @param name nome de entrada
   * @return se ele é valido ou não
   */
  public static boolean isValidUsername(String name) {
    return VALID_USERNAME.matcher(name).matches();
  }

  /**
   * Verifica se o Discord é valido
   *
   * @param name nome de entrada
   * @return se ele é valido ou não
   */
  public static boolean isValidDiscord(String name) {
    return VALID_DISCORD.matcher(name).matches();
  }
}
