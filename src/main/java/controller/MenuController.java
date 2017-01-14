package controller;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

import model.MenuModel;
import view.MenuView;

public class MenuController implements Controller {

	CourseListController clCtrl;
	TimeTableController ttCtrl;
	MenuModel model;
	MenuView view;
	
	public MenuController(MenuModel m, MenuView v,
			CourseListController clc, TimeTableController ttc) {
		this.model = m;
		this.view = v;
		this.clCtrl = clc;
		this.ttCtrl = ttc;
		
		this.view.addActionListener(this);
		
		init();
	}

	@Override
	public void actionPerformed(@SuppressWarnings("unused") ActionEvent __) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerListenerToProperty(PropertyChangeListener l, String p) {
		model.addPropertyChangeListener(p, l);
		
	}

	@Override
	public void unregisterListenerToProperty(PropertyChangeListener l, String p) {
		model.removePropertyChangeListener(p, l);
		
	}

}
