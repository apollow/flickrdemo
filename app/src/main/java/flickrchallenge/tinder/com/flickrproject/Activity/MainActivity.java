package flickrchallenge.tinder.com.flickrproject.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import flickrchallenge.tinder.com.flickrproject.Fragment.ImageDetailViewFragment;
import flickrchallenge.tinder.com.flickrproject.Fragment.RecentFragment;
import flickrchallenge.tinder.com.flickrproject.Model.FlickrImage;
import flickrchallenge.tinder.com.flickrproject.R;

import static android.R.attr.handle;
import static flickrchallenge.tinder.com.flickrproject.Fragment.ImageDetailViewFragment.newInstance;

public class MainActivity extends AppCompatActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);

      startWithRecentBrowse();
      getFragmentManager().addOnBackStackChangedListener(
           new FragmentManager.OnBackStackChangedListener() {
              public void onBackStackChanged() {
                 handleBackStackChanged();
              }
           });
   }



   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.menu_main, menu);
      // Get the SearchView and set the searchable configuration
//      SearchManager searchManager =
//              (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//      SearchView searchView =
//              (SearchView) menu.findItem(R.id.search).getActionView();
//      searchView.setSearchableInfo(
//              searchManager.getSearchableInfo(getComponentName()));
      return true;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      // Handle action bar item clicks here. The action bar will
      // automatically handle clicks on the Home/Up button, so long
      // as you specify a parent activity in AndroidManifest.xml.
      int id = item.getItemId();

      //noinspection SimplifiableIfStatement
      if (id == R.id.home) {
         onBackPressed();
      }

      return super.onOptionsItemSelected(item);
   }

   public void startWithRecentBrowse() {
      RecentFragment recentFragment = (RecentFragment)RecentFragment.instantiate(this,
              RecentFragment.class.getName());
      replaceOnTopFragment(recentFragment);
   }

   public void toDetailImage(FlickrImage image) {
      ImageDetailViewFragment fragment = ImageDetailViewFragment.newInstance(this, image);
      replaceOnTopFragment(fragment);
   }

   public void replaceOnTopFragment(Fragment fragment) {
      FragmentManager manager = getFragmentManager();
      boolean fragmentPopped = manager.popBackStackImmediate(fragment.toString(), 0);

      if (!fragmentPopped) { //fragment not in back stack, create it.
         FragmentTransaction ft = manager.beginTransaction();
         ft.replace(R.id.fragment_container, fragment);
         ft.addToBackStack(fragment.toString());
         ft.commit();
      }
   }

   public Fragment getCurrentFragment() {
      return getFragmentManager().findFragmentById(R.id.fragment_container);
   }

   public void handleBackStackChanged() {
      Fragment fr = getCurrentFragment();
      if (getSupportActionBar() == null) {
         return;
      }
      if (fr instanceof RecentFragment) {
         getSupportActionBar().setDisplayHomeAsUpEnabled(false);
      } else if (fr instanceof ImageDetailViewFragment) {
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      }
   }
}
