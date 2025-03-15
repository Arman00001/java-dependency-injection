package org.example.infrastructure.configurator;

import lombok.SneakyThrows;
import org.example.infrastructure.ApplicationContext;
import org.example.infrastructure.annotation.Inject;
import org.example.infrastructure.annotation.Qualifier;

import java.lang.reflect.Field;

public class InjectAnnotationObjectConfigurator implements ObjectConfigurator {

    @Override
    @SneakyThrows
    public void configure(Object obj, ApplicationContext context) {
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(Inject.class)) {
                field.setAccessible(true);

                if(field.isAnnotationPresent(Qualifier.class))
                    field.set(obj, context.getObject(field.getAnnotation(Qualifier.class).value()));
                else
                    field.set(obj, context.getObject(field.getType()));

            }
        }
    }
}
