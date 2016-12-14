package flickrchallenge.tinder.com.flickrproject.Adapters;

/**
 * Created by apollow on 12/14/16.
 */

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import flickrchallenge.tinder.com.flickrproject.Constants.FlickrAPIUrl;
import flickrchallenge.tinder.com.flickrproject.Helper.RequestEntity;
import flickrchallenge.tinder.com.flickrproject.Model.FlickrImage;
import flickrchallenge.tinder.com.flickrproject.R;

import java.util.*;

/**
 * Created by apollow on 5/26/15.
 */
public class FlickrImageAdapter extends RecyclerView.Adapter<FlickrImageAdapter.ImageViewHolder> {
   private final Context mContext;
   private final List<FlickrImage> mList;
   private OnItemClickListener mItemClickListener;
   private static final int ANIM_DURATION = 500;

   public FlickrImageAdapter(Context context, List<FlickrImage> list) {
      this.mList = list;
      this.mContext = context;
   }

   @Override
   public int getItemCount() {
      return mList.size();
   }

   public interface OnItemClickListener {
      void onItemClick(View view, int position);
   }

   protected class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
      public TextView name;
      public ImageView icon;

      public ImageViewHolder(View v) {
         super(v);
         name = (TextView) v.findViewById(R.id.field_image_title);
         icon = (ImageView) v.findViewById(R.id.image_icon);
         v.setOnClickListener(this);
      }

      @Override
      public void onClick(View v) {
         mItemClickListener.onItemClick(v, getLayoutPosition()); //OnItemClickListener mItemClickListener;
      }
   }


   public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
      this.mItemClickListener = mItemClickListener;
   }

   @Override
   public void onBindViewHolder(final ImageViewHolder holder, int position) {
      final FlickrImage item = mList.get(position);

      holder.name.setText(item.getTitle());
      final ImageView imageView = holder.icon;

      final Animator.AnimatorListener animListener = new Animator.AnimatorListener() {
         @Override
         public void onAnimationStart(Animator animator) {
         }

         @Override
         public void onAnimationEnd(Animator animator) {
         }

         @Override
         public void onAnimationCancel(Animator animator) {
         }

         @Override
         public void onAnimationRepeat(Animator animator) {
         }
      };

      RequestEntity.getInstance(mContext).getImageLoader().
              get(FlickrAPIUrl.FLICKR_IMAGE_URL(item), new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                       imageView.setImageBitmap(response.getBitmap());
                       if (!isImmediate) {
                          Animator anim = ObjectAnimator.ofFloat(imageView, "alpha", 0.3f, 1).setDuration(ANIM_DURATION);
                          anim.addListener(animListener);
                          anim.start();
                       }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                       imageView.setImageResource(R.drawable.flickr_placeholder);
                       Animator anim = ObjectAnimator.ofFloat(imageView, "alpha", 0.3f, 1).setDuration(ANIM_DURATION);
                       anim.addListener(animListener);
                       anim.start();
                    }
                 });
   }

   @Override
   public void onViewRecycled(ImageViewHolder holder) {
//      holder.imageLayout.setVisibility(View.GONE);
      super.onViewRecycled(holder);
   }

   @Override
   public ImageViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
      View itemView = LayoutInflater.
              from(viewGroup.getContext()).
              inflate(R.layout.view_flickr_image, viewGroup, false);

      return new ImageViewHolder(itemView);
   }
}


