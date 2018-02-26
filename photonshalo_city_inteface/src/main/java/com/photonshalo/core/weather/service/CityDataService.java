package com.photonshalo.core.weather.service;

import com.photonshalo.core.weather.vo.City;

import java.util.List;

public interface CityDataService {

    List<City> listCity() throws Exception;
}
