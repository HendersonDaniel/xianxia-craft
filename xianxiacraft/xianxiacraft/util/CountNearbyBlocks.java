package xianxiacraft.xianxiacraft.util;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class CountNearbyBlocks {


    public static int countNearbyBlocks(Player player, Material material) {
        int playerX = player.getLocation().getBlockX();
        int playerY = player.getLocation().getBlockY();
        int playerZ = player.getLocation().getBlockZ();

        Set<Block> blocks = new HashSet<>();
        int radius = 10;

        for (int x = playerX - radius; x <= playerX + radius; x++) {
            for (int y = playerY - radius; y <= playerY + radius; y++) {
                for (int z = playerZ - radius; z <= playerZ + radius; z++) {
                    Block block = player.getWorld().getBlockAt(x, y, z);
                    if (block.getType() == material) {
                        blocks.add(block);
                    }
                }
            }
        }

        return blocks.size();
    }


    public static int countNearbyBlocks(Player player, Material material,int radius) {
        int playerX = player.getLocation().getBlockX();
        int playerY = player.getLocation().getBlockY();
        int playerZ = player.getLocation().getBlockZ();

        Set<Block> blocks = new HashSet<>();

        for (int x = playerX - radius; x <= playerX + radius; x++) {
            for (int y = playerY - radius; y <= playerY + radius; y++) {
                for (int z = playerZ - radius; z <= playerZ + radius; z++) {
                    Block block = player.getWorld().getBlockAt(x, y, z);
                    if (block.getType() == material) {
                        blocks.add(block);
                    }
                }
            }
        }

        return blocks.size();
    }
}


