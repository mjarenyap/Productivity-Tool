/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package designchallenge1;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Arturo III
 */
public class TableRenderer extends DefaultTableCellRenderer
{
    public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused, int row, int column)
    {
            super.getTableCellRendererComponent(table, value, selected, focused, row, column);
            if (column == 0 || column == 6)
                setBackground(new Color(255,255,255));

            else
            setBackground(Color.WHITE);
            setBorder(null);
            setForeground(Color.black);
            setHorizontalAlignment(JTextField.CENTER);

            if(value != null && value.toString().length() > 2)
            {
                String colorname = "";
                StringBuilder sb = new StringBuilder(value.toString());

                for(int i = 0; i < 5; i++)
                {
                    colorname += value.toString().charAt(i);
                    sb = sb.deleteCharAt(0);
                }

                if(colorname.compareTo("25500") == 0)
                    setForeground(Color.red);

                else if(colorname.compareTo("00255") == 0)
                    setForeground(Color.blue);

                else if(colorname.compareTo("02550") == 0)
                    setForeground(Color.green);

                setValue(sb.toString());
            }

            /*
            if(value != null && value.toString().length() > 2)
            {
                for(int i = 0; i < CalendarProgram.record.size(); i++)
                {
                    if((CalendarProgram.record.get(i).date.get(GregorianCalendar.DAY_OF_MONTH)+
                        CalendarProgram.record.get(i).name).equalsIgnoreCase(value.toString()))
                        setForeground(CalendarProgram.record.get(i).color);
                }
            }
            */

            return this;  
    }
}
