import java.util.ArrayList;

/**
 * Created by Thomas Pachico on 06/05/2017.
 */
public class TracerouteData
{

    private String mDate;
    private String mTime;
    private String mDestination;
    private int mHopCount;
    private ArrayList<String> mHopHosts = new ArrayList<>();

    public void parseData(String data)
    {
        String [] temp;

        temp = data.split(" ",2);
        mDate = temp[0];
        mDate = mDate.replace('-','/');

        data = temp[1];

        temp = data.split("traceroute",2);
        mTime = temp[0];
        mTime = mTime.replace('_',':');

        data = temp[1];
        temp = data.split("\\(",2);
        data = temp[1];
        temp = data.split("\\)",2);
        mDestination = temp[0];

        data = temp[1];
        temp = data.split("\\n",2);
        data = temp[1];

        String [] hopsList = data.split("\\n");
        mHopCount = hopsList.length;

        for(String hop : hopsList)
        {
            hop = hop.trim();
            String [] hopTemp = hop.split(" ");
            hop = hopTemp[2];

            mHopHosts.add(hop);
        }
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append(mDestination+","+mHopCount+","+mDate+","+mTime+",");

        for (int i = 0; i< mHopHosts.size(); i++)
        {
            if(i == mHopHosts.size()-1)
            {
                sb.append(mHopHosts.get(i));
            }
            else
            {
                sb.append(mHopHosts.get(i)).append(",");
            }
        }
        return sb.toString().replace("\n","");
    }
}
