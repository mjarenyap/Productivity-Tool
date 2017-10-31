import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.awt.Color;
import java.util.Calendar;

public class CSVparser implements DataParser
{
	public CSVparser(String filename)
	{
		content = new ArrayList<String>();
		eventname = new ArrayList<String>();
		date = new ArrayList<Calendar>();
		color = new ArrayList<Color>();
		FILENAME = filename;
		try
		{
			BufferedReader csvfile = new BufferedReader(new FileReader(FILENAME));
			String currline;

			while((currline = csvfile.readLine()) != null)
				content.add(currline);

			searchDate();
			searchColor();
			searchEvent();
		}

		catch(IOException e)
    	{
    		e.printStackTrace();
    	}
	}

	public void searchDate()
	{
		for(int i = 0; i < content.size(); i++)
		{
			String dateline = "";
			int j = 0;
			while(content.get(i).charAt(j) != ',')
			{
				dateline += content.get(i).charAt(j);
				j++;
			}

			int day = searchDay(dateline);
			int month = searchMonth(dateline);
			month--;
			int year = searchYear(dateline);
			Calendar tempDate = Calendar.getInstance();
			tempDate.set(year, month, day);
			date.add(tempDate);
		}
	}

	public int searchMonth(String dateline)
	{
		String month = "";
		month += dateline.charAt(0);
		month += dateline.charAt(1);

		return Integer.parseInt(month);

	}

	public int searchDay(String dateline)
	{
		String day = "";
		day += dateline.charAt(3);
		day += dateline.charAt(4);

		return Integer.parseInt(day);

	}


	public int searchYear(String dateline)
	{
		String year = "";
		year += dateline.charAt(6);
		year += dateline.charAt(7);
		year += dateline.charAt(8);
		year += dateline.charAt(9);

		return Integer.parseInt(year);
	}

	public void searchColor()
	{
		for(int i = 0; i < content.size(); i++)
		{
			int len = content.get(i).length() - 1;
			String colorname = "";

			while(content.get(i).charAt(len) != ',')
			{
				colorname += content.get(i).charAt(len);
				len--;
			}

			Color tempColor = new Color(0, 0, 0);

			if(colorname.compareTo("deR") == 0)
				tempColor = new Color(255, 0, 0);

			else if(colorname.compareTo("eulB") == 0)
				tempColor = new Color(0, 0, 255);

			else if(colorname.compareTo("neerG") == 0)
				tempColor = new Color(0, 255, 0);

			color.add(tempColor);
		}
	}

	public void searchEvent()
	{
		int j;
		for(int i = 0; i < content.size(); i++)
		{
			String eventline = "";
			j = 11;
			while(content.get(i).charAt(j) != ',')
			{
				eventline += content.get(i).charAt(j);
				j++;
			}
			eventname.add(eventline);
		}
	}

	public ArrayList<String> getEvents()
	{
		return eventname;
	}

	public ArrayList<Color> getColors()
	{
		return color;
	}

	public ArrayList<Calendar> getDates()
	{
		return date;
	}

	private final String FILENAME;
	private ArrayList<String> content;
	private ArrayList<String> eventname;
	private ArrayList<Calendar> date;
	private ArrayList<Color> color;
}