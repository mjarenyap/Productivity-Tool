import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.awt.Color;
import java.util.Calendar;

public class PSVparser implements DataParser
{
	public PSVparser(String filename)
	{
		content = new ArrayList<String>();
		eventname = new ArrayList<String>();
		date = new ArrayList<Calendar>();
		color = new ArrayList<Color>();
		FILENAME = filename;
		try
		{
			BufferedReader psvfile = new BufferedReader(new FileReader(FILENAME));
			String currline;

			while((currline = psvfile.readLine()) != null)
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
			String temp = "";
			int j = 0;
			int count = 0;

			while(count != 2)
			{
				content.get(i).charAt(j);
				j++;
				if(content.get(i).charAt(j) == '|')
					count++;
			}


			while(content.get(i).charAt(j-1) != '|')
			{
				temp += content.get(i).charAt(j-1);
				j--;
			}

			for(int k = temp.length()-1; k > 0; k--)
			{
				dateline += temp.charAt(k);
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
		month += dateline.charAt(1);
		month += dateline.charAt(2);

		return Integer.parseInt(month);

	}

	public int searchDay(String dateline)
	{
		String day = "";
		day += dateline.charAt(4);
		day += dateline.charAt(5);

		return Integer.parseInt(day);

	}


	public int searchYear(String dateline)
	{
		String year = "";
		year += dateline.charAt(7);
		year += dateline.charAt(8);
		year += dateline.charAt(9);
		year += dateline.charAt(10);

		return Integer.parseInt(year);
	}

	public void searchColor()
	{
		for(int i = 0; i < content.size(); i++)
		{
			int len = content.get(i).length() - 1;
			String colorname = "";

			while(content.get(i).charAt(len) != '|')
			{
				colorname += content.get(i).charAt(len);
				len--;
			}

			Color tempColor = new Color(0, 0, 0);

			if(colorname.compareTo("deR ") == 0)
				tempColor = new Color(255, 0, 0);

			else if(colorname.compareTo("eulB ") == 0)
				tempColor = new Color(0, 0, 255);

			else if(colorname.compareTo("neerG ") == 0)
				tempColor = new Color(0, 255, 0);

			color.add(tempColor);
		}
	}

	public void searchEvent()
	{
		
		for(int i = 0; i < content.size(); i++)
		{
			int j = 0;
			String eventline = "";
			while(content.get(i).charAt(j) != '|')
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