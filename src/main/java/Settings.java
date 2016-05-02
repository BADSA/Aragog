import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class Settings {
    public static String database = null;
    public static String downloadFolder = null;
    public static String logPath = null;

    public static void load() {
        try {
            Properties props = new Properties();
            FileInputStream fIS = new FileInputStream("./settings.properties");
            props.load(fIS);

            database = props.getProperty("databaseName");
            downloadFolder = props.getProperty("downloadFolder");
            logPath = props.getProperty("logPath");

            fIS.close();
        } catch (Exception e) {
            System.out.println("Configuration file doesn't exists.");
            // TODO Log error if config file can not be load
        }
    }
}
