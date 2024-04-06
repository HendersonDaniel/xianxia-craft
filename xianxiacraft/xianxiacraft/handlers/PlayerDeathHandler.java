package xianxiacraft.xianxiacraft.handlers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import xianxiacraft.xianxiacraft.XianxiaCraft;

import static xianxiacraft.xianxiacraft.QiManagers.ManualManager.getManual;
import static xianxiacraft.xianxiacraft.QiManagers.PointManager.*;
import static xianxiacraft.xianxiacraft.QiManagers.PointManager.addPoints;
import static xianxiacraft.xianxiacraft.QiManagers.ScoreboardManager1.updateScoreboard;
import static xianxiacraft.xianxiacraft.util.ManualUtils.getCultivationModifier;

public class PlayerDeathHandler implements Listener {

    XianxiaCraft plugin;

    public PlayerDeathHandler(XianxiaCraft plugin){
        Bukkit.getPluginManager().registerEvents(this,plugin);
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){


        if(event.getEntity().getKiller() != null){

            final Player defendingPlayer = event.getEntity();
            final Player attackingPlayer = event.getEntity().getKiller();


            assert attackingPlayer != null;
            if(getManual(attackingPlayer).equals("Demonic Manual")){

                int cultivationModifier = getCultivationModifier(attackingPlayer);

                int points1 = getPoints(defendingPlayer);
                int stage1 = getStage(defendingPlayer);

                //var1 is the points needed to get from stage 0 to current stage-1
                int variable1 = (int) (20 * Math.pow(2, (stage1))) - 20;
                int leeched = (points1 - variable1-1);

                setPoints(defendingPlayer,points1-leeched);
                addPoints(attackingPlayer,leeched + (leeched * cultivationModifier));
                updateScoreboard(defendingPlayer);
                updateScoreboard(attackingPlayer);

            }
        }
    }
}
