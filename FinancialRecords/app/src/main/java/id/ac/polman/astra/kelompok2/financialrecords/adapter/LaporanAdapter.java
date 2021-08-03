package id.ac.polman.astra.kelompok2.financialrecords.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.ac.polman.astra.kelompok2.financialrecords.Entity.UserEntity;
import id.ac.polman.astra.kelompok2.financialrecords.R;
import id.ac.polman.astra.kelompok2.financialrecords.model.KategoriModel;
import id.ac.polman.astra.kelompok2.financialrecords.model.LaporanModel;
import id.ac.polman.astra.kelompok2.financialrecords.model.ResponseModel;
import id.ac.polman.astra.kelompok2.financialrecords.ui.fragment.KategoriDialogFragment;
import id.ac.polman.astra.kelompok2.financialrecords.ui.fragment.KategoriTabFragment;
import id.ac.polman.astra.kelompok2.financialrecords.ui.fragment.LaporanTabFragment;
import id.ac.polman.astra.kelompok2.financialrecords.utils.Preference;

public class LaporanAdapter extends RecyclerView.Adapter<LaporanAdapter.MyViewHolder> {
    LaporanTabFragment ltr;
    List<LaporanModel> laporanList;

    public LaporanAdapter(LaporanTabFragment ltr, List<LaporanModel> laporanList){
        this.ltr = ltr;
        this.laporanList = laporanList;
    }

    @NonNull
    @Override
    public LaporanAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView =  inflater.inflate(R.layout.list_item_laporan, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        LaporanModel laporanModel = laporanList.get(position);
//        holder.nama_kategori.setText("Nama                 : "+laporanModel.);
//        holder.jenis_kategori.setText("Jenis Kategori : " + kategoriModel.getJenis());
//
//        Log.d("","CEK KEY:"+kategoriModel.getKey());
        Log.d("","CEK POSISI:"+getItemCount());

    }

    @Override
    public int getItemCount() {
        return laporanList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nama_kategori;
        TextView jenis_kategori;
        //TextView jenis_kategori;

        CardView cv_kategori;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            nama_kategori = itemView.findViewById(R.id.nama_kategori);
            jenis_kategori = itemView.findViewById(R.id.jenis_kategori);
            cv_kategori = itemView.findViewById(R.id.cv_kategori);
        }
    }
}
