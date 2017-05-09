/**
 * Created by Thomas Pachico on 06/05/2017.
 */
public abstract class Parser
{
    protected String[] extractDate(String file)
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
