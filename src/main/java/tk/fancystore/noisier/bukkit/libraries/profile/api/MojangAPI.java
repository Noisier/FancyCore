package tk.fancystore.noisier.bukkit.libraries.profile.api;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import tk.fancystore.noisier.bukkit.libraries.profile.Mojang;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class MojangAPI extends Mojang {

  private boolean response;

  @Override
  public String fetchProfileID(String name) {
    this.response = false;
    try {
      URLConnection conn = new URL("https://api.mojang.com/users/profiles/minecraft/" + name).openConnection();
      conn.setConnectTimeout(5000);
      final BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      this.response = true;
      StringBuilder builder = new StringBuilder();
      String read;
      while ((read = reader.readLine()) != null) {
        builder.append(read);
      }
      return builder.length() == 0 ? null : new JsonParser().parse(builder.toString()).getAsJsonObject().get("id").getAsString();
    } catch (Exception e) {
      return null;
    }
  }

  @Override
  public String[] fechProperties(String id) {
    this.response = false;
    try {
      URLConnection conn = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + id + "?unsigned=false").openConnection();
      conn.setConnectTimeout(5000);
      final BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      this.response = true;
      StringBuilder builder = new StringBuilder();
      String read;
      while ((read = reader.readLine()) != null) {
        builder.append(read);
      }

      if (builder.length() != 0) {
        JsonObject json = new JsonParser().parse(builder.toString()).getAsJsonObject();

        String[] result = new String[3];
        JsonObject properties = json.get("properties").getAsJsonArray().get(0).getAsJsonObject();
        result[0] = json.get("name").getAsString();
        result[1] = properties.get("value").getAsString();
        result[2] = properties.get("signature").getAsString();
        return result;
      }
    } catch (Exception ignored) {
    }

    return null;
  }

  @Override
  public boolean getResponse() {
    return response;
  }
}
