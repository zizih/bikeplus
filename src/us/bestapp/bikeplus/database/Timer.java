package us.bestapp.bikeplus.database;

import java.util.Date;

public class Timer {

	private long begin_time_long = 0;
	private long start_time_long = 0;
	private long pause_start_time = 0;
	private long pause_duration = 0;
	private long end_time_long = 0;

	private static Timer instance = null;

	private Timer() {
	}

	public static Timer getInstance() {
		if (instance == null) {
			instance = new Timer();
		}
		return instance;
	}

	public long now() {
		return new Date().getTime();
	}

	public void start() {
		start_time_long = now();
		pause_start_time = 0;
		pause_duration = 0;
	}

	public void pause() {
		pause_start_time = now();
	}

	public void resume() {
		pause_duration += now() - pause_start_time;
	}

	// 用于未resume暂时计算时间
	public long tmpGetLastTime() {
		long tmp = pause_duration;
		pause_duration += now() - pause_start_time;
		long result = now() - start_time_long - pause_duration;
		pause_duration = tmp;
		return result;
	}

	public void stop() {
		resume();
		end_time_long = now();
	}

	public long getLastTime() {
		return now() - start_time_long - pause_duration;
	}

	public long getBegin_time_long() {
		return begin_time_long;
	}

	public void setBegin_time_long(long begin_time_long) {
		this.begin_time_long = begin_time_long;
	}

	public long getStart_time_long() {
		return start_time_long;
	}

	public void setStart_time_long(long start_time_long) {
		this.start_time_long = start_time_long;
	}

	public long getPause_start_time() {
		return pause_start_time;
	}

	public void setPause_start_time(long pause_start_time) {
		this.pause_start_time = pause_start_time;
	}

	public long getPause_duration() {
		return pause_duration;
	}

	public void setPause_duration(long pause_duration) {
		this.pause_duration = pause_duration;
	}

	public long getEnd_time_long() {
		return end_time_long;
	}

	public void setEnd_time_long(long end_time_long) {
		this.end_time_long = end_time_long;
	}

}
