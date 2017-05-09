/**
 * Created by Thomas Pachico on 06/05/2017.
 */
public class WgetData
{
    private static final String FILE_HEADER = "Total File Size (MB),Throughput,Duration,Date,Time";

    private String mTotalSize;
    private String mThroughput;
    private String mDuration;
    private String mDate;
    private String mTime;

    public void parseData(String data)
    {
        String [] temp;

        temp = data.split(" ",2);
        mDate = temp[0];
        int count = 0;

        for(int i=0; i<mDate.length(); i++)
        {
            if(mDate.charAt(i) == '-')
            {
                count++;
            }
        }

        if(count == 2)
        {
          String [] tempDate = mDate.split("-",2);
          mDate = tempDate[1];
        }
        else if(count == 3)
        {
            String [] tempDate = mDate.split("-",2);
            mDate = tempDate[1];
        }

        mDate = mDate.replace('-','/');

        data = temp[1];

        temp = data.split("L",2);
        mTime = temp[0];
        mTime = mTime.replace('_',':');
        data = temp[1];

        temp = data.split("\\(",2);
        data = temp[1];
        temp = data.split("\\)",2);
        mTotalSize = temp[0];
        data = temp[1];

        temp = data.split("=",2);
        data = temp[1];
        temp = data.split("2017",2);
        mDuration = temp[0];
        data = temp[1];

        temp = data.split("\\(",2);
        data = temp[1];
        temp = data.split("\\)",2);
        mThroughput = temp[0];
    }

    @Override
    public String toString()
    {
        return mTotalSize+","+mThroughput+","+mDuration+","+mDate+","+mTime;
    }

}
