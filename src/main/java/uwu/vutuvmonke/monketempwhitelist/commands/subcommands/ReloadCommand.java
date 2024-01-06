package uwu.vutuvmonke.monketempwhitelist.commands.subcommands;

import org.bukkit.command.CommandSender;
import uwu.vutuvmonke.monketempwhitelist.MonkeTempWhitelist;
import uwu.vutuvmonke.monketempwhitelist.Serializer;

public class ReloadCommand {

  public static void invoke(CommandSender sender) {
    MonkeTempWhitelist.get().reload();
    sender.sendMessage(Serializer.deserialize(MonkeTempWhitelist.get().getConfig().getString("reload")));
  }
}
