package uwu.vutuvmonke.monketempwhitelist.commands.subcommands;

import java.text.MessageFormat;
import org.bukkit.command.CommandSender;
import uwu.vutuvmonke.monketempwhitelist.MonkeTempWhitelist;
import uwu.vutuvmonke.monketempwhitelist.Serializer;
import uwu.vutuvmonke.monketempwhitelist.Whitelist;

public class RemoveCommand {
  private static final String REMOVED;
  private static final String NOT_WHITELISTED;

  static {
    var config = MonkeTempWhitelist.get().getConfig().getConfigurationSection("messages");
    REMOVED = config.getString("removed");
    NOT_WHITELISTED = config.getString("not-whitelisted");
  }
  public static void invoke(CommandSender sender, String[] args) {
    String nickname = args[1];
    if (Whitelist.remove(nickname)) {
      sender.sendMessage(Serializer.deserialize(MessageFormat.format(REMOVED, nickname)));
    } else {
      sender.sendMessage(Serializer.deserialize(MessageFormat.format(NOT_WHITELISTED, nickname)));
    }
  }
}
