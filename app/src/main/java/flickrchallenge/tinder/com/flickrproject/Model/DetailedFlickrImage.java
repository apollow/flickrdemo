package flickrchallenge.tinder.com.flickrproject.Model;

import com.google.gson.JsonElement;
import flickrchallenge.tinder.com.flickrproject.Helper.JSONParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apollow on 12/14/16.
 */
public class DetailedFlickrImage {
   public String getOwner() {
      return owner;
   }

   public String getTitle() {
      return title;
   }

   public String getTaken() {
      return taken;
   }

   public String getDescription() {
      return description;
   }

   public String getViews() {
      return views;
   }

   private String owner, title, taken, description, views;
   public DetailedFlickrImage(String owner, String title, String taken, String description, String views) {
      this.owner = owner;
      this.title = title;
      this.taken = taken;
      this.description = description;
      this.views = views;
   }

   public static DetailedFlickrImage fromJSON(String response) {
      JsonElement photo = JSONParser.getElement(response, "photo");
      JsonElement owner = JSONParser.getElement(photo, "owner");
      JsonElement title = JSONParser.getElement(photo, "title");
      JsonElement description = JSONParser.getElement(photo, "title");
      JsonElement dates = JSONParser.getElement(photo, "dates");

      return new DetailedFlickrImage(
           JSONParser.stringParse(owner, "username"),
           JSONParser.stringParse(title, "_content"),
           JSONParser.stringParse(dates, "taken"),
           JSONParser.stringParse(description, "_content"),
           JSONParser.stringParse(photo, "views")
      );
   }
}
