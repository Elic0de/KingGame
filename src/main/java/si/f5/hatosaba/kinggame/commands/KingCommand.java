package si.f5.hatosaba.kinggame.commands;

import com.github.elic0de.spigotcommandlib.CommandHandle;
import com.github.elic0de.spigotcommandlib.CommandHandler;
import org.bukkit.command.CommandSender;

public class KingCommand implements CommandHandler {

    @CommandHandle(
            command = "king",
            permission = "perm.king",
            description = "king"
    )
    public void king(CommandSender sender) {
        sender.sendMessage("");
    }

}
