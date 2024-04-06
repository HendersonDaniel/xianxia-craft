package xianxiacraft.xianxiacraft.commands;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xianxiacraft.xianxiacraft.QiManagers.ManualManager;

import static xianxiacraft.xianxiacraft.QiManagers.ScoreboardManager1.hideScoreboard;
import static xianxiacraft.xianxiacraft.QiManagers.ScoreboardManager1.showScoreboard;
import static xianxiacraft.xianxiacraft.util.ManualItems.tutorialBookItem;

public class PlayerCommands implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!(commandSender instanceof Player)){
            commandSender.sendMessage("Must be a player to use this command.");
            return true;
        }

        Player sender = (Player) commandSender;


        //tutorial book
        if(command.getName().equalsIgnoreCase("cultutorial")){
            sender.getWorld().dropItem(sender.getLocation(), tutorialBookItem);
            return true;
        }

        //accept manual
        if(command.getName().equalsIgnoreCase("manualaccept")){

            ManualManager.accept(sender);
            return true;
        }

        // show scoreboard
        if(command.getName().equalsIgnoreCase("dantianscoreboard")){

            if(strings.length != 1){
                sender.sendMessage(ChatColor.RED + "Usage: " + command.getUsage());
                return true;
            }
            if(strings[0].equals("show")){
                //createScoreboard(sender);
                showScoreboard(sender);

            } else if(strings[0].equals("hide")){
                //removeScoreboard(sender);
                hideScoreboard(sender);

            } else {
                sender.sendMessage(ChatColor.RED + "Usage: " + command.getUsage());
            }
            return true;
        }


        return true;

    }
}
