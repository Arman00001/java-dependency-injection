package org.example.infrastructure.configreader;

import org.example.infrastructure.annotation.Component;
import org.example.infrastructure.exception.IllegalQualifierException;
import org.example.infrastructure.exception.NonComponentException;
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
            throw new NonComponentException("The given class is not a Component");
        }

        Set<Class<? extends T>> subTypesOf =
                reflections.getSubTypesOf(cls);

        subTypesOf.removeIf(clz -> !clz.isAnnotationPresent(Component.class));

        if (subTypesOf.size() != 1) {
            throw new RuntimeException("Interface should have only one implementation");
        }

        Class<? extends T> t = subTypesOf.iterator().next();

        if(t.isAnnotationPresent(Component.class)) return t;

        throw new NonComponentException("The given class is not a Component");
    }

    @Override
    public <T> Collection<Class<? extends T>> getImplClasses(Class<T> cls) {
        return reflections.getSubTypesOf(cls);
    }

    @Override
    public <T> Class<T> getImplClass(Class<T> cls, Class<?> qualifierType) {
        if(cls.isAssignableFrom(qualifierType))
            return (Class<T>) qualifierType;
        throw new IllegalQualifierException("Non-assignable qualifier given");

    }
}