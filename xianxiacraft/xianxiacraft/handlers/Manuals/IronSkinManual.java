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

public class IronSkinManual extends Manual {




    //name: Iron Skin Scripture
    //qi regen: 0.01

    public IronSkinManual(){
        super("Ironskin Manual",0.01,2,7);

    }




    // eat 1 iron to gain 1 point
    public static void ironSkinManualPointIncrement(ItemStack itemInHand,Player player){

        int stage = getStage(player);
        int points = getPoints(player);

        if (itemInHand.getType() == Material.IRON_INGOT){
            //fix equation to work all the time FIXED
            //points + 1 ==  (int) (20 * Math.pow(10,(stage+1) * Math.log10(2)) - 20)
            if(points + 1 ==  (int) (20 * Math.pow(2,(stage+1)) - 20)){
                if(!(CountNearbyBlocks.countNearbyBlocks(player,Material.IRON_BLOCK) >= (Math.pow(2,stage-1)))){
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

/*
    private static int countNearbyIronBlocks(Player player) {
        int playerX = player.getLocation().getBlockX();
        int playerY = player.getLocation().getBlockY();
        int playerZ = player.getLocation().getBlockZ();

        Set<Block> ironBlocks = new HashSet<>();
        int radius = 10;

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

*/
}
