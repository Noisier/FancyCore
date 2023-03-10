package tk.fancystore.noisier.bungee.cmd;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import tk.fancystore.noisier.bungee.fake.FakeManager;

public class FakeResetCommand extends Commands {

  public FakeResetCommand() {
    super("faker");
  }

  @Override
  public void perform(CommandSender sender, String[] args) {
    if (!(sender instanceof ProxiedPlayer)) {
      sender.sendMessage(TextComponent.fromLegacyText("§cApenas jogadores podem utilizar este comando."));
      return;
    }

    ProxiedPlayer player = (ProxiedPlayer) sender;
    if (!player.hasPermission("fancycore.cmd.fake")) {
      player.sendMessage(TextComponent.fromLegacyText("§cVocê não possui permissão para utilizar este comando."));
      return;
    }

    if (!FakeManager.isFake(player.getName())) {
      player.sendMessage(TextComponent.fromLegacyText("§cVocê não está utilizando um nickname falso."));
      return;
    }

    FakeManager.removeFake(player);
  }
}
