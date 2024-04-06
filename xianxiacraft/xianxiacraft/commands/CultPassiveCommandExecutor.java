package xianxiacraft.xianxiacraft.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xianxiacraft.xianxiacraft.QiManagers.ManualManager;
import xianxiacraft.xianxiacraft.QiManagers.TechniqueManager;

import static xianxiacraft.xianxiacraft.QiManagers.ManualManager.getManual;
import static xianxiacraft.xianxiacraft.QiManagers.PointManager.getMaxQi;
import static xianxiacraft.xianxiacraft.QiManagers.PointManager.getStage;
import static xianxiacraft.xianxiacraft.QiManagers.QiManager.getQi;
import static xianxiacraft.xianxiacraft.QiManagers.QiManager.setQi;
import static xianxiacraft.xianxiacraft.QiManagers.TechniqueManager.getAuraBool;
import static xianxiacraft.xianxiacraft.QiManagers.TechniqueManager.qiAuraGlow;
import static xianxiacraft.xianxiacraft.handlers.Manuals.FattyManual.fattyManualQiMove;
import static xianxiacraft.xianxiacraft.handlers.Manuals.FungalManual.fungalManualQiMove;
import static xianxiacraft.xianxiacraft.handlers.Manuals.SugarFiendManual.sugarFiendQiMove;
import static xianxiacraft.xianxiacraft.util.ManualItems.tutorialBookItem;

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
        if(command.getName().equalsIgnoreCase("cultutorial")){
            sender.getWorld().dropItem(sender.getLocation(), tutorialBookItem);
            return true;
        }

        //detonate
        if(command.getName().equalsIgnoreCase("detonate")){

            if(!(getQi(sender) >= getMaxQi(sender))){
                sender.sendMessage(ChatColor.GOLD + "You must be at full qi to self-detonate.");
                return true;
            }

            sender.sendMessage(ChatColor.RED + "You self-detonated.");
            boolean fire = false;
            if(getManual(sender).equals("Phoenix Manual")){
                fire = true;
            }
            if(getManual(sender).equals("LightningManual")){
                sender.getWorld().strikeLightning(sender.getLocation());
            }
            sender.getWorld().createExplosion(sender.getLocation(),getStage(sender),fire);
            sender.setHealth(0.0);
            setQi(sender,0);

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
            return true;
        }

        //QiMine
        if(command.getName().equalsIgnoreCase("qimine")){
            if(!(getStage(sender) >= 2)){
                sender.sendMessage(ChatColor.GOLD + "Must have Stage 2 or above cultivation base to use this technique.");
                return true;
            }

            boolean currentMineBool = TechniqueManager.getMineBool(sender);

            if(currentMineBool){
                sender.sendMessage(ChatColor.GOLD + "QiMine: Inactive");
                sender.removePotionEffect(PotionEffectType.FAST_DIGGING);
            } else {
                sender.sendMessage(ChatColor.GOLD + "QiMine: Active");
                //max haste is 4 because any higher causes glitches
                int amplifier = (int) (getStage(sender)/2.0)-1;
                if (amplifier > 3){
                    amplifier = 3;
                }
                sender.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE,amplifier,false,false,false));
            }

            TechniqueManager.setMineBool(sender,!currentMineBool);


            return true;
        }

        //QiMove
        if(command.getName().equalsIgnoreCase("qimove")){
            if(!(getStage(sender) >= 3)){
                sender.sendMessage(ChatColor.GOLD + "Must have Stage 3 or above cultivation base to use this technique.");
                return true;
            }

            boolean currentMoveBool = TechniqueManager.getMoveBool(sender);

            if(currentMoveBool){
                sender.sendMessage(ChatColor.GOLD + "QiMove: Inactive");
            } else {
                sender.sendMessage(ChatColor.GOLD + "QiMove: Active");
            }

            TechniqueManager.setMoveBool(sender,!currentMoveBool);
            String senderManual = getManual(sender);


            switch (senderManual) {
                case "Sugar Fiend":
                    sugarFiendQiMove(sender, TechniqueManager.getMoveBool(sender));
                    break;
                case "Fatty Manual":
                    fattyManualQiMove(sender, TechniqueManager.getMoveBool(sender));
                    break;
                case "Fungal Manual":
                    fungalManualQiMove(sender,TechniqueManager.getMoveBool(sender));
                    break;
            }
            return true;
        }

        //QiAura
        if(command.getName().equalsIgnoreCase("qiaura")){
            if(!(getStage(sender) >= 6)){
                sender.sendMessage(ChatColor.GOLD + "Must have Stage 6 or above cultivation base to use this technique.");
                return true;
            }

            boolean currentAuraBool = TechniqueManager.getAuraBool(sender);

            if(currentAuraBool){
                sender.sendMessage(ChatColor.GOLD + "QiAura: Inactive");
            } else {
                sender.sendMessage(ChatColor.GOLD + "QiAura: Active");
            }

            TechniqueManager.setAuraBool(sender,!currentAuraBool);
            qiAuraGlow(sender,getAuraBool(sender));
            return true;
        }

        //qifly
        if(command.getName().equalsIgnoreCase("qifly")){
            if(!(getStage(sender) >= 7)){
                sender.sendMessage(ChatColor.GOLD + "Must have Stage 7 or above cultivation base to use this technique.");
                return true;
            }

            boolean currentFlyBool = TechniqueManager.getFlyBool(sender);

            if(currentFlyBool){
                sender.sendMessage(ChatColor.GOLD + "QiFly: Inactive");
                sender.setAllowFlight(false);
                sender.setFlySpeed(0.1f);
            } else {
                sender.sendMessage(ChatColor.GOLD + "QiFly: Active");
                sender.setAllowFlight(true);

                if(getManual(sender).equals("Sugar Fiend")){
                    sender.setFlySpeed((float) (getStage(sender)*0.05));
                }

            }

            TechniqueManager.setFlyBool(sender,!currentFlyBool);

            return true;
        }

        //accept manual
        if(command.getName().equalsIgnoreCase("manaccept")){

            ManualManager.accept(sender);

        }





        return true;
    }
}
