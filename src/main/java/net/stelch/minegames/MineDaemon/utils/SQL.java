// 
// Decompiled by Procyon v0.5.36
// 

package net.stelch.minegames.MineDaemon.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;

public class SQL
{
    private Connection connection;
    
    public SQL(final String host, final int port, final String username, final String password, final String database) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + ((port > 0) ? port : 3306) + "/" + database + "?autoReconnect=true&useSSL=false", username, password);
        }
        catch (SQLException | ClassNotFoundException ex2) {
            ex2.printStackTrace();
            System.out.println("Failed to connect to SQL ServerEvents [IP: " + host + ":" + port + ";Database:" + database + "]");
        }
    }
    
    public void query(final String query, final boolean t) {
        if (!t) {
            return;
        }
        if (this.connection == null) {
            return;
        }
        try {
            this.connection.createStatement().execute(query);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to execute a SQL Query, assumed server is offline.");
        }
    }
    
    public ResultSet query(final String query) {
        if (this.connection == null) {
            return null;
        }
        try {
            return this.connection.createStatement().executeQuery(query);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to execute a SQL Query, assumed server is offline.");
            return null;
        }
    }
    
    public boolean isInitilized() {
        try {
            return !this.connection.isClosed();
        }
        catch (Exception e) {
            return false;
        }
    }
    
    public void close() {
        try {
            this.connection.close();
        }
        catch (Exception ex) {}
    }
}
