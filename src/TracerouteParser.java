import java.io.*;
import java.util.ArrayList;

/**
 * Created by Thomas Pachico on 21/04/2017.
 */
public class TracerouteParser extends Parser
{
    private static final String TRACEROUTE_LOC = "\\traceroute";

    private BufferedReader br = null;
    private FileReader fr = null;
    private ArrayList<String> mDirectoryList = new ArrayList<>();
    private Filter mFilter = new Filter();

    private static final String FILE_HEADER = "Destination,Hop Count,Date,Time";
    private String mGeneratedHeaders = FILE_HEADER;

    private static final String FILE_LOCATION = "C:\\Users\\Thomas Pachico\\Documents\\UEA\\Networks Monitor\\TracerouteData.csv";

    public TracerouteParser(ArrayList<String> directoryList)
    {
        ArrayList<String> tracerouteList = new ArrayList<>();
        //Searches the directory list for folders containing a traceroute folder
        for(int x = 0; x < directoryList.size(); x++)
        {
            Filter fil = new Filter();
            File[] rootlist = fil.folderFinder(directoryList.get(x));

            for (File folder : rootlist ) {
                if(folder.toString().contains("traceroute"))
                {
                    tracerouteList.add(folder.toString());
                }
            }
        }

        //Gets the list of traceroute files for each traceroute folder
        for(int x = 0; x < tracerouteList.size(); x++)
        {
            File[] tracerouteFileList = mFilter.textFinder(tracerouteList.get(x));
            for (File tracerouteFile : tracerouteFileList) {
                mDirectoryList.add(tracerouteFile.toString());
            }
        }

        generateAdditionalHeaders(findLargestHopCount());
    }

    private int findLargestHopCount()
    {
        int largestHop = 0;
        for (String textFile : mDirectoryList)
        {
            int lineCount = 0;
            try
            {
                fr = new FileReader(textFile);
                br = new BufferedReader(fr);

                while (br.readLine() != null)
                {
                    lineCount++;
                }
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }

            if(lineCount > largestHop)
            {
                largestHop = lineCount;
            }
        }
        return largestHop;
    }

    private void generateAdditionalHeaders(int i)
    {
        char delimiter = ',';
        StringBuilder sb = new StringBuilder();
        sb.append(mGeneratedHeaders).append(delimiter);

        for(int j = 0; j < i-1; j++)
        {
            sb.append("Hop ").append(j+1).append(delimiter);
        }

        mGeneratedHeaders = sb.toString();
    }

    public void readFiles()
    {
        FileWriter writer = null;
        try
        {
            writer = new FileWriter(FILE_LOCATION);
            writer.append(mGeneratedHeaders);
            for(String textFile : mDirectoryList)
            {
                //read the file
                //take what we want
                //save it to a csv
                boolean saveNextLines = false;
                StringBuilder sb = new StringBuilder();
                ArrayList<Double> medianData = new ArrayList<>();
                String [] temp = extractDate(textFile);

                sb.append(temp[0]).append(" ").append(temp[1]).append("\n");

                try
                {
                    fr = new FileReader(textFile);
                    br = new BufferedReader(fr);

                    String sCurrentLine;

                    while((sCurrentLine = br.readLine()) != null)
                    {
                        sb.append(sCurrentLine).append("\n");
                    }

                    TracerouteData data = new TracerouteData();
                    data.parseData(sb.toString());

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
}
