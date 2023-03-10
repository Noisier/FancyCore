package tk.fancystore.noisier.bungee.cmd;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;
import tk.fancystore.noisier.bungee.Main;

public abstract class Commands extends Command {

  public Commands(String name, String... aliases) {
    super(name, null, aliases);
    ProxyServer.getInstance().getPluginManager().registerCommand(Main.getInstance(), this);
  }

  public static void setupCommands() {
    new CashCommand();
    new FakeCommand();
    new FakeResetCommand();
    new FakeListCommand();
    new PartyCommand();
    new PartyChatCommand();
    new TellCommand();
    new ReplyCommand();
  }

  public abstract void perform(CommandSender sender, String[] args);

  @Override
  public void execute(CommandSender sender, String[] args) {
    this.perform(sender, args);
  }
}
