package org.example.infrastructure.configreader;

import org.example.infrastructure.annotation.Component;
import org.reflections.Reflections;

import java.util.Collection;
import java.util.Set;

public class JavaObjectConfigReader implements ObjectConfigReader {

    private Reflections reflections;

    public JavaObjectConfigReader(String packageToScan) {
        this.reflections = new Reflections(packageToScan);
    }

    @Override
    public <T> Class<? extends T> getImplClass(Class<T> cls) {
        if (!cls.isInterface()) {
            if(cls.isAnnotationPresent(Component.class)) return cls;
            return null;
        }

        Set<Class<? extends T>> subTypesOf =
                reflections.getSubTypesOf(cls);

        if (subTypesOf.size() != 1) {
            throw new RuntimeException("Interface should have only one implementation");
        }

        Class<? extends T> t = subTypesOf.iterator().next();

        if(t.isAnnotationPresent(Component.class)) return t;

        return null;
    }

    @Override
    public <T> Collection<Class<? extends T>> getImplClasses(Class<T> cls) {
        return reflections.getSubTypesOf(cls);
    }
}
