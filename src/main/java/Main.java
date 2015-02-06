package main.java;

import main.java.configanalysis.ConfigAnalysis;
import main.java.configreader.ConfigReader;
import main.java.parser.Parser;
import main.java.parser.implementations.CheckstyleParser;
import main.java.parser.implementations.FindBugsParser;
import main.java.parser.implementations.JSHintParser;
import main.java.parser.implementations.PMDParser;
import main.java.resultwriter.ResultWriter;
import main.java.util.AnalyzerLogger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Main
{
    public static void main(String[] args)
    {
        List<Parser> parsers = new ArrayList<>();
        List<File> directories = new ArrayList<>();

        // Checkstyle
        parsers.add(new CheckstyleParser());
        directories.add(new File(System.getProperty("user.dir") + "/src/main/resources/checkstyle/"));

        // PMD
        parsers.add(new PMDParser());
        directories.add(new File(System.getProperty("user.dir") + "/src/main/resources/pmd/"));

        // FindBugs
        parsers.add(new FindBugsParser());
        directories.add(new File(System.getProperty("user.dir") + "/src/main/resources/findbugs/"));

        // JSHint
        parsers.add(new JSHintParser());
        directories.add(new File(System.getProperty("user.dir") + "/src/main/resources/jshint/"));

        AnalyzerLogger.getLogger().log(Level.INFO, "Reading config files");

        List<ConfigAnalysis> result = ConfigReader.read(parsers, directories);

        AnalyzerLogger.getLogger().log(Level.INFO, "Reads successful");

        AnalyzerLogger.getLogger().log(Level.INFO, "Writing results to file");

        ResultWriter.writeResults(result, new File(System.getProperty("user.dir") + "/out/"));

        AnalyzerLogger.getLogger().log(Level.INFO, "Written results from all tools to file");
    }
}
