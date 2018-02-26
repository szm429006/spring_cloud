package com.photonshalo.core.weather.job;

import com.photonshalo.core.weather.service.WeatherDataCollectionService;
import com.photonshalo.core.weather.vo.City;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.ArrayList;
import java.util.List;

public class WeatherDataSyncJob extends QuartzJobBean {

    private static final Logger logger = LoggerFactory.getLogger(WeatherDataSyncJob.class);

    @Autowired
    private WeatherDataCollectionService weatherDataCollectionService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        logger.info("Weather Data Sync Job Start!");
        List<City> cityList = null;
        try{
            cityList = new ArrayList<>();
            City city = new City();
            city.setCityId("101280601");
            cityList.add(city);
        } catch (Exception e) {
            logger.error("Weather Sync Exception in: " + e);
        }
        // 遍历城市ID获取天气
        for (City city : cityList) {
            String cityId = city.getCityId();
            weatherDataCollectionService.syncDataByCityId(cityId);
            logger.info("Weather Data Sync Job, cityId:" + cityId);
        }
        logger.info("Weather Data Sync Job Finished!");
    }
}
