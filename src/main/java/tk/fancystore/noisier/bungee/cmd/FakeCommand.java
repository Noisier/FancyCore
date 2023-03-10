package tk.fancystore.noisier.bungee.cmd;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import tk.fancystore.noisier.Manager;
import tk.fancystore.noisier.bungee.fake.FakeManager;
import tk.fancystore.noisier.role.Role;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class FakeCommand extends Commands {

  public FakeCommand() {
    super("fake");
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

    if (FakeManager.getRandomNicks().stream().noneMatch(FakeManager::isUsable)) {
      player.sendMessage(TextComponent.fromLegacyText(" \n §c§lALTERAR NICKNAME\n \n §cNenhum nickname está disponível para uso no momento.\n "));
      return;
    }

    if (args.length == 0) {
      FakeManager.sendRole(player, null);
      return;
    }

    String roleName = args[0];
    if (!FakeManager.isFakeRole(roleName)) {
      FakeManager.sendRole(player, "VILLAGER_NO");
      return;
    }

    if (Role.getRoleByName(roleName) == null) {
      FakeManager.sendRole(player, "VILLAGER_NO");
      return;
    }

    if (args.length == 1) {
      FakeManager.sendSkin(player, roleName, "ORB_PICKUP");
      return;
    }

    String skin = args[1];
    if (!skin.equalsIgnoreCase("alex") && !skin.equalsIgnoreCase("steve") && !skin.equalsIgnoreCase("you")) {
      FakeManager.sendSkin(player, roleName, "VILLAGER_NO");
      return;
    }

    List<String> enabled = FakeManager.getRandomNicks().stream().filter(FakeManager::isUsable).collect(Collectors.toList());
    String fakeName = enabled.isEmpty() ? null : enabled.get(ThreadLocalRandom.current().nextInt(enabled.size()));
    if (fakeName == null) {
      player.sendMessage(TextComponent.fromLegacyText(" \n §c§lALTERAR NICKNAME\n \n §cNenhum nickname está disponível para uso no momento.\n "));
      return;
    }

    enabled.clear();
    FakeManager.applyFake(player, fakeName, roleName, skin.equalsIgnoreCase("steve") ? FakeManager.STEVE : skin.equalsIgnoreCase("you") ? Manager.getSkin(player.getName(), "signature") : FakeManager.ALEX);
  }
}
