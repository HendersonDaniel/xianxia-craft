package xianxiacraft.xianxiacraft.util;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

import static xianxiacraft.xianxiacraft.QiManagers.ManualManager.getManual;
import static xianxiacraft.xianxiacraft.QiManagers.PointManager.daoAttainmentMap;

public class ManualUtils {


    public static int getCultivationModifier(Player player){

        Set<String> daoAttainmentSet = new HashSet<>(daoAttainmentMap.getOrDefault(player.getUniqueId(), new HashSet<>()));
        daoAttainmentSet.remove(getManual(player));
        return daoAttainmentSet.size();
    }


}
