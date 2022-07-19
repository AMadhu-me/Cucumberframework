package util;


import Base.GlobalMethods;

import java.io.FileReader;
import java.util.Properties;

public class PropertiesLoader {

GlobalMethods globalMethods = new GlobalMethods();
    public static Properties commonProp;
    public static Properties elementProp;
    public void commonConfigLoader() throws Exception{

        FileReader propFilePath = new FileReader("config/common.properties");
        FileReader elementspath = new FileReader("config/sit_elementlocators.properties");

        commonProp = globalMethods.loadProp(propFilePath);

        String site = commonProp.getProperty("environment");

        switch(site.toLowerCase())
        {
            case "sit" :
                elementProp= globalMethods.loadProp(elementspath);
                break;
        }
    }
}
