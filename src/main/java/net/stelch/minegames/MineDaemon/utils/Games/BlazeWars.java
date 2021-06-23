// 
// Decompiled by Procyon v0.5.36
// 

package net.stelch.minegames.MineDaemon.utils.Games;

import net.stelch.minegames.MineDaemon.utils.MinecraftServer;
import net.stelch.minegames.MineDaemon.utils.Player;
import java.util.HashMap;

public class BlazeWars extends Game
{
    HashMap<Player, Integer> playerBlaze;
    
    public BlazeWars(final MinecraftServer server) {
        super("BlazeWars", server);
        this.playerBlaze = new HashMap<Player, Integer>();
    }
}
