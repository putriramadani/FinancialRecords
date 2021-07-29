package id.ac.polman.astra.kelompok2.financialrecords.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.ac.polman.astra.kelompok2.financialrecords.R;
import id.ac.polman.astra.kelompok2.financialrecords.model.KategoriModel;
import id.ac.polman.astra.kelompok2.financialrecords.ui.fragment.KategoriTabFragment;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    KategoriTabFragment ktr;
    List<KategoriModel> kategoriList;

    public RecyclerAdapter(KategoriTabFragment ktr, List<KategoriModel> kategoriList){
        this.ktr = ktr;
        this.kategoriList = kategoriList;
    }

    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView =  inflater.inflate(R.layout.list_item_kategori, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        KategoriModel kategoriModel = kategoriList.get(position);
        holder.nama_kategori.setText("Nama:"+kategoriModel.getPemasukan());
        holder.jenis_kategori.setText("Jenis Kategori:" + kategoriModel.getJenis());
    }

    @Override
    public int getItemCount() {
        return kategoriList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nama_kategori;
        TextView jenis_kategori;

        CardView cv_kategori;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            nama_kategori = itemView.findViewById(R.id.nama_kategori);
            jenis_kategori = itemView.findViewById(R.id.jenis_kategori);
            cv_kategori = itemView.findViewById(R.id.cv_kategori);
        }
    }
}
