package tk.fancystore.noisier.bukkit.player.hotbar;

import org.bukkit.entity.Player;
import tk.fancystore.noisier.bukkit.listeners.Listeners;
import tk.fancystore.noisier.bukkit.menus.MenuProfile;
import tk.fancystore.noisier.bukkit.menus.MenuServers;
import tk.fancystore.noisier.bukkit.player.Profile;
import tk.fancystore.noisier.utils.StringUtils;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public abstract class HotbarActionType {

  private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###.#");
  private static final Map<String, HotbarActionType> actionTypes = new HashMap<>();

  static {
    actionTypes.put("comando", new HotbarActionType() {

      @Override
      public void execute(Profile profile, String action) {
        profile.getPlayer().performCommand(action);
      }
    });
    actionTypes.put("mensagem", new HotbarActionType() {

      @Override
      public void execute(Profile profile, String action) {
        profile.getPlayer().sendMessage(StringUtils.formatColors(action).replace("\\n", "\n"));
      }
    });
    actionTypes.put("core", new HotbarActionType() {

      @Override
      public void execute(Profile profile, String action) {
        if (action.equalsIgnoreCase("jogos")) {
          new MenuServers(profile);
        } else if (action.equalsIgnoreCase("perfil")) {
          new MenuProfile(profile);
        } else if (action.equalsIgnoreCase("jogadores")) {
          Player player = profile.getPlayer();
          long start = Listeners.DELAY_PLAYERS.getOrDefault(player.getName(), 0L) ;
          if (start > System.currentTimeMillis()) {
            double time = (start - System.currentTimeMillis()) / 1000.0;
            if (time > 0.1) {
              String timeString = DECIMAL_FORMAT.format(time).replace(",", ".");
              if (timeString.endsWith("0")) {
                timeString = timeString.substring(0, timeString.lastIndexOf("."));
              }

              player.sendMessage("§cVocê precisa aguardar mais " + timeString + "s para utilizar novamente.");
              return;
            }
          }

          Listeners.DELAY_PLAYERS.put(player.getName(), System.currentTimeMillis() + 3000);
          profile.getPreferencesContainer().changePlayerVisibility();
          switch (profile.getPreferencesContainer().getPlayerVisibility()) {
            case TODOS:
              player.sendMessage("§aVisibilidade de jogadores ativada.");
              break;
            case NENHUM:
              player.sendMessage("§cVisibilidade de jogadores desativada.");
              break;
          }
          profile.refreshPlayers();
        }
      }
    });
  }

  public static void addActionType(String name, HotbarActionType actionType) {
    actionTypes.put(name.toLowerCase(), actionType);
  }

  public static HotbarActionType fromName(String name) {
    return actionTypes.get(name.toLowerCase());
  }

  public abstract void execute(Profile profile, String action);
}
