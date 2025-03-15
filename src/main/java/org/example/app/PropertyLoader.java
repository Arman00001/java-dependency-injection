package org.example.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PropertyLoader {
    private static Map<String, String> properties= new HashMap<>();

    static {
        try(BufferedReader bf = new BufferedReader(new FileReader("src/main/resources/application.properties"))){
            while(true){
                String el = bf.readLine();
                if(el==null) break;
                if(el.isEmpty()) continue;
                String[] keyVal = el.split("=");
                properties.put(keyVal[0],keyVal[1]);
            }
        } catch (IOException e){
            throw new RuntimeException("Failed to load properties");
        }
    }

    public static String getProperties(String key){
        return properties.get(key);
    }
}
