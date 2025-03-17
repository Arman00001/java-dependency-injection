package org.example.infrastructure.configurator;

import lombok.SneakyThrows;
import org.example.infrastructure.loader.PropertyLoader;
import org.example.infrastructure.ApplicationContext;
import org.example.infrastructure.annotation.Property;

import java.lang.reflect.Field;

public class PropertyAnnotationObjectConfigurator implements ObjectConfigurator {
    @Override
    @SneakyThrows
    public void configure(Object obj, ApplicationContext context) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if(field.isAnnotationPresent(Property.class)){
                field.setAccessible(true);
                String key = field.getAnnotation(Property.class).value().isEmpty() ?
                        field.getName() : field.getAnnotation(Property.class).value();

                field.set(obj, PropertyLoader.getProperties(key));
            }
        }
    }
}