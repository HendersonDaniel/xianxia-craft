package xianxiacraft.xianxiacraft;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import xianxiacraft.xianxiacraft.QiManagers.PointManager;
import xianxiacraft.xianxiacraft.QiManagers.QiManager;
import xianxiacraft.xianxiacraft.QiManagers.ScoreboardManager1;
import xianxiacraft.xianxiacraft.QiManagers.ManualManager;
import xianxiacraft.xianxiacraft.handlers.Manuals.IronSkinManual;
import xianxiacraft.xianxiacraft.handlers.Manuals.Manual;
import xianxiacraft.xianxiacraft.handlers.PointHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class XianxiaCraft extends JavaPlugin {

    PointManager pointManager;
    ManualManager manualManager;
    QiManager qiManager;

    private int taskId;
    @Override
    public void onEnable() {
        // Plugin startup logic
        pointManager = new PointManager(this);
        pointManager.loadPointData();

        manualManager = new ManualManager(this);
        manualManager.loadManualData();

        qiManager = new QiManager(this);
        qiManager.loadQiData();

        new ScoreboardManager1(this);
        new PointHandler(this);


        //manuals in the game
        IronSkinManual ironSkinManual = new IronSkinManual(this);

        //list of the manuals
        List<Object> manualList = new ArrayList<>();
        manualList.add(ironSkinManual);


        taskId = new BukkitRunnable() {
            @Override
            public void run() {
                // Code to be executed every 3 seconds
                updateQiLevels(manualList);
            }
        }.runTaskTimer(this, 0L, 60L).getTaskId();


        /*
        depreciated code:
        //qi regen task
        int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new BukkitRunnable() {
            @Override
            public void run() {
                updateQiLevels(manualList);
            }
        }, 0L, 20L);
        */

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        pointManager.savePointData();
        manualManager.saveManualData();
        qiManager.saveQiData();



        Bukkit.getScheduler().cancelTasks(this);
    }

    private void updateQiLevels(List<Object> manualList) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            UUID playerId = player.getUniqueId();
            if(!(manualManager.getManual(player).equals("none"))){
                int currentQi = qiManager.getQi(player);
                //remember maxQi for scoreboard and pointmanager later
                int maxQi = (int) (10 * Math.pow(2,PointManager.getStage(player)));
                double qiRegenPercent;

                for(Object manual1 : manualList){
                    if(manual1 instanceof Manual){
                        Manual manual = (Manual) manual1;
                        if (!(manual.getManualName().equals("none"))){
                            if(manualManager.getManual(player).equals(manual.getManualName())){
                                qiRegenPercent = manual.getQiRegeneration();
                                if(currentQi < maxQi){
                                    if((currentQi + (int) (maxQi*manual.getQiRegeneration())) > maxQi){
                                        qiManager.setQi(player,maxQi);
                                    } else {
                                        qiManager.addQi(player,(int) Math.ceil(maxQi*manual.getQiRegeneration()));
                                    }
                                }
                            }
                        }
                    }
                }

                ScoreboardManager1.updateScoreboard(player);

            }


        }
    }



}
