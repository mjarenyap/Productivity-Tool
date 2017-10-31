import java.util.*;
import java.time.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.event.*;

public class AgendaPage
{
	public AgendaPage()
	{
		red = new Color(234, 71, 71);
		blue = new Color(51, 186, 239);
		green = new Color(49, 145, 63);
		grey = new Color(91, 91, 91);
		lightGrey = new Color(229, 229, 229);
		panelBorder = new LineBorder(lightGrey, 1);
		noEventsFont = new Font("Arial", Font.BOLD, 20);

		setNoEventsLabel();
		setInnerPanel();
		setScrollPane();

		combineAll();
	}

	public void combineAll()
	{
		innerPanel.add(noEvents);
	}

	public void resetPanel()
	{
		innerPanel.removeAll();
		innerPanel.revalidate();
		innerPanel.repaint();
		innerPanel.setPreferredSize(new Dimension(1000, 200));
		innerPanel.add(noEvents);
		noEvents.setVisible(true);
		labelOffset = 40;
	}

	public void updateDetails(SidebarPage sidebarPanel, ArrayList<Events> events, ArrayList<Tasks> tasks, int row, int col, boolean isEvent, boolean isTask)
	{
		String d = sidebarPanel.getCalendarTable().getModel().getValueAt(row, col).toString();
	    int id = Integer.parseInt(d);

	    ArrayList<String> occasions = new ArrayList<String>();
	    ArrayList<String> time = new ArrayList<String>();
	    ArrayList<Color> colors = new ArrayList<Color>();

	    if(isEvent == true)
	    {
	    	for(int i = 0; i < events.size(); i++)
			{
				if(sidebarPanel.getYearToday() == events.get(i).getDate().get(Calendar.YEAR) &&
					sidebarPanel.getMonthToday() == events.get(i).getDate().get(Calendar.MONTH) - 1 &&
					id == events.get(i).getDate().get(Calendar.DAY_OF_MONTH))
				{
					occasions.add(events.get(i).getName());
					time.add(events.get(i).getFromDate() + " - " + events.get(i).getToDate());
					colors.add(events.get(i).getColor());
				}
			}
	    }

	    if(isTask == true)
	    {
	    	for(int i = 0; i < tasks.size(); i++)
			{
				if(sidebarPanel.getYearToday() == tasks.get(i).getDate().get(Calendar.YEAR) &&
					sidebarPanel.getMonthToday() == tasks.get(i).getDate().get(Calendar.MONTH) - 1 &&
					id == tasks.get(i).getDate().get(Calendar.DAY_OF_MONTH))
				{
					occasions.add(tasks.get(i).getName());
					time.add(tasks.get(i).getFromDate());

					if(tasks.get(i).getDone() == false)
						colors.add(tasks.get(i).getColor());

					else colors.add(lightGrey);
				}
			}
	    }

	    arrangeList(occasions, time, colors);
	}

	public void updateDetails(MenuPage menuPanel, ArrayList<Events> events, ArrayList<Tasks> tasks, boolean isEvent, boolean isTask)
	{
	    ArrayList<String> occasions = new ArrayList<String>();
	    ArrayList<String> time = new ArrayList<String>();
	    ArrayList<Color> colors = new ArrayList<Color>();

	    if(isEvent == true)
	    {
	    	for(int i = 0; i < events.size(); i++)
			{
				if(menuPanel.getYearNow() == events.get(i).getDate().get(Calendar.YEAR) &&
					menuPanel.getMonthNow() == events.get(i).getDate().get(Calendar.MONTH) - 1 &&
					menuPanel.getDayNow() == events.get(i).getDate().get(Calendar.DAY_OF_MONTH))
				{
					occasions.add(events.get(i).getName());
					time.add(events.get(i).getFromDate() + " - " + events.get(i).getToDate());
					colors.add(events.get(i).getColor());
				}
			}
	    }

	    if(isTask == true)
	    {
	    	for(int i = 0; i < tasks.size(); i++)
			{
				if(menuPanel.getYearNow() == tasks.get(i).getDate().get(Calendar.YEAR) &&
					menuPanel.getMonthNow() == tasks.get(i).getDate().get(Calendar.MONTH) - 1 &&
					menuPanel.getDayNow() == tasks.get(i).getDate().get(Calendar.DAY_OF_MONTH))
				{
					occasions.add(tasks.get(i).getName());
					time.add(tasks.get(i).getFromDate());

					if(tasks.get(i).getDone() == false)
						colors.add(tasks.get(i).getColor());

					else colors.add(lightGrey);
				}
			}
	    }

	    arrangeList(occasions, time, colors);
	}

	public void arrangeList(ArrayList<String> occasions, ArrayList<String> time, ArrayList<Color> colors)
	{
		boolean found = false;
		if(occasions.size() != 0)
		{
			for(int i = 0; i < occasions.size(); i++)
			{
				String iFrom;
				if(time.get(i).length() > 5)
					iFrom = cutFromTime(time.get(i));

				else iFrom = time.get(i);

				int iFromHour = parseHour(iFrom);
				int iFromMinute = parseMinute(iFrom);

				for(int j = i; j < occasions.size(); j++)
				{
					String jFrom;
					if(time.get(j).length() > 5)
						jFrom = cutFromTime(time.get(j));

					else jFrom = time.get(j);

					int jFromHour = parseHour(jFrom);
					int jFromMinute = parseMinute(jFrom);

					if(jFromHour == iFromHour && jFromMinute > iFromMinute)
					{
						String occasionTemp = occasions.get(i);
						String timeTemp = time.get(i);
						Color colorTemp = colors.get(i);

						occasions.set(i, occasions.get(j));
						time.set(i, time.get(j));
						colors.set(i, colors.get(j));

						occasions.set(j, occasionTemp);
						time.set(j, timeTemp);
						colors.set(j, colorTemp);
					}

					else if(jFromHour > iFromHour)
					{
						String occasionTemp = occasions.get(i);
						String timeTemp = time.get(i);
						Color colorTemp = colors.get(i);

						occasions.set(i, occasions.get(j));
						time.set(i, time.get(j));
						colors.set(i, colors.get(j));

						occasions.set(j, occasionTemp);
						time.set(j, timeTemp);
						colors.set(j, colorTemp);
					}
				}
			}

			for(int i = occasions.size() - 1; i >= 0; i--)
			{
				innerPanel.add(nameField(occasions.get(i), colors.get(i)));
				innerPanel.add(timeField(time.get(i)));
				labelOffset += 40;
				innerPanel.setPreferredSize(new Dimension(1000, 40 + labelOffset));
			}

			found = true;
			noEvents.setVisible(false);
		}

		if(found == false)
			noEvents.setVisible(true);
	}

	public String cutFromTime(String stream)
	{
		String temp = "";
		int i = 0;
		while(stream.charAt(i) != ' ')
		{
			temp += stream.charAt(i);
			i++;
		}

		return temp;
	}

	public int parseHour(String time)
	{
		String temp = "";
		if(time.length() == 5)
		{
			temp += time.charAt(0);
			temp += time.charAt(1);
		}

		else temp += time.charAt(0);

		return Integer.parseInt(temp);
	}

	public int parseMinute(String time)
	{
		String temp = "";
		if(time.length() == 5)
		{
			temp += time.charAt(3);
			temp += time.charAt(4);
		}

		else
		{
			temp += time.charAt(2);
			temp += time.charAt(3);
		}

		return Integer.parseInt(temp);
	}

	public String palindrome(String stream)
	{
		String temp = "";
		for(int i = stream.length() - 1; i >= 0; i--)
			temp += stream.charAt(i);

		return temp;
	}

	public void setNoEventsLabel()
	{
		noEvents = new JLabel("No agendas for this day.");
		noEvents.setFont(noEventsFont);
		noEvents.setForeground(lightGrey);
		noEvents.setBounds(180, 200, 250, 30);
	}

	public void setScrollPane()
	{
		scrollPane = new JScrollPane(innerPanel);
		scrollPane.setBounds(262, 62, 600, 478);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    	scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setVisible(true);
	}

	public void setInnerPanel()
	{
		innerPanel = new JPanel(null);
		innerPanel.setPreferredSize(new Dimension(1000, 200));
		innerPanel.setBackground(Color.white);
	}

	public JScrollPane scrollPanel()
	{
		return scrollPane;
	}

	public JTextField timeField(String date)
	{
		JTextField temp = new JTextField(date);
		temp.setBounds(60, labelOffset, 100, 40);
		temp.setFont(new Font("Arial", Font.PLAIN, 13));
		temp.setHorizontalAlignment(JTextField.RIGHT);
		temp.setBackground(Color.white);
		temp.setBorder(null);
		temp.setEditable(false);
		
		return temp;
	}

	public JTextField nameField(String name, Color color)
	{
		JTextField temp = new JTextField(name);
		temp.setBounds(200, labelOffset, 300, 40);
		temp.setFont(new Font("Arial", Font.PLAIN, 13));
		temp.setForeground(color);
		temp.setBackground(Color.white);
		temp.setBorder(null);
		temp.setEditable(false);
		
		return temp;
	}

	public void setEventsLabel()
	{
		events = new JLabel("EVENTS");
		events.setFont(new Font("Arial", Font.BOLD, 20));

	}

	public void setTasksLabel()
	{
		tasks = new JLabel("TASKS");
		tasks.setFont(new Font("Arial", Font.BOLD, 20));
	}

	private JPanel innerPanel;
	private JScrollPane scrollPane;
	private JLabel events;
	private JLabel tasks; 

	private LineBorder panelBorder;
	private int labelOffset = 40;

	private JLabel noEvents;
	private Font noEventsFont;

	private Color blue;
	private Color red;
	private Color green;
	private Color grey;
	private Color lightGrey;
}