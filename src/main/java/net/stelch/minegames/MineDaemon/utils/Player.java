// 
// Decompiled by Procyon v0.5.36
// 

package net.stelch.minegames.MineDaemon.utils;

import java.util.Map;
import org.json.simple.JSONObject;
import java.sql.ResultSet;
import net.stelch.minegames.MineDaemon.Main;
import java.util.HashMap;
import net.stelch.minegames.MineDaemon.utils.variables.Rank;
import java.util.UUID;

public class Player
{
    private final UUID uuid;
    private String username;
    private Rank rank;
    private final long session_started;
    private final String ip;
    private MinecraftProxy proxy;
    private MinecraftServer server;
    private static final HashMap<UUID, Player> players;
    
    public Player(final UUID uuid) {
        this.ip = "127.1.1.1";
        this.uuid = uuid;
        Player.players.put(uuid, this);
        this.session_started = System.currentTimeMillis();
        this.fetchUser();
    }
    
    public void fetchUser() {
        final ResultSet resultSet = Main.sql.query(String.format("SELECT * FROM `games`.`players` WHERE `uuid`='%s' LIMIT 1;", this.uuid));
        try {
            resultSet.next();
            this.rank = Rank.valueOf(resultSet.getString("ranks").split("[,]")[0]);
            this.username = resultSet.getString("username");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void sendMessage(final String string) {
        final HashMap<String, Object> payload = new HashMap<String, Object>();
        final HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("MESSAGE", string);
        data.put("player", this.getPayload());
        payload.put("action", "SEND_MESSAGE");
        payload.put("data", new JSONObject(data));
        this.proxy.getSession().write(new JSONObject(payload).toJSONString());
    }
    
    public void sendActionbar(final String string) {
        final HashMap<String, Object> payload = new HashMap<String, Object>();
        final HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("MESSAGE", string);
        data.put("player", this.getPayload());
        payload.put("action", "SEND_ACTION");
        payload.put("data", new JSONObject(data));
        this.server.getSession().write(new JSONObject(payload).toJSONString());
    }
    
    public void setRank(final Rank rank) {
        if (rank.getLevel() >= 50 || this.getRank().getLevel() >= 50) {
            this.sendMessage(String.format("§7Your rank has been updated. You are now §r%s§r§7!", rank.getFormatted()));
        }
        final HashMap<String, Object> payload = new HashMap<String, Object>();
        final HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("RANK", rank.name());
        data.put("player", this.getPayload());
        payload.put("action", "UPDATE_RANK");
        payload.put("data", new JSONObject(data));
        try {
            if (this.proxy != null && this.proxy.getSession() != null) {
                this.proxy.getSession().write(new JSONObject(payload).toJSONString());
            }
            if (this.server != null && this.server.getSession() != null) {
                this.server.getSession().write(new JSONObject(payload).toJSONString());
            }
        }
        catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println(String.format("Proxy %s\n    Session %s\nServer %s\n    Session %s", this.proxy, this.proxy.getSession(), this.server, this.server.getSession()));
        }
        this.rank = rank;
    }
    
    public void kick(final String reason) {
        final HashMap<String, Object> payload = new HashMap<String, Object>();
        final HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("REASON", reason);
        data.put("player", this.getPayload());
        payload.put("action", "PLAYER_KICK");
        payload.put("data", new JSONObject(data));
        this.proxy.getSession().write(new JSONObject(payload).toJSONString());
    }
    
    public MinecraftProxy getProxy() {
        return this.proxy;
    }
    
    public void setProxy(final MinecraftProxy proxy) {
        this.proxy = proxy;
    }
    
    @Deprecated
    public void setServer(final MinecraftServer server) {
        this.server = server;
    }
    
    public MinecraftServer getServer() {
        return this.server;
    }
    
    public void connect(final MinecraftServer server) {
        if (server == null) {
            return;
        }
        final HashMap<String, Object> payload = new HashMap<String, Object>();
        final HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("player", this.getPayload());
        data.put("server", server.getPayload());
        payload.put("action", "PLAYER_SEND");
        payload.put("data", new JSONObject(data));
        if (this.proxy.getSession() != null) {
            this.proxy.getSession().write(new JSONObject(payload).toJSONString());
        }
        server.addPlayer(this);
        this.server = server;
    }
    
    public UUID getUuid() {
        return this.uuid;
    }
    
    public JSONObject getPayload() {
        final HashMap<String, Object> payload = new HashMap<String, Object>();
        payload.put("UUID", this.uuid.toString());
        return new JSONObject(payload);
    }
    
    public long getSessionStarted() {
        return this.session_started;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public String getIp() {
        return this.ip;
    }
    
    public Rank getRank() {
        return this.rank;
    }
    
    public void disconnect() {
        Player.players.remove(this.uuid);
    }
    
    public static HashMap<UUID, Player> getPlayers() {
        return Player.players;
    }
    
    public static boolean isConnected(final UUID uuid) {
        return Player.players.containsKey(uuid);
    }
    
    public static Player getPlayer(final UUID uuid) {
        return Player.players.get(uuid);
    }
    
    static {
        players = new HashMap<UUID, Player>();
    }
}
