package view;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

class DefaultCellAttribute 
//implements CellAttribute ,CellSpan  {
implements CellAttribute ,CellSpan ,ColoredCell ,CellFont {

	//
	// !!!! CAUTION !!!!!
	// these values must be synchronized to Table data
	//
	protected int rowSize;
	protected int columnSize;
	protected int[][][] span;                   // CellSpan
	protected Color[][] foreground;             // ColoredCell
	protected Color[][] background;             //
	protected Font[][]  font;                   // CellFont

	public DefaultCellAttribute() {
		this(1,1);
	}

	public DefaultCellAttribute(final int numRows, final int numColumns) {
		setSize(new Dimension(numColumns, numRows)); 
	}

	protected void initValue() {
		for(int i=0; i<span.length;++i)
			for (int j = 0; j < span[i].length; ++j)
				span[i][j][CellSpan.ROW] = span[i][j][CellSpan.COLUMN] = 1;
	}


	//
	// CellSpan
	//
	@Override
	public int[] getSpan(final int row, final int column) {
		return isOutOfBounds(row, column) ? new int[] { 1, 1 } : span[row][column];
	}

	@Override
	public void setSpan(final int[] span, final int row, final int column) {
		if (!isOutOfBounds(row, column))
			this.span[row][column] = span;
	}

	@Override
	public boolean isVisible(final int row, final int column) {
		return !isOutOfBounds(row, column) && span[row][column][CellSpan.COLUMN] >= 1 && span[row][column][CellSpan.ROW] >= 1;
	}

	@Override
	public void combine(final int[] rows, final int[] columns) {
		if (isOutOfBounds(rows, columns)) return;
		final int    rowSpan  = rows.length;
		final int columnSpan  = columns.length;
		final int startRow    = rows[0];
		final int startColumn = columns[0];
		for (int i=0;i<rowSpan;++i)
			for (int j = 0; j < columnSpan; ++j)
				if (span[i + startRow][j + startColumn][CellSpan.COLUMN] != 1
						|| span[i + startRow][j + startColumn][CellSpan.ROW] != 1)
					return;
		for (int i=0,ii=0;i<rowSpan;++i,--ii)
			for (int j = 0, jj = 0; j < columnSpan; ++j, --jj) {
				span[i + startRow][j + startColumn][CellSpan.COLUMN] = jj;
				span[i + startRow][j + startColumn][CellSpan.ROW] = ii;
			}
		span[startRow][startColumn][CellSpan.COLUMN] = columnSpan;
		span[startRow][startColumn][CellSpan.ROW]    =    rowSpan;

	}

	@Override
	public void split(final int row, final int column) {
		if (!isOutOfBounds(row, column))
			for (int i = 0; i < span[row][column][CellSpan.ROW]; ++i)
				for (int j = 0; j < span[row][column][CellSpan.COLUMN]; ++j)
					span[i + row][j + column][CellSpan.ROW] = span[i + row][j + column][CellSpan.COLUMN] = 1;
	}


	//
	// ColoredCell
	//
	@Override
	public Color getForeground(final int row, final int column) {
		return isOutOfBounds(row, column) ? null : foreground[row][column];
	}
	@Override
	public void setForeground(final Color c, final int row, final int column) {
		if (!isOutOfBounds(row, column))
			foreground[row][column] = c;
	}
	@Override
	public void setForeground(final Color c, final int[] rows, final int[] columns) {
		if (!isOutOfBounds(rows, columns))
			setValues(foreground, c, rows, columns);
	}
	@Override
	public Color getBackground(final int row, final int column) {
		return isOutOfBounds(row, column) ? null : background[row][column];
	}
	@Override
	public void setBackground(final Color c, final int row, final int column) {
		if (!isOutOfBounds(row, column))
			background[row][column] = c;
	}
	@Override
	public void setBackground(final Color c, final int[] rows, final int[] columns) {
		if (!isOutOfBounds(rows, columns))
			setValues(background, c, rows, columns);
	}
	//


	//
	// CellFont
	//
	@Override
	public Font getFont(final int row, final int column) {
		return isOutOfBounds(row, column) ? null : font[row][column];
	}
	@Override
	public void setFont(final Font f, final int row, final int column) {
		if (!isOutOfBounds(row, column))
			font[row][column] = f;
	}
	@Override
	public void setFont(final Font f, final int[] rows, final int[] columns) {
		if (!isOutOfBounds(rows, columns))
			setValues(font, f, rows, columns);
	}
	//


	//
	// CellAttribute
	//
	@Override
	public void addColumn() {
		final int[][][] oldSpan = span;
		final int numRows    = oldSpan.length;
		final int numColumns = oldSpan[0].length;
		span = new int[numRows][numColumns + 1][2];
		System.arraycopy(oldSpan,0,span,0,numRows);
		for (int ¢=0;¢<numRows;++¢)
			span[¢][numColumns][CellSpan.ROW] = span[¢][numColumns][CellSpan.COLUMN] = 1;
	}

	@Override
	public void addRow() {
		final int[][][] oldSpan = span;
		final int numRows    = oldSpan.length;
		final int numColumns = oldSpan[0].length;
		span = new int[numRows + 1][numColumns][2];
		System.arraycopy(oldSpan,0,span,0,numRows);
		for (int ¢=0;¢<numColumns;++¢)
			span[numRows][¢][CellSpan.ROW] = span[numRows][¢][CellSpan.COLUMN] = 1;
	}

	@Override
	public void insertRow(final int row) {
		final int[][][] oldSpan = span;
		final int numRows    = oldSpan.length;
		final int numColumns = oldSpan[0].length;
		span = new int[numRows + 1][numColumns][2];
		if (row > 0)
			System.arraycopy(oldSpan, 0, span, 0, row - 1);
		System.arraycopy(oldSpan,0,span,row,numRows - row);
		for (int ¢=0;¢<numColumns;++¢)
			span[row][¢][CellSpan.ROW] = span[row][¢][CellSpan.COLUMN] = 1;
	}

	@Override
	public Dimension getSize() {
		return new Dimension(rowSize, columnSize);
	}

	@Override
	public void setSize(final Dimension size) {
		columnSize = size.width;
		rowSize    = size.height;
		span = new int[rowSize][columnSize][2];   // 2: COLUMN,ROW
		foreground = new Color[rowSize][columnSize];
		background = new Color[rowSize][columnSize];
		font = new Font[rowSize][columnSize];
		
		initValue();
	}

	/*
public void changeAttribute(int row, int column, Object command) {
}

public void changeAttribute(int[] rows, int[] columns, Object command) {
}
	 */




	protected boolean isOutOfBounds(final int row, final int column) {
		return row < 0 || rowSize <= row || column < 0 || columnSize <= column;
	}

	protected boolean isOutOfBounds(final int[] rows, final int[] columns) {
		for (final int row2 : rows)
			if (row2 < 0 || rowSize <= row2)
				return true;
		for (final int column2 : columns)
			if (column2 < 0 || columnSize <= column2)
				return true;
		return false;
	}

	@SuppressWarnings("static-method")
	protected void setValues(final Object[][] target, final Object value,
			final int[] rows, final int[] columns) {
		for (int i=0;i<rows.length;++i)
			for (int j = 0; j < columns.length; ++j)
				target[rows[i]][columns[j]] = value;
	}
}
