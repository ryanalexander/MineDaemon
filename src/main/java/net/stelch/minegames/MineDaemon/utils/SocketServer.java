// 
// Decompiled by Procyon v0.5.36
// 

package net.stelch.minegames.MineDaemon.utils;

import net.stelch.minegames.MineDaemon.events.Event;
import net.stelch.minegames.MineDaemon.events.EventCoordinator;
import net.stelch.minegames.MineDaemon.events.ServerCreateEvent;
import net.stelch.minegames.MineDaemon.utils.variables.ServerRole;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetAddress;

public class SocketServer
{
    public SocketServer() {
        try {
            final ServerSocket server = new ServerSocket(DaemonConfig.getPort(), 100, InetAddress.getByName(DaemonConfig.getHost()));
            System.out.println("MineGames Daemon now listening on " + server.getInetAddress().getHostAddress() + ":" + server.getLocalPort());
            while (true) {
                final Socket connection = server.accept();
                System.out.println("Connection request from " + connection.getInetAddress() + ":" + connection.getPort());
                new Thread() {
                    @Override
                    public void run() {
                        new SocketSession(connection, SocketServer.this::handleSessionCommand, session -> this.interrupt());
                        super.run();
                    }
                }.start();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("MineGames Daemon failed to start");
            System.exit(0);
        }
    }
    
    public void handleSessionCommand(final SocketSession session, final String data) {
        if (DaemonConfig.isDebug()) {
            System.out.println("IN: " + data);
        }
        JSONObject message;
        try {
            message = (JSONObject)new JSONParser().parse(data);
        }
        catch (ParseException e) {
            System.out.println("Failed to parse " + data);
            e.printStackTrace();
            return;
        }
        final String action = message.containsKey("action") ? ((String)message.get("action")).toUpperCase().trim() : "";
        if (action.equals("AUTH")) {
            if (message.containsKey("data")) {
                final JSONObject payload = (JSONObject) message.get("data");
                if (payload.containsKey("pass") && payload.containsKey("role") && payload.containsKey("host") && payload.containsKey("port") && payload.containsKey("node")) {
                    if (payload.get("pass").equals(DaemonConfig.getPass())) {
                        final ServerRole role = ServerRole.valueOf((String)payload.get("role"));
                        switch (role) {
                            case DEDICATED: {
                                session.setServer(new DedicatedServer(session, (String)payload.get("host"), Integer.parseInt(payload.get("port") + "")));
                                EventCoordinator.callEvent(new ServerCreateEvent(session.getServer()));
                                break;
                            }
                            case GAME: {
                                session.setServer(new MinecraftServer(session, (String)payload.get("host"), Integer.parseInt(payload.get("port") + "")));
                                EventCoordinator.callEvent(new ServerCreateEvent(session.getServer()));
                                break;
                            }
                            case PROXY: {
                                session.setServer(new MinecraftProxy(session, (String)payload.get("host"), Integer.parseInt(payload.get("port") + "")));
                                EventCoordinator.callEvent(new ServerCreateEvent(session.getServer()));
                                break;
                            }
                            case API: {
                                session.setServer(new RestAPI(session, (String)payload.get("host"), Integer.parseInt(payload.get("port") + "")));
                                break;
                            }
                            default: {
                                System.out.println("Invalid server type");
                                break;
                            }
                        }
                    }
                    else {
                        System.out.println(String.format("Timed out %s for [AUTH_INVALID_CREDENTIALS]", session.getSocket().getInetAddress().toString()));
                    }
                }
                else {
                    System.out.println(String.format("Timed out %s for [AUTH_INVALID_PAYLOAD]", session.getSocket().getInetAddress().toString()));
                }
            }
            else {
                System.out.println("No data received");
            }
            return;
        }
        if (action.equals("PONG")) {
            session.getServer().setLastPoll(System.currentTimeMillis());
            return;
        }
        if (message.get("action") == null) {
            System.out.println("Invalid packet sent by " + session.getServer().getUid());
            System.out.println(message.toJSONString());
        }
        else {
            session.getServer().handlePacket(message);
        }
    }
}
