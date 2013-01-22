package us.bestapp.bikeplus.tools;

import android.app.Service;
import android.content.Intent;
import android.location.LocationManager;
import android.os.IBinder;

public class MainService extends Service {

	public static String START_MAIN_SERVICE_ACTION = "us.bestapp.bikeplus.MAINSERVICE";
	private LocationManager mLocationManager;
	private GPS gpsUtil;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		System.out.println("servcer is start..");

		gpsUtil = new GPS();
		mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				0, 0, gpsUtil);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.out.println("servcer is stop..");
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
