package xianxiacraft.xianxiacraft.QiManagers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.Yaml;
import xianxiacraft.xianxiacraft.XianxiaCraft;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ManualManager implements Listener {

    private final JavaPlugin plugin;

    public static Map<UUID, String> manualsMap = new HashMap<>();

    public ManualManager(XianxiaCraft plugin){
        Bukkit.getPluginManager().registerEvents(this,plugin);
        this.plugin = plugin;
    }

    public static String getManual(Player player){
        return manualsMap.getOrDefault(player.getUniqueId(),"none");
    }


    //needs to be fixed so that on right click of a MANUAL it changes the string manual. I think it is fixed
    @EventHandler
    public void onManualChange(PlayerInteractEvent event) {
        ItemStack itemInHand = event.getPlayer().getInventory().getItemInMainHand();

        // Check if the item in hand is a written book
        if (itemInHand.getType() == Material.WRITTEN_BOOK) {
            BookMeta bookMeta = (BookMeta) itemInHand.getItemMeta();

            // Check if the book has a specific title
            if (bookMeta != null && bookMeta.hasTitle() && bookMeta.hasAuthor()) {
                String bookTitle = bookMeta.getTitle();
                String bookAuthor = bookMeta.getAuthor();

                // Check if the book title matches a specific technique manual
                if(bookAuthor.equals("Spellslot")){
                    if(!(bookTitle.equals(manualsMap.get(event.getPlayer().getUniqueId())))){
                        manualsMap.put(event.getPlayer().getUniqueId(),bookTitle);
                        PointManager.setPoints(event.getPlayer(),1);
                        QiManager.setQi(event.getPlayer(),0);
                        event.getPlayer().sendMessage("Cultivation Manual changed to " + bookTitle + ".\nCultivation has been reset.");
                        // Prevent the book from being opened when right-clicked
                        event.setCancelled(true);
                    }
                }

            }
        }
    }

    public void saveManualData() {
        File dataFolder = plugin.getDataFolder();
        File dataFile = new File(dataFolder, "player_manual_data.yml");
        try {
            if (!dataFile.exists()) {
                dataFolder.mkdirs();
                dataFile.createNewFile();
            }

            FileWriter writer = new FileWriter(dataFile);
            Yaml yaml = new Yaml();
            yaml.dump(manualsMap, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadManualData() {
        File dataFile = new File(plugin.getDataFolder(), "player_manual_data.yml");
        try {
            if (dataFile.exists()) {
                FileReader reader = new FileReader(dataFile);
                Yaml yaml = new Yaml();
                manualsMap = yaml.loadAs(reader, HashMap.class);
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
