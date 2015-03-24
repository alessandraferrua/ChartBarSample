package it.alessandraferrua.chartbarSample;

import android.content.Context;
import android.graphics.Color;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.Iterator;

import it.alessandraferrua.chartbarSample.utils.Utils;


public class MainActivity extends ActionBarActivity implements LocationListener, GpsStatus.Listener {
    private final String TAG = this.getClass().getCanonicalName();
    private LocationManager locationManager;
    private static final String PROVIDER = LocationManager.GPS_PROVIDER;
    private PowerSnrView powerView;
    private GpsSatellite satellite;
    private TextView coords;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate");
        coords = (TextView) findViewById(R.id.coords);

        if(powerView==null)
            powerView = (PowerSnrView) findViewById(R.id.powerSnr);
    }


    protected void onPause() {
        locationManager.removeUpdates(this);
        super.onPause();
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return super.onRetainCustomNonConfigurationInstance();
    }

    protected void onResume() {
        registerLocationListeners();
        super.onResume();
    }


    private void registerLocationListeners() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(PROVIDER, 1000, 1, this);
        locationManager.addGpsStatusListener(this);
    }

    @Override
    public void onGpsStatusChanged(int event) {
        switch (event) {
            case GpsStatus.GPS_EVENT_STARTED:
                break;
            case GpsStatus.GPS_EVENT_SATELLITE_STATUS:

                GpsStatus gpsStatus = locationManager.getGpsStatus(null);
                Iterable<GpsSatellite> mISatellites = gpsStatus.getSatellites();
                Iterator<GpsSatellite> mItGPS = mISatellites.iterator();

                int sat = 0;
                int fix = 0;

                while (mItGPS.hasNext()) {
                    satellite = (GpsSatellite) mItGPS.next();

                    int snr = (int) satellite.getSnr();

                    powerView.onRectSizeChanged(snr * 20, sat, Utils.getPrn(satellite.getPrn()));

                    if (satellite.usedInFix()) {
                        fix++;
                        powerView.onChangeColor(getResources().getColor(R.color.lime_A400), sat);

                    } else {
                        powerView.onChangeColor(Color.DKGRAY, sat);
                    }
                    sat++;
                    if (sat > 11)
                        return;

                }


                break;
            case GpsStatus.GPS_EVENT_FIRST_FIX:
                break;
            case GpsStatus.GPS_EVENT_STOPPED:
                break;
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        String result = Utils.getFormatCoord(location.getLatitude()) + " - " + Utils.getFormatCoord(location.getLongitude()
        );
        coords.setText(result);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
