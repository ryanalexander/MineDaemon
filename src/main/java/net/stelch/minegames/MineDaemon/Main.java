// 
// Decompiled by Procyon v0.5.36
// 

package net.stelch.minegames.MineDaemon;

import net.stelch.minegames.MineDaemon.utils.SocketServer;
import net.stelch.minegames.MineDaemon.utils.Server;
import net.stelch.minegames.MineDaemon.utils.variables.ServerRole;
import net.stelch.minegames.MineDaemon.handlers.PlayerEvents;
import net.stelch.minegames.MineDaemon.events.Listener;
import net.stelch.minegames.MineDaemon.events.EventCoordinator;
import net.stelch.minegames.MineDaemon.handlers.ServerEvents;
import net.stelch.minegames.MineDaemon.utils.DaemonConfig;
import net.stelch.minegames.MineDaemon.utils.SQL;

public class Main
{
    public static SQL sql;
    
    public static void main(final String[] args) {
        for (final String s : args) {
            final String[] parts = s.split("=");
            final String s2 = parts[0];
            switch (s2) {
                case "-production": {
                    DaemonConfig.setProduction(Boolean.parseBoolean(parts[1]));
                    break;
                }
                case "-host": {
                    DaemonConfig.setHost(parts[1]);
                    break;
                }
                case "-port": {
                    DaemonConfig.setPort(Integer.parseInt(parts[1]));
                    break;
                }
                case "-sql-host": {
                    DaemonConfig.setSqlHost(parts[1]);
                    break;
                }
                case "-sql-user": {
                    DaemonConfig.setSqlUser(parts[1]);
                    break;
                }
                case "-sql-pass": {
                    DaemonConfig.setSqlPass(parts[1]);
                    break;
                }
                case "-sql-db": {
                    DaemonConfig.setSqlDatabase(parts[1]);
                    break;
                }
                case "-debug": {
                    System.out.println("\u001b[31mSystem in debug mode!\u001b[0m");
                    DaemonConfig.setDebug(true);
                    break;
                }
            }
        }
        System.out.println("\u001b[37m-----\u001b[35m| \u001b[37mMineGames Daemon \u001b[35m|\u001b[37m-----");
        EventCoordinator.registerListener(new ServerEvents());
        EventCoordinator.registerListener(new PlayerEvents());
        Main.sql = new SQL(DaemonConfig.getSqlHost(), 3306, DaemonConfig.getSqlUser(), DaemonConfig.getSqlPass(), DaemonConfig.getSqlDatabase());
        for (final ServerRole value : ServerRole.values()) {
            Server.registerType(value);
        }
        new SocketServer();
    }
}
