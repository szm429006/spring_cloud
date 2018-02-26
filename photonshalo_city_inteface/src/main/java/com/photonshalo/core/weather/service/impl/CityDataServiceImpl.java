package com.photonshalo.core.weather.service.impl;

import com.photonshalo.core.weather.service.CityDataService;
import com.photonshalo.core.weather.util.XmlBuilder;
import com.photonshalo.core.weather.vo.City;
import com.photonshalo.core.weather.vo.CityList;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class CityDataServiceImpl implements CityDataService {

    @Override
    public List<City> listCity() throws Exception {
        //读取XML文件
        Resource resource = new ClassPathResource("citylist.xml");
        BufferedReader buffRead = new BufferedReader(new InputStreamReader(resource.getInputStream(), "utf-8"));

        StringBuffer strBuffer = new StringBuffer();
        String line = "";
        while ((line = buffRead.readLine()) != null) {
            strBuffer.append(line);
        }
        buffRead.close();
        //XML转化为对象
        CityList cityList = (CityList) XmlBuilder.xmlStrToObject(CityList.class, strBuffer.toString());
        return cityList.getCityList();
    }
}
