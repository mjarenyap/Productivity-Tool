import java.util.*;
import java.time.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.event.*;

public class DayPage
{
	public DayPage()
	{
		red = new Color(234, 71, 71);
		blue = new Color(51, 186, 239);
		green = new Color(49, 145, 63);
		grey = new Color(91, 91, 91);
		lightGrey = new Color(229, 229, 229);
		panelBorder = new LineBorder(lightGrey, 1);
		noEventsFont = new Font("Arial", Font.BOLD, 20);
		timeFont = new Font("Arial", Font.PLAIN, 13);

		setNoEventsLabel();
		setInnerPanel();
		setScrollPane();
		setTimeLabels();
		setEventLabels();

		combineAll();
	}

	public void updateDetails(MenuPage menuPanel, ArrayList<Events> events, ArrayList<Tasks> tasks, boolean isEvent, boolean isTask)
	{
		if(isEvent == true)
		{
			for(int i = 0; i < events.size(); i++)
			{
				if(menuPanel.getYearNow() == events.get(i).getDate().get(Calendar.YEAR) &&
					menuPanel.getMonthNow() == events.get(i).getDate().get(Calendar.MONTH) - 1 &&
					menuPanel.getDayNow() == events.get(i).getDate().get(Calendar.DAY_OF_MONTH))
				{
					int x = 0;
					while(events.get(i).getFromDate().compareTo(timeField.get(x).getText().toString()) != 0)
						x++;

					nameField.get(x).setText("  " + events.get(i).getName());
					nameField.get(x).setBackground(events.get(i).getColor());
					nameField.get(x).setBorder(new LineBorder(events.get(i).getColor()));

					while(events.get(i).getToDate().compareTo(timeField.get(x).getText().toString()) != 0)
					{
						nameField.get(x).setBackground(events.get(i).getColor());
						nameField.get(x).setBorder(new LineBorder(events.get(i).getColor()));
						x++;
					}

					nameField.get(x).setBackground(events.get(i).getColor());
					nameField.get(x).setBorder(new LineBorder(events.get(i).getColor()));
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
					int y = 0;
					while(tasks.get(i).getFromDate().compareTo(timeField.get(y).getText().toString()) != 0)
						y++;

					nameField.get(y).setText("  " + tasks.get(i).getName());

					if(tasks.get(i).getDone() == false)
					{
						nameField.get(y).setBackground(tasks.get(i).getColor());
						nameField.get(y).setBorder(new LineBorder(tasks.get(i).getColor()));
					}

					else
					{
						nameField.get(y).setBackground(lightGrey);
						nameField.get(y).setBorder(new LineBorder(lightGrey));
					}
				}
			}
		}
	}

	public void updateDetails(SidebarPage sidebarPanel, ArrayList<Events> events, ArrayList<Tasks> tasks, int row, int col, boolean isEvent, boolean isTask)
	{
		String d = sidebarPanel.getCalendarTable().getModel().getValueAt(row, col).toString();
	    int id = Integer.parseInt(d);

	    if(isTask == true)
		{
			for(int i = 0; i < tasks.size(); i++)
			{
				if(sidebarPanel.getYearToday() == tasks.get(i).getDate().get(Calendar.YEAR) &&
					sidebarPanel.getMonthToday() == tasks.get(i).getDate().get(Calendar.MONTH) - 1 &&
					id == tasks.get(i).getDate().get(Calendar.DAY_OF_MONTH))
				{
					int y = 0;
					while(tasks.get(i).getFromDate().compareTo(timeField.get(y).getText().toString()) != 0)
						y++;

					nameField.get(y).setText("  " + tasks.get(i).getName());
					
					if(tasks.get(i).getDone() == false)
					{
						nameField.get(y).setBackground(tasks.get(i).getColor());
						nameField.get(y).setBorder(new LineBorder(tasks.get(i).getColor()));
					}

					else
					{
						nameField.get(y).setBackground(lightGrey);
						nameField.get(y).setBorder(new LineBorder(lightGrey));
					}
				}
			}
		}

	    if(isEvent == true)
		{
			for(int i = 0; i < events.size(); i++)
			{
				if(sidebarPanel.getYearToday() == events.get(i).getDate().get(Calendar.YEAR) &&
					sidebarPanel.getMonthToday() == events.get(i).getDate().get(Calendar.MONTH) - 1 &&
					id == events.get(i).getDate().get(Calendar.DAY_OF_MONTH))
				{
					int x = 0;
					while(events.get(i).getFromDate().compareTo(timeField.get(x).getText().toString()) != 0)
						x++;

					nameField.get(x).setText("  " + events.get(i).getName());
					nameField.get(x).setBackground(events.get(i).getColor());
					nameField.get(x).setBorder(new LineBorder(events.get(i).getColor()));

					while(events.get(i).getToDate().compareTo(timeField.get(x).getText().toString()) != 0)
					{
						nameField.get(x).setBackground(events.get(i).getColor());
						nameField.get(x).setBorder(new LineBorder(events.get(i).getColor()));
						x++;
					}

					nameField.get(x).setBackground(events.get(i).getColor());
					nameField.get(x).setBorder(new LineBorder(events.get(i).getColor()));
				}
			}
		}
	}

	public void resetPanel()
	{
		innerPanel.removeAll();
		innerPanel.revalidate();
		innerPanel.repaint();
		innerPanel.setPreferredSize(new Dimension(1000, 20));
		setTimeLabels();
		setEventLabels();

		for(int i = 0; i < nameField.size(); i++)
			nameField.get(i).setText("");

		for(int i = 0; i < timeField.size(); i++)
			innerPanel.add(timeField.get(i));

		for(int i = 0; i < nameField.size(); i++)
			innerPanel.add(nameField.get(i));
	}

	public void combineAll()
	{
		for(int i = 0; i < timeField.size(); i++)
			innerPanel.add(timeField.get(i));

		for(int i = 0; i < nameField.size(); i++)
			innerPanel.add(nameField.get(i));

		innerPanel.add(noEvents);
	}

	public void setTimeLabels()
	{
		timeField = new ArrayList<JTextField>();
		int labelOffset = -1;
		int hour = 0;

		for(int i = 0; i < 23; i++)
		{
			JTextField first = new JTextField(hour + ":" + "00");
			JTextField second = new JTextField(hour + ":" + "30");

			first.setBounds(-1, labelOffset, 100, 40);
			first.setHorizontalAlignment(JTextField.CENTER);
			first.setFont(timeFont);
			first.setForeground(grey);
			first.setBackground(Color.white);
			first.setBorder(panelBorder);
			first.setEditable(false);

			timeField.add(first);
			labelOffset += 39;
			innerPanel.setPreferredSize(new Dimension(1000, labelOffset));

			second.setBounds(-1, labelOffset, 100, 40);
			second.setHorizontalAlignment(JTextField.CENTER);
			second.setFont(timeFont);
			second.setForeground(grey);
			second.setBackground(Color.white);
			second.setBorder(panelBorder);
			second.setEditable(false);

			timeField.add(second);
			labelOffset += 39;
			innerPanel.setPreferredSize(new Dimension(1000, labelOffset));

			hour++;
		}
	}

	public void setEventLabels()
	{
		nameField = new ArrayList<JTextField>();
		int labelOffset = -1;
		for(int i = 0; i < 46; i++)
		{
			JTextField temp = new JTextField("");
			temp.setBounds(98, labelOffset, 499, 40);
			temp.setHorizontalAlignment(JTextField.LEFT);
			temp.setFont(timeFont);
			temp.setForeground(Color.white);
			temp.setBackground(Color.white);
			temp.setBorder(panelBorder);
			temp.setEditable(false);

			nameField.add(temp);
			labelOffset += 39;
			innerPanel.setPreferredSize(new Dimension(1000, labelOffset));
		}
	}

	public void setNoEventsLabel()
	{
		noEvents = new JLabel("No events for this day.");
		noEvents.setFont(noEventsFont);
		noEvents.setForeground(lightGrey);
		noEvents.setBounds(180, 200, 250, 30);
		noEvents.setVisible(false);
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
		innerPanel.setBackground(Color.white);
	}

	public JScrollPane scrollPanel()
	{
		return scrollPane;
	}

	private JPanel innerPanel;
	private JScrollPane scrollPane;

	private LineBorder panelBorder;

	private ArrayList<JTextField> timeField;
	private ArrayList<JTextField> nameField;

	private JLabel noEvents;
	private Font noEventsFont;
	private Font timeFont;

	private Color blue;
	private Color red;
	private Color green;
	private Color grey;
	private Color lightGrey;
}