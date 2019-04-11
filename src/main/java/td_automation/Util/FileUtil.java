package td_automation.Util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;

public class FileUtil {

    static Logger LOGGER = LogManager.getLogger(FileUtil.class.getName());

    public static ArrayList<String> readLine(String filePath){
        ArrayList<String> lines = new ArrayList<String>();
        try {
            LOGGER.info("Read " + filePath);
            File file = new File(filePath);
            BufferedReader br = new BufferedReader(new FileReader(file));

            String st;
            while ((st = br.readLine()) != null){
                lines.add(st);
            }
        }catch (FileNotFoundException e){
            LOGGER.error(e.toString());
        }catch (IOException e){
            LOGGER.error(e.toString());
        }
        return lines;
    }
}
