package tk.fancystore.noisier.database.data.container;

import org.json.simple.JSONObject;
import tk.fancystore.noisier.bukkit.player.enums.*;
import tk.fancystore.noisier.database.data.DataContainer;
import tk.fancystore.noisier.database.data.interfaces.AbstractContainer;

@SuppressWarnings("unchecked")
public class SocialContainer extends AbstractContainer {

  public SocialContainer(DataContainer dataContainer) {
    super(dataContainer);
  }

  public void set(String id, String social) {
    JSONObject preferences = this.dataContainer.getAsJsonObject();
    preferences.put(id, social);
    this.dataContainer.set(preferences.toString());
    preferences.clear();
  }

  public String getOrDefault(String id, String def) {
    JSONObject sociais = this.dataContainer.getAsJsonObject();
    String social = sociais.getOrDefault(id, def).toString();
    sociais.clear();
    return social;
  }
}
