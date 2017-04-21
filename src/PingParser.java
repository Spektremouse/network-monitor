import java.io.*;
import java.util.ArrayList;

/**
 * Created by Thomas Pachico on 21/04/2017.
 */
public class PingParser
{
    private static final String PING_LOC = "\\ping";
    private static final String FILE_HEADER = "Hostname,Received,Transmitted,Loss Rate,Total Time,RTTMin,RTTMax,"+
            "RTTMean,RTTMedian,RTTDeviation,Date,Time";
    private static final String FILE_LOCATION = "C:\\Users\\Thomas Pachico\\Documents\\UEA\\Networks Monitor\\PingData.csv";

    private BufferedReader br = null;
    private FileReader fr = null;
    private ArrayList<String> mDirectoryList = new ArrayList<>();
    private Filter mFilter = new Filter();

    public PingParser(ArrayList<String> directoryList)
    {
        for(int x = 0; x < directoryList.size(); x++)
        {
            StringBuilder sb = new StringBuilder();
            sb.append(directoryList.get(x)).append(PING_LOC);
            File [] pingList = mFilter.textFinder(sb.toString());
            for (File pingFile : pingList)
            {
                mDirectoryList.add(pingFile.toString());
            }
        }
    }

    public void readFiles()
    {
        FileWriter writer = null;
        try
        {
            writer = new FileWriter(FILE_LOCATION);
            writer.append(FILE_HEADER);
            for(String textFile : mDirectoryList)
            {
                //read the file
                //take what we want
                //save it to a csv
                boolean saveNextLines = false;
                StringBuilder sb = new StringBuilder();
                ArrayList<Double> medianData = new ArrayList<>();
                String [] temp = extractDate(textFile);

                sb.append(temp[0]).append(" ").append(temp[1]);

                try
                {
                    fr = new FileReader(textFile);
                    br = new BufferedReader(fr);

                    String sCurrentLine;

                    while((sCurrentLine = br.readLine()) != null)
                    {
                        if(sCurrentLine.contains("time="))
                        {
                            temp = sCurrentLine.split("time=",2);
                            String unit = temp[1];
                            temp = unit.split(" ",2);
                            unit = temp[0];

                            medianData.add(Double.parseDouble(unit));
                        }

                        if(sCurrentLine.contains("---"))
                        {
                            saveNextLines = true;
                        }
                        if(saveNextLines)
                        {
                            sb.append(sCurrentLine);
                        }
                    }

                    PingData data = new PingData();
                    data.parseData(sb.toString());
                    data.findMedian(medianData);

                    writer.append("\n");
                    writer.append(data.toString());
                }
                catch(IOException ex)
                {
                    ex.printStackTrace();
                }
                finally
                {
                    try {
                        if (br != null)
                        {
                            br.close();
                        }
                        if (fr != null)
                        {
                            fr.close();
                        }
                    }
                    catch (IOException ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                if(writer != null)
                {
                    writer.close();
                }
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
    }

    private String[] extractDate(String file)
    {
        String date;
        String time;
        String dateTime;

        String[] temp = file.split("\\\\");

        dateTime = temp[temp.length - 1];

        temp = dateTime.split("\\.");

        dateTime = temp[0];

        temp = dateTime.split("@");

        date = temp[0];
        time = temp[temp.length-1];

        String [] result = {date, time};
        return result;
    }
}