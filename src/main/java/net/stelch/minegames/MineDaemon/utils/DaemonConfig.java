// 
// Decompiled by Procyon v0.5.36
// 

package net.stelch.minegames.MineDaemon.utils;

public class DaemonConfig
{
    static boolean production;
    static boolean debug;
    static String host;
    static int port;
    static String pass;
    static String sqlHost;
    static String sqlUser;
    static String sqlPass;
    static String sqlDatabase;
    
    public static void setPort(final int port) {
        DaemonConfig.port = port;
    }
    
    public static void setHost(final String host) {
        DaemonConfig.host = host;
    }
    
    public static void setDebug(final boolean debug) {
        DaemonConfig.debug = debug;
    }
    
    public static void setProduction(final boolean production) {
        DaemonConfig.production = production;
    }
    
    public static void setSqlDatabase(final String sqlDatabase) {
        DaemonConfig.sqlDatabase = sqlDatabase;
    }
    
    public static void setSqlHost(final String sqlHost) {
        DaemonConfig.sqlHost = sqlHost;
    }
    
    public static void setSqlUser(final String sqlUser) {
        DaemonConfig.sqlUser = sqlUser;
    }
    
    public static void setSqlPass(final String sqlPass) {
        DaemonConfig.sqlPass = sqlPass;
    }
    
    public static int getPort() {
        return DaemonConfig.port;
    }
    
    public static String getHost() {
        return DaemonConfig.host;
    }
    
    public static String getSqlDatabase() {
        return DaemonConfig.sqlDatabase;
    }
    
    public static String getSqlHost() {
        return DaemonConfig.sqlHost;
    }
    
    public static String getSqlUser() {
        return DaemonConfig.sqlUser;
    }
    
    public static String getSqlPass() {
        return DaemonConfig.sqlPass;
    }
    
    public static String getPass() {
        return DaemonConfig.pass;
    }
    
    public static boolean isDebug() {
        return DaemonConfig.debug;
    }
    
    public static boolean isProduction() {
        return DaemonConfig.production;
    }
    
    static {
        DaemonConfig.production = false;
        DaemonConfig.debug = false;
        DaemonConfig.host = "";
        DaemonConfig.port = 0;
        DaemonConfig.pass = "GrimsReallySecurePasswordIdea1337";
        DaemonConfig.sqlHost = "";
        DaemonConfig.sqlUser = "";
        DaemonConfig.sqlPass = "";
        DaemonConfig.sqlDatabase = "";
    }
}
