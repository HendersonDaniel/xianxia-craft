package xianxiacraft.xianxiacraft.handlers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import xianxiacraft.xianxiacraft.QiManagers.ScoreboardManager1;
import xianxiacraft.xianxiacraft.XianxiaCraft;
import xianxiacraft.xianxiacraft.handlers.Manuals.SpaceManual;
import xianxiacraft.xianxiacraft.util.CountNearbyBlocks;
import xianxiacraft.xianxiacraft.util.FreezeEffect;

import java.util.Objects;

import static xianxiacraft.xianxiacraft.QiManagers.ManualManager.*;
import static xianxiacraft.xianxiacraft.QiManagers.PointManager.*;
import static xianxiacraft.xianxiacraft.QiManagers.QiManager.getQi;
import static xianxiacraft.xianxiacraft.QiManagers.QiManager.subtractQi;
import static xianxiacraft.xianxiacraft.QiManagers.TechniqueManager.getPunchBool;
import static xianxiacraft.xianxiacraft.handlers.Manuals.VampireManual.demonicManualManualPointIncrement;

public class HitEvents implements Listener {

    private XianxiaCraft plugin;
    FreezeEffect freezeEffect;
    public HitEvents(XianxiaCraft plugin){
        Bukkit.getPluginManager().registerEvents(this,plugin);
        this.plugin = plugin;
        freezeEffect = new FreezeEffect(plugin);
    }


    //this entire event is just fungal manual corruption
    @EventHandler
    public void onPlayerHitBlock(PlayerInteractEvent event){

        Player player = (Player) event.getPlayer();

        if(getManual(player).equals("Fungal Manual")) {
            if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                if (getPunchBool(player) && getQi(player) >= 10) {
                    subtractQi(player, 10);
                    ScoreboardManager1.updateScoreboard(player);

                    int radius = getStage(player);

                    Block clickedBlock = event.getClickedBlock();
                    if (clickedBlock != null) {
                        for (int x = -radius; x <= radius; x++) {
                            for (int y = -radius; y <= radius; y++) {
                                for (int z = -radius; z <= radius; z++) {
                                    Block targetBlock = clickedBlock.getRelative(x, y, z);
                                    if (targetBlock.getType() != Material.AIR) {
                                        targetBlock.setType(Material.MYCELIUM);
                                    }
                                }
                            }
                        }
                    }
                } else if(getPunchBool(player)){
                    player.sendMessage(ChatColor.GOLD + "You did not have enough qi to augment your punch.");
                }
            }
        }
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
                switch (attackingPlayerManual) {
                    case "Ironskin Manual":
                        attackDamage += (2 * attackingPlayerStage);

                        break;
                    case "Fatty Manual": {
                        LivingEntity target = (LivingEntity) event.getEntity();
                        Vector knockbackDirection = target.getLocation().toVector().subtract(attackingPlayer.getLocation().toVector()).normalize();
                        double knockbackStrength = 1.5 * attackingPlayerStage; // Adjust this value to control the knockback strength
                        double knockbackVertical = 0.4 * attackingPlayerStage; // Adjust this value to control the vertical knockback
                        Vector knockbackVelocity = knockbackDirection.multiply(knockbackStrength).setY(knockbackVertical);
                        target.setVelocity(knockbackVelocity);
                        attackDamage += (2 * attackingPlayerStage);

                        break;
                    }
                    case "Ice Manual": {
                        LivingEntity target = (LivingEntity) event.getEntity();
                        //freezeEffect.applyFreezeDamage(target,attackingPlayer);
                        target.setFreezeTicks(20 * 15 * getStage(attackingPlayer));
                        attackDamage += attackingPlayerStage;
                        break;
                    }
                    case "Phoenix Manual": {
                        LivingEntity target = (LivingEntity) event.getEntity();
                        target.setFireTicks(20 * 7 * getStage(attackingPlayer));
                        attackDamage += attackingPlayerStage;
                        break;
                    }
                    case "Poison Manual": {
                        LivingEntity target = (LivingEntity) event.getEntity();
                        target.addPotionEffect(new PotionEffect(PotionEffectType.POISON,20*5,getStage(attackingPlayer)));
                        target.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,20*5,getStage(attackingPlayer)));
                        break;
                    }
                    case "Space Manual": {
                        LivingEntity target = (LivingEntity) event.getEntity();
                        target.teleport(SpaceManual.getRandomLocationWithinRadius(target.getLocation(),15*attackingPlayerStage));
                        break;
                    }
                    case "Sugar Fiend": {
                        attackDamage += (2*attackingPlayerStage);
                        break;
                    }
                    case "Demonic Manual": {
                        //increase health by half a heart
                        attackingPlayer.setHealth(attackingPlayer.getHealth()+1);
                        break;
                    }
                    case "LightningManual": {
                        LivingEntity target = (LivingEntity) event.getEntity();
                        target.getWorld().strikeLightning(target.getLocation());
                        attackDamage += 2*attackingPlayerStage;
                        break;
                    }
                }
            } else if(getPunchBool(attackingPlayer)){
                attackingPlayer.sendMessage(ChatColor.GOLD + "You did not have enough qi to augment your punch.");

            }

            //fungal manual buff if in biome
            if(CountNearbyBlocks.countNearbyBlocks(attackingPlayer,Material.MYCELIUM)>=1){
                attackDamage *= 2;
            }


            //check for if target is player or not and respond accordingly
            if (!(event.getEntity() instanceof Player)) {
                event.setDamage(attackDamage);
                if(attackingPlayerManual.equals("Demonic Manual")){
                    demonicManualManualPointIncrement(attackingPlayer,(LivingEntity) event.getEntity());
                }
                return;
            }

            Player defendingPlayer = (Player) event.getEntity();
            String defendingPlayerManual = getManual(defendingPlayer);
            int defendingPlayerStage = getStage(defendingPlayer);

            double defense = defendingPlayerStage * getManualDefensePerStage(defendingPlayerManual);

            double resultAttackDamage = attackDamage - defense;



            if (resultAttackDamage > 0) {
                event.setDamage(resultAttackDamage+1);
            } else {
                //minimum attack
                event.setDamage(1);
            }

            //demonic manual leeching
            if((defendingPlayer.getHealth()-event.getFinalDamage()) <= 0 && attackingPlayerManual.equals("Demonic Manual")){
                int points1 = getPoints(defendingPlayer);
                int stage1 = getStage(defendingPlayer);

                //var1 is the points needed to get from stage 0 to current stage-1
                int variable1 = (int) (20 * Math.pow(2, (stage1))) - 20;
                int leeched = (points1 - variable1-1);

                setPoints(defendingPlayer,points1-leeched);
                addPoints(attackingPlayer,leeched);

            }



            //debug statements
            /*
            Bukkit.getLogger().info("AttackingPlayerManual: " + attackingPlayerManual);
            Bukkit.getLogger().info("AttackingPlayerStage: " + attackingPlayerStage);
            Bukkit.getLogger().info("AttackPerStage: " + getManualAttackPerStage(attackingPlayerManual));
            Bukkit.getLogger().info("Attack dmg: " + attackDamage);

            Bukkit.getLogger().info("DefendingPlayerManual: " + defendingPlayerManual);
            Bukkit.getLogger().info("DefendingPlayerStage: " + defendingPlayerStage);
            Bukkit.getLogger().info("DefensePerStage: " + getManualDefensePerStage(defendingPlayerManual));
            Bukkit.getLogger().info("Defense: " + defense);
            Bukkit.getLogger().info("Resulting dmg: " + resultAttackDamage);
            Bukkit.getLogger().info("setDamage: " + event.getDamage());
            Bukkit.getLogger().info("getFinalDamage: " + event.getFinalDamage() + "\n");
            */


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

        if(defendingPlayerManual.equals("Phoenix Manual") && defendingPlayer.getFireTicks() > 0 ){
            event.setCancelled(true);
            return;
        }

        if(defendingPlayerManual.equals("Ice Manual") && defendingPlayer.getFreezeTicks() > 0){
            event.setCancelled(true);
            return;
        }

        if(defendingPlayerManual.equals("Poison Manual") && event.getCause() == EntityDamageEvent.DamageCause.POISON){
            event.setCancelled(true);
            return;
        }

        if(defendingPlayerManual.equals("LightningManual") && event.getCause() == EntityDamageEvent.DamageCause.LIGHTNING){
            event.setCancelled(true);
            return;
        }

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
