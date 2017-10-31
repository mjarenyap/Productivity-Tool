import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.Color;
import java.util.Calendar;

public class TasksWriter
{
	public void setNameWriter(ArrayList<Tasks> tasks)
	{
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter(TASKS_NAME));

			for(int i = 0; i < tasks.size(); i++)
				writer.write(tasks.get(i).getName() + "\n");

			writer.close();
		}

		catch(IOException e)
    	{
    		e.printStackTrace();
    	}
	}

	public void setFromWriter(ArrayList<Tasks> tasks)
	{
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter(TASKS_FROM));

			for(int i = 0; i < tasks.size(); i++)
				writer.write(tasks.get(i).getFromDate() + "\n");

			writer.close();
		}

		catch(IOException e)
    	{
    		e.printStackTrace();
    	}
	}

	public void setDateWriter(ArrayList<Tasks> tasks)
	{
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter(TASKS_DATE));
			
			for(int i = 0; i < tasks.size(); i++)
			{
				int m = tasks.get(i).getDate().get(Calendar.MONTH);
				int d = tasks.get(i).getDate().get(Calendar.DAY_OF_MONTH);
				int y = tasks.get(i).getDate().get(Calendar.YEAR);

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

	public void setDoneWriter(ArrayList<Tasks> tasks)
	{
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter(TASKS_DONE));
			
			for(int i = 0; i < tasks.size(); i++)
			{
				if(tasks.get(i).getDone() == true)
					writer.write("Done\n");

				else writer.write("Undone\n");
			}

			writer.close();
		}

		catch(IOException e)
    	{
    		e.printStackTrace();
    	}
	}

	private final String TASKS_NAME = "records/tasks_name.txt";
	private final String TASKS_DATE = "records/tasks_date.txt";
	private final String TASKS_FROM = "records/tasks_from.txt";
	private final String TASKS_DONE = "records/tasks_done.txt";
}