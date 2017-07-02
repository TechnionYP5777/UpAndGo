package upandgo.client.view;

/**
 * 
 * @author danabra
 * @since 8-06-17
 * 
 * this class represents a single cell in the selected courses' table 
 * 
 */


import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import upandgo.client.Resources;

public class SelectedCourseCell extends AbstractCell<String> {
	
	public SelectedCourseCell() {
		Resources.INSTANCE.courseListCellStyle().ensureInjected();
	}
	@Override
	public void render(@SuppressWarnings("unused") Context c, String value, SafeHtmlBuilder sb) {
	    if (value == null)
			return;
	    int idx = value.lastIndexOf("-");
	    @SuppressWarnings("unused")
		String parts[] = {value.substring(0, idx), value.substring(idx+1)}; 
	    sb.appendHtmlConstant("<div style=\"width:17.4vw;\"><div class=\"course\">" + value
				+ "</div><button type=\"button\" class=\"btn btn-danger cell-button\" >"
				+ "<i class=\"fa fa-times\" aria-hidden=\"true\"></i>&nbsp;&nbsp;הסר</button></div>");
		
	}


}