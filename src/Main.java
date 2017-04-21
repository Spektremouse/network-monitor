import java.io.File;
import java.util.ArrayList;

/**
 * Created by Thomas Pachico on 21/04/2017.
 */
public class Main
{
    public static void main(String[] args)
    {
        ArrayList<String> directoryList = new ArrayList<>();

        Filter fil = new Filter();
        File[] list = fil.folderFinder("C:\\Users\\Thomas Pachico\\Documents\\UEA\\Networks Monitor\\output-19-04-2017");

        for(int i=0; i< list.length; i++)
        {
            directoryList.add(list[i].toString());
        }

        PingParser pparse = new PingParser(directoryList);
        pparse.readFiles();
    }
}
