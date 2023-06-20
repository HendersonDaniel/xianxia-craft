package xianxiacraft.xianxiacraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static xianxiacraft.xianxiacraft.customItems.ToolItems.taijiPaintingItem;
import static xianxiacraft.xianxiacraft.util.ManualItems.*;
import static xianxiacraft.xianxiacraft.customItems.ToolItems.superHoeItem;
import static xianxiacraft.xianxiacraft.QiManagers.ManualManager.*;
import static xianxiacraft.xianxiacraft.QiManagers.PointManager.*;
import static xianxiacraft.xianxiacraft.QiManagers.ScoreboardManager1.updateScoreboard;

public class OperatorCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(!(commandSender instanceof Player)){
            commandSender.sendMessage("Must be a player to use this command.");
            return true;
        }

        Player sender = (Player) commandSender;



        //obtain item
        if(command.getName().equalsIgnoreCase("obtain")){
            if(!(sender.hasPermission("xianxiacraft.utility.obtain"))){
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                return true;
            }
            if(strings.length == 1){
                switch(strings[0]){
                    case "Mycelium_Chronicle":
                        sender.getWorld().dropItem(sender.getLocation(),fungalManualItem);
                        break;
                    case "Lightning_Emperor_Legacy":
                        sender.getWorld().dropItem(sender.getLocation(),lightningManualItem);
                        break;
                    case "Infinite_Heart_Flow_Yin_Technique":
                        sender.getWorld().dropItem(sender.getLocation(),demonicManualItem);
                        break;
                    case "Sugar_Fiend_Ascension":
                        sender.getWorld().dropItem(sender.getLocation(),sugarFiendManualItem);
                        break;
                    case "Void_Lotus_Art":
                        sender.getWorld().dropItem(sender.getLocation(),spaceManualItem);
                        break;
                    case "Youqin_Xuanya_Guide_to_Toxicity":
                        sender.getWorld().dropItem(sender.getLocation(),poisonManualItem);
                        break;
                    case "Phoenix_Resurrection_Technique":
                        sender.getWorld().dropItem(sender.getLocation(),phoenixManualItem);
                        break;
                    case "Iron_Path_of_Indestructible_Vitality":
                        sender.getWorld().dropItem(sender.getLocation(),ironSkinManualItem);
                        break;
                    case "Celestial_Icebound_Path":
                        sender.getWorld().dropItem(sender.getLocation(),iceManualItem);
                        break;
                    case "Supreme_Gluttony_Scripture":
                        sender.getWorld().dropItem(sender.getLocation(),fattyManualItem);
                        break;
                    case "True_Essence_Hoe":
                        sender.getWorld().dropItem(sender.getLocation(),superHoeItem);
                        break;
                    case "Taiji_Painting":
                        sender.getWorld().dropItem(sender.getLocation(),taijiPaintingItem);
                        break;
                }
                return true;
            } else {
                sender.sendMessage(ChatColor.RED + "Use /obtain item_name");
            }
            return true;
        }


        //addstage
        if(command.getName().equalsIgnoreCase("addstage")){
            if(!(sender.hasPermission("xianxiacraft.cultivation.addstage"))){
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                return true;
            }

            if(strings.length == 1){
                Player player = Bukkit.getPlayerExact(strings[0]);
                if(player != null){
                    setPoints(player,1 + (int) (20 * Math.pow(2,getStage(sender)+1)));
                    updateScoreboard(player);

                } else {
                    sender.sendMessage(ChatColor.RED + "Player not found.");
                }
                return true;
            }

            setPoints(sender,1 + (int) (20 * Math.pow(2,getStage(sender)+1)));
            updateScoreboard(sender);
            return true;
        }

        //checkstats
        if(command.getName().equalsIgnoreCase("checkstats")){
            if(!(sender.hasPermission("xianxiacraft.cultivation.checkstats"))){
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                return true;
            }
            if(strings.length == 1){
                Player player = Bukkit.getPlayerExact(strings[0]);
                if(player != null){
                    sender.sendMessage(ChatColor.GOLD + "Player: " + player.getName() + "\nManual: " + getManual(player) +
                            "\nStage: " + getStage(player) + " [" + percentToNextStage(player) + "%]\nAttack: " + getManualAttackPerStage(getManual(player))*getStage(player) +
                            "\nDefense: " + getManualDefensePerStage(getManual(player))*getStage(player));

                } else {
                    sender.sendMessage(ChatColor.RED + "Player not found.");
                }
                return true;
            } else {
                sender.sendMessage(ChatColor.RED + "Usage is '/checkstats player'");
            }

        }

        return true;
    }
}
