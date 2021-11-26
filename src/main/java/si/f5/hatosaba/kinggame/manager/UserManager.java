package si.f5.hatosaba.kinggame.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import si.f5.hatosaba.kinggame.KingGame;
import si.f5.hatosaba.kinggame.config.Yaml;
import si.f5.hatosaba.kinggame.data.UserData;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class UserManager {
    private static UserManager instance;

    public static void load(){
        instance = new UserManager();
    }

    public static UserManager getInstnace(){
        return instance;
    }

    private final KingGame plugin = KingGame.getInstance();

    //ユーザーデータを保存するフォルダー
    public final File folder = new File(plugin.getDataFolder() + File.separator + "Users");

    private final Map<UUID, UserData> users = new HashMap<>();

    private UserManager(){
        //フォルダーが存在しなければ作成する
        if(!folder.exists()) folder.mkdirs();

        for(File file : Optional.ofNullable(folder.listFiles()).orElse(new File[0])){
            //ファイルをコンフィグとして読み込む
            Yaml yaml = new Yaml(plugin, file, "user.yml");

            //コンフィグを基にユーザーを生成する
            UserData user = new UserData(yaml);

            //登録する
            users.put(user.uuid, user);
        }
    }

    public List<UserData> getOnlineUsers(){
        return Bukkit.getOnlinePlayers().stream().map(this::getUser).collect(Collectors.toList());
    }

    public UserData getUser(Player player){
        return users.get(player.getUniqueId());
    }

    public UserData getUser(UUID uuid){
        return users.get(uuid);
    }

    public boolean containsUser(Player player){
        return containsUser(player.getUniqueId());
    }

    public boolean containsUser(UUID uuid){
        return users.containsKey(uuid);
    }

    public Yaml makeYaml(UUID uuid){
        return new Yaml(plugin, new File(folder, uuid.toString() + ".yml"), "user.yml");
    }

    public void saveAll(){
        users.values().forEach(UserData::writeToFile);
    }

    public void onJoin(Player player){
        UUID uuid = player.getUniqueId();

        //既にユーザーデータが存在するのであれば戻る
        if(users.containsKey(uuid)) return;
        //ユーザーデータコンフィグ作成する
        Yaml yaml = makeYaml(uuid);

        //コンフィグを基にユーザーを生成する
        UserData user = new UserData(yaml);

        //登録する
        users.put(uuid, user);

    }

}
