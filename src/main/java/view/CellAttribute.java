package view;

import java.awt.Dimension;

/**
 * 
 * This code was taken from http://www.java2s.com/Code/Java/Swing-Components/MultiSpanCellTableExample.htm
 * 
 * @author danabra
 * @since 13-01-2017
 * 
 * Class for storing table cell's position and dimention properties.
 * 
 */
public interface CellAttribute {
	void addColumn();

	void addRow();

	void insertRow(int row);

	Dimension getSize();

	void setSize(Dimension size);

}
