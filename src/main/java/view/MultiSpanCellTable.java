package view;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Enumeration;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/**
 * 
 * This code was taken from http://www.java2s.com/Code/Java/Swing-Components/MultiSpanCellTableExample.htm
 * 
 * @author danabra
 * @since 13-01-2017
 * 
 * Class for displaying table that allows merging cells.
 * 
 */
@SuppressWarnings("serial")
public class MultiSpanCellTable extends JTable {

	public MultiSpanCellTable(final TableModel model) {
		super(model);
		setUI(new MultiSpanCellTableUI());
		getTableHeader().setReorderingAllowed(false);
		setCellSelectionEnabled(false);
		// setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	}

	@Override
	public Rectangle getCellRect(int row, int column, final boolean includeSpacing) {
		if (row < 0 || column < 0 || getRowCount() <= row || getColumnCount() <= column)
			return super.getCellRect(row, column, includeSpacing);
		final CellSpan cellAtt = (CellSpan) ((AttributiveCellTableModel) getModel()).getCellAttribute();
		if (!cellAtt.isVisible(row, column)) {
			final int temp_row = row, temp_column = column;
			row += cellAtt.getSpan(temp_row, temp_column)[CellSpan.ROW];
			column += cellAtt.getSpan(temp_row, temp_column)[CellSpan.COLUMN];
		}
		final int[] n = cellAtt.getSpan(row, column);

		int index = 0;
		final int columnMargin = getColumnModel().getColumnMargin();
		final Rectangle $ = new Rectangle();
		final int aCellHeight = rowHeight + rowMargin;
		$.y = row * aCellHeight;
		$.height = aCellHeight * n[CellSpan.ROW];

		final Enumeration eeration = getColumnModel().getColumns();
		for (; eeration.hasMoreElements(); ++index) {
			$.width = columnMargin + ((TableColumn) eeration.nextElement()).getWidth() - 1;
			if (index == column)
				break;
			$.x += $.width;
		}
		for (int ¢ = 0; ¢ < n[CellSpan.COLUMN] - 1; ++¢)
			$.width += ((TableColumn) eeration.nextElement()).getWidth() + columnMargin;

		if (!includeSpacing) {
			final Dimension spacing = getIntercellSpacing();
			$.setBounds($.x + spacing.width / 2, $.y + spacing.height / 2, $.width - spacing.width,
					$.height - spacing.height);
		}
		return $;
	}

	private int[] rowColumnAtPoint(final Point p) {
		final int[] $ = { -1, -1 };
		final int row = p.y / (rowHeight + rowMargin);
		if (row < 0 || getRowCount() <= row)
			return $;
		final int column = getColumnModel().getColumnIndexAtX(p.x);

		final CellSpan cellAtt = (CellSpan) ((AttributiveCellTableModel) getModel()).getCellAttribute();

		if (cellAtt.isVisible(row, column)) {
			$[CellSpan.COLUMN] = column;
			$[CellSpan.ROW] = row;
			return $;
		}
		$[CellSpan.COLUMN] = column + cellAtt.getSpan(row, column)[CellSpan.COLUMN];
		$[CellSpan.ROW] = row + cellAtt.getSpan(row, column)[CellSpan.ROW];
		return $;
	}

	@Override
	public int rowAtPoint(final Point ¢) {
		return rowColumnAtPoint(¢)[CellSpan.ROW];
	}

	@Override
	public int columnAtPoint(final Point ¢) {
		return rowColumnAtPoint(¢)[CellSpan.COLUMN];
	}

	@Override
	public void columnSelectionChanged(final ListSelectionEvent __) {
		repaint();
	}

	@Override
	public void valueChanged(final ListSelectionEvent e) {
		final int firstIndex = e.getFirstIndex(), lastIndex = e.getLastIndex();
		if (firstIndex == -1 && lastIndex == -1)
			repaint();
		final Rectangle dirtyRegion = getCellRect(firstIndex, 0, false);
		final int numCoumns = getColumnCount();
		int index = firstIndex;
		for (int ¢ = 0; ¢ < numCoumns; ++¢)
			dirtyRegion.add(getCellRect(index, ¢, false));
		index = lastIndex;
		for (int ¢ = 0; ¢ < numCoumns; ++¢)
			dirtyRegion.add(getCellRect(index, ¢, false));
		repaint(dirtyRegion.x, dirtyRegion.y, dirtyRegion.width, dirtyRegion.height);
	}

}
