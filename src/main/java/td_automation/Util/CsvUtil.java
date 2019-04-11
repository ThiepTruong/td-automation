package td_automation.Util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class CsvUtil {

    public static Logger LOGGER = LogManager.getLogger(Util.class.getName());
    public static String delimiter = ",";

    public static boolean compareCsv(ArrayList<HashMap<String, String>> srcData,ArrayList<HashMap<String, String>> desData){

        boolean result = true;
        int match = 0;
        if(compareQuantity(srcData, desData)){

            int numOfColumn = srcData.get(0).size();
            HashMap<String, String> diffMap = new HashMap<String, String>();

            for (int i = 0; i < srcData.size(); i ++){
                HashMap<String, String> srcRow = srcData.get(i);
                int finalDiff = numOfColumn;
                int foundIndex = 0;

                for (int j = 0; j < desData.size(); j++){
                    HashMap<String, String> desRow = desData.get(j);
                    int currentOfDiff = 0;

                    for (String key: srcRow.keySet()){

                        if(!desRow.get(key).equals(srcRow.get(key))){
                            currentOfDiff ++;
                        }
                    }

                    if (currentOfDiff < finalDiff){
                        finalDiff = currentOfDiff;
                        diffMap = desRow;
                    }

                    if (currentOfDiff == 0) foundIndex = j;
                }

                if (finalDiff == 0){

                    //Found 1 match so remove that item out of desData so the next search won't touch it
                    desData.remove(foundIndex);
                    match ++;
                } else {
                    result = false;
                    LOGGER.info("No match !");
                    LOGGER.info(String.format("The current record %s", srcRow.toString()));
                    LOGGER.info(String.format("Most likely record %s", diffMap.toString()));
                }
            }
            LOGGER.info(String.format("%d match(es) found !", match));
            LOGGER.info(String.format("Total record %d !", srcData.size()));
        } else {
            result = false;
        }
        LOGGER.info(String.format("Compare result %s !", String.valueOf(result)));
        return result;
    }

    public static boolean compareCsv(String srcCsv,String desCsv){
        ArrayList<HashMap<String, String>> srcData = fileToMaps(srcCsv);
        ArrayList<HashMap<String, String>> desData = fileToMaps(desCsv);
        return compareCsv(srcData, desData);
    }

    public static boolean compareCsv(String srcCsv,String desCsv, String myDelimiter){
        ArrayList<HashMap<String, String>> srcData = fileToMaps(srcCsv, myDelimiter);
        ArrayList<HashMap<String, String>> desData = fileToMaps(desCsv, myDelimiter);
        return compareCsv(srcData, desData);
    }

    public static ArrayList<HashMap<String, String>> fileToMaps(String filePath){
        ArrayList<HashMap<String, String>> csvData = new ArrayList<HashMap<String, String>>();
        ArrayList<String> fileData = FileUtil.readLine(filePath);
        String [] keys = fileData.get(0).split(delimiter);

        for (int i = 1; i < fileData.size(); i ++){
            String [] values = fileData.get(i).split(delimiter);
            HashMap<String, String> tmpMap = new HashMap<String, String>();

            for (int j = 0; j < keys.length; j ++)
                try{
                    tmpMap.put(keys[j], values[j]);
                } catch (Exception e){
                    tmpMap.put(keys[j], "");
                }
                csvData.add(tmpMap);
        }
        return csvData;
    }

    public static ArrayList<HashMap<String, String>> fileToMaps(String filePath, String myDelimiter){
        String oldDelimiter = delimiter;
        delimiter = myDelimiter;
        ArrayList<HashMap<String, String>> csvData = fileToMaps(filePath);
        delimiter = oldDelimiter;
        return csvData;
    }

    public static boolean compareQuantity(ArrayList<HashMap<String, String>> srcData, ArrayList<HashMap<String, String>> desData){
        int srcNumOfColumn = srcData.get(0).size();
        int desNumOfColumn = desData.get(0).size();
        int srcNumOfRow = srcData.size();
        int desNumOfRow = desData.size();
        boolean result = true;

        if (srcNumOfColumn != desNumOfColumn){
            LOGGER.info(String.format("Source #column = %d while Destination #column = %d", srcNumOfColumn, desNumOfColumn));
            result = false;
        } else {
            Set <String> srcKeys = srcData.get(0).keySet();
            Set <String> desKeys = desData.get(0).keySet();

            for(String srcKey: srcKeys){

                boolean found = false;
                for(String desKey: desKeys){

                    if(srcKey.equals(desKey)){
                        //desKeys.remove(desKey);
                        found = true;
                        break;
                    }
                }

                if (found == false){
                    LOGGER.error(String.format("Cannot find key %s in keySet %s", srcKey, desKeys.toString()));
                    result = false;
                    break;
                }
            }
        }

        if (srcNumOfRow != desNumOfRow){
            LOGGER.info(String.format("Source #row = %d while Destination #row = %d", srcNumOfRow, desNumOfRow));
            result = false;
        }
        return result;
    }

    public static boolean compareQuantity(String srcCsv, String desCsv){
        ArrayList<HashMap<String, String>> srcData = fileToMaps(srcCsv);
        ArrayList<HashMap<String, String>> desData = fileToMaps(desCsv);
        return compareQuantity(srcData, desData);
    }

    public static boolean compareQuantity(String srcCsv, String desCsv, String myDelimiter){
        ArrayList<HashMap<String, String>> srcData = fileToMaps(srcCsv, myDelimiter);
        ArrayList<HashMap<String, String>> desData = fileToMaps(desCsv, myDelimiter);
        return compareQuantity(srcData, desData);
    }

}
