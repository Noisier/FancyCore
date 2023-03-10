package tk.fancystore.noisier.bukkit.cmd;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tk.fancystore.noisier.bukkit.Core;
import tk.fancystore.noisier.bukkit.player.Profile;
import tk.fancystore.noisier.database.data.container.LevelContainer;

public class CoreCommand extends Commands {

  public CoreCommand() {
    super("fancycore", "fc");
  }

  @Override
  public void perform(CommandSender sender, String label, String[] args) {
    if (sender instanceof Player) {
      Player player = (Player) sender;

      if (!player.hasPermission("fancycore.admin")) {
        player.sendMessage("§6FancyCore §bv" + Core.getInstance().getDescription().getVersion() + " §7Criado por §6Noisier§7.");
        return;
      }

      if (args.length == 0) {
        player.sendMessage(" \n§6/fc atualizar §f- §7Atualizar o FancyCore.\n ");
        return;
      }

      String action = args[0];
      if (action.equalsIgnoreCase("atualizar")) {
        int amount = 50;
        if (args.length > 1) {
          amount = Integer.parseInt(args[1]);
        }

        Profile.getProfile(player.getName()).addExp(amount);
        /*if (FancyUpdater.UPDATER != null) {
          if (!FancyUpdater.UPDATER.canDownload) {
            player.sendMessage(
                " \n§6§l[FANCYCORE]\n \n§aA atualização já está baixada, ela será aplicada na próxima reinicialização do servidor. Caso deseje aplicá-la agora, utilize o comando /stop.\n ");
            return;
          }
          FancyUpdater.UPDATER.canDownload = false;
          FancyUpdater.UPDATER.downloadUpdate(player);
        } else {*/
          player.sendMessage("§aO plugin já se encontra em sua última versão.");
        //}
      } else {
        player.sendMessage(" \n§6/kc atualizar §f- §7Atualizar o FancyCore.\n ");
      }
    } else {
      sender.sendMessage("§cApenas jogadores podem utilizar este comando.");
    }
  }
}
