package tk.fancystore.noisier.database.data.container;

import org.json.simple.JSONObject;
import tk.fancystore.noisier.bukkit.titles.Title;
import tk.fancystore.noisier.database.data.DataContainer;
import tk.fancystore.noisier.database.data.interfaces.AbstractContainer;

@SuppressWarnings("unchecked")
public class SelectedContainer extends AbstractContainer {

  public SelectedContainer(DataContainer dataContainer) {
    super(dataContainer);
  }

  public void setIcon(String id) {
    JSONObject selected = this.dataContainer.getAsJsonObject();
    selected.put("icon", id);
    this.dataContainer.set(selected.toString());
    selected.clear();
  }

  public Title getTitle() {
    return Title.getById(this.dataContainer.getAsJsonObject().get("title").toString());
  }

  public void setTitle(String id) {
    JSONObject selected = this.dataContainer.getAsJsonObject();
    selected.put("title", id);
    this.dataContainer.set(selected.toString());
    selected.clear();
  }
}
