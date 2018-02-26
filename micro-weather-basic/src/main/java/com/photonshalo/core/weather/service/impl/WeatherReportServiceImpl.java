package com.photonshalo.core.weather.service.impl;

import com.photonshalo.core.weather.service.WeatherDataService;
import com.photonshalo.core.weather.service.WeatherReportService;
import com.photonshalo.core.weather.vo.Weather;
import com.photonshalo.core.weather.vo.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherReportServiceImpl implements WeatherReportService {

    @Autowired
    private WeatherDataService weatherDataService;

    @Override
    public Weather getDataByCityId(String cityId) {
        WeatherResponse resp = weatherDataService.getDataByCityId(cityId);
        return resp.getData();
    }
}
