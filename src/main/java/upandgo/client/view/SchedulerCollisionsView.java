package upandgo.client.view;

import java.util.ArrayList;
import java.util.List;

import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.InlineCheckBox;
import org.gwtbootstrap3.client.ui.InlineRadio;
import org.gwtbootstrap3.client.ui.ModalComponent;
import org.gwtbootstrap3.client.ui.constants.HeadingSize;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;

import upandgo.client.resources.Resources;
import upandgo.client.resources.Resources.SchedualerConstraintsStyle;
import upandgo.shared.model.scedule.CourseTuple;


/**
 * @author Yaniv Levinsky
 *
 */

public class SchedulerCollisionsView extends VerticalPanel implements ModalComponent{
	
	//InlineCheckBox daysOffCB = new InlineCheckBox("מספר מקסימלי של ימים חופשיים");

	//HorizontalPanel freeDaysPanel = new HorizontalPanel();
	Label freeDaysLabel = new Label("קיימות התנגשויות שלא מאפשרות להשלים את בניית המערכת. אנא וותר על אחד מהקורסים הבאים על מנת לפתור אותן:");
	List<CourseTuple> solvers;
	List<InlineRadio> radios;

	
	private SchedualerConstraintsStyle cStyle = Resources.INSTANCE.schedualerConstraintsStyle();

	public SchedulerCollisionsView(){
		
	    //InitializeTimeLBs();
		Log.info("SchedCol/1");
		radios = new ArrayList<InlineRadio>();
    	InitializePanel();
    	cStyle.ensureInjected();
    	Log.info("SchedCol/2");
    	
    }
	
	public List<InlineRadio> getRadios(){
		return radios;
	}
	
	public List<CourseTuple> getSolversTuples(){
		return solvers;
	}
	
	public void updateList(List<CourseTuple> solvers){
		Log.info("SchedCol/3");
		this.solvers = solvers;
		
		for(InlineRadio r : radios){
			this.remove(r);
		}
		radios.clear();
		
		for(CourseTuple s : solvers){
			InlineRadio r;
			if (s.getCourseId().equals("999999")){
				r = new InlineRadio("radioGroup", "אירועים אישיים");
			} else {
	    		r = new InlineRadio("radioGroup", s.getCourseId() + " - " + s.getCourseName());
			}
			r.addStyleName(Resources.INSTANCE.schedualerConstraintsStyle().onlyCheckBox());
    		radios.add(r);
    		this.add(r);

    	}
		
		// set first by default
		if(radios.get(0) != null)
			radios.get(0).setValue(true);
		 
		Log.info("SchedCol/4");
	}

	    
    private void InitializePanel(){
    	this.setHorizontalAlignment(ALIGN_RIGHT);
    	
    	this.add(new Heading(HeadingSize.H5,"קיימות התנגשויות שלא מאפשרות להשלים את בניית המערכת."));
    	this.add(new Heading(HeadingSize.H5,"אנא וותר על אחד מהקורסים הבאים על מנת לפתור אותן:"));
    	

    	this.setStyleName(cStyle.constraintsPanel());

    }
	

}
