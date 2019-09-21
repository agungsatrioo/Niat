package id.sera5.niat.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String TABLE_TALL = "history";
    public static final String COL_ID = "_ID";
    public static final String COL_SURAT = "_SURAT";
    public static final String COL_AYAT = "_AYAT";
    public static final String COL_DATE = "_TANGGAL";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "db_niat";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = String.format(Locale.US, "CREATE TABLE %s (" +
                "%s INTEGER PRIMARY KEY," +
                "%s INTEGER NOT NULL," +
                "%s INTEGER NOT NULL," +
                "%s TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL" +
                ");", TABLE_TALL, COL_ID, COL_SURAT, COL_AYAT, COL_DATE);

        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TALL);
        onCreate(sqLiteDatabase);
    }
}
