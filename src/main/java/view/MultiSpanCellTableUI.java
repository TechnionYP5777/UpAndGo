package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicTableUI;
import javax.swing.table.TableCellRenderer;

public class MultiSpanCellTableUI extends BasicTableUI {

	@Override
	@SuppressWarnings("unused")
	public void paint(final Graphics g, final JComponent c) {

		final Rectangle oldClipBounds = g.getClipBounds();
		final Rectangle clipBounds    = new Rectangle(oldClipBounds);
		final int tableWidth   = table.getColumnModel().getTotalColumnWidth();
		clipBounds.width = Math.min(clipBounds.width, tableWidth);
		g.setClip(clipBounds);

		final int firstIndex = table.rowAtPoint(new Point(0, clipBounds.y));
		final int  lastIndex = table.getRowCount()-1;

		final Rectangle rowRect = new Rectangle(0,0,
				tableWidth, table.getRowHeight() + table.getRowMargin());
		rowRect.y = firstIndex*rowRect.height;

		for (int index = firstIndex; index <= lastIndex; ++index) {
			if (rowRect.intersects(clipBounds))
				//System.out.println();                  // debug
				//System.out.print("" + index +": ");    // row
				paintRow(g, index);
			rowRect.y += rowRect.height;
		}
		g.setClip(oldClipBounds);
	}

	private void paintRow(final Graphics g, final int row) {
		final Rectangle rect = g.getClipBounds();
		boolean drawn  = false;

		final AttributiveCellTableModel tableModel = (AttributiveCellTableModel)table.getModel();
		final CellSpan cellAtt = (CellSpan)tableModel.getCellAttribute();
		final int numColumns = table.getColumnCount();

		for (int column = 0; column < numColumns; ++column) {
			final Rectangle cellRect = table.getCellRect(row,column,true);
			int cellRow,cellColumn;
			if (cellAtt.isVisible(row,column)) {
				cellRow    = row;
				cellColumn = column;
				//  System.out.print("   "+column+" ");  // debug
			} else {
				cellRow    = row + cellAtt.getSpan(row,column)[CellSpan.ROW];
				cellColumn = column + cellAtt.getSpan(row,column)[CellSpan.COLUMN];
				//  System.out.print("  ("+column+")");  // debug
			}
			if (cellRect.intersects(rect)) {
				drawn = true;
				paintCell(g, cellRect, cellRow, cellColumn);
			} else if (drawn) break; 
		}

	}

	private void paintCell(final Graphics g, final Rectangle cellRect, final int row, final int column) {
		final int spacingHeight = table.getRowMargin();
		final int spacingWidth  = table.getColumnModel().getColumnMargin();

		final AttributiveCellTableModel tableModel = (AttributiveCellTableModel)table.getModel();
		final ColoredCell cellAtt = (ColoredCell)tableModel.getCellAttribute();
		final Color c = g.getColor();
		g.setColor(table.getGridColor());
		g.drawRect(cellRect.x,cellRect.y,cellRect.width-1,cellRect.height-1);
		g.setColor(cellAtt.getBackground(row, column));
		g.fillRect(cellRect.x,cellRect.y,cellRect.width-1,cellRect.height-1);
		g.setColor(c);

		cellRect.setBounds(cellRect.x + spacingWidth/2, cellRect.y + spacingHeight/2,
				cellRect.width - spacingWidth, cellRect.height - spacingHeight);

		if (table.isEditing() && table.getEditingRow()==row &&
				table.getEditingColumn()==column) {
			final Component component = table.getEditorComponent();
			component.setBounds(cellRect);
			component.validate();
		}
		else {
			final TableCellRenderer renderer = table.getCellRenderer(row, column);
			final Component component = table.prepareRenderer(renderer, row, column);

			if (component.getParent() == null)
				rendererPane.add(component);
			rendererPane.paintComponent(g, component, table, cellRect.x, cellRect.y,
					cellRect.width, cellRect.height, true);
		}
	}    
}

