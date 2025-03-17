package org.example.infrastructure.configurator;

import lombok.SneakyThrows;
import org.example.infrastructure.ApplicationContext;
import org.example.infrastructure.annotation.Env;

import java.lang.reflect.Field;

public class EnvAnnotationObjectConfigurator implements ObjectConfigurator {
    @Override
    @SneakyThrows
    public void configure(Object obj, ApplicationContext context) {

        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(Env.class)) {
                field.setAccessible(true);
                String val = field.getAnnotation(Env.class).value();
                if(val.isEmpty())
                    val = field.getName();
                String variable = System.getenv(val);

                field.set(obj, variable);

            }
        }
    }
}
