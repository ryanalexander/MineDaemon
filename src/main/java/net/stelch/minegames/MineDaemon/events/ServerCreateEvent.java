// 
// Decompiled by Procyon v0.5.36
// 

package net.stelch.minegames.MineDaemon.events;

import java.lang.reflect.Method;
import java.util.ArrayList;
import net.stelch.minegames.MineDaemon.utils.Server;

public class ServerCreateEvent extends Event
{
    Server server;
    public static ArrayList<Method> listeners;
    
    public ServerCreateEvent(final Server server) {
        System.out.println(String.format("[Event] Fired as " + server.getUid() + " has connected [Host:%s Port:%s]", server.getHost(), server.getPort()));
        this.server = server;
    }
    
    public Server getServer() {
        return this.server;
    }
    
    @Override
    public ArrayList<Method> getHandlers() {
        return ServerCreateEvent.listeners;
    }
    
    public static ArrayList<Method> getHandlerList() {
        return ServerCreateEvent.listeners;
    }
    
    public static void addHandler(final Method m) {
        ServerCreateEvent.listeners.add(m);
    }
    
    static {
        ServerCreateEvent.listeners = new ArrayList<Method>();
    }
}
