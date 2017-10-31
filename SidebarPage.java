import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.event.*;

public class SidebarPage
{
	public SidebarPage()
	{
		lightGrey = new Color(229, 229, 229);
		grey = new Color(91, 91, 91);
		panelBorder = new LineBorder(lightGrey, 1);
		calendarFont = new Font("Arial", Font.PLAIN, 16);

		doneCheck = new ArrayList<JCheckBox>();
		deleteCheck = new ArrayList<JCheckBox>();
		deletedIndex = new ArrayList<Integer>();

		setCalendar();
		setCreateButton();
		setImportButton();
		setCalendarComponents();
		setJCheckBox();
		setTaskButtons();

		collect();
		collectCalendarComponents();
	}

	public void setCalendar()
	{
		calendar = new JPanel(null);
		calendar.setBounds(0, 0, 263, 560);
		calendar.setBackground(Color.WHITE);
		calendar.setBorder(panelBorder);
		calendar.setVisible(true);
	}

	public void setCalendarComponents()
	{
		monthLabel = new JLabel("January");
		yearLabel = new JLabel("Change Year:");
		cmbYear = new JComboBox();
		next = new JButton(">");
		previous = new JButton("<");

		modelCalendarTable = new DefaultTableModel()
        {
            public boolean isCellEditable(int rowIndex, int mColIndex)
            {
                return false;
            }
        };

		calendarTable = new JTable(modelCalendarTable);
        scrollCalendarTable = new JScrollPane(calendarTable);

		yearLabel.setBounds(50, 380, 100, 40);
		cmbYear.setBounds(140, 380, 100, 40);
		previous.setBounds(172, 140, 33, 33);
		next.setBounds(202, 140, 33, 33);
		scrollCalendarTable.setBounds(29, 180, 205, 200);

		GregorianCalendar cal = new GregorianCalendar();
		dayBound = cal.get(GregorianCalendar.DAY_OF_MONTH);
		monthBound = cal.get(GregorianCalendar.MONTH);
		yearBound = cal.get(GregorianCalendar.YEAR);
		monthToday = monthBound; 
		yearToday = yearBound;

		dateLabel = new JLabel(monthLabel.getText().toString() + " " + Integer.toString(yearBound));
		dateLabel.setBounds(29, 135, 300, 50);
		dateLabel.setFont(calendarFont);
		dateLabel.setForeground(grey);

		yearLabel.setForeground(grey);
		
		String[] headers = {"  S", "  M", "  T", "  W", "  T", "  F", "  S"}; //All headers
		for (int i=0; i<7; i++){
			modelCalendarTable.addColumn(headers[i]);
		}
		
		calendarTable.getParent().setBackground(calendarTable.getBackground()); //Set background

		calendarTable.getTableHeader().setResizingAllowed(false);
		calendarTable.getTableHeader().setReorderingAllowed(false);

		calendarTable.setColumnSelectionAllowed(true);
		calendarTable.setRowSelectionAllowed(true);
		calendarTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		calendarTable.setRowHeight(30);
		modelCalendarTable.setColumnCount(7);
		modelCalendarTable.setRowCount(6);

		calendarTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		calendarTable.getColumnModel().getColumn(0).setPreferredWidth(29);
		calendarTable.getColumnModel().getColumn(1).setPreferredWidth(29);
		calendarTable.getColumnModel().getColumn(2).setPreferredWidth(29);
		calendarTable.getColumnModel().getColumn(3).setPreferredWidth(29);
		calendarTable.getColumnModel().getColumn(4).setPreferredWidth(29);
		calendarTable.getColumnModel().getColumn(5).setPreferredWidth(28);
		calendarTable.getColumnModel().getColumn(6).setPreferredWidth(28);

		scrollCalendarTable.setBorder(null);
		
		for (int i = yearBound-100; i <= yearBound+100; i++)
        {
			cmbYear.addItem(String.valueOf(i));
		}
	}

	public void updateDateLabel()
	{
		dateLabel.setText(monthLabel.getText().toString() + " " + Integer.toString(yearToday));
	}

	public void setCreateButton()
	{
		create = new JButton("Create");
		create.setBounds(29, 94, 205, 37);
		create.setBackground(new Color(234, 71, 71));
		create.setForeground(Color.WHITE);
		create.setFont(new Font("Arial", Font.PLAIN, 18));
		create.setOpaque(true);
		create.setBorderPainted(false);
		create.setBorder(null);
		create.setVisible(true);
	}

	public void setImportButton()
	{
		fetcher = new JButton("Import");
		fetcher.setBounds(0, 490, 263, 48);
		fetcher.setBackground(new Color(229, 229, 229));
		fetcher.setForeground(new Color(91, 91, 91));
		fetcher.setFont(new Font("Arial", Font.PLAIN, 14));
		fetcher.setOpaque(true);
		fetcher.setBorderPainted(false);
		fetcher.setBorder(null);
		fetcher.setVisible(true);
	}

	public void setJCheckBox()
	{
		event = new JCheckBox("Event");
		task = new JCheckBox("Task");
		event.setBounds(40, 450, 100, 20);
		task.setBounds(140, 450, 100, 20);
		event.setSelected(true);
		task.setSelected(true);
		event.setVisible(true);
		task.setVisible(true);
	}

	public void setTaskButtons()
	{
		Font taskFont = new Font("Arial", Font.PLAIN, 12);
		Color red = new Color(234, 71, 71);

		done = new JButton("Mark items done");
		done.setBounds(30, 480, 100, 37);
		done.setFont(taskFont);
		done.setForeground(red);
		done.setBackground(Color.white);
		done.setOpaque(true);
		done.setBorderPainted(false);
		done.setBorder(null);
		done.setVisible(true);

		delete = new JButton("Delete items");
		delete.setBounds(140, 480, 100, 37);
		delete.setFont(taskFont);
		delete.setForeground(red);
		delete.setBackground(Color.white);
		delete.setOpaque(true);
		delete.setBorderPainted(false);
		delete.setBorder(null);
		delete.setVisible(true);
	}

	public int showDonePopup(ArrayList<Tasks> tasks)
	{
		doneCheck.clear();
		for(int i = 0; i < tasks.size(); i++)
		{
			doneCheck.add(new JCheckBox(tasks.get(i).getName()));
			if(tasks.get(i).getDone() == true)
				doneCheck.get(i).setSelected(true);
		}

		doneInnerPanel = new JPanel(null);
		doneInnerPanel.setPreferredSize(new Dimension(300, 200));
		doneInnerPanel.setBackground(Color.white);

		addDoneToPopup(tasks.size());

		doneScrollPane = new JScrollPane(doneInnerPanel);
		doneScrollPane.setBounds(0, 0, 300, 100);
		doneScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    	doneScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		doneScrollPane.setVisible(true);

		return JOptionPane.showConfirmDialog(null, doneScrollPane, "Mark your items done", JOptionPane.OK_CANCEL_OPTION);
	}

	public void addDoneToPopup(int len)
	{
		int labelOffset = 30;
		Font listFont = new Font("Arial", Font.PLAIN, 11);

		for(int i = 0; i < len; i++)
		{
			doneCheck.get(i).setBounds(20, labelOffset, 300, 20);
			doneCheck.get(i).setFont(listFont);
			doneInnerPanel.setPreferredSize(new Dimension(300, labelOffset+200));
			doneInnerPanel.add(doneCheck.get(i));
			labelOffset += 30;
		}
	}

	public ArrayList<JCheckBox> getDoneCheck()
	{
		return doneCheck;
	}

	public int showDeletePopup(ArrayList<Tasks> tasks)
	{
		deleteCheck.clear();
		deletedIndex.clear();
		for(int i = 0; i < tasks.size(); i++)
		{
			if(tasks.get(i).getDone() == true)
			{
				deleteCheck.add(new JCheckBox(tasks.get(i).getName()));
				deletedIndex.add(i);
			}
		}

		deleteInnerPanel = new JPanel(null);
		deleteInnerPanel.setPreferredSize(new Dimension(300, 200));
		deleteInnerPanel.setBackground(Color.white);

		addDeleteToPopup(deleteCheck.size());
		arrangeIndex();

		deleteScrollPane = new JScrollPane(deleteInnerPanel);
		deleteScrollPane.setBounds(0, 0, 300, 100);
		deleteScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    	deleteScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		deleteScrollPane.setVisible(true);

		return JOptionPane.showConfirmDialog(null, deleteScrollPane, "Delete your accomplished tasks", JOptionPane.OK_CANCEL_OPTION);
	}

	public void addDeleteToPopup(int len)
	{
		int labelOffset = 30;
		Font listFont = new Font("Arial", Font.PLAIN, 11);

		for(int i = 0; i < len; i++)
		{
			deleteCheck.get(i).setBounds(20, labelOffset, 300, 20);
			deleteCheck.get(i).setFont(listFont);
			deleteInnerPanel.setPreferredSize(new Dimension(300, labelOffset+200));
			deleteInnerPanel.add(deleteCheck.get(i));
			labelOffset += 30;
		}
	}

	public void arrangeIndex()
	{
		for(int i = 0; i < deletedIndex.size(); i++)
		{
			for(int j = i; j < deletedIndex.size(); j++)
			{
				if(deletedIndex.get(j) < deletedIndex.get(i))
				{
					int temp = deletedIndex.get(i);
					deletedIndex.set(i, deletedIndex.get(j));
					deletedIndex.set(j, temp);
				}
			}
		}
	}

	public void adjustIndex()
	{
		for(int i = 0; i < deletedIndex.size(); i++)
			deletedIndex.set(i, deletedIndex.get(i) - 1);
	}

	public ArrayList<JCheckBox> getDeletedCheck()
	{
		return deleteCheck;
	}

	public ArrayList<Integer> getDeletedIndex()
	{
		return deletedIndex;
	}

	public void collect()
	{
		calendar.add(create);
		calendar.add(event);
		calendar.add(task);
		calendar.add(done);
		calendar.add(delete);
		//calendar.add(fetcher);
	}

	public void collectCalendarComponents()
	{
		calendar.add(next);
		calendar.add(previous);
		calendar.add(monthLabel);
		calendar.add(dateLabel);
		calendar.add(yearLabel);
		calendar.add(cmbYear);
		calendar.add(scrollCalendarTable);
	}

	public void modifyMonthYear(int mOffset, int yOffset)
	{
		monthToday = mOffset;
		yearToday += yOffset;
	}

	public void modifyMonth(int offset)
	{
		monthToday += offset;
	}

	public void modifyYear(int offset)
	{
		yearToday = offset;
	}

	public void forceChangeDate(int m, int y)
	{
		monthToday = m;
		yearToday = y;
	}

	public JPanel sidebarPanel()
	{
		return calendar;
	}

	public JButton createButton()
	{
		return create;
	}

	public int getMonthToday()
	{
		return monthToday;
	}

	public int getYearToday()
	{
		return yearToday;
	}

	public JButton previousButton()
	{
		return previous;
	}

	public JButton nextButton()
	{
		return next;
	}

	public JButton doneButton()
	{
		return done;
	}

	public JButton deleteButton()
	{
		return delete;
	}

	public int getYearBound()
	{
		return yearBound;
	}

	public JComboBox comboYear()
	{
		return cmbYear;
	}

	public JLabel getMonthLabel()
	{
		return monthLabel;
	}

	public DefaultTableModel getModelCalendarTable()
	{
		return modelCalendarTable;
	}

	public JTable getCalendarTable()
	{
		return calendarTable;
	}

	public JCheckBox taskBox()
	{
		return task;
	}

	public JCheckBox eventBox()
	{
		return event;
	}

	private Font calendarFont;

	private LineBorder panelBorder;
	private Color lightGrey;
	private Color grey;
	private JPanel calendar;

	private JButton create;
	private JButton fetcher;
	private JButton delete;
	private JButton done;

	private JCheckBox task;
	private JCheckBox event;

	private ArrayList<JCheckBox> doneCheck;
	private ArrayList<JCheckBox> deleteCheck;
	private ArrayList<Integer> deletedIndex;

	private JScrollPane doneScrollPane;
	private JPanel doneInnerPanel;
	private JScrollPane deleteScrollPane;
	private JPanel deleteInnerPanel;

	/* Elements for the calendar */
	public int yearBound, monthBound, dayBound, yearToday, monthToday;
	private JLabel monthLabel, yearLabel, dateLabel;
	private JComboBox cmbYear;
	private JScrollPane scrollCalendarTable;
	private JTable calendarTable;
	private DefaultTableModel modelCalendarTable;
	private JButton next, previous;
}