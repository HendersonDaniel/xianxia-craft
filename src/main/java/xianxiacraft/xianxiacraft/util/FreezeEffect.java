package xianxiacraft.xianxiacraft.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import xianxiacraft.xianxiacraft.XianxiaCraft;

import static xianxiacraft.xianxiacraft.QiManagers.PointManager.getStage;

public class FreezeEffect {

    private static XianxiaCraft plugin;

    public FreezeEffect(XianxiaCraft plugin){
        this.plugin = plugin;
    }

    private static final int FREEZE_DURATION_PER_STAGE = 7; //7 sec per stage

    public static void createIce(Location location) {
        Block block = location.getBlock();
        if(block.getType() == Material.AIR || block.getType() == Material.CAVE_AIR){
            block.setType(Material.ICE);
        }
    }

    public static void removeIce(Location location) {
        Block block = location.getBlock();
        if (block.getType() == Material.ICE) {
            block.setType(Material.AIR);
        }
    }

    public void applyFreezeDamage(LivingEntity target, Player attacker) {
        // Store the player's current location
        Location playerLocation = target.getLocation();

        // Set the block appearance to powdered snow
        Material ice = Material.PACKED_ICE;
        int radius = 1;

        World world = target.getWorld();

        // Change the block appearance around the player
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                for (int y = -radius; y <= radius; y++) {
                    Location blockLocation = playerLocation.clone().add(x, y+1, z);
                    if (blockLocation.getBlock().getType() != ice) {
                        world.getBlockAt(blockLocation).setType(ice);
                    }
                }
            }
        }


        // Schedule a task to revert the block appearance back to normal after the duration
        new BukkitRunnable() {
            @Override
            public void run() {
                for (int x = -radius-1; x <= radius+1; x++) {
                    for (int z = -radius-1; z <= radius+1; z++) {
                        for(int y = -radius-1; y <= radius+1; y++){
                            Location blockLocation = playerLocation.clone().add(x, y+1, z);
                            if (blockLocation.getBlock().getType() == ice) {
                                world.getBlockAt(blockLocation).setType(Material.AIR);
                            }
                        }
                    }
                }
            }
        }.runTaskLater(plugin, FREEZE_DURATION_PER_STAGE * getStage(attacker) * 20L);
    }
}
