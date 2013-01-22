package us.bestapp.bikeplus;

import java.text.DecimalFormat;

import us.bestapp.bikeplus.database.Factory;
import us.bestapp.bikeplus.database.Status;
import us.bestapp.bikeplus.tools.Chart;
import us.bestapp.bikeplus.tools.Gyroscope;
import us.bestapp.bikeplus.tools.MainService;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private TextView txt_current_speed;
	private TextView txt_current_durition;
	private TextView txt_start_time;
	private TextView txt_stop_time;
	private TextView txt_high_speed;
	private TextView txt_average_speed;
	private TextView txt_total_distance;
	private TextView txt_bearing;
	private TextView txt_chart_max_speed;

	private ImageView img_gyroscope_bg;
	private ImageView img_gyroscope_arow;

	private Button btn_start;
	private Button btn_pause;
	private Button btn_share;
	private Button btn_setting;

	private Chart chart;
	private Intent serviceIntent;
	private Status status = Factory.getStatusInstance();

	private LocationManager mLocationManager;
	private SensorManager mSensorManager;
	private Operate operateUtil;
	private Gyroscope gyroscopeUtil;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		serviceIntent = new Intent();
		serviceIntent.setAction(MainService.START_MAIN_SERVICE_ACTION);

		txt_current_speed = (TextView) findViewById(R.id.txt_current_speed);
		txt_current_durition = (TextView) findViewById(R.id.txt_current_durition);
		txt_start_time = (TextView) findViewById(R.id.txt_start_time);
		txt_stop_time = (TextView) findViewById(R.id.txt_stop_time);
		txt_high_speed = (TextView) findViewById(R.id.txt_high_speed);
		txt_average_speed = (TextView) findViewById(R.id.txt_average_speed);
		txt_total_distance = (TextView) findViewById(R.id.txt_total_distance);
		txt_bearing = (TextView) findViewById(R.id.txt_bearing);
		txt_chart_max_speed = (TextView) findViewById(R.id.txt_max_speed);

		chart = (Chart) findViewById(R.id.chart);
		img_gyroscope_bg = (ImageView) findViewById(R.id.img_gyroscope_bg);
		img_gyroscope_arow = (ImageView) findViewById(R.id.img_gyroscope_arow);

		btn_start = (Button) findViewById(R.id.start);
		btn_pause = (Button) findViewById(R.id.pause);
		btn_share = (Button) findViewById(R.id.btn_share);
		btn_setting = (Button) findViewById(R.id.btn_setting);
		btn_share.setOnClickListener(new MyOnClickListener());
		btn_setting.setOnClickListener(new MyOnClickListener());

		gyroscopeUtil = new Gyroscope(MainActivity.this, img_gyroscope_bg,
				img_gyroscope_arow, txt_bearing);
		operateUtil = new Operate(MainActivity.this, txt_current_speed,
				txt_current_durition, txt_start_time, txt_stop_time,
				txt_high_speed, txt_average_speed, txt_total_distance, chart,
				btn_start, btn_pause);

		// data about background
		if (status.isRunInBackground()) {
			operateUtil.resumeButton();
			operateUtil.resumeTextView();
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		try {
			mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
			mSensorManager.registerListener(gyroscopeUtil,
					mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
					SensorManager.SENSOR_DELAY_GAME);
			mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
			mLocationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, 0, 0, operateUtil);
		} catch (Exception e) {

		}
	}

	// about start and pause buttons
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.start:
			if (status.isStarted() == false) {
				operateUtil.recordStart();
			} else {
				operateUtil.recordStop();
			}
			break;
		case R.id.pause:
			if (status.isPause() == false) {
				operateUtil.recordPause();
			} else {
				operateUtil.recordResume();
			}

			break;
		}
	}

	private class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btn_share:
				Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setTitle("后台运行");
				builder.setIcon(MainActivity.this.getResources().getDrawable(
						R.drawable.ic_menu_set_as));
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								if (status.isStarted()) {
									// 开启service
									status.setRunInBackground(true);
									startService(serviceIntent);
									MainActivity.this.finish();
								} else {
									Toast.makeText(MainActivity.this,
											"还没有开始，不进入后台运行", Toast.LENGTH_SHORT)
											.show();
								}
							}

						}).setNegativeButton("取消", null).show();
				break;
			case R.id.btn_setting:
				setting();
				break;

			}
		}

		private void setting() {
			String[] items = new String[] {
					"修改警告速度(" + (int) chart.getWarningSpeed() + ")、最大车速"
							+ (int) chart.getMaxSpeed(),
					"重置警告速度和最大速度(默认:" + (int) chart.getDefaultWarningSpeed()
							+ "*" + (int) chart.getDefaultMaxSpeed() + ")",
					"修改折线图的长宽" + chart.getWIDTH() + "*" + chart.getHEIGHT(),
					"重置折线图(默认:" + chart.getDefaultWIDTH() + "*"
							+ chart.getDefaultHEIGHT() + ")" };
			Builder builder = new AlertDialog.Builder(MainActivity.this);
			builder.setTitle("设置");
			builder.setIcon(MainActivity.this.getResources().getDrawable(
					R.drawable.ic_menu_preferences));
			builder.setItems(items, new ManagerItemListener());
			builder.create().show();
		}

		class ManagerItemListener implements DialogInterface.OnClickListener {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				switch (which) {
				case 0:
					setMaxAndWarnSpeed();
					break;
				case 1:
					chart.resetMaxAndWarnSpeed();
					break;
				case 2:
					setChartWH();
				case 3:
					chart.resetWH();
				default:
					break;
				}
			}

			private void setMaxAndWarnSpeed() {
				setDialog(001, "输入警告车速", "警告车速:", "最大速度:",
						String.valueOf(chart.getWarningSpeed()),
						String.valueOf(chart.getMaxSpeed()));

			}

			public void setChartWH() {
				setDialog(002, "输入\"长\" \"宽\"分别为:", "长:", "宽:",
						String.valueOf(chart.getWIDTH()),
						String.valueOf(chart.getHEIGHT()));
			}

			public void setDialog(final int flag, String title,
					String tv1_string, String tv2_string,
					String et1_default_string, String et2_default_string) {
				// layout
				LinearLayout layout = (LinearLayout) getLayoutInflater()
						.inflate(R.layout.dialog, null);
				// 设置具有两行EditText的dialog
				final EditText et1 = (EditText) layout.findViewById(R.id.et1);
				final EditText et2 = (EditText) layout.findViewById(R.id.et2);
				final TextView tv1 = (TextView) layout.findViewById(R.id.tv1);
				final TextView tv2 = (TextView) layout.findViewById(R.id.tv2);

				if (flag == 002) {
					et1.setInputType(InputType.TYPE_CLASS_NUMBER);
					et2.setInputType(InputType.TYPE_CLASS_NUMBER);
				}

				et1.setText(et1_default_string);
				et2.setText(et2_default_string);
				tv1.setText(tv1_string);
				tv2.setText(tv2_string);

				et1.setSelectAllOnFocus(true);
				et2.setSelectAllOnFocus(true);

				// dialog
				Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setTitle(title);
				builder.setView(layout);
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
								switch (flag) {
								case 001:
									chart.setWarningSpeed(Float.valueOf(et1
											.getText().toString()));
									chart.setMaxSpeed(Integer
											.valueOf(new DecimalFormat("#")
													.format(Float.valueOf(et2
															.getText()
															.toString()))));
									txt_chart_max_speed.setText(et2.getText()
											.toString());
									break;
								case 002:
									chart.setWH(Integer.valueOf(et1.getText()
											.toString()), Integer.valueOf(et2
											.getText().toString()));
								}
							}
						});
				builder.setNegativeButton("取消", null);
				builder.create().show();
			}
		}
	}

	public void setChartMaxSpeed(String text) {
		txt_chart_max_speed.setText(text);
	}

	public void showCurrentDurition() {
		operateUtil.showCurrentDuritionTime();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.i("", "onDestroy is finished");
	}
}
