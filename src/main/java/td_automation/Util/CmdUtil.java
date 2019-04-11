package td_automation.Util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CmdUtil {

    static Logger LOGGER = LogManager.getLogger(FileUtil.class.getName());
    public static final String TD_QUERY = "td query";
    public static final String OUTPUT_FILE = String.format("-o %s", Constant.RESOURCE_PATH + "csv/Result.csv");
    public static final String QUERY_TYPE = "-T presto";
    public static final String FILE_FORMAT = "-f csv";



    public static String execute(String command, String parameter){
        String cmdOutput = "";
        try {
            ProcessBuilder builder = new ProcessBuilder(command, parameter);
            builder.redirectErrorStream(true);
            Process p = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;

            while (true) {
                line = r.readLine();

                if (line == null) break;
                cmdOutput += line + "\n";
            }
        }catch (IOException e){
            LOGGER.error(e.toString());
        }
        LOGGER.info(cmdOutput);
        return cmdOutput;
    }

    public static String tdExport(String dbName, String query){

        String parameter = String.format("-d %s -w %s %s %s %s", dbName, QUERY_TYPE, FILE_FORMAT, OUTPUT_FILE, query);
        return execute(TD_QUERY, parameter);
    }

    public static void main(String [] args){
        CmdUtil.execute("td", "help:all");
    }
}
