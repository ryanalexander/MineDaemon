// 
// Decompiled by Procyon v0.5.36
// 

package net.stelch.minegames.MineDaemon.utils;

import net.stelch.minegames.MineDaemon.events.PlayerServerChangeEvent;
import net.stelch.minegames.MineDaemon.events.PlayerDisconnectEvent;
import net.stelch.minegames.MineDaemon.events.Event;
import net.stelch.minegames.MineDaemon.events.EventCoordinator;
import net.stelch.minegames.MineDaemon.events.PlayerConnectEvent;
import org.json.simple.JSONObject;
import java.util.Iterator;
import java.util.UUID;
import net.stelch.minegames.MineDaemon.utils.variables.ServerRole;
import java.util.HashMap;

public class MinecraftProxy extends Server
{
    private final HashMap<Player, MinecraftServer> players;
    
    public MinecraftProxy(final SocketSession session, final String host, final int port) {
        super(session, ServerRole.PROXY, host, port);
        this.players = new HashMap<Player, MinecraftServer>();
    }
    
    @Override
    public Player getPlayer(final UUID uuid) {
        for (final Player player : this.players.keySet()) {
            if (player.getUuid().equals(uuid)) {
                return player;
            }
        }
        return null;
    }
    
    @Override
    public boolean isPlayerHolding() {
        return false;
    }
    
    public void doPlayerConnect(final Player player, final MinecraftServer server) {
        player.setProxy(this);
        this.players.put(player, server);
    }
    
    @Override
    public void doPlayerDisconnect(final Player player) {
        player.disconnect();
        this.players.remove(player);
    }
    
    @Override
    public void handlePacket(final JSONObject packet) {
        final JSONObject payload = (JSONObject)packet.get("data");
        final String upperCase = ((String)packet.get("action")).toUpperCase();
        switch (upperCase) {
            case "PLAYER_CONNECT": {
                final Player player = new Player(UUID.fromString("" + payload.get("UUID")));
                player.setProxy(this);
                EventCoordinator.callEvent(new PlayerConnectEvent(this, player));
                break;
            }
            case "PLAYER_DISCONNECT": {
                EventCoordinator.callEvent(new PlayerDisconnectEvent(this, Player.getPlayer(UUID.fromString("" + payload.get("UUID")))));
                break;
            }
            case "REQUEST_SERVER": {
                Player.getPlayer(UUID.fromString(payload.get("UUID") + "")).connect(MinecraftServer.servers.get("" + payload.get("NAME")));
                EventCoordinator.callEvent(new PlayerServerChangeEvent(MinecraftServer.servers.get("" + payload.get("NAME")), Player.getPlayer(UUID.fromString(payload.get("UUID") + ""))));
                break;
            }
        }
    }
}
