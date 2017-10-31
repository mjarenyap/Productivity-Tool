import java.awt.Color;
import java.util.Calendar;
import javax.swing.text.*;

public class Events
{
	public Events(String n, Calendar d, String f, String t)
	{
		eventColor = new Color(51, 186, 239);
		setName(n);
		setDate(d);
		setFromDate(f);
		setToDate(t);
	}

	public void setFromDate(String f)
	{
		fromDate = f;
	}

	public void setToDate(String t)
	{
		toDate = t;
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
		return eventColor;
	}

	public String getFromDate()
	{
		return fromDate;
	}

	public String getToDate()
	{
		return toDate;
	}

	private String name;
	private String fromDate;
	private String toDate;
	private Calendar date;
	private Color eventColor;
}