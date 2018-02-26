package com.photonshalo.core.weather.controller;

import com.photonshalo.core.weather.service.WeatherReportService;
import com.photonshalo.core.weather.vo.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/report")
public class WeatherReportController {

    @Autowired
    private WeatherReportService weatherReportService;

    @GetMapping("/cityId")
    public ModelAndView getReportByCityId(@RequestParam String cityId, Model model) throws Exception {
        //TODO 改为由城市数据api提供数据

        List<City> cityList = null;
        cityList = new ArrayList<City>();
        City city = new City();
        city.setCityId("101280601");
        city.setCityName("深圳");
        cityList.add(city);

        model.addAttribute("title", "weather report");
        model.addAttribute("cityId", cityId);
        model.addAttribute("cityList", cityList);
        model.addAttribute("report", weatherReportService.getDataByCityId(cityId));
        return new ModelAndView("weather/report", "reportModel", model);
    }
}
