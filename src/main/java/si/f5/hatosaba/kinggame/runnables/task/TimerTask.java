package si.f5.hatosaba.kinggame.runnables.task;

import com.elic0de.aooni.Aooni;
import com.elic0de.aooni.Constants;
import com.elic0de.aooni.types.Game;
import org.bukkit.Bukkit;
import si.f5.hatosaba.kinggame.KingGame;
import si.f5.hatosaba.kinggame.game.Game;

public class TimerTask implements Runnable {

    private int timeLimit;
    private final int id;
    private final Game game;

    public TimerTask(Game game) {
        this.timeLimit = game.getTimeLimit();
        this.game = game;
        this.id = Bukkit.getScheduler().runTaskTimer(KingGame.getInstance(), this, 20L, 20L).getTaskId();
    }

    @Override
    public void run() {
        timeLimit--;
        game.setTimeLimit(timeLimit);

        if (timeLimit == 0) {
            stop();
            game.endGame();
        }
    }

    public void stop() {
        Bukkit.getScheduler().cancelTask(id);
    }

}
