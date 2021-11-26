package si.f5.hatosaba.kinggame;

import com.github.elic0de.spigotcommandlib.registry.CommandLib;
import com.github.elic0de.spigotcommandlib.CommandHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import si.f5.hatosaba.kinggame.commands.KingCommand;
import si.f5.hatosaba.kinggame.listener.GameListener;
import si.f5.hatosaba.kinggame.listener.UserDataListener;
import si.f5.hatosaba.kinggame.util.KGConstants;

public final class KingGame extends JavaPlugin {

    private static KingGame instance;

    private CommandLib lib;

    @Override
    public void onEnable() {
        // Plugin startup logic
        KingGame.instance = this;
        this.saveDefaultConfig();

        // レシピを登録
        KGConstants.initRecipes();

        // イベントを登録
        registerEventListeners(
                new UserDataListener(),
                new GameListener()
        );

        // コマンドを登録
        registerCommandHandler(
                new KingCommand()
        );


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        /*Bukkit.getOnlinePlayers().forEach(player -> {
            PlayerPreferences playerData = PlayerPreferences.get(player);
            if (playerData.isDirty())
                playerData.writeToFile(playerDataDirectory);
        });

        PlayerPreferences.clearCache();*/
    }

    private void registerEventListeners(Listener... listeners) {
        for(Listener listener : listeners){
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    private void registerCommandHandler(CommandHandler... handlers) {
        this.lib = new CommandLib(this);
        for (CommandHandler handler : handlers)
            this.lib.registerCommandHandler(handler);
    }

    public static KingGame getInstance() {
        return instance;
    }
}
