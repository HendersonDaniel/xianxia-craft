package xianxiacraft.xianxiacraft.util;

import org.bukkit.Particle;
import org.bukkit.block.data.type.Bed;
import org.bukkit.entity.Player;

import static xianxiacraft.xianxiacraft.QiManagers.ManualManager.getManual;
import static xianxiacraft.xianxiacraft.QiManagers.PointManager.getStage;

public class ParticleEffects {



    public static void qiAuraParticleEffect(Player player){
        int stage = getStage(player);
        String manual = getManual(player);
        Particle particle;


        switch(manual){
            case "Phoenix Manual":
                particle = Particle.FLAME;
                break;
            case "Ice Manual":
                particle = Particle.SNOWFLAKE;
                break;
            case "Fatty Manual":
                particle = Particle.DRIPPING_HONEY;
                break;
            case "Fungal Manual":
                particle = Particle.DRIPPING_OBSIDIAN_TEAR;
                break;
            case "Ironskin Manual":
                particle = Particle.EXPLOSION_NORMAL;
                break;
            case "LightningManual":
                particle = Particle.ELECTRIC_SPARK;
                break;
            case "Poison Manual":
                particle = Particle.SCULK_CHARGE_POP;
                break;
            case "Space Manual":
                particle = Particle.DRAGON_BREATH;
                break;
            case "Sugar Fiend":
                particle = Particle.ASH;
                break;
            case "Demonic Manual":
                particle = Particle.SCULK_SOUL;
                break;
            default:
                particle = Particle.DRIP_WATER;
        }

        player.spawnParticle(particle,player.getLocation(),100,4,4,4);


    }


}
