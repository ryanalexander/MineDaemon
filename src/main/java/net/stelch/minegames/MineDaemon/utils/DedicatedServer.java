// 
// Decompiled by Procyon v0.5.36
// 

package net.stelch.minegames.MineDaemon.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.ArrayList;
import net.stelch.minegames.MineDaemon.utils.variables.ServerRole;
import java.util.List;

public class DedicatedServer extends Server
{
    private List<Integer> cpu;
    private List<Integer> memory_allocated;
    private List<Integer> memory_cache;
    private int memory_total;
    
    public DedicatedServer(final SocketSession session, final String host, final int port) {
        super(session, ServerRole.DEDICATED, host, port);
        this.cpu = new ArrayList<Integer>();
        this.memory_allocated = new ArrayList<Integer>();
        this.memory_cache = new ArrayList<Integer>();
        this.memory_total = 0;
    }
    
    public void logCpu(final Integer values) {
        this.cpu.add(values);
        if (this.cpu.size() > 6) {
            this.cpu = this.cpu.subList(this.cpu.size() - 6, this.cpu.size());
        }
    }
    
    public void logMemory(final int value) {
        this.memory_allocated.add(value);
        if (this.memory_allocated.size() > 6) {
            this.memory_allocated = this.memory_allocated.subList(this.memory_allocated.size() - 6, this.memory_allocated.size());
        }
    }
    
    public void logCache(final int value) {
        this.memory_cache.add(value);
        if (this.memory_cache.size() > 6) {
            this.memory_cache = this.memory_cache.subList(this.memory_cache.size() - 6, this.memory_cache.size());
        }
    }
    
    public void setMemoryTotal(final int total) {
        this.memory_total = total;
    }
    
    @Override
    public void handlePacket(final JSONObject packet) {
        final JSONObject payload = (JSONObject) packet.get("data");
        final String upperCase = ((String)packet.get("action")).toUpperCase();
        switch (upperCase) {
            case "SERVER_STATUS": {
                final int cpu = (int)payload.get("CPU");
                final JSONObject memory = (JSONObject)payload.get("MEMORY");
                final JSONArray hdd = (JSONArray)payload.get("HDD");
                final JSONArray containers = (JSONArray)payload.get("CONTAINERS");
                this.logCpu(cpu);
                this.logMemory((int)memory.get("allocated"));
                this.logCache((int)memory.get("cache"));
                this.setMemoryTotal((int)memory.get("total"));
                break;
            }
        }
    }
}
