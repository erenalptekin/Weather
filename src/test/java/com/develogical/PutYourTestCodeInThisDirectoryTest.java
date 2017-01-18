package com.develogical;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Region;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class PutYourTestCodeInThisDirectoryTest {
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();


    @Test
    public void delegatesToForecasterAdapter() throws Exception{
        final ForecasterAdapter forecasterAdapter = context.mock(ForecasterAdapter.class);
        CachingForecaster ape = new CachingForecaster(forecasterAdapter);

        context.checking(new Expectations(){{
            exactly(1).of(forecasterAdapter).forecastFor(Region.LONDON, Day.MONDAY);
            will(returnValue(new Forecast("test", 10)));
        }});

        Forecast forecast = ape.getForecastFaster(Region.LONDON, Day.MONDAY);
        assertThat(forecast.summary(), equalTo("test"));
        assertThat(forecast.temperature(), equalTo(10));
    }

    @Test
    public void cachesResultFromForecasterAdapter() throws Exception{
        final ForecasterAdapter forecasterAdapter = context.mock(ForecasterAdapter.class);
        CachingForecaster ape = new CachingForecaster(forecasterAdapter);

        context.checking(new Expectations(){{
            exactly(1).of(forecasterAdapter).forecastFor(Region.LONDON, Day.MONDAY);
            will(returnValue(new Forecast("test", 10)));
        }});

        Forecast forecast = ape.getForecastFaster(Region.LONDON, Day.MONDAY);
        forecast = ape.getForecastFaster(Region.LONDON, Day.MONDAY);
        assertThat(forecast.summary(), equalTo("test"));
        assertThat(forecast.temperature(), equalTo(10));
    }

    @Test
    public void TimeToLive() throws Exception{
        final ForecasterAdapter forecasterAdapter = context.mock(ForecasterAdapter.class);
        CachingForecaster ape = new CachingForecaster(forecasterAdapter);

        context.checking(new Expectations(){{
            exactly(2).of(forecasterAdapter).forecastFor(Region.LONDON, Day.MONDAY);
            will(returnValue(new Forecast("test", 10)));
        }});

        Forecast forecast = ape.getForecastFaster(Region.LONDON, Day.MONDAY);
        ape.systemClockInMiliseconds = ape.systemClockInMiliseconds + (60*60*1000) + 1;
        forecast = ape.getForecastFaster(Region.LONDON, Day.MONDAY);
        assertThat(forecast.summary(), equalTo("test"));
        assertThat(forecast.temperature(), equalTo(10));
    }

}
