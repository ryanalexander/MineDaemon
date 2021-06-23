// 
// Decompiled by Procyon v0.5.36
// 

package net.stelch.minegames.MineDaemon.utils.variables;

public enum ServerRole
{
    GAME(Character.valueOf('m')), 
    PROXY(Character.valueOf('p')), 
    DEDICATED(Character.valueOf('d')), 
    API(Character.valueOf('a'));
    
    Character character;
    
    ServerRole(final Character c) {
        this.character = c;
    }
    
    public Character getCharacter() {
        return this.character;
    }
}
