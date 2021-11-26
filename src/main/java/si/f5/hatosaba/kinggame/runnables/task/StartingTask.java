package si.f5.hatosaba.kinggame.runnables.task;

import com.elic0de.aooni.Aooni;
import com.elic0de.aooni.locale.language.jp;
import com.elic0de.aooni.types.Game;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import si.f5.hatosaba.kinggame.game.Game;

public class StartingTask implements Runnable {

    private int timer;
    private final int id;
    private final Game game;

    public StartingTask(Game game) {
        this.timer = 30;
        this.game = game;

        g.allBroadcast("&6&l Aooni &c&l" + g.getName() + " &6&lは30秒後に始まります!");
        g.allBroadcast("&6&l コマンド:&c&l " + jp.GAME_JOIN_COMMAND + " " + g.getName() + "&6&lで参加!");

        this.id = Bukkit.getScheduler().runTaskTimer(Aooni.instance(), this, 20L, 20L).getTaskId();
    }

    @Override
    public void run() {
        timer--;

        game.setTimer(timer);

        if (timer <= 0) {
            stop();
            game.startGame();
        } else if(timer % 5 == 0){
            game.getPlayers().forEach(player -> {
                game.playSound(player, Sound.BLOCK_LEVER_CLICK);
                game.sendTitle(player,"&e>>> &c" + timer + " &e<<<","");
            });
            game.broadcast("&6&lはじまるまで &c&l" + timer + "&6&l秒前");
        }else if(timer <= 5) {
            game.getPlayers().forEach(player -> {
                game.playSound(player, Sound.BLOCK_LEVER_CLICK);
                game.sendTitle(player,"&e>>> &c" + timer + " &e<<<","");
            });
            game.broadcast("&6&lはじまるまで &c&l" + timer + "&6&l秒前");
        }
    }

    public void stop() {
        Bukkit.getScheduler().cancelTask(id);
    }

}
