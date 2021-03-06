package config;

import java.io.FileInputStream;
import java.util.Properties;

/*
    Loads needed settings for the application.
*/
public class Settings {
    public static String database = null;
    public static String downloadFolder = null;
    public static String logPath = null;
    public static String stopWordsPath = null;
    public static String blackListFile = null;

    public static void load() {
        try {
            Properties props = new Properties();
            FileInputStream fIS = new FileInputStream("./config/settings.properties");
            props.load(fIS);
            String userDir = System.getProperty("user.home");
            database = props.getProperty("databaseName").replace("~", userDir);
            downloadFolder = props.getProperty("downloadFolder").replace("~", userDir);
            logPath = props.getProperty("logPath").replace("~", userDir);
            stopWordsPath = props.getProperty("stopWordsPath").replace("~", userDir);
            blackListFile = props.getProperty("blackListFile").replace("~", userDir);
            fIS.close();
        } catch (Exception e) {
            System.out.println("Configuration file doesn't exists.");
            // TODO Log error if config file can not be load
        }
    }
}
