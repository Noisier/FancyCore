package tk.fancystore.noisier.bungee;

import tk.fancystore.noisier.Manager;
import tk.fancystore.noisier.bungee.cmd.Commands;
import tk.fancystore.noisier.bungee.listener.Listeners;
import tk.fancystore.noisier.bungee.plugin.NBungee;
import tk.fancystore.noisier.database.Database;
import tk.fancystore.noisier.role.Role;

public class Main extends NBungee {

  private static Main instance;

  public static Main getInstance() {
    return instance;
  }

  @Override
  public void start() {
    instance = this;
  }

  @Override
  public void load() {
  }

  @Override
  public void enable() {
    saveDefaultConfig();
    Manager.setupManager(this);

    Database.setupDatabase(
        getConfig().getString("database.type"),
        getConfig().getString("database.mysql.host"),
        getConfig().getString("database.mysql.port"),
        getConfig().getString("database.mysql.name"),
        getConfig().getString("database.mysql.user"),
        getConfig().getString("database.mysql.password"),
        getConfig().getBoolean("database.mysql.mariadb", false)
    );

    Role.setupRoles();
    Commands.setupCommands();
    Listeners.setupListeners();

    this.getLogger().info("O plugin foi ativado.");
  }

  @Override
  public void disable() {
    this.getLogger().info("O plugin foi desativado.");
  }
}
