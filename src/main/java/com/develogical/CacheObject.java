package com.develogical;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Region;

public class CacheObject {
    public Region region;
    public Day day;
    public Forecast forecast;
    public Long timeToLive;

    @Override
    public boolean equals(Object object){
        return equals((CacheObject) object);
    }

    private boolean equals(CacheObject object){
        return object != null && this.region == object.region && this.day == object.day;
    }

    @Override
    public int hashCode() {
        return region.hashCode() + day.hashCode();
    }
}
