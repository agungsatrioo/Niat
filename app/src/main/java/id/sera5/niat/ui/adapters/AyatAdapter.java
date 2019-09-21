package id.sera5.niat.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.sera5.niat.R;
import id.sera5.niat.models.Ayat;

public class AyatAdapter extends RecyclerView.Adapter<AyatAdapter.AyatViewHolder> {
    private ArrayList<Ayat> listAyat;

    public AyatAdapter(ArrayList<Ayat> listAyat) {
        this.listAyat = listAyat;
    }

    @NonNull
    @Override
    public AyatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ayat, parent, false);
        return new AyatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AyatViewHolder holder, int position) {
        final Ayat item = listAyat.get(position);

        holder.noAyat.setText(item.getNumber());
        holder.itemArab.setText(item.getArab());
        holder.itemIndo.setText(item.getIndo());
    }

    @Override
    public int getItemCount() {
        return listAyat.size();
    }

    class AyatViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.no_ayat)
        TextView noAyat;
        @BindView(R.id.item_arab)
        TextView itemArab;
        @BindView(R.id.item_indo)
        TextView itemIndo;

        AyatViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
