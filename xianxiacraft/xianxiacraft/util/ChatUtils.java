package xianxiacraft.xianxiacraft.util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.player.PlayerInteractEvent;
import xianxiacraft.xianxiacraft.XianxiaCraft;

public class ChatUtils {

    public static void handleManualChange(PlayerInteractEvent e) {
        TextComponent msg = Component.text("Click ")
                .append(Component.text("accept",NamedTextColor.GREEN).clickEvent(ClickEvent.runCommand("/xianxiacraft:manaccept")))
                .append(Component.text(" to cultivate this manual"));
        XianxiaCraft.getAdventure().sender(e.getPlayer()).sendMessage(msg);
    }


}
