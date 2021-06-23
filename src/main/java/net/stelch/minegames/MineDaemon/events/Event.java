// 
// Decompiled by Procyon v0.5.36
// 

package net.stelch.minegames.MineDaemon.events;

import java.lang.reflect.Method;
import java.util.ArrayList;

public abstract class Event
{
    public ArrayList<Method> listeners;
    private boolean cancelled;
    
    public String getEventName() {
        return this.getClass().getSimpleName();
    }
    
    public abstract ArrayList<Method> getHandlers();
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
}
