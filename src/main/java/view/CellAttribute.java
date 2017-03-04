package view;

import java.awt.Dimension;

public interface CellAttribute {
	void addColumn();

	void addRow();

	void insertRow(int row);

	Dimension getSize();

	void setSize(Dimension size);

}
