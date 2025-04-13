package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.DataProvider;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DataProviderUtils {

    private static List<Map<String, Object>> loadJsonData(String fileName) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = DataProviderUtils.class.getClassLoader().getResourceAsStream("testdata/" + fileName);
            if (inputStream == null) {
                throw new RuntimeException("Test data file not found: " + fileName);
            }
            return mapper.readValue(inputStream, new TypeReference<List<Map<String, Object>>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Failed to load or parse test data from " + fileName, e);
        }
    }

    private static Object[][] wrapListInDataProvider(List<Map<String, Object>> dataList) {
        Object[][] data = new Object[dataList.size()][1];
        Iterator<Map<String, Object>> iterator = dataList.iterator();
        for (int i = 0; i < dataList.size(); i++) {
            data[i][0] = iterator.next();
        }
        return data;
    }

    @DataProvider(name = "getLoginData")
    public static Object[][] getLoginData() {
        return wrapListInDataProvider(loadJsonData("login_data.json"));
    }

    @DataProvider(name = "getAlertData")
    public static Object[][] getAlertData() {
        return wrapListInDataProvider(loadJsonData("alert_data.json"));
    }

    @DataProvider(name = "getTodoData")
    public static Object[][] getTodoData() {
        return wrapListInDataProvider(loadJsonData("todo_data.json"));
    }

    // ✅ Type-safe helper for list of strings from JSON
    @SuppressWarnings("unchecked")
    public static List<String> getStringList(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value instanceof List<?>) {
            List<?> rawList = (List<?>) value;
            for (Object item : rawList) {
                if (!(item instanceof String)) {
                    throw new IllegalArgumentException("List for key '" + key + "' contains non-string values");
                }
            }
            return (List<String>) value;
        } else {
            throw new IllegalArgumentException("Expected List<String> for key: " + key);
        }
    }
}
