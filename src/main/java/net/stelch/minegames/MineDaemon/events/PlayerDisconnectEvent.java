// 
// Decompiled by Procyon v0.5.36
// 

package net.stelch.minegames.MineDaemon.events;

import java.lang.reflect.Method;
import java.util.ArrayList;
import net.stelch.minegames.MineDaemon.utils.Player;
import net.stelch.minegames.MineDaemon.utils.MinecraftProxy;

public class PlayerDisconnectEvent extends Event
{
    MinecraftProxy server;
    Player player;
    public static ArrayList<Method> listeners;
    
    public PlayerDisconnectEvent(final MinecraftProxy server, final Player player) {
        this.server = server;
        this.player = player;
    }
    
    public MinecraftProxy getServer() {
        return this.server;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    @Override
    public ArrayList<Method> getHandlers() {
        return PlayerDisconnectEvent.listeners;
    }
    
    public static ArrayList<Method> getHandlerList() {
        return PlayerDisconnectEvent.listeners;
    }
    
    public static void addHandler(final Method m) {
        PlayerDisconnectEvent.listeners.add(m);
    }
    
    static {
        PlayerDisconnectEvent.listeners = new ArrayList<Method>();
    }
}
