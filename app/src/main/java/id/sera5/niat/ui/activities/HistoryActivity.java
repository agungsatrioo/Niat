package id.sera5.niat.ui.activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.sera5.niat.R;
import id.sera5.niat.data.DatabaseHelper;
import id.sera5.niat.models.History;
import id.sera5.niat.ui.adapters.HistoryAdapter;

public class HistoryActivity extends BaseActivity {

    ArrayList<History> historyArrayList;
    @BindView(R.id.rv_history)
    RecyclerView rvHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);

        historyArrayList = new ArrayList<>();

        setTitle("Riwayat");
        setBackButton();

        readHistory();
    }

    private void readHistory() {
        SQLiteDatabase mdb = getReadableDatabase();

        Cursor c = mdb.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_TALL, null);

        if (c.moveToFirst()) {
            do {
                String surat = String.valueOf(c.getInt(1));
                String ayat = String.valueOf(c.getInt(2));
                String tanggal = String.valueOf(c.getInt(3));

                historyArrayList.add(new History(surat, ayat, tanggal));
            } while (c.moveToNext());
        }

        c.close();

        rvHistory.setLayoutManager(new LinearLayoutManager(this));
        HistoryAdapter listHeroAdapter = new HistoryAdapter(historyArrayList);
        rvHistory.setAdapter(listHeroAdapter);

        listHeroAdapter.setMyClickListener(item -> {
            Bundle b = new Bundle();
            b.putInt("surat", Integer.parseInt(item.getSurat()));
            b.putInt("ayat", Integer.parseInt(item.getAyat()));

            launchActivity(HistoryActivity.this, AyatActivity.class, b);
        });
    }
}
