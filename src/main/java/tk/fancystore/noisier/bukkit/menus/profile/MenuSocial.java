package tk.fancystore.noisier.bukkit.menus.profile;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import tk.fancystore.noisier.bukkit.Core;
import tk.fancystore.noisier.bukkit.libraries.menu.PlayerMenu;
import tk.fancystore.noisier.bukkit.listeners.Listeners;
import tk.fancystore.noisier.bukkit.menus.MenuProfile;
import tk.fancystore.noisier.bukkit.player.Profile;
import tk.fancystore.noisier.database.data.container.SocialContainer;
import tk.fancystore.noisier.role.Role;
import tk.fancystore.noisier.utils.BukkitUtils;
import tk.fancystore.noisier.utils.StringUtils;
import tk.fancystore.noisier.utils.enums.EnumSound;

public class MenuSocial extends PlayerMenu {

  public MenuSocial(Profile profile) {
    this(profile, profile);
  }

  private Profile target;

  public MenuSocial(Profile profile, Profile target) {
    super(profile.getPlayer(), "Redes Sociais", 4);

    this.target = target;

    String lore = profile == target ?
        "&fAtual:\n&a{link}\n \n{info}\n \n&eClique para modificar!" :
        "&7Link do perfil de " + Role.getPrefixed(target.getName()) + "\n&7nessa rede social.\n \n&fAtual: \n&a{link}\n \n&eClique para acessar!";

    SocialContainer sc = target.getSocialContainer();

    this.setItem(10, BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : name>&aTwitter : " +
        "lore>" + lore.replace("{link}", sc.getOrDefault("tt", "Nenhum")).replace("{info}", "&8informe seu Arroba para que\n&8os outros usuários possam te achar\n&8e possivelmente te seguir.") + " : " +
        "skinvalue>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzY4NWEwYmU3NDNlOTA2N2RlOTVjZDhjNmQxYmEyMWFiMjFkMzczNzFiM2Q1OTcyMTFiYjc1ZTQzMjc5In19fQ=="));

    this.setItem(11, BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : name>&aTikTok : " +
        "lore>" + lore.replace("{link}", sc.getOrDefault("ttk", "Nenhum")).replace("{info}", "&8informe seu Arroba para que\n&8os outros usuários possam te achar\n&8e possivelmente te seguir.") + " : " +
        "skinvalue>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYThlNDE1YjVjMWViNDU2NjNkYTZmZGFlNTc5OGRkMTk5YjIxNGQ0NzY3NmIzOTJjYTMwNmY3OTI5M2M5N2Y4NCJ9fX0="));

    this.setItem(12, BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : name>&aYouTube : " +
        "lore>" + lore.replace("{link}", sc.getOrDefault("yt", "Nenhum")).replace("{info}", "&8informe seu Canal para que\n&8os outros usuários possam te achar\n&8e possivelmente te seguir.") + " : " +
        "skinvalue>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjQzNTNmZDBmODYzMTQzNTM4NzY1ODYwNzViOWJkZjBjNDg0YWFiMDMzMWI4NzJkZjExYmQ1NjRmY2IwMjllZCJ9fX0="));

    this.setItem(13, BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : name>&aDicord : " +
        "lore>" + lore.replace("{link}", sc.getOrDefault("dc", "Nenhum")).replace("{info}", "&8Siga o padrão &fnome#0000&8 para que\n&8outros usuários possam te enviar uma\n&8solicitação de amizade.") + " : " +
        "skinvalue>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzg3M2MxMmJmZmI1MjUxYTBiODhkNWFlNzVjNzI0N2NiMzlhNzVmZjFhODFjYmU0YzhhMzliMzExZGRlZGEifX19"));

    this.setItem(14, BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : name>&aInstagram : " +
        "lore>" + lore.replace("{link}", sc.getOrDefault("ig", "Nenhum")).replace("{info}", "&8informe seu Arroba para que\n&8os outros usuários possam te achar\n&8e possivelmente te seguir.") + " : " +
        "skinvalue>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjViM2YyY2ZhMDczOWM0ZTgyODMxNmYzOWY5MGIwNWJjMWY0ZWQyN2IxZTM1ODg4NTExZjU1OGQ0Njc1In19fQ=="));

    this.setItem(15, BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : name>&aTwitch : " +
        "lore>" + lore.replace("{link}", sc.getOrDefault("tw", "Nenhum")).replace("{info}", "&8informe seu Usuário para que\n&8os outros usuários possam te achar\n&8e possivelmente te seguir.") + " : " +
        "skinvalue>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDZiZTY1ZjQ0Y2QyMTAxNGM4Y2RkZDAxNThiZjc1MjI3YWRjYjFmZDE3OWY0YzFhY2QxNThjODg4NzFhMTNmIn19fQ=="));

    this.setItem(16, BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : name>&aRedit : " +
        "lore>" + lore.replace("{link}", sc.getOrDefault("rd", "Nenhum")).replace("{info}", "&8informe seu Usuário para que\n&8os outros usuários possam te achar\n&8e possivelmente te seguir.") + " : " +
        "skinvalue>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWU5OGFkMzE5MWY3MDIzNjMzYWJiZmNhYjQ3ZmEwMzVkZjE2MDBmZDNmOGY2ZTliMTRmOGFkMTAxNzI3NzExMSJ9fX0="));

    this.setItem(31, BukkitUtils.deserializeItemStack("INK_SACK:1 : 1 : name>&c" + (profile == target ? "voltar" : "fechar")));

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
            if (evt.getSlot() == 31) {
              EnumSound.ITEM_PICKUP.play(this.player, 0.5F, 2.0F);

              if (target == profile) {
                new MenuProfile(profile);
              } else {
                player.closeInventory();
              }
            } else {
              String id = evt.getSlot() == 10 ?
                  "tt" : evt.getSlot() == 11 ? "ttk" : evt.getSlot() == 12 ?
                  "yt" : evt.getSlot() == 13 ? "dc" : evt.getSlot() == 14 ?
                  "ig" : evt.getSlot() == 15 ? "tw" : evt.getSlot() == 16 ?
                  "rd" : null;

              if (id != null) {
                if (target == profile) {
                  Listeners.SETTING_SOCIAL.put(player.getName(), id);

                  player.closeInventory();
                  player.spigot().sendMessage(StringUtils.toTextComponent(" \n&aDigite o seu usuário nessa rede social\n&7Ou clique </append>&7&lAQUI&7</actions>hover:show_text>&7Clique para cancelar!</and>click:run_command>event:cancel_social_setting</append> &7para cancelar essa operação\n "));
                } else {
                  String social = target.getSocialContainer().getOrDefault(id, "");

                  if (!social.isEmpty()) {
                    player.spigot().sendMessage(StringUtils.toTextComponent(" \n&aClique </append>&a&lAQUI&7</actions>hover:show_text>&7Clique para copiar!</and>click:suggest_command>" + social + "</append> &apara copiar a rede social de " + Role.getColored(target.getName()) + "&a!\n "));
                    player.closeInventory();
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  public void cancel() {
    this.target = null;
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