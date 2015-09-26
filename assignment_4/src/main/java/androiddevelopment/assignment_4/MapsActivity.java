package androiddevelopment.assignment_4;


import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v4.app.FragmentTransaction;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks
        , com.google.android.gms.location.LocationListener, QuestDialog.OnOptionSelected
        {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private GoogleApiClient mGoogleApiClient;
    private Treasure mTh;
    private Marker mMarker;
    public Bundle args;
    public MediaPlayer mMediaPlayer;
    public Vibrator mVibrator;
    LocationRequest mLocationRequest;
    public boolean playerAnswerd = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        args = new Bundle();
        mTh = new Treasure();
        mTh.setCurrentTreasureLocation();
        mVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        mMediaPlayer = MediaPlayer.create(this,R.raw.beep);

        setUpMapIfNeeded();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .build();
        mGoogleApiClient.connect();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }


    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        mMap.setMyLocationEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        LatLng home = new LatLng(55.600644, 13.028285);

        UiSettings mUISetting = mMap.getUiSettings();
        mUISetting.setZoomControlsEnabled(true);
        mUISetting.setMyLocationButtonEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(home));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(16));
        mMarker = mMap.addMarker(new MarkerOptions().position(mTh.getCurrentTreasureLocation()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_place_black_48dp)));

    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i("TAG", "Connected");
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mTh.setCurrentPlayerLocation(location);
        if (mTh.isTreasureFound() && playerAnswerd) {
            vibrateSoundQuestion();
            playerAnswerd = false;

        }
    }


    public void vibrateSoundQuestion() {
        args.putInt("quest",mTh.getTreasureFoundCounter());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        QuestDialog mDialog = new QuestDialog();
        mDialog.setArguments(args);
        mDialog.show(ft,"dialog");

        mMediaPlayer.start();
        mVibrator.vibrate(2000);
    }


    @Override
    public void onComplete(int option) {
        playerAnswerd = true;
        Toast toast;
        //If answer is wrong --> game over
        if (option != mTh.RIGHT_ANSWERS[mTh.getTreasureFoundCounter()]) {
            toast = Toast.makeText(getApplicationContext(), "Wrong answer, game over!!!", Toast.LENGTH_LONG);
            mMap.clear();
            playerAnswerd = false;
        }
        //If answer is right --> continue
        else {
            toast = Toast.makeText(getApplicationContext(), "Right answer!!!", Toast.LENGTH_LONG);
            //If it is last treasure_point ---> player win
            if (mTh.getTreasureFoundCounter() == mTh.N_TREASURES) {
                toast = Toast.makeText(getApplicationContext(), "CONGRATULATIONS, YOU FOUND ALL TREASURES!!!", Toast.LENGTH_LONG);
                mMap.clear();
                playerAnswerd = false;
            }
            //If it is right answer but not last treasure_point ---> spawn new treasure_point
            else {
                mMarker.remove();
                mTh.incrementTreasureFoundCounter();
                mTh.setCurrentTreasureLocation();
                mMarker = mMap.addMarker(new MarkerOptions().position(mTh.getCurrentTreasureLocation()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_place_black_48dp)));
            }
        }

        toast.show();
    }
}