// 
// Decompiled by Procyon v0.5.36
// 

package net.stelch.minegames.MineDaemon.handlers;

import net.stelch.minegames.MineDaemon.events.PlayerDisconnectEvent;
import net.stelch.minegames.MineDaemon.events.PlayerServerChangeEvent;
import net.stelch.minegames.MineDaemon.utils.MinecraftServer;
import net.stelch.minegames.MineDaemon.events.PlayerConnectEvent;
import net.stelch.minegames.MineDaemon.events.Listener;

public class PlayerEvents implements Listener
{
    @EventCallable
    public static void PlayerConnect(final PlayerConnectEvent e) {
        if (e.getPlayer().getRank().getLevel() < 10) {
            e.getPlayer().kick("&cFailed to connect to the MineGames Network.\n\n&fConstruction site ahead! No entry!\n\n&7MineGames is still under development. Players are not yet able to connect.");
            System.out.println("\u001b[31mKICK\u001b[37m> " + e.getPlayer().getUsername() + " has been kicked from the Network *Not Whitelisted*");
            return;
        }
        if (e.getPlayer().getRank().getLevel() >= 50) {
            e.getPlayer().sendMessage("&7Staff Announcements\n&e- &fRanks have just been updated, any players with issue should contact Aspy directly.");
        }
        final MinecraftServer server = MinecraftServer.getLobby();
        if (server != null) {
            System.out.println("\u001b[36mJOIN\u001b[37m> " + e.getPlayer().getUsername() + " (" + e.getServer().getUid() + ") connecting to " + server.getUid());
            server.doPlayerConnect(e.getPlayer());
            e.getPlayer().sendMessage("&7You have been connected to &b" + server.getUid() + "&7 as a lobby!");
            e.getPlayer().connect(server);
        }
        else {
            System.out.println("\u001b[31mJOIN\u001b[37m> " + e.getPlayer().getUsername() + " (" + e.getServer().getUid() + ") failed to locate lobby");
            e.getPlayer().kick("&cFailed to locate available lobby!");
        }
    }
    
    @EventCallable
    public static void PlayerServerChange(final PlayerServerChangeEvent e) {
        if (e.getServer() != null & e.getPlayer() != null) {
            e.getPlayer().sendMessage("&bM&dG&r &7You have been sent to &e" + e.getServer().getUid() + "&7!");
        }
    }
    
    @EventCallable
    public static void PlayerDisconnect(final PlayerDisconnectEvent e) {
        System.out.println("\u001b[36mLEAVE\u001b[37m> " + e.getPlayer().getUsername() + " (" + e.getServer().getUid() + ") disconnected");
        e.getServer().doPlayerDisconnect(e.getPlayer());
    }
}
