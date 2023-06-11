package xianxiacraft.xianxiacraft.handlers;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;
import xianxiacraft.xianxiacraft.QiManagers.PointManager;
import xianxiacraft.xianxiacraft.QiManagers.ScoreboardManager1;
import xianxiacraft.xianxiacraft.XianxiaCraft;


public class PointHandler implements Listener {

    public PointHandler(XianxiaCraft plugin){

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    //I need to write a list of every manual name and its description
    // I need to make these manuals drop from mobs and also trade for villagers

    //all the cultivation manuals will be here
    // for example
    // onEvent(whatever the event a manual does) {
    // if( MANUALSTRING.equals(manualsMap.get(event.getPlayer().getUniqueId())) then point++ etc
    // }




/*
    this was a test for the point stuff but it is no longer needed
    @EventHandler
    public void onDropDirt(PlayerDropItemEvent event){

        PointManager.addPoints(event.getPlayer(),2);
        ScoreboardManager1.updateScoreboard(event.getPlayer());

    }

 */
}
