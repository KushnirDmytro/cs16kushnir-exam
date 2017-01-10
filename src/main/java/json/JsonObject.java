package json;

import java.security.KeyStore;
import java.util.*;

/**
 * Created by Andrii_Rodionov on 1/3/2017.
 */
public class JsonObject extends Json {

    private Map<String, Json> jsonMap;


    public JsonObject(JsonPair... jsonPairs) {
        this.jsonMap = new HashMap<String, Json>();
        for (JsonPair newPair : jsonPairs) {
            this.add(newPair);
        }
    }

    @Override
    public String toJson() {
        return "{" + getJsonObjBody() + "}";
    }

    private String getJsonObjBody() {
        StringBuilder jsonStr = new StringBuilder();
        Set<String>jsonKeys = jsonMap.keySet();
        Iterator<String> jKeyIter = jsonKeys.iterator();
        String key;
        while (jKeyIter.hasNext()) {
            key = jKeyIter.next();
            jsonStr.append(key + ": " + jsonMap.get(key).toJson());
            if (jKeyIter.hasNext()) {
                jsonStr.append(", ");
            }
        }
        return jsonStr.toString();
    }

    public void add(JsonPair jsonPair) {
        jsonMap.put(jsonPair.key, jsonPair.value);
    }

    public Json find(String name) {
        return this.jsonMap.get(name);
    }

    public boolean contains(String name) {
        return this.jsonMap.containsKey(name);
    }

    public JsonObject projection(String... names) {
        List<JsonPair> res = new ArrayList<>();
        for (String name : names) {
            if (this.contains(name)){
                res.add(new JsonPair(name, jsonMap.get(name)) );
            }
        }

        return new JsonObject(res.toArray(new JsonPair[]{}));

    }
}