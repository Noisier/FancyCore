package tk.fancystore.noisier.bukkit.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tk.fancystore.noisier.bukkit.menus.profile.MenuStatistics;
import tk.fancystore.noisier.bukkit.player.Profile;
import tk.fancystore.noisier.database.Database;

public class StatsCommand extends Commands {

  public StatsCommand() {
    super("stats");
  }

  @Override
  public void perform(CommandSender sender, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("§cApenas jogadores podem utilizar este comando.");
      return;
    }

    Player player = (Player) sender;

    if (!player.hasPermission("fancycore.cmd.stats")) {
      player.sendMessage("§cVocê não tem permissão para utilizar este comando.");
      return;
    }

    if (args.length == 0) {
      player.sendMessage("§cUtilize /stats [jogador]");
      return;
    }

    if (args[0].equalsIgnoreCase(player.getName())) {
      player.sendMessage("§cVocê não pode ver seus stats através deste comando.");
      return;
    }

    String target = Bukkit.getPlayerExact(args[0]) == null ? null : args[0];
    if (target == null) {
      if ((target = Database.getInstance().exists(args[0])) == null) {
        player.sendMessage("§cEste jogador não existe.");
        return;
      }
    }

    new MenuStatistics(Profile.getProfile(player.getName()), target);
  }
}
