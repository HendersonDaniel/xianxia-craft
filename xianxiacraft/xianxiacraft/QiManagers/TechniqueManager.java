package xianxiacraft.xianxiacraft.QiManagers;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TechniqueManager {



    //punch technique
    private static Map<UUID,Boolean> punchBool = new HashMap<>();
    private static Map<UUID,Boolean> moveBool = new HashMap<>();
    private static Map<UUID,Boolean> mineBool = new HashMap<>();
    public static Map<UUID,Boolean> hiddenByTaijiPainting = new HashMap<>();

    //hiddenByTaijiPainting
    public static Boolean getHiddenByTaijiPaintingBool(Player player){
        return hiddenByTaijiPainting.getOrDefault(player.getUniqueId(),false);
    }
    public static void setHiddenByTaijiPaintingBool(Player player, boolean bool){
        hiddenByTaijiPainting.put(player.getUniqueId(),bool);
    }

    //qipunch
    public static Boolean getPunchBool(Player player){
        return punchBool.getOrDefault(player.getUniqueId(),false);
    }
    public static void setPunchBool(Player player, boolean bool){
        punchBool.put(player.getUniqueId(),bool);
    }

    //qimove
    public static Boolean getMoveBool(Player player){
        return moveBool.getOrDefault(player.getUniqueId(),false);
    }
    public static void setMoveBool(Player player, boolean bool){
        moveBool.put(player.getUniqueId(),bool);
    }

    //qimine
    public static Boolean getMineBool(Player player){
        return mineBool.getOrDefault(player.getUniqueId(),false);
    }
    public static void setMineBool(Player player, boolean bool){
        mineBool.put(player.getUniqueId(),bool);
    }


}
