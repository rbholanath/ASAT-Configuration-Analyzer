package main.java;

import main.java.configanalysis.ConfigAnalysis;
import main.java.configreader.ConfigReader;
import main.java.parser.Parser;
import main.java.parser.implementations.CheckstyleParser;

import java.util.ArrayList;
import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("--- Working Directory = " + System.getProperty("user.dir"));
        List<Parser> parsers = new ArrayList<>();
        List<String> directories = new ArrayList<>();

        // Checkstyle
        parsers.add(new CheckstyleParser());
        directories.add(System.getProperty("user.dir") + "/src/main/resources/checkstyle/");

        System.out.println("--- Reading config files");

        List<ConfigAnalysis> result = ConfigReader.read(parsers, directories);

        System.out.println("--- Reads successful");
    }
}
