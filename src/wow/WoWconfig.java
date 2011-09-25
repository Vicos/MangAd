package wow;

import java.util.Properties;
import java.io.FileInputStream;

public class WoWconfig {
    
    final String configFile = "conf.ini";
    
    static WoWconfig self;
    Properties config;
    
    public WoWconfig() {
        try {
            config = new Properties();
            config.load(new FileInputStream(configFile));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static WoWconfig self() {
        if (self == null)
            self = new WoWconfig();
        return self;
    }
    
    public String GetS(String key) {
        return config.getProperty(key);
    }
}