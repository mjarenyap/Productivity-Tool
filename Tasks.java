import java.awt.Color;
import java.util.Calendar;
import javax.swing.text.*;

public class Tasks
{
	public Tasks(String n, Calendar d, String f, boolean o)
	{
		taskColor = new Color(49, 145, 63);
		setDone(o);
		setName(n);
		setDate(d);
		setFromDate(f);
	}

	public void setDone(boolean o)
	{
		done = o;
	}

	public void setFromDate(String f)
	{
		fromDate = f;
	}

	public void setName(String eventName)
	{
		name = eventName;
	}

	public void setDate(Calendar d)
	{
		date = d;
	}

	public String getName()
	{
		return name;
	}

	public Calendar getDate()
	{
		return date;
	}

	public Color getColor()
	{
		return taskColor;
	}

	public String getFromDate()
	{
		return fromDate;
	}

	public void markAsDone()
	{
		done = true;
	}

	public void markAsUndone()
	{
		done = false;
	}

	public boolean getDone()
	{
		return done;
	}

	private String name;
	private String fromDate;
	private Calendar date;
	private Color taskColor;
	private boolean done;
}