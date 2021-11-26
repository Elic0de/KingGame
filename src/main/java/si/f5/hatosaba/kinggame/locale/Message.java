package si.f5.hatosaba.kinggame.locale;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.entity.Player;
import si.f5.hatosaba.kinggame.KingGame;

import static net.md_5.bungee.api.ChatColor.*;

public interface Message {

    char OPEN_BRACKET = '(';
    char CLOSE_BRACKET = ')';
    char FULL_STOP = '.';

    BaseComponent[] PREFIX_COMPONENT = new ComponentBuilder("[")
            .color(GRAY)
            .append("KG")
            .color(AQUA)
            .bold(true)
            .append("]")
            .bold(false)
            .color(GRAY)
            .create();

    Args0 LOGIN_MESSAGE = () -> prefixed(
            new ComponentBuilder("提案はこのメッセージをクリック")
                    .color(GREEN)
                    .event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://forms.gle/WihVDsuYszhKTtyZ7"))
                    .create()
    );

    Args0 NO_READY = () -> prefixed(
            new ComponentBuilder("準備中です")
                    .color(GREEN)
                    .create()
    );

    static BaseComponent[] prefixed(BaseComponent[] component) {
        return new ComponentBuilder("")
                .append(PREFIX_COMPONENT)
                .append("")
                .append(component)
                .create();
    }

    static void sendMessage(CommandSender sender, BaseComponent[] message) {
        // we can safely send async for players and the console - otherwise, send it sync
        if (sender instanceof Player || sender instanceof ConsoleCommandSender || sender instanceof RemoteConsoleCommandSender) {
            sender.spigot().sendMessage(message);
        }
    }

    interface Args0 {
        BaseComponent[] build();

        default void send(CommandSender sender) {
            sendMessage(sender, build());
        }
    }

    interface Args1<A0> {
        BaseComponent[] build(A0 arg0);

        default void send(CommandSender sender, A0 arg0) {
            sendMessage(sender, build(arg0));
        }
    }

    interface Args2<A0, A1> {
        BaseComponent[] build(A0 arg0, A1 arg1);

        default void send(CommandSender sender, A0 arg0, A1 arg1) {
            sendMessage(sender, build(arg0, arg1));
        }
    }

    interface Args3<A0, A1, A2> {
        BaseComponent[] build(A0 arg0, A1 arg1, A2 arg2);

        default void send(CommandSender sender, A0 arg0, A1 arg1, A2 arg2) {
            sendMessage(sender, build(arg0, arg1, arg2));
        }
    }

    interface Args4<A0, A1, A2, A3> {
        BaseComponent[] build(A0 arg0, A1 arg1, A2 arg2, A3 arg3);

        default void send(CommandSender sender, A0 arg0, A1 arg1, A2 arg2, A3 arg3) {
            sendMessage(sender, build(arg0, arg1, arg2, arg3));
        }
    }

    interface Args5<A0, A1, A2, A3, A4> {
        BaseComponent[] build(A0 arg0, A1 arg1, A2 arg2, A3 arg3, A4 arg4);

        default void send(CommandSender sender, A0 arg0, A1 arg1, A2 arg2, A3 arg3, A4 arg4) {
            sendMessage(sender, build(arg0, arg1, arg2, arg3, arg4));
        }
    }

    interface Args6<A0, A1, A2, A3, A4, A5> {
        BaseComponent[] build(A0 arg0, A1 arg1, A2 arg2, A3 arg3, A4 arg4, A5 arg5);

        default void send(CommandSender sender, A0 arg0, A1 arg1, A2 arg2, A3 arg3, A4 arg4, A5 arg5) {
            sendMessage(sender, build(arg0, arg1, arg2, arg3, arg4, arg5));
        }
    }

}

