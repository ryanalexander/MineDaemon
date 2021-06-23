// 
// Decompiled by Procyon v0.5.36
// 

package net.stelch.minegames.MineDaemon.requests;

import net.stelch.minegames.MineDaemon.utils.Player;
import java.util.UUID;
import org.json.simple.JSONObject;

public class RequestUtils
{
    public static Request getRequest(final JSONObject object) {
        try {
            final Class<Request> requestClass = (Class<Request>)Class.forName("net.stelch.minegames.WorldDeployDaemon.requests." + object.get("type"));
            return (Request)requestClass.getMethod(requestClass.getName(), new Class[0]).invoke(new Player(UUID.fromString((String) object.get("player"))), new Object[0]);
        }
        catch (Exception e) {
            return null;
        }
    }
}
