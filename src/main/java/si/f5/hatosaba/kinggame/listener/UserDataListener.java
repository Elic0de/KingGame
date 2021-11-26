package si.f5.hatosaba.kinggame.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import si.f5.hatosaba.kinggame.manager.UserManager;

public class UserDataListener implements Listener {

    private UserManager userManager = UserManager.getInstnace();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        userManager.onJoin(player);
    }
}
