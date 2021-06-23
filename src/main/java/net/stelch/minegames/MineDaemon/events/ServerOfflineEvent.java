// 
// Decompiled by Procyon v0.5.36
// 

package net.stelch.minegames.MineDaemon.events;

import java.lang.reflect.Method;
import java.util.ArrayList;
import net.stelch.minegames.MineDaemon.utils.Server;

public class ServerOfflineEvent extends Event
{
    Server server;
    public static ArrayList<Method> listeners;
    
    public ServerOfflineEvent(final Server server) {
        this.server = server;
    }
    
    public Server getServer() {
        return this.server;
    }
    
    @Override
    public ArrayList<Method> getHandlers() {
        return ServerOfflineEvent.listeners;
    }
    
    public static ArrayList<Method> getHandlerList() {
        return ServerOfflineEvent.listeners;
    }
    
    public static void addHandler(final Method m) {
        ServerOfflineEvent.listeners.add(m);
    }
    
    static {
        ServerOfflineEvent.listeners = new ArrayList<Method>();
    }
}
