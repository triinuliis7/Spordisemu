package spordisemu.spordisemu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Triinu Liis on 11/10/2015.
 */
public class Map extends android.support.v4.app.FragmentActivity{

    private GoogleMap mMap;
    private boolean chooseMarker = false;
    private Address address;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map);

        Intent intent = getIntent();



        setUpMapIfNeeded();

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

            SupportMapFragment mMapFragment = (SupportMapFragment) (getSupportFragmentManager()
                    .findFragmentById(R.id.map));

            mMap = mMapFragment.getMap();

            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int height = size.y;
            int width = size.x;

            ViewGroup.LayoutParams params = mMapFragment.getView().getLayoutParams();
            params.height = height - 130;
            mMapFragment.getView().setLayoutParams(params);
            mMap.setMyLocationEnabled(true);
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {

        Location myLocation = mMap.getMyLocation();

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(58.653895, 25.532339), 6));

        if (myLocation != null) {
            mMap.clear();
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()), 14.0f));
            mMap.addMarker(new MarkerOptions().position(new LatLng(myLocation.getLatitude(), myLocation.getLongitude())).title(getAddress(myLocation).getAddressLine(0)));
            address = getAddress(myLocation);
        }

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                chooseMarker = true;
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLng).title(getAddress(latLng).getAddressLine(0)));
                address = getAddress(latLng);
            }
        });

        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

            @Override
            public void onMyLocationChange(Location arg0) {
                // TODO Auto-generated method stub
                if (!chooseMarker) {
                    mMap.clear();
                    mMap.addMarker(new MarkerOptions().position(new LatLng(arg0.getLatitude(), arg0.getLongitude())).title(getAddress(arg0).getAddressLine(0)));
                    address = getAddress(arg0);
                }
            }

            public void onMyLocationButtonClick(Location arg0) {
                chooseMarker = false;
                mMap.clear();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(arg0.getLatitude(), arg0.getLongitude()), 14.0f));
                mMap.addMarker(new MarkerOptions().position(new LatLng(arg0.getLatitude(), arg0.getLongitude())).title(getAddress(arg0).getAddressLine(0)));
                address = getAddress(arg0);
            }
        });

        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {

            @Override
            public boolean onMyLocationButtonClick() {
                Location arg0 = mMap.getMyLocation();
                chooseMarker = false;
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(new LatLng(arg0.getLatitude(), arg0.getLongitude())).title(getAddress(arg0).getAddressLine(0)));
                address = getAddress(arg0);
                return false;
            }

        });
    }

    public void setLocation (View view) {
        if (address.getAddressLine(0).toString() != null) {
            finish();
            CreateProfileLocation.setText(address.getAddressLine(0).toString());
            CreateProfileLocation.location.setText(address.getAddressLine(0).toString());
        }

    }

    public Address getAddress (Location latLng) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.getLatitude(),latLng.getLongitude(), 1);
            Address address = addresses.get(0);
            return address;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Address(null);
    }

    public Address getAddress (LatLng latLng) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude,latLng.longitude, 1);
            Address address = addresses.get(0);
            return address;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Address(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
