package flickrchallenge.tinder.com.flickrproject.Fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import flickrchallenge.tinder.com.flickrproject.Activity.MainActivity;
import flickrchallenge.tinder.com.flickrproject.Adapters.FlickrImageAdapter;
import flickrchallenge.tinder.com.flickrproject.Constants.FlickrAPIUrl;
import flickrchallenge.tinder.com.flickrproject.Helper.RequestEntity;
import flickrchallenge.tinder.com.flickrproject.Model.FlickrImage;
import flickrchallenge.tinder.com.flickrproject.R;

import java.util.LinkedList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecentFragment extends Fragment {
   private RecyclerView imageRecyclerView;
   private ProgressBar mBar;
   private List<FlickrImage> images;
   private FlickrImageAdapter flickrImageAdapter;
   private Context mContext;

   private Response.ErrorListener errorListener = new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
         Toast.makeText(mContext, "Couldn't retrieve Flickr data", Toast.LENGTH_SHORT).show();
         mBar.setVisibility(View.GONE);
      }
   };

   private Response.Listener<String> successListener = new Response.Listener<String>() {
      @Override
      public void onResponse(String response) {
         mBar.setVisibility(View.GONE);
         images.clear();
         images.addAll(FlickrImage.fromJSONAsList(response));
         flickrImageAdapter.notifyDataSetChanged();
      }
   };

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      View v = inflater.inflate(R.layout.fragment_recent_photos, container, false);
      mContext = inflater.getContext();

      mBar = (ProgressBar) v.findViewById(R.id.progress_bar);

      images = new LinkedList<>();

      flickrImageAdapter = new FlickrImageAdapter(mContext, images);
      flickrImageAdapter.setOnItemClickListener(new FlickrImageAdapter.OnItemClickListener() {
         @Override
         public void onItemClick(View view, int position) {
            ((MainActivity) getActivity()).toDetailImage(images.get(position));
         }
      });

      imageRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
      LinearLayoutManager llm = new LinearLayoutManager(mContext);
      imageRecyclerView.setLayoutManager(llm);
      imageRecyclerView.setAdapter(flickrImageAdapter);


      return v;
   }


   @Override
   public void onResume() {
      super.onResume();
      getRecentImages();
   }

   private void getRecentImages() {
      String request = FlickrAPIUrl.GET_RECENT;
      StringRequest imagesRequest = new StringRequest(Request.Method.GET, request,
              successListener, errorListener);
//      videoRequest.setShouldCache(false);
      RequestEntity.getInstance(mContext).addToRequestQueue(imagesRequest);
   }


}
