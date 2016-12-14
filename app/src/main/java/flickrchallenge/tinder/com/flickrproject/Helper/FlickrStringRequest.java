package flickrchallenge.tinder.com.flickrproject.Helper;
/**
 * Created by apollow on 12/14/16.
 */

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class FlickrStringRequest extends StringRequest {
   private static final int SLOW_TIMEOUT = 30;
   private static final int NORMAL_TIMEOUT = 30;
   public FlickrStringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
      super(method, url, listener, errorListener);
   }
}

