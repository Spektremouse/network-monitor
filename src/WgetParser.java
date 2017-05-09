import java.io.*;
import java.util.ArrayList;

/**
 * Created by Thomas Pachico on 06/05/2017.
 */
public class WgetParser extends Parser {

    private static final String FILE_HEADER = "Total File Size (MB),Throughput,Duration,Date,Time";

    private BufferedReader br = null;
    private FileReader fr = null;
    private ArrayList<String> mRootDirectoryList = new ArrayList<>();
    private ArrayList<String> mAlphaDirectoryList = new ArrayList<>();
    private ArrayList<String> mBravoDirectoryList = new ArrayList<>();
    private ArrayList<String> mCharlieDirectoryList = new ArrayList<>();
    private ArrayList<String> mDeltaDirectoryList = new ArrayList<>();
    private Filter mFilter = new Filter();

    public WgetParser(ArrayList<String> directoryList)
    {
        for(int x = 0; x < directoryList.size(); x++)
        {
            Filter fil = new Filter();
            File[] rootlist = fil.folderFinder(directoryList.get(x));

            for (File folder : rootlist ) {
                if(folder.toString().contains("alpha"))
                {
                    mRootDirectoryList.add(folder.toString());
                    File [] wgetList = mFilter.textFinder(folder.toString());
                    for (File wgetFile : wgetList)
                    {
                        mAlphaDirectoryList.add(wgetFile.toString());
                    }
                }
                if(folder.toString().contains("bravo"))
                {
                    mRootDirectoryList.add(folder.toString());
                    File [] wgetList = mFilter.textFinder(folder.toString());
                    for (File wgetFile : wgetList)
                    {
                        mBravoDirectoryList.add(wgetFile.toString());
                    }
                }
                if(folder.toString().contains("charlie"))
                {
                    mRootDirectoryList.add(folder.toString());
                    File [] wgetList = mFilter.textFinder(folder.toString());
                    for (File wgetFile : wgetList)
                    {
                        mCharlieDirectoryList.add(wgetFile.toString());
                    }
                }
                if(folder.toString().contains("delta"))
                {
                    mRootDirectoryList.add(folder.toString());
                    File [] wgetList = mFilter.textFinder(folder.toString());
                    for (File wgetFile : wgetList)
                    {
                        mDeltaDirectoryList.add(wgetFile.toString());
                    }
                }
            }
        }
    }

    public void test()
    {
        System.out.println( "ROOT");
        for (String file : mRootDirectoryList )
        {
            System.out.println(file);
        }
        System.out.println( "ALPHA");
        for (String file : mAlphaDirectoryList )
        {
            System.out.println(file);
        }
        System.out.println( "BRAVO");
        for (String file : mBravoDirectoryList )
        {
            System.out.println(file);
        }
        System.out.println( "CHARLIE");
        for (String file : mCharlieDirectoryList )
        {
            System.out.println(file);
        }

        System.out.println( "DELTA");
        for (String file : mDeltaDirectoryList )
        {
            System.out.println(file);
        }

    }

    private void readAlpha()
    {
        String location = "C:\\Users\\Thomas Pachico\\Documents\\UEA\\Networks Monitor\\AlphaData.csv";
        parseData(mAlphaDirectoryList, location);
    }

    private void readBravo()
    {
        String location = "C:\\Users\\Thomas Pachico\\Documents\\UEA\\Networks Monitor\\BravoData.csv";
        parseData(mBravoDirectoryList, location);
    }

    private void readCharlie()
    {
        String location = "C:\\Users\\Thomas Pachico\\Documents\\UEA\\Networks Monitor\\CharlieData.csv";
        parseData(mCharlieDirectoryList, location);
    }

    private void readDelta()
    {
        String location = "C:\\Users\\Thomas Pachico\\Documents\\UEA\\Networks Monitor\\DeltaData.csv";
        parseData(mDeltaDirectoryList, location);
    }

    private void parseData(ArrayList<String> directory, String outputFile)
    {
        FileWriter writer = null;
        try
        {
            //LOCATION WILL CHANGE
            writer = new FileWriter(outputFile);
            writer.append(FILE_HEADER);
            for(String textFile : directory)
            {
                //read the file
                //take what we want
                //save it to a csv
                StringBuilder sb = new StringBuilder();
                String [] temp = extractDate(textFile);

                sb.append(temp[0]).append(" ").append(temp[1]);

                try
                {
                    fr = new FileReader(textFile);
                    br = new BufferedReader(fr);

                    String sCurrentLine;

                    while((sCurrentLine = br.readLine()) != null)
                    {
                        if(sCurrentLine.contains("Length:"))
                        {
                            sb.append(sCurrentLine);
                        }
                        else if(sCurrentLine.contains("100%"))
                        {
                            sb.append(sCurrentLine);
                        }
                        else if(sCurrentLine.contains("saved"))
                        {
                            sb.append(sCurrentLine);
                        }
                    }

                    WgetData data = new WgetData();
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

    public void readFiles()
    {
        readAlpha();
        readBravo();
        readCharlie();
        readDelta();
    }


}
