package id.sera5.niat.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import id.sera5.niat.data.DatabaseHelper;

public class BaseActivity extends AppCompatActivity {

    private DatabaseHelper db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (db == null) db = new DatabaseHelper(this);
    }

    public SQLiteDatabase getReadableDatabase() {
        return db.getReadableDatabase();
    }

    public SQLiteDatabase getWritableDatabase() {
        return db.getWritableDatabase();
    }


    public void printDB() {
        SQLiteDatabase mdb = getReadableDatabase();

        Cursor c = mdb.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_TALL, null);

        if (c.moveToFirst()) {
            do {
                Log.d("DATABASE_NIAT", c.getInt(1) + " ayat " + c.getInt(2));
            } while (c.moveToNext());
        }

        c.close();

    }

    protected void launchActivity(Context c, Class<?> cls, Bundle bundle) {
        Intent i = new Intent(c, cls);
        if (bundle != null) i.putExtras(bundle);
        c.startActivity(i);
    }

    protected void launchActivity(Context c, Class<?> cls) {
        launchActivity(c, cls, null);
    }

    protected void setBackButton() {
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        } else if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
