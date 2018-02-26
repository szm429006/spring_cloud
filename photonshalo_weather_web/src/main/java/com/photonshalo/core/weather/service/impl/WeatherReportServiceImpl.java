package com.photonshalo.core.weather.service.impl;

import com.photonshalo.core.weather.service.WeatherReportService;
import com.photonshalo.core.weather.vo.Forecast;
import com.photonshalo.core.weather.vo.Weather;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherReportServiceImpl implements WeatherReportService {

    @Override
    public Weather getDataByCityId(String cityId) {
        // TODO 改为天气数据api提供数据
        Weather data = new Weather();
        data.setAqi("81");
        data.setCity("深圳");
        data.setGanmao("容易感冒");
        data.setWendu("22");

        List<Forecast> forecastList = new ArrayList<Forecast>();
        Forecast forecast = new Forecast();
        forecast.setDate("29日星期日");
        forecast.setType("晴");
        forecast.setFengxiang("西北风");
        forecast.setFengli("3~4级");
        forecast.setHigh("高温11度");
        forecast.setLow("低温1度");
        forecastList.add(forecast);

        Forecast forecast2 = new Forecast();
        forecast2.setDate("28日星期六");
        forecast2.setType("晴");
        forecast2.setFengxiang("西北风");
        forecast2.setFengli("3~4级");
        forecast2.setHigh("高温11度");
        forecast2.setLow("低温1度");
        forecastList.add(forecast2);

        Forecast forecast3 = new Forecast();
        forecast3.setDate("27日星期五");
        forecast3.setType("晴");
        forecast3.setFengxiang("西北风");
        forecast3.setFengli("3~4级");
        forecast3.setHigh("高温11度");
        forecast3.setLow("低温1度");
        forecastList.add(forecast3);

        data.setForecast(forecastList);
        return data;
    }
}
