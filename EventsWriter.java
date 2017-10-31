import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.Color;
import java.util.Calendar;

public class EventsWriter
{
	public void setNameWriter(ArrayList<Events> events)
	{
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter(EVENTS_NAME));

			for(int i = 0; i < events.size(); i++)
				writer.write(events.get(i).getName() + "\n");

			writer.close();
		}

		catch(IOException e)
    	{
    		e.printStackTrace();
    	}
	}

	public void setToWriter(ArrayList<Events> events)
	{
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter(EVENTS_TO));

			for(int i = 0; i < events.size(); i++)
				writer.write(events.get(i).getToDate() + "\n");

			writer.close();
		}

		catch(IOException e)
    	{
    		e.printStackTrace();
    	}
	}

	public void setFromWriter(ArrayList<Events> events)
	{
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter(EVENTS_FROM));

			for(int i = 0; i < events.size(); i++)
				writer.write(events.get(i).getFromDate() + "\n");

			writer.close();
		}

		catch(IOException e)
    	{
    		e.printStackTrace();
    	}
	}

	public void setDateWriter(ArrayList<Events> events)
	{
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter(EVENTS_DATE));
			
			for(int i = 0; i < events.size(); i++)
			{
				int m = events.get(i).getDate().get(Calendar.MONTH);
				int d = events.get(i).getDate().get(Calendar.DAY_OF_MONTH);
				int y = events.get(i).getDate().get(Calendar.YEAR);

				String month = Integer.toString(m);
				String day = Integer.toString(d);
				String year = Integer.toString(y);

				writer.write(month + "/" + day + "/" + year + "\n");
			}

			writer.close();
		}

		catch(IOException e)
    	{
    		e.printStackTrace();
    	}
	}

	private final String EVENTS_NAME = "records/events_name.txt";
	private final String EVENTS_DATE = "records/events_date.txt";
	private final String EVENTS_TO = "records/events_to.txt";
	private final String EVENTS_FROM = "records/events_from.txt";
}