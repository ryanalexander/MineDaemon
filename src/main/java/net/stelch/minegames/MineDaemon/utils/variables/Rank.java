// 
// Decompiled by Procyon v0.5.36
// 

package net.stelch.minegames.MineDaemon.utils.variables;

public enum Rank
{
    MEMBER(0, "§f§l"), 
    SUPER(1, "§a§l"), 
    MEGA(2, "§b§l"), 
    ULTRA(3, "§d§l"), 
    IMPERIAL(4, "§e§l"), 
    HELPER(50, "§3§l"), 
    MODERATOR(75, "§9§l"), 
    MANAGER(85, "§c§l"), 
    OWNER(100, "§5§l");
    
    private final int level;
    private final String rank;
    
    public int getLevel() {
        return this.level;
    }
    
    public String getFormatted() {
        return this.getColor() + this.name() + "§r";
    }
    
    public String getColor() {
        return this.rank;
    }
    
    Rank(final int level, final String rank) {
        this.rank = rank.toUpperCase();
        this.level = level;
    }
}
