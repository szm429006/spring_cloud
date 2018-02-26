package com.photonshalo.core.weather.service;

import com.photonshalo.core.weather.vo.Weather;

public interface WeatherReportService {

    Weather getDataByCityId(String cityId);
}
