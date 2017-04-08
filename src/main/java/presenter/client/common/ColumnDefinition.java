package presenter.client.common;

import com.google.gwt.user.client.ui.Widget;

public abstract class ColumnDefinition<T> {

	public abstract Widget render(T t, StringBuilder sb);

	@SuppressWarnings("static-method")
	public boolean isClickable() {
		return false;
	}
}
