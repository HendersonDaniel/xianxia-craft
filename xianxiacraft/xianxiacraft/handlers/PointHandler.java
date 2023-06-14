package xianxiacraft.xianxiacraft.handlers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import xianxiacraft.xianxiacraft.QiManagers.PointManager;
import xianxiacraft.xianxiacraft.QiManagers.ScoreboardManager1;
import xianxiacraft.xianxiacraft.XianxiaCraft;
import xianxiacraft.xianxiacraft.handlers.Manuals.FattyManual;
import xianxiacraft.xianxiacraft.handlers.Manuals.IceManual;
import xianxiacraft.xianxiacraft.handlers.Manuals.IronSkinManual;

import static xianxiacraft.xianxiacraft.QiManagers.ManualManager.getManual;
import static xianxiacraft.xianxiacraft.QiManagers.PointManager.getStage;


public class PointHandler implements Listener {


    public PointHandler(XianxiaCraft plugin){

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }


    //all the cultivation manuals will be here
    // for example
    // onEvent(whatever the event a manual does) {
    // if( MANUALSTRING.equals(manualsMap.get(event.getPlayer().getUniqueId())) then point++ etc
    // }


    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event){

        Player player = event.getPlayer();
        ItemStack itemInHand = event.getPlayer().getInventory().getItemInMainHand();
        String manual = getManual(player);

        //ironskin manual
        if(manual.equals("Ironskin Manual")){
            IronSkinManual.ironSkinManualPointIncrement(itemInHand,player);
        } else if(manual.equals("Ice Manual")){
            IceManual.iceManualPointIncrement(itemInHand,player);
        }


    }

    @EventHandler
    public void onPlayerPlaceEvent(BlockPlaceEvent event){

        Player player = event.getPlayer();
        String manual = getManual(player);

        if(manual.equals("Ice Manual") && event.getBlockPlaced().getType() == Material.ICE){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerEatEvent(PlayerItemConsumeEvent event){
        //fatty manual
        Player player = event.getPlayer();
        ItemStack item = event.getItem();


        if(getManual(player).equals("Fatty Manual") && item.getType().isEdible()){
            FattyManual.fattyManualPointIncrement(player,item);
            event.setCancelled(true);

        }
    }

/*
    public static void updateCultPerms(Player player){

        int stage = getStage(player);


        //put command perms in this
        switch (stage){
            default:

            case 10:
            case 9:
            case 8:
            case 7:
            case 6:
            case 5:
            case 4:
            case 3:
            case 2:
            case 1:



        }

    }
*/


}
