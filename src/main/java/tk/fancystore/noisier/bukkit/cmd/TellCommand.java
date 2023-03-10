package tk.fancystore.noisier.bukkit.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tk.fancystore.noisier.bukkit.player.Profile;
import tk.fancystore.noisier.bukkit.player.enums.PrivateMessages;
import tk.fancystore.noisier.role.Role;
import tk.fancystore.noisier.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class TellCommand extends Commands {

  public TellCommand() {
    super("tell", "r");
  }

  public static Map<String, String> TELL_CACHE = new HashMap<>();

  @Override
  public void perform(CommandSender sender, String label, String[] args) {
    if (sender instanceof Player) {
      Player player = (Player) sender;
      if (label.equalsIgnoreCase("tell")) {
        if (args.length <= 1) {
          player.sendMessage("§cUtilize /tell [jogador] [mensagem]");
          return;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
          player.sendMessage("§cEste usuário não está online.");
          return;
        }

        if (target.equals(player)) {
          player.sendMessage("§cVocê não pode enviar mensagens privadas para si mesmo.");
          return;
        }

        if (Profile.getProfile(player.getName()).getPreferencesContainer().getPrivateMessages() == PrivateMessages.NENHUM) {
          player.sendMessage("§cVocê não pode enviar mensagens privadas com as mensagens privadas desativadas.");
          return;
        }

        if (Profile.getProfile(target.getName()).getPreferencesContainer().getPrivateMessages() == PrivateMessages.NENHUM) {
          player.sendMessage("§cEste usuário desativou as mensagens privadas.");
          return;
        }

        String message = StringUtils.join(args, 1, " ");
        if (player.hasPermission("fancycore.tell.color")) {
          message = StringUtils.formatColors(message);
        }

        TELL_CACHE.put(player.getName(), target.getName());
        TELL_CACHE.put(target.getName(), player.getName());
        target.sendMessage("§8Mensagem de: " + Role.getColored(player.getName()) + "§8: §6" + message);
        player.sendMessage("§8Mensagem para: " + Role.getColored(target.getName()) + "§8: §6" + message);
      } else if (label.equalsIgnoreCase("r")) {
        if (args.length == 0) {
          player.sendMessage("§cUtilize /r [mensagem]");
          return;
        }

        if (!TELL_CACHE.containsKey(player.getName())) {
          player.sendMessage("§cVocê não tem ninguém para responder.");
          return;
        }

        player.performCommand("tell " + TELL_CACHE.get(player.getName()) + " " + StringUtils.join(args, " "));
      }
    }
  }
}