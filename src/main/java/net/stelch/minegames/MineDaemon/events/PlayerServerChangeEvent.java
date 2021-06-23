// 
// Decompiled by Procyon v0.5.36
// 

package net.stelch.minegames.MineDaemon.events;

import java.lang.reflect.Method;
import java.util.ArrayList;
import net.stelch.minegames.MineDaemon.utils.Player;
import net.stelch.minegames.MineDaemon.utils.Server;

public class PlayerServerChangeEvent extends Event
{
    Server server;
    Player player;
    public static ArrayList<Method> listeners;
    
    public PlayerServerChangeEvent(final Server server, final Player player) {
        this.server = server;
        this.player = player;
    }
    
    public Server getServer() {
        return this.server;
    }
    
    public void broadcastMessage(final String string) {
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    @Override
    public ArrayList<Method> getHandlers() {
        return PlayerServerChangeEvent.listeners;
    }
    
    public static ArrayList<Method> getHandlerList() {
        return PlayerServerChangeEvent.listeners;
    }
    
    public static void addHandler(final Method m) {
        PlayerServerChangeEvent.listeners.add(m);
    }
    
    static {
        PlayerServerChangeEvent.listeners = new ArrayList<Method>();
    }
}
