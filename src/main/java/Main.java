package main.java;

import main.java.configanalysis.ConfigAnalysis;
import main.java.configreader.ConfigReader;
import main.java.parser.ConfigParser;
import main.java.parser.implementations.*;
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
        List<ConfigParser> parsers = new ArrayList<>();
        List<File> directories = new ArrayList<>();
        List<File> defaults = new ArrayList<>();
        List<File> classifications = new ArrayList<>();
        List<File> redefines = new ArrayList<>();

//        // Checkstyle
//        parsers.add(new CheckstyleParser());
//        directories.add(new File(System.getProperty("user.dir") + "/src/main/resources/checkstyle/"));
//
        // PMD
        parsers.add(new PMDParser());
        directories.add(new File(System.getProperty("user.dir") + "/src/main/resources/pmd/"));
        defaults.add(new File(System.getProperty("user.dir") + "/src/main/resources/pmd/default.xml"));
        classifications.add(new File(System.getProperty("user.dir") + "/src/main/resources/pmd/possible_pmd.csv"));
        redefines.add(new File(System.getProperty("user.dir") + "/src/main/resources/pmd/redefines_pmd.csv"));
//
//        // FindBugs XML Filters
//        parsers.add(new FindBugsXMLParser());
//        directories.add(new File(System.getProperty("user.dir") + "/src/main/resources/findbugsxml/"));
//
        // FindBugs Eclipse preference files
        parsers.add(new FindBugsPrefsParser());
        directories.add(new File(System.getProperty("user.dir") + "/src/main/resources/findbugsprefs/"));
        defaults.add(new File(System.getProperty("user.dir") + "/src/main/resources/findbugsprefs/.fbprefs"));
        classifications.add(new File(System.getProperty("user.dir") + "/src/main/resources/findbugsprefs/possible_findbugsprefs.csv"));
        redefines.add(new File(System.getProperty("user.dir") + "/src/main/resources/findbugsprefs/redefines_findbugsprefs.csv"));

        // JSHint
        parsers.add(new JSHintParser());
        directories.add(new File(System.getProperty("user.dir") + "/src/main/resources/jshint/"));
        defaults.add(new File(System.getProperty("user.dir") + "/src/main/resources/jshint/.jshintrc"));
        classifications.add(new File(System.getProperty("user.dir") + "/src/main/resources/jshint/possible_jshint.csv"));
        redefines.add(new File(System.getProperty("user.dir") + "/src/main/resources/jshint/redefines_jshint.csv"));
//
//        // JSCS
//        parsers.add(new JSCSParser());
//        directories.add(new File(System.getProperty("user.dir") + "/src/main/resources/jscs/"));
//
        // ESLint
        parsers.add(new ESLintParser());
        directories.add(new File(System.getProperty("user.dir") + "/src/main/resources/eslint/"));
        defaults.add(new File(System.getProperty("user.dir") + "/src/main/resources/eslint/.eslintrc"));
        classifications.add(new File(System.getProperty("user.dir") + "/src/main/resources/eslint/possible_eslint.csv"));
        redefines.add(new File(System.getProperty("user.dir") + "/src/main/resources/eslint/redefines_eslint.csv"));

        // JSL
        parsers.add(new JSLParser());
        directories.add(new File(System.getProperty("user.dir") + "/src/main/resources/jsl/"));
        defaults.add(new File(System.getProperty("user.dir") + "/src/main/resources/jsl/jsl.default.conf"));
        classifications.add(new File(System.getProperty("user.dir") + "/src/main/resources/jsl/possible_jsl.csv"));
        redefines.add(new File(System.getProperty("user.dir") + "/src/main/resources/jsl/redefines_jsl.csv"));

        // Pylint
        parsers.add(new PylintParser());
        directories.add(new File(System.getProperty("user.dir") + "/src/main/resources/pylint/"));
        defaults.add(new File(System.getProperty("user.dir") + "/src/main/resources/pylint/.pylintrc"));
        classifications.add(new File(System.getProperty("user.dir") + "/src/main/resources/pylint/possible_pylint.csv"));
        redefines.add(new File(System.getProperty("user.dir") + "/src/main/resources/pylint/redefines_pylint.csv"));

        // RuboCop
        parsers.add(new RuboCopParser());
        directories.add(new File(System.getProperty("user.dir") + "/src/main/resources/rubocop/"));
        defaults.add(new File(System.getProperty("user.dir") + "/src/main/resources/rubocop/rubocop.yml"));
        classifications.add(new File(System.getProperty("user.dir") + "/src/main/resources/rubocop/possible_rubocop.csv"));
        redefines.add(new File(System.getProperty("user.dir") + "/src/main/resources/rubocop/redefines_rubocop.csv"));

        AnalyzerLogger.getLogger().log(Level.INFO, "Reading config files");

        List<ConfigAnalysis> result = ConfigReader.read(parsers, directories, defaults, classifications, redefines);

        AnalyzerLogger.getLogger().log(Level.INFO, "Reads successful");

        AnalyzerLogger.getLogger().log(Level.INFO, "Writing results to file");

        CSVResultWriter.writeResults(result, new File(System.getProperty("user.dir") + "/out/"));

        AnalyzerLogger.getLogger().log(Level.INFO, "Written results from all tools to file");
    }
}
