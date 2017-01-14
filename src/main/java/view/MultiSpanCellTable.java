package view;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Enumeration;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

@SuppressWarnings("serial")
public class MultiSpanCellTable extends JTable {

	  public MultiSpanCellTable(TableModel model) {
	    super(model);
	    setUI(new MultiSpanCellTableUI());
	    getTableHeader().setReorderingAllowed(false);
	    setCellSelectionEnabled(false);
	    //setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	  }
	  
	  
	  @Override
	public Rectangle getCellRect(int row, int column, boolean includeSpacing) {
	    Rectangle $ = super.getCellRect(row,column,includeSpacing);
	    if ((row <0) || (column<0) ||
	        (getRowCount() <= row) || (getColumnCount() <= column))
			return $;
	    CellSpan cellAtt = (CellSpan)((AttributiveCellTableModel)getModel()).getCellAttribute();
	    if (! cellAtt.isVisible(row,column)) {
	      int temp_row    = row;
	      int temp_column = column;
	      row    += cellAtt.getSpan(temp_row,temp_column)[CellSpan.ROW];
	      column += cellAtt.getSpan(temp_row,temp_column)[CellSpan.COLUMN];      
	    }
	    int[] n = cellAtt.getSpan(row,column);

	    int index = 0;
	    int columnMargin = getColumnModel().getColumnMargin();
	    Rectangle cellFrame = new Rectangle();
	    int aCellHeight = rowHeight + rowMargin;
	    cellFrame.y = row * aCellHeight;
	    cellFrame.height = aCellHeight * n[CellSpan.ROW];
	    
	    Enumeration eeration = getColumnModel().getColumns();
	    for (; eeration.hasMoreElements(); ++index) {
			TableColumn aColumn = (TableColumn) eeration.nextElement();
			cellFrame.width = columnMargin + aColumn.getWidth() - 1;
			if (index == column)
				break;
			cellFrame.x += cellFrame.width;
		}
	    for (int ¢=0;¢< n[CellSpan.COLUMN]-1;++¢)
			cellFrame.width += ((TableColumn) eeration.nextElement()).getWidth() + columnMargin;
	    
	    

	    if (!includeSpacing) {
	      Dimension spacing = getIntercellSpacing();
	      cellFrame.setBounds(cellFrame.x +      spacing.width/2,
	        cellFrame.y +      spacing.height/2,
	        cellFrame.width -  spacing.width,
	        cellFrame.height - spacing.height);
	    }
	    return cellFrame;
	  }
	  
	  
	  private int[] rowColumnAtPoint(Point p) {
	    int[] $ = {-1,-1};
	    int row = p.y / (rowHeight + rowMargin);
	    if ((row <0)||(getRowCount() <= row)) return $;
	    int column = getColumnModel().getColumnIndexAtX(p.x);

	    CellSpan cellAtt = (CellSpan)((AttributiveCellTableModel)getModel()).getCellAttribute();

	    if (cellAtt.isVisible(row,column)) {
	      $[CellSpan.COLUMN] = column;
	      $[CellSpan.ROW   ] = row;
	      return $;
	    }
	    $[CellSpan.COLUMN] = column + cellAtt.getSpan(row,column)[CellSpan.COLUMN];
	    $[CellSpan.ROW   ] = row + cellAtt.getSpan(row,column)[CellSpan.ROW];
	    return $;
	  }

	  
	  @Override
	public int rowAtPoint(Point ¢) {
	    return rowColumnAtPoint(¢)[CellSpan.ROW];
	  }
	  @Override
	public int columnAtPoint(Point ¢) {
	    return rowColumnAtPoint(¢)[CellSpan.COLUMN];
	  }
	 

	  
	@Override
	public void columnSelectionChanged(ListSelectionEvent __) {
	    repaint();
	  }

	  @Override
	public void valueChanged(ListSelectionEvent e) {
	    int firstIndex = e.getFirstIndex();
	    int  lastIndex = e.getLastIndex();
	    if (firstIndex == -1 && lastIndex == -1)
			repaint();
	    Rectangle dirtyRegion = getCellRect(firstIndex, 0, false);
	    int numCoumns = getColumnCount();
	    int index = firstIndex;
	    for (int ¢=0;¢<numCoumns;++¢)
			dirtyRegion.add(getCellRect(index, ¢, false));
	    index = lastIndex;
	    for (int ¢=0;¢<numCoumns;++¢)
			dirtyRegion.add(getCellRect(index, ¢, false));
	    repaint(dirtyRegion.x, dirtyRegion.y, dirtyRegion.width, dirtyRegion.height);
	  }
	 
	}
