package xianxiacraft.xianxiacraft.runnables;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static xianxiacraft.xianxiacraft.QiManagers.ManualManager.getManual;
import static xianxiacraft.xianxiacraft.QiManagers.PointManager.getStage;

public class RunAura {
    private final JavaPlugin plugin;
    private final Player player;
    private int angle = 0;
    private BukkitRunnable currentTask;
    private List<List<FallingBlock>> auraBlockList = new ArrayList<>();

    private final Random random = new Random();

    public RunAura(JavaPlugin plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
    }


    public void start() {

        int playerStage = getStage(player);
        String playerManual = getManual(player);

        int numberOfRings = playerStage/2;

        double radius = 2 + playerStage / 2.0;
        int numberOfBlocks = (int) Math.ceil(3.141 * 2 * radius); // per ring

        for(int i = 0; i < numberOfRings; i++){
            auraBlockList.add(new ArrayList<FallingBlock>());
        }


        currentTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline()) {
                    this.cancel();
                    return;
                }

                Location center = player.getLocation();

                for (int ring = 0; ring < numberOfRings; ring++) {
                    for (int i = 0; i < numberOfBlocks; i++) {

                        double angleIncrement = 360.0 / numberOfBlocks;
                        double rad = Math.toRadians(angle + (ring*(360.0/numberOfRings)) + angleIncrement * i);

                        double ringAngle = Math.toRadians(angle + (360.0/numberOfRings) * ring);

                        double x;
                        double y;
                        double z;

                        if(ring % 2 == 0){
                            x = center.getX() + (radius * Math.cos(rad));
                            y = center.getY() + (radius * Math.sin(rad) * Math.sin(ringAngle));
                            z = center.getZ() + (radius * Math.sin(rad) * Math.cos(ringAngle));
                        } else {
                            x = center.getX() + (radius * Math.cos(rad) * Math.sin(ringAngle));
                            y = center.getY() + (radius * Math.sin(rad));
                            z = center.getZ() + (radius * Math.cos(rad) * Math.cos(ringAngle));
                        }




                        Location loc = new Location(center.getWorld(), x, y, z);

                        //remove old block
                        if (i < auraBlockList.get(ring).size()) {
                            FallingBlock block = auraBlockList.get(ring).get(i);
                            if (block != null && !block.isDead()) {
                                block.remove();
                            }
                        }

                        Material blockType1;
                        Material blockType2;

                        switch(playerManual){
                            case "Ice Manual":
                                blockType1 = Material.PACKED_ICE;
                                blockType2 = Material.BLUE_ICE;
                                break;
                            case "Sugar Fiend":
                                blockType1 = Material.NETHERRACK;
                                blockType2 = Material.NETHER_QUARTZ_ORE;
                                break;
                            case "Fatty Manual":
                                blockType1 = Material.DIRT;
                                blockType2 = Material.COARSE_DIRT;
                                break;
                            case "Fungal Manual":
                                blockType1 = Material.MYCELIUM;
                                blockType2 = Material.MYCELIUM;
                                break;
                            case "Ironskin Manual":
                                blockType1 = Material.RAW_IRON_BLOCK;
                                blockType2 = Material.IRON_BLOCK;
                                break;
                            case "LightningManual":
                                blockType1 = Material.RAW_COPPER_BLOCK;
                                blockType2 = Material.COPPER_BLOCK;
                                break;
                            case "Phoenix Manual":
                                blockType1 = Material.HONEYCOMB_BLOCK;
                                blockType2 = Material.MAGMA_BLOCK;
                                break;
                            case "Poison Manual":
                                blockType1 = Material.WARPED_HYPHAE;
                                blockType2 = Material.MANGROVE_ROOTS;
                                break;
                            case "Space Manual":
                                blockType1 = Material.BLACK_CONCRETE;
                                blockType2 = Material.END_STONE;
                                break;
                            case "Demonic Manual":
                                blockType1 = Material.SCULK;
                                blockType2 = Material.SCULK;
                                break;
                            default:
                                blockType1 = Material.AIR;
                                blockType2 = Material.AIR;
                        }

                        int randomNumber = random.nextInt(2);

                        Material endBlockType;

                        if(randomNumber==0){
                            endBlockType = blockType1;
                        } else {
                            endBlockType = blockType2;
                        }

                        if (Objects.requireNonNull(center.getWorld()).getBlockAt(loc).getType() == Material.AIR || center.getWorld().getBlockAt(loc).getType() == Material.CAVE_AIR) {
                            FallingBlock auraBlock = Objects.requireNonNull(center.getWorld()).spawnFallingBlock(loc, endBlockType.createBlockData());
                            auraBlock.setDropItem(false);
                            auraBlock.setGravity(false);

                            if (i < auraBlockList.get(ring).size()) {
                                auraBlockList.get(ring).set(i, auraBlock); // update existing entry
                            } else {
                                auraBlockList.get(ring).add(auraBlock); // add new block to the list
                            }
                        }
                    }
                }

                angle = (angle + 10) % 360; // Increment angle for revolving effect
            }
        };
        currentTask.runTaskTimer(plugin, 0L, 3L);
    }

    public void stop() {
        if (currentTask != null && !currentTask.isCancelled()) {
            currentTask.cancel();
        }

        for(List<FallingBlock> list : auraBlockList){
            for (FallingBlock block : list) {
                if (block != null && !block.isDead()) {
                    block.remove();
                }
            }
        }
        auraBlockList = new ArrayList<>();

    }
}
