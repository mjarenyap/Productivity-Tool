import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.awt.Color;
import java.util.Calendar;

public class EventsReader
{
	public EventsReader()
	{
		events_name = new ArrayList<String>();
		events_date = new ArrayList<Calendar>();
		events_to = new ArrayList<String>();
		events_from = new ArrayList<String>();

		setNameReader();
		setDateReader();
		setToReader();
		setFromReader();
	}

	public void setNameReader()
	{
		try{
			BufferedReader reader = new BufferedReader(new FileReader(EVENTS_NAME));
			String currLine;

			while((currLine = reader.readLine()) != null)
				events_name.add(currLine);

			reader.close();
		}

		catch(IOException e)
    	{
    		e.printStackTrace();
    	}
	}

	public void setToReader()
	{
		try{
			BufferedReader reader = new BufferedReader(new FileReader(EVENTS_TO));
			String currLine;

			while((currLine = reader.readLine()) != null)
				events_to.add(currLine);

			reader.close();
		}

		catch(IOException e)
    	{
    		e.printStackTrace();
    	}
	}

	public void setFromReader()
	{
		try{
			BufferedReader reader = new BufferedReader(new FileReader(EVENTS_FROM));
			String currLine;

			while((currLine = reader.readLine()) != null)
				events_from.add(currLine);

			reader.close();
		}

		catch(IOException e)
    	{
    		e.printStackTrace();
    	}
	}

	public void setDateReader()
	{
		try{
			BufferedReader reader = new BufferedReader(new FileReader(EVENTS_DATE));
			String currLine;

			while((currLine = reader.readLine()) != null)
			{
				int month = searchMonth(currLine);
				int day = searchDay(currLine);
				int year = searchYear(currLine);

				Calendar tempDate = Calendar.getInstance();
				tempDate.set(year, month, day);
				events_date.add(tempDate);
			}

			reader.close();
		}

		catch(IOException e)
    	{
    		e.printStackTrace();
    	}
	}

	public int searchMonth(String currLine)
	{
		int i = 0;
		String dateline = "";
		while(currLine.charAt(i) != '/')
		{
			dateline += currLine.charAt(i);
			i++;
		}

		return Integer.parseInt(dateline);
	}

	public int searchDay(String currLine)
	{
		int i = 0;
		String dateline = "";
		while(currLine.charAt(i) != '/')
			i++;

		i++;

		while(currLine.charAt(i) != '/')
		{
			dateline += currLine.charAt(i);
			i++;
		}

		return Integer.parseInt(dateline);
	}

	public int searchYear(String currLine)
	{
		int i = currLine.length() - 1;
		String dateline = "";

		while(currLine.charAt(i) != '/')
		{
			dateline += currLine.charAt(i);
			i--;
		}

		dateline = palindrome(dateline);
		return Integer.parseInt(dateline);
	}

	public String palindrome(String text)
	{
		String temp = "";

		for(int i = text.length() - 1; i >= 0; i--)
			temp += text.charAt(i);

		return temp;
	}

	public ArrayList<String> getEventName()
	{
		return events_name;
	}

	public ArrayList<Calendar> getEventDate()
	{
		return events_date;
	}

	public ArrayList<String> getEventTo()
	{
		return events_to;
	}

	public ArrayList<String> getEventFrom()
	{
		return events_from;
	}

	private final String EVENTS_NAME = "records/events_name.txt";
	private final String EVENTS_DATE = "records/events_date.txt";
	private final String EVENTS_TO = "records/events_to.txt";
	private final String EVENTS_FROM = "records/events_from.txt";

	private ArrayList<String> events_name;
	private ArrayList<Calendar> events_date;
	private ArrayList<String> events_to;
	private ArrayList<String> events_from;
}