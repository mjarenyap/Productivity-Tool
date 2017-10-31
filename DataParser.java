import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.awt.Color;
import java.util.Calendar;

public interface DataParser
{
	public void searchDate();
	public int searchMonth(String dateline);
	public int searchDay(String dateline);
	public int searchYear(String dateline);
	public void searchColor();
	public void searchEvent();
	public ArrayList<String> getEvents();
	public ArrayList<Color> getColors();
	public ArrayList<Calendar> getDates();
}