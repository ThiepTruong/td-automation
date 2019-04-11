package td_automation.Util;
import java.io.File;
import org.apache.logging.log4j.*;

public class Util {

	static Logger log = LogManager.getLogger(Util.class.getName());

	public Util(){
		log.info("Hello World !");
		log.debug("Debug message !");
	}
	
	public static boolean compareCsv(String sourceFilePath, String desFilePath) throws Exception{
		File srcFile, desFile;
		try {
			srcFile = new File(sourceFilePath);
			desFile = new File(desFilePath);
		} catch (Exception e){
			log.info(e.toString());
		}


		boolean result = true;
		return result;
	}

	public static void main (String [] args){
		new Util();
	}
}
