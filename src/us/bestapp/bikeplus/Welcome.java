package us.bestapp.bikeplus;

import us.bestapp.bikeplus.database.Factory;
import us.bestapp.bikeplus.database.Status;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;

public class Welcome extends Activity implements OnTouchListener {

	private Intent mainIntent;
	private Status status = Factory.getStatusInstance();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		LinearLayout layout = new LinearLayout(this);
		layout.setOnTouchListener(this);
		mainIntent = new Intent(Welcome.this, MainActivity.class);

		if (status.isRunInBackground()) {
			startMainActivity();
		} else {

			new Handler().postDelayed(new Runnable() {
				public void run() {
					startMainActivity();
				}
			}, 3000);
		}

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		Log.i("about onTouch", +event.getDownTime() + "");
		startMainActivity();
		return false;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	public void startMainActivity() {
		startActivity(mainIntent);
		finish();
	}
}