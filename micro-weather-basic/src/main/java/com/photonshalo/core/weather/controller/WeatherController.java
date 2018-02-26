package com.photonshalo.core.weather.controller;

import com.photonshalo.core.weather.service.WeatherDataService;
import com.photonshalo.core.weather.vo.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherDataService weatherDataService;

    @GetMapping("/cityId")
    public WeatherResponse getWeatherByCityId(@RequestParam String cityId) {
        return weatherDataService.getDataByCityId(cityId);
    }

    @GetMapping("/cityName")
    public WeatherResponse getWeatherByCityName(@RequestParam String cityName) {
        return weatherDataService.getDataByCityName(cityName);
    }
}
