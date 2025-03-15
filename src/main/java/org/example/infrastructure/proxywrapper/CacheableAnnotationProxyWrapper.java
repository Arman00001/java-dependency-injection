package org.example.infrastructure.proxywrapper;

import net.sf.cglib.proxy.Enhancer;
import org.example.infrastructure.annotation.CacheKey;
import org.example.infrastructure.annotation.Cacheable;
import org.example.infrastructure.cache.CacheManager;

import java.lang.reflect.*;

public class CacheableAnnotationProxyWrapper implements ProxyWrapper {
    @Override
    @SuppressWarnings("unchecked")
    public <T> T wrap(T obj, Class<T> cls) {
        boolean cacheable = false;
        for (Method method : cls.getDeclaredMethods()) {
            if(method.isAnnotationPresent(Cacheable.class)) {
                cacheable=true;
                break;
            }
        }
        if(!cacheable) return obj;


        if (cls.getInterfaces().length != 0) {
            return (T) Proxy.newProxyInstance(
                    cls.getClassLoader(),
                    cls.getInterfaces(),
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            return invokationLogicHandler(method, args, cls, obj);
                        }
                    }
            );
        }

        return (T) Enhancer.create(
                cls,
                new net.sf.cglib.proxy.InvocationHandler() {
                    @Override
                    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
                        return invokationLogicHandler(method, args, cls, obj);
                    }
                }
        );
    }

    private static <T> Object invokationLogicHandler(Method method, Object[] args, Class<T> cls, T obj) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method m = cls.getDeclaredMethod(method.getName(), method.getParameterTypes());
        if(m.isAnnotationPresent(Cacheable.class))
        {
            StringBuilder parameters = new StringBuilder();
            for (int i = 0; i < m.getParameters().length; i++) {
                if(m.getParameters()[i].isAnnotationPresent(CacheKey.class))
                    parameters.append(args[i]);
            }

            String key = parameters.append(m.getName()).append(cls.getName()).toString();

            System.out.println("Looking up in Cache for the value");
            Object res = CacheManager.getFromCache(key);
            if(res==null) {
                res = method.invoke(obj, args);
                CacheManager.putInCache(key,res);
                System.out.println("Putting value in cache");
            }
            return res;
        }
        return method.invoke(obj, args);
    }

}
