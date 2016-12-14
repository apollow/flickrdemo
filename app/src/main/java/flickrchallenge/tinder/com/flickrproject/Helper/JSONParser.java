package flickrchallenge.tinder.com.flickrproject.Helper;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by apollow on 4/7/15.
 */
public class JSONParser {
   public static final Gson GSON = new Gson();

   public static Map getElementHash(String jsonStr) {
      return (Map) GSON.fromJson(jsonStr, Object.class);
   }

   public static Map getElementHash(JsonElement jsonEle) {
      return (Map) GSON.fromJson(jsonEle, Object.class);
   }

   public static List<JsonElement> getElementList(String jsonStr, String... path) {
      JsonObject jsonObject = GSON.fromJson(jsonStr, JsonObject.class);

      return getElementList(jsonObject, path);
   }

   public static List<JsonElement> getElementList(JsonElement jsonEle, String... path) {
      return getElementList(jsonEle.getAsJsonObject(), path);
   }

   public static List<JsonElement> getElementList(JsonObject jsonObj, String... path) {
      List<JsonElement> values = new ArrayList<JsonElement>();

      if (getElement(jsonObj, path) != null && !(getElement(jsonObj, path).isJsonNull())) {
         for (JsonElement element : getElement(jsonObj, path).getAsJsonArray()) {
            values.add(element);
         }
      }
      return values;
   }

   public static JsonElement getElement(String jsonStr, String... path) {
      JsonObject jsonObject = GSON.fromJson(jsonStr, JsonObject.class);

      return getElement(jsonObject, path);
   }

   public static JsonElement getElement(JsonElement jsonEle, String... path) {
      return getElement(jsonEle.getAsJsonObject(), path);
   }

   public static JsonElement getElement(JsonObject jsonObj, String... path) {
      JsonElement value = jsonObj;

      for (String key : path) {
         value = jsonObj.get(key);
         if (value.isJsonObject()) {
            jsonObj = value.getAsJsonObject();
         }
      }

      return value;
   }

   public static String stringParse(JsonElement ele, String str) {
      try {
         return JSONParser.getElement(ele, str).getAsString();
      }
      catch (Exception e) {
         return "";
      }
   }
}
