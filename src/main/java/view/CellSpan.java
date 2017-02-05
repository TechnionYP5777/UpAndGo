package view;

public interface CellSpan {
	  int ROW    = 0;
	  int COLUMN = 1;
	  
	  int[] getSpan(int row, int column);
	  void setSpan(int[] span, int row, int column);
	  
	  boolean isVisible(int row, int column);
	  
	  void combine(int[] rows, int[] columns);
	  void split(int row, int column);
}
