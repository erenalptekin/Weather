package com.develogical;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Region;

import java.util.*;

public class Cacher {
    private final List<CacheObject> cache = new ArrayList<>();

    public void add(CacheObject object){
        if(cache.contains(object))
            cache.remove(object);
        cache.add(object);
    }

    public CacheObject get(Region region, Day day){
        CacheObject object = new CacheObject();
        object.day = day;
        object.region = region;
        object.forecast = null;
        int index = cache.indexOf(object);
        if(index >= 0){
            CacheObject obj = cache.get(index);
            object.forecast = obj.forecast;
            object.timeToLive = obj.timeToLive;
        }
        return object;
    }
}
