package xianxiacraft.xianxiacraft.handlers.Manuals;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xianxiacraft.xianxiacraft.QiManagers.PointManager;
import xianxiacraft.xianxiacraft.QiManagers.ScoreboardManager1;
import xianxiacraft.xianxiacraft.util.CountNearbyBlocks;

import static xianxiacraft.xianxiacraft.QiManagers.PointManager.getPoints;
import static xianxiacraft.xianxiacraft.QiManagers.PointManager.getStage;
import static xianxiacraft.xianxiacraft.util.CountNearbyBlocks.countNearbyBlocks;

public class FungalManual extends Manual{

    public FungalManual(){
        super("Fungal Manual",0.01,4,4);
    }

    public static void fungalManualQiMove(Player player, boolean bool){
        if(bool){
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,80,1,false,false,false));
        } else{
            player.removePotionEffect(PotionEffectType.INVISIBILITY);
        }
    }

    public static boolean fungalManualPointIncrement(Player player, ItemStack item) {

        if (item.getType() == Material.SUSPICIOUS_STEW) {

            int stage = getStage(player);
            int points = getPoints(player);

            ItemStack itemInRightHand = player.getInventory().getItemInMainHand();
            ItemStack itemInLeftHand = player.getInventory().getItemInOffHand();

            if (item.equals(itemInRightHand)) {
                itemInRightHand.setAmount(itemInRightHand.getAmount() - 1);
            } else if (item.equals(itemInLeftHand)) {
                itemInLeftHand.setAmount(itemInLeftHand.getAmount() - 1);
            }

            if (points + 2 == (int) (20 * Math.pow(10, (stage + 1) * Math.log10(2)) - 20)) {
                if (!(CountNearbyBlocks.countNearbyBlocks(player,Material.MYCELIUM) >= 1)) {
                    //send message "Breakthrough requirement not met. Consult your manual."
                    player.sendMessage(ChatColor.GOLD + "Breakthrough requirement not met. Consult your manual.");
                    return false;
                }
                PointManager.addPoints(player, 2);
                ScoreboardManager1.updateScoreboard(player);
                return true;
            }

            PointManager.addPoints(player, 2);
            ScoreboardManager1.updateScoreboard(player);
            return true;
        }
        return false;
    }
}
