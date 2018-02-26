package com.photonshalo.core.weather.job;

import com.photonshalo.core.weather.service.CityDataService;
import com.photonshalo.core.weather.service.WeatherDataService;
import com.photonshalo.core.weather.vo.City;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;

public class WeatherDataSyncJob extends QuartzJobBean {

    private static final Logger logger = LoggerFactory.getLogger(WeatherDataSyncJob.class);

    @Autowired
    private CityDataService cityDataService;

    @Autowired
    private WeatherDataService weatherDataService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        logger.info("Weather Data Sync Job Start!");
        List<City> cityList = null;
        try{
            cityList = cityDataService.listCity();
        } catch (Exception e) {
            logger.error("Weather Sync Exception in: " + e);
        }
        // 遍历城市ID获取天气
        for (City city : cityList) {
            String cityId = city.getCityId();
            weatherDataService.syncDataByCityId(cityId);
            logger.info("Weather Data Sync Job, cityId:" + cityId);
        }
        logger.info("Weather Data Sync Job Finished!");
    }
}
