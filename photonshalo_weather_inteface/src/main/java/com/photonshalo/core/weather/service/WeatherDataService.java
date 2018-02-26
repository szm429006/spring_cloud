package com.photonshalo.core.weather.service;

import com.photonshalo.core.weather.vo.WeatherResponse;

public interface WeatherDataService {

    WeatherResponse getDataByCityId(String cityId);

    WeatherResponse getDataByCityName(String cityName);
}
