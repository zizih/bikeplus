package us.bestapp.bikeplus.tools;

import us.bestapp.bikeplus.R;
import android.content.Context;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//三个参数：doInBackground 、onProgressUpdate、 doInBackground(返回值)和onPostExecute（参数）
@SuppressWarnings("deprecation")
public class Gyroscope implements SensorEventListener {

	private float defautDegree = 0f;

	private ImageView img_gyroscope_bg;
	private ImageView img_gyroscope_arow;
	private TextView txt_bearing;

	private Resources mRes;

	public Gyroscope(final Context activity, ImageView img_gyroscope_bg,
			ImageView img_gyroscope_arow, TextView txt_bearing) {
		this.img_gyroscope_bg = img_gyroscope_bg;
		this.img_gyroscope_arow = img_gyroscope_arow;
		this.txt_bearing = txt_bearing;
		if (android.os.Build.VERSION.SDK_INT > 10) {
			img_gyroscope_arow.setRotation(defautDegree);
		} else {
			img_gyroscope_bg.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast toast = Toast.makeText(activity, "你的系统版本"
							+ android.os.Build.VERSION.RELEASE + "，不支持图片旋转",
							Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.TOP, 0, 0);
				}

			});
		}
		mRes = activity.getResources();
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
			if (android.os.Build.VERSION.SDK_INT > 10) {
				img_gyroscope_bg.setRotation(-event.values[0] - 90);
			}
			synchronized (this) {
				String text = null;
				if (Math.abs(event.values[0] - 0.0f) < 1)
					return;
				switch ((int) event.values[0] + 90) {
				case 0:
				case 360:
					text = mRes.getText(R.string.NORTH).toString();
					break;
				case 90:
					text = mRes.getText(R.string.EAST).toString();
					break;
				case 180:
					text = mRes.getText(R.string.SOUTH).toString();
					break;
				case 270:
					text = mRes.getText(R.string.WEST).toString();
					break;

				default:
					int v = (int) event.values[0] + 90;
					v = v >= 360 ? v - 360 : v;
					if (v > 0 && v < 90) {
						text = mRes.getText(R.string.NORTH_EAST).toString()
								+ " " + v + " 度";
						// chart.setSpeed(v / 3);// 用角度做速度测试chart
					}
					if (v > 90 && v < 180) {
						text = mRes.getText(R.string.SOUTH_EAST).toString()
								+ " " + (180 - v) + " 度";
					}
					if (v > 180 && v < 270) {
						text = mRes.getText(R.string.SOUTH_WEST).toString()
								+ " " + (v - 180) + " 度";
					}
					if (v > 270 && v < 360) {
						text = mRes.getText(R.string.NORTH_WEST).toString()
								+ " " + (360 - v) + " 度";
					}
					break;
				}
				txt_bearing.setText(text);
			}
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

}
