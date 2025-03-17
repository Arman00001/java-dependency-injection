package org.example.infrastructure.proxywrapper;

import net.sf.cglib.proxy.Enhancer;
import org.example.infrastructure.annotation.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class LogAnnotationProxyWrapper implements ProxyWrapper {

    @Override
    @SuppressWarnings("unchecked")
    public <T> T wrap(T obj, Class<T> cls) {
        if (!cls.isAnnotationPresent(Log.class)) {
            boolean checkAnnots = false;
            for (Method method : cls.getDeclaredMethods()) {
                if(method.isAnnotationPresent(Log.class)) {
                    checkAnnots=true;
                    break;
                }
            }
            if(!checkAnnots)
                return obj;
        }

        if (cls.getInterfaces().length != 0) {
            return (T) Proxy.newProxyInstance(
                    cls.getClassLoader(),
                    cls.getInterfaces(),
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            return invokationHandler(method, args, cls, obj);
                        }
                    }
            );
        }

        return (T) Enhancer.create(
                cls,
                new net.sf.cglib.proxy.InvocationHandler() {
                    @Override
                    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
                        return invokationHandler(method, args, cls, obj);
                    }
                }
        );
    }

    private static <T> Object invokationHandler(Method method, Object[] args, Class<T> cls, T obj) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if(cls.isAnnotationPresent(Log.class) ||
                cls.getDeclaredMethod(method.getName(), method.getParameterTypes()).isAnnotationPresent(Log.class))
            System.out.printf(
                    "Calling method: %s. Args: %s\n", method.getName(), Arrays.toString(args));

        return method.invoke(obj, args);
    }

}
