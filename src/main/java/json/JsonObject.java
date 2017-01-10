package json;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Andrii_Rodionov on 1/3/2017.
 */
public class JsonObject extends Json {

    private ArrayList<JsonPair> jsonPairs;


    public JsonObject(JsonPair... jsonPairs) {
        this.jsonPairs = new ArrayList<JsonPair>();
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
        Iterator<JsonPair> jsonPairsIterator = jsonPairs.iterator();
        while (jsonPairsIterator.hasNext()) {
            JsonPair jsonPair = jsonPairsIterator.next();
            jsonStr.append(jsonPair.key + ": " + jsonPair.value.toJson());
            if (jsonPairsIterator.hasNext()) {
                jsonStr.append(", ");
            }
        }
        return jsonStr.toString();
    }

    public void add(JsonPair jsonPair) {
        int possibleTwinIndex = this.findIndex(jsonPair.key);
        if (possibleTwinIndex == -1) {
            this.jsonPairs.add(jsonPair);
        } else {
            this.jsonPairs.set(possibleTwinIndex, jsonPair); //overwiting existing val
        }
    }

    public int findIndex(String name) {
        int iter = 0;
        for (JsonPair jsonPair : this.jsonPairs) {
            if (jsonPair.key.equals(name)) {
                return iter;
            }
            ++iter;
        }
        return -1;
    }


    public Json find(String name) {
        for (JsonPair jsonPair : this.jsonPairs) {
            if (jsonPair.key.equals(name)) {
                return jsonPair.value;
            }
        }
        return null;
    }

    public boolean contains(String name) {
        return (this.find(name) != null);
    }

    public JsonObject projection(String... names) {
        ArrayList<JsonPair> res = new ArrayList<>();
        int indx;
        for (String name : names) {
            indx = this.findIndex(name);
            if (indx != -1) {
                res.add(this.jsonPairs.get(indx));
            }
        }

        return new JsonObject(res.toArray(new JsonPair[]{}));


    }
}