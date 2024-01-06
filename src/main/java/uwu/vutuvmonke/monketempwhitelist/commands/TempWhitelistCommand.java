package uwu.vutuvmonke.monketempwhitelist.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import uwu.vutuvmonke.monketempwhitelist.MonkeTempWhitelist;
import uwu.vutuvmonke.monketempwhitelist.Serializer;
import uwu.vutuvmonke.monketempwhitelist.commands.subcommands.AddCommand;
import uwu.vutuvmonke.monketempwhitelist.commands.subcommands.AddTempCommand;
import uwu.vutuvmonke.monketempwhitelist.commands.subcommands.ListCommand;
import uwu.vutuvmonke.monketempwhitelist.commands.subcommands.ReloadCommand;
import uwu.vutuvmonke.monketempwhitelist.commands.subcommands.RemoveCommand;

public class TempWhitelistCommand implements CommandExecutor {

  private static Component USAGE;
  private static Component NO_PERMISSION;

  public TempWhitelistCommand(MonkeTempWhitelist plugin) {
    var config = plugin.getConfig().getConfigurationSection("messages");
    USAGE = Serializer.deserialize(config.getString("usage"));
    NO_PERMISSION = Serializer.deserialize(config.getString("no-permission"));
  }

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
    if (!sender.hasPermission("monketempwhitelist.use") || !sender.hasPermission("monketempwhitelist.reload")) {
      sender.sendMessage(NO_PERMISSION);
      return true;
    }
    if (args.length == 0 || args.length > 3) {
      sender.sendMessage(USAGE);
      return true;
    }
    switch (args[0]) {
      case "addtemp" -> {
        if (args.length != 3) {
          sender.sendMessage(USAGE);
          break;
        }
        AddTempCommand.invoke(sender, args);
      }
      case "add" -> {
        if (args.length != 2) {
          sender.sendMessage(USAGE);
          break;
        }
        AddCommand.invoke(sender, args);
      }
      case "remove" -> {
        if (args.length != 2) {
          sender.sendMessage(USAGE);
          break;
        }
        RemoveCommand.invoke(sender, args);
      }
      case "list" -> {
        if (args.length != 1) {
          sender.sendMessage(USAGE);
          break;
        }
        ListCommand.invoke(sender);
      }
      case "reload" -> {
        if (args.length != 1) {
          sender.sendMessage(USAGE);
          break;
        }
        ReloadCommand.invoke(sender);
      }
      default -> sender.sendMessage(USAGE);
    }
    return true;
  }
}
