package xianxiacraft.xianxiacraft.util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.player.PlayerInteractEvent;
import xianxiacraft.xianxiacraft.XianxiaCraft;

public class ChatUtils {

//    public static void handleManualChange(PlayerInteractEvent e) {
//        TextComponent msg = Component.text("Click ")
//                .append(Component.text("accept",NamedTextColor.GREEN).clickEvent(ClickEvent.runCommand("/xianxiacraft:manaccept")))
//                .append(Component.text(" to cultivate this manual"));
//        XianxiaCraft.getAdventure().sender(e.getPlayer()).sendMessage(msg);
//    }

    public static void handleManualChange(PlayerInteractEvent e) {
        Component msg = Component.text("Are you sure you want to learn a different manual? Changing manuals resets your cultivation progress! ")
                .append(Component.text("Click here", NamedTextColor.RED)
                        .clickEvent(ClickEvent.runCommand("/manualaccept"))
                        .hoverEvent(Component.text("Click to confirm").color(NamedTextColor.RED)))
                .append(Component.text(" or type ", NamedTextColor.GOLD))
                .append(Component.text("/manualaccept ",NamedTextColor.RED)
                        .clickEvent(ClickEvent.runCommand("/manualaccept"))
                        .hoverEvent(Component.text("Click to confirm").color(NamedTextColor.RED)))
                .append(Component.text("to confirm.",NamedTextColor.GOLD));
        XianxiaCraft.getAdventure().sender(e.getPlayer()).sendMessage(msg.color(NamedTextColor.GOLD));
    }

}
