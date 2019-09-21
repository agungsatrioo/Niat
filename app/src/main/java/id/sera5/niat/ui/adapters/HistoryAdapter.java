package id.sera5.niat.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.sera5.niat.R;
import id.sera5.niat.models.History;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private ArrayList<History> listAyat;
    private MyClickListener myClickListener;

    public HistoryAdapter(ArrayList<History> listAyat) {
        this.listAyat = listAyat;
    }


    public void setMyClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_ayat, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        final History item = listAyat.get(position);

        holder.textSurat.setText(String.format(Locale.US, "QS %s: %s", item.getSurat(), item.getAyat()));

        SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        Date date = new Date(Long.parseLong(item.getTimestamp()));

        holder.textTanggal.setText(sf.format(date));

        if(myClickListener != null) holder.itemView.setOnClickListener(view -> myClickListener.onClick(item));
    }

    @Override
    public int getItemCount() {
        return listAyat.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_surat)
        TextView textSurat;
        @BindView(R.id.text_tanggal)
        TextView textTanggal;

        HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface MyClickListener{
        public void onClick(History item);
    }
}
