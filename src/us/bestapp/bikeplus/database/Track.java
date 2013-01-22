package us.bestapp.bikeplus.database;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tracks")
public class Track implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5424203238201737651L;

	private static Track instance = null;

	Track() {
	}

	public static Track getInstance() {
		if (instance == null) {
			instance = new Track();
		}
		return instance;
	}

	@DatabaseField(generatedId = true)
	public Integer id;

	@DatabaseField
	public double startLatitude;

	@DatabaseField
	public double endLatitude;

	@DatabaseField
	public double startLongitude;

	@DatabaseField
	public double endLongitude;

	@DatabaseField
	public String startTime;

	@DatabaseField
	public String endTime;

	@DatabaseField
	public Long lastTime;

	@DatabaseField
	public double distance;

	@DatabaseField
	public double averageSpeed;

	@DatabaseField
	public double maxSpeed;

	@DatabaseField
	public String uploadTime;
}
