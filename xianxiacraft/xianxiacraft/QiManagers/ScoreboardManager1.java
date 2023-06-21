package xianxiacraft.xianxiacraft.QiManagers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;
import xianxiacraft.xianxiacraft.XianxiaCraft;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static xianxiacraft.xianxiacraft.QiManagers.PointManager.*;
import static xianxiacraft.xianxiacraft.QiManagers.QiManager.getQi;
import static xianxiacraft.xianxiacraft.QiManagers.TechniqueManager.*;
import static xianxiacraft.xianxiacraft.QiManagers.TechniqueManager.setAuraBool;

public class ScoreboardManager1 implements Listener {
    private static Map<UUID, Scoreboard> scoreboardMap = new HashMap<>();
    private static Map<UUID, Score> scoreQiMap = new HashMap<>();
    private static Map<UUID,Score> scoreStageMap = new HashMap<>();

    private final JavaPlugin plugin;

   // private static Score scoreQi;
   // private static Score scoreStage;

    public ScoreboardManager1(XianxiaCraft plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        createScoreboard(player);
        player.setGlowing(false);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        removeScoreboard(player);
    }

    public void createScoreboard(Player player) {
        Scoreboard scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("customObjective", "dummy", ChatColor.RED + "" + ChatColor.BOLD + "Dantian");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);


        int qi = getQi(player);
        int maxQi = getMaxQi(player);
        int stage = getStage(player);
        double percent = percentToNextStage(player);


        Score scoreQi = objective.getScore(ChatColor.GOLD + "" + "Qi: " + qi + "/" + maxQi);
        scoreQi.setScore(0);

        Score scoreStage = objective.getScore(ChatColor.GOLD + "" + "Stage: " + stage + " [" + percent + "%]");
        scoreStage.setScore(1);


        scoreboardMap.put(player.getUniqueId(), scoreboard);
        scoreQiMap.put(player.getUniqueId(),scoreQi);
        scoreStageMap.put(player.getUniqueId(),scoreStage);
        player.setScoreboard(scoreboard);




    }



    public static void updateScoreboard(Player player) {
        Scoreboard scoreboard = scoreboardMap.get(player.getUniqueId());
        if (scoreboard != null) {
            Objective objective = scoreboard.getObjective("customObjective");
            if (objective != null) {

                Score scoreQi = scoreQiMap.get(player.getUniqueId());
                Score scoreStage = scoreStageMap.get(player.getUniqueId());

                int qi = getQi(player);
                int maxQi = getMaxQi(player);
                int stage = getStage(player);
                double percent = percentToNextStage(player);


                scoreboard.resetScores(scoreQi.getEntry());
                scoreboard.resetScores((scoreStage.getEntry()));

                scoreQi = objective.getScore(ChatColor.GOLD + "" + "Qi: " + qi + "/" + maxQi);
                scoreQi.setScore(0);

                scoreStage = objective.getScore(ChatColor.GOLD + "" + "Stage: " + stage + " [" + percent + "%]");
                scoreStage.setScore(1);

                scoreboardMap.put(player.getUniqueId(),scoreboard);
                scoreQiMap.put(player.getUniqueId(),scoreQi);
                scoreStageMap.put(player.getUniqueId(),scoreStage);

            }
        }
    }




    public void removeScoreboard(Player player) {
        Scoreboard scoreboard = scoreboardMap.remove(player.getUniqueId());
        if (scoreboard != null) {
            player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        }
    }
}


