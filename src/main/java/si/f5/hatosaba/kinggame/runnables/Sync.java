package si.f5.hatosaba.kinggame.runnables;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import si.f5.hatosaba.kinggame.KingGame;

public interface Sync extends Runnable {

    static Sync define(Sync sync){
        return sync;
    }

    default void execute(){
        Bukkit.getScheduler().runTask(KingGame.getInstance(), this);
    }

    default void executeLater(long delay){
        Bukkit.getScheduler().runTaskLater(KingGame.getInstance(), this, delay);
    }

    default BukkitTask executeTimer(long period, long delay){
        return Bukkit.getScheduler().runTaskTimer(KingGame.getInstance(), this, period, delay);
    }

    default BukkitTask executeTimer(long interval){
        return executeTimer(interval, interval);
    }

}
