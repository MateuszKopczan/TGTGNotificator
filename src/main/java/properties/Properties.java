package properties;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.util.HashMap;
import java.util.Map;

public class Properties {

    public static Map<String, String> DEFAULT_VALUES = new HashMap<>() {{
        put("latitude", "52.237049");
        put("longitude", "21.017532");
        put("radius", "25");
        put("min_watcher_interval", "1");
        put("max_watcher_interval", "5");
        put("console_print", "true");
        put("windows_notifications", "true");
    }};

    public static void saveConfigProperty(String key, String value){
        PropertiesConfiguration config = new PropertiesConfiguration();
        try{
            config.load(Properties.class.getClassLoader().getResourceAsStream("user.properties"));
            config.setProperty(key, value);
            config.save(Properties.class.getClassLoader().getResourceAsStream("user.properties").toString());
            System.setProperty(key, value);
        } catch (ConfigurationException ex){
            ex.printStackTrace();
            System.err.println("[ERROR] Something went wrong. Try again later.");
            System.exit(-1);
        }
    }

    public static void loadUserCredentials(){
        PropertiesConfiguration config = new PropertiesConfiguration();
        try{
            config.load(Properties.class.getClassLoader().getResourceAsStream("user.properties"));
            System.setProperty("accessToken", config.getString("accessToken", null));
            System.setProperty("refreshToken", config.getString("refreshToken", null));
            System.setProperty("userId", config.getString("userId", null));
            System.setProperty("email", config.getString("email", null));
        } catch (ConfigurationException ex){
            System.err.println("[ERROR] Something went wrong. Try again later.");
            System.exit(-1);
        }
    }

    public static void loadConfig(){
        PropertiesConfiguration config = new PropertiesConfiguration();
        try{
            config.load(Properties.class.getClassLoader().getResourceAsStream("config.properties"));
            System.setProperty("latitude", getConfigFloatProperty("latitude", config));
            System.setProperty("longitude", getConfigFloatProperty("longitude", config));
            System.setProperty("radius", getConfigIntProperty("radius", config));
            System.setProperty("min_watcher_interval", getConfigIntProperty("min_watcher_interval", config));
            System.setProperty("max_watcher_interval", getConfigIntProperty("max_watcher_interval", config));
            System.setProperty("console_print", getConfigBooleanProperty("console_print", config));
            System.setProperty("windows_notifications", getConfigBooleanProperty("windows_notifications", config));
        } catch (ConfigurationException ex){
            System.err.println("[ERROR] Something went wrong. Try again later.");
            System.exit(-1);
        }
    }

    private static String getConfigFloatProperty(String key, PropertiesConfiguration config){
        String defaultValue = getConfigPropertyDefaultValue(key);
        try{
            config.load(Properties.class.getClassLoader().getResourceAsStream("config.properties"));
            String value = config.getString(key);
            try{
                Float.parseFloat(value);
            } catch (NumberFormatException ex){
                System.err.println("[WARNING] Invalid " + key  + ". Run with default: " + defaultValue);
                return defaultValue;
            }
            return value;
        } catch (ConfigurationException ex){
            System.err.println("[WARNING] Something went wrong while loading configuration.");
            System.err.println("[WARNING] Check the config.properties file and try again");
            System.err.println("[WARNING] Invalid " + key  + ". Run with default: " + defaultValue);
            return defaultValue;
        }

    }

    private static String getConfigIntProperty(String key, PropertiesConfiguration config){
        String defaultValue = getConfigPropertyDefaultValue(key);
        try{
            config.load(Properties.class.getClassLoader().getResourceAsStream("config.properties"));
            String value = config.getString(key);
            try{
                Integer.parseInt(value);
            } catch (NumberFormatException ex){
                System.err.println("[WARNING] Invalid " + key  + ". Run with default: " + defaultValue);
                return defaultValue;
            }
            return value;
        } catch (ConfigurationException ex){
            System.err.println("[WARNING] Something went wrong while loading configuration.");
            System.err.println("[WARNING] Check the config.properties file and try again");
            System.err.println("[WARNING] Invalid " + key  + ". Run with default: " + defaultValue);
            return defaultValue;
        }
    }

    private static String getConfigBooleanProperty(String key, PropertiesConfiguration config){
        String defaultValue = getConfigPropertyDefaultValue(key);
        try{
            config.load(Properties.class.getClassLoader().getResourceAsStream("config.properties"));
            String value = config.getString(key);
            if(value.isEmpty()) {
                System.err.println("[WARNING] Invalid " + key  + ". Run with default: true");
                return "true";
            }
            else
                return String.valueOf(Boolean.parseBoolean(value));
        } catch (ConfigurationException ex){
            System.err.println("[WARNING] Something went wrong while loading configuration.");
            System.err.println("[WARNING] Check the config.properties file and try again");
            System.err.println("[WARNING] Invalid " + key  + ". Run with default: " + defaultValue);
            return defaultValue;
        }
    }

    private static String getConfigPropertyDefaultValue(String key){
        return DEFAULT_VALUES.get(key);
    }
}
