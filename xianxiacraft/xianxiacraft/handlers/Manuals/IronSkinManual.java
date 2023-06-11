package xianxiacraft.xianxiacraft.handlers.Manuals;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import xianxiacraft.xianxiacraft.QiManagers.PointManager;
import xianxiacraft.xianxiacraft.QiManagers.ScoreboardManager1;
import xianxiacraft.xianxiacraft.XianxiaCraft;

import java.util.HashSet;
import java.util.Set;

import static xianxiacraft.xianxiacraft.QiManagers.ManualManager.getManual;
import static xianxiacraft.xianxiacraft.QiManagers.PointManager.getPoints;
import static xianxiacraft.xianxiacraft.QiManagers.PointManager.getStage;

public class IronSkinManual extends Manual {




    //name: Iron Skin Scripture
    //qi regen: 0.01

    public IronSkinManual(XianxiaCraft plugin){
        super("Ironskin Manual");
        Bukkit.getPluginManager().registerEvents(this, plugin);

    }



    // eat 1 iron to gain 1 point
    @EventHandler
    public void onIronEat(PlayerInteractEvent event){
        ItemStack itemInHand = event.getPlayer().getInventory().getItemInMainHand();
        Player player = event.getPlayer();

        if(!(getManual(player).equals(getManualName()))){
            return;
        }

        int stage = getStage(player);
        int points = getPoints(player);


        if (itemInHand.getType() == Material.IRON_INGOT){
            //fix equation to work all the time FIXED BUT it rounds wrong on stage 2
            if(points + 1 ==  (int) (20 * Math.pow(10,(stage+1) * Math.log10(2)) - 20)){
                if(!(countNearbyIronBlocks(player,10) >= (Math.pow(2,stage-1)))){
                    //send message "Breakthrough requirement not met. Consult your manual."
                    player.sendMessage("Breakthrough requirement not met. Consult your manual.");
                    return;
                }
                itemInHand.setAmount(itemInHand.getAmount()-1);
                PointManager.addPoints(player,1);
                ScoreboardManager1.updateScoreboard(player);
                return;
            }

            itemInHand.setAmount(itemInHand.getAmount()-1);
            PointManager.addPoints(player,1);
            ScoreboardManager1.updateScoreboard(player);
        }
    }


    public int countNearbyIronBlocks(Player player, int radius) {
        int playerX = player.getLocation().getBlockX();
        int playerY = player.getLocation().getBlockY();
        int playerZ = player.getLocation().getBlockZ();

        Set<Block> ironBlocks = new HashSet<>();

        for (int x = playerX - radius; x <= playerX + radius; x++) {
            for (int y = playerY - radius; y <= playerY + radius; y++) {
                for (int z = playerZ - radius; z <= playerZ + radius; z++) {
                    Block block = player.getWorld().getBlockAt(x, y, z);
                    if (block.getType() == Material.IRON_BLOCK) {
                        ironBlocks.add(block);
                    }
                }
            }
        }

        return ironBlocks.size();
    }


}
