package us.bestapp.bikeplus.database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;

import android.content.Context;

public class TrackTaker {

	public Context mContext;

	public TrackTaker(Context ctx) {
		mContext = ctx;
	}

	public void Insert(String startTime, String endTime, long lastTime,
			int distance, float averageSpeed, float maxSpeed) {
		OrmDatabaseHelper helper = (OrmDatabaseHelper) OpenHelperManager
				.getHelper(mContext, OrmDatabaseHelper.class);

		Track track = new Track();
		track.startTime = startTime;
		track.endTime = endTime;
		track.lastTime = lastTime;
		track.distance = distance;
		track.averageSpeed = averageSpeed;
		track.maxSpeed = maxSpeed;

		try {
			Dao<Track, Object> countryDao = helper.getTrackDao();
			countryDao.create(track);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void Insert(Track track) {
		OrmDatabaseHelper helper = (OrmDatabaseHelper) OpenHelperManager
				.getHelper(mContext, OrmDatabaseHelper.class);

		try {
			Dao<Track, Object> countryDao = helper.getTrackDao();
			countryDao.create(track);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void Delete(int id) {
		OrmDatabaseHelper helper = (OrmDatabaseHelper) OpenHelperManager
				.getHelper(mContext, OrmDatabaseHelper.class);

		try {
			Dao<Track, Object> countryDao = helper.getTrackDao();
			DeleteBuilder<Track, Object> deleteBuilder = countryDao
					.deleteBuilder();
			Where<Track, Object> where = deleteBuilder.where();
			where.eq("id", id);
			countryDao.delete(deleteBuilder.prepare());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Track> getAllTracks() {
		OrmDatabaseHelper helper = (OrmDatabaseHelper) OpenHelperManager
				.getHelper(mContext, OrmDatabaseHelper.class);

		List<Track> tracks = new ArrayList<Track>();

		try {
			Dao<Track, Object> countryDao = helper.getTrackDao();
			QueryBuilder<Track, Object> deleteBuilder = countryDao
					.queryBuilder();
			tracks = countryDao.query(deleteBuilder.prepare());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return tracks;
	}

	public void updateUploadTime(Track track) {
		OrmDatabaseHelper helper = (OrmDatabaseHelper) OpenHelperManager
				.getHelper(mContext, OrmDatabaseHelper.class);

		try {
			Dao<Track, Object> countryDao = helper.getTrackDao();
			UpdateBuilder<Track, Object> updateBuilder = countryDao
					.updateBuilder();
			Where<Track, Object> where = updateBuilder.where();
			where.eq("id", track.id);
			updateBuilder.updateColumnValue("uploadTime", track.uploadTime);
			countryDao.update(updateBuilder.prepare());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
