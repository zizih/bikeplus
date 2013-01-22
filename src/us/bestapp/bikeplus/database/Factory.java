package us.bestapp.bikeplus.database;

public class Factory {

	public static Timer getTimerInstance() {
		return Timer.getInstance();
	}

	public static Track getTrackInstance() {
		return Track.getInstance();
	}

	public static Status getStatusInstance() {
		return Status.getInstance();
	}

}
