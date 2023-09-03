package persistence;

import org.json.JSONObject;

public interface Savable {

    //EFFECTS: returns this as a Savable object
    JSONObject convertToJson();
}
