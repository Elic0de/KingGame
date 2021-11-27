package si.f5.hatosaba.kinggame.runnables.task;

import com.elic0de.aooni.Aooni;
import com.elic0de.aooni.locale.language.jp;
import com.elic0de.aooni.types.Game;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import si.f5.hatosaba.kinggame.KingGame;
import si.f5.hatosaba.kinggame.game.Game;

public class StartingTask implements Runnable {

    private int timer;
    private final int id;
    private final Game game;

    public StartingTask(Game game) {
        this.timer = 30;
        this.game = game;
        this.id = Bukkit.getScheduler().runTaskTimer(KingGame.getInstance(), this, 20L, 20L).getTaskId();
    }

    @Override
    public void run() {
        timer--;

        game.setTimer(timer);

        if (timer <= 0) {
            stop();
            game.startGame();
        } else if(timer % 5 == 0){
            game.getActivePlayers().forEach(player -> {
                game.playSound(player, Sound.BLOCK_LEVER_CLICK);
                game.sendTitle(player,"&e>>> &c" + timer + " &e<<<","");
            });
            game.broadcast("&6&lはじまるまで &c&l" + timer + "&6&l秒前");
        }else if(timer <= 5) {
            game.getActivePlayers().forEach(player -> {
                game.playSound(player, Sound.BLOCK_LEVER_CLICK);
                game.sendTitle(player,"&e>>> &c" + timer + " &e<<<","");
            });
            game.broadcast("&6&lはじまるまで &c&l" + timer + "&6&l秒前");
        }
    }

    public void stop() {
        game.stopTask(id);
        Bukkit.getScheduler().cancelTask(id);
    }

    public int getId() {
        return id;
    }
}
