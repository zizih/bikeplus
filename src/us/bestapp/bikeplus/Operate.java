package us.bestapp.bikeplus;

import us.bestapp.bikeplus.R;
import us.bestapp.bikeplus.database.Factory;
import us.bestapp.bikeplus.database.Status;
import us.bestapp.bikeplus.database.Timer;
import us.bestapp.bikeplus.database.Track;
import us.bestapp.bikeplus.database.TrackTaker;
import us.bestapp.bikeplus.tools.Chart;
import us.bestapp.bikeplus.tools.CommonUtility;
import us.bestapp.bikeplus.tools.GPS;
import android.content.Context;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Operate extends GPS {

	private Location myLocation;
	private Chart chart;

	private Context activity;
	private RefreshDurationThread refreshDurationThread;
	private Resources mRes;

	private TextView txt_current_speed;
	private TextView txt_current_durition;
	private TextView txt_start_time;
	private TextView txt_stop_time;
	private TextView txt_high_speed;
	private TextView txt_average_speed;
	private TextView txt_total_distance;
	private Button btn_start;
	private Button btn_pause;

	private Timer timer = Factory.getTimerInstance();
	private Status status = Factory.getStatusInstance();
	private Track myTrack = Factory.getTrackInstance();

	public void recordStart() {
		status.setStarted(true);
		timer.setBegin_time_long(timer.now());
		timer.start();
		myTrack.distance = 0;
		myTrack.averageSpeed = 0;
		myTrack.maxSpeed = 0;
		if (myLocation != null) {
			myTrack.startLatitude = myLocation.getLatitude();
			myTrack.startLongitude = myLocation.getLongitude();
		}
		if (refreshDurationThread == null) {
			refreshDurationThread = new RefreshDurationThread(activity);
			refreshDurationThread.start();
		}
		refreshDurationThread.isPause = false;
		btn_pause.setEnabled(true);
		btn_start.setText(mRes.getString(R.string.text_stop));
		txt_start_time.setText(CommonUtility.formatTime(timer
				.getBegin_time_long()));
		txt_average_speed.setText("");
		txt_high_speed.setText("");
		txt_current_speed.setText("");
		txt_stop_time.setText("");
		txt_total_distance.setText("");
	}

	public void recordPause() {
		status.setPause(true);
		timer.pause();
		refreshDurationThread.isPause = true;
		btn_pause.setText(mRes.getString(R.string.text_resulme));
	}

	public void recordResume() {
		status.setPause(false);
		timer.resume();
		refreshDurationThread.isPause = false;
		btn_pause.setText(mRes.getString(R.string.text_pause));
	}

	public void recordStop() {
		status.setStarted(false);
		status.setPause(false);
		timer.stop();

		refreshDurationThread.isPause = true;
		if (myLocation != null) {
			myTrack.endLatitude = myLocation.getLatitude();
			myTrack.endLongitude = myLocation.getLongitude();
		}
		// database
		myTrack.startTime = CommonUtility
				.formatTime(timer.getBegin_time_long());
		myTrack.endTime = CommonUtility.formatTime(timer.getEnd_time_long());
		myTrack.lastTime = timer.getLastTime();
		TrackTaker trackTaker = new TrackTaker(activity);
		trackTaker.Insert(myTrack);

		txt_stop_time.setText(myTrack.endTime);
		btn_pause.setEnabled(false);
		btn_pause.setText(mRes.getString(R.string.text_pause));
		btn_start.setText(mRes.getString(R.string.text_begin));
		showTextView();

	}

	public void resumeButton() {
		if (status.isStarted()) {
			btn_start.setText(mRes.getString(R.string.text_stop));
			btn_pause.setEnabled(true);
			if (refreshDurationThread == null) {
				refreshDurationThread = new RefreshDurationThread(activity);
				refreshDurationThread.start();
			}
			refreshDurationThread.isPause = false;
			if (status.isPause()) {
				btn_pause.setText(mRes.getString(R.string.text_resulme));
				refreshDurationThread.isPause = true;
				txt_current_durition.setText(CommonUtility.formatDuring(timer
						.tmpGetLastTime()));
			}
		}
	}

	public void resumeTextView() {
		txt_start_time.setText(CommonUtility.formatTime(timer
				.getBegin_time_long()));
		showTextView();
	}

	public void showTextView() {
		txt_high_speed.setText(CommonUtility.formatSpeed(myTrack.maxSpeed));
		txt_average_speed.setText(CommonUtility
				.formatSpeed(myTrack.averageSpeed) + " km/h");
		txt_total_distance.setText(CommonUtility
				.formatDistance(myTrack.distance));
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		super.onLocationChanged(location);

		if (status.isStarted() && status.isPause() == false) {
			txt_current_speed.setText(CommonUtility.formatSpeed(location
					.getSpeed()));
			txt_high_speed.setText(CommonUtility.formatSpeed(myTrack.maxSpeed));
			txt_average_speed.setText(CommonUtility
					.formatSpeed(myTrack.averageSpeed) + " km/h");
			txt_total_distance.setText(CommonUtility
					.formatDistance(myTrack.distance));
			chart.setSpeed(location.getSpeed());
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

	// true
	public void showCurrentDuritionTime() {
		txt_current_durition.setText(CommonUtility.formatDuring(timer
				.getLastTime()));
	}

	public Operate(Context activity, TextView txt_current_speed,
			TextView txt_current_durition, TextView txt_start_time,
			TextView txt_stop_time, TextView txt_high_speed,
			TextView txt_average_speed, TextView txt_total_distance,
			Chart chart, Button btn_start, Button btn_pause) {
		this.activity = activity;
		this.txt_current_speed = txt_current_speed;
		this.txt_current_durition = txt_current_durition;
		this.txt_start_time = txt_start_time;
		this.txt_stop_time = txt_stop_time;
		this.txt_high_speed = txt_high_speed;
		this.txt_average_speed = txt_average_speed;
		this.txt_total_distance = txt_total_distance;
		this.chart = chart;
		this.btn_start = btn_start;
		this.btn_pause = btn_pause;
		mRes = activity.getResources();
	}

}
