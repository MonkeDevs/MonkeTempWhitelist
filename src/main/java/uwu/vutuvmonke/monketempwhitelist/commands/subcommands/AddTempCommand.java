package uwu.vutuvmonke.monketempwhitelist.commands.subcommands;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import uwu.vutuvmonke.monketempwhitelist.MonkeTempWhitelist;
import uwu.vutuvmonke.monketempwhitelist.Serializer;
import uwu.vutuvmonke.monketempwhitelist.Whitelist;

public class AddTempCommand {

  private static final Component WRONG_TIME_FORMAT;
  private static final String WHITELISTED;

  static {
    var config = MonkeTempWhitelist.get().getConfig().getConfigurationSection("messages");
    WRONG_TIME_FORMAT = Serializer.deserialize(config.getString("wrong-time-format"));
    WHITELISTED = config.getString("temp-whitelisted");
  }

  public static void invoke(CommandSender sender, String[] args) {
    String nickname = args[1];
    long parsedTime = parseTime(args[2]);

    if (parsedTime == -1) {
      sender.sendMessage(WRONG_TIME_FORMAT);
      return;
    }

    long time = Instant.now().getEpochSecond() + parsedTime;
    Whitelist.add(nickname, time);
    sender.sendMessage(Serializer.deserialize(MessageFormat.format(WHITELISTED, nickname, args[2])));
  }

  private static long parseTime(String string) {

    List<String> list = new ArrayList<>();

    String c;
    int goBack = 0;
    for (int i = 0; i < string.length(); i++) {
      c = String.valueOf(string.charAt(i));
      if (c.matches("[a-z]")) {
        list.add(string.substring(goBack, i + 1));
        goBack = i + 1;

      }
    }
    long amount;
    long total = 0;
    char ch;
    for (String st : list) {
      ch = st.charAt(st.length() - 1);
      if (st.length() != 1 && String.valueOf(ch).matches("[d,h,m,s]")) {
        amount = Math.abs(Integer.parseInt(st.substring(0, st.length() - 1)));
        switch (ch) {
          case 's' -> total += amount;
          case 'm' -> total += amount * 60;
          case 'h' -> total += amount * 3600;
          case 'd' -> total += amount * 3600 * 24;
        }
      }
    }

    if (total == 0) {
      return -1;
    }

    return total;

  }
}
