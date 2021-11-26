package si.f5.hatosaba.kinggame.scoreboard;

import com.elic0de.aooni.Aooni;
import com.elic0de.aooni.locale.language.jp;
import com.elic0de.aooni.scoreboard.common.EntryBuilder;
import com.elic0de.aooni.scoreboard.type.Entry;
import com.elic0de.aooni.scoreboard.type.ScoreboardHandler;
import com.elic0de.aooni.types.Game;
import com.elic0de.aooni.user.User;
import com.elic0de.aooni.util.format.TimeFormat;
import org.bukkit.entity.Player;
import si.f5.hatosaba.kinggame.scoreboard.common.EntryBuilder;
import si.f5.hatosaba.kinggame.util.TimeFormat;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

//テスト
//テスト

public class SimpleBoard {

    private static final Function<User, List<Entry>> LOBBY;
    private static final BiFunction<User, Game, List<Entry>> LINES;
    private static final BiFunction<User, Game, List<Entry>> WAITING_LINES;
    private static final BiFunction<User, Game, List<Entry>> WAITING_START_LINES;
    private static final BiFunction<User, Game, List<Entry>> FINISHING_GAME;

    private static String ADDRESS = "hatosaba.f5.si";

    static{
        LOBBY = (user) -> new EntryBuilder()
                .next("&c/king join")
                .next("&7を実行してゲームに")
                .next("&7参加しよう！")
                .blank()
                .next("&e" + ADDRESS)
                .build();

        LINES = (user, game) -> {
            EntryBuilder entryBuilder = new EntryBuilder()
                    .blank()
                    .next("時間:&a" + TimeFormat.secondsToTimeString(game.getTimeLimit()));

            if(!game.getWinners().isEmpty())
                entryBuilder.next(jp.ESCAPEE + ":&e" + game.getWinners().size());

            entryBuilder.next(jp.SURVIVOR + ":&a" + (game.getLives().size() - game.getAooniPlayers().size() - game.getWinners().size()));

            if(!game.getAooniPlayers().contains(user.asBukkitPlayer()) && !game.getSpectators().contains(user.asBukkitPlayer()))
                entryBuilder.blank().next(jp.IS + ":&c" + game.getLives().get(user.asBukkitPlayer()));

            entryBuilder.blank().next("&e" + jp.IP);

            return entryBuilder.build();
        };

        WAITING_LINES = (user, game) -> {
            EntryBuilder entryBuilder = new EntryBuilder()
                    .next("ゲームをはじめるには")
                    .next("あと&a" + (game.getMinPlayers() - game.getPlayers().size()) + "&r人のプレイヤー")
                    .next("が必要です")
                    .blank()
                    .next("離脱するには")
                    .next("&c/king leave")
                    .blank()
                    .next("&e" + ADDRESS);

            return entryBuilder.build();
        };

        WAITING_START_LINES = (user, game) -> new EntryBuilder()
                .blank()
                .next("はじまるまで")
                .next("残り&a" + game.getTimer() + " &r秒")
                .blank()
                .next("&e" + ADDRESS)
                .build();

        FINISHING_GAME = (user, game) -> new EntryBuilder()
                .blank()
                .next("&c終了")
                .blank()
                .next("&e" + ADDRESS)
                .build();
    }

    private final User user;
    private final Game game;

    public SimpleBoard(User user){
        this.user = user;
        this.game = user.getGamePlayingNow();
    }

    public void loadScoreboard() {
        user.scoreboard = Aooni.instance().createScoreboard(user.asBukkitPlayer())
                .setHandler(new ScoreboardHandler() {

                    @Override
                    public String getTitle(Player player) {
                        return jp.NAME_JP;
                    }

                    @Override
                    public List<Entry> getEntries(Player player) {
                        return getLine();
                    }

                })
                .setUpdateInterval(2l);
        user.scoreboard.activate();

    }

    private List<Entry> getLine() {
        if(user.getGamePlayingNow() != null) {
            Game game = user.getGamePlayingNow();
            if (game.getMatchState() == Game.GameMode.WAITING) {
                return WAITING_LINES.apply(user, game);
            } else if (game.getMatchState() == Game.GameMode.STARTING) {
                return WAITING_START_LINES.apply(user, game);
            } else if (game.getMatchState() == Game.GameMode.INGAME) {
                return LINES.apply(user, game);
            } else if (game.getMatchState() == Game.GameMode.FINISHING) {
                return FINISHING_GAME.apply(user, game);
            } else {
                return LOBBY.apply(user);
            }
        }else {
            return LOBBY.apply(user);
        }
    }

}
