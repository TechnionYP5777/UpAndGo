package view;

import java.awt.Font;

public interface CellFont {

    public Font getFont(int row, int column);
    public void setFont(Font font, int row, int column);
    public void setFont(Font font, int[] rows, int[] columns);
}
