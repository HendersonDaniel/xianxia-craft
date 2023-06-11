package xianxiacraft.xianxiacraft.handlers.Manuals;

import org.bukkit.event.Listener;

public class Manual implements Listener {

    private String name;
    private double qiRegeneration = 0.01;

    public Manual(String name, double qiRegeneration){
        this(name);
        this.qiRegeneration = qiRegeneration;
    }

    public Manual(String name){
        this.name = name;
    }

    public String getManualName(){
        return name;
    }

    public double getQiRegeneration(){
        return qiRegeneration;
    }




}
