package tk.fancystore.noisier.bukkit.listeners;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tk.fancystore.noisier.Manager;
import tk.fancystore.noisier.bukkit.fake.FakeManager;
import tk.fancystore.noisier.bukkit.nms.NMS;
import tk.fancystore.noisier.bukkit.party.BukkitParty;
import tk.fancystore.noisier.bukkit.party.BukkitPartyManager;
import tk.fancystore.noisier.bukkit.player.Profile;
import tk.fancystore.noisier.cash.CashException;
import tk.fancystore.noisier.cash.CashManager;
import tk.fancystore.noisier.party.PartyPlayer;
import tk.fancystore.noisier.party.PartyRole;
import tk.fancystore.noisier.utils.StringUtils;
import tk.fancystore.noisier.utils.enums.EnumSound;

import java.util.Locale;

public class PluginMessageListener implements org.bukkit.plugin.messaging.PluginMessageListener {

  @Override
  public void onPluginMessageReceived(String channel, Player receiver, byte[] data) {
    if (channel.equals("FancyCore")) {
      //noinspection UnstableApiUsage
      ByteArrayDataInput in = ByteStreams.newDataInput(data);

      String subChannel = in.readUTF();
      switch (subChannel) {
        case "CASH_MANAGER": {
          String name = in.readUTF();
          String method = in.readUTF();
          long amount = in.readLong();

          try {
            switch (method) {
              case "add":
                CashManager.addCash(name, amount);
                break;
              case "remove":
                CashManager.removeCash(name, amount);
                break;
              case "set":
                CashManager.setCash(name, amount);
                break;
              case "display":
                Player player = Bukkit.getPlayerExact(name);
                if (player != null) {
                  player.sendMessage("§eCash: §b" + StringUtils.formatNumber(Profile.getProfile(player.getName()).getStats("FancyCoreProfile", "cash")));
                }
                break;
            }
          } catch (CashException ignored) {}
          break;
        }
        case "FAKE": {
          Player player = Bukkit.getPlayerExact(in.readUTF());
          if (player != null) {
            String fakeName = in.readUTF();
            String roleName = in.readUTF();
            String skin = in.readUTF();
            FakeManager.applyFake(player, fakeName, roleName, skin);
            NMS.refreshPlayer(player);
          }
          break;
        }
        case "FAKE_BOOK": {
          Player player = Bukkit.getPlayerExact(in.readUTF());
          if (player != null) {
            try {
              String sound = in.readUTF();
              EnumSound.valueOf(sound).play(player, 1.0F, sound.contains("VILL") ? 1.0F : 2.0F);
            } catch (Exception ignore) {
            }
            FakeManager.sendRole(player);
          }
          break;
        }
        case "FAKE_BOOK2": {
          Player player = Bukkit.getPlayerExact(in.readUTF());
          if (player != null) {
            String roleName = in.readUTF();
            String sound = in.readUTF();
            EnumSound.valueOf(sound).play(player, 1.0F, sound.contains("VILL") ? 1.0F : 2.0F);
            FakeManager.sendSkin(player, roleName);
          }
          break;
        }
        case "SEND_PARTY": {
          Player player = Bukkit.getPlayerExact(in.readUTF());
          Player leader = Bukkit.getPlayerExact(in.readUTF());
          if (player != null && leader != null) {
            Profile profile = Profile.getProfile(player.getName());
            Profile pLeader = Profile.getProfile(leader.getName());
            if (pLeader.getGame() == null) {
              return;
            }
            if (profile.getGame() != null) {
              profile.getGame().leave(profile, null);
            }
            pLeader.getGame().join(profile);
          }
        }
        case "PARTY":
          try {
            JSONObject changes = (JSONObject) new JSONParser().parse(in.readUTF());
            String leader = changes.get("leader").toString();
            boolean delete = changes.containsKey("delete");
            BukkitParty party = BukkitPartyManager.getLeaderParty(leader);
            if (party == null) {
              if (delete) {
                return;
              }
              party = BukkitPartyManager.createParty(leader, 0);
            }

            if (delete) {
              party.delete();
              return;
            }

            if (changes.containsKey("newLeader")) {
              party.transfer(changes.get("newLeader").toString());
            }

            if (changes.containsKey("remove")) {
              party.listMembers().removeIf(pp -> pp.getName().equalsIgnoreCase(changes.get("remove").toString()));
            }

            for (Object object : (JSONArray) changes.get("members")) {
              if (!party.isMember(object.toString())) {
                party.listMembers().add(new PartyPlayer(object.toString(), PartyRole.MEMBER));
              }
            }
          } catch (ParseException ignore) {
          }
          break;
      }
    }
  }
}
