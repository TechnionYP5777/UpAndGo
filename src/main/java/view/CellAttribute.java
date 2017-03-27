package view;

import java.awt.Dimension;

/**
 * 
 * @author Dan Abramovich
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
