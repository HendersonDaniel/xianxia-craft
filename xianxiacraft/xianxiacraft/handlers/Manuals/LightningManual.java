package xianxiacraft.xianxiacraft.handlers.Manuals;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xianxiacraft.xianxiacraft.QiManagers.PointManager;
import xianxiacraft.xianxiacraft.QiManagers.ScoreboardManager1;
import xianxiacraft.xianxiacraft.util.CountNearbyBlocks;

import static xianxiacraft.xianxiacraft.QiManagers.PointManager.getPoints;
import static xianxiacraft.xianxiacraft.QiManagers.PointManager.getStage;

public class LightningManual extends Manual{

    public LightningManual(){
        super("LightningManual",0.05,7,1);
    }

    public static void lightningManualPointIncrement(ItemStack itemInHand, Player player){

        int stage = getStage(player);
        int points = getPoints(player);

        if (itemInHand.getType() == Material.COPPER_INGOT){
            //fix equation to work all the time FIXED
            //points + 1 ==  (int) (20 * Math.pow(10,(stage+1) * Math.log10(2)) - 20)
            if(points + 1 ==  (int) (20 * Math.pow(2,(stage+1)) - 20)){
                if(!((CountNearbyBlocks.countNearbyBlocks(player,Material.COPPER_BLOCK) + CountNearbyBlocks.countNearbyBlocks(player,Material.WAXED_COPPER_BLOCK)) >= (Math.pow(2,stage-1)))){
                    //send message "Breakthrough requirement not met. Consult your manual."
                    player.sendMessage(ChatColor.GOLD + "Breakthrough requirement not met. Consult your manual.");
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
}
