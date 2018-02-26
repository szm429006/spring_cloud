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
    private static final long TIME_OUT = 1800L;  //默认1800s

    @Autowired
    private RestTemplate restTemplate;

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
        logger.info(" Success write in key: " + key + " and weather: ", strBody);
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
            // 没有命中则调用三方接口
            ResponseEntity<String> respString = restTemplate.getForEntity(uri, String.class);
            if(respString.getStatusCodeValue() == 200) {
                strBody = respString.getBody();
            }
            //数据写入redis
            ops.set(key, strBody, TIME_OUT, TimeUnit.SECONDS);
            logger.info("Weather Redis Not Cache in key: " + key + " Success write weather: ", strBody);
        }
        try{
            resp = mapper.readValue(strBody, WeatherResponse.class);
        } catch (IOException e) {
            logger.error("Weather Redis Exception in: " + e);
        }
        return resp;
    }
}
