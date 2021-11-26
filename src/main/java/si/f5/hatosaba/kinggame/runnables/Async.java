package si.f5.hatosaba.kinggame.runnables;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import si.f5.hatosaba.kinggame.KingGame;

public interface Async extends Runnable {

    static Async define(Async async){
        return async;
    }

    default BukkitTask execute(){
        return Bukkit.getScheduler().runTaskAsynchronously(KingGame.getInstance(), this);
    }

    default BukkitTask executeLater(long delay){
        return Bukkit.getScheduler().runTaskLaterAsynchronously(KingGame.getInstance(), this, delay);
    }

    default BukkitTask executeTimer(long period, long delay) {
        return Bukkit.getScheduler().runTaskTimer(KingGame.getInstance(), this, period, delay);
    }

    default BukkitTask executeTimer(long interval){
        return executeTimer(interval, interval);
    }

}
