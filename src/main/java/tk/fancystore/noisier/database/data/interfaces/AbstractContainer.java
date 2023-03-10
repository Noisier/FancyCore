package tk.fancystore.noisier.database.data.interfaces;

import tk.fancystore.noisier.database.data.DataContainer;

public abstract class AbstractContainer {

  protected DataContainer dataContainer;

  public AbstractContainer(DataContainer dataContainer) {
    this.dataContainer = dataContainer;
  }

  public void gc() {
    this.dataContainer = null;
  }
}
