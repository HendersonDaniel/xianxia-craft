package xianxiacraft.xianxiacraft.handlers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;
import xianxiacraft.xianxiacraft.QiManagers.ScoreboardManager1;
import xianxiacraft.xianxiacraft.XianxiaCraft;

import static xianxiacraft.xianxiacraft.QiManagers.ManualManager.*;
import static xianxiacraft.xianxiacraft.QiManagers.PointManager.getStage;
import static xianxiacraft.xianxiacraft.QiManagers.QiManager.getQi;
import static xianxiacraft.xianxiacraft.QiManagers.QiManager.subtractQi;
import static xianxiacraft.xianxiacraft.QiManagers.TechniqueManager.getPunchBool;

public class HitEvents implements Listener {

    public HitEvents(XianxiaCraft plugin){
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }


    //attack damage calculation, defense against other player calculation, punch techniques
    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event){

        //player vs players and mobs
        if(event.getDamager() instanceof Player) {

            Player attackingPlayer = (Player) event.getDamager();
            String attackingPlayerManual = getManual(attackingPlayer);
            int attackingPlayerStage = getStage(attackingPlayer);

            double attackDamage = Math.ceil(event.getDamage()) + attackingPlayerStage * getManualAttackPerStage(attackingPlayerManual);


            //PUNCHING TECHNIQUES EFFECTS
            if (getPunchBool(attackingPlayer) && getQi(attackingPlayer) >= 10) {
                subtractQi(attackingPlayer, 10);
                ScoreboardManager1.updateScoreboard(attackingPlayer);

                //check for manual type
                if (attackingPlayerManual.equals("Ironskin Manual")) {
                    //ironskin manual multiplies attack by 2.
                    attackDamage *= 1.5;
                } else if(attackingPlayerManual.equals("Fatty Manual")){
                    LivingEntity target = (LivingEntity) event.getEntity();
                    Vector knockbackDirection = target.getLocation().toVector().subtract(attackingPlayer.getLocation().toVector()).normalize();
                    double knockbackStrength = 1.5 * attackingPlayerStage; // Adjust this value to control the knockback strength
                    double knockbackVertical = 0.4 * attackingPlayerStage; // Adjust this value to control the vertical knockback
                    Vector knockbackVelocity = knockbackDirection.multiply(knockbackStrength).setY(knockbackVertical);
                    target.setVelocity(knockbackVelocity);
                    attackDamage += 2 * attackingPlayerStage;
                } // else if() for other manuals punching technique go here
            } else if(getPunchBool(attackingPlayer)){
                attackingPlayer.sendMessage(ChatColor.GOLD + "You did not have enough qi to augment your punch.");

            }


            //check for if target is player or not and respond accordingly
            if (!(event.getEntity() instanceof Player)) {
                event.setDamage(attackDamage);
                return;
            }

            Player defendingPlayer = (Player) event.getEntity();
            String defendingPlayerManual = getManual(defendingPlayer);
            int defendingPlayerStage = getStage(defendingPlayer);

            double defense = defendingPlayerStage * getManualDefensePerStage(defendingPlayerManual);

            double resultAttackDamage = attackDamage - defense;

            if (resultAttackDamage > 0) {
                event.setDamage(resultAttackDamage);
            } else {
                //minimum attack
                event.setDamage(1);
            }

            //mobs vs players
        } else if((event.getDamager() instanceof Monster) && (event.getEntity() instanceof Player)){
            Player defendingPlayer = (Player) event.getEntity();
            String defendingPlayerManual = getManual(defendingPlayer);
            int defendingPlayerStage = getStage(defendingPlayer);

            double defense = defendingPlayerStage * getManualDefensePerStage(defendingPlayerManual);
            double damage = event.getDamage();

            double resultDamage = damage - defense;

            if(resultDamage > 0){
                event.setDamage(resultDamage);
            } else {
                //minimum damage for PvE
                event.setDamage(1);
            }
        }


    }

    //Environment vs player

    @EventHandler
    public void onPlayerTakeDamage(EntityDamageEvent event){
        if(!(event.getEntity() instanceof Player)){
            return;
        }


        Player defendingPlayer = (Player) event.getEntity();
        String defendingPlayerManual = getManual(defendingPlayer);
        int defendingPlayerStage = getStage(defendingPlayer);

        double defense = defendingPlayerStage * getManualDefensePerStage(defendingPlayerManual);
        double damage = event.getDamage();

        double resultDamage = damage - defense;

        if(resultDamage > 0){
            event.setDamage(resultDamage);
        } else {
            //minimum damage for PvE
            event.setDamage(1);
        }

    }


}
