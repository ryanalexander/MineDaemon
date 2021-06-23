// 
// Decompiled by Procyon v0.5.36
// 

package net.stelch.minegames.MineDaemon.utils.Games;

import java.util.ArrayList;
import net.stelch.minegames.MineDaemon.utils.variables.GameState;
import java.util.HashMap;
import net.stelch.minegames.MineDaemon.utils.Player;
import java.util.List;
import net.stelch.minegames.MineDaemon.utils.MinecraftServer;

public class Game
{
    String name;
    MinecraftServer server;
    Games gameType;
    List<Player> players;
    HashMap<Player, Integer> playerKills;
    HashMap<Player, Integer> playerDeaths;
    GameState state;
    
    public Game(final String name, final MinecraftServer server) {
        this.players = new ArrayList<Player>();
        this.playerKills = new HashMap<Player, Integer>();
        this.playerDeaths = new HashMap<Player, Integer>();
        this.name = name;
        this.server = server;
    }
    
    public String getName() {
        return this.name;
    }
    
    public MinecraftServer getServer() {
        return this.server;
    }
    
    public GameState getState() {
        return this.state;
    }
    
    public Player[] getPlayers() {
        return this.players.toArray(new Player[0]);
    }
    
    public void setState(final GameState state) {
        this.state = state;
    }
}
