package org.example.infrastructure.cache;


import java.util.HashMap;
import java.util.Map;

public class CacheManager {
    private static Map<String, Object> cache = new HashMap<>();

    public static Object getFromCache(String key){
        return cache.getOrDefault(key,null);
    }

    public static void putInCache(String key, Object value){
        cache.put(key,value);
    }

}
