package view;

import java.awt.Font;

/**
 * 
 * This code was taken from http://www.java2s.com/Code/Java/Swing-Components/MultiSpanCellTableExample.htm
 * 
 * @author danabra
 * @since 13-01-2017
 * 
 * Class for storing font information that us used in cells.
 * 
 */
public interface CellFont {

	Font getFont(int row, int column);

	void setFont(Font f, int row, int column);

	void setFont(Font f, int[] rows, int[] columns);
}
