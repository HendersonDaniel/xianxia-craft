package xianxiacraft.xianxiacraft.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class ManualItems {

    public static ItemStack fattyManualItem;
    public static ItemStack iceManualItem;
    public static ItemStack ironSkinManualItem;
    public static ItemStack phoenixManualItem;
    public static ItemStack poisonManualItem;
    public static ItemStack spaceManualItem;
    public static ItemStack sugarFiendManualItem;
    public static ItemStack demonicManualItem;
    public static ItemStack lightningManualItem;
    public static ItemStack fungalManualItem;
    public static ItemStack tutorialBookItem;

    public static void init(){
        createFattyManualItem();
        createIceManualItem();
        createIronSkinManualItem();
        createPhoenixManualItem();
        createPoisonManualItem();
        createSpaceManualItem();
        createSugarFiendManualItem();
        createDemonicManualItem();
        createLightningManualItem();
        createFungalManualItem();
        createTutorialBookItem();
    }

    private static void createTutorialBookItem(){
        ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) item.getItemMeta();


        assert bookMeta != null;
        bookMeta.setTitle("Tutorial Book");
        bookMeta.setAuthor("Anonymous");
        bookMeta.setDisplayName("A Beginner's Guide to Cultivation");
        bookMeta.addPage("Welcome to Spellslot's (Daniel's) XianxiaCraft!\n" +
                "\n" +
                "To get started, find and use a Cultivation Manual! They have a chance of dropping from certain mobs like:\n" +
                "Pigs,Piglins,Blazes,Mooshroom Cows,Villagers,Iron Golems,Cave Spiders,Endermen,Strays,Creepers.");
        bookMeta.addPage("After you have a manual, just follow it!\n" +
                "\n" +
                "The chances of a manual dropping from a mob are currently 2%. \n" +
                "\n" +
                "This is a test run so that I can understand the power level of the manuals currently.");
        bookMeta.addPage("This is still a work in progress. Please report any bugs to danialdamanual on discord.");

        item.setItemMeta(bookMeta);
        tutorialBookItem = item;
    }

    private static void createFungalManualItem(){
        ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) item.getItemMeta();


        assert bookMeta != null;
        bookMeta.setTitle("Fungal Manual");
        bookMeta.setAuthor("Spellslot");
        bookMeta.setDisplayName("Mycelium Chronicle");
        bookMeta.addPage("The True Fungus conquers all. \n" +
                "\n" +
                "Eat suspicious stew to harness your fungal powers.\n" +
                "\n" +
                "To breakthrough, you must be near Mycelium blocks. \n" +
                "\n" +
                "You do double damage near Mycelium blocks.");
        bookMeta.addPage("Stage 1:\n" +
                "\n" +
                "You can now expell your qi into the land around you, converting it into Mycelium. The radius of the change increases as you progress in stage.\n" +
                "\n" +
                "Use /qipunch to toggle this technique. Only works on blocks.");
        bookMeta.addPage("more techniques will be added later");

        item.setItemMeta(bookMeta);
        fungalManualItem = item;
    }

    private static void createLightningManualItem(){
        ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) item.getItemMeta();


        assert bookMeta != null;
        bookMeta.setTitle("LightningManual");
        bookMeta.setAuthor("Spellslot");
        bookMeta.setDisplayName("Lightning Emperor's Legacy");
        bookMeta.addPage("Right now you are below the Heavens, but one day, you will BE the Heavens. \n" +
                "\n" +
                "Absorb the essence of copper ingots to increase your cultivation. \n" +
                "\n" +
                "To breakthrough, be near copper blocks. Requirement doubles every stage.");
        bookMeta.addPage("Stage 1:\n" +
                "\n" +
                "You can now imbue your qi into your attacks, calling lightning from the sky on your enemies. \n" +
                "\n" +
                "Toggle this power using /qipunch.\n" +
                "\n" +
                "It costs 10 qi per attack.");
        bookMeta.addPage("more techniques will be added later");

        item.setItemMeta(bookMeta);
        lightningManualItem = item;
    }

    private static void createDemonicManualItem(){
        ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) item.getItemMeta();


        assert bookMeta != null;
        bookMeta.setTitle("Demonic Manual");
        bookMeta.setAuthor("Spellslot");
        bookMeta.setDisplayName("Yin Heart Flow Technique");
        bookMeta.addPage("Absorb the lifeforce of villagers and players to advance to immortality. \n" +
                "\n" +
                "Villagers give a measly sum, but players give huge amounts of energy.");
        bookMeta.addPage("This wondrous scripture has no breakthrough requirement. The speed of your progression is limited only by your mercy.");
        bookMeta.addPage("Stage 1:\n" +
                "\n" +
                "You have learned to absorb the life force of your foes in combat. While /healqi is toggled active, you gain half a heart every hit. Stealing lifeforce also increases your damage.");
        bookMeta.addPage("more techniques will be added later");

        item.setItemMeta(bookMeta);
        demonicManualItem = item;
    }

    private static void createSugarFiendManualItem(){
        ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) item.getItemMeta();


        assert bookMeta != null;
        bookMeta.setTitle("Sugar Fiend");
        bookMeta.setAuthor("Spellslot");
        bookMeta.setDisplayName("Sugar Fiend Ascension");
        bookMeta.addPage("The Sugar Fiend Ascension turns you into a Sugar Fiend. \n" +
                "\n" +
                "You can only cultivate in the Nether.\n" +
                "\n" +
                "You cultivate by eating sweet foods like cookies and pumpkin pie (not cake).");
        bookMeta.addPage("To breakthrough, you must be near placed cake in the Nether. The number of required cakes near you doubles every stage. ");
        bookMeta.addPage("Stage 1:\n" +
                "\n" +
                "At this stage you will find that you can infuse qi into your attacks, causing them to do more damage. \n" +
                "\n" +
                "Type /qipunch to toggle this technique. It requires 10 qi per attack.");
        bookMeta.addPage("Stage 3:\n\nAt this stage you can imbue you qi into your movements.\n\nType /qimove to toggle this technique. It requires 4 qi per second.");

        item.setItemMeta(bookMeta);
        sugarFiendManualItem = item;
    }

    private static void createSpaceManualItem(){
        ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) item.getItemMeta();


        assert bookMeta != null;
        bookMeta.setTitle("Space Manual");
        bookMeta.setAuthor("Spellslot");
        bookMeta.setDisplayName("Void Lotus Art");
        bookMeta.addPage("In the realm's ethereal tapestry, seek chorus fruits and shulker boxes. Eat the fruits, hear their whispers.");
        bookMeta.addPage("Embrace the boxes, feel their resonance. Harmonize essence, transcend boundaries. Ascend with purpose, balance in all. The dance begins, mysteries unfold.");
        bookMeta.addPage("Stage 1:\n" +
                "\n" +
                "Listen, young disciple, for I shall impart upon you the esoteric knowledge of infusing qi into your very fists. A technique that shall unleash the boundless potential within you. Prepare to embark on this journey of enlightenment:/qipunch");
        bookMeta.addPage("Stage 3:\n\nAt this stage you can use qi to teleport short distances.\n\nType /qimove to toggle this technique. Right click with any item (like a sword) in hand to teleport.");

        item.setItemMeta(bookMeta);
        spaceManualItem = item;
    }

    private static void createPoisonManualItem(){
        ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) item.getItemMeta();


        assert bookMeta != null;
        bookMeta.setTitle("Poison Manual");
        bookMeta.setAuthor("Spellslot");
        bookMeta.setDisplayName("Youqin Xuanya's Guide to Toxicity");
        bookMeta.addPage("Follow in Xuanya's footsteps and become the most toxic junior!\n" +
                "\n" +
                "Eat poisonous foods like spider eyes and puffer fish to cultivate. Potions don't count.\n" +
                "\n" +
                "Poison no longer damages you. You gain a huge defense while poisoned");
        bookMeta.addPage("To breakthrough to the next stage, you must cultivate in a swamp biome. \n");
        bookMeta.addPage("Stage 1:\n" +
                "\n" +
                "At this stage you will find that you can infuse qi into your attacks, poisoning enemies. The toxicity of the poison increases per stage. \n" +
                "\n" +
                "Type /qipunch to toggle this technique. It requires 10 qi per attack.");
        bookMeta.addPage("more techniques will be added later");

        item.setItemMeta(bookMeta);
        poisonManualItem = item;
    }

    private static void createPhoenixManualItem(){
        ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) item.getItemMeta();


        assert bookMeta != null;
        bookMeta.setTitle("Phoenix Manual");
        bookMeta.setAuthor("Spellslot");
        bookMeta.setDisplayName("Phoenix Resurrection Technique");
        bookMeta.addPage("Three words: \n" +
                "Die to cultivate. \n" +
                "\n" +
                "The Phoenix is reborn again stronger after every death, the knowledge of their experience aiding them in their new life. \n" +
                "\n" +
                "Die with XP levels to cultivate.");
        bookMeta.addPage("Fire no longer damages you. While on fire, most things cannot hurt you.\n\nTo breakthrough to the next stage, die with 10 times stage levels of experience.\n" +
                "\n");
        bookMeta.addPage("Stage 1:\n" +
                "\n" +
                "At this stage you will find that you can infuse qi into your attacks, causing your enemies to burn. The burn length increases with your stage.\n" +
                "\n" +
                "Type /qipunch to toggle this technique. It requires 10 qi per attack.");
        bookMeta.addPage("more techniques will be added later");

        item.setItemMeta(bookMeta);
        phoenixManualItem = item;
    }
    private static void createIronSkinManualItem(){
        ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) item.getItemMeta();


        assert bookMeta != null;
        bookMeta.setTitle("Ironskin Manual");
        bookMeta.setAuthor("Spellslot");
        bookMeta.setDisplayName("Iron Path of Indestructible Vitality");
        bookMeta.addPage("BECOME INDESTRUCTIBLE! That is the path this scripture follows. As the very first cultivation technique in existence, it is obviously the best! \n" +
                "\n" +
                "Eat iron ingots to cultivate.\n" +
                "\n" +
                "Iron farms to immortality!");
        bookMeta.addPage("To break through to the next stage, you must be near placed iron blocks. The requirement doubles every stage. ");
        bookMeta.addPage("Stage 1:\n" +
                "\n" +
                "At this stage you will find that you can infuse qi into your attacks, causing YOUR FISTS TO BE HARDER! \n" +
                "\n" +
                "Type /qipunch to toggle this technique. It requires 10 qi per attack.");
        bookMeta.addPage("Stage 3:\n\nAt this stage you can imbue you qi into your movements.\n\nType /qimove to toggle this technique.");

        item.setItemMeta(bookMeta);
        ironSkinManualItem = item;
    }

    private static void createIceManualItem(){
        ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) item.getItemMeta();


        assert bookMeta != null;
        bookMeta.setTitle("Ice Manual");
        bookMeta.setAuthor("Spellslot");
        bookMeta.setDisplayName("Celestial Icebound Path");
        bookMeta.addPage("Follow the Celestial Icebound Path by refining ice essence into your Dantian. This can be done through eating ice, although obtaining it may be a challenge...\n" +
                "\n" +
                "This art will make you immune to the cold. ");
        bookMeta.addPage("To breakthrough to the next stage, you must be near placed blue ice blocks. This requirement doubles every stage.\n");
        bookMeta.addPage("Stage 1:\n" +
                "\n" +
                "At this stage you will find that you can infuse qi into your attacks, causing enemies to freeze. The length increases with your stage. \n" +
                "\n" +
                "Type /qipunch to toggle this technique. It requires 10 qi per attack.");
        bookMeta.addPage("more techniques will be added later");


        item.setItemMeta(bookMeta);
        iceManualItem = item;
    }

    private static void createFattyManualItem(){
        ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) item.getItemMeta();


        assert bookMeta != null;
        bookMeta.setTitle("Fatty Manual");
        bookMeta.setAuthor("Spellslot");
        bookMeta.setDisplayName("Supreme Gluttony Scripture");
        bookMeta.addPage("Using this method, you shall gain power rivalling the heavens through simply EATING FOOD. \n" +
                "\n" +
                "This art will let you eat nearly any food without suffering negative effects. Just know that you will rarely have your hunger fufilled... ");
        bookMeta.addPage("To breakthrough to the next stage, you must allow yourself to starve until your body starts eating itself inside out. Only then can you eat and surpass the bottleneck. \n");
        bookMeta.addPage("Stage 1:\n" +
                "\n" +
                "At this stage you will find that you are able to infuse your qi into your attacks, sending your enemies flying. \n" +
                "\n" +
                "To toggle this technique, type /qipunch.");
        //to be continued
        bookMeta.addPage("Stage 3:\n\nAt this stage you can imbue you qi into your body and not take knockback.\n\nType /qimove to toggle this technique. It requires 4 qi per second.");

        item.setItemMeta(bookMeta);
        fattyManualItem = item;
    }

}
