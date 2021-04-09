package td_automation.tdautomation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.junit.Test;
import td_automation.Util.Constant;
import td_automation.Util.CsvUtil;
import td_automation.Util.JsonUtil;

import java.io.File;
import java.io.FileReader;

import static org.junit.Assert.assertTrue;

public class JsonTest {

    private String parent = Constant.RESOURCE_PATH + "JSON/Parent.json";
    private String child = Constant.RESOURCE_PATH + "JSON/Child.json";
    public static Logger LOGGER = LogManager.getLogger(JsonTest.class.getName());

    @Test
    public void jsonTest() {
        LOGGER.info("------------- Start running jsonTest -------------");
        JSONArray parentJson = (JSONArray) JsonUtil.getJson(parent);
        JSONArray childJson = (JSONArray) JsonUtil.getJson(child);
        boolean result = JsonUtil.jsonArrayContains(parentJson, childJson);
        assertTrue(result);
    }
}
