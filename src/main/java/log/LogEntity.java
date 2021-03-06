package log;

import config.Settings;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/*
    Takes responsibility of the logging
    for the application.
*/
public class LogEntity {

    public static Logger logger;

    public LogEntity() {
        Settings.load();
        logger = Logger.getLogger("AragogLog");
        logger.setUseParentHandlers(false);
        FileHandler fh;
        String logFile = Settings.logPath + "aragog.log";
        try {
            fh = new FileHandler(logFile, true);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (IOException e) {
            System.out.println("Error during logger creation.");
        }
    }

    /*
        Writes given message in the logs file.
    */
    public void write(String msg) {
        logger.info(msg);
    }

}
