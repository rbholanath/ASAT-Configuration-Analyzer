package main.java;

import main.java.configanalysis.ConfigAnalysis;
import main.java.configreader.ConfigReader;
import main.java.parser.Parser;
import main.java.parser.implementations.RuboCopParser;
import main.java.resultwriter.CSVResultWriter;
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
        List<File> defaults = new ArrayList<>();
        List<File> classifications = new ArrayList<>();

        // Checkstyle
//        parsers.add(new CheckstyleParser());
//        directories.add(new File(System.getProperty("user.dir") + "/src/main/resources/checkstyle/"));
//
//        // PMD
//        parsers.add(new PMDParser());
//        directories.add(new File(System.getProperty("user.dir") + "/src/main/resources/pmd/"));
//
//        // FindBugs XML Filters
//        parsers.add(new FindBugsXMLParser());
//        directories.add(new File(System.getProperty("user.dir") + "/src/main/resources/findbugsxml/"));
//
//        // FindBugs Eclipse preference files
//        parsers.add(new FindBugsPrefsParser());
//        directories.add(new File(System.getProperty("user.dir") + "/src/main/resources/findbugsprefs/"));
//
//        // JSHint
//        parsers.add(new JSHintParser());
//        directories.add(new File(System.getProperty("user.dir") + "/src/main/resources/jshint/"));
//
//        // JSCS
//        parsers.add(new JSCSParser());
//        directories.add(new File(System.getProperty("user.dir") + "/src/main/resources/jscs/"));
//
//        // ESLint
//        parsers.add(new ESLintParser());
//        directories.add(new File(System.getProperty("user.dir") + "/src/main/resources/eslint/"));
//
//        // JSL
//        parsers.add(new JSLParser());
//        directories.add(new File(System.getProperty("user.dir") + "/src/main/resources/jsl/"));
//
//        // Pylint
//        parsers.add(new PylintParser());
//        directories.add(new File(System.getProperty("user.dir") + "/src/main/resources/pylint/"));

        // Rubocop
        parsers.add(new RuboCopParser());
        directories.add(new File(System.getProperty("user.dir") + "/src/main/resources/rubocop/"));
        defaults.add(new File(System.getProperty("user.dir") + "/src/main/resources/rubocop/rubocop.yml"));
        classifications.add(new File(System.getProperty("user.dir") + "/src/main/resources/rubocop/rubocop.txt"));

        AnalyzerLogger.getLogger().log(Level.INFO, "Reading config files");

        List<ConfigAnalysis> result = ConfigReader.read(parsers, directories, defaults, classifications);

        AnalyzerLogger.getLogger().log(Level.INFO, "Reads successful");

        AnalyzerLogger.getLogger().log(Level.INFO, "Writing results to file");

        CSVResultWriter.writeResults(result, new File(System.getProperty("user.dir") + "/out/"));

        AnalyzerLogger.getLogger().log(Level.INFO, "Written results from all tools to file");
    }
}
