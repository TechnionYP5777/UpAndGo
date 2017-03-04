package view;

import java.awt.Color;

public interface ColoredCell {

	Color getForeground(int row, int column);

	void setForeground(Color c, int row, int column);

	void setForeground(Color c, int[] rows, int[] columns);

	Color getBackground(int row, int column);

	void setBackground(Color c, int row, int column);

	void setBackground(Color c, int[] rows, int[] columns);

}
