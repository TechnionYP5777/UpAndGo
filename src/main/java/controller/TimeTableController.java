package controller;

import java.awt.event.ActionEvent;

import model.TimeTableModel;
import property.TimeTableProperty;
import view.TimeTableView;

public class TimeTableController implements Controller{

	protected TimeTableModel model;
	protected TimeTableView view;
	
	public TimeTableController(TimeTableModel model, TimeTableView view) {
		this.model = model;
		this.view = view;
		
		this.model.addPropertyChangeListener(TimeTableProperty.SCHEDULE, this.view);
		
		this.view.addActionListener(this);
	}
	@Override
	public void actionPerformed(@SuppressWarnings("unused") ActionEvent __) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

}
