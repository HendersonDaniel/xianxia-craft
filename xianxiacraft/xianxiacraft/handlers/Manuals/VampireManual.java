package xianxiacraft.xianxiacraft.handlers.Manuals;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import xianxiacraft.xianxiacraft.QiManagers.PointManager;
import xianxiacraft.xianxiacraft.QiManagers.ScoreboardManager1;

import static xianxiacraft.xianxiacraft.QiManagers.PointManager.getPoints;
import static xianxiacraft.xianxiacraft.QiManagers.PointManager.getStage;

public class VampireManual extends Manual {

    public VampireManual() {
        super("Demonic Manual", 0.01, 5, 4);
    }


    public static void demonicManualManualPointIncrement(Player player, LivingEntity target) {

        int stage = getStage(player);
        int points = getPoints(player);

        EntityType e = target.getType();
        if (e == EntityType.VILLAGER || e == EntityType.PILLAGER || e == EntityType.ILLUSIONER || e == EntityType.EVOKER || e == EntityType.WITCH) {
            PointManager.addPoints(player, 1);
            ScoreboardManager1.updateScoreboard(player);
        }
    }




}






