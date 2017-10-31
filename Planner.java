import java.util.*;
import java.time.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.event.*;

public class Planner
{
	public JFrame plan;
	public Container mainPane;

	public SidebarPage sidebarPanel;
	public MenuPage menuPanel;
	public CreatePage createPanel;
	public AgendaPage agendaPanel;
	public DayPage dayPanel;

	public int clickedCol = 1;
	public int clickedRow = 2;

	public EventsReader eventLoader;
	public TasksReader taskLoader;

	public EventsWriter eventWriter;
	public TasksWriter taskWriter;

	public ArrayList<Events> events;
	public ArrayList<Tasks> tasks;

	public Planner()
	{
		plan = new JFrame("Planner Program");
		mainPane = plan.getContentPane();
		plan.setSize(860, 560);

		/* Starting up models */
		events = new ArrayList<Events>();
		tasks = new ArrayList<Tasks>();
		eventLoader = new EventsReader();
		taskLoader = new TasksReader();
		eventWriter = new EventsWriter();
		taskWriter = new TasksWriter();

		loadEvents();
		loadTasks();

		/* Starting up panels */
		menuPanel = new MenuPage();
		sidebarPanel = new SidebarPage();
		createPanel = new CreatePage();
		agendaPanel = new AgendaPage();
		dayPanel = new DayPage();

		/* Adding panels to pane */
		mainPane.setLayout(null);
		mainPane.add(menuPanel.menuPanel());
		mainPane.add(sidebarPanel.sidebarPanel());
		mainPane.add(createPanel.createPanel());
		mainPane.add(agendaPanel.scrollPanel());
		mainPane.add(dayPanel.scrollPanel());

		/* First impression of the program */
		createPanel.createPanel().setVisible(false);
		agendaPanel.scrollPanel().setVisible(false);
		dayPanel.scrollPanel().setVisible(true);
		menuPanel.setDayActive();

		agendaPanel.updateDetails(menuPanel, events, tasks, sidebarPanel.eventBox().isSelected(), sidebarPanel.taskBox().isSelected());
		dayPanel.updateDetails(menuPanel, events, tasks, sidebarPanel.eventBox().isSelected(), sidebarPanel.taskBox().isSelected());

		/*adding action listeners to the buttons.*/
		menuPanel.dayButton().addActionListener(new dayButton_action());
		menuPanel.agendaButton().addActionListener(new agendaButton_action());
		menuPanel.todayButton().addActionListener(new todayButton_action());

		sidebarPanel.createButton().addActionListener(new createButton_action());
		sidebarPanel.previousButton().addActionListener(new btnPrev_action());
		sidebarPanel.nextButton().addActionListener(new btnNext_action());
		sidebarPanel.comboYear().addActionListener(new cmbYear_action());
		sidebarPanel.taskBox().addActionListener(new checkbox_action());
		sidebarPanel.eventBox().addActionListener(new checkbox_action());
		sidebarPanel.doneButton().addActionListener(new doneButton_action());
		sidebarPanel.deleteButton().addActionListener(new deleteButton_action());

		createPanel.saveButton().addActionListener(new saveButton_action());
		createPanel.discardButton().addActionListener(new discardButton_action());
		createPanel.getRadioEvent().addActionListener(new radioEvent_action());
		createPanel.getRadioTask().addActionListener(new radioTask_action());

		/* Clickable calendar functions */
		sidebarPanel.getCalendarTable().addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent evt)
            {
            	int col = sidebarPanel.getCalendarTable().getSelectedColumn();
	            int row = sidebarPanel.getCalendarTable().getSelectedRow();

            	if(sidebarPanel.getCalendarTable().getModel().getValueAt(row, col) != null)
            	{
            		clickedCol = col;
	            	clickedRow = row;
	                menuPanel.updateDateLabel(sidebarPanel.getMonthLabel().getText().toString(),
	                	sidebarPanel.getCalendarTable().getModel().getValueAt(row, col).toString(), sidebarPanel.getYearToday());

	                agendaPanel.resetPanel();
					agendaPanel.updateDetails(sidebarPanel, events, tasks, clickedRow, clickedCol, sidebarPanel.eventBox().isSelected(), sidebarPanel.taskBox().isSelected());
					dayPanel.resetPanel();
					dayPanel.updateDetails(sidebarPanel, events, tasks, clickedRow, clickedCol, sidebarPanel.eventBox().isSelected(), sidebarPanel.taskBox().isSelected());
		            menuPanel.menuPanel().setVisible(true);
	            }
            }
        });

		refreshCalendar(sidebarPanel.getMonthToday(), sidebarPanel.getYearToday());
		plan.setLocationRelativeTo(null);
		plan.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		plan.setVisible(true);
		plan.setResizable(false);
	}

	public void loadEvents()
	{
		for(int i = 0; i < eventLoader.getEventName().size(); i++)
		{
			String n = eventLoader.getEventName().get(i);
			Calendar d = eventLoader.getEventDate().get(i);
			String f = eventLoader.getEventFrom().get(i);
			String t = eventLoader.getEventTo().get(i);
			
			events.add(new Events(n, d, f, t));
		}
	}

	public void loadTasks()
	{
		for(int i = 0; i < taskLoader.getTaskName().size(); i++)
		{
			String n = taskLoader.getTaskName().get(i);
			Calendar d = taskLoader.getTaskDate().get(i);
			String f = taskLoader.getTaskFrom().get(i);
			boolean o = taskLoader.getTaskDone().get(i);

			tasks.add(new Tasks(n, d, f, o));
		}
	}

	public void refreshCalendar(int month, int year)
	{
		String[] months =  {"January", "February", "March", "April", "May", "June", "July",
							"August", "September", "October", "November", "December"};
		int nod, som, i, j;
		
		sidebarPanel.previousButton().setEnabled(true);
		sidebarPanel.nextButton().setEnabled(true);
		if (month == 0 && year <= sidebarPanel.getYearBound()-10)
	        sidebarPanel.previousButton().setEnabled(false);
		if (month == 11 && year >= sidebarPanel.getYearBound()+100)
	        sidebarPanel.nextButton().setEnabled(false);
	            
		sidebarPanel.getMonthLabel().setText(months[month]);
		sidebarPanel.comboYear().setSelectedItem(""+year);
		sidebarPanel.updateDateLabel();
		
		for (i = 0; i < 6; i++)
			for (j = 0; j < 7; j++)
				sidebarPanel.getModelCalendarTable().setValueAt(null, i, j);
		
		GregorianCalendar cal = new GregorianCalendar(year, month, 1);
		nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		som = cal.get(GregorianCalendar.DAY_OF_WEEK);
		
		for (i = 1; i <= nod; i++)
	    {
			int row = new Integer((i+som-2)/7);
			int column  =  (i+som-2)%7;

			sidebarPanel.getModelCalendarTable().setValueAt(i, row, column);
		}

		sidebarPanel.getCalendarTable().setDefaultRenderer(sidebarPanel.getCalendarTable().getColumnClass(0), new TableRenderer());
	}

	class createButton_action implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			menuPanel.setAgendaInactive();
			menuPanel.setDayInactive();

			createPanel.createPanel().setVisible(true);
			dayPanel.scrollPanel().setVisible(false);
			agendaPanel.scrollPanel().setVisible(false);

			createPanel.getToTimeField().setVisible(true);
			createPanel.getToLabel().setVisible(true);

			createPanel.getRadioTask().setSelected(false);
			createPanel.getRadioEvent().setSelected(false);
		}
	}

	class dayButton_action implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			menuPanel.setDayActive();
			menuPanel.setAgendaInactive();

			agendaPanel.scrollPanel().setVisible(false);
			dayPanel.scrollPanel().setVisible(true);
			createPanel.createPanel().setVisible(false);
		}
	}

	class todayButton_action implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String[] months =  {"January", "February", "March", "April", "May", "June", "July",
								"August", "September", "October", "November", "December"};

			String dayNow = Integer.toString(menuPanel.getDayNow());
			menuPanel.updateDateLabel(months[menuPanel.getMonthNow()], dayNow, menuPanel.getYearNow());

			menuPanel.setDayActive();
			menuPanel.setAgendaInactive();

			agendaPanel.scrollPanel().setVisible(false);
			dayPanel.scrollPanel().setVisible(true);
			createPanel.createPanel().setVisible(false);

			sidebarPanel.forceChangeDate(menuPanel.getMonthNow(), menuPanel.getYearNow());
			refreshCalendar(menuPanel.getMonthNow(), menuPanel.getYearNow());

			agendaPanel.resetPanel();
			agendaPanel.updateDetails(menuPanel, events, tasks, sidebarPanel.eventBox().isSelected(), sidebarPanel.taskBox().isSelected());
			dayPanel.resetPanel();
			dayPanel.updateDetails(menuPanel, events, tasks, sidebarPanel.eventBox().isSelected(), sidebarPanel.taskBox().isSelected());
			menuPanel.menuPanel().setVisible(true);
		}
	}

	class agendaButton_action implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			menuPanel.setAgendaActive();
			menuPanel.setDayInactive();

			agendaPanel.scrollPanel().setVisible(true);
			dayPanel.scrollPanel().setVisible(false);
			createPanel.createPanel().setVisible(false);
		}
	}

	class saveButton_action implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(createPanel.checkDuplicate(events, tasks) == false)
			{
				String eventField = createPanel.getEventField();
				String monthField = createPanel.getMonthField();
				String dayField = createPanel.getDayField();
				String yearField = createPanel.getYearField();
				String fromTime = createPanel.getFromTime();
				String toTime = createPanel.getToTime();

				Calendar tempDate = Calendar.getInstance();
				tempDate.set(Integer.parseInt(yearField), Integer.parseInt(monthField), Integer.parseInt(dayField));

				if(createPanel.getRadioEvent().isSelected() == true)
				{
					events.add(new Events(eventField, tempDate, fromTime, toTime));
					eventWriter.setNameWriter(events);
					eventWriter.setToWriter(events);
					eventWriter.setFromWriter(events);
					eventWriter.setDateWriter(events);
					JOptionPane.showMessageDialog(null, "Successfully created an event!");
				}

				else if(createPanel.getRadioTask().isSelected() == true)
				{
					tasks.add(new Tasks(eventField, tempDate, fromTime, false));
					taskWriter.setNameWriter(tasks);
					taskWriter.setFromWriter(tasks);
					taskWriter.setDateWriter(tasks);
					taskWriter.setDoneWriter(tasks);
					JOptionPane.showMessageDialog(null, "Successfully created a task!");
				}

				else JOptionPane.showMessageDialog(null, "Is it an event or task?");

				createPanel.setDefaultFields();
				menuPanel.setDayActive();
				menuPanel.setAgendaInactive();
				agendaPanel.scrollPanel().setVisible(false);
				dayPanel.scrollPanel().setVisible(true);
				createPanel.createPanel().setVisible(false);
				createPanel.getRadioEvent().setSelected(false);
				createPanel.getRadioTask().setSelected(false);

				agendaPanel.resetPanel();
				agendaPanel.updateDetails(sidebarPanel, events, tasks, clickedRow, clickedCol, sidebarPanel.eventBox().isSelected(), sidebarPanel.taskBox().isSelected());
				dayPanel.resetPanel();
				dayPanel.updateDetails(sidebarPanel, events, tasks, clickedRow, clickedCol, sidebarPanel.eventBox().isSelected(), sidebarPanel.taskBox().isSelected());
			}

			else if(createPanel.getRadioTask().isSelected() == false && createPanel.getRadioEvent().isSelected() == false)
				JOptionPane.showMessageDialog(null, "Is it an event or task?");

			else
			{
				createPanel.setDefaultFields();
				JOptionPane.showMessageDialog(null, "Unable to create the event/task.");
			}
		}
	}

	class discardButton_action implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			createPanel.setDefaultFields();
			menuPanel.setDayActive();
			menuPanel.setAgendaInactive();
			agendaPanel.scrollPanel().setVisible(false);
			dayPanel.scrollPanel().setVisible(true);
			createPanel.createPanel().setVisible(false);
			createPanel.getRadioEvent().setSelected(false);
			createPanel.getRadioTask().setSelected(false);
		}
	}

	class btnPrev_action implements ActionListener
    {
		public void actionPerformed(ActionEvent e)
        {
			if (sidebarPanel.getMonthToday() == 0)
            	sidebarPanel.modifyMonthYear(11, -1);

			else
            {
            	sidebarPanel.modifyMonth(-1);
			}

			refreshCalendar(sidebarPanel.getMonthToday(), sidebarPanel.getYearToday());
		}
	}

	class radioEvent_action implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			createPanel.getRadioTask().setSelected(false);
			createPanel.getToTimeField().setVisible(true);
			createPanel.getToLabel().setVisible(true);
		}
	}

	class radioTask_action implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			createPanel.getRadioEvent().setSelected(false);
			createPanel.getToTimeField().setVisible(false);
			createPanel.getToLabel().setVisible(false);
		}
	}

	class btnNext_action implements ActionListener
   	{
		public void actionPerformed(ActionEvent e)
        {
			if (sidebarPanel.getMonthToday() == 11)
				sidebarPanel.modifyMonthYear(0, 1);

			else
            {
            	sidebarPanel.modifyMonth(1);
			}

			refreshCalendar(sidebarPanel.getMonthToday(), sidebarPanel.getYearToday());
		}
	}

	class cmbYear_action implements ActionListener
    {
		public void actionPerformed (ActionEvent e)
        {
			if (sidebarPanel.comboYear().getSelectedItem() != null)
            {
				String b = sidebarPanel.comboYear().getSelectedItem().toString();
				sidebarPanel.modifyYear(Integer.parseInt(b));
				refreshCalendar(sidebarPanel.getMonthToday(), sidebarPanel.getYearToday());
			}
		}
	}

	class checkbox_action implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			agendaPanel.resetPanel();
			agendaPanel.updateDetails(sidebarPanel, events, tasks, clickedRow, clickedCol, sidebarPanel.eventBox().isSelected(), sidebarPanel.taskBox().isSelected());
			dayPanel.resetPanel();
			dayPanel.updateDetails(sidebarPanel, events, tasks, clickedRow, clickedCol, sidebarPanel.eventBox().isSelected(), sidebarPanel.taskBox().isSelected());
		}
	}

	class doneButton_action implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			int addConfirm = sidebarPanel.showDonePopup(tasks);

			if(addConfirm == JOptionPane.OK_OPTION)
			{
				for(int i = 0; i < tasks.size(); i++)
				{
					if(sidebarPanel.getDoneCheck().get(i).isSelected() == true)
						tasks.get(i).markAsDone();

					else tasks.get(i).markAsUndone();
				}

				taskWriter.setNameWriter(tasks);
				taskWriter.setFromWriter(tasks);
				taskWriter.setDateWriter(tasks);
				taskWriter.setDoneWriter(tasks);

				agendaPanel.resetPanel();
				agendaPanel.updateDetails(sidebarPanel, events, tasks, clickedRow, clickedCol, sidebarPanel.eventBox().isSelected(), sidebarPanel.taskBox().isSelected());
				dayPanel.resetPanel();
				dayPanel.updateDetails(sidebarPanel, events, tasks, clickedRow, clickedCol, sidebarPanel.eventBox().isSelected(), sidebarPanel.taskBox().isSelected());
			}
		}
	}

	class deleteButton_action implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			int addConfirm = sidebarPanel.showDeletePopup(tasks);

			if(addConfirm == JOptionPane.OK_OPTION)
			{
				for(int i = 0; i < sidebarPanel.getDeletedCheck().size(); i++)
					if(sidebarPanel.getDeletedCheck().get(i).isSelected() == true)
					{
						int index = sidebarPanel.getDeletedIndex().get(i);
						tasks.remove(index);
						sidebarPanel.adjustIndex();
					}

				taskWriter.setNameWriter(tasks);
				taskWriter.setFromWriter(tasks);
				taskWriter.setDateWriter(tasks);
				taskWriter.setDoneWriter(tasks);

				agendaPanel.resetPanel();
				agendaPanel.updateDetails(sidebarPanel, events, tasks, clickedRow, clickedCol, sidebarPanel.eventBox().isSelected(), sidebarPanel.taskBox().isSelected());
				dayPanel.resetPanel();
				dayPanel.updateDetails(sidebarPanel, events, tasks, clickedRow, clickedCol, sidebarPanel.eventBox().isSelected(), sidebarPanel.taskBox().isSelected());
			}
		}
	}
}