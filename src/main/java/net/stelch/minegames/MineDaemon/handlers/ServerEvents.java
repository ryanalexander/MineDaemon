// 
// Decompiled by Procyon v0.5.36
// 

package net.stelch.minegames.MineDaemon.handlers;

import net.stelch.minegames.MineDaemon.utils.Server;
import java.util.ArrayList;
import net.stelch.minegames.MineDaemon.utils.MinecraftServer;
import net.stelch.minegames.MineDaemon.utils.variables.ServerRole;
import net.stelch.minegames.MineDaemon.utils.Player;
import net.stelch.minegames.MineDaemon.events.ServerOfflineEvent;
import net.stelch.minegames.MineDaemon.events.ServerCreateEvent;
import net.stelch.minegames.MineDaemon.events.Listener;

public class ServerEvents implements Listener
{
    @EventCallable
    public static void ServerStart(final ServerCreateEvent e) {
        System.out.println("\u001b[36mSTART\u001b[37m> " + e.getServer().getUid() + " (" + e.getServer().getHost() + ":" + e.getServer().getPort() + ") now online");
    }
    
    @EventCallable
    public static void ServerOffline(final ServerOfflineEvent e) {
        System.out.println("\u001b[36mSTOP\u001b[37m> " + e.getServer().getUid() + " (" + e.getServer().getHost() + ":" + e.getServer().getPort() + ") now offline");
        for (final Player player : e.getServer().getPlayers().toArray(new Player[0])) {
            if (e.getServer().isPlayerHolding()) {
                e.getServer().doPlayerDisconnect(player);
            }
        }
        if (e.getServer().getRole() == ServerRole.GAME) {
            MinecraftServer.servers.remove(e.getServer().getUid());
        }
        Server.servers.get(e.getServer().getRole()).remove(e.getServer());
    }
}
