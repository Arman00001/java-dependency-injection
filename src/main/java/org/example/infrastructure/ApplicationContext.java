package org.example.infrastructure;

import lombok.Getter;
import lombok.Setter;
import org.example.infrastructure.annotation.Scope;
import org.example.infrastructure.configreader.ObjectConfigReader;
import org.example.infrastructure.enums.ScopeType;

import java.util.HashMap;
import java.util.Map;

public class ApplicationContext {

    @Setter
    private ObjectFactory objectFactory;

    @Getter
    private ObjectConfigReader objectConfigReader;

    private Map<Class<?>, Object> singletonCache = new HashMap<>();

    public ApplicationContext(ObjectConfigReader objectConfigReader) {
        this.objectConfigReader = objectConfigReader;
    }

    @SuppressWarnings("unchecked")
    public <T> T getObject(Class<T> cls) {
        Class<? extends T> implClass = objectConfigReader.getImplClass(cls);

        if(implClass==null) return null;

        if (singletonCache.containsKey(implClass)) {
            return (T) singletonCache.get(implClass);
        }

        T object = objectFactory.createObject(implClass);

        if (!implClass.isAnnotationPresent(Scope.class) ||
                implClass.getAnnotation(Scope.class).value().equals(ScopeType.SINGLETON)) {
            singletonCache.put(implClass, object);
        }

        return object;
    }
}
