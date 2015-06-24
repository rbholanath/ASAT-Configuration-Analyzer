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
                File directoryToCreate = new File(directory.getAbsolutePath() + "/csv/");

                directoryToCreate.mkdirs();

                Path resultFileOcc = Paths.get(directory.getAbsolutePath() + "/csv/occ_" + result.getToolName() + ".csv");

                BufferedWriter bufferedWriter = Files.newBufferedWriter(resultFileOcc, Charset.forName("UTF-8"));

                for (Map.Entry<String, Integer> entry : result.getOccurrences().entrySet())
                {
                    bufferedWriter.write(entry.getKey() + "," + entry.getValue());
                    bufferedWriter.newLine();
                }

                bufferedWriter.close();

                Path resultFileExcl = Paths.get(directory.getAbsolutePath() + "/csv/excl_" + result.getToolName() + ".csv");

                BufferedWriter bufferedWriterExcl = Files.newBufferedWriter(resultFileExcl, Charset.forName("UTF-8"));

                for (Map.Entry<String, Integer> entry : result.getExclusions().entrySet())
                {
                    bufferedWriterExcl.write(entry.getKey() + "," + entry.getValue());
                    bufferedWriterExcl.newLine();
                }

                bufferedWriterExcl.close();

                Path resultFileDefaults = Paths.get(directory.getAbsolutePath() + "/csv/defaults_" + result.getToolName() + ".csv");

                BufferedWriter bufferedWriterDefaults = Files.newBufferedWriter(resultFileDefaults, Charset.forName("UTF-8"));

                bufferedWriterDefaults.write("Total," + result.getTotal());
                bufferedWriterDefaults.newLine();

                bufferedWriterDefaults.write("Deviations," + result.getDeviations());
                bufferedWriterDefaults.newLine();

                bufferedWriterDefaults.write("Only Redefines," + result.getOnlyRedefines());
                bufferedWriterDefaults.newLine();

                bufferedWriterDefaults.write("Adds Rules," + result.getAddsRules());
                bufferedWriterDefaults.newLine();

                bufferedWriterDefaults.write("Uses Custom Rules," + result.getUsesCustomRules());
                bufferedWriterDefaults.newLine();

                bufferedWriterDefaults.close();

                AnalyzerLogger.getLogger().log(Level.FINE, "Successfully written results from: " + result.getToolName() + " to file.");
            }
            catch (IOException e)
            {
                AnalyzerLogger.getLogger().log(Level.SEVERE, e.getMessage());
            }
        }
    }
}
