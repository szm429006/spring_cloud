package com.photonshalo.core.weather.service.impl;

import com.photonshalo.core.weather.service.WeatherDataCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Service
public class WeatherDataCollectionServiceImpl implements WeatherDataCollectionService {

    private static final String WEATHER_URI = "http://wthrcdn.etouch.cn/weather_mini?";
    private static final long TIME_OUT = 1800L;  //默认1800s

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void syncDataByCityId(String cityId) {
        String uri = WEATHER_URI + "citykey=" + cityId;
        this.cacheWeatherData(uri);
    }

    private void cacheWeatherData(String uri) {
        String key = uri;
        String strBody = null;
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ResponseEntity<String> respString = restTemplate.getForEntity(uri, String.class);
        if(respString.getStatusCodeValue() == 200) {
            strBody = respString.getBody();
        }
        //数据写入redis
        ops.set(key, strBody, TIME_OUT, TimeUnit.SECONDS);
    }
}
