// 
// Decompiled by Procyon v0.5.36
// 

package net.stelch.minegames.MineDaemon.utils;

import org.json.simple.JSONObject;
import java.util.Iterator;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
import net.stelch.minegames.MineDaemon.utils.variables.ServerRole;
import java.util.HashMap;

public class Server
{
    private final String uid;
    private long lastPoll;
    public static HashMap<ServerRole, ArrayList<Server>> servers;
    private final long started;
    private final String host;
    private final int port;
    private ServerRole role;
    private final SocketSession session;
    private final List<Player> players;
    
    public Server(final SocketSession session, final ServerRole role, final String host, final int port) {
        this.players = new ArrayList<Player>();
        this.uid = this.calcUID(role);
        this.lastPoll = System.currentTimeMillis();
        this.started = System.currentTimeMillis();
        this.role = role;
        this.session = session;
        this.host = host;
        this.port = port;
        Server.servers.get(role).add(this);
    }
    
    public boolean isPlayerHolding() {
        return true;
    }
    
    public static void registerType(final ServerRole role) {
        Server.servers.put(role, new ArrayList<Server>());
    }
    
    public void setLastPoll(final long pollTime) {
        this.lastPoll = pollTime;
    }
    
    public void setRole(final ServerRole role) {
        this.role = role;
    }
    
    public Player getPlayer(final UUID uuid) {
        for (final Player player : this.players) {
            if (player.getUuid().equals(uuid)) {
                return player;
            }
        }
        return null;
    }
    
    public void doPlayerConnect(final Player player) {
        this.players.add(player);
    }
    
    public void doPlayerDisconnect(final Player player) {
        this.players.remove(player);
    }
    
    public ServerRole getRole() {
        return this.role;
    }
    
    public SocketSession getSession() {
        return this.session;
    }
    
    public long getStarted() {
        return this.started;
    }
    
    public List<Player> getPlayers() {
        return this.players;
    }
    
    public String getUid() {
        return this.uid;
    }
    
    public long getLastPoll() {
        return this.lastPoll;
    }
    
    public int getPort() {
        return this.port;
    }
    
    public String getHost() {
        return this.host;
    }
    
    public void broadcastMessage(final String message) {
        final JSONObject payload = new JSONObject();
        final JSONObject data = new JSONObject();
        data.put("MESSAGE", message);
        payload.put("action", "BROADCAST");
        payload.put("data", data);
        this.getSession().write(payload.toJSONString());
    }
    
    private String calcUID(final ServerRole role) {
        final ArrayList<Integer> ids = new ArrayList<Integer>();
        if (!Server.servers.containsKey(role) && role != null) {
            Server.servers.put(role, new ArrayList<Server>());
        }
        Server.servers.get(role).forEach(server -> ids.add(Integer.parseInt(server.getUid().replaceFirst("[" + role.getCharacter() + "]", ""))));
        for (int i = 0; i < 999; ++i) {
            if (!ids.contains(i)) {
                return role.getCharacter().toString() + i;
            }
        }
        return null;
    }
    
    public void handlePacket(final JSONObject packet) {
        System.out.println("Unregistered packet received " + packet.toJSONString());
    }
    
    public static List<Server> getByType(final ServerRole role) {
        return Server.servers.get(role);
    }
    
    public JSONObject getPayload() {
        final JSONObject payload = new JSONObject();
        payload.put("host", this.host);
        payload.put("port", this.port);
        payload.put("name", this.uid);
        return payload;
    }
    
    static {
        Server.servers = new HashMap<ServerRole, ArrayList<Server>>();
    }
}
