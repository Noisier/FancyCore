package tk.fancystore.noisier.database.data.container;

import org.json.simple.JSONArray;
import tk.fancystore.noisier.bukkit.titles.Title;
import tk.fancystore.noisier.database.data.DataContainer;
import tk.fancystore.noisier.database.data.interfaces.AbstractContainer;

@SuppressWarnings("unchecked")
public class TitlesContainer extends AbstractContainer {

  public TitlesContainer(DataContainer dataContainer) {
    super(dataContainer);
  }

  public void add(Title title) {
    JSONArray titles = this.dataContainer.getAsJsonArray();
    titles.add(title.getId());
    this.dataContainer.set(titles.toString());
    titles.clear();
  }

  public boolean has(Title title) {
    return this.dataContainer.getAsJsonArray().contains(title.getId());
  }
}
