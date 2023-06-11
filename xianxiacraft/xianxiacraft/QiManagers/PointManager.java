package xianxiacraft.xianxiacraft.QiManagers;

import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.lang.Math;

public class PointManager implements Listener {

    private static Map<UUID, Integer> pointMap = new HashMap<>();
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
        int stageStartPoints = 20 * (int) Math.pow(2, stage - 1);
        int stageEndPoints = (20 * (int) Math.pow(2, stage)) - 1;
        double stagePointsRange = stageEndPoints - stageStartPoints + 1; // Add 1 to include the end point
        double progressWithinStage = points - stageStartPoints;
        double percentage = (progressWithinStage / stagePointsRange) * 100;

        return Math.round(percentage * 100.0) / 100.0;
    }

    /*
    public static double percentToNextStage(Player player) {

        int points = getPoints(player);
        int stage = getStage(player);
        int stageStartPoints = 20 * (int) Math.pow(2, stage - 1);
        int stageEndPoints = (20 * (int) Math.pow(2, stage)) - 1;
        double stagePointsRange = stageEndPoints - stageStartPoints;
        double progressWithinStage = points - stageStartPoints;
        double percentage = (progressWithinStage / stagePointsRange) * 100;

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
                Yaml yaml = new Yaml();
                pointMap = yaml.loadAs(reader, HashMap.class);
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
