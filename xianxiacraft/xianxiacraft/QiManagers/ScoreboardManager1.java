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

public class ScoreboardManager1 implements Listener {
    private static Map<UUID, Scoreboard> scoreboardMap = new HashMap<>();

    private final JavaPlugin plugin;

    private static Score scoreQi;
    private static Score scoreStage;

    public ScoreboardManager1(XianxiaCraft plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        createScoreboard(player);
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


        scoreQi = objective.getScore(ChatColor.GOLD + "" + "Qi: " + qi + "/" + maxQi);
        scoreQi.setScore(0);

        scoreStage = objective.getScore(ChatColor.GOLD + "" + "Stage: " + stage + " [" + percent + "%]");
        scoreStage.setScore(1);


        scoreboardMap.put(player.getUniqueId(), scoreboard);
        player.setScoreboard(scoreboard);

        /*
        Team stageTeam = scoreboard.registerNewTeam("stageTeam");
        stageTeam.addEntry(ChatColor.GOLD + "" + "Stage: ");
        //change percentage to actual percentage
        stageTeam.setSuffix("percentage");
        objective.getScore(ChatColor.GOLD + "" + "Stage: ").setScore(getStage(player));

        Team qiTeam = scoreboard.registerNewTeam("qiTeam");
        qiTeam.addEntry(ChatColor.GOLD + "" + "Qi: ");
        qiTeam.setSuffix(getQi(player) + "/" + getMaxQi(player));
        objective.getScore(ChatColor.GOLD + "" + "Qi: ").setScore(getQi(player));
        */



    }



    public static void updateScoreboard(Player player) {
        Scoreboard scoreboard = scoreboardMap.get(player.getUniqueId());
        if (scoreboard != null) {
            Objective objective = scoreboard.getObjective("customObjective");
            if (objective != null) {

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

            }
        }
    }


    /*
    public static void updateScoreboard(Player player) {
        Scoreboard scoreboard = scoreboardMap.get(player.getUniqueId());
        if (scoreboard != null) {
            Objective objective = scoreboard.getObjective("customObjective");
            if (objective != null) {
                Score scoreStage = objective.getScore(ChatColor.GOLD + "" + "Stage:");
                scoreStage.setScore(getStage(player));

                Score scoreQi = objective.getScore(ChatColor.GOLD + "" + "Qi:");
                scoreQi.setScore(getQi(player));


                // Add updated scores with suffixes
                Team stageTeam = scoreboard.registerNewTeam("stageTeam");
                stageTeam.addEntry(ChatColor.GOLD + "" + "Stage: ");
                //remember to chagne percentage
                stageTeam.setSuffix("percentage");
                objective.getScore(ChatColor.GOLD + "" + "Stage: ").setScore(getStage(player));

                Team qiTeam = scoreboard.registerNewTeam("qiTeam");
                qiTeam.addEntry(ChatColor.GOLD + "" + "Qi: ");
                qiTeam.setSuffix(getQi(player) + "/" + getMaxQi(player));
                objective.getScore(ChatColor.GOLD + "" + "Qi: ").setScore(getQi(player));
            }
        }
    }

     */



    public void removeScoreboard(Player player) {
        Scoreboard scoreboard = scoreboardMap.remove(player.getUniqueId());
        if (scoreboard != null) {
            player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        }
    }
}


