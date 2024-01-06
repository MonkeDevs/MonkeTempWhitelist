package uwu.vutuvmonke.monketempwhitelist;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class Serializer {

  private static final MiniMessage SERIALIZER = MiniMessage.miniMessage();

  public static Component deserialize(String message) {
    return SERIALIZER.deserialize(message);
  }
}
