package com.photonshalo.core.weather.controller;

import com.photonshalo.core.weather.service.CityDataService;
import com.photonshalo.core.weather.vo.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/city")
public class CityController {

    @Autowired
    private CityDataService cityDataService;

    @GetMapping("/list")
    public List<City> listCity() throws Exception {
        return cityDataService.listCity();
    }
}
