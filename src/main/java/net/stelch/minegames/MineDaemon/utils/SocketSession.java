// 
// Decompiled by Procyon v0.5.36
// 

package net.stelch.minegames.MineDaemon.utils;

import net.stelch.minegames.MineDaemon.events.Event;
import net.stelch.minegames.MineDaemon.events.EventCoordinator;
import net.stelch.minegames.MineDaemon.events.ServerOfflineEvent;
import java.io.IOException;
import org.json.simple.JSONObject;
import java.util.TimerTask;
import java.util.Timer;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.PrintWriter;

public class SocketSession
{
    private boolean authenticated;
    private Server server;
    private onData dataAction;
    private final onClose closeAction;
    PrintWriter out;
    private final Socket socket;
    
    public SocketSession(final Socket socket, final onData dataAction, final onClose closeAction) {
        this.socket = socket;
        this.dataAction = dataAction;
        this.closeAction = closeAction;
        try {
            this.out = new PrintWriter(socket.getOutputStream(), true);
            final BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            final SocketSession session = this;
            new Thread(() -> new Timer().scheduleAtFixedRate(new TimerTask() {
                final Socket socket = SocketSession.this.socket;
                @Override
                public void run() {
                    final JSONObject object = new JSONObject();
                    object.put("action", "PING");
                    SocketSession.this.write(object.toJSONString());
                    if (this.socket.isClosed()) {
                        this.cancel();
                        return;
                    }
                    if (SocketSession.this.getServer() != null && System.currentTimeMillis() - SocketSession.this.getServer().getLastPoll() > 5000L) {
                        SocketSession.this.terminateConnection();
                        System.out.println("Closing connection due to keep-alive failing!");
                        this.cancel();
                    }
                }
            }, 0L, 1000L)).start();
            String line;
            while (session.getSocket().isConnected() && (line = in.readLine()) != null) {
                dataAction.onData(this, line);
            }
        }
        catch (IOException e) {
            if (e.getMessage().equals("Connection reset") || e.getMessage().equals("Socket closed")) {
                this.terminateConnection();
                System.out.println("Closing connection due to connection closure!");
                return;
            }
            e.printStackTrace();
        }
    }
    
    public void onData(final onData action) {
        this.dataAction = action;
    }
    
    public boolean isAuthenticated() {
        return this.authenticated;
    }
    
    public void setAuthenticated(final boolean authenticated) {
        this.authenticated = authenticated;
    }
    
    public Server getServer() {
        return this.server;
    }
    
    public void setServer(final Server server) {
        this.server = server;
    }
    
    public void write(final String string) {
        if (DaemonConfig.isDebug()) {
            System.out.println("OUT: " + string);
        }
        this.out.println(string);
        this.out.flush();
    }
    
    public void terminateConnection() {
        try {
            this.socket.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if (this.closeAction != null) {
            this.closeAction.onClose(this);
        }
        EventCoordinator.callEvent(new ServerOfflineEvent(this.getServer()));
    }
    
    public Socket getSocket() {
        return this.socket;
    }
    
    interface onClose
    {
        void onClose(final SocketSession p0);
    }
    
    interface onData
    {
        void onData(final SocketSession p0, final String p1);
    }
}
