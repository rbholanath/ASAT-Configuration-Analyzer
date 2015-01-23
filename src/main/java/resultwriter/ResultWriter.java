package main.java.resultwriter;

import main.java.configanalysis.ConfigAnalysis;
import main.java.util.AnalyzerLogger;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

public class ResultWriter
{
    public static void writeResults(final List<ConfigAnalysis> results, final File directory)
    {
        for (ConfigAnalysis result : results)
        {
            try
            {
                JSONObject jsonResult = new JSONObject();
                jsonResult.put("tool", result.getToolName());
                jsonResult.put("occurrences", result.getOccurrences());
                jsonResult.put("exclusions", result.getExclusions());

                FileWriter file = new FileWriter(directory.getAbsolutePath() + "/" + result.getToolName() + ".json");

                file.write(jsonResult.toString(4));
                AnalyzerLogger.getLogger().log(Level.FINE, "Successfully written results from: " + result.getToolName() + " to file.");

                file.flush();
                file.close();

            }
            catch (IOException | JSONException e)
            {
                AnalyzerLogger.getLogger().log(Level.SEVERE, e.getMessage());
            }
        }
    }
}
