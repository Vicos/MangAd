package wow;

import java.io.FileNotFoundException;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WoWconfig {
    
    final String configFile = "conf.ini";
    
    static WoWconfig self;
    Properties config;
    
    public WoWconfig() {
        try {
            FileInputStream in = new FileInputStream(configFile);
            config = new Properties();
            config.load(in);
            in.close();
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
    
    public void SetS(String key, String value) {
        config.setProperty(key, value);
    }
    
    public void save() {
        FileOutputStream out;
        try {
            out = new FileOutputStream(configFile);
            config.store(out, null);
            out.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}