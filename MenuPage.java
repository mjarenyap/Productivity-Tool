import java.util.*;
import java.time.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.event.*;

public class MenuPage
{
	public MenuPage()
	{
		grey = new Color(91, 91, 91);
		lightGrey = new Color(229, 229, 229);
		activeBGcolor = new Color(51, 186, 239);
		panelBorder = new LineBorder(lightGrey, 1);
		dateToday = "";

		setMenu();
		setTimeNow();
		setLabel();
		setDateButton();
		setDayButton();
		setAgendaButton();
		setDisplayDate();
		
		collect();
	}

	public void setTimeNow()
	{
		localDate = LocalDate.now();

		int day = localDate.getDayOfMonth();
		int year = localDate.getYear();

		dateToday += localDate.getMonth().toString();
		dateToday += " ";
		dateToday += Integer.toString(day);
		dateToday += ", ";
		dateToday += Integer.toString(year);

		monthNow = localDate.getMonthValue() - 1;
		yearNow = year;
		dayNow = day;
	}

	public int getMonthNow()
	{
		return monthNow;
	}

	public int getDayNow()
	{
		return dayNow;
	}

	public int getYearNow()
	{
		return yearNow;
	}

	public void setMenu()
	{
		menu = new JPanel(null);
		menu.setBounds(0, 0, 860, 64);
		menu.setBorder(panelBorder);
		menu.setBackground(Color.WHITE);
	}

	public void setLabel()
	{
		label = new JLabel("My Productivity Tool");
		label.setBounds(30, 15, 250, 30);
		label.setBackground(Color.WHITE);
		label.setForeground(grey);
		label.setFont(new Font("Arial", Font.BOLD, 16));
	}

	public void setDateButton()
	{
		date = new JButton("Today");
		date.setBounds(220, 15, 100, 35);
		date.setBackground(new Color(229, 229, 229));
		date.setForeground(new Color(91, 91, 91));
		date.setFont(new Font("Arial", Font.PLAIN, 16));
		date.setOpaque(true);
		date.setBorderPainted(false);
		date.setBorder(null);
		date.setVisible(true);

	}

	public void setDayButton()
	{
		day = new JButton("Day");
		day.setBounds(680, 15, 61, 35);
		day.setBackground(new Color(229, 229, 229));
		day.setForeground(new Color(91, 91, 91));
		day.setFont(new Font("Arial", Font.PLAIN, 13));
		day.setOpaque(true);
		day.setBorderPainted(false);
		day.setBorder(null);
		day.setVisible(true);
	}

	public void setAgendaButton()
	{
		agenda = new JButton("Agenda");
		agenda.setBounds(741, 15, 82, 35);
		agenda.setBackground(new Color(229, 229, 229));
		agenda.setForeground(new Color(91, 91, 91));
		agenda.setFont(new Font("Arial", Font.PLAIN, 13));
		agenda.setOpaque(true);
		agenda.setBorderPainted(false);
		agenda.setBorder(null);
		agenda.setVisible(true);
	}

	public void setDisplayDate()
	{
		dispDate = new JLabel(dateToday);
		dispDate.setBounds(350, 15, 250, 30);
		dispDate.setBackground(Color.WHITE);
		dispDate.setForeground(grey);
		dispDate.setFont(new Font("Arial", Font.BOLD, 20));

	}

	public void collect()
	{
		menu.add(label);
		menu.add(date);
		menu.add(day);
		menu.add(agenda);
		menu.add(dispDate);
	}

	public void updateDateLabel(String month, String day, int year)
	{
		dispDate.setText(month + " " + day + ", " + Integer.toString(year));
	}

	public void setDayActive()
	{
		day.setBackground(activeBGcolor);
		day.setForeground(Color.white);
	}

	public void setDayInactive()
	{
		day.setBackground(lightGrey);
		day.setForeground(grey);
	}

	public void setAgendaActive()
	{
		agenda.setBackground(activeBGcolor);
		agenda.setForeground(Color.white);
	}

	public void setAgendaInactive()
	{
		agenda.setBackground(lightGrey);
		agenda.setForeground(grey);
	}

	public JPanel menuPanel()
	{
		return menu;
	}

	public JButton dayButton()
	{
		return day;
	}

	public JButton agendaButton()
	{
		return agenda;
	}

	public JButton todayButton()
	{
		return date;
	}

	private int monthNow;
	private int yearNow;
	private int dayNow;

	private LocalDate localDate;

	private LineBorder panelBorder;

	private Color lightGrey;
	private Color grey;
	private Color activeBGcolor;

	private String dateToday;

	private JLabel label;
	private JLabel dispDate;

	private JPanel menu;

	private JButton date;
	private JButton day;
	private JButton agenda;
}