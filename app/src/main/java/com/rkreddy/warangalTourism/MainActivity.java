
package com.rkreddy.warangalTourism;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity
    implements DestinationListFragment.onDestinationSelected {

  private AdView mAdView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    MobileAds.initialize(this, "ca-app-pub-8172825757124969~1622588304");
    mAdView = (AdView)findViewById(R.id.adView1);
    AdRequest adRequest = new AdRequest.Builder().build();
    mAdView.loadAd(adRequest);

    mAdView.setAdListener(new AdListener() {
      @Override
      public void onAdLoaded() {

      }

      @Override
      public void onAdFailedToLoad(int errorCode) {

      }

      @Override
      public void onAdOpened() {

      }

      @Override
      public void onAdLeftApplication() {

      }

      @Override
      public void onAdClosed() {

      }
    });

    if (savedInstanceState == null) {
      getSupportFragmentManager()
          .beginTransaction()
          .add(R.id.root_layout, DestinationListFragment.newInstance(), "destinationList")
          .commit();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.exit:
        MainActivity.this.finish();
        return true;
      case R.id.email:
        launchEmail();
      case R.id.about:
        Toast.makeText(this,R.string.about, Toast.LENGTH_LONG).show();
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  public void launchEmail() {
    Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
    emailIntent.setType("message/rfc822");
    emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {"9rkreddy@gmail.com"});
    try {
      emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Hi Vagabond, my feedback on the app-");
      startActivity(emailIntent);
    } catch (ActivityNotFoundException e) {
      Toast.makeText(getApplicationContext(), "You have no email client installed! Email me at 9rkreddy@gmail.com", Toast.LENGTH_LONG).show();
    }
  }

  @Override
  public void onDestinationSelected(int imageResId, String name, String description, String url) {
    final DestinationDetailsFragment detailsFragment =
        DestinationDetailsFragment.newInstance(imageResId, name, description, url);
    getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.root_layout, detailsFragment, "destinationDetails")
        .addToBackStack(null)
        .commit();
  }

}
