package xianxiacraft.xianxiacraft.QiManagers;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TechniqueManager {



    //punch technique
    private static Map<UUID,Boolean> punchBool = new HashMap<>();

    public static Boolean getPunchBool(Player player){
        return punchBool.getOrDefault(player.getUniqueId(),false);
    }

    public static void setPunchBool(Player player, boolean bool){
        punchBool.put(player.getUniqueId(),bool);
    }

    //__ technique

}
