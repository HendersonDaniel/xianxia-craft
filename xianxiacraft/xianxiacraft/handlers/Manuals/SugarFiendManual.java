package xianxiacraft.xianxiacraft.handlers.Manuals;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xianxiacraft.xianxiacraft.QiManagers.PointManager;
import xianxiacraft.xianxiacraft.QiManagers.ScoreboardManager1;

import static xianxiacraft.xianxiacraft.QiManagers.PointManager.getPoints;
import static xianxiacraft.xianxiacraft.QiManagers.PointManager.getStage;
import static xianxiacraft.xianxiacraft.util.CountNearbyBlocks.countNearbyBlocks;

public class SugarFiendManual extends Manual{

    public SugarFiendManual(){
        super("Sugar Fiend",0.05,5,4);
    }



    public static void sugarFiendQiMove(Player player,boolean bool){
        if(bool){
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE,getStage(player),false,false,false));
        } else{
            player.removePotionEffect(PotionEffectType.SPEED);
        }
    }

    public static boolean sugarFiendManualPointIncrement(Player player, ItemStack item) {

        if (player.getWorld().getName().equals("world_nether")) {
            if (item.getType() == Material.CAKE || item.getType() == Material.COOKIE || item.getType() == Material.PUMPKIN_PIE || item.getType() == Material.HONEY_BOTTLE) {

                int stage = getStage(player);
                int points = getPoints(player);

                ItemStack itemInRightHand = player.getInventory().getItemInMainHand();
                ItemStack itemInLeftHand = player.getInventory().getItemInOffHand();

                if (item.equals(itemInRightHand)) {
                    itemInRightHand.setAmount(itemInRightHand.getAmount() - 1);
                } else if (item.equals(itemInLeftHand)) {
                    itemInLeftHand.setAmount(itemInLeftHand.getAmount() - 1);
                }

                if (points + 1 == (int) (20 * Math.pow(10, (stage + 1) * Math.log10(2)) - 20)) {
                    if (!(countNearbyBlocks(player, Material.CAKE) >= (Math.pow(2, stage - 1)))) {
                        //send message "Breakthrough requirement not met. Consult your manual."
                        player.sendMessage(ChatColor.GOLD + "Breakthrough requirement not met. Consult your manual.");
                        return false;
                    }
                    PointManager.addPoints(player, 1);
                    ScoreboardManager1.updateScoreboard(player);
                    player.setFoodLevel(19);
                    return true;
                }

                PointManager.addPoints(player, 1);
                ScoreboardManager1.updateScoreboard(player);
                player.setFoodLevel(19);
                return true;
            }
            return false;
        }
        return false;
    }

}
