package uwu.vutuvmonke.monketempwhitelist.commands.subcommands;

import java.text.MessageFormat;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.command.CommandSender;
import uwu.vutuvmonke.monketempwhitelist.MonkeTempWhitelist;
import uwu.vutuvmonke.monketempwhitelist.Serializer;
import uwu.vutuvmonke.monketempwhitelist.Whitelist;

public class ListCommand {

  private static final String FORMAT = MonkeTempWhitelist.get().getConfig().getString("messages.list");

  public static void invoke(CommandSender sender) {
    int size = Whitelist.list().size();
    sender.sendMessage(Serializer.deserialize(MessageFormat.format(FORMAT, size, StringUtils.join(Whitelist.list().keySet(), ", "))));
  }
}
