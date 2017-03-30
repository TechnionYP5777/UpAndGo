package logger_example;

 /**
  * 
  * @author danabra
  * @since 30-03-17
  * 
  * Usage example of Log
  * 
  */

 import logger.Log;


 
public class example {

	public static void main(String[] args) {
	    Log.debug("debug_msg");
	    Log.error("error_msg");
	    Log.info("info_msg");
	    Log.trace("trace_msg");
	    Log.warn("warning_msg");

	}

}
