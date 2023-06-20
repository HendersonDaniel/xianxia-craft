package xianxiacraft.xianxiacraft.util;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;

public class FungalBlockData {

    private static ArrayList<Material> fungalToMycelium = new ArrayList<Material>(Arrays.asList(Material.DIRT,Material.DIRT_PATH,Material.COARSE_DIRT,Material.ROOTED_DIRT,Material.STONE,Material.END_STONE,Material.SMOOTH_STONE,Material.GRASS_BLOCK,Material.SAND,Material.RED_SAND,Material.GRAVEL,Material.NETHERRACK));
    private static ArrayList<Material> fungalToMushroom = new ArrayList<Material>(Arrays.asList(Material.ACACIA_LEAVES,Material.AZALEA_LEAVES,Material.BIRCH_LEAVES,Material.FLOWERING_AZALEA_LEAVES,Material.JUNGLE_LEAVES,Material.MANGROVE_LEAVES,Material.OAK_LEAVES,Material.DARK_OAK_LEAVES,Material.SPRUCE_LEAVES));
    private static ArrayList<Material> fungalToStem = new ArrayList<Material>(Arrays.asList(Material.ACACIA_LOG,Material.BIRCH_LOG,Material.JUNGLE_LOG,Material.MANGROVE_LOG,Material.DARK_OAK_LOG,Material.OAK_LOG,Material.SPRUCE_LOG));
    private static ArrayList<Material> fungalToMushroomFlower = new ArrayList<Material>(Arrays.asList(Material.POPPY,Material.OXEYE_DAISY,Material.ORANGE_TULIP,Material.PINK_TULIP,Material.CORNFLOWER,Material.ALLIUM,Material.RED_TULIP,Material.WHITE_TULIP,Material.DANDELION,Material.AZURE_BLUET));


    public static boolean checkToMushroomFlower(Material blockToBeChecked) {
        for(Material m : fungalToMushroomFlower){
            if(m == blockToBeChecked){
                return true;
            }
        }
        return false;
    }
    public static boolean checkMycelium(Material blockToBeChecked) {
        for(Material m : fungalToMycelium){
            if(m == blockToBeChecked){
                return true;
            }
        }
        return false;
    }
    public static boolean checkMushroom(Material blockToBeChecked) {
        for(Material m : fungalToMushroom){
            if(m == blockToBeChecked){
                return true;
            }
        }
        return false;
    }
    public static boolean checkStem(Material blockToBeChecked) {
        for(Material m : fungalToStem){
            if(m == blockToBeChecked){
                return true;
            }
        }
        return false;
    }



}
