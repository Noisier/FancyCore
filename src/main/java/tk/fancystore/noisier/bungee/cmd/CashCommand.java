package tk.fancystore.noisier.bungee.cmd;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import tk.fancystore.noisier.cash.CashException;
import tk.fancystore.noisier.cash.CashManager;
import tk.fancystore.noisier.plugin.logger.LoggerLevel;
import tk.fancystore.noisier.role.Role;
import tk.fancystore.noisier.utils.StringUtils;

public class CashCommand extends Commands {

  public CashCommand() {
    super("cash");
  }

  @Override
  public void perform(CommandSender sender, String[] args) {
    if (args.length == 0) {
      if (sender instanceof ProxiedPlayer) {
        CashManager.sendMessage((ProxiedPlayer) sender, "display");
      } else {
        sendHelp(sender);
      }
      return;
    } else {
      if (!sender.hasPermission("fancycore.cmd.cash")) {
        return;
      }
    }

    String action = args[0].toLowerCase();
    if (!action.equals("set") && !action.equals("add") && !action.equals("remove")) {
      sendHelp(sender);
      return;
    }

    if (args.length <= 2) {
      sender.sendMessage(new TextComponent("§cUtilize /cash " + action + " [jogador] [quantia]"));
      return;
    }

    long amount;
    try {
      if (args[2].startsWith("-")) {
        throw new NumberFormatException();
      }

      amount = Long.parseLong(args[2]);
    } catch (NumberFormatException ex) {
      sender.sendMessage(new TextComponent("§cUtilize números válidos e positivos."));
      return;
    }

    try {
      switch (action.toLowerCase()) {
        case "set":
          CashManager.setCash(args[1], amount);
          sender.sendMessage(new TextComponent("§aVocê setou o Cash de " + Role.getColored(args[1]) + " §apara §b" + StringUtils.formatNumber(amount) + "§a."));
          break;
        case "add":
          CashManager.addCash(args[1], amount);
          sender.sendMessage(new TextComponent("§aVocê adicionou §b" + StringUtils.formatNumber(amount) + " §ade Cash para " + Role.getColored(args[1]) + "§a."));
          break;
        case "remove":
          CashManager.removeCash(args[1], amount);
          sender.sendMessage(new TextComponent("§aVocê removeu §b" + StringUtils.formatNumber(amount) + " §ade Cash de " + Role.getColored(args[1]) + "§a."));
      }
    } catch (CashException ex) {
      sender.sendMessage(new TextComponent(LoggerLevel.SEVERE.format(ex.getMessage())));
    }
  }

  public static void sendHelp(CommandSender sender) {
    sender.sendMessage(new TextComponent(
        " \n§6/cash set [jogador] [quantia] §f- §7Setar o cash do jogador." +
            "\n§6/cash add [jogador] [quantia] §f- §7Dar cash para um jogador." +
            "\n§6/cash remove [jogador] [quantia] §f- §7Remover o cash de um jogador.\n "
    ));
  }
}
