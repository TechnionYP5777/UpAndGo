package view;

import java.awt.Font;

/**
 * 
 * @author Dan Abramovich
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
