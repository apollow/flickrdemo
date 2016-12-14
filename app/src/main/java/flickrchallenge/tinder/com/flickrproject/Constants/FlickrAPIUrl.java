package flickrchallenge.tinder.com.flickrproject.Constants;

import flickrchallenge.tinder.com.flickrproject.Model.FlickrImage;

/**
 * Created by apollow on 12/14/16.
 */
public class FlickrAPIUrl {
   //TODO: Put into a server for more security, unrecommended to stay in code
   private static final String debugFlickrKey = "&api_key=ea059cc4ae1714a5dd0dbfbde4699674";
   private static final String jsonAddon = "&format=json&nojsoncallback=1";
   private static final String apiURLTail = debugFlickrKey + jsonAddon;


   //https://api.flickr.com/services/rest/?&method=flickr.photos.getRecent&api_key=ea059cc4ae1714a5dd0dbfbde4699674
   private static final String DOMAIN = "https://api.flickr.com/services/rest/?";
   public static final String GET_RECENT = DOMAIN + "&method=flickr.photos.getRecent" + apiURLTail;

   public static String FLICKR_IMAGE_URL(FlickrImage image) {
      String test = "https://farm" + image.getFarmId() + ".staticflickr.com/" + image.getServerId() + "/" + image.getId()
              + "_" + image.getSecret()  + ".jpg";
      return test;
   }



}
