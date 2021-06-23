// 
// Decompiled by Procyon v0.5.36
// 

package net.stelch.minegames.MineDaemon.utils;

import net.stelch.minegames.MineDaemon.utils.variables.Rank;
import java.util.Map;
import java.util.HashMap;
import org.json.simple.JSONObject;
import java.util.List;
import java.util.UUID;
import net.stelch.minegames.MineDaemon.utils.variables.ServerRole;

public class RestAPI extends Server
{
    public RestAPI(final SocketSession session, final String host, final int port) {
        super(session, ServerRole.PROXY, host, port);
        System.out.println("API has connected");
    }
    
    @Override
    public Player getPlayer(final UUID uuid) {
        return null;
    }
    
    @Override
    public void doPlayerConnect(final Player player) {
    }
    
    @Override
    public boolean isPlayerHolding() {
        return false;
    }
    
    @Override
    public void doPlayerDisconnect(final Player player) {
    }
    
    @Override
    public List<Player> getPlayers() {
        return null;
    }
    
    @Override
    public void broadcastMessage(final String message) {
    }
    
    @Override
    public void handlePacket(final JSONObject packet) {
        final JSONObject payload = (JSONObject) packet.get("data");
        final HashMap<String, Object> reply = new HashMap<String, Object>();
        final HashMap<String, Object> reply_data = new HashMap<String, Object>();
        Player player = null;
        if (payload != null && payload.containsKey("player")) {
            player = Player.getPlayer(UUID.fromString(((JSONObject)payload.get("player")).get("UUID") + ""));
        }
        final String upperCase = ((String)packet.get("action")).toUpperCase();
        switch (upperCase) {
            case "REFRESH_STATS": {
                final HashMap<String, Object> players = new HashMap<String, Object>();
                players.put("cur", Player.getPlayers().size());
                players.put("max", (int)players.get("cur") + 1);
                final HashMap<String, Object> servers = new HashMap<String, Object>();
                servers.put("minecraft", Server.getByType(ServerRole.GAME).size());
                servers.put("proxy", Server.getByType(ServerRole.PROXY).size());
                servers.put("dedicated", Server.getByType(ServerRole.DEDICATED).size());
                servers.put("api", Server.getByType(ServerRole.API).size());
                reply_data.put("players", players);
                reply_data.put("servers", servers);
                reply.put("action", "SATISFY_REQUEST");
                reply.put("qid", packet.get("qid"));
                reply.put("data", reply_data);
                this.getSession().write(new JSONObject(reply).toJSONString());
                break;
            }
            case "LOCATE_PLAYER": {
                reply.put("action", "SATISFY_REQUEST");
                reply.put("qid", packet.get("qid"));
                reply_data.put("server", (player != null && player.getServer() != null) ? player.getServer().getUid() : null);
                reply_data.put("session", (player != null && player.getServer() != null) ? Long.valueOf(System.currentTimeMillis() - player.getSessionStarted()) : null);
                reply.put("data", reply_data);
                this.getSession().write(new JSONObject(reply).toJSONString());
                break;
            }
            case "KICK_PLAYER":
            case "YEET_PLAYER": {
                if (player != null) {
                    player.kick(payload.get("reason") + "");
                    break;
                }
                break;
            }
            case "PLAYER_MESSAGE": {
                if (player != null) {
                    player.sendMessage(payload.get("message") + "");
                    break;
                }
                break;
            }
            case "UPDATE_RANK": {
                if (player != null) {
                    player.setRank(Rank.valueOf((String)payload.get("rank")));
                    break;
                }
                break;
            }
        }
    }
}
