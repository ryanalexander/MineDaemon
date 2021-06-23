// 
// Decompiled by Procyon v0.5.36
// 

package net.stelch.minegames.MineDaemon.events;

import java.util.Iterator;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class EventCoordinator
{
    public static void registerListener(final Listener listener) {
        for (final Method callable : listener.getClass().getMethods()) {
            for (final Annotation annotation : callable.getAnnotations()) {
                if (annotation instanceof Listener.EventCallable) {
                    try {
                        callable.getParameterTypes()[0].getMethod("addHandler", Method.class).invoke("addHandler", callable);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    
    public static void callEvent(final Event event) {
        for (final Method m : event.getHandlers()) {
            try {
                m.invoke(m.getName(), event);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
