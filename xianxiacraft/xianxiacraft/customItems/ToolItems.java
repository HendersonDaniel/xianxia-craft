package xianxiacraft.xianxiacraft.customItems;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import xianxiacraft.xianxiacraft.XianxiaCraft;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ToolItems {

    public static ItemStack taijiPaintingItem;
    public static ItemStack superHoeItem;

    private static XianxiaCraft plugin;

    public ToolItems(XianxiaCraft plugin){
        ToolItems.plugin = plugin;
    }

    public static void init2(){
        createSuperHoe();
        createTaijiPainting();
    }

    private static void createTaijiPainting(){
        ItemStack item = new ItemStack(Material.PAINTING);

        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.setDisplayName(ChatColor.GOLD + "Taiji Painting");


        List<String> lore = new ArrayList<>();
        lore.add("The Supreme Painting that");
        lore.add("hides one from the heavens.");
        lore.add("Don't place it. The heavens");
        lore.add("might take it back.");
        itemMeta.setLore(lore);

        itemMeta.addEnchant(Enchantment.LUCK,1,false);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS,ItemFlag.HIDE_ATTRIBUTES);

        NamespacedKey key = new NamespacedKey(plugin, "customUtilityTools");
        itemMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING,"taijiPainting");

        item.setItemMeta(itemMeta);

        taijiPaintingItem = item;
    }


    private static void createSuperHoe(){
        ItemStack item = new ItemStack(Material.WOODEN_HOE);

        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.setDisplayName(ChatColor.GOLD + "True Essence Hoe");


        List<String> lore = new ArrayList<>();
        lore.add("A hoe containing a");
        lore.add("drop of true essence.");

        itemMeta.setLore(lore);

        itemMeta.addEnchant(Enchantment.MENDING,1,false);
        itemMeta.addEnchant(Enchantment.LUCK,10,false);
        itemMeta.addEnchant(Enchantment.DURABILITY,10,false);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS,ItemFlag.HIDE_ATTRIBUTES);

        NamespacedKey key = new NamespacedKey(plugin, "customUtilityTools");
        itemMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING,"superHoe");

        item.setItemMeta(itemMeta);

        superHoeItem = item;
    }



}
