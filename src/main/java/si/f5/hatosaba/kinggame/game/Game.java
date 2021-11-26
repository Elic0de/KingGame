package si.f5.hatosaba.kinggame.game;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import si.f5.hatosaba.kinggame.config.Yaml;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public enum GameMode {
        DISABLED,
        LOADING,
        INACTIVE,
        WAITING,
        STARTING,
        INGAME,
        FINISHING,
        RESETTING,
        ERROR
    }

    private final Yaml yaml;

    private GameMode mode = GameMode.DISABLED;
    private List<Player> activePlayers = new ArrayList<>();
    private List<Player> inactivePlayers = new ArrayList<>();
    private List<Integer> tasks = new ArrayList<>();

    public Game(Yaml yaml) {
        this.yaml = yaml;
        setup(yaml);
    }

    public void setup(Yaml yaml) {
        mode = GameMode.LOADING;



        mode = GameMode.WAITING;
    }

    public void addPlayer(Player player) {
        if (mode == GameMode.WAITING || mode == GameMode.STARTING) {
            activePlayers.add(player);

            //player.teleport();
            //はじめられるかチェック
            checkStart();
        }
    }

    public void removePlayer(Player player) {
        activePlayers.remove(player);
        inactivePlayers.remove(player);
        clearInv(player);
    }

    public void checkStart() {
    }

    public void startGame() {
        if (mode == GameMode.INGAME) return;
        mode = GameMode.INGAME;

    }

    public void endGame() {
        mode = GameMode.FINISHING;
        showResult();
        resetGame();
    }

    public void killPlayer(Player player) {
        activePlayers.remove(player);
        inactivePlayers.add(player);
        broadcast("はによって殺されました");
    }

    public void showResult() {

    }

    public void broadcast(String msg) {
        activePlayers.forEach(player -> player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg)));
    }

    public void resetGame() {
        mode = GameMode.RESETTING;

        activePlayers.forEach(player -> removePlayer(player));
        inactivePlayers.forEach(player -> removePlayer(player));
        cancelTasks();

        mode = GameMode.WAITING;
    }

    public void cancelTasks() {
        if (timerTask != null) {
            timerTask.stop();
            timerTask = null;
        }
        if (startingTask != null) {
            startingTask.stop();
            startingTask = null;
        }
    }

    public void clearInv(Player p) {
        ItemStack[] inv = p.getInventory().getContents();
        for (int i = 0; i < inv.length; i++) {
            inv[i] = null;
        }
        p.getInventory().setContents(inv);
        inv = p.getInventory().getArmorContents();
        for (int i = 0; i < inv.length; i++) {
            inv[i] = null;
        }
        p.getInventory().setArmorContents(inv);
        p.updateInventory();
    }
}
