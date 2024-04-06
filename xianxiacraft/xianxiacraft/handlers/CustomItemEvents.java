package xianxiacraft.xianxiacraft.handlers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import xianxiacraft.xianxiacraft.XianxiaCraft;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static xianxiacraft.xianxiacraft.QiManagers.ManualManager.getManual;
import static xianxiacraft.xianxiacraft.QiManagers.ManualManager.getManualQiRegen;
import static xianxiacraft.xianxiacraft.QiManagers.PointManager.getMaxQi;
import static xianxiacraft.xianxiacraft.QiManagers.QiManager.getQi;
import static xianxiacraft.xianxiacraft.QiManagers.QiManager.subtractQi;
import static xianxiacraft.xianxiacraft.QiManagers.TechniqueManager.getHiddenByTaijiPaintingBool;
import static xianxiacraft.xianxiacraft.QiManagers.TechniqueManager.setHiddenByTaijiPaintingBool;

public class CustomItemEvents implements Listener {

    private XianxiaCraft plugin;
    public CustomItemEvents(XianxiaCraft plugin){
        Bukkit.getPluginManager().registerEvents(this,plugin);
        this.plugin = plugin;
    }



    @EventHandler
    public void onRightClickWithTool(PlayerInteractEvent event){

        Player player = event.getPlayer();

        // right clicking ON A BLOCK WITH A TOOL IN HAND
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getItem() != null){

            // True Essence Hoe
            NamespacedKey key = new NamespacedKey(plugin, "customUtilityTools");
            ItemMeta itemMeta = event.getItem().getItemMeta();
            PersistentDataContainer container = itemMeta.getPersistentDataContainer();

            if(container.has(key , PersistentDataType.STRING)) {
                String foundValue = container.get(key, PersistentDataType.STRING);
                assert foundValue != null;
                if(foundValue.contains("superHoe")){

                    Block block = event.getClickedBlock();

                    int blockX = block.getX();
                    int blockY = block.getY();
                    int blockZ = block.getZ();

                    int radius = 5;

                    for (int x = blockX - radius; x <= blockX + radius; x++) {
                        for (int y = blockY - 1; y <= blockY + 1; y++) { //1 block above and below
                            for (int z = blockZ - radius; z <= blockZ + radius; z++) {
                                Block blockToPlow = player.getWorld().getBlockAt(x, y, z);
                                if (blockToPlow.getType() == Material.GRASS_BLOCK || blockToPlow.getType() == Material.DIRT) {
                                    blockToPlow.setType(Material.FARMLAND);
                                }
                            }
                        }
                    }
                }
            }
            // end of True Essence Hoe

            // right click anything with something in their hand
        } else if((event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) && event.getItem() != null) {

            //Taiji Painting
            NamespacedKey key = new NamespacedKey(plugin, "customUtilityTools");
            ItemMeta itemMeta = event.getItem().getItemMeta();
            assert itemMeta != null;
            PersistentDataContainer container = itemMeta.getPersistentDataContainer();

            if (container.has(key, PersistentDataType.STRING)) {
                String foundValue = container.get(key, PersistentDataType.STRING);
                assert foundValue != null;
                if (foundValue.contains("taijiPainting")) {
                    //code here lol. open a gui and

                    double regenPercent = getManualQiRegen(getManual(player));

                    if(getHiddenByTaijiPaintingBool(player)){
                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                            if(player1 != player){
                                player1.showPlayer(plugin,player);
                            }
                        }
                        setHiddenByTaijiPaintingBool(player,false);
                        player.sendMessage(ChatColor.GOLD + "You are no longer hidden.");
                    } else if(getQi(player) >= (int) Math.ceil(getMaxQi(player)*(regenPercent+0.01))+1){
                        subtractQi(player,(int) Math.ceil(getMaxQi(player)*(regenPercent+0.01))+1);
                        setHiddenByTaijiPaintingBool(player,true);
                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                            if(player1 != player){
                                player1.hidePlayer(plugin,player);
                            }
                        }
                        player.sendMessage(ChatColor.GOLD + "You have been hidden by the Taiji Painting.");
                    } else {
                        player.sendMessage(ChatColor.GOLD + "You do not have enough qi to use the Taiji Painting.");
                    }
                    event.setCancelled(true);
                }
                //end of taiji painting
            }
        }



    }






}
