import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.awt.Color;
import java.util.Calendar;

public class TasksReader
{
	public TasksReader()
	{
		tasks_name = new ArrayList<String>();
		tasks_date = new ArrayList<Calendar>();
		tasks_from = new ArrayList<String>();
		tasks_done = new ArrayList<Boolean>();

		setNameReader();
		setDateReader();
		setFromReader();
		setDoneReader();
	}

	public void setNameReader()
	{
		try{
			BufferedReader reader = new BufferedReader(new FileReader(TASKS_NAME));
			String currLine;

			while((currLine = reader.readLine()) != null)
				tasks_name.add(currLine);

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
			BufferedReader reader = new BufferedReader(new FileReader(TASKS_FROM));
			String currLine;

			while((currLine = reader.readLine()) != null)
				tasks_from.add(currLine);

			reader.close();
		}

		catch(IOException e)
    	{
    		e.printStackTrace();
    	}
	}

	public void setDoneReader()
	{
		try{
			BufferedReader reader = new BufferedReader(new FileReader(TASKS_DONE));
			String currLine;

			while((currLine = reader.readLine()) != null)
			{
				if(currLine.compareTo("Done") == 0)
					tasks_done.add(true);

				else tasks_done.add(false);
			}

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
			BufferedReader reader = new BufferedReader(new FileReader(TASKS_DATE));
			String currLine;

			while((currLine = reader.readLine()) != null)
			{
				int month = searchMonth(currLine);
				int day = searchDay(currLine);
				int year = searchYear(currLine);

				Calendar tempDate = Calendar.getInstance();
				tempDate.set(year, month, day);
				tasks_date.add(tempDate);
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

	public ArrayList<String> getTaskName()
	{
		return tasks_name;
	}

	public ArrayList<Calendar> getTaskDate()
	{
		return tasks_date;
	}

	public ArrayList<String> getTaskFrom()
	{
		return tasks_from;
	}

	public ArrayList<Boolean> getTaskDone()
	{
		return tasks_done;
	}

	private final String TASKS_NAME = "records/tasks_name.txt";
	private final String TASKS_DATE = "records/tasks_date.txt";
	private final String TASKS_FROM = "records/tasks_from.txt";
	private final String TASKS_DONE = "records/tasks_done.txt";

	private ArrayList<String> tasks_name;
	private ArrayList<Calendar> tasks_date;
	private ArrayList<String> tasks_from;
	private ArrayList<Boolean> tasks_done;
}