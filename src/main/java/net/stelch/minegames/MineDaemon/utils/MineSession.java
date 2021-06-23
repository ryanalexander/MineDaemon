// 
// Decompiled by Procyon v0.5.36
// 

package net.stelch.minegames.MineDaemon.utils;

import org.json.simple.JSONObject;
import java.io.Serializable;

public class MineSession implements Serializable
{
    public long login;
    public long logout;
    
    public MineSession() {
        this.login = System.currentTimeMillis();
    }
    
    public MineSession(final long login, final long logout) {
        this.login = login;
        this.logout = logout;
    }
    
    public long getSessionStartTime() {
        return this.login;
    }
    
    public long getSessionEndTime() {
        return this.logout;
    }
    
    public long getTotalSessionTime() {
        return System.currentTimeMillis() - this.login;
    }
    
    public JSONObject getJSON() {
        final JSONObject session = new JSONObject();
        session.put("login", this.login);
        session.put("logout", this.login);
        return session;
    }
}
