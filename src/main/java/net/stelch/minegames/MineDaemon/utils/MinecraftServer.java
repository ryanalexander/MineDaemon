// 
// Decompiled by Procyon v0.5.36
// 

package net.stelch.minegames.MineDaemon.utils;

import java.util.Iterator;
import org.json.simple.JSONObject;
import java.util.ArrayList;
import net.stelch.minegames.MineDaemon.utils.variables.ServerRole;
import java.util.HashMap;
import java.util.List;
import net.stelch.minegames.MineDaemon.utils.variables.ServerCategory;
import net.stelch.minegames.MineDaemon.utils.Games.Game;

public class MinecraftServer extends Server
{
    private Game game;
    private ServerCategory category;
    private final List<Player> players;
    public static HashMap<String, MinecraftServer> servers;
    public static HashMap<ServerCategory, List<MinecraftServer>> categoryServers;
    
    public MinecraftServer(final SocketSession session, final String host, final int port) {
        super(session, ServerRole.GAME, host, port);
        this.players = new ArrayList<>();
        MinecraftServer.servers.put(this.getUid(), this);
    }
    
    public void setCategory(final ServerCategory category) {
        if(MinecraftServer.categoryServers.containsKey(this.category)) {
            MinecraftServer.categoryServers.get(this.category).remove(this);
        }else {
            MinecraftServer.categoryServers.put(this.category,new ArrayList<>());
        }
        if(MinecraftServer.categoryServers.containsKey(category)) {
            MinecraftServer.categoryServers.get(category).remove(this);
        }else {
            MinecraftServer.categoryServers.put(category,new ArrayList<>());
        }
        MinecraftServer.categoryServers.get(category).add(this);
        this.category = category;
    }
    
    public ServerCategory getCategory() {
        return this.category;
    }
    
    public void addPlayer(final Player player) {
        this.players.add(player);
    }
    
    public void remPlayer(final Player player) {
        this.players.remove(player);
    }
    
    public void setGame(final Game game) {
        this.game = game;
    }
    
    public Game getGame() {
        return this.game;
    }
    
    @Override
    public void handlePacket(final JSONObject packet) {
        final JSONObject payload = (JSONObject)packet.get("data");
        final String upperCase = ((String)packet.get("action")).toUpperCase();
        switch (upperCase) {
            case "SERVER_STATUS": {}
            case "SERVER_TYPE": {
                this.setCategory(ServerCategory.valueOf((String)payload.get("type")));
                break;
            }
        }
    }
    
    public static MinecraftServer getLobby() {
        final List<MinecraftServer> lobbies = MinecraftServer.categoryServers.get(ServerCategory.LOBBY);
        MinecraftServer decided = null;
        for (final MinecraftServer server : lobbies) {
            if (decided == null || server.getPlayers().size() < decided.getPlayers().size()) {
                decided = server;
            }
        }
        return decided;
    }
    
    static {
        MinecraftServer.servers = new HashMap<String, MinecraftServer>();
        MinecraftServer.categoryServers = new HashMap<ServerCategory, List<MinecraftServer>>();
    }
}
