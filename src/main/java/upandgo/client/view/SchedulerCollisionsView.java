package upandgo.client.view;

import java.util.ArrayList;
import java.util.List;

import org.gwtbootstrap3.client.ui.ModalComponent;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;

import upandgo.client.Resources;
import upandgo.client.Resources.SchedualerConstraintsStyle;
import upandgo.shared.model.scedule.CourseTuple;


/**
 * @author Yaniv Levinsky
 *
 */

public class SchedulerCollisionsView extends VerticalPanel implements ModalComponent{
	
	
	Label freeDaysLabel = new Label("קיימות התנגשויות שלא מאפשרות להשלים את בניית המערכת. אנא וותר על אחד מהקורסים הבאים על מנת לפתור אותן:");
	List<CourseTuple> solvers;
	List<RadioButton> radios;

	
	private SchedualerConstraintsStyle cStyle = Resources.INSTANCE.schedualerConstraintsStyle();

	@SuppressWarnings("unused")
	public SchedulerCollisionsView(){
		
	    Log.info("SchedCol/1");
		radios = new ArrayList<RadioButton>();
    	InitializePanel();
    	cStyle.ensureInjected();
    	Log.info("SchedCol/2");
    	
    }
	
	public List<RadioButton> getRadios(){
		return radios;
	}
	
	public List<CourseTuple> getSolversTuples(){
		return solvers;
	}
	
	@SuppressWarnings("boxing")
	public void updateList(@SuppressWarnings("hiding") List<CourseTuple> solvers){
		Log.info("SchedCol/3");
		this.solvers = solvers;
		
		for(RadioButton r : radios)
			this.remove(r);
		radios.clear();
		
		for(CourseTuple s : solvers){
    		RadioButton r = new RadioButton("radioGroup", s.getCourseId() + " - " + s.getCourseName());
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

    	this.add(freeDaysLabel);
    	this.setStyleName(cStyle.constraintsPanel());

    }
	

}
