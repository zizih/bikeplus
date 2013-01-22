package us.bestapp.bikeplus.database;

public class Status {

	private boolean isStarted = false;
	private boolean isPause = false;
	private boolean isRunInBackground = false;

	private static Status instance = null;

	private Status() {

	}

	public static Status getInstance() {
		if (instance == null) {
			instance = new Status();
		}
		return instance;
	}

	public boolean isStarted() {
		return isStarted;
	}

	public void setStarted(boolean isStarted) {
		this.isStarted = isStarted;
	}

	public boolean isPause() {
		return isPause;
	}

	public void setPause(boolean isPause) {
		this.isPause = isPause;
	}

	public boolean isRunInBackground() {
		return isRunInBackground;
	}

	public void setRunInBackground(boolean isRunInBackground) {
		this.isRunInBackground = isRunInBackground;
	}

}
