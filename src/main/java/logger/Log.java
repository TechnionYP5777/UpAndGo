package logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author danabra
 * @since 30-03-17
 * 
 * Top-level static-like class for logging events
 * 
 */
public final class Log { // final because it supposed to be a static class so there's no sense in inheriting this class
	private static Logger logger = LoggerFactory.getLogger("logger.Log");
	
	private Log(){ // we don't want more than one instance

	}
	
	public static void debug(String msg){
		logger.debug(msg);
	}
	
	public static void error(String msg){
		logger.error(msg);
	}
	
	public static void info(String msg){
		logger.info(msg);
	}
	
	public static void trace(String msg){
		logger.trace(msg);
	}
	
	public static void warn(String msg){
		logger.warn(msg);
	}
}
