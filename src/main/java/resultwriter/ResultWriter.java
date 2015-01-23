package main.java.resultwriter;

import main.java.configanalysis.ConfigAnalysis;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ResultWriter
{
    public static void writeResults(List<ConfigAnalysis> results, final String directory)
    {
        for (ConfigAnalysis result : results)
        {
            try
            {
                System.out.println("-- Writing results for: " + result.getToolName());

                JSONObject jsonResult = new JSONObject();
                jsonResult.put("tool", result.getToolName());
                jsonResult.put("occurences", result.getOccurrences());
                jsonResult.put("exclusions", result.getExclusions());

                FileWriter file = new FileWriter(directory + result.getToolName() + ".json");

                file.write(jsonResult.toString(4));
                System.out.println("-- Successfully written results from: " + result.getToolName() + " to file.");

                file.flush();
                file.close();

            }
            catch (IOException | JSONException e)
            {
                e.printStackTrace();
            }
        }
    }
}
