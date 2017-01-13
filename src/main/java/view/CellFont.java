package view;

import java.awt.Font;

public interface CellFont {

    Font getFont(int row, int column);
    void setFont(Font f, int row, int column);
    void setFont(Font f, int[] rows, int[] columns);
}
