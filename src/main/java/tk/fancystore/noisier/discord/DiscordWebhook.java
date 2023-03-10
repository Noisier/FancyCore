package tk.fancystore.noisier.discord;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;

public class DiscordWebhook {

  private final String url;

  public DiscordWebhook(String url) {
    this.url = url;
  }

  public void execute(String jsonString) throws IOException {
    URL url = new URL(this.url);
    HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
    connection.addRequestProperty("Content-Type", "application/json");
    connection.addRequestProperty("User-Agent", "Java-DiscordWebhook-BY-Gelox_");
    connection.setDoOutput(true);
    connection.setRequestMethod("POST");

    OutputStream stream = connection.getOutputStream();
    stream.write(jsonString.getBytes());
    stream.flush();
    stream.close();

    connection.getInputStream().close();
    connection.disconnect();
  }

  private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

  public static String getCurrentTimestamp() {
    return getTimestamp(System.currentTimeMillis());
  }

  public static String getTimestamp(long epoch) {
    return SDF.format(epoch);
  }
}
