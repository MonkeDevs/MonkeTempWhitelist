package uwu.vutuvmonke.monketempwhitelist;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import java.text.MessageFormat;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.plugin.java.JavaPlugin;
import uwu.vutuvmonke.monketempwhitelist.commands.TempWhitelistCommand;
import uwu.vutuvmonke.monketempwhitelist.listeners.JoinListener;

public final class MonkeTempWhitelist extends JavaPlugin {

  private static MonkeTempWhitelist INSTANCE;
  private static Component KICK_MESSAGE;
  private ScheduledTask expireTask;

  @Override
  public void onEnable() {
    INSTANCE = this;
    reload();
  }

  @Override
  public void onDisable() {
    Whitelist.save();
  }

  public void reload() {
    saveDefaultConfig();
    reloadConfig();

    KICK_MESSAGE = Serializer.deserialize(this.getConfig().getString("messages.removed-kick"));

    HandlerList.unregisterAll(this);
    this.getServer().getPluginManager().registerEvents(new JoinListener(), this);

    this.getCommand("tempwhitelist").setExecutor(new TempWhitelistCommand(this));

    if (this.expireTask != null) {
      this.expireTask.cancel();
    }

    Whitelist.init(this.getDataFolder().toPath().resolve("whitelist.json").toFile());

    this.expireTask = this.getServer().getAsyncScheduler().runAtFixedRate(this, task -> {
      int removed = 0;
      for (Map.Entry<String, Long> entry : Whitelist.list().entrySet()) {
        if (entry.getValue() != -1 && Instant.now().getEpochSecond() > entry.getValue()) {
          var player = Bukkit.getPlayer(entry.getKey());
          if (player != null && player.isOnline()) {
            player.kick(KICK_MESSAGE, PlayerKickEvent.Cause.WHITELIST);
          }
          removed++;
          Whitelist.list().remove(entry.getKey());
          this.getLogger().info(MessageFormat.format("Player {0} was expired and removed from whitelist.", entry.getKey()));
        }
      }
      if (removed > 0) {
        this.getLogger().info(MessageFormat.format("Removed {0} expired players from whitelist.", removed));
        Whitelist.save();
      }
    }, 0, 2, TimeUnit.SECONDS);
  }

  public static MonkeTempWhitelist get() {
    return INSTANCE;
  }
}
