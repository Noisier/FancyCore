package tk.fancystore.noisier.database.data.container;

import org.json.simple.JSONObject;
import tk.fancystore.noisier.bukkit.player.enums.*;
import tk.fancystore.noisier.database.data.DataContainer;
import tk.fancystore.noisier.database.data.interfaces.AbstractContainer;

@SuppressWarnings("unchecked")
public class PreferencesContainer extends AbstractContainer {

  public PreferencesContainer(DataContainer dataContainer) {
    super(dataContainer);
  }

  public void changePlayerVisibility() {
    JSONObject preferences = this.dataContainer.getAsJsonObject();
    preferences.put("pv", PlayerVisibility.getByOrdinal((long) preferences.get("pv")).next().ordinal());
    this.dataContainer.set(preferences.toString());
    preferences.clear();
  }

  public void changeChatMessages() {
    JSONObject preferences = this.dataContainer.getAsJsonObject();
    preferences.put("cm", PrivateMessages.getByOrdinal((long) preferences.get("cm")).next().ordinal());
    this.dataContainer.set(preferences.toString());
    preferences.clear();
  }

  public void changeChatMention() {
    JSONObject preferences = this.dataContainer.getAsJsonObject();
    preferences.put("mt", PrivateMessages.getByOrdinal((long) preferences.get("mt")).next().ordinal());
    this.dataContainer.set(preferences.toString());
    preferences.clear();
  }

  public void changePrivateMessages() {
    JSONObject preferences = this.dataContainer.getAsJsonObject();
    preferences.put("pm", PrivateMessages.getByOrdinal((long) preferences.get("pm")).next().ordinal());
    this.dataContainer.set(preferences.toString());
    preferences.clear();
  }

  public void changeBloodAndGore() {
    JSONObject preferences = this.dataContainer.getAsJsonObject();
    preferences.put("bg", BloodAndGore.getByOrdinal((long) preferences.get("bg")).next().ordinal());
    this.dataContainer.set(preferences.toString());
    preferences.clear();
  }

  public void changeProtectionLobby() {
    JSONObject preferences = this.dataContainer.getAsJsonObject();
    preferences.put("pl", ProtectionLobby.getByOrdinal((long) preferences.get("pl")).next().ordinal());
    this.dataContainer.set(preferences.toString());
    preferences.clear();
  }

  public void changeFriendsRequests() {
    JSONObject settings = this.dataContainer.getAsJsonObject();
    settings.put("fr", FriendsRequests.getByOrdinal((long) settings.get("fr")).next().ordinal());
    this.dataContainer.set(settings.toString());
    settings.clear();
  }

  public void changePartyRequests() {
    JSONObject settings = this.dataContainer.getAsJsonObject();
    settings.put("pr", FriendsRequests.getByOrdinal((long) settings.get("pr")).next().ordinal());
    this.dataContainer.set(settings.toString());
    settings.clear();
  }

  public PlayerVisibility getPlayerVisibility() {
    return PlayerVisibility.getByOrdinal((long) this.dataContainer.getAsJsonObject().get("pv"));
  }

  public ChatMessages getChatMessages() {
    return ChatMessages.getByOrdinal((long) this.dataContainer.getAsJsonObject().get("cm"));
  }

  public ChatMention getChatMention() {
    return ChatMention.getByOrdinal((long) this.dataContainer.getAsJsonObject().get("mt"));
  }

  public PrivateMessages getPrivateMessages() {
    return PrivateMessages.getByOrdinal((long) this.dataContainer.getAsJsonObject().get("pm"));
  }

  public BloodAndGore getBloodAndGore() {
    return BloodAndGore.getByOrdinal((long) this.dataContainer.getAsJsonObject().get("bg"));
  }

  public ProtectionLobby getProtectionLobby() {
    return ProtectionLobby.getByOrdinal((long) this.dataContainer.getAsJsonObject().get("pl"));
  }

  public FriendsRequests getFriendsRequests() {
    return FriendsRequests.getByOrdinal((long) this.dataContainer.getAsJsonObject().get("fr"));
  }

  public PartyRequests getPartyRequests() {
    return PartyRequests.getByOrdinal((long) this.dataContainer.getAsJsonObject().get("pr"));
  }
}
