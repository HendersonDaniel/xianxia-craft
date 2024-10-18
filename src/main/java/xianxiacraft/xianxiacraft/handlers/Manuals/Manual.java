package xianxiacraft.xianxiacraft.handlers.Manuals;

public class Manual {

    private String name;
    private double qiRegeneration = 0.01;
    private int attackDamagePerStage = 4;
    private int defensePerStage = 4;

    public Manual(String name, double qiRegeneration,int attackDamagePerStage,int defensePerStage){
        this(name,qiRegeneration);
        this.attackDamagePerStage = attackDamagePerStage;
        this.defensePerStage = defensePerStage;
    }

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

    public int getAttackPerStage() { return attackDamagePerStage; }

    public int getDefensePerStage() { return defensePerStage; }




}
