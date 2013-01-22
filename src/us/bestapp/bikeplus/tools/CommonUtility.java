package us.bestapp.bikeplus.tools;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtility {

	private static SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static String getFormatCurrentTime() {
		Date date = new Date();
		return localSimpleDateFormat.format(date);
	}

	public static String formatTime(long time) {
		Date date = new Date(time);
		return localSimpleDateFormat.format(date);

	}

	public static String formatDuring(long mss) {
		long days = mss / (1000 * 60 * 60 * 24);
		long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
		long seconds = (mss % (1000 * 60)) / 1000;
		StringBuilder sb = new StringBuilder();
		if (days != 0) {
			sb.append(days + " d ");
		}
		if (hours != 0) {
			sb.append(hours + " h ");
		}
		if (minutes != 0) {
			sb.append(minutes + " m ");
		}
		if (seconds != 0) {
			sb.append(seconds + " s ");
		}
		return sb.toString();
	}

	public static final double EARTH_RADIUS = 6378137.0;

	public static double gps2m(double lat_a, double lng_a, double lat_b,
			double lng_b) {
		double radLat1 = (lat_a * Math.PI / 180.0);
		double radLat2 = (lat_b * Math.PI / 180.0);
		double a = radLat1 - radLat2;
		double b = (lng_a - lng_b) * Math.PI / 180.0;
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}

	// DecimalFormat 不是四捨五入
	public static String formatSpeed(double speed) {
		return formatDecimal(speed * 3.6);
	}

	public static String formatDecimal(double value) {
		DecimalFormat df = new DecimalFormat("00.00");
		return df.format(value);
	}

	public static String formatDistance(double distance) {
		if (distance > 1000) {
			return formatDecimal(distance / 1000) + " km";
		}
		return (int) distance + " m";
	}
}