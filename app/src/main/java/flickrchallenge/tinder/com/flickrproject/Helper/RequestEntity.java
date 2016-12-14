package flickrchallenge.tinder.com.flickrproject.Helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import flickrchallenge.tinder.com.flickrproject.R;

/**
 * Created by apollow on 5/5/15.
 */
public class RequestEntity {
   private static RequestEntity mInstance;
   private RequestQueue mRequestQueue;
   private ImageLoader mImageLoader;
   private static Context mCtx;
   private RequestEntity(Context context) {

      mCtx = context;
      mRequestQueue = getRequestQueue();

      mImageLoader = new ImageLoader(mRequestQueue,
              new ImageLoader.ImageCache() {
                 private final LruBitmapCache
                         cache = new LruBitmapCache(mCtx);

                 @Override
                 public Bitmap getBitmap(String url) {
                    return cache.get(url);
                 }

                 @Override
                 public void putBitmap(String url, Bitmap bitmap) {
                    cache.put(url, bitmap);
                 }
              });
   }

   public static synchronized RequestEntity getInstance(Context context) {
      if (mInstance == null) {
         mInstance = new RequestEntity(context);

      }
      return mInstance;
   }

   public RequestQueue getRequestQueue() {
      if (mRequestQueue == null) {
         // getApplicationContext() is key, it keeps you from leaking the
         // Activity or BroadcastReceiver if someone passes one in.
         mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
      }
      return mRequestQueue;
   }

   public <T> void addToRequestQueue(Request<T> req) {
      getRequestQueue().add(req);
   }

   public ImageLoader getImageLoader() {
      return mImageLoader;
   }
//
//   public void getImageNoCache(final ProgressBar bigBar, String requestUrl, final ImageView imageView) {
//      int maxImageSize = mCtx.getResources().getDimensionPixelSize(R.id.image_max_size);
//
//      Request<Bitmap> imageRequest = new ImageRequest(requestUrl, new Response.Listener<Bitmap>() {
//         @Override
//         public void onResponse(Bitmap response) {
//            bigBar.setVisibility(View.GONE);
//            imageView.setImageBitmap(response);
//            imageView.invalidate();
//         }
//      },
//              maxImageSize, maxImageSize,
//              ImageView.ScaleType.CENTER, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
//         @Override
//         public void onErrorResponse(VolleyError error) {
//            imageView.setImageResource(R.drawable.default_avatar);
//            bigBar.setVisibility(View.GONE);
//         }
//      });
//      imageRequest.setShouldCache(false);
//      bigBar.setVisibility(View.VISIBLE);
//      RequestEntity.getInstance(mCtx).getRequestQueue().add(imageRequest);
//   }
//
//   public void getImageNoCache(String requestUrl, final ImageView imageView) {
//      int maxImageSize = mCtx.getResources().getDimensionPixelSize(R.id.image_max_size);
//
//      Request<Bitmap> imageRequest = new ImageRequest(requestUrl, new Response.Listener<Bitmap>() {
//         @Override
//         public void onResponse(Bitmap response) {
//            imageView.setImageBitmap(response);
//            imageView.invalidate();
//         }
//      },
//              maxImageSize, maxImageSize,
//              ImageView.ScaleType.CENTER, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
//         @Override
//         public void onErrorResponse(VolleyError error) {
//            imageView.setImageResource(R.drawable.default_avatar);
//         }
//      });
//      imageRequest.setShouldCache(false);
//      RequestEntity.getInstance(mCtx).getRequestQueue().add(imageRequest);
//   }

//   public void getImage(String requestUrl, final ImageView imageView) {
//      getInstance(mCtx).getImageLoader().get(requestUrl, ImageLoader.getImageListener(imageView, R.drawable.default_avatar, R.drawable.default_avatar
//      ));
//   }
   public static ImageLoader.ImageListener getImageListenerDrawable(final ImageView view,
                                                                    final Drawable drawable, final int errorImageResId) {
      return new ImageLoader.ImageListener() {
         @Override
         public void onErrorResponse(VolleyError error) {
            if (errorImageResId != 0) {
               view.setImageResource(errorImageResId);
            }
         }
         @Override
         public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
            if (response.getBitmap() != null) {
               view.setImageBitmap(response.getBitmap());
            } else if (drawable != null) {
               view.setImageDrawable(drawable);
            }
         }
      };
   }
}
