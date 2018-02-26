package com.photonshalo.core.weather.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.photonshalo.core.weather.service.WeatherDataService;
import com.photonshalo.core.weather.vo.WeatherResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class WeatherDataServiceImpl implements WeatherDataService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherDataServiceImpl.class);
    private static final String WEATHER_URI = "http://wthrcdn.etouch.cn/weather_mini?";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public WeatherResponse getDataByCityId(String cityId) {
        String uri = WEATHER_URI + "citykey=" + cityId;
        return this.doGetWeather(uri);
    }

    @Override
    public WeatherResponse getDataByCityName(String cityName) {
        String uri = WEATHER_URI + "city=" + cityName;
        return this.doGetWeather(uri);
    }

    private WeatherResponse doGetWeather(String uri) {
        String key = uri;
        String strBody = null;
        ObjectMapper mapper = new ObjectMapper();
        WeatherResponse resp = null;
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        //先访问 redis 缓存
        if(stringRedisTemplate.hasKey(key)) {
            strBody = ops.get(key);
            logger.info("Weather Redis Cached in key: ", key);
        } else {
            logger.info("Weather Redis Not Cache in key: " + key);
            throw new RuntimeException("do not has data");
        }
        try{
            resp = mapper.readValue(strBody, WeatherResponse.class);
        } catch (IOException e) {
            logger.error("Weather Redis Exception in: " + e);
        }
        return resp;
    }
}
