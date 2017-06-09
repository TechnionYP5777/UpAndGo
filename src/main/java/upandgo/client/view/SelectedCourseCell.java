package upandgo.client.view;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import upandgo.client.Resources;
import upandgo.client.Resources.CourseListCellStyle;

public class SelectedCourseCell extends AbstractCell<String> {
	private Boolean drawButton = Boolean.FALSE;
	
	
	public SelectedCourseCell() {
		super();
		CourseListCellStyle cStyle = Resources.INSTANCE.courseListCellStyle();
		cStyle.ensureInjected();
	}
	@Override
	public void render(@SuppressWarnings("unused") Context context, String value, SafeHtmlBuilder sb) {
	    if (value == null) {
	          return;
	    }
	    String parts[] = value.split(" - ");
	    sb.appendHtmlConstant("<div class=\"course\"><div class=\"course-name\">" + parts[0] + "</div> -" + parts[1]);
		if (drawButton == Boolean.TRUE) {
					sb.appendHtmlConstant(
					"<button type=\"button\" class=\"btn btn-danger cell-button\" >"
							+ "<i class=\"fa fa-times\" aria-hidden=\"true\"></i>" + "&nbsp;&nbsp;הסר" + "</button>"
							+ "</div>");
		} else
			sb.appendHtmlConstant("</div>");
		
	}
	public void dontDrawButton(){
		drawButton = Boolean.FALSE;
	}
	
	public void drawButton(){
		drawButton = Boolean.TRUE;
	
    }


}