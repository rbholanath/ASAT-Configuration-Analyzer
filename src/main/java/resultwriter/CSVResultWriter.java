package main.java.resultwriter;

import main.java.configanalysis.ConfigAnalysis;
import main.java.util.AnalyzerLogger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class CSVResultWriter
{
    public static void writeResults(final List<ConfigAnalysis> results, final File directory)
    {
        for (ConfigAnalysis result : results)
        {
            try
            {
                Path resultFileOcc = Paths.get(directory.getAbsolutePath() + "/csv/" + result.getToolName() + "occ.csv");

                BufferedWriter bufferedWriter = Files.newBufferedWriter(resultFileOcc, Charset.forName("UTF-8"));

                for (Map.Entry<String, Integer> entry : result.getOccurrences().entrySet())
                {
                    bufferedWriter.write(entry.getKey() + "," + entry.getValue());
                    bufferedWriter.newLine();
                }

                bufferedWriter.close();

                Path resultFileExcl = Paths.get(directory.getAbsolutePath() + "/csv/" + result.getToolName() + "excl.csv");

                BufferedWriter bufferedWriterExcl = Files.newBufferedWriter(resultFileExcl, Charset.forName("UTF-8"));

                for (Map.Entry<String, Integer> entry : result.getExclusions().entrySet())
                {
                    bufferedWriterExcl.write(entry.getKey() + "," + entry.getValue());
                    bufferedWriterExcl.newLine();
                }

                bufferedWriterExcl.close();

                AnalyzerLogger.getLogger().log(Level.FINE, "Successfully written results from: " + result.getToolName() + " to file.");


            }
            catch (IOException e)
            {
                AnalyzerLogger.getLogger().log(Level.SEVERE, e.getMessage());
            }
        }
    }
}
