package uwu.vutuvmonke.monketempwhitelist;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.HashMap;

public class Whitelist {

  private static final HashMap<String, Long> WHITELIST = new HashMap<>();
  private static final Gson GSON = new Gson();
  private static final Type TYPE_TOKEN = new TypeToken<HashMap<String, Long>>() {
  }.getType();
  private static File CONFIG_FILE;

  public static void init(File configFile) {
    CONFIG_FILE = configFile;
    load();
  }

  public static void load() {
    WHITELIST.clear();
    try {
      if (CONFIG_FILE.exists()) {
        WHITELIST.putAll(GSON.fromJson(new FileReader(CONFIG_FILE), TYPE_TOKEN));
      } else {
        GSON.toJson(WHITELIST, TYPE_TOKEN, new FileWriter(CONFIG_FILE));
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static void save() {
    try {
      GSON.toJson(WHITELIST, TYPE_TOKEN, new FileWriter(CONFIG_FILE));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static boolean check(String nickname) {
    if (!WHITELIST.containsKey(nickname)) {
      return false;
    }
    long expires = WHITELIST.get(nickname);
    return expires == -1 || Instant.now().getEpochSecond() < expires;
  }

  public static void add(String nickname, long time) {
    WHITELIST.put(nickname, time);
    save();
  }

  public static boolean remove(String nickname) {
    return WHITELIST.remove(nickname) != null;
  }

  public static HashMap<String, Long> list() {
    return WHITELIST;
  }
}
