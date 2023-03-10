package tk.fancystore.noisier.bukkit.menus.profile;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import tk.fancystore.noisier.bukkit.Core;
import tk.fancystore.noisier.bukkit.libraries.menu.UpdatablePlayerMenu;
import tk.fancystore.noisier.bukkit.listeners.Listeners;
import tk.fancystore.noisier.bukkit.menus.MenuProfile;
import tk.fancystore.noisier.bukkit.player.Profile;
import tk.fancystore.noisier.bukkit.player.enums.*;
import tk.fancystore.noisier.database.data.container.PreferencesContainer;
import tk.fancystore.noisier.utils.BukkitUtils;
import tk.fancystore.noisier.utils.StringUtils;
import tk.fancystore.noisier.utils.enums.EnumSound;

public class MenuPreferences extends UpdatablePlayerMenu {

  public enum PreferenceSection {
    CHAT("Chat", 46), GAME("Partida", 47), LOBBY("Lobby", 48);

    private final String name;
    private final int slot;

    PreferenceSection(String name, int slot) {
      this.name = name;
      this.slot = slot;
    }

    public String getName() {
      return this.name;
    }

    public int getSlot() {
      return slot;
    }
  }

  public Profile profile;
  public PreferenceSection section;

  public MenuPreferences(Profile profile) {
    this(profile, PreferenceSection.CHAT);
  }

  public MenuPreferences(Profile profile, PreferenceSection section) {
    super(profile.getPlayer(), "Preferências - " + section.getName(), 6);
    this.profile = profile;
    this.section = section;

    PreferencesContainer pc = profile.getPreferencesContainer();
    if (this.section == PreferenceSection.CHAT) {
      ChatMessages cm = pc.getChatMessages();
      this.setItem(10, BukkitUtils.deserializeItemStack("PAPER : 1 : name>&aMensagens do Chat : lore>&7Ative ou desative as mensagens no chat."));
      this.setItem(19, BukkitUtils.deserializeItemStack("INK_SACK:" + cm.getInkSack() + " : 1 : name>" + cm.getName() + " : lore>&fEstado: &7" + StringUtils.stripColors(cm.getName()) + "\n \n&eClique para modificar!"));

      ChatMention mt = pc.getChatMention();
      this.setItem(11, BukkitUtils.deserializeItemStack("MAP : 1 : hide>all : name>&aMenção no Chat : lore>&7Ative ou desative as menções no chat."));
      this.setItem(20, BukkitUtils.deserializeItemStack("INK_SACK:" + mt.getInkSack() + " : 1 : name>" + mt.getName() + " : lore>&fEstado: &7" + StringUtils.stripColors(mt.getName()) + "\n \n&eClique para modificar!"));

      PrivateMessages pm = pc.getPrivateMessages();
      this.setItem(12, BukkitUtils.deserializeItemStack("EMPTY_MAP : 1 : name>&aMensagens privadas : lore>&7Ative ou desative as mensagens\n&7enviadas através do tell."));
      this.setItem(21, BukkitUtils.deserializeItemStack("INK_SACK:" + pm.getInkSack() + " : 1 : name>" + pm.getName() + " : lore>&fEstado: &7" + StringUtils.stripColors(pm.getName()) + "\n \n&eClique para modificar!"));

      FriendsRequests fr = pc.getFriendsRequests();
      this.setItem(13, BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : name>&aSolicitações de amizade : lore>&7Ative ou desative as solicitações de\n&7amizade. : skinvalue>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzZjYmFlNzI0NmNjMmM2ZTg4ODU4NzE5OGM3OTU5OTc5NjY2YjRmNWE0MDg4ZjI0ZTI2ZTA3NWYxNDBhZTZjMyJ9fX0="));
      this.setItem(22, BukkitUtils.deserializeItemStack("INK_SACK:" + fr.getInkSack() + " : 1 : name>" + fr.getName() + " : lore>&fEstado: &7" + StringUtils.stripColors(fr.getName()) + "\n \n&eClique para modificar!"));

      PartyRequests pr = pc.getPartyRequests();
      this.setItem(14, BukkitUtils.deserializeItemStack("RED_ROSE : 1 : name>&aPedidos de Party : lore>&7Ative ou desative os pedidos de\n&7party."));
      this.setItem(23, BukkitUtils.deserializeItemStack("INK_SACK:" + pr.getInkSack() + " : 1 : name>" + pr.getName() + " : lore>&fEstado: &7" + StringUtils.stripColors(fr.getName()) + "\n \n&eClique para modificar!"));
    } else if (this.section == PreferenceSection.GAME) {
      BloodAndGore bg = pc.getBloodAndGore();
      this.setItem(10, BukkitUtils.deserializeItemStack("REDSTONE : 1 : name>&aViolência : lore>&7Ative ou desative as partículas\n&7de sangue no PvP."));
      this.setItem(19, BukkitUtils.deserializeItemStack("INK_SACK:" + bg.getInkSack() + " : 1 : name>" + bg.getName() + " : lore>&fEstado: &7" + StringUtils.stripColors(bg.getName()) + "\n \n&eClique para modificar!"));
    } else if (this.section == PreferenceSection.LOBBY) {
      ProtectionLobby pl = pc.getProtectionLobby();
      this.setItem(11, BukkitUtils.deserializeItemStack("NETHER_STAR : 1 : name>&aProteção no /lobby : lore>&7Ative ou desative o pedido de\n&7confirmação ao utilizar /lobby."));
      this.setItem(20, BukkitUtils.deserializeItemStack("INK_SACK:" + pl.getInkSack() + " : 1 : name>" + pl.getName() + " : lore>&fEstado: &7" + StringUtils.stripColors(pl.getName()) + "\n \n&eClique para modificar!"));
    }

    for (int i = 0; i < 9; i++) {
      this.setItem(36 + i, BukkitUtils.deserializeItemStack("STAINED_GLASS_PANE:15 : 1 : name>&8↑ &7Configurações : lore>&8↓ &7Categorias"));
    }

    this.setItem(this.section.getSlot() - 9, BukkitUtils.deserializeItemStack("STAINED_GLASS_PANE:5 : 1 : name>&2↑ &aConfigurações : lore>&2↓ &aCategorias"));

    this.setItem(PreferenceSection.CHAT.getSlot(), BukkitUtils.deserializeItemStack("PAPER : 1 : name>&aChat : lore>&eClique para ver as preferências do chat!"));
    this.setItem(PreferenceSection.GAME.getSlot(), BukkitUtils.deserializeItemStack("COMMAND : 1 : name>&aPartida : lore>&eClique para ver as preferências das partidas!"));
    this.setItem(PreferenceSection.LOBBY.getSlot(), BukkitUtils.deserializeItemStack("BEACON : 1 : name>&aLobby : lore>&eClique para ver as preferências do lobby!"));

    this.setItem(52, BukkitUtils.deserializeItemStack("INK_SACK:1 : 1 : name>&cVoltar"));

    this.update();
    this.register(Core.getInstance(), 10);
    this.open();
  }

  @Override
  public void update() {
    if (this.section == PreferenceSection.LOBBY) {
      PlayerVisibility pv = this.profile.getPreferencesContainer().getPlayerVisibility();
      this.setItem(10, BukkitUtils.deserializeItemStack("347 : 1 : name>&aJogadores : lore>&7Ative ou desative os\n&7jogadores no lobby."));
      this.setItem(19, BukkitUtils.deserializeItemStack("INK_SACK:" + pv.getInkSack() + " : 1 : name>" + pv.getName() + " : lore>&fEstado: &7" + StringUtils.stripColors(pv.getName()) + "\n \n&eClique para modificar!"));
    }
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
            if (evt.getSlot() == 10 || evt.getSlot() == 11 || evt.getSlot() == 12 || evt.getSlot() == 13 || evt.getSlot() == 14) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
            } else if (evt.getSlot() == 46) {
              if (this.section != PreferenceSection.CHAT) {
                EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
                new MenuPreferences(profile, PreferenceSection.CHAT);
              } else {
                EnumSound.ITEM_PICKUP.play(this.player, 0.5F, 2.0F);
              }
            } else if (evt.getSlot() == 47) {
              if (this.section != PreferenceSection.GAME) {
                EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
                new MenuPreferences(profile, PreferenceSection.GAME);
              } else {
                EnumSound.ITEM_PICKUP.play(this.player, 0.5F, 2.0F);
              }
            } else if (evt.getSlot() == 48) {
              if (this.section != PreferenceSection.LOBBY) {
                EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
                new MenuPreferences(profile, PreferenceSection.LOBBY);
              } else {
                EnumSound.ITEM_PICKUP.play(this.player, 0.5F, 2.0F);
              }
            } else if (evt.getSlot() == 52) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuProfile(profile);
            } else if ((evt.getSlot() == 19 || evt.getSlot() == 20 || evt.getSlot() == 21 || evt.getSlot() == 22 || evt.getSlot() == 23)) {
              if (Listeners.ANTI_SPAM.getOrDefault(player.getName(), 0L) > System.currentTimeMillis()) {
                return;
              }

              EnumSound.ITEM_PICKUP.play(this.player, 0.5F, 2.0F);
              Listeners.ANTI_SPAM.put(player.getName(), System.currentTimeMillis() + 900);

              PreferencesContainer pc = profile.getPreferencesContainer();
              if (this.section == PreferenceSection.CHAT && evt.getSlot() == 19) {
                pc.changeChatMessages();
              } else if (this.section == PreferenceSection.CHAT && evt.getSlot() == 20) {
                pc.changeChatMention();
              } else if (this.section == PreferenceSection.CHAT && evt.getSlot() == 21) {
                pc.changePrivateMessages();
                updateBungeePreferences("pm", pc.getPrivateMessages().ordinal());
              } else if (this.section == PreferenceSection.CHAT && evt.getSlot() == 22) {
                pc.changeFriendsRequests();
                updateBungeePreferences("fr", pc.getFriendsRequests().ordinal());
              } else if (this.section == PreferenceSection.CHAT && evt.getSlot() == 23) {
                pc.changePartyRequests();
                updateBungeePreferences("pr", pc.getPartyRequests().ordinal());
              } else if (this.section == PreferenceSection.GAME && evt.getSlot() == 19) {
                pc.changeBloodAndGore();
              } else if (this.section == PreferenceSection.LOBBY && evt.getSlot() == 19) {
                pc.changePlayerVisibility();
                if (!profile.playingGame()) {
                  profile.refreshPlayers();
                }
              } else if (this.section == PreferenceSection.LOBBY && evt.getSlot() == 20) {
                pc.changeProtectionLobby();
                updateBungeePreferences("pl", pc.getProtectionLobby().ordinal());
              }

              new MenuPreferences(profile, this.section);
            }
          }
        }
      }
    }
  }

  private void updateBungeePreferences(String id, int ordinal) {
    //noinspection UnstableApiUsage
    ByteArrayDataOutput out = ByteStreams.newDataOutput();
    out.writeUTF("UPDATE_PREFERENCE");
    out.writeUTF(id);
    out.writeInt(ordinal);
    this.player.sendPluginMessage(Core.getInstance(), "FancyCore", out.toByteArray());
  }

  public void cancel() {
    super.cancel();
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
