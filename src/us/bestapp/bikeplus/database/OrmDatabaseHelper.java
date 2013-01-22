package us.bestapp.bikeplus.database;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class OrmDatabaseHelper extends OrmLiteSqliteOpenHelper {

	private static final String DATABASE_NAME = "bestbiker.db";
	private static final int DATABASE_VERSION = 1;

	Dao<Track, Object> trackDao = null;

	@SuppressWarnings("unused")
	private Context context;

	public OrmDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	public boolean ClearAll() {
		ConnectionSource cs = getConnectionSource();
		try {
			TableUtils.clearTable(cs, Track.class);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource cs) {
		try {
			TableUtils.createTable(cs, Track.class);
		} catch (SQLException e) {
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource cs,
			int oldVersion, int newVersion) {
		try {
			TableUtils.dropTable(cs, Track.class, true);
			
			onCreate(db);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Dao<Track, Object> getTrackDao() throws SQLException {
		if (trackDao == null) {
			trackDao = DaoManager.createDao(getConnectionSource(),
					Track.class);
		}
		return trackDao;
	}
}
