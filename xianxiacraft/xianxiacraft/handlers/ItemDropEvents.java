package xianxiacraft.xianxiacraft.handlers;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import xianxiacraft.xianxiacraft.XianxiaCraft;

import static xianxiacraft.xianxiacraft.util.ManualItems.*;

public class ItemDropEvents implements Listener {

    public ItemDropEvents(XianxiaCraft plugin){
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }


    @EventHandler
    public void onMobDeath(EntityDeathEvent event){
        if(event.getEntity() instanceof Player){
            return;
        }

        int random = (int) (Math.random() * 50) +1;

        EntityType entityType = event.getEntityType();

        if(random == 2){
            if(entityType == EntityType.PIG){
                event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), fattyManualItem);
            } else if(entityType == EntityType.MUSHROOM_COW){
                event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), fungalManualItem);
            } else if(entityType == EntityType.ENDERMAN){
                event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), spaceManualItem);
            } else if(entityType == EntityType.VILLAGER){
                event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), demonicManualItem);
            } else if(entityType == EntityType.BLAZE){
                event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), phoenixManualItem);
            } else if(entityType == EntityType.CAVE_SPIDER){
                event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), poisonManualItem);
            } else if(entityType == EntityType.STRAY){
                event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), iceManualItem);
            } else if(entityType == EntityType.PIGLIN || entityType == EntityType.ZOMBIFIED_PIGLIN){
                event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), sugarFiendManualItem);
            } else if(entityType == EntityType.CREEPER){
                event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), tutorialBookItem);
            }
        }
    }



}
