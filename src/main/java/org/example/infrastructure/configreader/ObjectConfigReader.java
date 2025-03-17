package org.example.infrastructure.configreader;

import java.util.Collection;

public interface ObjectConfigReader {

    <T> Class<? extends T> getImplClass(Class<T> cls);

    <T> Collection<Class<? extends T>> getImplClasses(Class<T> cls);

    <T> Class<T> getImplClass(Class<T> cls, Class<?> qualifierType);
}
