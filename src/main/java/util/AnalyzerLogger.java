package main.java.util;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class AnalyzerLogger
{
    private final static Logger logger = Logger.getLogger(AnalyzerLogger.class.getName());

    private final File logDirectory = new File(System.getProperty("user.dir") + "/out/log/");

    private static AnalyzerLogger instance;

    private AnalyzerLogger()
    {
        try
        {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmm");
            LocalDateTime dateTime = LocalDateTime.now();
            String now = dateTime.format(formatter);

            logDirectory.mkdirs();

            FileHandler fileHandler = new FileHandler(logDirectory.getAbsolutePath() + "/" + now + "-log.log");
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
        }
        catch (IOException e)
        {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    public static Logger getLogger()
    {
        if (instance == null)
        {
            instance = new AnalyzerLogger();
        }

        return logger;
    }
}
