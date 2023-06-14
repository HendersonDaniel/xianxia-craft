package xianxiacraft.xianxiacraft.handlers.Manuals;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xianxiacraft.xianxiacraft.QiManagers.PointManager;
import xianxiacraft.xianxiacraft.QiManagers.ScoreboardManager1;

import static xianxiacraft.xianxiacraft.QiManagers.PointManager.getPoints;
import static xianxiacraft.xianxiacraft.QiManagers.PointManager.getStage;

public class FattyManual extends Manual{

    public FattyManual(){
        super("Fatty Manual",0.01,4,4);
    }

    public static void fattyManualPointIncrement(Player player, ItemStack item){

        int stage = getStage(player);
        int points = getPoints(player);

        ItemStack itemInRightHand = player.getInventory().getItemInMainHand();
        ItemStack itemInLeftHand = player.getInventory().getItemInOffHand();

        if(item.equals(itemInRightHand)){
            itemInRightHand.setAmount(itemInRightHand.getAmount()-1);
        } else if (item.equals(itemInLeftHand)){
            itemInLeftHand.setAmount(itemInLeftHand.getAmount()-1);
        }

        if(points + 1 ==  (int) (20 * Math.pow(10,(stage+1) * Math.log10(2)) - 20)){
            if(player.getFoodLevel() > 0){
                //send message "Breakthrough requirement not met. Consult your manual."
                player.sendMessage(ChatColor.GOLD + "Breakthrough requirement not met. Consult your manual.");
                return;
            }
            PointManager.addPoints(player,1);
            ScoreboardManager1.updateScoreboard(player);
            player.setFoodLevel(20);
            return;
        }

        PointManager.addPoints(player,1);
        ScoreboardManager1.updateScoreboard(player);
        player.setFoodLevel(19);
    }

}

