package tk.fancystore.noisier.bukkit.libraries.profile;

import com.google.common.base.Charsets;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import tk.fancystore.noisier.bukkit.libraries.profile.api.MineToolsAPI;
import tk.fancystore.noisier.bukkit.libraries.profile.api.MojangAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@SuppressWarnings({"UnstableApiUsage", "StaticInitializerReferencesSubClass"})
public abstract class Mojang {

  private static final List<Mojang> APIS;

  private static final Cache<String, String> CACHED_IDS;
  private static final Cache<String, String[]> CACHED_PROPERTY;

  static {
    APIS = new ArrayList<>();
    APIS.add(new MojangAPI());
    APIS.add(new MineToolsAPI());

    CACHED_IDS = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.HOURS).build();
    CACHED_PROPERTY = CacheBuilder.newBuilder().expireAfterWrite(30, TimeUnit.MINUTES).build();
  }

  public static String getProfileID(String name) throws InvalidMojangException {
    String id = CACHED_IDS.getIfPresent(name);
    if (id != null) {
      return id;
    }

    for (Mojang api : APIS) {
      id = api.fetchProfileID(name);
      if (api.getResponse()) {
        if (id != null) {
          CACHED_IDS.put(name, id);
        }

        return id;
      }
    }

    throw new InvalidMojangException("Nenhuma api conseguiu pegar o ID pelo nome: " + name);
  }

  public static String[] getProperties(String id) throws InvalidMojangException {
    String[] property = CACHED_PROPERTY.getIfPresent(id);
    if (property != null) {
      return property;
    }

    for (Mojang api : APIS) {
      property = api.fechProperties(id);
      if (api.getResponse()) {
        if (property != null) {
          CACHED_PROPERTY.put(id, property);
        }

        return property;
      }
    }

    throw new InvalidMojangException("Nenhuma api conseguiu pegar a Property pelo id: " + id);
  }

  public static UUID getOfflineUUID(String name) {
    return UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes(Charsets.UTF_8));
  }

  public static String parseUUID(String withoutDashes) {
    return withoutDashes.substring(0, 8) + '-' + withoutDashes.substring(8, 12) + '-' + withoutDashes.substring(12, 16) + '-' + withoutDashes.substring(16, 20) + '-'
        + withoutDashes.substring(20, 32);
  }

  public abstract String fetchProfileID(String name);

  public abstract String[] fechProperties(String uuid);

  public abstract boolean getResponse();
}
