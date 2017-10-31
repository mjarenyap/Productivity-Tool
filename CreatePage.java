import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.event.*;

public class CreatePage
{
	public CreatePage()
	{
		red = new Color(234, 71, 71);
		blue = new Color(51, 186, 239);
		green = new Color(49, 145, 63);
		grey = new Color(91, 91, 91);
		lightGrey = new Color(229, 229, 229);

		eventTitle = new Font("Arial", Font.ITALIC, 16);
		guideText = new Font("Arial", Font.BOLD, 16);
		otherField = new Font("Arial", Font.PLAIN, 13);

		panelBorder = new LineBorder(lightGrey, 1);

		setCreatePanel();

		setRadios();
		setColorComboBox();
		setSaveButton();
		setDiscardButton();
		setFromTime();
		setToTime();
		setMonthField();
		setDayField();
		setYearField();
		setEvent();
		setMonthLabel();
		setDayLabel();
		setYearLabel();
		setToLabel();

		combineAll();
	}

	public boolean checkDuplicate(ArrayList<Events> events, ArrayList<Tasks> tasks)
	{
		int inputFromHour = parseHour(getFromTime());
		int inputFromMinute = parseMinute(getFromTime());
		int inputToHour = parseHour(getToTime());
		int inputToMinute = parseMinute(getToTime());

		for(int i = 0; i < events.size(); i++)
		{
			String eFrom = events.get(i).getFromDate();
			String eTo = events.get(i).getToDate();
			int eventFromHour = parseHour(eFrom);
			int eventFromMinute = parseMinute(eFrom);
			int eventToHour = parseHour(eTo);
			int eventToMinute = parseMinute(eTo);

			if(rTask.isSelected() == true)
			{
				inputToHour = inputFromHour;
				inputToMinute = inputFromMinute;
			}

			if(events.get(i).getDate().get(Calendar.DAY_OF_MONTH) == Integer.parseInt(getDayField()) &&
				events.get(i).getDate().get(Calendar.MONTH) == Integer.parseInt(getMonthField()) &&
				events.get(i).getDate().get(Calendar.YEAR) == Integer.parseInt(getYearField()))
			{
				if(inputFromHour == eventFromHour && inputFromMinute == eventFromMinute)
					return true;

				else if(inputFromHour == eventToHour && inputFromMinute == eventToMinute)
					return true;

				else if(inputToHour == eventFromHour && inputToMinute == eventFromMinute)
					return true;

				else if(inputToHour == eventToHour && inputToMinute == eventToMinute)
					return true;

				else if(inputFromHour >= eventFromHour && inputFromHour <= eventToHour)
					return true;

				else if(inputToHour >= eventFromHour && inputToHour <= eventToHour)
					return true;
			}
		}

		for(int i = 0; i < tasks.size(); i++)
		{
			String tFrom = tasks.get(i).getFromDate();
			int taskFromHour = parseHour(tFrom);
			int taskFromMinute = parseMinute(tFrom);

			if(rTask.isSelected() == true)
			{
				inputToHour = inputFromHour;
				inputToMinute = inputFromMinute;
			}

			if(tasks.get(i).getDate().get(Calendar.DAY_OF_MONTH) == Integer.parseInt(getDayField()) &&
				tasks.get(i).getDate().get(Calendar.MONTH) == Integer.parseInt(getMonthField()) &&
				tasks.get(i).getDate().get(Calendar.YEAR) == Integer.parseInt(getYearField()))
			{
				if(inputFromHour == taskFromHour && taskFromMinute == inputFromMinute)
					return true;

				else if(inputToHour == taskFromHour && taskFromMinute == inputToMinute)
					return true;
			}
		}

		return false;
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

	public void combineAll()
	{
		cPanel.add(event);
		cPanel.add(rEvent);
		cPanel.add(rTask);
		cPanel.add(month);
		cPanel.add(monthField);
		cPanel.add(day);
		cPanel.add(dayField);
		cPanel.add(year);
		cPanel.add(yearField);
		cPanel.add(fromTime);
		cPanel.add(to);
		cPanel.add(toTime);
		cPanel.add(save);
		cPanel.add(discard);
	}

	public void setCreatePanel()
	{
		cPanel = new JPanel(null);
		cPanel.setBounds(262, 0, 598, 560);
		cPanel.setBorder(panelBorder);
		cPanel.setBackground(Color.white);
	}

	public void setRadios()
	{
		rEvent = new JRadioButton("Event");
		rEvent.setBounds(43, 200, 100, 19);
		rEvent.setFont(guideText);
		rEvent.setForeground(grey);
		rEvent.setVisible(true);

		rTask = new JRadioButton("Task");
		rTask.setBounds(141, 200, 100, 19);
		rTask.setFont(guideText);
		rTask.setForeground(grey);
		rTask.setVisible(true);
	}

	public void setColorComboBox()
	{
		color = new JComboBox();
		color.setBounds(255, 195, 100, 37);

		color.addItem(String.valueOf("Red"));
		color.addItem(String.valueOf("Blue"));
		color.addItem(String.valueOf("Green"));
	}

	public void setSaveButton()
	{
		save = new JButton("Save");
		save.setBounds(43, 350, 74, 37);
		save.setBackground(red);
		save.setForeground(Color.white);
		save.setFont(otherField);
		save.setOpaque(true);
		save.setBorderPainted(false);
		save.setVisible(true);
	}

	public void setDiscardButton()
	{
		discard = new JButton("Discard");
		discard.setBounds(124, 350, 100, 37);
		discard.setBackground(lightGrey);
		discard.setForeground(grey);
		discard.setFont(otherField);
		discard.setOpaque(true);
		discard.setBorderPainted(false);
		discard.setVisible(true);
	}

	public void setEvent()
	{
		event = new JTextField("New Event");
		event.setBounds(43, 101, 307, 37);
		event.setHorizontalAlignment(JTextField.CENTER);
		event.setForeground(grey);
		event.setBackground(Color.white);
		event.setBorder(panelBorder);
		event.setVisible(true);
	}

	public void setMonthLabel()
	{
		month = new JLabel("Month:");
		month.setBounds(43, 160, 80, 20);
		month.setForeground(grey);
		month.setFont(guideText);
		month.setVisible(true);
	}

	public void setDayLabel()
	{
		day = new JLabel("Day:");
		day.setBounds(160, 160, 80, 20);
		day.setForeground(grey);
		day.setFont(guideText);
		day.setVisible(true);
	}

	public void setYearLabel()
	{
		year = new JLabel("Year:");
		year.setBounds(255, 160, 80, 20);
		year.setForeground(grey);
		year.setFont(guideText);
		year.setVisible(true);
	}

	public void setMonthField()
	{
		monthField = new JTextField("3");
		monthField.setBounds(100, 156, 35, 27);
		monthField.setHorizontalAlignment(JTextField.CENTER);
		monthField.setFont(eventTitle);
		monthField.setForeground(grey);
		monthField.setBackground(Color.white);
		monthField.setBorder(panelBorder);
		monthField.setFont(otherField);
		monthField.setVisible(true);
	}

	public void setDayField()
	{
		dayField = new JTextField("10");
		dayField.setBounds(200, 156, 35, 27);
		dayField.setHorizontalAlignment(JTextField.CENTER);
		dayField.setFont(eventTitle);
		dayField.setForeground(grey);
		dayField.setBackground(Color.white);
		dayField.setBorder(panelBorder);
		dayField.setFont(otherField);
		dayField.setVisible(true);
	}

	public void setYearField()
	{
		yearField = new JTextField("2017");
		yearField.setBounds(300, 156, 50, 27);
		yearField.setHorizontalAlignment(JTextField.CENTER);
		yearField.setFont(eventTitle);
		yearField.setForeground(grey);
		yearField.setBackground(Color.white);
		yearField.setBorder(panelBorder);
		yearField.setFont(otherField);
		yearField.setVisible(true);
	}

	public void setFromTime()
	{
		fromTime = new JTextField("9:00");
		fromTime.setBounds(43, 240, 77, 27);
		fromTime.setForeground(grey);
		fromTime.setHorizontalAlignment(JTextField.CENTER);
		fromTime.setBackground(Color.white);
		fromTime.setBorder(panelBorder);
		fromTime.setFont(otherField);
		fromTime.setVisible(true);
	}

	public void setToTime()
	{
		toTime = new JTextField("10:00");
		toTime.setBounds(163, 240, 77, 27);
		toTime.setForeground(grey);
		toTime.setHorizontalAlignment(JTextField.CENTER);
		toTime.setBackground(Color.white);
		toTime.setBorder(panelBorder);
		toTime.setFont(otherField);
		toTime.setVisible(true);
	}

	public void setToLabel()
	{
		to = new JLabel("to");
		to.setBounds(132, 240, 32, 27);
		to.setForeground(grey);
		to.setFont(guideText);
		to.setVisible(true);
	}

	public void setDefaultFields()
	{
		event.setText("New Event");
		fromTime.setText("9:00");
		toTime.setText("10:00");
		monthField.setText("3");
		dayField.setText("10");
		yearField.setText("2017");
	}

	public JPanel createPanel()
	{
		return cPanel;
	}

	public JButton saveButton()
	{
		return save;
	}

	public JButton discardButton()
	{
		return discard;
	}

	public String getEventField()
	{
		return event.getText().toString();
	}

	public String getMonthField()
	{
		return monthField.getText().toString();
	}

	public String getDayField()
	{
		return dayField.getText().toString();
	}

	public String getYearField()
	{
		return yearField.getText().toString();
	}

	public String getFromTime()
	{
		return fromTime.getText().toString();
	}

	public String getToTime()
	{
		return toTime.getText().toString();
	}

	public JRadioButton getRadioEvent()
	{
		return rEvent;
	}

	public JRadioButton getRadioTask()
	{
		return rTask;
	}

	public JLabel getToLabel()
	{
		return to;
	}

	public JTextField getToTimeField()
	{
		return toTime;
	}

	private JComboBox color;

	private JRadioButton rEvent;
	private JRadioButton rTask;

	private JLabel month;
	private JLabel day;
	private JLabel year;
	private JLabel to;

	private Font eventTitle;
	private Font guideText;
	private Font otherField;

	private Color grey;
	private Color lightGrey;
	private Color red;
	private Color blue;
	private Color green;

	private JButton save;
	private JButton discard;

	private JTextField event;
	private JTextField fromTime;
	private JTextField toTime;
	private JTextField monthField;
	private JTextField dayField;
	private JTextField yearField;

	private LineBorder panelBorder;

	private JPanel cPanel;
}