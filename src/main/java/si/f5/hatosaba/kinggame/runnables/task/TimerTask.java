package si.f5.hatosaba.kinggame.runnables.task;

import com.elic0de.aooni.Aooni;
import com.elic0de.aooni.Constants;
import com.elic0de.aooni.types.Game;
import org.bukkit.Bukkit;

public class TimerTask implements Runnable {

    private int timeLimit;
    private final int id;
    private final Game game;

    public TimerTask(Game g) {
        this.timeLimit = g.getTimeLimit();
        this.game = g;
        this.id = Bukkit.getScheduler().runTaskTimer(Aooni.instance(), this, 20L, 20L).getTaskId();
    }

    @Override
    public void run() {
        timeLimit--;

        if(timeLimit <= 895 && timeLimit >= 891) {
            game.broadcast("&6&l青鬼が出現するまで &c&l" + ((timeLimit + 10 ) - 900) + "&6&l秒前");
        }

        if(timeLimit == 890)
            game.getAooniPlayers().forEach(player ->  Constants.tryGivingTeleportItemTo(player));

        game.setTimeLimit(timeLimit);

        //10秒後
        if(timeLimit == 890)
            game.getAooniPlayers().stream().forEach(aooni -> game.teleportToArea(aooni));

        if(timeLimit == 880) {
            game.getAooniPlayers().forEach(player1 -> game.cooldownPlayers.remove(player1));
        }

        if (timeLimit == 0) {
            stop();
            game.endGame();
        }

        game.getPlayers().forEach(player -> {
            /*if (timeLimit == 600) {
                // = true;
                game.broadcast("&c地下室が開放可能です");
                game.sendTitle(player, "&c地下室が開放可能です", "");
            }
            if (timeLimit == 300) {
                //canOpenFiveFloorDoor = true;
                game.broadcast("&c地下室が開放可能です");
                game.sendTitle(player, "&c地下室が開放可能です", "");
            }*/


        });
    }

    public void stop() {
        Bukkit.getScheduler().cancelTask(id);
    }

}
