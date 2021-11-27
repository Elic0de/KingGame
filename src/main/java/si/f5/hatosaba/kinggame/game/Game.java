package si.f5.hatosaba.kinggame.game;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import si.f5.hatosaba.kinggame.KingGame;
import si.f5.hatosaba.kinggame.config.Yaml;
import si.f5.hatosaba.kinggame.data.UserData;
import si.f5.hatosaba.kinggame.manager.GameManager;
import si.f5.hatosaba.kinggame.manager.UserManager;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
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
    private final UserManager userManager = UserManager.getInstnace();

    private List<String> roles = new ArrayList<>(
            Arrays.asList(
                    "村",
                    "村狼",
                    "村村狼",
                    "村狼占狼",
                    "村村狼占狼",
                    "村村村狼占狼",
                    "村村村村狼占狼",
                    "村村村村狼狼占村",
                    "村村村村村狼狼占村",
                    "村村村村狼狼占狼村村",
                    "村村村村村狼狼占狼村村",
                    "村村村村村村狼狼占狼村村",
                    "村村村村村村村狼狼占狼村村",
                    "村村村村村村村村狼狼占狼村村",
                    "村村村村村村村村村狼狼占狼村村",
                    "村村村村村村村狼狼狼占狼村村村村"
            )
    );

    private GameMode mode = GameMode.DISABLED;
    private String name;
    private List<Player> activePlayers = new ArrayList<>();
    private List<Player> inactivePlayers = new ArrayList<>();
    private List<Integer> tasks = new ArrayList<>();

    public Game(Yaml yaml) {
        this.yaml = yaml;
    }

    public void setup() {
        mode = GameMode.LOADING;

        name = yaml.getString("name");

        mode = GameMode.WAITING;
    }

    public void addPlayer(Player player) {
        if (mode == GameMode.WAITING || mode == GameMode.STARTING) {
            activePlayers.add(player);
            //テレポート
            //player.teleport();
            userManager.getUser(player).join(this);
            //はじめられるかチェック
            checkStart();
        }
    }

    public void removePlayer(Player player) {
        activePlayers.remove(player);
        inactivePlayers.remove(player);
        userManager.getUser(player).leave();
        clearInv(player);
    }

    public void checkStart() {
    }

    public void startGame() {
        if (mode == GameMode.INGAME) return;
        mode = GameMode.INGAME;
        chooseRole();
        broadcast("役職が配布されました。配布された自分の役職を確認し、準備を完了させてください。");
        broadcast("ゲームが開始されました。それぞれの役職にあった行動をとってください。");
    }

    public void endGame() {
        mode = GameMode.FINISHING;
        showResult();
        resetGame();
    }

    public void killPlayer(Player player) {
        broadcast( player.getName() + "はによって殺されました");
        activePlayers.remove(player);
        inactivePlayers.add(player);
        checkWin();
    }

    public void checkWin() {

    }

    public void showResult() {

    }

    public void broadcast(String msg) {
        activePlayers.forEach(player -> player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg)));
    }

    public void chooseRole() {
        int playerCount = activePlayers.size();
        String role = roles.get(playerCount);
        List<Character> role_list = new ArrayList<>();
        for(int i = 0; i < role.length(); i++) {
            role_list.add(role.charAt(i));
        }
        Collections.shuffle(role_list);
        for(int i = 0; i < role.length(); i++) {
            UserData userData = userManager.getUser(activePlayers.get(i));
            Character gameRole = role_list.get(i);
            userData.asBukkitPlayer().sendMessage("あなたの役職は" + gameRole + "です");
            userData.setRole(gameRole);
        }
    }

    public void resetGame() {
        mode = GameMode.RESETTING;

        activePlayers.forEach(player -> removePlayer(player));
        inactivePlayers.forEach(player -> removePlayer(player));
        activePlayers.clear();
        inactivePlayers.clear();
        cancelTasks();
        setup();

        mode = GameMode.WAITING;
    }

    public void sendTitle(Player player, String title, String subtitle) {
        player.sendTitle(ChatColor.translateAlternateColorCodes('&', title), ChatColor.translateAlternateColorCodes('&', subtitle), 15, 60, 15);
    }

    public void stopTask(int id) {
        tasks.remove(id);
    }

    public void cancelTasks() {
        tasks.forEach(integer -> KingGame.getInstance().getServer().getScheduler().cancelTask(integer));
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

    public void save() {
        Yaml yaml = GameManager.getInstance().makeYaml(this.name);
        yaml.save();
    }
}
