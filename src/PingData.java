import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Thomas Pachico on 21/04/2017.
 */
public class PingData implements Comparator<Double>
{
    private String mHostname;
    private String mReceived;
    private String mTransmitted;
    private String mLossRate;
    private String mTotalTime;
    private String mRTTMin;
    private String mRTTMax;
    private String mRTTMean;
    private Double mRTTMedian;
    private String mRTTDeviation;
    private String mDate;
    private String mTime;

    public void parseData(String data)
    {
        String [] temp;

        temp = data.split(" ",2);
        mDate = temp[0];
        data = temp[1];

        temp = data.split("_",2);
        mTime = temp[0];
        data = temp[1];

        mTime = mTime + ":00";

        temp = data.split(" ",2);
        data = temp[1];
        temp = data.split(" ",2);
        mHostname = temp[0];
        data = temp[1];

        temp = data.split("---",2);
        data = temp[1];
        temp = data.split(" ",2);
        mTransmitted = temp[0];
        data = temp[1];

        temp = data.split(", ",2);
        data = temp[1];
        temp = data.split(" ",2);
        mReceived = temp[0];
        data = temp[1];

        temp = data.split(", ",2);
        data = temp[1];
        temp = data.split("%",2);
        mLossRate = temp[0];
        data = temp[1];

        temp = data.split(", ",2);
        data = temp[1];
        temp = data.split(" ",2);
        data = temp[1];
        temp = data.split("ms",2);
        mTotalTime = temp[0];
        data = temp[1];

        if(data.length() > 0)
        {
            temp = data.split("= ",2);
            data = temp[1];
            temp = data.split(" ",2);
            data = temp[0];
            temp = data.split("/",4);
            mRTTMin = temp[0];
            mRTTMean = temp[1];
            mRTTMax = temp[2];
            mRTTDeviation = temp[3];
        }
    }

    public void findMedian(ArrayList<Double> data)
    {
        if(data.size() > 0)
        {
            data.sort(this);
            mRTTMedian = data.get((data.size()/2)-1);
        }
        else
        {
            mRTTMedian = 0.0;
        }
    }

    @Override
    public String toString()
    {
        return mHostname+","+mReceived+","+mTransmitted+","+mLossRate+","+mTotalTime+","+mRTTMin+","+mRTTMax+","+
                mRTTMean+","+mRTTMedian+","+mRTTDeviation+","+mDate+","+mTime;
    }

    @Override
    public int compare(Double p1, Double p2) {
        return Double.compare(p1, p2);
    }
}