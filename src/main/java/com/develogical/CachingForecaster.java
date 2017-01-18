package com.develogical;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Forecaster;
import com.weather.Region;

public class CachingForecaster {
    private final Cacher cache = new Cacher();
    ForecasterAdapter forecasterAdapter;
    public static long systemClockInMiliseconds;
    public CachingForecaster(ForecasterAdapter forecasterAdapter){
        this.forecasterAdapter = forecasterAdapter;
        this.systemClockInMiliseconds = System.currentTimeMillis();
    }

    public Forecast getForecastFaster(Region region, Day day){
        CacheObject cacheItem = cache.get(region, day);

        Forecast result = null;

        if(cacheItem.timeToLive != null && cacheItem.timeToLive > this.systemClockInMiliseconds)
                result = cacheItem.forecast;

        if(result == null) {
            //Forecaster forecaster = new Forecaster();
            result = forecasterAdapter.forecastFor(region, day);
            cacheItem.region = region;
            cacheItem.day = day;
            cacheItem.forecast = result;
            cacheItem.timeToLive = this.systemClockInMiliseconds + (60*60*1000);
            cache.add(cacheItem);
        }
        return result;
    }

    public void expireTheCache(Region region, Day day){


    }
}
