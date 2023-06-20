package xianxiacraft.xianxiacraft.handlers;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.player.PlayerInteractEvent;

import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import xianxiacraft.xianxiacraft.XianxiaCraft;

import static xianxiacraft.xianxiacraft.QiManagers.ManualManager.getManual;
import static xianxiacraft.xianxiacraft.QiManagers.PointManager.getStage;
import static xianxiacraft.xianxiacraft.QiManagers.QiManager.getQi;
import static xianxiacraft.xianxiacraft.QiManagers.QiManager.subtractQi;
import static xianxiacraft.xianxiacraft.QiManagers.ScoreboardManager1.updateScoreboard;
import static xianxiacraft.xianxiacraft.QiManagers.TechniqueManager.getMoveBool;
import static xianxiacraft.xianxiacraft.util.CountNearbyBlocks.countNearbyBlocks;
import static xianxiacraft.xianxiacraft.util.FreezeEffect.createIce;
import static xianxiacraft.xianxiacraft.util.FreezeEffect.removeIce;


public class MoveEvents implements Listener {

    XianxiaCraft plugin;

    public MoveEvents(XianxiaCraft plugin){
        Bukkit.getPluginManager().registerEvents(this,plugin);
        this.plugin = plugin;
    }


    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();

        if(getMoveBool(player)){
            String playerManual = getManual(player);

            /*if(playerManual.equals("Fungal Manual")){

                if(countNearbyBlocks(player, Material.MYCELIUM,2) > 0){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 40,1,false,false,false));
                }

            } else */

            if(playerManual.equals("Ice Manual")){
                if(!player.isSneaking()) {

                    Location[] locations = new Location[9];

                    Location playerLocation = player.getLocation();
                    Location baseLocation = playerLocation.subtract(1, 1, 1);

                    int index = 0;
                    for (int x = 0; x < 3; x++) {
                        for (int z = 0; z < 3; z++) {
                            locations[index] = baseLocation.clone().add(x, 0, z);
                            index++;
                        }
                    }


                    /*
                    Location[] locations = new Location[7];

                    Location location1 = player.getLocation().subtract(0, 1, 0);

                    locations[0] = player.getLocation().subtract(0, 1, 0);
                    locations[1] = player.getLocation().subtract(1, 1, 0);
                    locations[2] = player.getLocation().subtract(0, 1, 1);
                    locations[3] = location1.add(1, 0, 0);
                    locations[4] = location1.add(0, 0, 1);
                    locations[5] = location1.add(1,0,1);
                    locations[6] = player.getLocation().subtract(1,1,1);
                    */
                    for (Location location : locations) {
                        createIce(location);
                        Bukkit.getScheduler().runTaskLater(plugin, () -> removeIce(location), 100);
                    }
                }
            }

        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event){

        Player player = event.getPlayer();

        if(getMoveBool(player)) {
            String playerManual = getManual(player);
            switch (playerManual) {
                case "Space Manual":
                    if (event.getAction().toString().contains("RIGHT_CLICK")) { //SPACE MANUAL TP DASH
                        if (!(getQi(player) >= 50)) {
                            player.sendMessage(ChatColor.GOLD + "You do not have enough qi to teleport.");
                            return;
                        }
                        subtractQi(player, 50);
                        updateScoreboard(player);

                        Vector direction = player.getLocation().getDirection();

                        double teleportDistance = 3 * getStage(player) + 5;

                        Vector teleportVector = direction.multiply(teleportDistance);
                        player.teleport(player.getLocation().add(teleportVector));
                    }
                    break;
                case "LightningManual":  // LIGHTNING MANUAL TP DASH
                    if (event.getAction().toString().contains("RIGHT_CLICK")) {
                        if (!(getQi(player) >= 20)) {
                            player.sendMessage(ChatColor.GOLD + "You do not have enough qi to dash.");
                            return;
                        }
                        subtractQi(player, 20);
                        updateScoreboard(player);

                        Vector direction = player.getLocation().getDirection();
                        direction.setY(0).normalize(); //so they cant fly

                        double teleportDistance = 3 * getStage(player) + 5;

                        Block blockingBlock = null;

                        // Check for any blocks in the teleport path
                        for (double distance = 0; distance <= teleportDistance; distance += 0.5) {
                            Vector checkPosition = player.getEyeLocation().toVector().add(direction.clone().multiply(distance));
                            Location checkLocation = checkPosition.toLocation(player.getWorld());
                            Block block = checkLocation.getBlock();
                            if (!block.isPassable()) {
                                blockingBlock = block;
                                break; //break loop if tp is blocked
                            }
                        }

                        if (blockingBlock != null) {
                            Block highestSolidBlock = null;
                            for (int y = blockingBlock.getY() + 1; y <= player.getWorld().getMaxHeight(); y++) {
                                Block aboveBlock = blockingBlock.getWorld().getBlockAt(blockingBlock.getX(), y, blockingBlock.getZ());
                                if (aboveBlock.getType().isSolid()) {
                                    highestSolidBlock = aboveBlock;
                                } else {
                                    break;
                                }
                            }

                            if (highestSolidBlock != null) {
                                player.teleport(highestSolidBlock.getLocation());
                                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1.0f, 1.0f);
                            }
                        } else {
                            Vector teleportVector = direction.multiply(teleportDistance);
                            player.teleport(player.getLocation().add(teleportVector));
                            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1.0f, 1.0f);
                        }
                    }
                    break;

                case "Phoenix Manual":
                    if (event.getAction().toString().contains("RIGHT_CLICK")) { //SPACE MANUAL TP DASH
                        if (!(getQi(player) >= 30)) {
                            player.sendMessage(ChatColor.GOLD + "You do not have enough qi to dash.");
                            return;
                        }
                        subtractQi(player, 30);
                        updateScoreboard(player);

                        Vector direction = player.getLocation().getDirection();
                        direction.setY(1).normalize(); //to launch into air

                        double strength = 1 + getStage(player)/3.0; //pushing strength scales with stage
                        Vector velocity = direction.multiply(strength);
                        //velocity.setY(1); //pushing height

                        player.setVelocity(velocity);
                        break;
                    }
                case "Poison Manual":
                case "Demonic Manual":
                case "Ironskin Manual":
                case "Sugar Fiend":
                    if (event.getAction().toString().contains("RIGHT_CLICK")) {
                        if (!(getQi(player) >= 20)) {
                            player.sendMessage(ChatColor.GOLD + "You do not have enough qi to dash.");
                            return;
                        }
                        subtractQi(player, 20);
                        updateScoreboard(player);

                        Vector direction = player.getLocation().getDirection();
                        direction.setY(0).normalize(); //so they don't fly

                        double strength = 1 + getStage(player)/3.0; //pushing strength scales with stage
                        Vector velocity = direction.multiply(strength);
                        //velocity.setY(1); //pushing height

                        player.setVelocity(velocity);
                        break;
                    }
            } //end of switch statement

        }
    }




}
