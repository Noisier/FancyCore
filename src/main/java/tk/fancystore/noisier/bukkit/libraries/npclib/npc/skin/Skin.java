package tk.fancystore.noisier.bukkit.libraries.npclib.npc.skin;

import tk.fancystore.noisier.bukkit.libraries.profile.InvalidMojangException;
import tk.fancystore.noisier.bukkit.libraries.profile.Mojang;
import tk.fancystore.noisier.bukkit.nms.NMS;

public class Skin {

  private String value, signature;

  private Skin(String value, String signature) {
    this.value = value;
    this.signature = signature;
  }

  public static Skin fromName(String name) {
    return new Skin(null, null).fetch(name);
  }

  public static Skin fromData(String value, String signature) {
    return new Skin(value, signature);
  }

  private Skin fetch(String name) {
    try {
      String profileID = Mojang.getProfileID(name);
      if (profileID != null) {
        // valid premium username
        String[] property = Mojang.getProperties(profileID);
        if (property != null) {
          // valid skin property
          this.value = property[1];
          this.signature = property[2];
        }
      }
    } catch (InvalidMojangException e) {
      System.out.println("Cannot fetch skin from name " + name + ": " + e.getMessage());
    }

    return this;
  }

  public void apply(SkinnableEntity entity) {
    NMS.setValueAndSignature(entity.getEntity(), value, signature);
  }
}
