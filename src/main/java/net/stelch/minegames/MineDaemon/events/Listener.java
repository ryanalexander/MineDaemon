// 
// Decompiled by Procyon v0.5.36
// 

package net.stelch.minegames.MineDaemon.events;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.Annotation;

public interface Listener
{
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.METHOD })
    @interface EventCallable {
    }
}
