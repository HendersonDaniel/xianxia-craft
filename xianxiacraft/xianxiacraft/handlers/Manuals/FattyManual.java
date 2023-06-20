package xianxiacraft.xianxiacraft.handlers.Manuals;

import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xianxiacraft.xianxiacraft.QiManagers.PointManager;
import xianxiacraft.xianxiacraft.QiManagers.ScoreboardManager1;

import static xianxiacraft.xianxiacraft.QiManagers.PointManager.getPoints;
import static xianxiacraft.xianxiacraft.QiManagers.PointManager.getStage;

public class FattyManual extends Manual{

    public FattyManual(){
        super("Fatty Manual",0.01,5,5);
    }


    public static void fattyManualQiMove(Player player, boolean bool){
        if(bool){
            player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(1.0);
        } else{
            player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(0.0);
        }
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
            player.setSaturation(20);
            return;
        }

        PointManager.addPoints(player,1);
        ScoreboardManager1.updateScoreboard(player);
        player.setFoodLevel(19);
        player.setSaturation(8);
    }

}

