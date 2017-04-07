package presenter.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.LayoutPanel;

public class TimeTableGUI extends LayoutPanel { 
	FlexTable t = new FlexTable();
	
	public TimeTableGUI(){
    	InitializePanel();
    }
    private void InitializePanel(){
    	t.setText(0, 0, "שעה");
    	t.setText(0, 1, "ראשון");
    	t.setText(0, 2, "שני");
    	t.setText(0, 3, "שלישי");
    	t.setText(0, 4, "רביעי");
    	t.setText(0, 5, "חמישי");
    	//t.setText(0, 5, "שישי");
    	t.getRowFormatter().addStyleName(0, "watchListHeader");
    	t.getCellFormatter().addStyleName(0, 0, "hourCol");
    	for(int i = 1; i<6; i++){
    		t.getCellFormatter().addStyleName(0, i, "watchListNumericColumn");
    	}
    	
    	for(int i = 1; i<12; i++){
    		t.setText(2*i-1, 0, Integer.toString(i+7)+":30");
    		t.setText(2*i, 0, Integer.toString(i+8)+":00");
    		t.getRowFormatter().addStyleName(2*i-1, "watchListHeader");
    		t.getRowFormatter().addStyleName(2*i, "watchListHeader");
    		//t.getFlexCellFormatter().setRowSpan(i, 0, 2);
    	}
    	
    	
    	
    	
    	
    	/*t.setText(0, 0, "upper-left corner");
	    t.setText(0, 1, "center corner");
	    t.setText(0, 2, "right corner");
	    //t.setText(0, 1, "upper-center");
	    t.setText(1, 1, "center-left");
	    t.setText(2, 2-1, "bottom-right corner");*/

	    // Let's put a button in the middle...
	    //t.setWidget(1, 0, new Button("Wide Button"));

	    // ...and set it's column span so that it takes up the whole row.
	    //t.getFlexCellFormatter().setRowSpan(1, 0, 2);
	    /*t.getRowFormatter().addStyleName(0, "watchListHeader");
	    t.getRowFormatter().addStyleName(1, "watchListHeader");
	    t.getRowFormatter().addStyleName(2, "watchListHeader");*/
	    /*t.getCellFormatter().addStyleName(0, 0, "watchListNumericColumn");
	    t.getCellFormatter().addStyleName(0, 1, "watchListNumericColumn");
	    t.getCellFormatter().addStyleName(0, 2, "watchListNumericColumn");*/
	    t.addStyleName("watchList");
	    
	    this.add(t);
	    /*this.setWidgetTopBottom(cc, 0, Unit.EM, 0, Unit.EM);
	    this.setWidgetTopBottom(ccl, 1.5,  Unit.EM, 0, Unit.EM);
	    this.setWidgetTopBottom(sc, 30,  Unit.EM, 0, Unit.EM);
	    this.setWidgetTopBottom(scl, 31.5,  Unit.EM, 0, Unit.EM);*/
    }
}
