package upandgo.client;
import com.allen_sauer.gwt.log.client.Log;
/**
 * 
 * @author danabra
 * @since 02-05-17
 * 
 * THis code is first to execute when the application is loaded
 * 
 */
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.user.client.ui.RootLayoutPanel;

import upandgo.server.model.loader.XmlCourseLoader;
import com.firebase.client.Firebase;

import com.firebase.client.Firebase;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.core.client.Callback;


import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Transaction;
import com.firebase.client.Transaction.Handler;
import com.firebase.client.Transaction.Result;



public class UpAndGo implements EntryPoint {

	
	@Override
	public void onModuleLoad() {
		
		Log.warn("aaaaaaaaaaaaaaaaaaaaaaaa");
		final Injector injector = Injector.INSTANCE;

		AppController appViewer = new AppController((CoursesServiceAsync)(GWT.create(CoursesService.class)), injector.getEventBus());

		appViewer.go(RootLayoutPanel.get());
			
		
		
		 ScriptInjector.fromUrl("http://cdn.firebase.com/v0/firebase.js")
		    .setCallback(new Callback<Void,Exception>(){
		      @Override
		      public void onFailure(Exception reason) {
		      }
		      @Override
		      public void onSuccess(Void result) {
		        onScriptLoaded();
		      }
		    })
		    .inject();

				
	}
	
	
	public void onScriptLoaded() {
		Log.warn("aaaaaaaaaaaaaaaaaaaaaaaa2");
	    /*Button button = new Button("Increment!");
	    RootPanel.get().add(button);*/
	    Firebase firebase = new Firebase("https://upandgofire.firebaseio.com");
	    
	    firebase.child("value").runTransaction(new Handler() {
	        @Override
	        public Result doTransaction(MutableData currentData) {
	        	Integer value = currentData.getValue(Integer.class);
	          currentData.setValue(value == null ? 1 : value + 1);
	          return Transaction.success(currentData);
	        }

	        @Override
	        public void onComplete(FirebaseError error, boolean committed, DataSnapshot currentData) {
	          //callback.onIncrement(currentData.getValue(Long.class));
	        	Integer val = currentData.getValue(Integer.class);
	        	Log.warn(val +"");
	        }
	      });
	    
//	    firebase.child("value").runTransaction(new Handler() {
//	        @Override
//	        public Result doTransaction(MutableData currentData) {
//	          Long value = currentData.getValue(Long.class);
//	          currentData.setValue(value == null ? 1 : value + 1);
//	          return Transaction.success(currentData);
//	        }
//
//	        @Override
//	        public void onComplete(FirebaseError error, boolean committed, DataSnapshot currentData) {
//	          callback.onIncrement(currentData.getValue(Long.class));
//	        }
//	      });
	    
	    
	    
	    /*final NumberIncrementer incrementer = new NumberIncrementer(firebase);
	    button.addClickHandler(new ClickHandler() {
	      @Override
	      public void onClick(ClickEvent event) {
	        incrementer.increment(new NumberIncrementer.Callback() {
	          @Override
	          public void onIncrement(long numIncrements) {
	            Window.alert(numIncrements + " increments!");
	          }
	        });
	      }
	    });*/
	  }
	
	
	

}
