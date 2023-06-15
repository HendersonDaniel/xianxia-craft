package xianxiacraft.xianxiacraft;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import xianxiacraft.xianxiacraft.QiManagers.PointManager;
import xianxiacraft.xianxiacraft.QiManagers.QiManager;
import xianxiacraft.xianxiacraft.QiManagers.ScoreboardManager1;
import xianxiacraft.xianxiacraft.QiManagers.ManualManager;
import xianxiacraft.xianxiacraft.commands.CultPassiveCommandExecutor;
import xianxiacraft.xianxiacraft.handlers.HitEvents;
import xianxiacraft.xianxiacraft.handlers.ItemDropEvents;
import xianxiacraft.xianxiacraft.handlers.Manuals.*;
import xianxiacraft.xianxiacraft.handlers.PointHandler;
import xianxiacraft.xianxiacraft.util.ManualItems;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
        new HitEvents(this);
        new ItemDropEvents(this);

        //command logic
        CultPassiveCommandExecutor cultPassiveCommandExecutor = new CultPassiveCommandExecutor();
        getCommand("qipunch").setExecutor(cultPassiveCommandExecutor);
        Objects.requireNonNull(getCommand("tutorial")).setExecutor(cultPassiveCommandExecutor);


        //item adding logic
        ManualItems.init();


        //initialize every manual in the game here.
        IronSkinManual ironSkinManual = new IronSkinManual();
        FattyManual fattyManual = new FattyManual();
        IceManual iceManual = new IceManual();
        PhoenixManual phoenixManual = new PhoenixManual();
        SpaceManual spaceManual = new SpaceManual();
        SugarFiendManual sugarFiendManual = new SugarFiendManual();
        VampireManual vampireManual = new VampireManual();
        PoisonManual poisonManual = new PoisonManual();
        FungalManual fungalManual = new FungalManual();
        LightningManual lightningManual = new LightningManual();

        //list of the manuals (MAKE SURE TO UPDATE THIS AND THE ONE IN MANUALMANAGER AS MORE ARE ADDED)
        List<Object> manualList = new ArrayList<>();
        manualList.add(ironSkinManual);
        manualList.add(fattyManual);
        manualList.add(iceManual);
        manualList.add(phoenixManual);
        manualList.add(spaceManual);
        manualList.add(sugarFiendManual);
        manualList.add(vampireManual);
        manualList.add(poisonManual);
        manualList.add(fungalManual);
        manualList.add(lightningManual);



        taskId = new BukkitRunnable() {
            @Override
            public void run() {
                // Code to be executed every 3 seconds
                updateQiLevels(manualList);
            }
        }.runTaskTimer(this, 0L, 60L).getTaskId();





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
            if(!(ManualManager.getManual(player).equals("none"))){
                int currentQi = QiManager.getQi(player);
                int maxQi = (int) (10 * Math.pow(2,PointManager.getStage(player)));
                double qiRegenPercent;

                for(Object manual1 : manualList){
                    if(manual1 instanceof Manual){
                        Manual manual = (Manual) manual1;
                        if (!(manual.getManualName().equals("none"))){
                            if(ManualManager.getManual(player).equals(manual.getManualName())){
                                qiRegenPercent = manual.getQiRegeneration();
                                if(currentQi < maxQi){
                                    if((currentQi + (int) (maxQi*qiRegenPercent)) > maxQi){
                                        QiManager.setQi(player,maxQi);
                                    } else {
                                        QiManager.addQi(player,(int) Math.ceil(maxQi*manual.getQiRegeneration()));
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
