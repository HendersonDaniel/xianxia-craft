package xianxiacraft.xianxiacraft;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import xianxiacraft.xianxiacraft.customItems.ToolItems;
import xianxiacraft.xianxiacraft.handlers.*;
import xianxiacraft.xianxiacraft.QiManagers.PointManager;
import xianxiacraft.xianxiacraft.QiManagers.QiManager;
import xianxiacraft.xianxiacraft.QiManagers.ScoreboardManager1;
import xianxiacraft.xianxiacraft.QiManagers.ManualManager;
import xianxiacraft.xianxiacraft.commands.CultPassiveCommandExecutor;
import xianxiacraft.xianxiacraft.commands.OperatorCommands;
import xianxiacraft.xianxiacraft.handlers.Manuals.*;
import xianxiacraft.xianxiacraft.util.ManualItems;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static xianxiacraft.xianxiacraft.QiManagers.ManualManager.getManual;
import static xianxiacraft.xianxiacraft.QiManagers.ManualManager.getManualQiRegen;
import static xianxiacraft.xianxiacraft.QiManagers.PointManager.getMaxQi;
import static xianxiacraft.xianxiacraft.QiManagers.QiManager.getQi;
import static xianxiacraft.xianxiacraft.QiManagers.QiManager.subtractQi;
import static xianxiacraft.xianxiacraft.QiManagers.TechniqueManager.*;
import static xianxiacraft.xianxiacraft.handlers.Manuals.FattyManual.fattyManualQiMove;
import static xianxiacraft.xianxiacraft.handlers.Manuals.FungalManual.fungalManualQiMove;
import static xianxiacraft.xianxiacraft.handlers.Manuals.SugarFiendManual.sugarFiendQiMove;
import static xianxiacraft.xianxiacraft.util.CountNearbyBlocks.countNearbyBlocks;
import static xianxiacraft.xianxiacraft.util.ParticleEffects.qiAuraParticleEffect;

public final class XianxiaCraft extends JavaPlugin {

    PointManager pointManager;
    ManualManager manualManager;
    QiManager qiManager;
    private static BukkitAudiences adventure;

    @Override
    public void onEnable() {
        // Plugin startup logic
        pointManager = new PointManager(this);
        pointManager.loadPointData();

        manualManager = new ManualManager(this);
        manualManager.loadManualData();

        qiManager = new QiManager(this);
        qiManager.loadQiData();

        adventure = BukkitAudiences.create(this);

        new ScoreboardManager1(this);
        new PointHandler(this);
        new HitEvents(this);
        new ItemDropEvents(this);
        new MoveEvents(this);
        new CustomItemEvents(this);



        //command logic
        CultPassiveCommandExecutor cultPassiveCommandExecutor = new CultPassiveCommandExecutor();
        Objects.requireNonNull(getCommand("qipunch")).setExecutor(cultPassiveCommandExecutor);
        Objects.requireNonNull(getCommand("cultutorial")).setExecutor(cultPassiveCommandExecutor);
        Objects.requireNonNull(getCommand("qimine")).setExecutor(cultPassiveCommandExecutor);
        Objects.requireNonNull(getCommand("qimove")).setExecutor(cultPassiveCommandExecutor);
        Objects.requireNonNull(getCommand("detonate")).setExecutor(cultPassiveCommandExecutor);
        Objects.requireNonNull(getCommand("qiaura")).setExecutor(cultPassiveCommandExecutor);
        Objects.requireNonNull(getCommand("qifly")).setExecutor(cultPassiveCommandExecutor);
        Objects.requireNonNull(getCommand("manaccept")).setExecutor(cultPassiveCommandExecutor);

        OperatorCommands operatorCommands = new OperatorCommands();
        Objects.requireNonNull(getCommand("addstage")).setExecutor(operatorCommands);
        Objects.requireNonNull(getCommand("checkstats")).setExecutor(operatorCommands);
        Objects.requireNonNull(getCommand("obtain")).setExecutor(operatorCommands);


        //custom tools using PersistentDataContainer
        new ToolItems(this);
        //item adding logic
        ManualItems.init();
        ToolItems.init2();



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


        // Code to be executed every 3 seconds
        int taskId = new BukkitRunnable() {
            @Override
            public void run() {
                // Code to be executed every 3 seconds
                updateQiLevels(manualList);
            }
        }.runTaskTimer(this, 0L, 60L).getTaskId();

        // every 1/2 second
        int taskId2 = new BukkitRunnable() {
            @Override
            public void run() {
                auraParticles();
            }
        }.runTaskTimer(this, 0L, 10L).getTaskId();





    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        pointManager.savePointData();
        manualManager.saveManualData();
        qiManager.saveQiData();

        if (adventure != null) {
            adventure.close();
            adventure = null;
        }

        Bukkit.getScheduler().cancelTasks(this);
    }

    private void updateQiLevels(List<Object> manualList) {
        for (Player player : Bukkit.getOnlinePlayers()) {

            String playerManual = getManual(player);

            if (!(playerManual.equals("none"))) {
                int currentQi = getQi(player);
                int maxQi = (int) (10 * Math.pow(2, PointManager.getStage(player)));
                double qiRegenPercent;

                //qi regen
                for (Object manual1 : manualList) {
                    if (manual1 instanceof Manual) {
                        Manual manual = (Manual) manual1;
                        if (!(manual.getManualName().equals("none"))) {
                            if (playerManual.equals(manual.getManualName())) {
                                qiRegenPercent = manual.getQiRegeneration();
                                if (currentQi < maxQi) {
                                    if ((currentQi + (int) (maxQi * qiRegenPercent)) > maxQi) {
                                        QiManager.setQi(player, maxQi);
                                    } else {
                                        QiManager.addQi(player, (int) Math.ceil(maxQi * manual.getQiRegeneration()));
                                    }
                                }
                            }
                        }
                    }
                }

                //passive depletion for QiMine
                if(getMineBool(player)){
                    if (getQi(player) >= 3) {
                        QiManager.subtractQi(player, 3);
                    } else {
                        setMineBool(player, false);
                        player.removePotionEffect(PotionEffectType.FAST_DIGGING);
                        player.sendMessage(ChatColor.GOLD + "You did not have enough qi to augment your mining.\nQiMine: Inactive");
                    }
                }

                //passive depletion from taiji painting
                if(getHiddenByTaijiPaintingBool(player)){
                    if (getQi(player) >= (int) Math.ceil(getMaxQi(player)*(getManualQiRegen(playerManual)+0.01))+1) {
                        QiManager.subtractQi(player, (int) Math.ceil(getMaxQi(player)*(getManualQiRegen(playerManual)+0.01))+1);
                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                            if(player1 != player){
                                player1.hidePlayer(this,player);
                            }
                        }
                    } else {
                        setHiddenByTaijiPaintingBool(player, false);
                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                            if(player1 != player){
                                player1.showPlayer(this,player);
                            }
                        }
                        player.sendMessage(ChatColor.GOLD + "You ran out of qi to supply the Taiji Painting. You are no longer hidden.");
                    }
                }

                //passive qi depletion for QiMove
                if (getMoveBool(player)) {
                    switch (playerManual) {
                        case "Sugar Fiend":
                            if (getQi(player) >= 12) {
                                QiManager.subtractQi(player, 12);
                            } else {
                                setMoveBool(player, false);
                                sugarFiendQiMove(player,false);
                                player.sendMessage(ChatColor.GOLD + "You did not have enough qi to augment your movements.\nQiMove: Inactive");
                            }
                            break;
                        case "Fatty Manual":
                            if (getQi(player) >= 12) {
                                QiManager.subtractQi(player, 12);
                            } else {
                                setMoveBool(player, false);
                                fattyManualQiMove(player,false);
                                player.sendMessage(ChatColor.GOLD + "You did not have enough qi to augment your movements.\nQiMove: Inactive");
                            }
                            break;
                        case "Fungal Manual":
                            if (getQi(player) >= 16) {
                                QiManager.subtractQi(player, 16);
                                if(countNearbyBlocks(player, Material.MYCELIUM,2) > 0){
                                    fungalManualQiMove(player,true); // true because it is in an if statement already checking for movebool
                                }
                            } else {
                                setMoveBool(player, false);
                                player.sendMessage(ChatColor.GOLD + "You did not have enough qi to conceal your movements.\nQiMove: Inactive");
                            }
                            break;
                        case "Ice Manual":
                            if (getQi(player) >= 20) {
                                QiManager.subtractQi(player, 20);
                            } else {
                                setMoveBool(player, false);
                                player.sendMessage(ChatColor.GOLD + "You did not have enough qi to augment your movements.\nQiMove: Inactive");
                            }
                            break;
                    }

                }

                //passive depletion for qiaura
                if(getAuraBool(player)){
                    if(getQi(player) >= 100){
                        subtractQi(player,100);

                        //particles every 3 sec based on manual
                        qiAuraParticleEffect(player);
                    } else {
                        setAuraBool(player, false);
                        qiAuraGlow(player,getAuraBool(player));
                        player.sendMessage(ChatColor.GOLD + "You did not have enough qi to sustain your aura.");
                    }
                }

                //passive depletion for qifly
                if(getFlyBool(player) && player.isFlying()){
                    if(getQi(player) >= 100){
                        subtractQi(player,100);


                    } else {
                        setFlyBool(player, false);
                        player.setAllowFlight(false);
                        player.setFlySpeed(0.1f);
                        player.sendMessage(ChatColor.GOLD + "You did not have enough qi to sustain your flight.");
                    }
                }

                ScoreboardManager1.updateScoreboard(player);
            }
        }

    }

    private void auraParticles(){
        for(Player player : Bukkit.getOnlinePlayers()){
            if(getAuraBool(player)){
                qiAuraParticleEffect(player);
            }
        }
    }



    public static BukkitAudiences getAdventure() {
        return adventure;
    }

}
