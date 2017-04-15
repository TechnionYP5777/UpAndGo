package upandgo.server.logger_example;

 import upandgo.server.logger.Log;


 
public class example {

	public static void main(String[] args) {
	    Log.debug("debug_msg");
	    Log.error("error_msg");
	    Log.info("info_msg");
	    Log.trace("trace_msg");
	    Log.warn("warning_msg");

	}

}
