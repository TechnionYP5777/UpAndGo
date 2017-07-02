package upandgo.client.common;

import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author Nikita Dizhur
 * @since 06-04-16
 * 
 * Abstract class that houses any type-specific code inside some render-unit (e.g. cell, row, column, tile, etc...).
 * These ColumnDefinition(s) would be created outside of the presenter (for instance in AppController),
 * so that we can reuse its logic regardless of what view we’ve attached ourself to.
 * 
 * Each column definition is expected to render itself into the StringBuilder.
 */

public abstract class ColumnDefinition<T> {

	public abstract Widget render(T t, StringBuilder b);

	@SuppressWarnings("static-method")
	public boolean isClickable() {
		return false;
	}
	
	@SuppressWarnings("static-method")
	public boolean isSelectable() {
	      return false;
	}
}
