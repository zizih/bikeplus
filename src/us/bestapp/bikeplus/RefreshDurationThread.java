package us.bestapp.bikeplus;

import android.app.Activity;
import android.content.Context;

public class RefreshDurationThread extends Thread {

	Context context;
	long startTime;
	public boolean isPause = false;

	public RefreshDurationThread(Context context) {
		this.context = context;
	}

	@Override
	public void run() {
		startTime = System.currentTimeMillis();
		int count = 0;
		while (true) {
			if (isPause == false) {
				if (count > 1000) {
					((Activity) context).runOnUiThread(new Runnable() {
						public void run() {
							((MainActivity) context).showCurrentDurition();
						}
					});
					count = 0;
				} else {
					try {
						Thread.sleep(100);
						count += 100;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
