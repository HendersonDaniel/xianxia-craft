package xianxiacraft.xianxiacraft.handlers.Manuals;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import xianxiacraft.xianxiacraft.QiManagers.PointManager;
import xianxiacraft.xianxiacraft.QiManagers.ScoreboardManager1;
import xianxiacraft.xianxiacraft.util.CountNearbyBlocks;

import static xianxiacraft.xianxiacraft.QiManagers.PointManager.getPoints;
import static xianxiacraft.xianxiacraft.QiManagers.PointManager.getStage;

public class PhoenixManual extends Manual{

    public PhoenixManual(){
        super("Phoenix Manual",0.03,6,4);
    }


    public static void phoenixManualPointIncrement(Player player){

        int stage = getStage(player);
        int points = getPoints(player);
        int level = player.getLevel();

        if (level > 0){
            //fix equation to work all the time FIXED
            //points + 1 ==  (int) (20 * Math.pow(10,(stage+1) * Math.log10(2)) - 20)
            if(points + level >=  (int) (20 * Math.pow(2,(stage+1)) - 20)){
                if(!(level >= 10*getStage(player))){
                    //send message "Breakthrough requirement not met. Consult your manual."
                    player.sendMessage(ChatColor.GOLD + "Breakthrough requirement not met. Consult your manual.");
                    return;
                }
                PointManager.addPoints(player,level);
                ScoreboardManager1.updateScoreboard(player);
                return;
            }

            PointManager.addPoints(player,level);
            ScoreboardManager1.updateScoreboard(player);
        }
    }


}
