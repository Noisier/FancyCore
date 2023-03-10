package tk.fancystore.noisier.bukkit.menus;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import tk.fancystore.noisier.bukkit.Core;
import tk.fancystore.noisier.bukkit.deliveries.Delivery;
import tk.fancystore.noisier.bukkit.libraries.menu.PlayerMenu;
import tk.fancystore.noisier.bukkit.menus.profile.*;
import tk.fancystore.noisier.bukkit.player.Profile;
import tk.fancystore.noisier.database.data.container.LevelContainer;
import tk.fancystore.noisier.role.Role;
import tk.fancystore.noisier.utils.BukkitUtils;
import tk.fancystore.noisier.utils.StringUtils;
import tk.fancystore.noisier.utils.enums.EnumSound;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class MenuProfile extends PlayerMenu {

  private static final SimpleDateFormat SDF = new SimpleDateFormat("d 'de' MMMM. yyyy 'às' HH:mm",
      Locale.forLanguageTag("pt-BR"));

  public MenuProfile(Profile profile) {
    super(profile.getPlayer(), "Meu Perfil", 4);

    this.setItem(10, BukkitUtils.putProfileOnSkull(this.player, BukkitUtils.deserializeItemStack(
        "SKULL_ITEM:3 : 1 : name>&aInformações Pessoais : lore>&fCargo: " + Role.getPlayerRole(this.player).getName()
            + "\n&fCash: &b" + StringUtils.formatNumber(profile.getStats("FancyCoreProfile", "cash")) + "\n \n&fCadastrado em: &7" + SDF.format(profile.getDataContainer("FancyCoreProfile", "created").getAsLong()) + "\n&fÚltimo acesso: &7" + SDF
            .format(profile.getDataContainer("FancyCoreProfile", "lastlogin").getAsLong()))));

    this.setItem(12, BukkitUtils.deserializeItemStack(
        "PAPER : 1 : name>&aEstatísticas : lore>&7Visualize as suas estatísticas de\n&7cada Minigame do nosso servidor.\n \n&8Jogadores com o cargo &eVIP&8 ou superior\n&8podem usar &f/stats [jogador]&8 para ver\n&8as estatísticas de outros jogadores.\n \n&eClique para ver as estatísticas!"));

    this.setItem(13, BukkitUtils.deserializeItemStack(
        "POTION:8232 : 1 : hide>all : name>&aMultiplicadores de Coins : lore>&7Em nosso servidor existe um sistema\n&7de &6Multiplicadores de Coins &7que afetam\n&7a quantia de &6Coins &7ganhos nas partidas.\n \n&8Os Multiplicadores podem variar de\n&8pessoais ou gerais, podendo beneficiar\n&8você e até mesmo os outros jogadores.\n \n&eClique para ver seus multiplicadores!"));

    this.setItem(14, BukkitUtils.deserializeItemStack(
        "REDSTONE_COMPARATOR : 1 : name>&aPreferências : lore>&7Em nosso servidor você pode personalizar\n&7sua experiência de jogo por completo.\n&7Personalize várias opções únicas como\n&7você desejar!\n \n&8As opções incluem ativar ou desativar as\n&8mensagens privadas, os jogadores e outros.\n \n&eClique para personalizar as opções!"));

    this.setItem(15, BukkitUtils.deserializeItemStack(
        "MAP : 1 : hide>all : name>&aTítulos : lore>&7Esbanje estilo com um título exclusivo\n&7que ficará acima da sua cabeça para\n&7os outros jogadores.\n \n&8Lembrando que você não verá o título,\n&8apenas os outros jogadores.\n \n&eClique para selecionar um título!"));

    List<Delivery> deliveries = Delivery.listDeliveries().stream().filter(delivery -> delivery.hasPermission(player)
        && !profile.getDeliveriesContainer().alreadyClaimed(delivery.getId())).collect(Collectors.toList());

    StringBuilder list = new StringBuilder();
    deliveries.forEach(delivery -> list.append("&a ➟ &f").append(StringUtils.stripColors(
        delivery.getIcon(profile).getItemMeta().getDisplayName())).append("\n"));

    this.setItem(16, BukkitUtils.deserializeItemStack(
        (deliveries.size() > 0 ? "STORAGE_MINECART" : "MINECART") + " : 1 : name>&aEntregas : lore>" +
            (deliveries.size() > 0 ? "&7Você possui &n" + deliveries.size()
                + "&7 entrega" + (deliveries.size() > 1 ? "s" : "") + " para coletar.\n" + list + "\n&eClique para coletar!" : "&cVocê não possui entregas no momento.")));

    this.setItem(19, BukkitUtils.deserializeItemStack(
        //"SKULL_ITEM:3 : 1 : name>&aRedes Sociais : lore>&7Acrescente mais informações sobre \n&7você, adicione suas redes sociais favoritas\n&7no seu perfil.\n \n&8Você pode visitar as redes sociais de\n&8qualquer jogador no lobby utilizando o\n&8comando &f/social [jogador]&8.\n \n&eClique para modificar as suas redes sociais! : skinvalue>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzY4NWEwYmU3NDNlOTA2N2RlOTVjZDhjNmQxYmEyMWFiMjFkMzczNzFiM2Q1OTcyMTFiYjc1ZTQzMjc5In19fQ=="));
        "SKULL_ITEM:3 : 1 : name>&aRedes Sociais : lore>&7Acrescente mais informações sobre \n&7você, adicione suas redes sociais favoritas\n&7no seu perfil.\n \n&8Você pode visitar as redes sociais de\n&8qualquer jogador, basta clicar em alguém\n&8no lobby segurando &dShift&8.\n \n&eClique para modificar as suas redes sociais! : skinvalue>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzY4NWEwYmU3NDNlOTA2N2RlOTVjZDhjNmQxYmEyMWFiMjFkMzczNzFiM2Q1OTcyMTFiYjc1ZTQzMjc5In19fQ=="));

    LevelContainer lc = profile.getLevelContainer();
    this.setItem(24, BukkitUtils.deserializeItemStack(
        "BREWING_STAND_ITEM : 1 : name>&aNíveis : lore>&7Jogando partidas e completando missões você\n&7irá receber certa quantia de &6Experiência&7.\n&7A &6Experiência&7 será necessária para evoluir o\n&7seu nível e adquirir novas vantagens.\n \n&fNível da Conta: &b" + lc.getLevel().getIndex() + "\n&fExperiência necessária: &6" + StringUtils.formatNumber(lc.getLevel().getExperienceUntil(lc.getExp())) + "\n&fProgressão: &8[" + lc.getLevel().getProgressBar(profile) + "&8]\n \n&8Nosso sistema de nível é universal, portanto\n&8o nível que você adquirir em um minigame\n&8também irá valer para os demais minigames.\n \n&eClique para visualizar!"));

    this.setItem(25, BukkitUtils.deserializeItemStack(
        "GOLD_INGOT : 1 : name>&aDesafios : lore>&7Em nosso servidor existe um sistema\n&7de &6Desafios &7que se consiste em missões\n&7de realização única que lhe garante\n&7vários prêmios vitalícios.\n \n&8Os Prêmios variam entre títulos, coins,\n&8ícones de prestígio e outros cosméticos.\n \n&eClique para ver os desafios!"));

    this.register(Core.getInstance());
    this.open();
  }

  @EventHandler
  public void onInventoryClick(InventoryClickEvent evt) {
    if (evt.getInventory().equals(this.getInventory())) {
      evt.setCancelled(true);

      if (evt.getWhoClicked().equals(this.player)) {
        Profile profile = Profile.getProfile(this.player.getName());
        if (profile == null) {
          this.player.closeInventory();
          return;
        }

        if (evt.getClickedInventory() != null && evt.getClickedInventory().equals(this.getInventory())) {
          ItemStack item = evt.getCurrentItem();

          if (item != null && item.getType() != Material.AIR) {
            if (evt.getSlot() == 10) {
              EnumSound.ITEM_PICKUP.play(this.player, 0.5F, 2.0F);
            } else if (evt.getSlot() == 12) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuStatistics(profile);
            } else if (evt.getSlot() == 13) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuBoosters(profile);
            } else if (evt.getSlot() == 14) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuPreferences(profile);
            } else if (evt.getSlot() == 15) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuTitles(profile);
            } else if (evt.getSlot() == 16) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuDeliveries(profile, true);
            } else if (evt.getSlot() == 19) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuSocial(profile);
            } else if (evt.getSlot() == 24) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuLevelRewards(profile);
            } else if (evt.getSlot() == 25) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuAchievements(profile);
            }
          }
        }
      }
    }
  }

  public void cancel() {
    HandlerList.unregisterAll(this);
  }

  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent evt) {
    if (evt.getPlayer().equals(this.player)) {
      this.cancel();
    }
  }

  @EventHandler
  public void onInventoryClose(InventoryCloseEvent evt) {
    if (evt.getPlayer().equals(this.player) && evt.getInventory().equals(this.getInventory())) {
      this.cancel();
    }
  }
}