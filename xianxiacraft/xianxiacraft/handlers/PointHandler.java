package xianxiacraft.xianxiacraft.handlers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import xianxiacraft.xianxiacraft.QiManagers.PointManager;
import xianxiacraft.xianxiacraft.QiManagers.ScoreboardManager1;
import xianxiacraft.xianxiacraft.XianxiaCraft;
import xianxiacraft.xianxiacraft.handlers.Manuals.*;

import static xianxiacraft.xianxiacraft.QiManagers.ManualManager.getManual;
import static xianxiacraft.xianxiacraft.QiManagers.PointManager.getStage;
import static xianxiacraft.xianxiacraft.handlers.Manuals.PhoenixManual.phoenixManualPointIncrement;


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
        switch (manual) {
            case "Ironskin Manual":
                IronSkinManual.ironSkinManualPointIncrement(itemInHand, player);
                break;
            case "Ice Manual":
                IceManual.iceManualPointIncrement(itemInHand, player);
                break;
            case "LightningManual":
                LightningManual.lightningManualPointIncrement(itemInHand, player);
                break;
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
        String manual = getManual(player);

        if(!item.getType().isEdible()){
            return;
        }

        switch (manual) {
            case "Fatty Manual":
                FattyManual.fattyManualPointIncrement(player, item);
                event.setCancelled(true);
                break;
            case "Poison Manual":
                event.setCancelled(PoisonManual.poisonManualPointIncrement(player, item));
                break;
            case "Space Manual":
                SpaceManual.spaceManualPointIncrement(player, item);
                break;
            case "Sugar Fiend":
                SugarFiendManual.sugarFiendManualPointIncrement(player,item);
                break;
            case "Fungal Manual":
                FungalManual.fungalManualPointIncrement(player,item);
                break;
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        Player player = event.getEntity();


        if(getManual(player).equals("Phoenix Manual")){
            PhoenixManual.phoenixManualPointIncrement(player);
        }



    }



}
