package uwu.vutuvmonke.monketempwhitelist.commands.subcommands;

import java.text.MessageFormat;
import org.bukkit.command.CommandSender;
import uwu.vutuvmonke.monketempwhitelist.MonkeTempWhitelist;
import uwu.vutuvmonke.monketempwhitelist.Serializer;
import uwu.vutuvmonke.monketempwhitelist.Whitelist;

public class AddCommand {

  private static final String WHITELISTED = MonkeTempWhitelist.get().getConfig().getString("messages.whitelisted");

  public static void invoke(CommandSender sender, String[] args) {
    String nickname = args[1];
    Whitelist.add(nickname, -1);
    sender.sendMessage(Serializer.deserialize(MessageFormat.format(WHITELISTED, nickname)));
  }
}
