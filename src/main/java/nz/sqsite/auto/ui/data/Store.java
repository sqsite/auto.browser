package nz.sqsite.auto.ui.data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Store {
    private static final ThreadLocal<Map<String, Object>> data = ThreadLocal.withInitial(ConcurrentHashMap::new);
    private static final Map<String, Object> dataStoreMap = data.get();
    private static final String testDataKey = "FrTWXyBbXyA";


    @SuppressWarnings("unchecked")
    public static void addTestData(String key, String value) {
        Object testData = dataStoreMap.get(testDataKey);
        if (testData == null) {
            dataStoreMap.put(testDataKey, new ConcurrentHashMap<String, String>());
        }
        ((Map<String, String>) dataStoreMap.get(testDataKey)).put(key, value);
    }

    @SuppressWarnings("unchecked")
    public static String getTestData(String key) {
        return  ((Map<String, String>) dataStoreMap.get(testDataKey)).get(key);
    }

    public static void storeTypeObject(String key, Object object){
        dataStoreMap.put(key, object);
    }

    public static Object getTypeObject(String key){
        return dataStoreMap.get(key);
    }


}
