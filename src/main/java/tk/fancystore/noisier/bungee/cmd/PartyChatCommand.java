package tk.fancystore.noisier.bungee.cmd;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import tk.fancystore.noisier.bungee.party.BungeeParty;
import tk.fancystore.noisier.bungee.party.BungeePartyManager;
import tk.fancystore.noisier.role.Role;
import tk.fancystore.noisier.utils.StringUtils;

public class PartyChatCommand extends Commands {

  public PartyChatCommand() {
    super("p");
  }

  @Override
  public void perform(CommandSender sender, String[] args) {
    if (!(sender instanceof ProxiedPlayer)) {
      sender.sendMessage(TextComponent.fromLegacyText("§cApenas jogadores podem utilizar este comando."));
      return;
    }

    ProxiedPlayer player = (ProxiedPlayer) sender;
    if (args.length == 0) {
      player.sendMessage(TextComponent.fromLegacyText("§cUtilize /p [mensagem] para conversar com a sua Party."));
      return;
    }

    BungeeParty party = BungeePartyManager.getMemberParty(player.getName());
    if (party == null) {
      player.sendMessage(TextComponent.fromLegacyText("§cVocê não pertence a uma Party."));
      return;
    }

    party.broadcast("§d[Party] " + Role.getPrefixed(player.getName()) + "§f: " + StringUtils.join(args, " "));
  }
}
