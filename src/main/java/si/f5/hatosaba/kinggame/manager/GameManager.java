package si.f5.hatosaba.kinggame.manager;

import org.bukkit.World;
import si.f5.hatosaba.kinggame.KingGame;
import si.f5.hatosaba.kinggame.config.Yaml;
import si.f5.hatosaba.kinggame.game.Game;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class GameManager {

    private static GameManager instance;

    public static void load(){
        instance = new GameManager();
    }

    public static GameManager getInstance(){
        return instance;
    }

    private final KingGame plugin = KingGame.getInstance();

    //マップデータを保存するフォルダー
    public final File folder = new File(plugin.getDataFolder() + File.separator + "maps");

    //ゲームのマップ
    private final Map<String, Game> games = new HashMap<>();

    private GameManager(){
        //フォルダーが存在しなければ作成する
        if(!folder.exists()) folder.mkdirs();

        //各コンフィグ毎に処理をする
        for(File file : Optional.ofNullable(folder.listFiles()).orElse(new File[0])){
            if(file.isFile()) {
                String fileName = file.getName();
                //拡張子を削除してゲーム名を取得する
                String gameName = fileName.substring(0, fileName.length() - 4);

/*
                File dataDirectory = new File(plugin.getDataFolder(), "maps");
                File source = new File(dataDirectory, gameName);
                File target = new File(plugin.getServer().getWorldContainer().getAbsolutePath(), gameName);
                //マップを読み込む
                Aooni.instance().getWM().unloadWorld(gameName, true);
                Aooni.instance().getWM().deleteWorld(target);
                Aooni.instance().getWM().copyWorld(source, target);
                Aooni.instance().getWM().loadWorld(gameName, World.Environment.NORMAL);
*/

                //ゲームを登録する
                registerGame(gameName);
            }
        }
    }

    public void saveAll(){
        games.values().forEach(Game::save);
    }

    public void registerGame(String gameName){
        File file = new File(folder, gameName + ".yml");

        //コンフィグが存在しなければ戻る
        if(!file.exists()) return;

        //コンフィグを取得する
        Yaml yaml = makeYaml(gameName);

        //コンフィグに基づきアスレを生成する
        Game game =  new Game(yaml);
        games.put(game.getName(), game);
    }

/*    public World createNewMap(String mapName, World.Environment environment) {
        World newWorld = KingGame.getInstance().getWM().createEmptyWorld(mapName, environment);
        if (newWorld == null) {
            return null;
        }
        return newWorld;
    }*/

    public Collection<Game> getGames(){
        return games.values();
    }

    /*public Stream<Game> getEnabledGames(){
        return games.values().stream()
                .filter(game -> game.isEnable());
    }*/

    public Game getGame(String gameName){
        return games.get(gameName);
    }

    public boolean containsGame(Game game){
        return containsGame(game.getName());
    }

    public boolean containsGame(String gameName){
        return games.containsKey(gameName);
    }

    public Yaml makeYaml(String gameName){
        return new Yaml(plugin, new File(folder, gameName + ".yml"), "game.yml");
    }
}
