package flickrchallenge.tinder.com.flickrproject.Fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import flickrchallenge.tinder.com.flickrproject.Activity.MainActivity;
import flickrchallenge.tinder.com.flickrproject.Adapters.FlickrImageAdapter;
import flickrchallenge.tinder.com.flickrproject.Constants.FlickrAPIUrl;
import flickrchallenge.tinder.com.flickrproject.Helper.RequestEntity;
import flickrchallenge.tinder.com.flickrproject.Model.FlickrImage;
import flickrchallenge.tinder.com.flickrproject.R;

import java.util.LinkedList;

/**
 * Created by apollow on 12/14/16.
 */
public class ImageDetailViewFragment extends Fragment {
   SubsamplingScaleImageView imageView;
   FlickrImage image;
   Context mContext;

   public static ImageDetailViewFragment newInstance(Context ctx, FlickrImage image) {
      ImageDetailViewFragment myFragment = (ImageDetailViewFragment)
              Fragment.instantiate(ctx, ImageDetailViewFragment.class.getName());

      Bundle args = new Bundle();
      args.putParcelable("image", image);
      myFragment.setArguments(args);
      return myFragment;
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      View v = inflater.inflate(R.layout.fragment_recent_photos, container, false);
      mContext = inflater.getContext();
      imageView = (SubsamplingScaleImageView)v.findViewById(R.id.image_view);

      return v;
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
                       Animator anim = ObjectAnimator.ofFloat(imageView, "alpha", 0.3f, 1).setDuration(ANIM_DURATION);
//                       anim.addListener(animListener);
//                       anim.start();
                    }
                 }

                 @Override
                 public void onErrorResponse(VolleyError error) {
                    imageView.setImage(ImageSource.resource(R.drawable.flickr_placeholder));
                    Animator anim = ObjectAnimator.ofFloat(imageView, "alpha", 0.3f, 1).setDuration(ANIM_DURATION);
//                    anim.addListener(animListener);
//                    anim.start();
                 }
              });
   }
}
