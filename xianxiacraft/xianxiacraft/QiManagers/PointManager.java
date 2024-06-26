package xianxiacraft.xianxiacraft.QiManagers;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;

import java.util.*;
import java.lang.Math;

import static xianxiacraft.xianxiacraft.QiManagers.ManualManager.getManual;

public class PointManager implements Listener {

    private static Map<UUID, Integer> pointMap = new HashMap<>();
    public static Map<UUID, Set<String>> daoAttainmentMap = new HashMap<>(); // number of strings in the array will be the modifier
    private final JavaPlugin plugin;

    public PointManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }


    public static int getMaxQi(Player player){
        return (int) (10 * Math.pow(2,PointManager.getStage(player)));
    }

    //method to get stage of a cultivator. is part of a logarithmic function
    public static int getStage(Player player){
        int points = (pointMap.getOrDefault(player.getUniqueId(),0));
        //return (int) Math.floor(Math.log(points));
        //this goes 0,20,40,80,etc
        return (int) (Math.log((points / 20.0) + 1) / Math.log(2));
    }

    // Method to retrieve points for a player
    public static int getPoints(Player player) {
        return pointMap.getOrDefault(player.getUniqueId(), 0);
    }

    //Method to add points
    public static void addPoints(Player player, int points) {
        UUID id = player.getUniqueId();
        pointMap.put(id, getPoints(player) + points);
        if(getStage(player) >= 10){
            Set<String> t = daoAttainmentMap.getOrDefault(id,new HashSet<>());
            if(t.add(getManual(player))){
                player.sendMessage(ChatColor.DARK_PURPLE + "You have achieved a Dao Attainment.");
                player.playSound(player.getLocation(), Sound.BLOCK_END_PORTAL_SPAWN, 1.0F, 1.0F);
            }
            daoAttainmentMap.put(id,t);
        }
    }
    public static void addPointsWithoutDaoCheck(Player player, int points) {
        pointMap.put(player.getUniqueId(), getPoints(player) + points);
    }

    // Method to set points for a player
    public static void setPoints(Player player, int points) {
        pointMap.put(player.getUniqueId(), points);
    }


    //fix this
    public static double percentToNextStage(Player player) {
        int points = getPoints(player);
        int stage = getStage(player);

        //percent = 100*(myManager.getPoints() / (20 * Math.pow(10, (myManager.getStage()+1) * Math.log10(2)) - 20));

        //okay equation for progress:
        // 100 * (points - (20 * Math.pow(10,(stage) * Math.log10(2)) - 20)) / ((20 * Math.pow(10,(stage+1) * Math.log10(2)) - 20) - (20 * Math.pow(10,(stage) * Math.log10(2)) - 20))
        // (points - previous stage total points) / (next stage points - prevous stage points)

        //working but not to the degree I want
        //double percentage = 100*(points/(20 * Math.pow(10,(stage+1) * Math.log10(2)) - 20));

        double variable1 = 20 * Math.pow(2, stage) - 20;
        double percentage = 100 * (points - variable1) / ((20 * Math.pow(10,(stage+1) * Math.log10(2)) - 20) - variable1);


        return Math.round(percentage * 100.0) / 100.0;
    }





    public void savePointData() {
        File dataFolder = plugin.getDataFolder();
        File dataFile = new File(dataFolder, "point_data.yml");
        try {
            if (!dataFile.exists()) {
                dataFolder.mkdirs();
                dataFile.createNewFile();
            }

            FileWriter writer = new FileWriter(dataFile);
            Yaml yaml = new Yaml();
            yaml.dump(pointMap, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadPointData() {
        File dataFile = new File(plugin.getDataFolder(), "point_data.yml");
        try {
            if (dataFile.exists()) {
                FileReader reader = new FileReader(dataFile);
                LoaderOptions loaderOptions = new LoaderOptions();
                loaderOptions.setTagInspector(tag -> true);
                Yaml yaml = new Yaml(loaderOptions);
                pointMap = yaml.loadAs(reader, HashMap.class);
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveDaoAttainmentData() {
        File dataFolder = plugin.getDataFolder();
        File dataFile = new File(dataFolder, "dao_attainment_data.yml");
        try {
            if (!dataFile.exists()) {
                dataFolder.mkdirs();
                dataFile.createNewFile();
            }

            FileWriter writer = new FileWriter(dataFile);
            Yaml yaml = new Yaml();
            yaml.dump(daoAttainmentMap, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadDaoAttainmentData() {
        File dataFile = new File(plugin.getDataFolder(), "dao_attainment_data.yml");
        try {
            if (dataFile.exists()) {
                FileReader reader = new FileReader(dataFile);
                LoaderOptions loaderOptions = new LoaderOptions();
                loaderOptions.setTagInspector(tag -> true);
                Yaml yaml = new Yaml(loaderOptions);
                daoAttainmentMap = yaml.loadAs(reader, HashMap.class);
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
