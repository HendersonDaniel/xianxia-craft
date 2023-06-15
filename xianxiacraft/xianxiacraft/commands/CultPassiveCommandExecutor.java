package xianxiacraft.xianxiacraft.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xianxiacraft.xianxiacraft.QiManagers.TechniqueManager;

import static xianxiacraft.xianxiacraft.QiManagers.PointManager.getStage;
import static xianxiacraft.xianxiacraft.util.ManualItems.lightningManualItem;

public class CultPassiveCommandExecutor implements CommandExecutor {


    //all cult commands that give passive effects
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(!(commandSender instanceof Player)){
            commandSender.sendMessage("Must be a player to use this command.");
            return true;
        }

        Player sender = (Player) commandSender;


        //tutorial book
        if(command.getName().equalsIgnoreCase("tutorial")){
            sender.getWorld().dropItem(sender.getLocation(), lightningManualItem);
            return true;
        }


        //QiPunch
        if(command.getName().equalsIgnoreCase("qipunch")){
            if(!(getStage(sender) >= 1)){
                sender.sendMessage(ChatColor.GOLD + "Must have Stage 1 or above cultivation base to use this technique.");
                return true;
            }
            boolean currentPunchBool = TechniqueManager.getPunchBool(sender);

            if(currentPunchBool){
                sender.sendMessage(ChatColor.GOLD + "QiPunch: Inactive");
            } else {
                sender.sendMessage(ChatColor.GOLD + "QiPunch: Active");
            }

            TechniqueManager.setPunchBool(sender,!currentPunchBool);
        }

        //next technique passive



        return true;
    }
}
