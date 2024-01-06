package uwu.vutuvmonke.monketempwhitelist.listeners;

import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import uwu.vutuvmonke.monketempwhitelist.MonkeTempWhitelist;
import uwu.vutuvmonke.monketempwhitelist.Serializer;
import uwu.vutuvmonke.monketempwhitelist.Whitelist;

public class JoinListener implements Listener {

  private static final Component KICK_MESSAGE = Serializer.deserialize(MonkeTempWhitelist.get().getConfig().getString("messages.kick-message"));

  @EventHandler
  public void onLogin(final PlayerLoginEvent event) {
    if (!Whitelist.check(event.getPlayer().getName())) {
      event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, KICK_MESSAGE);
    }
  }
}
