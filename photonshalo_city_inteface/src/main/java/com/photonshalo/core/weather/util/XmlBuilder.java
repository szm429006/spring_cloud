package com.photonshalo.core.weather.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.Reader;
import java.io.StringReader;

public class XmlBuilder {

    /**
     * 将XML转化为指定对象
     * @param className
     * @param xmlStr
     * @return
     * @throws Exception
     */
    public static Object xmlStrToObject(Class<?> className, String xmlStr) throws Exception {
        Object xmlObject = null;
        Reader reader = null;
        JAXBContext context = JAXBContext.newInstance(className);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        reader = new StringReader(xmlStr);
        xmlObject = unmarshaller.unmarshal(reader);
        if(reader != null) {
            reader.close();
        }
        return xmlObject;
    }
}
