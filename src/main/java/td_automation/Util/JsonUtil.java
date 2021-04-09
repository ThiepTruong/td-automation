package td_automation.Util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.Set;


public class JsonUtil {
    public static Logger LOGGER = LogManager.getLogger(JsonUtil.class.getName());

    public static boolean jsonObjectContains(JSONObject parent, JSONObject child) {
        Set<String> keySet = child.keySet();
        boolean found = true;
        for (String key : keySet) {

            if (parent.containsKey(key)) {
                Object fromChild = child.get(key);
                Object fromParent = parent.get(key);

                if (fromChild instanceof JSONObject && fromParent instanceof JSONObject) {
                    found = jsonObjectContains((JSONObject) fromParent, (JSONObject) fromChild);
                } else {

                    if (fromChild instanceof JSONArray && fromParent instanceof JSONArray) {
                        found = jsonArrayContains((JSONArray) fromParent, (JSONArray) fromChild);
                    } else {

                        if (!fromParent.equals(fromChild)) {
                            LOGGER.info(String.format("From parent: %s while from child: %s", fromParent.toString(), fromChild.toString()));
                            found = false;
                            break;
                        }
                    }
                }
            } else {
                LOGGER.info(String.format("Parent doesn't contain key: %s", key));
                found = false;
                break;
            }
        }
        //LOGGER.info("FOUND !");
        return found;
    }

    public static boolean jsonArrayContains(JSONArray parent, JSONArray child) {
        int parentLen = parent.size();
        int childLen = child.size();
        if (parentLen < childLen) {
            LOGGER.info(String.format("Parent len: %d while child len: %d", parentLen, childLen));
            return false;
        } else {
            boolean found = true;
            for (int i = 0; i < childLen; i++) {
                Object fromChild = child.get(i);

                for (int j = 0; j < parentLen; j++) {
                    Object fromParent = parent.get(j);

                    if (fromChild instanceof JSONObject && fromParent instanceof JSONObject) {
                        found = jsonObjectContains((JSONObject) fromParent, (JSONObject) fromChild);
                    } else {

                        if (fromChild instanceof JSONArray && fromParent instanceof JSONArray) {
                            found = jsonArrayContains((JSONArray) fromParent, (JSONArray) fromChild);
                        }
                    }
                }

                if (!found) {
                    LOGGER.info(String.format("%s NOT found", fromChild.toString()));
                    return false;
                }
            }
        }
        //LOGGER.info("FOUND !");
        return true;
    }

    public static Object getJson(String fileName) {
        FileReader fileReader;
        JSONParser parser = new JSONParser();
        try {
            fileReader = new FileReader(fileName);
            return parser.parse(fileReader);
        } catch (Exception e){
            LOGGER.info(e.toString());
        }
        return null;
    }

}
