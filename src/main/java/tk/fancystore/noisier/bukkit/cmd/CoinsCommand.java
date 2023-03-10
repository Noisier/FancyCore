package tk.fancystore.noisier.bukkit.cmd;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tk.fancystore.noisier.bukkit.player.Profile;
import tk.fancystore.noisier.utils.StringUtils;

public class CoinsCommand extends Commands {

  public CoinsCommand() {
    super("coins");
  }

  @Override
  public void perform(CommandSender sender, String label, String[] args) {
    if (sender instanceof Player) {
      Player player = (Player) sender;
      Profile profile = Profile.getProfile(player.getName());

      player.sendMessage("\n§eSeus coins:");
      for (String name : new String[]{"Bed Wars", "Sky Wars", "The Bridge"}) {
        player.sendMessage(" §8▪ §f" + name + " §7" + StringUtils
            .formatNumber(profile.getCoins("FancyCore" + name.replace(" ", ""))));
      }
      player.sendMessage("\n");
    } else {
      sender.sendMessage("§cApenas jogadores podem utilizar este comando.");
    }
  }
}