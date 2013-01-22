package us.bestapp.bikeplus.tools;

import us.bestapp.bikeplus.database.Factory;
import us.bestapp.bikeplus.database.Status;
import us.bestapp.bikeplus.database.Timer;
import us.bestapp.bikeplus.database.Track;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class GPS implements LocationListener {

	private Location myLocation;
	private Timer timer = Factory.getTimerInstance();
	private Status status = Factory.getStatusInstance();
	private Track myTrack = Factory.getTrackInstance();

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		if (status.isStarted() && !status.isPause()) {
			if (myLocation != null) {
				double periodDistance = CommonUtility.gps2m(
						myLocation.getLatitude(), myLocation.getLongitude(),
						location.getLatitude(), location.getLongitude());
				if (periodDistance > 0) {
					myTrack.distance += periodDistance;
				}
			}
			myLocation = location;
			if (location.getSpeed() > myTrack.maxSpeed) {
				myTrack.maxSpeed = location.getSpeed();
			}
			if (timer.getLastTime() != 0) {
				myTrack.averageSpeed = myTrack.distance / timer.getLastTime()
						* 1000;
			}
		}

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}
}
