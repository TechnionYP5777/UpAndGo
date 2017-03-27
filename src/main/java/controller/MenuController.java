package controller;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

import command.CourseCommand;
import command.MenuCommand;
import command.TimeTableCommand;
import model.MenuModel;
import view.MenuView;

/**
 * 
 * @author Nikita Dizhur
 * @since 9-30-2016
 * 
 * Corresponds to {@link MenuView} and {@link MenuModel} that represent the menu bar and all actions that one can perform from there.
 * 
 */
public class MenuController implements Controller {

	CourseListController clCtrl;
	TimeTableController ttCtrl;
	MenuModel model;
	MenuView view;

	public MenuController(final MenuModel m, final MenuView v, final CourseListController clc,
			final TimeTableController ttc) {
		model = m;
		view = v;
		clCtrl = clc;
		ttCtrl = ttc;

		view.addActionListener(this);

		init();
	}

	@Override
	public void actionPerformed(final ActionEvent ¢) {
		if (¢.getActionCommand().equals(MenuCommand.LOAD_CATALOG))
			clCtrl.loadCatalogFrom(view.getCatalogPath());
		else if (¢.getActionCommand().equals(MenuCommand.LOAD_GILAYON))
			clCtrl.loadGilaionFrom(view.getGilayonPath());
		else if (¢.getActionCommand().equals(MenuCommand.SAVE)) {
			clCtrl.actionPerformed(
					new ActionEvent(this, ActionEvent.ACTION_PERFORMED, CourseCommand.SAVE_CHOSEN_COURSES));
			ttCtrl.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, TimeTableCommand.SAVE_SCHED));
		}
	}

	@Override
	public void init() {
		// nothing to do here
	}

	@Override
	public void registerListenerToProperty(final PropertyChangeListener l, final String p) {
		model.addPropertyChangeListener(p, l);

	}

	@Override
	public void unregisterListenerToProperty(final PropertyChangeListener l, final String p) {
		model.removePropertyChangeListener(p, l);

	}

}
