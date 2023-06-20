package xianxiacraft.xianxiacraft.handlers.Manuals;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xianxiacraft.xianxiacraft.QiManagers.PointManager;
import xianxiacraft.xianxiacraft.QiManagers.ScoreboardManager1;

import java.util.Random;

import static xianxiacraft.xianxiacraft.QiManagers.PointManager.getPoints;
import static xianxiacraft.xianxiacraft.QiManagers.PointManager.getStage;
import static xianxiacraft.xianxiacraft.util.CountNearbyBlocks.countNearbyBlocks;

public class SpaceManual extends Manual{

    public SpaceManual(){
        super("Space Manual",0.01,4,3);
    }

    public static void spaceManualPointIncrement(Player player, ItemStack item) {

        if (item.getType() == Material.CHORUS_FRUIT) {

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
                if (!(countNearbyBlocks(player, Material.SHULKER_BOX) >= (Math.pow(2,stage-1)))) {
                    //send message "Breakthrough requirement not met. Consult your manual."
                    player.sendMessage(ChatColor.GOLD + "Breakthrough requirement not met. Consult your manual.");
                    return;
                }
                PointManager.addPoints(player, 1);
                ScoreboardManager1.updateScoreboard(player);
                return;
            }

            PointManager.addPoints(player, 1);
            ScoreboardManager1.updateScoreboard(player);
        }
    }


    public static Location getRandomLocationWithinRadius(Location center, double radius) {
        Random random = new Random();
        double randomAngle = random.nextDouble() * Math.PI * 2;
        double randomRadius = random.nextDouble() * radius;
        double randomYOffset = random.nextDouble() * 10 - 5; // Random offset within a range of -5 to 5

        double offsetX = Math.cos(randomAngle) * randomRadius;
        double offsetZ = Math.sin(randomAngle) * randomRadius;

        return center.clone().add(offsetX, randomYOffset, offsetZ);
    }

}
