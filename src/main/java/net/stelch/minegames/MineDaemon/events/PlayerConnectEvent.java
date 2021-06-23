// 
// Decompiled by Procyon v0.5.36
// 

package net.stelch.minegames.MineDaemon.events;

import java.lang.reflect.Method;
import java.util.ArrayList;
import net.stelch.minegames.MineDaemon.utils.Player;
import net.stelch.minegames.MineDaemon.utils.Server;

public class PlayerConnectEvent extends Event
{
    Server server;
    Player player;
    public static ArrayList<Method> listeners;
    
    public PlayerConnectEvent(final Server server, final Player player) {
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
        return PlayerConnectEvent.listeners;
    }
    
    public static ArrayList<Method> getHandlerList() {
        return PlayerConnectEvent.listeners;
    }
    
    public static void addHandler(final Method m) {
        PlayerConnectEvent.listeners.add(m);
    }
    
    static {
        PlayerConnectEvent.listeners = new ArrayList<Method>();
    }
}
