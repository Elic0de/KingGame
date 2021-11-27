package si.f5.hatosaba.kinggame.data;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import si.f5.hatosaba.kinggame.config.Yaml;
import si.f5.hatosaba.kinggame.game.Game;
import si.f5.hatosaba.kinggame.manager.UserManager;
import si.f5.hatosaba.kinggame.util.KGConstants;

import java.util.UUID;

public class UserData {

    //UUID
    public final UUID uuid;

    private Game gamePlayingNow;
    private Character role;

    private int win;
    private int lose;

    public UserData(Yaml yaml){
        //ファイル名に基づきUUIDを生成し代入する
        this.uuid = UUID.fromString(yaml.name);
        this.win = yaml.getInt("stats.win");
        this.lose = yaml.getInt("stats.lose");
    }

    public void join(Game game) {
        this.gamePlayingNow = game;
    }

    public void leave() {
        if(isPlayingGame()) {
            this.gamePlayingNow = null;
        }else {
            asBukkitPlayer().sendMessage("ゲームに参加していません");
        }
    }

    public void setRole(Character role) {
        this.role = role;
        switch (role) {
            case '村':
                asBukkitPlayer().getInventory().addItem(KGConstants.CHAT_HELMET);
                break;
            case '狼':
                break;
            case '占':
                break;
        }

    }

    //このユーザーに対応したプレイヤーを取得する
    public Player asBukkitPlayer(){
        return Bukkit.getPlayer(uuid);
    }

    //ごっこをプレイ中かどうか
    public boolean isPlayingGame(){
        return gamePlayingNow != null;
    }

    public void writeToFile() {
        Yaml yaml = UserManager.getInstnace().makeYaml(uuid);

        yaml.set("stats.win", win);
        yaml.set("stats.lose", lose);
        //セーブする
        yaml.save();
    }
}
