package flickrchallenge.tinder.com.flickrproject.Model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.JsonElement;
import flickrchallenge.tinder.com.flickrproject.Helper.JSONParser;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apollow on 12/14/16.
 */
public class FlickrImage implements Parcelable {
   private String id, farmId, serverId, secret, title;

   public FlickrImage(String id, String farm, String serverId, String secret, String title) {
      this.id = id;
      this.farmId = farm;
      this.serverId = serverId;
      this.secret = secret;
      this.title = title;
   }

   public String getFarmId() {
      return farmId;
   }

   public String getServerId() {
      return serverId;
   }

   public String getSecret() {
      return secret;
   }

   public String getId() {
      return id;
   }

   public static List<FlickrImage> fromJSONAsList(String response) {
      ArrayList<FlickrImage> images = new ArrayList<>();
      JsonElement photos = JSONParser.getElement(response, "photos");

      for (JsonElement photo : JSONParser.getElementList(photos, "photo")) {
         FlickrImage image = new FlickrImage(
                 JSONParser.stringParse(photo, "id"),
                 JSONParser.stringParse(photo, "farm"),
                 JSONParser.stringParse(photo, "server"),
                 JSONParser.stringParse(photo, "secret"),
                 JSONParser.stringParse(photo, "title")
                 );
         images.add(image);
      }

      return images;
   }

   @Override
   public int describeContents() {
      return 0;
   }

   @Override
   public void writeToParcel(Parcel dest, int flags) {
      dest.writeString(this.id);
      dest.writeString(this.farmId);
      dest.writeString(this.serverId);
      dest.writeString(this.secret);
      dest.writeString(this.title);
   }

   protected FlickrImage(Parcel in) {
      this.id = in.readString();
      this.farmId = in.readString();
      this.serverId = in.readString();
      this.secret = in.readString();
      this.title = in.readString();
   }

   public static final Parcelable.Creator<FlickrImage> CREATOR = new Parcelable.Creator<FlickrImage>() {
      @Override
      public FlickrImage createFromParcel(Parcel source) {
         return new FlickrImage(source);
      }

      @Override
      public FlickrImage[] newArray(int size) {
         return new FlickrImage[size];
      }
   };
}
