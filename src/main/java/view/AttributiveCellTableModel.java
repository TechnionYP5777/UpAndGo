package view;

import java.awt.Dimension;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class AttributiveCellTableModel extends DefaultTableModel {

	  protected CellAttribute cellAtt;
	    
	  @SuppressWarnings("rawtypes")
	public AttributiveCellTableModel() {
	    this((Vector)null, 0);
	  }
	  @SuppressWarnings("rawtypes")
	public AttributiveCellTableModel(int numRows, int numColumns) {
	    Vector names = new Vector(numColumns);
	    names.setSize(numColumns);
	    setColumnIdentifiers(names);
	    dataVector = new Vector();
	    setNumRows(numRows);
	    cellAtt = new DefaultCellAttribute(numRows,numColumns);
	  }
	  @SuppressWarnings("rawtypes")
	public AttributiveCellTableModel(Vector columnNames, int numRows) {
	    setColumnIdentifiers(columnNames);
	    dataVector = new Vector();
	    setNumRows(numRows);
	    cellAtt = new DefaultCellAttribute(numRows,columnNames.size());
	  }
	  public AttributiveCellTableModel(Object[] columnNames, int numRows) {
	    this(convertToVector(columnNames), numRows);
	  }  
	  @SuppressWarnings("rawtypes")
	public AttributiveCellTableModel(Vector data, Vector columnNames) {
	    setDataVector(data, columnNames);
	  }
	  public AttributiveCellTableModel(Object[][] data, Object[] columnNames) {
	    setDataVector(data, columnNames);
	  }

	    
	  @SuppressWarnings("rawtypes")
	@Override
	public void setDataVector(Vector newData, Vector columnNames) {
	    if (newData == null)
	      throw new IllegalArgumentException("setDataVector() - Null parameter");
	    dataVector = new Vector(0);
	    //setColumnIdentifiers(columnNames);
	    this.columnIdentifiers = columnNames;
	    dataVector = newData;
	    
	    //
	    cellAtt = new DefaultCellAttribute(dataVector.size(),
	                                       columnIdentifiers.size());
	    
	    newRowsAdded(new TableModelEvent(this, 0, getRowCount()-1,
	     TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
	  }

	  @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addColumn(Object columnName, Vector columnData) {
	    if (columnName == null)
	      throw new IllegalArgumentException("addColumn() - null parameter");
	    columnIdentifiers.addElement(columnName);
	    int index = 0;
	    for (Enumeration eeration = dataVector.elements(); eeration.hasMoreElements();) {
			Object value = columnData == null || index >= columnData.size() ? null : columnData.elementAt(index);
			((Vector) eeration.nextElement()).addElement(value);
			++index;
		}

	    //
	    cellAtt.addColumn();

	    fireTableStructureChanged();
	  }

	  @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addRow(Vector rowData) {
	    Vector newData = null;
	    if (rowData != null)
			rowData.setSize(getColumnCount());
		else
			newData = new Vector(getColumnCount());
	    dataVector.addElement(newData);

	    //
	    cellAtt.addRow();

	    newRowsAdded(new TableModelEvent(this, getRowCount()-1, getRowCount()-1,
	       TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
	  }

	  @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void insertRow(int row, Vector rowData) {
	    if (rowData != null)
			rowData.setSize(getColumnCount());
		else
			rowData = new Vector(getColumnCount());

	    dataVector.insertElementAt(rowData, row);

	    //
	    cellAtt.insertRow(row);

	    newRowsAdded(new TableModelEvent(this, row, row,
	       TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
	  }

	  public CellAttribute getCellAttribute() {
	    return cellAtt;
	  }

	  public void setCellAttribute(CellAttribute newCellAtt) {
	    int numColumns = getColumnCount();
	    int numRows    = getRowCount();
	    if ((newCellAtt.getSize().width  != numColumns) ||
	        (newCellAtt.getSize().height != numRows))
			newCellAtt.setSize(new Dimension(numRows, numColumns));
	    cellAtt = newCellAtt;
	    fireTableDataChanged();
	  }
	    
	}

