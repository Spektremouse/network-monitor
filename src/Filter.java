/**
 * Created by Thomas Pachico on 21/04/2017.
 */
import java.io.File;
import java.io.FilenameFilter;

public class Filter {

    public File[] folderFinder(String dirName){
        File dir = new File(dirName);

        return dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String filename)
            { return true; }
        } );
    }

    public File[] textFinder(String dirName){
        File dir = new File(dirName);

        return dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String filename)
            { return filename.endsWith("txt"); }
        } );

    }

}