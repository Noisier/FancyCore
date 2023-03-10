package tk.fancystore.noisier.bukkit.party;

import tk.fancystore.noisier.party.Party;

public class BukkitParty extends Party {

  public BukkitParty(String leader, int slots) {
    super(leader, slots);
  }

  @Override
  public void delete() {
    BukkitPartyManager.listParties().remove(this);
    this.destroy();
  }
}
