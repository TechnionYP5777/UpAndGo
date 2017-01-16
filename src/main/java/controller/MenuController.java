package controller;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

import command.CourseCommand;
import command.MenuCommand;
import command.TimeTableCommand;
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
		else if (¢.getActionCommand().equals(MenuCommand.SAVE)) {
			clCtrl.actionPerformed(
						new ActionEvent(this, ActionEvent.ACTION_PERFORMED, CourseCommand.SAVE_CHOSEN_COURSES));
			clCtrl.saveChosenLessonGroups();	//TODO
//			clCtrl.saveCatalog();	//TODO
//			clCtrl.saveGilaion();	//TODO
			ttCtrl.actionPerformed(
					new ActionEvent(this, ActionEvent.ACTION_PERFORMED, TimeTableCommand.SAVE_SCHED));
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
