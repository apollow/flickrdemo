package flickrchallenge.tinder.com.flickrproject.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import flickrchallenge.tinder.com.flickrproject.Constants.FlickrAPIUrl;
import flickrchallenge.tinder.com.flickrproject.Helper.RequestEntity;
import flickrchallenge.tinder.com.flickrproject.Model.DetailedFlickrImage;
import flickrchallenge.tinder.com.flickrproject.Model.FlickrImage;
import flickrchallenge.tinder.com.flickrproject.R;

import static flickrchallenge.tinder.com.flickrproject.R.id.fab;

/**
 * Created by apollow on 12/14/16.
 */
public class ImageDetailViewFragment extends Fragment {
   private SubsamplingScaleImageView imageView;
   private FlickrImage image;
   private TextView detailedImageTextView;
   private Context mContext;
   private int[] orientations = { SubsamplingScaleImageView.ORIENTATION_0,
           SubsamplingScaleImageView.ORIENTATION_90,
           SubsamplingScaleImageView.ORIENTATION_180,
           SubsamplingScaleImageView.ORIENTATION_270
   };
   private int orientationNdx = 0;

   public static ImageDetailViewFragment newInstance(Context ctx, FlickrImage image) {
      ImageDetailViewFragment myFragment = (ImageDetailViewFragment)
              Fragment.instantiate(ctx, ImageDetailViewFragment.class.getName());

      Bundle args = new Bundle();
      args.putParcelable("image", image);
      myFragment.setArguments(args);
      return myFragment;
   }

   private Response.ErrorListener errorListener = new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
         Toast.makeText(mContext, "Couldn't retrieve Flickr data", Toast.LENGTH_SHORT).show();
      }
   };

   private Response.Listener<String> successListener = new Response.Listener<String>() {
      @Override
      public void onResponse(String response) {
         bindDetailsOntoUI(DetailedFlickrImage.fromJSON(response));
      }
   };

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      View v = inflater.inflate(R.layout.fragment_detail_image, container, false);
      mContext = inflater.getContext();
      imageView = (SubsamplingScaleImageView)v.findViewById(R.id.image_view);
      detailedImageTextView = (TextView)v.findViewById(R.id.field_detail_image);

      detailedImageTextView.setMovementMethod(new ScrollingMovementMethod());

      FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
      fab.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            orientationNdx = (orientationNdx + 1) % (orientations.length);
            imageView.setOrientation(orientations[orientationNdx]);
         }
      });

      return v;
   }

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      image = getArguments().getParcelable("image");
   }

   @Override
   public void onResume() {
      super.onResume();

      RequestEntity.getInstance(mContext).getImageLoader().
              get(FlickrAPIUrl.FLICKR_IMAGE_URL(image), new ImageLoader.ImageListener() {
                 @Override
                 public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    imageView.setImage(ImageSource.bitmap(response.getBitmap()));
                    if (!isImmediate) {
//                       Animator anim = ObjectAnimator.ofFloat(imageView, "alpha", 0.3f, 1).setDuration(ANIM_DURATION);
//                       anim.addListener(animListener);
//                       anim.start();
                    }
                 }

                 @Override
                 public void onErrorResponse(VolleyError error) {
                    imageView.setImage(ImageSource.resource(R.drawable.flickr_placeholder));
//                    Animator anim = ObjectAnimator.ofFloat(imageView, "alpha", 0.3f, 1).setDuration(ANIM_DURATION);
//                    anim.addListener(animListener);
//                    anim.start();
                 }
              });
      getImageDetails();
   }

   private void getImageDetails() {
      String request = FlickrAPIUrl.FLICKR_IMAGE_DETAILS(image);
      StringRequest imagesRequest = new StringRequest(Request.Method.GET, request,
              successListener, errorListener);
      imagesRequest.setShouldCache(false);
      RequestEntity.getInstance(mContext).addToRequestQueue(imagesRequest);
   }

   private void bindDetailsOntoUI(DetailedFlickrImage detailedFlickrImage) {
      String detailText = "Title: " + detailedFlickrImage.getTitle() + "\n" +
              "Owner: " + detailedFlickrImage.getOwner() + "\n" +
              "Taken On: " + detailedFlickrImage.getTaken() + "\n" +
              "Views: " + detailedFlickrImage.getViews() + "\n" +
              "Description: " + detailedFlickrImage.getDescription();

      detailedImageTextView.setText(detailText);
   }
}
