package controller;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

import command.MenuCommand;
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
	public void actionPerformed(ActionEvent ¢) {
		if (¢.getActionCommand().equals(MenuCommand.LOAD_CATALOG))
			clCtrl.loadCatalogFrom(view.getCatalogPath());
		else if (¢.getActionCommand().equals(MenuCommand.LOAD_GILAYON))
			clCtrl.loadGilaionFrom(view.getGilayonPath());
		else if (¢.getActionCommand().equals(MenuCommand.SAVE_ALL)) {
			clCtrl.saveChosenCourses();
			clCtrl.saveCatalog();
			clCtrl.saveGilaion();
			ttCtrl.saveSchedule();
		}
	}

	@Override
	public void init() {
		// nothing to do here
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
